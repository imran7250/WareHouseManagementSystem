package com.nt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="so_dtl_tab")
public class SaleDtl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="so_dtl_id_col")
	private Integer id;
	@Column(name="so_dtl_qty_col")
	private Integer qty;
	
	//HAS-A /*..1
	//SaleOrder--<>part
    @ManyToOne
	@JoinColumn(name="pat_id_fk_col")
	private Part part;//HAS-A
    
    @ManyToOne
    @JoinColumn(name="order_id_fk_col")
    private SaleOrder order;
}
