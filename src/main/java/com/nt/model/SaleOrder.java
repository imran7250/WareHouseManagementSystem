package com.nt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sale_order_tab")
public class SaleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "so_id_col")
    private Integer id;

    @Column(name = "so_code_col")
    private String orderCode;


    @Column(name = "so_ref_num_col")
    private String refNumber;

    @Column(name = "so_stock_mode_col")
    private String stockMode;             // radio buttons

    @Column(name = "so_stock_source_col")
    private String stockSource;           // dropdown

    @Column(name = "so_status_col")
    private String status;                // “SALE-OPEN” by default (set in service)

    @Column(name = "so_desc_col")
    private String description;

	@ManyToOne
	@JoinColumn(name="ship_id_fk_col")
	private ShipmentType shipmentType; //HAS-A variable
	
    @ManyToOne
    @JoinColumn(name = "customer_id_fk_col")
    private WhUserType customer;              // dropdown (WhUser of type CUSTOMER)
}
