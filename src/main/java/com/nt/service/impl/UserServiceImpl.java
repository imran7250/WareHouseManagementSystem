package com.nt.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.nt.config.AppConfig;
import com.nt.model.User;
import com.nt.repo.UserRepository;
import com.nt.service.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

    private final AppConfig appConfig;

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

    UserServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }//HAS-A 
	
	@Override
	public Integer saveUser(User user) {	
		//read password
		String pwd=user.getPwd();
		//encode password
		String encPwd=encoder.encode(pwd);
		//send back to same user Object
		user.setPwd(encPwd);
		return repo.save(user).getId();
	}

	@Override
	public List<User> getAllUsers() {		
		return repo.findAll();
	}

	@Transactional
	public void modifyStatus(Integer id, boolean active) {
		 repo.updateStatus(id, active);		
	}


	@Override
	public Optional<User> findByEmail(String email) {
		
		return repo.findByEmail(email);
	}
	//-----------------------------------------------------------------------------------------------//

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//read model class object
		Optional<User> opt =findByEmail(username);
		if(opt.isEmpty() ) {
			throw new UsernameNotFoundException("User not exist");
		}else {
			User user=opt.get();
			return new org.springframework.security.core.userdetails.User(
					username,
					user.getPwd(),
					user.getRoles()
					.stream()
					.map(role-> new SimpleGrantedAuthority(role))
					.collect(Collectors.toSet())
					);
		}

	}

	@Override
	public User getOneUser(Integer id) {
		return repo.findById(id).get();
	}

	@Override
	@Transactional
	public void updatePwd(Integer id, String pwd) {
		repo.updatePwd(id, encoder.encode(pwd));		
	}


}
