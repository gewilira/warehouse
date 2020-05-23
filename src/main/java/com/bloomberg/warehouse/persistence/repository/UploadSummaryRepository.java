package com.bloomberg.warehouse.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloomberg.warehouse.persistence.entities.UploadSummary;

@Repository
public interface UploadSummaryRepository extends JpaRepository<UploadSummary, Long>{
	
	Optional<UploadSummary> findTopByOrderByIdDesc();
	
	Optional<UploadSummary> findByFileName(String fileName);

}
