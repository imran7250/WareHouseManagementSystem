package com.nt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="docs_tab")
public class Document {
   
	@Id
	@GeneratedValue
	@Column(name="doc_id")
	private Integer docId;
	@Column(name="doc_name")
	private String docName;
	@Lob
	@Column(name="doc_data", columnDefinition = "LONGBLOB")
	private byte[] docData;
	
}
