package com.nt.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="usertab")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="uid")
	private Integer id;
	
	@Column(name="uname")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="pwd")
	private String pwd;
	
	@ElementCollection(fetch=FetchType.EAGER) //EAGER
	@CollectionTable(name="rolestab", joinColumns=@JoinColumn(name="uid"))
	@Column(name="role")
	private List<String> roles;
	
    @Column(name="uactive", columnDefinition = "TINYINT(1)")
    private boolean active;
	
}
