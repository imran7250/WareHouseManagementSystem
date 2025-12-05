package com.nt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.PurchaseDtl;
import com.nt.model.PurchaseOrder;

public interface IPurchaseOrderService {
  
	//Screen#1
	public Integer SavePurchaseOrder(PurchaseOrder po);
	public PurchaseOrder getPurchaseOrder(Integer id);//public PurchaseOrder getOnePurchaseOrder(Integer id);
	public List<PurchaseOrder> getAllPurchaseOrders();
	//Screen#2
	public Integer savePurchaseDtl(PurchaseDtl dtl);  
    List<PurchaseDtl> findByOrderId(Integer orderId);//public List<PurchaseDtls> getPurchaseDtlsByOrderId(Integer orderId)
    public void removePurchaseDtl(Integer dtlId);
    public void updateStatus(Integer orderId,String status);
    public Integer findCountByOrderId(Integer orderId);
    public Map<Integer,String> getPurchaseOrderIdAndCodeByStatus(String status);
    
	public Page<PurchaseOrder> getAllPurchaseOrders(Pageable p);
}
