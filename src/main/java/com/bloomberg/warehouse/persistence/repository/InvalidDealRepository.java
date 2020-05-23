package com.bloomberg.warehouse.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloomberg.warehouse.persistence.entities.InvalidDeal;

@Repository
public interface InvalidDealRepository extends JpaRepository<InvalidDeal, Long> {

	Optional<InvalidDeal> findTopByOrderByIdDesc();
}
