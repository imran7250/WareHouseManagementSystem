package com.nt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nt.model.Shipping;
import com.nt.model.ShippingDtl;
import com.nt.repo.ShippingDtlRepository;
import com.nt.repo.ShppingRepository;
import com.nt.service.IShippingService;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShppingRepository repo;
    
    @Autowired
    private ShippingDtlRepository dtlRepo;
    
    @Override
    @Transactional
    public Integer saveShipping(Shipping shipping) {        
        return repo.save(shipping).getId();
    }

    @Override
    public List<Shipping> getAllShippings() {        
        return repo.findAll();
    }

    @Override
    @Transactional
    public Integer saveShippingDtl(ShippingDtl shippingDtl) {
        return dtlRepo.save(shippingDtl).getId();
    }

	@Override
	public List<ShippingDtl> getAllShippingDtlsByShippingID(Integer shippingId) {
		return dtlRepo.getAllShippingDtlsByShippingID(shippingId);
	}

	@Override
	public Shipping getOneShipping(Integer id) {
		return repo.findById(id).get();
	}

	@Transactional
	@Override
	public Integer updateShippingDtlStatus(Integer shippingDtlId, String status) {		
		return dtlRepo.updateShippingDtlStatus(shippingDtlId, status);
	}

	@Override
	public Page<Shipping> getAllShippings(Pageable p) {
		return repo.findAll(p);
	}

}
