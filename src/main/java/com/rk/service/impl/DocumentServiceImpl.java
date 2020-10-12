package com.rk.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.Document;
import com.rk.repo.DocumentRepository;
import com.rk.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private DocumentRepository repo;
	
	@Override
	@Transactional
	public void saveDocument(Document doc) {
		repo.save(doc);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findIdAndName() {
		return repo.findIdAndName();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Document> findDocument(Integer id) {
		return repo.findById(id);
	}
}
