package com.nt.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.model.Uom;
import com.nt.model.User;
import com.nt.repo.UomRepository;
import com.nt.service.IUomService;
import com.nt.service.exception.UomNotFoundException;
import com.nt.util.MyCollectionUtil;

@Service
public class UomServiceImpl  implements IUomService{

	@Autowired
	private UomRepository repo;
	
	@Override
	public Integer saveUom(Uom uom) {		  
		return repo.save(uom).getId();
	}

	@Override
	public List<Uom> getAllUoms() {		
		return repo.findAll();
	}

	@Override
	public void updateUom(Uom uom) {		
		repo.save(uom);
	}

	@Override
	public void deleteUom(Integer id) {
		Uom uom=getOneUom(id);
		repo.delete(uom);
		
		
	}

	@Override
	public Uom getOneUom(Integer id) {
		Uom uom=repo.findById(id).
				orElseThrow(()-> new UomNotFoundException("Uom'"+id+"'Not exists"));
		return uom;
	}

	@Override
	public boolean isUomModelExist(String uomModel) {
		boolean flag=true;
	  /*
		Integer count= repo.getUomModelCount(uomModel);
	  if(count==0) {
		  flag= false;// not exist
	  }else {
		  flag=true;          
	  }
		return flag;
		*/
		
		return repo.getUomModelCount(uomModel) > 0 ? true : false;
	}

	@Override
	public List<Object[]> getUomTypeAndCount() {
	
		return repo.getUomTypeAndCount();
	}

	@Override
	public Map<Integer, String> getUomIdAndModel() {
		List<Object[]> list=repo.getUomAndIdModel();	
		Map<Integer,String> map=MyCollectionUtil.convertListToMap(list);
		return map;
	}

	@Override
	public Page<Uom> getAllUoms(Pageable p) {
		return repo.findAll(p);
	}

	@Override
	public Page<Uom> findByUomModelContaining(String uomModel, Pageable pageable) {
	return repo.findByUomModelContaining(uomModel, pageable);
	}
	
	
	




}
