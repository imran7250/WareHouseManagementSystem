package com.nt.service;

import java.util.List;
import java.util.Optional;

import com.nt.model.User;

public interface IUserService {

	public Integer saveUser(User user);
	public User getOneUser(Integer id);
	public List<User> getAllUsers();
	public void modifyStatus(Integer id,boolean active);
	public void updatePwd(Integer id,String encPwd );
	public Optional<User> findByEmail(String email);
}
