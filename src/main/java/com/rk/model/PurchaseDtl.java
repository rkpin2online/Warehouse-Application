package com.rk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name="purchase_dtl_tab")
public class PurchaseDtl {

	@Id
	@GeneratedValue
	@Column(name="pur_dtl_id_col")
	private Integer id;
	
	@Transient
	private Integer slno;
	
	@Column(name="pur_dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name="part_id_fk")
	private Part part; //HAS-A
	
	/**
	 * Every PurchaseDtl must be linked 
	 * with its PurchaseOrder parent(Screen#1)
	 */
	@ManyToOne
	@JoinColumn(name="po_id_fk")
	private PurchaseOrder po; //HAS-A
	
}
