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

import com.nt.model.Part;
import com.nt.model.WhUserType;
import com.nt.service.IOrderMethodService;
import com.nt.service.IPartService;
import com.nt.service.IUomService;

@Controller
@RequestMapping("/part")
public class PartController {
    @Autowired
    private IPartService service;
    
    @Autowired
    private IUomService uomService;
    
    @Autowired
    private IOrderMethodService orderMethodService;
        
    private void addDynamicUiComponents(Model model) {
        model.addAttribute("uoms", uomService.getUomIdAndModel());
        model.addAttribute("sales", orderMethodService.getOrderMethodIdAndCodeByMode("Sale"));
    }

    //1.Show Register Page
    @GetMapping("/register")
    public String showReg(Model model) {
        model.addAttribute("part", new Part());
        addDynamicUiComponents(model);
        return "PartRegister";
    }        
    
    //2.On Click save
    @PostMapping("/save")
    public String savePart(
            @ModelAttribute Part part,//Read data as object
            Model model) { //send data to UI
        Integer id=service.savePart(part);
        String message="Part saved with id:"+id;
        model.addAttribute("message",message);
        model.addAttribute("part", new Part());
        addDynamicUiComponents(model);
        return "PartRegister";
    }
    
    //3.Show Data or Display Parts
    @GetMapping("/all")
    public String getAllParts(
    		@PageableDefault(page=0,size=3)Pageable p,
    		Model model) {
    	/*
        List<Part> list=service.getAllParts();
        model.addAttribute("list", list);
                   */
	   	Page<Part> page=service.getAllParts(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
        
        return "PartData";   
    }
    
    //4.Delete one Part
    @GetMapping("/delete")
    public String deletePart(@RequestParam Integer id,
    		@PageableDefault(page=0,size=3)Pageable p,
    		Model model) {
       // service.deletePart(id);
        model.addAttribute("message", "Part '"+id+"' Deleted");
       // model.addAttribute("list", service.getAllParts());
        
	   	Page<Part> page=service.getAllParts(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
        
        return "PartData";   
    }
    
    //5.Show Part Edit - FIXED
    @GetMapping("/edit") 
    public String showEdit(@RequestParam Integer id, Model model) {
        Part part=service.getOnePart(id);
        model.addAttribute("part", part); // 
        addDynamicUiComponents(model);    // Added missing call
        return "PartEdit";
    }
    
    //6. update Part
    @PostMapping("/update")
    public String updatePart(@ModelAttribute Part part,
    		@PageableDefault(page=0,size=3)Pageable p,
    		Model model) {
      //  service.updatePart(part);
        model.addAttribute("message", "Part '"+part.getId()+"' Updated");
      //  model.addAttribute("list", service.getAllParts());
        
	   	Page<Part> page=service.getAllParts(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
        
        return "PartData";
    }
}