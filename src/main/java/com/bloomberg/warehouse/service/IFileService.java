package com.bloomberg.warehouse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bloomberg.warehouse.service.dto.DealEntry;
import com.bloomberg.warehouse.service.dto.DealFileResponse;

public interface IFileService {

	void validateFile(MultipartFile file);

	List<DealEntry> parseFile(MultipartFile file);

	DealFileResponse processUploadFile(MultipartFile file);
}
