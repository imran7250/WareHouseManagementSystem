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

import com.nt.model.Uom;
import com.nt.service.IUomService;
import com.nt.util.UomUtil;
import com.nt.view.UomExcelView;
import com.nt.view.UomPdfView;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/uom")
public class UomController {
   
	@Autowired
	private IUomService service;
	
	@Autowired
	private UomUtil util;
	
	@Autowired
	private ServletContext sc;
	  
	//1.Show Register Page
	 @GetMapping("/register")
	  public String showReg(Model model) {
		 model.addAttribute("uom", new Uom());
		  return "UomRegister";
	  }      
		
	//2.On Click  save
	 @PostMapping("/save")
	private String savaUom(
			        @ModelAttribute Uom uom,//Read form data as Object
			        Model model)            //Send Data to UI
	{
		//Calling service
		Integer id=service.saveUom(uom);
		String message="Uom saved with id:"+id;
		//Sending data to UI
		model.addAttribute("message",message);
		
		//Form Backing object....
		model.addAttribute("uom", new Uom());
		return "UomRegister";
	}
	 

	//3.Show Data
	 @GetMapping("/all")
	 public String getAllUoms(
			 @PageableDefault(page=0,size=3)Pageable p,
			 @RequestParam(value="uomModel",required=false,defaultValue="")String uomModel,
			 Model model) {
		 
		   Page<Uom> page=null;
		   if(" ".equals(uomModel)) {//if no search input then fetch all
			   page=service.getAllUoms(p);
		   }else {
			   //specific search input exists then get matching data
			   page=service.findByUomModelContaining(uomModel, p);
		   }
		 
		 /* --OLD CODE--
		 List<Uom> list=service.getAllUoms();
		 model.addAttribute("list", list);
		 */
		// Page<Uom> page=service.getAllUoms(p);
		 //return List<T> data available in the current page
		 model.addAttribute("list", page.getContent());
		 //pagination information
		 model.addAttribute("page", page);
		 return "UomData";   
	 }
	 
	 //4.Delete one uom
	 @GetMapping("/delete")
	 public String deleteUom(@RequestParam Integer id,
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		 service.deleteUom(id);
		 model.addAttribute("message", "Uom '"+id+"' Deleted");
		// model.addAttribute("list", service.getAllUoms());
		 Page<Uom> page=service.getAllUoms(p);
		 //return List<T> data available in the current page
		 model.addAttribute("list", page.getContent());
		 //pagination information
		 model.addAttribute("page", page);
		 return "UomData";   
	 }
	 //5.Show Uom Edit
	 @GetMapping("/edit") 
	  public String showEdit(@RequestParam Integer id,Model model) {
		 Uom uom=service.getOneUom(id);
		 model.addAttribute("uom",uom);
		 return "UomEdit";
	  }
	 //6. update uom
	 @PostMapping("/update")
	 public String updateUom(@ModelAttribute Uom uom,
			 @PageableDefault(page=0,size=3)Pageable p,
			 Model model) {
		  service.updateUom(uom);
			 model.addAttribute("message", "Uom '"+uom.getId()+"' Updated");
			// model.addAttribute("list", service.getAllUoms());
			 Page<Uom> page=service.getAllUoms(p);
			 //return List<T> data available in the current page
			 model.addAttribute("list", page.getContent());
			 //pagination information
			 model.addAttribute("page", page);
			 return "UomData";
	 }
	 
	 //7.Excel Export
	 @GetMapping("/excel")
	 public ModelAndView showExcel() {
		 ModelAndView m=new ModelAndView();
		 m.setView(new UomExcelView());
		 //fetch Data from db
		 List<Uom> list=service.getAllUoms();
		 //send data to view class
		 m.addObject("list", list);
		 return m;
	 }
	 
	 
	 //8. PDP export
	 @GetMapping("/pdf")
	 public ModelAndView exportToPdf() {
		 ModelAndView m=new ModelAndView();
		 m.setView(new UomPdfView());
		 //fetch data from DB
		 List<Uom>list=service.getAllUoms();
		 //send data to view class
		 m.addObject("list", list);
		 return m;
	 }
	  
	 //9. AJAX VALIDATION
	 @GetMapping("/validate")
	 public  @ResponseBody String validateModel(@RequestParam String model) {	 
		 String message="";
		 if(service.isUomModelExist(model)) {
			 message="Uom model '"+model+"'already Exists";
		 }
		 return message; 
	 }
	 
	 //10. show charts
	 @GetMapping("/charts")
	 public String showCharts() {
		 List<Object[]> data=service.getUomTypeAndCount();
		    String path = sc.getRealPath("/images");
		 util.generatePie(path, data);
		 util.generateBar(path, data);   
		 return "UomCharts";
	 }
	 
//	    // ================= ERROR HANDLER INSIDE CONTROLLER =================
//	    @ExceptionHandler(UomNotFoundException.class)
//	    public String handleUomNotFound(UomNotFoundException ex, Model model) {
//	        model.addAttribute("status", 500);
//	        model.addAttribute("message", ex.getMessage());
//	        return "ErrorPage";  // ErrorPage.html
//	    }
//
//	    @ExceptionHandler(Exception.class)
//	    public String handleOtherErrors(Exception ex, Model model) {
//	        model.addAttribute("status", 500);
//	        model.addAttribute("message", "Unexpected error: " + ex.getMessage());
//	        return "ErrorPage";
//	    }
	 
	 
}
