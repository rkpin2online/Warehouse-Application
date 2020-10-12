package com.rk.edi;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.rk.model.Uom;
import com.rk.service.IUomService;
import com.rk.util.EmailUtil;
import com.rk.validate.UomValidator;

@Component
public class UomEdiMqService {

	private Logger log = LoggerFactory.getLogger(UomEdiMqService.class);

	@Autowired
	private IUomService service;

	@Autowired
	private UomValidator validator;

	@Autowired
	private EmailUtil util;
	@Value("${my.app.admin.email}")
	private String to;

	@JmsListener(destination = "uomcreate")
	public void createUom(String uomjson) {
		String message = null;
		try {
			log.info("UOM EDI SERVICE-SAVE OPERATION");
			ObjectMapper om = new ObjectMapper();
			Uom ob = om.readValue(uomjson, Uom.class);

			Map<String,String> errors =  validator.validate(ob);
			if(errors.isEmpty()) {
				//perform save
				Integer id = service.saveUom(ob);
				message = "UOM EDI SAVE SUCCESS:" + id;
				log.info(message);
			}else {
				message = "UOM CREATION FAILED WITH ERRORS => " + errors.values().toString();
				log.error(message);
			}
		} catch (Exception e) {
			message = "UOM SAVE FAIL:" + e.getMessage();
			log.error(message);
			e.printStackTrace();
		}
		//send one ack email to admin@support.com
		util.send(to, "CREATE-UOM", message);
	}
}
