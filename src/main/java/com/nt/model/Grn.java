package com.nt.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="grn_tab")
public class Grn {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grn_id_col")
    private Integer id;
    
    @Column(name="grn_code_col")
    private String code;
    
    @Column(name="grn_type_col")
    private String type;
    
    @Column(name="grn_desc_col")    
    private String description;
    
    //Integrations
    //1..1=*..1
    @ManyToOne
    @JoinColumn(name="po_order_id_fk")  // Removed unique=true
    private PurchaseOrder po;
    


}