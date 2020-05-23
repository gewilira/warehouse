package com.bloomberg.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileCannotBeParseException extends RuntimeException {

	private static final long serialVersionUID = 477333257520312644L;

	private static final String message = "File unparseable exception";

	public FileCannotBeParseException() {
		super(message);
	}
}
