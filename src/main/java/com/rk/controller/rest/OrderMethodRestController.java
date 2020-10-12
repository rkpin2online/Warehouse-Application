package com.rk.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.model.OrderMethod;
import com.rk.service.IOrderMethodService;

@RestController
@RequestMapping("/rest/ordermethod")
public class OrderMethodRestController {
	@Autowired
	private IOrderMethodService service;
	
	@GetMapping("/all")
	public ResponseEntity<List<OrderMethod>> getAll()
	{
		return ResponseEntity.ok(service.getAllOrderMethods());
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody OrderMethod orderMethod)
	{
		return ResponseEntity.ok(service.saveOrderMethod(orderMethod));
	}
	
	
}
