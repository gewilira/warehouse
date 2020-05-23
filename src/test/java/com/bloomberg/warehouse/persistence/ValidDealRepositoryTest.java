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
import com.bloomberg.warehouse.persistence.entities.ValidDeal;
import com.bloomberg.warehouse.persistence.repository.ValidDealRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ValidDealRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ValidDealRepository validDealRepository;

	@Test
	public void persist_thenGetOne() {
		UploadSummary file = UploadSummary.builder()
	    		.id(1L)
	    		.duration("12:08:22")
	    		.failed(10)
	    		.success(20)
	    		.total(30)
	    		.fileName("sample").build();
	    entityManager.persist(file);
	    
	    ValidDeal deal = ValidDeal.builder()
	    		.id(1L)
	    		.dealDate("09-09-2019")
	    		.fileName("deal.csv")
	    		.amount("100.00")
	    		.fromCurrencyCode("USD")
	    		.toCurrencyCode("AED")
	    		.build();
	    entityManager.persist(deal);
	    entityManager.flush();
	 
	    ValidDeal found = validDealRepository.getOne(1L);
	 
	    assertThat(found.toString()).isEqualTo(deal.toString());
	}
}