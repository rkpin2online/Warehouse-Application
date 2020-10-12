package com.rk.generator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


public class ShipmentTypeGen implements IdentifierGenerator{

	@Override
	public Serializable generate(
			SharedSessionContractImplementor session, Object object) 
			throws HibernateException {
	
		String pref="SHIP-";
		String dte=new SimpleDateFormat("ddMMyyyy").format(new Date());
		int random=new Random().nextInt(10000000);
		return pref+dte+"-"+random;
	}
}



