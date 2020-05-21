package com.bloomberg.warehouse.persistence.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;


/*@Entity
@Getter
@Setter*/
public class DealsFileSummary {

	private String fileName;
	private Integer dealCount;
	private String from_currency_code;
	private Boolean success;
	private Boolean failed;
}
