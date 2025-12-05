package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.SaleOrder;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {
 
	@Modifying
	@Query("UPDATE SaleOrder SET status=:status WHERE id=:orderId")
	public void updateSaleOrderStatusById(Integer orderId,String status);
	
	@Query("SELECT id,orderCode FROM SaleOrder WHERE status=:status")
	public List<Object[]> getSaleOrderIdAndCodeByStatus(String status);
}
