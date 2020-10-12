package com.rk.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.Part;
import com.rk.repo.PartRepository;
import com.rk.service.IPartService;
@Service
public class PartServiceImpl implements IPartService {
	@Autowired
	private PartRepository repo;

	@Override
	@Transactional
	public Integer savePart(Part p) {
		return repo.save(p).getId();
	}

	@Override
	@Transactional
	public void updatePart(Part p) {
		repo.save(p);
	}

	@Override
	@Transactional
	public void deletePart(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Part> getOnePart(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Part> getAllParts() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPartExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPartCodeExist(String partCode) {		
		return (repo.getPartCodeCount(partCode))>0?true:false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPartCodeExistForEdit(String partCode, Integer id) {
		return (repo.getPartCodeCountForEdit(partCode,id))>0?true:false;
	}

	@Override
	public Map<Integer, String> getPartIdAndCode() {
		return repo.getPartIdAndCode()
				.stream()
				.collect(Collectors.toMap(
						ob->Integer.valueOf(ob[0].toString()), 
						ob->ob[1].toString())
						);
	}
}