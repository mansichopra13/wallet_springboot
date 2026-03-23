package com.wallet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.common.ApiResponse;
import com.wallet.enums.DocType;
import com.wallet.model.CustomerDocuments;
import com.wallet.services.CustDocServ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/docs")
public class CustDocController {

    @Autowired
    private CustDocServ cds;

    @Value("${file.upload.dir}") private String BASE_DIR;
  
    @PostMapping(value = "/upload/{customerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadDocument(@PathVariable int customerId, @RequestParam("file") MultipartFile file,
            @RequestParam("docType") String docType) {
    	
    	
        try {
            int docId = cds.savedocs(file, customerId, DocType.valueOf(docType.toUpperCase()));
            ApiResponse response = ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Document Uploaded Successfully")
                    .data(docId)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } 

        catch (Exception e) {
            log.error("Upload failed", e);
            ApiResponse response = ApiResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to upload document")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{docId}")
    public ResponseEntity<ApiResponse> getDocument(@PathVariable int docId) {
        try {
            CustomerDocuments doc = cds.getDocFromCustDocId(docId);
            ApiResponse response = ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Document fetched successfully")
                    .data(doc)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse response = ApiResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Document not found")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse> getDocsByCustomer(@PathVariable int customerId) throws IOException {
        List<CustomerDocuments> docs = cds.getDocsByCustomerId(customerId);
        
        if(docs==null || docs.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("No documents found")
                            .data(null)
                            .build());
        }
        
        List<Map<String,Object>> responselist = new ArrayList<>();
        for(CustomerDocuments doc :docs) {
        	Path fullPath = Paths.get(BASE_DIR,doc.getFilePath());
        	
        	if(!Files.exists(fullPath)) continue;
        	
        	byte[] imageBytes = Files.readAllBytes(fullPath);
        	
        	String base64image= Base64.getEncoder().encodeToString(imageBytes);
        	
        	Map<String,Object> map = new HashMap<>();
        	map.put("custdocid", doc.getCustdocid());
        	map.put("docname", doc.getDocname());
        	map.put("fileName",doc.getFileName());
        	map.put("filetype", doc.getFiletype());
        	map.put("docuploaddate", doc.getDocuploaddate());
        	map.put("status", doc.isStatus());
        	map.put("imageData", base64image);
        	
        	responselist.add(map);
        	
        }
        
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Documents fetched successfully")
                        .data(responselist)
                        .build()
        );
    }
    
    @GetMapping("/doc/{customerId}/{docType}")
    public ResponseEntity<byte[]> getDocsByDocname(@PathVariable int customerId ,@PathVariable DocType docType) throws IOException {
        Optional<CustomerDocuments> docs = cds.getDocsByDocname(customerId, docType);
        
        if (docs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomerDocuments doc = docs.get();
        Path fullPath = Paths.get(BASE_DIR , doc.getFilePath());

        if (!Files.exists(fullPath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(fullPath);
        MediaType mediaType = MediaType.parseMediaType(doc.getFiletype());

        
        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(imageBytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFileName() + "\"")
                .body(imageBytes);
    }
    
    
    
    @DeleteMapping("/delete/{docid}")
    public ResponseEntity<ApiResponse> deleteDoc(@PathVariable int docid){
    	try {
    	cds.deleteDoc(docid);
    	ApiResponse apiResponse = ApiResponse.builder()
    							  .code(HttpStatus.OK.value())
    							  .message("Doc Deleted Successfully")
    							  .build();
    	return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.OK);
    	}catch(Exception e) {
    		ApiResponse apiResponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value())
    									.message("doc not found")
    									.build();
    		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    	}
    	
    }
    
   
    
}


