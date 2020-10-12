package com.rk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rk.service.IUserService;

@SpringBootApplication
public class WarehouseAppApplication {
	@Autowired
	private IUserService service;

	public static void main(String[] args) {
		SpringApplication.run(WarehouseAppApplication.class, args);
	}

}
