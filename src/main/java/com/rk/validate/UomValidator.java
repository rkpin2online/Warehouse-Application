package com.rk.validate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rk.model.Uom;
import com.rk.service.IUomService;

@Component
public class UomValidator {

	@Autowired
	private IUomService service;
	
	public Map<String,String> validate(Uom uom)
	{
		Map<String,String> errors = new HashMap<>();
		if(uom.getUomType()==null || uom.getUomType().isBlank()) {
			errors.put("uomType", "Empty Uom Type is Entered");
		}
		if(uom.getUomModel()==null || uom.getUomModel().isBlank()) {
			errors.put("uomModel", "Empty Uom Model is Entered");
		}else if(service.isUomModelExist(uom.getUomModel())) {
			errors.put("uomModel", "Uom Model '"+uom.getUomModel()+"' already Exist");
		}
		
		return errors;
	}
}


