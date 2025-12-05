package com.nt.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.Uom;

public interface UomRepository extends JpaRepository<Uom,Integer> {
	
	
	public Page<Uom> findByUomModelContaining(String uomModel,Pageable pageable);
    
	@Query("SELECT COUNT(uomModel) FROM Uom WHERE uomModel=:uomModel")
	Integer getUomModelCount(String uomModel);
	
	@Query("SELECT uomType, COUNT(uomType) FROM Uom GROUP BY uomType")
	List<Object[]> getUomTypeAndCount();
	 
	@Query("SELECT id,uomModel FROM Uom")
	List<Object[]> getUomAndIdModel();   
}
   