package com.bloomberg.warehouse.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.bloomberg.warehouse.exceptions.FileAlreadyUploadedException;
import com.bloomberg.warehouse.service.dto.DealFileResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class DealServiceTest {

	@Autowired
	private DealService dealService;
	
	@Test
	public void uploadResponse() throws IOException {
		File file = new File("src/test/resources/deal.csv");
		InputStream targetStream = new FileInputStream(file);
		MockMultipartFile initialFile = new MockMultipartFile("deal", targetStream);

		DealFileResponse response = dealService.processUploadFile(initialFile);

		assertThat(response.getTotal()).isEqualTo(103968);
		assertThat(response.getSuccess()).isEqualTo(103932);
		assertThat(response.getFailed()).isEqualTo(36);
	}
	
	@Test(expected = FileAlreadyUploadedException.class)
	public void fileAlreadyExist() throws IOException {
		
	    
		File file = new File("src/test/resources/deal.csv");
		InputStream targetStream = new FileInputStream(file);
		MockMultipartFile initialFile = new MockMultipartFile("deal", targetStream);

		dealService.processUploadFile(initialFile);
		
		File secondFile = new File("src/test/resources/deal.csv");
		InputStream secondTargetStream = new FileInputStream(secondFile);
		MockMultipartFile secondInitalFile = new MockMultipartFile("deal", secondTargetStream);

		dealService.processUploadFile(secondInitalFile);

	}
}