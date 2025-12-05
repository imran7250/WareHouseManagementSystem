package com.nt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import com.nt.model.ShipmentType;
import com.nt.repo.ShipmentTypeRepository;
import com.nt.service.IShipmentTypeService;
import com.nt.service.exception.ShipmentTypeNotFoundException;
import com.nt.util.MyCollectionUtil;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {
 
	 @Autowired
	 private ShipmentTypeRepository repo;
	
	@Override
	public Integer saveShipmentType(ShipmentType st) {	    
		return repo.save(st).getId();	
	}

	@Override
	public void updateShipmentType(ShipmentType st) {
		repo.save(st);
	}

	@Override
	public void deleteShipmentType(Integer id) {
	   ShipmentType st=repo.findById(id)
			             .orElseThrow(()->
	                      new ShipmentTypeNotFoundException("ShipmentType" +id+" Not Exist"));
	   repo.delete(st);
	}

	@Override
	public ShipmentType getOneShipmentType(Integer id) {
		   ShipmentType st=repo.findById(id)
		             .orElseThrow(()->
                    new ShipmentTypeNotFoundException("ShipmentType" +id+" Not Exist"));
		return st;
	} 

	@Override
	public List<ShipmentType> getAllShipmentTypes() {		
		return repo.findAll();
	}

	@Override
	public boolean isShipmentTypeCodeExist(String code) {
		return repo.getShipmentTypeCodeCount(code) >  0 ? true:false;
	}

	@Override
	public List<Object[]> getShipmentTypeModeAndCount() {
		return repo.getShipmentTypeModeAndCount();
	}

	@Override
	public Map<Integer, String> getShipmentIdAndCodeByEnabled(String enable) {
		List<Object[]> list=repo.getShipmentIdAndCodeByEnabled(enable);
		Map<Integer,String>map=MyCollectionUtil.convertListToMap(list);
		return map;
	}

	@Override
	public Page<ShipmentType> getAllShipmentTypes(Pageable p) {
		return repo.findAll(p);
	}

}
