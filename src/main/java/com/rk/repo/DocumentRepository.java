package com.rk.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

	@Query(" select doc.docId, doc.docName from com.rk.model.Document doc ")
	List<Object[]> findIdAndName();
}
