package com.nt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.model.OrderMethod;

@Service
public interface IOrderMethodService {

	public Integer saveOrderMethod(OrderMethod om);
	public List<OrderMethod> getAllOrderMethods();
	public void updateOrderMethod(OrderMethod om);
	public void deleteOrderMethod(Integer id);
	public OrderMethod getOneOrderMethod(Integer id);
	
	Map<Integer,String> getOrderMethodIdAndCodeByMode(String mode);
	//public boolean isOrderMethodModelExist(String model);
	
	  boolean isOrderMethodModelExist(String  orderCode);
	  
	  public Page<OrderMethod> getAllOrderMethods(Pageable p);
}
