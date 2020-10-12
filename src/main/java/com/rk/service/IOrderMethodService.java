package com.rk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.rk.model.OrderMethod;
 
public interface IOrderMethodService {

	Integer saveOrderMethod(OrderMethod om);
	void updateOrderMethod(OrderMethod om);
	
	void deleteOrderMethod(Integer id);
	Optional<OrderMethod> getOneOrderMethod(Integer id);
	
	List<OrderMethod> getAllOrderMethods();
	boolean isOrderMethodExist(Integer id);
	
	boolean isOrderMethodCodeExist(String code);
	boolean isOrderMethodCodeExistEdit(String code,Integer id);
	List<Object[]> getOrderModeCount();
	
	List<OrderMethod> getAllOrderMethods(Specification<OrderMethod> spec);
}
