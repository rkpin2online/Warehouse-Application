package com.rk.controller.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.model.Part;
import com.rk.service.IPartService;
import com.rk.validate.PartValidator;

@RestController
@RequestMapping("/rest/part")
public class PartRestController {
	@Autowired
	private IPartService service;
	@Autowired
	private PartValidator vaidator;
	
	@PostMapping("/save")
	public ResponseEntity<?> savePart(
			@RequestBody Part part
			) 
	{
		ResponseEntity<?> resp = null;
		Map<String,String> errors=vaidator.validate(part);
		if(errors.isEmpty()) {
			Integer id = service.savePart(part);
			resp = new ResponseEntity<>("Part '"+id+"' Saved",HttpStatus.CREATED);
		} else {
			resp = new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
