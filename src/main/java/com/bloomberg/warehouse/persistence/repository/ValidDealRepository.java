package com.bloomberg.warehouse.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloomberg.warehouse.persistence.entities.ValidDeal;

public interface ValidDealRepository extends JpaRepository<ValidDeal, Long> {

}
