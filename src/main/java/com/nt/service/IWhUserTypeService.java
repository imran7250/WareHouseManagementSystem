package com.nt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.WhUserType;

public interface IWhUserTypeService {
	public Integer saveWhUserType(WhUserType whUserType);
	public List<WhUserType> getAllWhUserTypes();
	
	public void updateWhUserType(WhUserType whUserType);
	public void deleteWhUserType(Integer id);
	public WhUserType getOneWhUserType(Integer id);
	
    // Add this method for email duplicate check
    boolean isUserEmailExist(String email);
    
    public Map<Integer,String> getWhUserTypeByUserType(String userType);
    
    public Page<WhUserType> getAllWhUserTypes(Pageable p);
}
