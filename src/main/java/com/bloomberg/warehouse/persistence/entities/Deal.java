package com.bloomberg.warehouse.persistence.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Deal {
	
	@Id
	private Long id;
	
	private String fromCurrencyCode;
	
	private String toCurrencyCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dealDate;
	
	private BigDecimal amount;
	

}
