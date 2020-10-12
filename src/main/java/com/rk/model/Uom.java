package com.rk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="uom_tab")
public class Uom {
	
	@Id
	@GeneratedValue
	@Column(name="uom_id_col")
	private Integer id;
	
	@Column(name="uom_type_col", length = 15, nullable = false)
	private String uomType;
	
	@Column(name="uom_model_col", length = 10, nullable = false)
	@NotNull(message = "Uom Model can not be null")
	//@Size(min = 3,max = 6,message = "Model Must be 3-6 Chars only")
	@Pattern(regexp = "[A-Z]{3,6}",message = "Invaild Pattern Entered")
	private String uomModel;
	
	@Column(name="description_col", length = 150, nullable = false)
	@Size(min=2,max = 10,message = "Length must be 2-10 chars only")
	@NotNull(message = "Uom description can not be null")
	private String description;
}