package com.nt.service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.model.PurchaseDtl;
import com.nt.model.PurchaseOrder;
import com.nt.repo.PurchaseDtlRepository;
import com.nt.repo.PurchaseOrderRepository;
import com.nt.service.IPurchaseOrderService;
import com.nt.service.exception.PurchaseOrderNotFound;
import com.nt.util.MyCollectionUtil;

import jakarta.transaction.Transactional;


@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repo;
	@Autowired
	private PurchaseDtlRepository dtlRepo;
	
	@Override
	public Integer SavePurchaseOrder(PurchaseOrder po) {
		return repo.save(po).getId();
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return repo.findAll();
	}

	@Override
	public PurchaseOrder getPurchaseOrder(Integer id) {
		return repo.findById(id).orElseThrow(()-> new PurchaseOrderNotFound("NOT FOUND"));
	}

	
	//Screen#2
	@Override
	public Integer savePurchaseDtl(PurchaseDtl dtl) {
		return dtlRepo.save(dtl).getId();
	}

	@Override
	public List<PurchaseDtl> findByOrderId(Integer orderId) {
	return dtlRepo. findByOrderId(orderId);
	}

	@Override
	public void removePurchaseDtl(Integer id) {
	dtlRepo.deleteById(id);
		
	}

	@Override
	@Transactional
	public void updateStatus(Integer orderId, String status) {
		repo.updatePurchaseOrderStatusById(orderId, status);
		
	}

	@Override
	public Integer findCountByOrderId(Integer orderId) {
		return dtlRepo.findCountByOrderId(orderId);
	}

	@Override
	public Map<Integer, String> getPurchaseOrderIdAndCodeByStatus(String status) {
		List<Object[]> list=repo.getPurchaseOrderIdAndCodeByStatus( status);
		return MyCollectionUtil.convertListToMap(list);
	}

	@Override
	public Page<PurchaseOrder> getAllPurchaseOrders(Pageable p) {
		return repo.findAll(p);
	}

}
