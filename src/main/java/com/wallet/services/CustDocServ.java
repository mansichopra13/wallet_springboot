package com.wallet.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.DocDTO;
import com.wallet.enums.DocType;
import com.wallet.model.Customer;
import com.wallet.model.CustomerDocuments;


public interface CustDocServ {
//	 int savedocs(DocDTO dd) throws IOException;
//	 CustomerDocuments getDocFromCustDocId(int custdocid);

	int savedocs(MultipartFile file, int cid, DocType docType) throws IOException;
    CustomerDocuments getDocFromCustDocId(int custdocid);
   List<CustomerDocuments> getDocsByCustomerId(int cid);
   void deleteDoc(int custdocid);
   Optional<CustomerDocuments> getDocsByDocname(int cid, DocType docType);

}

