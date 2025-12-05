package com.nt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.model.SaleDtl;
import com.nt.model.SaleOrder;
import com.nt.repo.SaleDtlRepository;
import com.nt.repo.SaleOrderRepository;
import com.nt.service.ISaleOrderService;
import com.nt.service.exception.PurchaseOrderNotFound;

import com.nt.util.MyCollectionUtil;

import jakarta.transaction.Transactional;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

    @Autowired
    private SaleOrderRepository repo;

    @Autowired
    private SaleDtlRepository dtlRepo;

    // Save SaleOrder
    @Override
    public Integer SaveSaleOrder(SaleOrder so) {
        return repo.save(so).getId();
    }

    // Get all SaleOrders
    @Override
    public List<SaleOrder> getAllSaleOrders() {
        return repo.findAll();
    }

    // Get SaleOrder by ID
    @Override
    public SaleOrder getSaleOrder(Integer id) {
        return repo.findById(id)
                   .orElseThrow(() -> new PurchaseOrderNotFound("Sale Order NOT FOUND"));
    }

    // Save SaleDtl
    @Override
    public Integer saveSaleDtl(SaleDtl dtl) {
        return dtlRepo.save(dtl).getId();
    }

    // Find SaleDtl by SaleOrder ID
    @Override
    public List<SaleDtl> findByOrderId(Integer orderId) {
        return dtlRepo.findByOrderId(orderId);
    }

    // Remove SaleDtl
    @Override
    public void removeSaleDtl(Integer id) {
        dtlRepo.deleteById(id);
    }

    // Update SaleOrder status
    @Override
    @Transactional
    public void updateStatus(Integer orderId, String status) {
        repo.updateSaleOrderStatusById(orderId, status);
    }

    // Count SaleDtl by Order ID
    @Override
    public Integer findCountByOrderId(Integer orderId) {
        return dtlRepo.findCountByOrderId(orderId);
    }

    // Get SaleOrder ID and Code by Status
    @Override
    public Map<Integer, String> getSaleOrderIdAndCodeByStatus(String status) {
        List<Object[]> list = repo.getSaleOrderIdAndCodeByStatus(status);
        return MyCollectionUtil.convertListToMap(list);
    }

    // Get paginated SaleOrders
    @Override
    public Page<SaleOrder> getAllSaleOrders(Pageable p) {
        return repo.findAll(p);
    }
}
