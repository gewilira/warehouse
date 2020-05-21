package com.bloomberg.warehouse.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloomberg.warehouse.persistence.entities.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

}
