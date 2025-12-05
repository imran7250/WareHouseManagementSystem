package com.nt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nt.model.OrderMethod;
import com.nt.repo.OrderMethodRepository;
import com.nt.service.IOrderMethodService;
import com.nt.service.exception.OrderMethodNotFoundException;
import com.nt.util.MyCollectionUtil;


@Service   // <-- add this annotation
public class OrderMethodServiceImpl implements IOrderMethodService {

	@Autowired
	private OrderMethodRepository repo;
	
	@Override
	public Integer saveOrderMethod(OrderMethod OrderMethod) {		  
		return repo.save(OrderMethod).getId();
	}

	@Override
	@Transactional(readOnly=true)
	public List<OrderMethod> getAllOrderMethods() {		
		return repo.findAll();
	}

	@Override
	public void updateOrderMethod(OrderMethod OrderMethod) {		
		repo.save(OrderMethod);
	}

	@Override
	public void deleteOrderMethod(Integer id) {
		OrderMethod OrderMethod=getOneOrderMethod(id);
		repo.delete(OrderMethod);
		
		
	}

	@Override
	public OrderMethod getOneOrderMethod(Integer id) {
		OrderMethod OrderMethod=repo.findById(id).
				orElseThrow(()-> new OrderMethodNotFoundException("OrderMethod'"+id+"'Not exists"));
		return OrderMethod;
	}

	@Override
	public Map<Integer, String> getOrderMethodIdAndCodeByMode(String mode) {
		List<Object[]> list=repo.getOrderMethodIdAndCodeByMode(mode);
		Map<Integer,String> map=MyCollectionUtil.convertListToMap(list);		
		return map;
	}

    @Override
    public boolean isOrderMethodModelExist(String orderCode) {
        return repo.existsByOrderCode(orderCode); // Assuming orderCode is unique
    }

	@Override
	public Page<OrderMethod> getAllOrderMethods(Pageable p) {
	return repo.findAll(p);
	}
}
