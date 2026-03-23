package com.wallet.services;

import java.io.IOException;
import java.util.Objects;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.response.BulkUploadResultDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	
	public ResponseEntity<ClassPathResource> downloadTemplate() throws IOException{
		ClassPathResource file = new ClassPathResource("templates/walletexcel.xlsx");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"walletexcel.xlsx\"")
				.contentType(MediaType.parseMediaType(
		                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
		            .contentLength(file.contentLength())
		            .body(file);
	}
	
	public ResponseEntity<ApiResponse> bulkUpload( MultipartFile file , CustomerService cs){
		log.info("Bulk upload triggered");
		
		if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".xlsx")) { 
			ApiResponse apiresponse = ApiResponse.builder()
		            .code(HttpStatus.BAD_REQUEST.value())
		            .message("Please upload a valid .xlsx file")
		            .build();
		        return new ResponseEntity<>(apiresponse, HttpStatus.BAD_REQUEST);
		}
		
		BulkUploadResultDTO result = cs.bulkCreateCustomers(file);
		
		String message = String.format("Upload complete. success : %d, Failed: %d",result.getSuccessCount(),result.getFailureCount() );
		 ApiResponse apiresponse = ApiResponse.builder()
			        .code(HttpStatus.OK.value())
			        .message(message)
			        .data(result)
			        .build();
			    return new ResponseEntity<>(apiresponse, HttpStatus.OK);
	}
}
