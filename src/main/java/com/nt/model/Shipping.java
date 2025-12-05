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
@Table(name="Shipping_tab")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shipping_id_col")
    private Integer id;

    @Column(name="shipping_code_col")
    private String code;

    @Column(name="shipping_ref_num_col")
    private String shippingRefNum;

    @Column(name="courier_ref_num_col")
    private String courierRefNum;

    @Column(name="contact_details_col")
    private String contactDetails;

    @Column(name="description_col")
    private String description;


    //Integration
    @ManyToOne
    @JoinColumn(name="so_order_id_fk")
    private SaleOrder so;

}
