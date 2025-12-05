package com.nt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.Grn;
import com.nt.model.GrnDtl;

public interface IGrnService {
  public Integer saveGrn(Grn grn);
  public Grn getOneGrn(Integer id);
  public List<Grn> getAllGrns();
  public Integer saveGrnDtl(GrnDtl grnDtl);
  
  // Screen#2
  public List<GrnDtl> getAllGrnDtlsByGrnID(Integer grnId);
  public Integer updateGrnDtlStatus(Integer grnDtlId,String status);
  
  public Page<Grn> getAllGrns(Pageable p);
}
