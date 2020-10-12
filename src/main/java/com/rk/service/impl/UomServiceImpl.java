package com.rk.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.Uom;
import com.rk.repo.UomRepository;
import com.rk.service.IUomService;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repo;

	@Transactional
	public Integer saveUom(Uom u) {
		Integer id = repo.save(u).getId();
		return id;
	}

	@Transactional
	public void updateUom(Uom u) {
		repo.save(u);
	}

	@Transactional
	public void deleteUom(Integer id) {
		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<Uom> getOneUom(Integer id) {
		Optional<Uom> opt = repo.findById(id);
		return opt;
	}

	@Transactional(readOnly = true)
	public List<Uom> getAllUoms() {
		List<Uom> list = repo.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	public boolean isUomExist(Integer id) {
		boolean exist = repo.existsById(id);
		return exist;
	}
	
	@Transactional(readOnly = true)
	public Map<Integer, String> getUomIdAndModel() {
		/*
		Map<Integer,String> map =repo.getUomIdAndModel();
		.stream()
		.collect(Collectors.toMap(
				array->Integer.valueOf(array[0].toString()), 
				array->array[1].toString()));
				*/
		Map<Integer,String> map = new LinkedHashMap<>();
		List<Object[]> list = repo.getUomIdAndModel();
		for(Object[] ob:list) {
			map.put(
					Integer.valueOf(ob[0].toString()), 
					ob[1].toString());
		}
		
		return map;
	}
	
	@Override
	public Page<Uom> getAllUoms(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	@Override
	public boolean isUomModelExist(String model) {
		return repo.getUomModelCount(model)>0?true:false;
	}
	
	
	
	
	
	
	

}