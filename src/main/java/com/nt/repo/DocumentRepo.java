package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.Document;     
public interface DocumentRepo extends JpaRepository<Document, Integer> {

	@Query("SELECT docId,docName FROM Document")
	List<Object[]> getDocumentIdAndNames();
}
      