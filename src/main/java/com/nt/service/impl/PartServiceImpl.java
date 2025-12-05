package com.nt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.model.Part;
import com.nt.repo.PartRepository;
import com.nt.service.IPartService;
import com.nt.service.exception.PartNotFoundException;
import com.nt.util.MyCollectionUtil;

@Service
public class PartServiceImpl implements IPartService {


	@Autowired
	private PartRepository repo;
	
	@Override
	public Integer savePart(Part part) {		  
		return repo.save(part).getId();
	}

	@Override
	public List<Part> getAllParts() {		
		return repo.findAll();
	}

	@Override
	public void updatePart(Part part) {		
		repo.save(part);
	}

	@Override
	public void deletePart(Integer id) {
		Part Part=getOnePart(id);
		repo.delete(Part);
		      
		
	}

	@Override
	public Part getOnePart(Integer id) {
		Part Part=repo.findById(id).
				orElseThrow(()-> new PartNotFoundException("Part'"+id+"'Not exists"));
		return Part;
	}

	@Override
	public Map<Integer, String> getPartIdAndCode() {
		List<Object[]>list=repo.getPartIdAndCode();
		return MyCollectionUtil.convertListToMap(list);
		
	}

	@Override
	public Page<Part> getAllParts(Pageable p) {
		return repo.findAll(p);
	}     




}
