package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer> {
  
	@Query("SELECT Id,orderCode FROM OrderMethod WHERE orderMode=:mode ")
    public List<Object[]> getOrderMethodIdAndCodeByMode(String mode);
	
    boolean existsByOrderCode(String orderCode);
	
}
