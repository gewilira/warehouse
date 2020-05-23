package com.bloomberg.warehouse.persistence.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ValidDeal implements IDeal {

	@Id
	private Long id;
	private String fromCurrencyCode;
	private String toCurrencyCode;
	private String dealDate;
	private String amount;
	private String fileName;

	@Override
	public void setId(long id) {
		this.id = id;
	}

}
