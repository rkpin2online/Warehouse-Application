package com.rk.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.model.Uom;
import com.rk.service.IUomService;

@RestController
@RequestMapping("/rest/uom")
public class UomRestController {
	@Autowired
	private IUomService service;

	//1. getAll Records from DB
	@GetMapping("/all")
	public ResponseEntity<List<Uom>> getAll(){
		List<Uom> list = service.getAllUoms();
		//return ResponseEntity.ok(list);
		return new ResponseEntity<List<Uom>>(
				list,
				HttpStatus.OK);
	}

	//2. get one Record from DB
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(
			@PathVariable Integer id)
	{
		Optional<Uom> opt = service.getOneUom(id);
		ResponseEntity<?> resp = null;
		if(opt.isPresent()) {
			resp = new ResponseEntity<Uom>(
					opt.get(),
					HttpStatus.OK);
		}else {
			resp = new ResponseEntity<String>(
					"Data Not Found",
					HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	//3. delete one Record from DB
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> delete(
			@PathVariable Integer id
			)
	{
		ResponseEntity<String> resp = null;
		if(service.isUomExist(id)) {
			try {
				service.deleteUom(id);
				resp = new ResponseEntity<String>(
						"Uom Deleted",
						HttpStatus.OK);
			} catch (Exception e) {
				resp = new ResponseEntity<String>(
						"Uom Can not be deleted, Used by other Operation",
						HttpStatus.BAD_REQUEST);
			}
		}else {
			resp = new ResponseEntity<String>(
					"Uom Not exist",
					HttpStatus.NOT_FOUND);
		}
		return resp;
	}


	//4. save one Record into DB
	@PostMapping("/insert")
	public ResponseEntity<?> save(
			@Valid @RequestBody Uom uom, BindingResult errors)
	{
		ResponseEntity<?> resp = null;
		try {

			if(errors.hasErrors()) {
				HashMap<String, String> errorsMap = new HashMap<>();
				for(FieldError err: errors.getFieldErrors()) {
					errorsMap.put(err.getField(), err.getDefaultMessage());
				}
				resp = new ResponseEntity<HashMap<String, String>>(
						errorsMap,HttpStatus.BAD_REQUEST);
			}else {
				//save data
				Integer id = service.saveUom(uom);
				resp = new ResponseEntity<String>(
						"Uom '"+id+"' saved with Id",
						HttpStatus.CREATED);
			}

		} catch (Exception e) {
			resp = new ResponseEntity<String>(
					"Uom unable to save",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	//5. update one Record into DB
	@PutMapping("/modify")
	public ResponseEntity<String> upate(
			@RequestBody Uom uom 
			)
	{
		ResponseEntity<String> resp = null;
		if(uom.getId()==null || !service.isUomExist(uom.getId()) ) {
			resp = new ResponseEntity<String>(
					"Given Uom not exist in DB",
					HttpStatus.NOT_FOUND);
		}else {
			service.updateUom(uom);
			resp = new ResponseEntity<String>(
					"Uom '"+uom.getId()+"' Updated",
					HttpStatus.OK);
		}

		return resp;
	}



}
