package com.nt.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="order_method_tab")
public class OrderMethod {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ord_id_col")
	private Integer id;
    
	@Column(name="ord_mode_col")
	private String orderMode;
	@Column(name="ord_code_col")
	private String orderCode;
	@Column(name="ord_tpye_col")
	private String orderType;
	@ElementCollection
	@CollectionTable(name="ord_acpt_tab" , joinColumns=@JoinColumn(name="ord_id_col"))
	@Column(name="ord_acpt_col")
	private List<String> orderAcpt;
	
	@Column(name="ord_desc_col")  
	private String description;
}
