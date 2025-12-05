package com.nt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nt.model.Shipping;
import com.nt.model.ShippingDtl;

public interface IShippingService {
  public Integer saveShipping(Shipping shipping);
  public Shipping getOneShipping(Integer id);
  public List<Shipping> getAllShippings();
  public Integer saveShippingDtl(ShippingDtl shippingDtl);
  
  // Screen#2
  public List<ShippingDtl> getAllShippingDtlsByShippingID(Integer shippingId);
  public Integer updateShippingDtlStatus(Integer shippingDtlId,String status);
  
  public Page<Shipping> getAllShippings(Pageable p);
}
