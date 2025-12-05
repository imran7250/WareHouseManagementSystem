package com.nt.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.nt.model.OrderMethod;
import com.nt.model.ShipmentType;
import com.nt.service.IOrderMethodService;
import com.nt.util.OrderMethodUtil;
// Add these imports for your view classes


import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/OrderMethod") 
public class OrderMethodController {
   
	@Autowired 
	private IOrderMethodService service;
	
	@Autowired
	private OrderMethodUtil util;
	
	@Autowired
	private ServletContext sc;
	  
	//1.Show Register Page
	 @GetMapping("/register")
	  public String showReg(Model model) {
		 model.addAttribute("OrderMethod", new OrderMethod());
		  return "OrderMethodRegister";
	  }      
		
	 @PostMapping("/save")
	 public String saveOrderMethod(
	         @ModelAttribute OrderMethod orderMethod, // Fixed parameter name (was OrderMethod)
	         Model model) {
	     //Calling service
	     Integer id = service.saveOrderMethod(orderMethod);
	     String message = "OrderMethod saved with id:" + id;
	     
	     //Sending data to UI
	     model.addAttribute("message", message);
	     
	     //Form Backing object
	     model.addAttribute("OrderMethod", new OrderMethod());
	     return "OrderMethodRegister";
	 }

	//3.Show Data
	 @GetMapping("/all")
	 public String getAllOrderMethods(
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		// List<OrderMethod> list=service.getAllOrderMethods();
		// model.addAttribute("list", list);
		   	Page<OrderMethod> page=service.getAllOrderMethods(p);
        	model.addAttribute("list", page.getContent());
        	model.addAttribute("page", page);
		 return "OrderMethodData";   
	 }
	 
	 //4.Delete one OrderMethod
	 @GetMapping("/delete")
	 public String deleteOrderMethod(@RequestParam Integer id,
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		 service.deleteOrderMethod(id);
		 model.addAttribute("message", "OrderMethod '"+id+"' Deleted");
		// model.addAttribute("list", service.getAllOrderMethods());
		   	Page<OrderMethod> page=service.getAllOrderMethods(p);
        	model.addAttribute("list", page.getContent());
        	model.addAttribute("page", page);
		 return "OrderMethodData";   
	 }
	 
	 //5.Show OrderMethod Edit
	 @GetMapping("/edit") 
	  public String showEdit(@RequestParam Integer id,Model model) {
		 OrderMethod orderMethod=service.getOneOrderMethod(id);
		 model.addAttribute("OrderMethod", orderMethod);
		 return "OrderMethodEdit";
	  }
	 
	 //6. update OrderMethod
	 @PostMapping("/update")
	 public String updateOrderMethod(@ModelAttribute OrderMethod orderMethod,
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		  service.updateOrderMethod(orderMethod);
			 model.addAttribute("message", "OrderMethod '"+orderMethod.getId()+"' Updated");
			// model.addAttribute("list", service.getAllOrderMethods());
			 
			   	Page<OrderMethod> page=service.getAllOrderMethods(p);
	        	model.addAttribute("list", page.getContent());
	        	model.addAttribute("page", page);
		  return "OrderMethodData";
	 }
	 
	// 9. AJAX VALIDATION
	 @GetMapping("/validate")
	 public @ResponseBody String validateModel(@RequestParam String orderCode) { // Changed from OrderMethod to orderCode
	     String message = "";
	     if(service.isOrderMethodModelExist(orderCode)) {
	         message = "Order Code '" + orderCode + "' already exists";
	     }
	     return message; 
	 } 
	 /*
	 
	 //7.Excel Export
	 @GetMapping("/excel")
	 public ModelAndView showExcel() {
		 ModelAndView m=new ModelAndView();
		 m.setView(new OrderMethodExcelView());
		 //fetch Data from db
		 List<OrderMethod> list=service.getAllOrderMethods();
		 //send data to view class
		 m.addObject("list", list);
		 return m;
	 }
	 
	 
	 //8. PDF export
	 @GetMapping("/pdf")
	 public ModelAndView exportToPdf() {
		 ModelAndView m=new ModelAndView();
		 m.setView(new OrderMethodPdfView());
		 //fetch data from DB
		 List<OrderMethod> list=service.getAllOrderMethods();
		 //send data to view class
		 m.addObject("list", list);
		 return m;
	 }
	  
	 
	 //10. show charts
	 @GetMapping("/charts")
	 public String showCharts() {
		 List<Object[]> data=service.getOrderMethodTypeAndCount();
		    String path = sc.getRealPath("/images");
		 util.generatePie(path, data);
		 util.generateBar(path, data);   
		 return "OrderMethodCharts";
	 }
	 */
	 
//	 // ================= ERROR HANDLER INSIDE CONTROLLER =================
//	 // Commented out as OrderMethodNotFoundException class doesn't exist
//	 /*
//	 @ExceptionHandler(OrderMethodNotFoundException.class)
//	 public String handleOrderMethodNotFound(OrderMethodNotFoundException ex, Model model) {
//	     model.addAttribute("status", 500);
//	     model.addAttribute("message", ex.getMessage());
//	     return "ErrorPage";  // ErrorPage.html
//	 }
//
//	 @ExceptionHandler(Exception.class)
//	 public String handleOtherErrors(Exception ex, Model model) {
//	     model.addAttribute("status", 500);
//	     model.addAttribute("message", "Unexpected error: " + ex.getMessage());
//	     return "ErrorPage";
//	 }
//	 */
}