package com.rk.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.ShipmentType;
import com.rk.repo.ShipmentTypeRepository;
import com.rk.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {
	@Autowired
	private ShipmentTypeRepository repo;

	@Transactional
	public Integer saveShipmentType(ShipmentType st) {
		Integer id=repo.save(st).getId();
		return id;
	}

	@Transactional
	public void updateShipmentType(ShipmentType st) {
		repo.save(st);
	}

	@Transactional
	public void deleteShipmentType(Integer id) {
		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<ShipmentType> getOneShipmentType(Integer id) {
		Optional<ShipmentType> opt=repo.findById(id);
		return opt;
	}

	@Transactional(readOnly = true)
	public List<ShipmentType> getAllShipmentTypes() {
		List<ShipmentType> list=repo.findAll();
		return list;
	}
	
	@Transactional(readOnly = true)
	public Page<ShipmentType> getAllShipmentTypes(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public boolean isShipmentTypeExist(Integer id) {
		boolean exist=repo.existsById(id);
		return exist;
	}

	@Transactional(readOnly = true)
	public boolean isShipmentTypeCodeExist(String shipmentCode) {
		int count=repo.getShipmentCodeCount(shipmentCode);
		boolean flag = (count>0 ? true: false);  
		return flag;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isShipmentTypeCodeExistForEdit(String code, Integer id) {
		int count=repo.getShipmentCodeCountForEdit(code,id);
		boolean flag = (count>0 ? true: false);  
		return flag;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getShipmentModeCount() {
		return repo.getShipmentModeCount();
	}

	@Override
	public Map<Integer, String> getShipmentIdAndCode() {
		return repo.getShipmentIdAndCode().stream()
				.collect(Collectors.toMap(
						ob->Integer.valueOf(ob[0].toString()), 
						ob->ob[1].toString()
						)
						);
	}
}
