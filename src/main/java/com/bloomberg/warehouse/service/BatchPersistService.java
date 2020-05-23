package com.bloomberg.warehouse.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bloomberg.warehouse.persistence.entities.IDeal;

@Service
public class BatchPersistService {

	@Autowired
	private EntityManager entityManager;

	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int batchSize;

	@Transactional
	public <T extends IDeal> void saveData(List<T> records, long lastId) {
		entityManager.flush();

		int i = 0;
		for (T info : records) {
			i++;
			info.setId(++lastId);
			entityManager.persist(info);

			if (i % batchSize == 0) {
				entityManager.flush();
				entityManager.clear();
				i = 0;
			}
		}
		;

		entityManager.flush();
		entityManager.clear();
	}

}
