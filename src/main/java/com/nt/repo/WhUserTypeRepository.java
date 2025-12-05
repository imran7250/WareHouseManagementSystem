package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nt.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {

    // Add this method for email count
    @Query("SELECT COUNT(w) FROM WhUserType w WHERE w.userEmail = :email")
    Integer getCountByUserEmail(@Param("email") String email);
    
    @Query("SELECT w.id, w.userCode FROM WhUserType w WHERE w.userType = :userType")
    List<Object[]> getWhUserTypeByUserType(@Param("userType") String userType);

}
