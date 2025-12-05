package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nt.model.GrnDtl;

@Repository
public interface GrnDtlRepository extends JpaRepository<GrnDtl, Integer> {

    @Query("SELECT dtl FROM GrnDtl dtl JOIN dtl.grn as grn WHERE grn.id=:grnId")
    public List<GrnDtl> getAllGrnDtlsByGrnID(@Param("grnId") Integer grnId);
    
    @Modifying
    @Query("UPDATE GrnDtl SET status=:grnDtlStatus WHERE id=:grnDtlId")
    public Integer updateGrnDtlStatus(
        @Param("grnDtlId") Integer grnDtlId, 
        @Param("grnDtlStatus") String status
    );
}