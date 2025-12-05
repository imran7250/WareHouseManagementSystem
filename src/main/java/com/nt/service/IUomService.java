package com.nt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.Uom;

public interface IUomService {
 
	public Integer saveUom(Uom uom);
	public List<Uom> getAllUoms();
	public void updateUom(Uom uom);
	public void deleteUom(Integer id);
	public Uom getOneUom(Integer id);
	
	public boolean isUomModelExist(String uomModel);
	
	public List<Object[]> getUomTypeAndCount();
 
	public Map<Integer,String> getUomIdAndModel();
	
	public Page<Uom> getAllUoms(Pageable p);
	
	public Page<Uom> findByUomModelContaining(String uomModel,Pageable pageable);
}
   