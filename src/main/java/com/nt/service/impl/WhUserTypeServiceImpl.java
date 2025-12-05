package com.nt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.model.WhUserType;
import com.nt.repo.WhUserTypeRepository;
import com.nt.service.IWhUserTypeService;
import com.nt.service.exception.WhUserTypeNotFoundException;
import com.nt.util.MyCollectionUtil;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {

    @Autowired
    private WhUserTypeRepository repo;
    
    @Override
    public Integer saveWhUserType(WhUserType whUserType) {		  
        return repo.save(whUserType).getId();
    }

    @Override
    public List<WhUserType> getAllWhUserTypes() {		
        return repo.findAll();
    }

    @Override
    public void updateWhUserType(WhUserType whUserType) {		
        repo.save(whUserType);
    }

    @Override
    public void deleteWhUserType(Integer id) {
        WhUserType whUserType = getOneWhUserType(id);
        repo.delete(whUserType);
    }

    @Override
    public WhUserType getOneWhUserType(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new WhUserTypeNotFoundException("Warehouse UserType '" + id + "' Not exists"));
    }

	@Override
	public boolean isUserEmailExist(String email) {
	     // New method to check email existence
        return repo.getCountByUserEmail(email) > 0;
	}

	@Override
	public Map<Integer, String> getWhUserTypeByUserType(String userType) {
	List<Object[]> list=repo.getWhUserTypeByUserType(userType);
		return MyCollectionUtil.convertListToMap(list);
	}

	@Override
	public Page<WhUserType> getAllWhUserTypes(Pageable p) {
	return repo.findAll(p);
	}

 
}
