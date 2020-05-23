package com.bloomberg.warehouse.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bloomberg.warehouse.persistence.entities.InvalidDeal;
import com.bloomberg.warehouse.persistence.entities.UploadSummary;
import com.bloomberg.warehouse.persistence.repository.InvalidDealRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class InvalidDealRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private InvalidDealRepository invalidDealRepository;

	@Test
	public void saveTheGetOne() {
	    InvalidDeal deal = InvalidDeal.builder()
	    		.id(1L)
	    		.dealDate("09-09-2019x")
	    		.fileName("deal.csv")
	    		.amount("1000d")
	    		.fromCurrencyCode("USD")
	    		.toCurrencyCode("AED")
	    		.build();
	    entityManager.persist(deal);
	    entityManager.flush();
	 
	    InvalidDeal found = invalidDealRepository.getOne(1L);
	 
	    assertThat(found.toString()).isEqualTo(deal.toString());
	}
}