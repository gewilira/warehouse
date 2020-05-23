package com.bloomberg.warehouse.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bloomberg.warehouse.persistence.entities.UploadSummary;
import com.bloomberg.warehouse.persistence.repository.UploadSummaryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UploadSummaryRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UploadSummaryRepository uploadSummaryRepository;

	@Test
	public void saveThenFindByFileName() {
		UploadSummary uploadSummary = UploadSummary.builder()
	    		.id(1L)
	    		.duration("12:08:22")
	    		.failed(10)
	    		.success(20)
	    		.total(30)
	    		.fileName("sample").build();
	    entityManager.persist(uploadSummary);
	    entityManager.flush();
	 
	    UploadSummary found = uploadSummaryRepository.findByFileName(uploadSummary.getFileName()).get();
	 
	    assertThat(found.toString()).isEqualTo(uploadSummary.toString());
	}
	
	@Test
	public void findTopByOrderByIdDesc() {
		UploadSummary uploadSummary = UploadSummary.builder()
	    		.id(10L)
	    		.duration("12:08:22")
	    		.failed(10)
	    		.success(20)
	    		.total(30)
	    		.fileName("sample").build();
	    entityManager.persist(uploadSummary);
	    
	    UploadSummary file2 = UploadSummary.builder()
	    		.id(5L)
	    		.duration("12:08:22")
	    		.failed(10)
	    		.success(20)
	    		.total(30)
	    		.fileName("sample").build();
	    entityManager.persist(file2);
	    entityManager.flush();
	 
	    UploadSummary found = uploadSummaryRepository.findTopByOrderByIdDesc().get();
	 
	    assertThat(found.toString()).isEqualTo(uploadSummary.toString());
	}

}