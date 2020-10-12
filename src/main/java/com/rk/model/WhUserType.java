package com.rk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="wh_user_type_tab")
public class WhUserType {
	@Id
	@GeneratedValue(generator = "whusertype_seq")
	@SequenceGenerator(name="whusertype_seq",sequenceName = "whuser_type_seq")
	@Column(name="wh_usr_id_col")
	private Integer id;
	
	@Column(name="wh_usr_type_col")
	private String userType;
	@Column(name="wh_usr_code_col")
	private String userCode;
	@Column(name="wh_usr_for_col")
	private String userFor;

	@Column(name="wh_usr_mail_col")
	private String userMail;
	@Column(name="wh_usr_contact_col")
	private String userContact;
	
	@Column(name="wh_usr_id_type_col")
	private String userIdType;
	@Column(name="wh_usr_if_other_col")
	private String ifother;
	@Column(name="wh_usr_if_num_col")
	private String idNumber;
	
	
}
