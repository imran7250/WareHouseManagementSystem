package com.nt.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.model.User;
import com.nt.service.IUserService;
import com.nt.util.EmailUtil;
import com.nt.util.UserUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	

    @Autowired
    private IUserService service;
    
    @Autowired
    private EmailUtil emailUtil;
    
    @Autowired
    private UserUtil userUtil;
  
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);  // Static logger for class
    
    // User login page
    @GetMapping("/showLogin")
    public String showLogin() {
        return "UserLogin";
    }
    
    // Show user register page
    @GetMapping("/register")
    public String showReg() {
        return "UserRegister";
    }
    
    // Add user
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
    	String pwd=user.getPwd();
        Integer id = service.saveUser(user);
        model.addAttribute("message", "User '" + user.getName() + ", (" + id + ")' Saved");
        if(id !=null ) {
        String text=
        		"Hello User ,Your UserName"+user.getEmail()+
        		",Password="+pwd+",Roles:"+user.getRoles()+
        		",Status:"+(user.isActive()?"ACTIVE":"IN ACTIVE");
        	emailUtil.sendEmail(user.getEmail(), "WELCOME TO USER !!", text);
        }
        
        return "UserRegister";
    }
     
    // View all users
    @GetMapping("/all")
    public String viewAll(Model model,HttpSession ses) {
        List<User> list = service.getAllUsers();
        
        // Get logged-in user from session
        User loggedUser = (User) ses.getAttribute("userOb");
        
        // Set other users as INACTIVE temporarily
        for (User u : list) {
            if (!u.getId().equals(loggedUser.getId())) {
                u.setActive(false); // TEMPORARY for display only
            }
        }
        
        model.addAttribute("list", list);
        return "UserData";
    }
    
    // Activate user
    @GetMapping("/activate")
    public String activateUser(@RequestParam Integer uid) {
        service.modifyStatus(uid, true);
        User user= service.getOneUser(uid);
        String text=
        		"Hello User (,Your UserName:" + user.getName()+
        		"),Your modifyied Status:ACTIVE";
        emailUtil.sendEmail(user.getEmail(), "Status Update Message", text);        		
        return "redirect:all";
    }
    
    // Inactivate user
    @GetMapping("/inactivate")
    public String inActivateUser(@RequestParam Integer uid) {
        service.modifyStatus(uid, false);
        User user= service.getOneUser(uid);
        String text=
        		"Hello User (,Your UserName:" + user.getName()+
        		"),Your modifyied Status:INACTIVE";
        emailUtil.sendEmail(user.getEmail(), "Status Update Message", text);        		
        
        return "redirect:all";
    }
    
     
    @GetMapping("/afterLogin")
    public String loginSetup(Principal p, HttpSession ses) {
        Logger logger = LoggerFactory.getLogger(getClass());
        Optional<User> opt = service.findByEmail(p.getName());
        if (opt.isPresent()) {
            ses.setAttribute("userOb", opt.get());
            logger.info("Session set for user: " + opt.get().getName());
        } else {
            logger.warn("User not found for email: " + p.getName());
        }
        return "redirect:../uom/all";  
    }

    
  
    
    // NEW: Session setup endpoint (accessible to all authenticated users)
    @GetMapping("/setupSession")
    public String setupSession(Principal p, HttpSession ses) {
        Optional<User> opt = service.findByEmail(p.getName());
        if (opt.isPresent()) {
            User user = opt.get();
            
            // Keep only current user ACTIVE
            user.setActive(true); // temporary for session    
            ses.setAttribute("userOb", user);
            logger.info("Session set for user: {} with roles: {}", user.getName(), user.getRoles());
            
            // Redirect based on role
            if (user.getRoles().contains("ADMIN")) {
                return "redirect:/user/all";  // Admin dashboard
            } else if (user.getRoles().contains("APPUSER")) {
                return "redirect:/user/all";  // APPUSER dashboard (adjust if needed)
            } else {
                logger.warn("Unknown roles for user: {}", user.getName());
                return "redirect:/user/showLogin?error";
            }
        } else {
            logger.warn("User not found for email: {}", p.getName());
            return "redirect:/user/showLogin?error";
        }
    }
    
    @GetMapping("/profile")
    public String showProfile(HttpSession ses,Model model) {
    	User user=(User)ses.getAttribute("userOb");
    	model.addAttribute("user", user);
    	return "UserProfile";
    }

    @GetMapping("/modifyPwd")
   public String showModifyPwd() {
	   return "UserPwd";
   }
    
    @PostMapping("pwdUpdate")
    public String updatePwd(
    		@RequestParam String pwd1,
    		HttpSession ses
    		) {
    	User user=(User)ses.getAttribute("userOb");
    	service.updatePwd(user.getId(), pwd1);
    	
    	String text="Hello,"+user.getName()
    	+ ",Your Password is modified,New password is:"+pwd1;
    	emailUtil.sendEmail(user.getEmail(), "Password Modified", text);
    	return "redirect:profile";
    }
    
    
    

    @GetMapping("/testEmail")
    @ResponseBody  // Add this to return plain text, not a template
    public String testEmail() {
        boolean sent = emailUtil.sendEmail("imranahmad9942880@gmail.com", "Test Subject", "This is a test body from Spring Boot.");
        if (sent) {
            return "Email sent successfully! Check the recipient's inbox/spam.";
        } else {
            return "Email failed to send. Check application logs for details.";
        }
    }

    @GetMapping("/showForgotPwd")
    public String showForgotPwd() {
    	return "UserForgotPwd";
    }

    @PostMapping("/newPwdGen")
    public String newPwdGen(
    		@RequestParam String uemail,Model model
    		) {    	
    	String message=null;
    	Optional<User> opt=service.findByEmail(uemail);
    	if(opt.isPresent()) {
    		User user=opt.get();
    		//generate new pwd
    		String pwd=userUtil.genPwd();
    		//update to db
    		service.updatePwd(user.getId(), pwd);    		
        	String text="Hello,"+user.getName()
        	+ ",Your Password is Updated,New password is:"+pwd;
        	emailUtil.sendEmail(user.getEmail(), "Password Generated", text);
        	
        	message="New Password is sent to your email";
    	}
    	else {
    		message=uemail+",Not Exist"; 
    	}
    	
    	model.addAttribute("message",message);
    	
    	return "UserForgotPwd";
    }
    
    

    
    
    }

