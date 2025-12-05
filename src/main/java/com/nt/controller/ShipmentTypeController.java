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

import com.nt.model.ShipmentType;
import com.nt.service.IShipmentTypeService;
import com.nt.util.ShipmentTypeUtil;
import com.nt.view.ShipmentTypeExcelView;
import com.nt.view.ShipmentTypePdfView;

import jakarta.servlet.ServletContext;  

@Controller	
@RequestMapping("/st")
public class ShipmentTypeController {

	@Autowired	
	private IShipmentTypeService service;
	
	@Autowired                             
	private ShipmentTypeUtil util;    
	
	@Autowired
	private ServletContext context;
	
	//  1. Show Register Page
	
	        @GetMapping("/register")
	        public String showReg(Model model) {
	        	//Form backing object or Form linking object.....
	        	model.addAttribute("shipmentType",new ShipmentType());
	        	return "ShipmentTypeRegister";
	        }           
	// 2. save
	        @PostMapping("/save")
	        public String save(@ModelAttribute ShipmentType shipmentType,Model model) {
	        	Integer id=service.saveShipmentType(shipmentType);
	        	String message="ShipmentType '"+id+"'Saved";
	        	model.addAttribute("message",message);
	        	//Form backing object or Form linking object.....
	        	model.addAttribute("shipmentType",new ShipmentType());
	        	return "ShipmentTypeRegister";   
	        }
	                                  
	// 3. Display all
	        @GetMapping("/all")                                                  
	        public String showAll(
	        		@PageableDefault(page=0,size=3)Pageable p,
	        		Model model) {
	        	//model.addAttribute("list",service.getAllShipmentTypes());
	        	Page<ShipmentType> page=service.getAllShipmentTypes(p);
	        	model.addAttribute("list", page.getContent());
	        	model.addAttribute("page", page);
	        	return "ShipmentTypeData";
	        }
	
	// 4. Delete ById
	        @GetMapping("/delete")
	        public String deleteOne(@RequestParam Integer id,
	        		@PageableDefault(page=0,size=3)Pageable p,
	        		Model model) {
	        	service.deleteShipmentType(id);
	        	model.addAttribute("message", "ShipmentType'"+id+"'deleted");
	        	//model.addAttribute("list",service.getAllShipmentTypes());
	        	
	        	Page<ShipmentType> page=service.getAllShipmentTypes(p);
	        	model.addAttribute("list", page.getContent());
	        	model.addAttribute("page", page);
	        	
	        	return "ShipmentTypeData";
	        }
	
	// 5. Show Edit
	        @GetMapping("/edit")
	        public String showEdit(@RequestParam Integer id,Model model) {
	        	model.addAttribute("shipmentType", service.getOneShipmentType(id));
	        	return "ShipmentTypeEdit";
	        }
	
	// 6.Update
	        @PostMapping("/update")
	        public String doUpdate(@ModelAttribute ShipmentType shipmentType,
	        		@PageableDefault(page=0,size=3)Pageable p,
	        		Model model) {
	        	 service.updateShipmentType(shipmentType);
	        	 model.addAttribute("message","ShipmentType"+shipmentType.getId()+"Updated");
	        	 
	        	// model.addAttribute("list", service.getAllShipmentTypes());
	        	
	        	 Page<ShipmentType> page=service.getAllShipmentTypes(p);
		         model.addAttribute("list", page.getContent());
		         model.addAttribute("page", page);
	        	 
	        	return "ShipmentTypeData";
	        }

	      //7.Excel Export
	        @GetMapping("/excel")
	        public ModelAndView excelToExport() {
	            ModelAndView m = new ModelAndView();
	            
	            //Service call
	            List<ShipmentType> list = service.getAllShipmentTypes();
	            
	            if(list == null || list.isEmpty()) {
	                // Set view to your data page (not Excel)
	                m.setViewName("ShipmentTypeData");
	                m.addObject("message", "Shipment data is not available for Excel export");
	                m.addObject("list", list); // Add empty list to maintain page structure
	            } else {
	                // Set Excel view and add data
	                m.setView(new ShipmentTypeExcelView());
	                m.addObject("list", list);
	            }
	            
	            return m;
	        }
	        
	        //8.PDF Export
	        @GetMapping("/pdf")
	        public ModelAndView excelToPdf() {
	            ModelAndView m = new ModelAndView();
	            
	            //Service call
	            List<ShipmentType> list = service.getAllShipmentTypes();
	            
	            if(list == null || list.isEmpty()) {
	                // Set view to your data page (not Excel)
	                m.setViewName("ShipmentTypeData");
	                m.addObject("message", "Shipment data is not available for Pdf export");
	                m.addObject("list", list); // Add empty list to maintain page structure
	            } else {
	                // Set Excel view and add data
	                m.setView(new ShipmentTypePdfView());
	                m.addObject("list", list);
	            }
	            
	            return m;
	        }
	        
	        // 9. Validate Code using AJAX
	       @GetMapping("/validate")
	        public  @ResponseBody String validateCode(@RequestParam String code) {
	    	   String message=" ";
	    	   if(service.isShipmentTypeCodeExist(code)) {
	    		   
	    		    message="ShipmentCode is'"+code+"' already Exist";
	    	   }
	        	return message;
	        }

	       //10. Charts
	       @GetMapping("/charts")
	       public String generateCharts() {
	    	 List<Object[]> data=  service.getShipmentTypeModeAndCount();
	    	 String path=context.getRealPath("/");
	    	 util.generateBar(path, data);
	    	 util.generatPie(path, data);
	    	   return "ShipmentTypeCharts";
	       }
	       
}
