package com.rk.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rk.model.WhUserType;
 
public interface IWhUserTypeService {

	Integer saveWhUserType(WhUserType user);
	void updateWhUserType(WhUserType user);
	
	void deleteWhUserType(Integer id);
	Optional<WhUserType> getOneWhUserType(Integer id);
	
	List<WhUserType> getAllWhUserTypes();
	boolean isWhUserTypeExist(Integer id);

	boolean isWhUserTypeEmailExist(String mail);
	
	Map<Integer,String> getWhUserTypeIdAndCode(String userType);
}
