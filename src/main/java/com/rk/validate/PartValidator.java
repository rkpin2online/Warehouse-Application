package com.rk.validate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rk.model.Part;
import com.rk.service.IPartService;
import com.rk.service.IUomService;

@Component
public class PartValidator {
	@Autowired
	private IPartService service;
	@Autowired
	private IUomService uomService;
	
	
	public Map<String,String> validate(Part part)
	{
		Map<String,String> errors = new HashMap<>();
	
		if(part.getPartCode()==null || part.getPartCode().isBlank()) {
			errors.put("partCode", "Invaild Part code Entered");
		}else if(service.isPartCodeExist(part.getPartCode())) {
			errors.put("partCode", "Part code already exist Entered");
		}
		
		//integration validation
		if(part.getUom()==null || part.getUom().getId()<=0) {
			errors.put("uom", "Invaild Uom Id provided");
		}else if(!uomService.isUomExist(part.getUom().getId())) {
			errors.put("uom", "Uom Id not exist");
			
		}
		
		return errors;
	}

}
