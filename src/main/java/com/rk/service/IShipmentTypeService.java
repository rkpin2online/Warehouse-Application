package com.rk.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rk.model.ShipmentType;
 
public interface IShipmentTypeService {

	Integer saveShipmentType(ShipmentType st);
	void updateShipmentType(ShipmentType st);
	
	void deleteShipmentType(Integer id);
	Optional<ShipmentType> getOneShipmentType(Integer id);
	
	List<ShipmentType> getAllShipmentTypes();
	Page<ShipmentType> getAllShipmentTypes(Pageable pageable);
	
	boolean isShipmentTypeExist(Integer id);
	
	boolean isShipmentTypeCodeExist(String shipmentCode);
	boolean isShipmentTypeCodeExistForEdit(String code,Integer id);
	
	public List<Object[]> getShipmentModeCount();
	
	public Map<Integer,String>  getShipmentIdAndCode();
	
}
