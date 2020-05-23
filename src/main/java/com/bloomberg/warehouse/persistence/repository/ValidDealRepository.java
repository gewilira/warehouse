package com.bloomberg.warehouse.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloomberg.warehouse.persistence.entities.ValidDeal;

@Repository
public interface ValidDealRepository extends JpaRepository<ValidDeal, Long> {

	Optional<ValidDeal> findTopByOrderByIdDesc();
}
