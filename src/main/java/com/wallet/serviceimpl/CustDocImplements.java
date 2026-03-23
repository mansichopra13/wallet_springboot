package com.wallet.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.DocDTO;
import com.wallet.enums.DocType;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.model.Customer;
import com.wallet.model.CustomerDocuments;
import com.wallet.repo.CustDocRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.services.CustDocServ;
import com.wallet.services.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustDocImplements implements CustDocServ{
	
	
	@Autowired
	CustomerRepo cr;
	
	@Autowired
	CustDocRepo cdr;
	
	@Value("${file.upload.dir}")
	private String BASE_DIR;
	
	@Override
	public int savedocs(MultipartFile file, int cid, DocType docType) throws IOException {
		Optional<Customer> optcust = cr.findById(cid);
        if (optcust.isEmpty()) {
            throw new ResourceNotFoundException("Kindly register as a customer first");
        }
        
        Customer customer = optcust.get();
        
        Optional<CustomerDocuments> existing= cdr.findByCustomer_CustomeridAndDocnameAndStatusTrue(cid, docType);
        if(existing.isPresent()) {
        	throw new IllegalStateException("A "+ docType.name()+" document already exist, delete the existing one to upload the new ");
        }
        
        
        //creatingfolder
        Path folder = Paths.get(BASE_DIR,"customer_"+cid);
        Files.createDirectories(folder);
        String fileName = docType.name()+"_"+System.currentTimeMillis()+"_"+file.getOriginalFilename();
        Files.write(folder.resolve(fileName),file.getBytes());
       
        //urlpathdb
        String filePath = "/customer_"+cid+"/"+fileName;
        
        
        CustomerDocuments cd = CustomerDocuments.builder()
                .customer(customer)
                .docname(docType)
                .filetype(file.getContentType())
                .filePath(filePath)
                .fileName(fileName)
                .docuploaddate(LocalDate.now())
                .status(true)
                .build();
        
        CustomerDocuments savedDoc = cdr.save(cd);
        return savedDoc.getCustdocid();
        
	}

	@Override
	public CustomerDocuments getDocFromCustDocId(int custdocid) {
		
//		CustomerDocuments optdoc = cdr.findByDocid(custdocid);
//		
//		return optdoc;
		
		Optional<CustomerDocuments> optDoc = cdr.findById(custdocid);
        if (optDoc.isEmpty()) {
            throw new ResourceNotFoundException("Document not found with id: " + custdocid);
        }
        return optDoc.get();
	}

	@Override
	public List<CustomerDocuments> getDocsByCustomerId(int cid) {
		return cdr.findByCustomer_CustomeridAndStatusTrue(cid);
	}
	
	public Optional<CustomerDocuments> getDocsByDocname(int cid , DocType docname) {
		return cdr.findByCustomer_CustomeridAndDocnameAndStatusTrue(cid, docname);
	}

	@Override
	public void deleteDoc(int custdocid) {
		CustomerDocuments doc = cdr.findByDocid(custdocid);
		if(doc==null) {
			throw new ResourceNotFoundException("doc not found with id :"+ custdocid);	
		}
		doc.setStatus(false);
		cdr.save(doc);
		
	}
	
	
	
//	@Override
//	public int savedocs(DocDTO dd) throws IOException {
//		
//		Optional<Customer> optcust= cr.findById(dd.getCustomer().getCustomerid());
//		if(optcust.isEmpty()) {
//			throw new ResourceNotFoundException("Kindly register as a customer first");
//		}
//		
//		Customer cust = optcust.get();
//		cust.setCustomerid(dd.getCustomer().getCustomerid());
//		
//		CustomerDocuments cd= CustomerDocuments.builder()
//							  .customer(cust)
//							  .docname(dd.getDocname())
//							  .filetype(dd.getFiletype())
//							  .filedata(dd.getFiledata())
//							  .docuploaddate(LocalDate.now())
//							  .build();
//		
//		CustomerDocuments cdd=cdr.save(cd);
//		return cdd.getCustdocid();
//	}
//
//	@Override
//	public CustomerDocuments getDocFromCustDocId(int custdocid) {
//		
//		return null;
//	}

	

	

}
