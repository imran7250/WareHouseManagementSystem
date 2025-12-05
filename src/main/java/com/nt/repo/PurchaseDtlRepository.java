package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.PurchaseDtl;

public interface PurchaseDtlRepository extends JpaRepository<PurchaseDtl, Integer> {
    
    @Query("SELECT pd FROM PurchaseDtl pd JOIN pd.order o WHERE o.id = :orderId")
    List<PurchaseDtl> findByOrderId(Integer orderId);
    
    @Query("SELECT count(pd.id) FROM PurchaseDtl pd JOIN pd.order o WHERE o.id = :orderId")
    public Integer findCountByOrderId(Integer orderId);
}
