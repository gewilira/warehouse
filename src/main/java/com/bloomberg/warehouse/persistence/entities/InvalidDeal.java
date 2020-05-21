package com.bloomberg.warehouse.persistence.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InvalidDeal extends Deal {

	private String fileName;
}
