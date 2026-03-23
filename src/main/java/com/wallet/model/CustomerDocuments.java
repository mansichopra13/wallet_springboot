package com.wallet.model;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wallet.enums.DocType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDocuments {
	 @Id
	 @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="dgen")
	 @SequenceGenerator(name="dgen",sequenceName="dseq",allocationSize=1)
	 private int custdocid;
	 @ManyToOne
	 @JoinColumn(name="customeridfk")
	 @JsonIgnore
	 private Customer customer;
	 @Enumerated(EnumType.STRING)
	 private DocType docname;
	 
	 private String filetype;
	 
	 @Column(name="file_path")
	 private String filePath;
	 private String fileName;
	
	 private LocalDate docuploaddate;
	 
	 @Builder.Default
	 private boolean status=true;
}
