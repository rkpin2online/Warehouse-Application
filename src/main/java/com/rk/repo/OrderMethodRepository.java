package com.rk.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.OrderMethod;

public interface OrderMethodRepository 
extends JpaRepository<OrderMethod, Integer>, 
JpaSpecificationExecutor<OrderMethod> 
{

	@Query("select count(om.orderCode) from OrderMethod om where om.orderCode=:code")
	public Integer getOrderMethodCodeCount(String code);

	@Query("select count(om.orderCode) from OrderMethod om where om.orderCode=:code and om.id!=:id")
	public Integer getOrderMethodCodeCountForEdit(String code,Integer id);

	@Query(" select orderMode, count(orderMode) from  OrderMethod  group by orderMode")
	public List<Object[]> getOrderModeCount();
}
