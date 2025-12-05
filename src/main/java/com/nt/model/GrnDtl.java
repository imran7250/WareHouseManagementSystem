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
@Table(name="grn_dtl_tab")
public class GrnDtl {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grn_dtl_id_col")   // ✅ Correct column for detail PK
    private Integer id;
    
    @Column(name="grn_code_col")
    private String itemCode;
    
    @Column(name="grn_cost_col")
    private Double baseCost;
    
    @Column(name="grn_qty_col")
    private Integer qty;
    
    @Column(name="grn_val_col")
    private Double itemVal;
    
    @Column(name="grn_status_col")
    private String status;
    
    @ManyToOne
    @JoinColumn(name="grn_id_fk_col")   // ✅ Foreign key reference to master GRN
    private Grn grn;
}
