package com.nt.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.nt.model.SaleDtl;

public interface SaleDtlRepository extends JpaRepository<SaleDtl, Integer> {
    
    @Query("SELECT sd FROM SaleDtl sd JOIN sd.order o WHERE o.id = :orderId")
    List<SaleDtl> findByOrderId(Integer orderId);
    
    @Query("SELECT count(sd.id) FROM SaleDtl sd JOIN sd.order o WHERE o.id = :orderId")
    public Integer findCountByOrderId(Integer orderId);
}
