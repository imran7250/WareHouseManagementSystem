package com.nt.setup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nt.model.User;
import com.nt.service.IUserService;

@Component
public class SetupAdmin implements CommandLineRunner {

    @Autowired   
    private IUserService service;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setName("NareshIT");
        user.setEmail("nit@gmail.com");
        user.setPwd(passwordEncoder.encode("nit")); // Encode the password
        user.setActive(true);
        user.setRoles(List.of("ADMIN", "APPUSER"));
        
        if(service.findByEmail(user.getEmail()).isEmpty()) {            
            service.saveUser(user);
            System.out.println("Admin is created successfully");
        } else {
            System.out.println("Admin user already exists!"); // Do NOT re-encode password
        }
    }
}      