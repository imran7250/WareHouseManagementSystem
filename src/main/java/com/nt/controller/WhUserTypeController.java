package com.nt.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.model.OrderMethod;
import com.nt.model.WhUserType;
import com.nt.service.IWhUserTypeService;

@Controller
@RequestMapping("/whUser")
public class WhUserTypeController {
   
	@Autowired
	private IWhUserTypeService service;

	  
	//1.Show Register Page
	 @GetMapping("/register")
	  public String showReg(Model model) {
		 model.addAttribute("whUserType", new WhUserType());
		  return "WhUserTypeRegister"; // KEEP THE ORIGINAL NAME
	  }      
		
	//2.On Click save - ADDED EMAIL VALIDATION
	 @PostMapping("/save")
	 public String saveWhUserType(
	     @ModelAttribute WhUserType whUserType,
	     Model model
	 ) {
	     // Check for duplicate email before saving - NEW CODE
	     if(service.isUserEmailExist(whUserType.getUserEmail())) {
	         model.addAttribute("message", "Error: Email '"+whUserType.getUserEmail()+"' is already registered. Please use a different email.");
	         model.addAttribute("whUserType", whUserType); // Keep form data
	         return "WhUserTypeRegister"; // KEEP THE ORIGINAL NAME
	     }
	     
	     //Calling service - EXISTING CODE
	     Integer id = service.saveWhUserType(whUserType);
	     String message = "WhUserType saved with id:" + id;
	     
	     //Sending data to UI - EXISTING CODE
	     model.addAttribute("message", message);
	     
	     //Form Backing object.... - EXISTING CODE
	     model.addAttribute("whUserType", new WhUserType());
	     return "WhUserTypeRegister"; // KEEP THE ORIGINAL NAME
	 }
	 
	//3. AJAX endpoint to check email availability - NEW CODE
	@GetMapping("/checkEmail")
	@ResponseBody
	public Map<String, Boolean> checkEmail(@RequestParam String email) {
	    Map<String, Boolean> response = new HashMap<>();
	    boolean exists = service.isUserEmailExist(email);
	    response.put("exists", exists);
	    return response;
	}
	 
	//4.Show Data - EXISTING CODE
	 @GetMapping("/all")
	 public String getAllWhUserTypes(
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		// List<WhUserType> list=service.getAllWhUserTypes();
		// model.addAttribute("list", list);
		 
		   	Page<WhUserType> page=service.getAllWhUserTypes(p);
        	model.addAttribute("list", page.getContent());
        	model.addAttribute("page", page);
		 
		 return "WhUserTypeData";   
	 }
	 
	 //5.Delete one WhUserType - EXISTING CODE
	 @GetMapping("/delete")
	 public String deleteWhUserType(@RequestParam Integer id,
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		 service.deleteWhUserType(id);
		 model.addAttribute("message", "WhUserType '"+id+"' Deleted");
		// model.addAttribute("list", service.getAllWhUserTypes());
		   	Page<WhUserType> page=service.getAllWhUserTypes(p);
        	model.addAttribute("list", page.getContent());
        	model.addAttribute("page", page);
		 
		 
		 return "WhUserTypeData";   
	 }
	 
	 //6.Show WhUserType Edit - EXISTING CODE
	 @GetMapping("/edit") 
	  public String showEdit(@RequestParam Integer id,Model model) {
		 WhUserType whUserType=service.getOneWhUserType(id);
		 model.addAttribute("whUserType", whUserType);
		 return "WhUserTypeEdit";
	  }
	 
	 //7. update WhUserType - ADDED EMAIL VALIDATION
	 @PostMapping("/update")
	 public String updateWhUserType(
	     @ModelAttribute WhUserType whUserType,
	     @PageableDefault(page=0,size=3)Pageable p,
	     Model model
	 ) {
	     // Check for duplicate email (excluding current user's email) - NEW CODE
	     WhUserType existingUser = service.getOneWhUserType(whUserType.getId());
	     if(!existingUser.getUserEmail().equals(whUserType.getUserEmail()) && 
	        service.isUserEmailExist(whUserType.getUserEmail())) {
	         model.addAttribute("message", "Error: Email '"+whUserType.getUserEmail()+"' is already registered. Please use a different email.");
	        // model.addAttribute("whUserType", whUserType); // Keep form data
	         
	         
	         return "WhUserTypeEdit";
	     }
	     
	     // EXISTING CODE
		 service.updateWhUserType(whUserType);
		 model.addAttribute("message", "WhUserType '"+whUserType.getId()+"' Updated");
		// model.addAttribute("list", service.getAllWhUserTypes());
		 Page<WhUserType> page=service.getAllWhUserTypes(p);
		 model.addAttribute("list", page.getContent());
		 model.addAttribute("page", page);
		 return "WhUserTypeData";
	 }
}