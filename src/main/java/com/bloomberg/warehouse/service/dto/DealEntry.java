package com.bloomberg.warehouse.service.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.bloomberg.warehouse.enums.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealEntry {
	private String dealDate;
	private String fromCurrencyCode;
	private String toCurrencyCode;
	private String amount;

	public boolean isValidDeal() {
		return validateDate(getDealDate(), "M/d/yyyy hh:mm") && validateCurrency(getFromCurrencyCode())
				&& validateCurrency(getToCurrencyCode()) && validateAmount(getAmount());
	}

	private boolean validateDate(String date, String format) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			formatter.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean validateCurrency(String currency) {
		try {
			Currency.valueOf(currency);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean validateAmount(String amount) {
		try {
			new BigDecimal(amount);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}