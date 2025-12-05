package com.nt.controller;

import java.util.List;
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

import com.nt.model.Grn;
import com.nt.model.GrnDtl;
import com.nt.model.PurchaseDtl;
import com.nt.model.PurchaseOrder;
import com.nt.service.IGrnService;
import com.nt.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {
 
	@Autowired
	private IGrnService service;
	
	@Autowired
	private IPurchaseOrderService poService;
	
	//Register page dropdown
	private void addDynamicUiComponents(Model model) {
		Map<Integer,String> pos=poService.getPurchaseOrderIdAndCodeByStatus("INVOICED");
		model.addAttribute("pos", pos);
	}
	
	//show grn register page
	@GetMapping("/register")
	public String showGrnReg(Model model) {
		model.addAttribute("grn", new Grn());
		addDynamicUiComponents( model);
		return "GrnRegister";
	}
	//save grn
	@PostMapping("/save")
	public String saveGrn(@ModelAttribute Grn grn,Model model) {
		Integer id=service.saveGrn(grn);
		model.addAttribute("message", "Grn'"+id+"'saved");
		model.addAttribute("grn", new Grn());
		
		//change po status on GRN Created successfully
		poService.updateStatus(grn.getPo().getId(), "RECEIVED");
		
		
		addDynamicUiComponents( model);
		
		createGrnDtls(grn);
		return "GrnRegister";
	}
	
	private void createGrnDtls(Grn grn) {
		//#a read purchase order id using Grn linked po
		Integer orderId=grn.getPo().getId();
		//#b read all purchaseDtls  data using purchaseOrder orderId
		List<PurchaseDtl> poDtls=poService.findByOrderId(orderId);//poService.getPurchaseDtlsByOrderId(orderId)
		
		for(PurchaseDtl poDtl:poDtls) {
			//#d create one grnDtl object using PurchaseDtl Object
		GrnDtl grnDtl=new GrnDtl();
		grnDtl.setItemCode(poDtl.getPart().getPartCode());
		grnDtl.setBaseCost(poDtl.getPart().getPartCost());
		grnDtl.setQty(poDtl.getQty());
		grnDtl.setItemVal(grnDtl.getBaseCost()*grnDtl.getQty());
		
		//#e Link GrnDtl object to Grn object id
		grnDtl.setGrn(grn);
		
		//#F save GrnDtl object
		service.saveGrnDtl(grnDtl);
		}
	}
	
	
	//save all Grns
	@GetMapping("/all")
	public String showGrns(
			@PageableDefault(page=0,size=3)Pageable p,
			Model model) {
		 /*
		List<Grn> list=service.getAllGrns();
		model.addAttribute("list", list);
		*/
		
	   	Page<Grn> page=service.getAllGrns(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
		
		return "GrnData";
	}
	
	//4. Show GrnDtlsView (Screen#2) based on GrnId
	@GetMapping("/viewParts")
	public String showGrnDtls(@RequestParam Integer grnId,Model model) {
		//Grn object
		Grn grn=service.getOneGrn(grnId);
		model.addAttribute("grn", grn);
		
		List<GrnDtl> list=service.getAllGrnDtlsByGrnID(grnId);
		model.addAttribute("list", list);
		return "GrnDtlView";
	}
	
	
	//5. update GrnDtl Status
	@GetMapping("/updateStatus")
	public String updateGrnDtlStatus(
			@RequestParam Integer grnId,
			@RequestParam Integer grnDtlId,
			@RequestParam String status
			
			) {
		service.updateGrnDtlStatus(grnDtlId, status);
		return "redirect:viewParts?grnId="+grnId;
	}
}
