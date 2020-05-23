package com.bloomberg.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 477333257520312644L;

	private static final String message = "File not found";

	public FileNotFoundException() {
		super(message);
	}
}
