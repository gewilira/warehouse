package com.bloomberg.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bloomberg.warehouse.exceptions.FileAlreadyUploadedException;
import com.bloomberg.warehouse.exceptions.FileCannotBeParseException;
import com.bloomberg.warehouse.exceptions.FileNotFoundException;
import com.bloomberg.warehouse.service.DealService;
import com.bloomberg.warehouse.service.dto.DealFileResponse;

@Controller
public class DealController {

	@Autowired
	private DealService dealService;

	@GetMapping("/upload")
	public String upload() {
		return "upload";
	}

	@PostMapping("/upload-deal")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {
		DealFileResponse fileResponse = dealService.processUploadFile(file);
		model.addAttribute("fileName", fileResponse.getFilename());
		model.addAttribute("duration", fileResponse.getDuration());
		model.addAttribute("success", fileResponse.getSuccess());
		model.addAttribute("failed", fileResponse.getFailed());
		model.addAttribute("total", fileResponse.getTotal());
		model.addAttribute("message", fileResponse.getMessage());

		return "upload-status";
	}
	
	@ExceptionHandler({FileAlreadyUploadedException.class, FileCannotBeParseException.class, FileNotFoundException.class})
	public ModelAndView handleException(RuntimeException ex) {
		// Do something additional if required
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("upload-status");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}

}
