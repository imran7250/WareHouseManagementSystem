package com.nt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.SaleDtl;
import com.nt.model.SaleOrder;

public interface ISaleOrderService {
  
	//Screen#1
	public Integer SaveSaleOrder(SaleOrder so);
	public SaleOrder getSaleOrder(Integer id);//public SaleOrder getOneSaleOrder(Integer id);
	public List<SaleOrder> getAllSaleOrders();
	//Screen#2
	public Integer saveSaleDtl(SaleDtl dtl);  
    List<SaleDtl> findByOrderId(Integer orderId);//public List<SaleDtls> getSaleDtlsByOrderId(Integer orderId)
    public void removeSaleDtl(Integer dtlId);
    public void updateStatus(Integer orderId,String status);
    public Integer findCountByOrderId(Integer orderId);
    public Map<Integer,String> getSaleOrderIdAndCodeByStatus(String status);
    
	public Page<SaleOrder> getAllSaleOrders(Pageable p);
}
