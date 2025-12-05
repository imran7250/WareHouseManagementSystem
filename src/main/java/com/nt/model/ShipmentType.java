package com.nt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="shipment_type_tab")
public class ShipmentType {
  

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ship_id_col")
    private Integer id;
	
	@Column(name="ship_mode_col")
	private String shipmentMode;
	@Column(name="ship_code_col",unique=true)
	private String shipmentCode;
	@Column(name="ship_enable_ship_col")
	private String enableShipment;
	@Column(name="ship_grade_col")
	private String shipmentGrade;
	@Column(name="ship_description_col")
	private String description;
}
