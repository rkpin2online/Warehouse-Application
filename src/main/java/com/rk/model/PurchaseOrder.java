package com.rk.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="purchase_order_tab")
public class PurchaseOrder {

	@Id
	@GeneratedValue
	@Column(name="porder_id_col")
	private Integer id;

	@Column(name="porder_code_col")
	private String orderCode;

	@Column(name="porder_ref_number_col")
	private String referenceNumber;

	@Column(name="porder_qtly_check_col")
	private String qualityCheck;

	@Column(name="porder_df_status_col")
	private String status;

	@Column(name="porder_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="shipment_type_id_fk")
	private ShipmentType shipmentType; //HAS-A
	
	@ManyToOne
	@JoinColumn(name="whuser_type_id_fk")
	private WhUserType vendor;//HAS-A
	
	//Bidirectional mapping
	@OneToMany(mappedBy = "po",fetch = FetchType.EAGER)
	private List<PurchaseDtl> dtls;
}
