package com.rk.spec;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.rk.model.OrderMethod;

//generate dynamic where condition(predicate)
public class OrderMethodSpec implements Specification<OrderMethod>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderMethod filter; //<<--ModelAttributes

	public OrderMethodSpec(OrderMethod filter) {
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(
			Root<OrderMethod> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder cb) 
	{
		Predicate p = cb.conjunction();//cb.disjunction();
		List<Expression<Boolean>> expressions = p.getExpressions(); //empty expression

		if(filter.getOrderCode()!=null && !filter.getOrderCode().isBlank()) {
			//where orderCode like '%__%'
			expressions.add(
					cb.like(
							root.get("orderCode"), 
							"%"+filter.getOrderCode()+"%"
							)
					);
		}
		if(filter.getDescription()!=null && !filter.getDescription().isBlank()) {
			expressions.add(
					cb.like(
							root.get("description"), 
							"%"+filter.getDescription()+"%"
							)
					);
		}
		if(filter.getOrderType()!=null && !filter.getOrderType().isBlank()) {
			expressions.add(
					cb.like(
							root.get("orderType"), 
							"%"+filter.getOrderType()+"%"
							)
					);
		}
		return p;
	}

}
