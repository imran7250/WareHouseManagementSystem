package com.nt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.Part;

public interface IPartService {
 
	public Integer savePart(Part part);
	public List<Part> getAllParts();
	public void updatePart(Part part);
	public void deletePart(Integer id);
	public Part getOnePart(Integer id);
	
	 public Map<Integer,String>getPartIdAndCode();
	 
	 public Page<Part> getAllParts(Pageable p);
	 
}
