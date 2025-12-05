package com.nt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nt.model.Grn;
import com.nt.model.GrnDtl;
import com.nt.repo.GrnDtlRepository;
import com.nt.repo.GrnRepository;
import com.nt.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {

    @Autowired
    private GrnRepository repo;
    
    @Autowired
    private GrnDtlRepository dtlRepo;
    
    @Override
    @Transactional
    public Integer saveGrn(Grn grn) {        
        return repo.save(grn).getId();
    }

    @Override
    public List<Grn> getAllGrns() {        
        return repo.findAll();
    }

    @Override
    @Transactional
    public Integer saveGrnDtl(GrnDtl grnDtl) {
        return dtlRepo.save(grnDtl).getId();
    }

	@Override
	public List<GrnDtl> getAllGrnDtlsByGrnID(Integer grnId) {
		return dtlRepo.getAllGrnDtlsByGrnID(grnId);
	}

	@Override
	public Grn getOneGrn(Integer id) {
		return repo.findById(id).get();
	}

	@Transactional
	@Override
	public Integer updateGrnDtlStatus(Integer grnDtlId, String status) {		
		return dtlRepo.updateGrnDtlStatus(grnDtlId, status);
	}

	@Override
	public Page<Grn> getAllGrns(Pageable p) {
		return repo.findAll(p);
	}


}