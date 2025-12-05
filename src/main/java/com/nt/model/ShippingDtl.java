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
@Table(name="shipping_dtl_tab")
public class ShippingDtl {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shipping_dtl_id_col")
    private Integer id;
    
    @Column(name="shipping_item_code_col")
    private String itemCode;
    
    @Column(name="shipping_base_cost_col")
    private Double baseCost;
    
    @Column(name="shipping_qty_col")
    private Integer qty;
    
    @Column(name="shipping_item_val_col")
    private Double itemVal;
    
    @Column(name="shipping_status_col")
    private String status;
    
    @ManyToOne
    @JoinColumn(name="shipping_id_fk_col")
    private Shipping shipping;
}
