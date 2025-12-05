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
import org.springframework.web.servlet.ModelAndView;

import com.nt.model.Part;
import com.nt.model.PurchaseDtl;
import com.nt.model.PurchaseOrder;
import com.nt.service.IPartService;
import com.nt.service.IPurchaseOrderService;
import com.nt.service.IShipmentTypeService;
import com.nt.service.IWhUserTypeService;
import com.nt.view.VendorInvoicePdfView;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {
 
	@Autowired
	private IPurchaseOrderService service;
	
	@Autowired
	private IShipmentTypeService shipmentService;
	
	@Autowired
	private IWhUserTypeService whService;
	
	@Autowired
	private IPartService partService;
	
	//Register page dropdown
	private void addDynamicUiComponents(Model model) {
		model.addAttribute("shipmenttypes", shipmentService.getShipmentIdAndCodeByEnabled("YES"));
		model.addAttribute("vendors", whService.getWhUserTypeByUserType("Vendor"));
	}
	
	//1.Show Register page 
	@GetMapping("/register")
	public String showReg(Model model) {
	    PurchaseOrder purchaseOrder = new PurchaseOrder();
	    purchaseOrder.setStatus("OPEN"); // Set default status here
	    
	    model.addAttribute("purchaseOrder", purchaseOrder);//Form backing object
	    addDynamicUiComponents(model);
	    return "PurchaseOrderRegister";
	}
	
	//save data
	@PostMapping("/save")
	public String save(@ModelAttribute PurchaseOrder purchaseOrder,//Read form data
			  Model model// send data back to Ui using model
			) {
		Integer id=service.SavePurchaseOrder(purchaseOrder);
		model.addAttribute("message", "Purchase Order'"+id+"'saved");//send message to ui
		model.addAttribute("purchaseOrder", new PurchaseOrder());//Form backing object
		addDynamicUiComponents(model);
		return "PurchaseOrderRegister";
	}
	
	//Display POs
	@GetMapping("/all")
	public String showAll(
			@PageableDefault(page=0,size=3)Pageable p,
			Model model) {
		  /*
		List<PurchaseOrder> list=service.getAllPurchaseOrders();
		model.addAttribute("list", list);
		       */
	   	Page<PurchaseOrder> page=service.getAllPurchaseOrders(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
		
		return "PurchaseOrderData";
	}
	
	//....................Methods for Screen#2 Purchase Order parts.............
	//---Section#2--------------
	
	private void addDynamicUiComponentsForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}
	
	/*
	 * This method is showing output of Screen#2
	 * It is displayed when we click on ADD PARTS from Screen#1
	 * and even after adding new Part/Remove added part same page is loaded
	 * @param id
	 * @param model
	 * @return
	 */
	
	@GetMapping("/parts")
	public String showPartsPage(@RequestParam Integer id,Model model) {
		//----Section#1-------------
		//Get Purchase Order By Id
		PurchaseOrder po=service.getPurchaseOrder(id);
		model.addAttribute("po", po);
		
		//---Section#2----------------
		//Dynamic Dropdown
		addDynamicUiComponentsForParts(model);
		
		//send form backing object
		model.addAttribute("purchaseDtl", new PurchaseDtl());	
		
		//Section#4
		//Show parts added to order based on orderId
		List<PurchaseDtl> dtls=service. findByOrderId(id);//orderId
		//send data to ui
		model.addAttribute("dtls", dtls);
		return "PurchaseOrderParts";
	}
	
	/*
	 * OnClick add part button,
	 * Read Form data as PurchaseDtl,
	 * save into DB using service method,
	 * redirect to same UI With /parts?id=<orderId>
	 */
	@PostMapping("/add")//Section#3
	public String addPart(@ModelAttribute PurchaseDtl purchaseDtl) {
		
		Integer Orderid=purchaseDtl.getOrder().getId();
		
		service.savePurchaseDtl(purchaseDtl);		
		
		//Update orderStatus
		service.updateStatus(Orderid, "PICKING");
		
		//From PurchaseDtl->get Order->From order get Id(ie order id)
		return "redirect:parts?id="+purchaseDtl.getOrder().getId();//Order id(PO-ID)
	}
	
	/**
	 * on click remove button/link,this method is called 
	 * with 2 inputs @param id, @param orderId
	 * it will remove one part using id and then redirect to s
	 * same page using orderId
	 * @return
	 */
	@GetMapping("/remove")
	public String removePart(
	        @RequestParam Integer dtlId,
	        @RequestParam Integer orderId 
			
			) {
		service.removePurchaseDtl(dtlId);
		if(service.findCountByOrderId(orderId)==0) {
			service.updateStatus(orderId, "OPEN");
			//service.updateStatus(orderId, orderStatus.OPEN.name());
		}
		return "redirect:parts?id="+orderId;
	}
	
	/**
	 * Read Ordered Id and update status of po to ordered
	 * Finally redirect to Screen to /part?id=<orderId>
	 */
	@GetMapping("/conformOrder")
	public String placeOrder(
			@RequestParam Integer orderId
			) {
		 service.updateStatus(orderId, "ORDERED");
		 //back to Screen#2
		 return "redirect:parts?id="+orderId;
	} 
	
	/**
	 * Generate INVOICED status change
	 *  from ORDERED TO INVOICED
	 */
	
	@GetMapping("/genInv")
	public String generateInvoice(@RequestParam Integer id)// orderId
	{
		service.updateStatus(id, "INVOICED");
		//service.updateStatus(id, orderStatus.INVOICED.name());
		return "redirect:all";//Screen #1
		
	}
	/**
	 * Print Vendor Invoice PDP based on orderId
	 */
	@GetMapping("/printInv")
	public ModelAndView showInvoice(@RequestParam Integer id)//orderId	
	{
		ModelAndView m=new ModelAndView();
		m.setView(new VendorInvoicePdfView());
		
		m.addObject("po",service.getPurchaseOrder(id));//also m.addObject("po",service.getOnePurchaseOrder(id));
		m.addObject("dtls",service.findByOrderId(id));//m.addObject("dtls",service.getPurchaseOrderDtlsByOrderId(id))
		return m;
	}
	
}
