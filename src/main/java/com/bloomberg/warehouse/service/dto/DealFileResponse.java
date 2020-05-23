package com.bloomberg.warehouse.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealFileResponse {

	private String filename;

	@Builder.Default
	private String duration = "00:00:00";

	@Builder.Default
	private Integer success = 0;

	@Builder.Default
	private Integer failed = 0;
	private String message;

	public Integer getTotal() {
		return success + failed;
	}
}
