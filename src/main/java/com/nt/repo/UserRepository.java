package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.User;



public interface UserRepository extends JpaRepository<User, Integer> {

	
	@Modifying 
	@Query("UPDATE  com.nt.model.User SET active=:active WHERE id=:id")
	public void updateStatus(Integer id,boolean active);
	
	@Modifying 
	@Query("UPDATE  com.nt.model.User SET pwd=:encPwd WHERE id=:id")
	public void updatePwd(Integer id,String encPwd);
	
	Optional<User> findByEmail(String email);
	
}
