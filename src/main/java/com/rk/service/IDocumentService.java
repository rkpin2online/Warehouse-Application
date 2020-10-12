package com.rk.service;

import java.util.List;
import java.util.Optional;

import com.rk.model.Document;

public interface IDocumentService {

	public void saveDocument(Document doc);
	public List<Object[]> findIdAndName();
	public Optional<Document> findDocument(Integer id);
}
