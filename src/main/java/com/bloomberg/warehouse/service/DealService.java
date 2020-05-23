package com.bloomberg.warehouse.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bloomberg.warehouse.exceptions.FileAlreadyUploadedException;
import com.bloomberg.warehouse.exceptions.FileCannotBeParseException;
import com.bloomberg.warehouse.exceptions.FileNotFoundException;
import com.bloomberg.warehouse.persistence.entities.InvalidDeal;
import com.bloomberg.warehouse.persistence.entities.UploadSummary;
import com.bloomberg.warehouse.persistence.entities.ValidDeal;
import com.bloomberg.warehouse.persistence.repository.InvalidDealRepository;
import com.bloomberg.warehouse.persistence.repository.UploadSummaryRepository;
import com.bloomberg.warehouse.persistence.repository.ValidDealRepository;
import com.bloomberg.warehouse.service.dto.DealEntry;
import com.bloomberg.warehouse.service.dto.DealFileResponse;
import com.bloomberg.warehouse.utils.DateUtils;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class DealService implements IFileService {

	private final UploadSummaryRepository uploadSummaryRepository;
	private final BatchPersistService batchPersistService;
	private final ValidDealRepository validDealRepository;
	private final InvalidDealRepository invalidDealRepository;

	@Autowired
	public DealService(UploadSummaryRepository uploadSummaryRepository, BatchPersistService batchPersistService,
			ValidDealRepository validDealRepository,
			InvalidDealRepository invalidDealRepository) {
		this.uploadSummaryRepository = uploadSummaryRepository;
		this.batchPersistService = batchPersistService;
		this.invalidDealRepository = invalidDealRepository;
		this.validDealRepository = validDealRepository;
	}

	@Override
	public DealFileResponse processUploadFile(MultipartFile file) {

		validateFile(file);

		Instant start = Instant.now();

		List<DealEntry> entries = parseFile(file);
		List<InvalidDeal> invalidDeals = new ArrayList<>();
		List<ValidDeal> validDeals = entries.parallelStream().filter(entry -> {
			if (!entry.isValidDeal()) {
				invalidDeals.add(InvalidDeal.builder().amount(entry.getAmount()).dealDate(entry.getDealDate())
						.fromCurrencyCode(entry.getFromCurrencyCode()).toCurrencyCode(entry.getFromCurrencyCode())
						.fileName(file.getOriginalFilename()).build());
				return false;
			}
			return true;
		}).map(entry -> {
			return ValidDeal.builder().amount(entry.getAmount()).dealDate(entry.getDealDate())
					.fromCurrencyCode(entry.getFromCurrencyCode()).toCurrencyCode(entry.getToCurrencyCode())
					.fileName(file.getOriginalFilename()).build();
		}).collect(Collectors.toList());

		batchPersistService.saveData(validDeals, validDealRepository.findTopByOrderByIdDesc().map(ValidDeal::getId).orElse(0L));
		batchPersistService.saveData(invalidDeals, invalidDealRepository.findTopByOrderByIdDesc().map(InvalidDeal::getId).orElse(0L));

		UploadSummary uploadSummary = UploadSummary.builder()
				.id(uploadSummaryRepository.findTopByOrderByIdDesc().map(UploadSummary::getId).orElse(1L) + 1)
				.success(validDeals.size()).failed(invalidDeals.size()).total(validDeals.size() + invalidDeals.size())
				.duration(DateUtils.millisToString(Duration.between(start, Instant.now()).toMillis(), "HH:mm:ss.SSS"))
				.fileName(file.getOriginalFilename()).build();
		uploadSummaryRepository.save(uploadSummary);

		return DealFileResponse.builder().duration(uploadSummary.getDuration()).success(uploadSummary.getSuccess())
				.failed(uploadSummary.getFailed()).filename(uploadSummary.getFileName())
				.message("File uploaded successfully").build();
	}

	@Override
	public void validateFile(MultipartFile file) {
		if (file.isEmpty()) {
			throw new FileNotFoundException();
		}

		if (uploadSummaryRepository.findByFileName(file.getOriginalFilename()).isPresent()) {
			throw new FileAlreadyUploadedException();
		}
	}

	@Override
	public List<DealEntry> parseFile(MultipartFile file) {
		try {
			Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			CsvToBean<DealEntry> csvToBean = new CsvToBeanBuilder<DealEntry>(reader).withType(DealEntry.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			return csvToBean.parse();
		} catch (IOException e) {
			throw new FileCannotBeParseException();
		}
	}
}
