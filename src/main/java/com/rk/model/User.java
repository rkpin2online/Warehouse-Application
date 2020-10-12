package com.rk.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usertab")
public class User {
	@Id
	@GeneratedValue
	@Column(name="usr_id_col")
	private Integer id;

	@Column(name="usr_name_col")
	private String name;
	
	@Column(name="usr_email_col")
	private String email;

	@Column(name="usr_pwd_col")
	private String password;
	
	@Column(name="usr_active_col")
	private int active;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="user_roles_tab",
			joinColumns = @JoinColumn(name="id_join_col")
			)
	@Column(name="usr_role_col")
	private Set<String> roles;

}