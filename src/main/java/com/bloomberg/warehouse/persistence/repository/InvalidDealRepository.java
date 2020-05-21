package com.bloomberg.warehouse.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloomberg.warehouse.persistence.entities.InvalidDeal;

public interface InvalidDealRepository extends JpaRepository<InvalidDeal, Long>{

}
