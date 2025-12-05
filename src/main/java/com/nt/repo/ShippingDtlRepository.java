package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nt.model.ShippingDtl;

@Repository
public interface ShippingDtlRepository extends JpaRepository<ShippingDtl, Integer> {

    @Query("SELECT dtl FROM ShippingDtl dtl JOIN dtl.shipping as shipping WHERE shipping.id=:shippingId")
    public List<ShippingDtl> getAllShippingDtlsByShippingID(@Param("shippingId") Integer shippingId);
    
    @Modifying
    @Query("UPDATE ShippingDtl SET status=:shippingDtlStatus WHERE id=:shippingDtlId")
    public Integer updateShippingDtlStatus(
        @Param("shippingDtlId") Integer shippingDtlId, 
        @Param("shippingDtlStatus") String status
    );
}
