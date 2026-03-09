package com.emp.model;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.emp.enums.DocType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
