package com.rk.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rk.model.Uom;

public interface IUomService {
	Integer saveUom(Uom u);
	void updateUom(Uom u);
	void deleteUom(Integer id);
	Optional<Uom> getOneUom(Integer id);
	List<Uom> getAllUoms();
	boolean isUomExist(Integer id);
	boolean isUomModelExist(String model);
	
	Map<Integer,String> getUomIdAndModel();
	
	Page<Uom> getAllUoms(Pageable pageable);
	
}