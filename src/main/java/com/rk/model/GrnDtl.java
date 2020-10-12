package com.rk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="grn_dtl_tab")
public class GrnDtl {

	@Id 
	@Column(name="grn_dtl_id_col")
	@GeneratedValue
	private Integer id;
	
	@Column(name="grn_dtl_part_code_col")
	private String partCode;
	@Column(name="grn_dtl_cost_col")
	private Double baseCost;
	@Column(name="grn_dtl_qty_col")
	private Integer qty;
	@Column(name="grn_dtl_line_col")
	private Double lineValue;
	
	@Column(name="grn_dtl_status_col")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="grn_id_fk")
	private Grn grn;
	
}
