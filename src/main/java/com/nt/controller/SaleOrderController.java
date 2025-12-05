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
import com.nt.model.SaleDtl;
import com.nt.model.SaleOrder;
import com.nt.service.IPartService;
import com.nt.service.ISaleOrderService;
import com.nt.service.IShipmentTypeService;
import com.nt.service.IWhUserTypeService;
import com.nt.view.CustomerInvoicePdfView;
import com.nt.view.VendorInvoicePdfView;

@Controller
@RequestMapping("/so")
public class SaleOrderController {
 
	@Autowired
	private ISaleOrderService service;
	
	@Autowired
	private IShipmentTypeService shipmentService;
	
	@Autowired
	private IWhUserTypeService whService;
	
	@Autowired
	private IPartService partService;
	
	//Register page dropdown
	private void addDynamicUiComponents(Model model) {
	    model.addAttribute("shipmenttypes", shipmentService.getShipmentIdAndCodeByEnabled("YES"));
	    model.addAttribute("customers", whService.getWhUserTypeByUserType("Customer")); // Changed from "vendors" to "customers"
	}
	
	
	//1.Show Register page 
	@GetMapping("/register")
	public String showReg(Model model) {
	    SaleOrder saleOrder = new SaleOrder(); // Changed to lowercase 's'
	    saleOrder.setStatus("SALE-OPEN"); // Set default status here
	    
	    model.addAttribute("saleOrder", saleOrder); // Form backing object - lowercase 's'
	    addDynamicUiComponents(model);
	    return "SaleOrderRegister";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("saleOrder") SaleOrder saleOrder, Model model) {
	    // set default status if null
	    if (saleOrder.getStatus() == null) {
	        saleOrder.setStatus("SALE-OPEN");
	    }

	    Integer id = service.SaveSaleOrder(saleOrder);
	    model.addAttribute("message", "Sale Order '" + id + "' saved successfully!");

	    // reset form
	    SaleOrder newOrder = new SaleOrder();
	    newOrder.setStatus("SALE-OPEN");
	    model.addAttribute("saleOrder", newOrder);

	    addDynamicUiComponents(model);
	    return "SaleOrderRegister";
	}

	
	//Display POs
	@GetMapping("/all")
	public String showAll(
			@PageableDefault(page=0,size=3)Pageable p,
			Model model) {
		  /*
		List<SaleOrder> list=service.getAllSaleOrders();
		model.addAttribute("list", list);
		       */
	   	Page<SaleOrder> page=service.getAllSaleOrders(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
		
		return "SaleOrderData";
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
		SaleOrder so=service.getSaleOrder(id);
		model.addAttribute("so", so);
		
		//---Section#2----------------
		//Dynamic Dropdown
		addDynamicUiComponentsForParts(model);
		
		//send form backing object
		model.addAttribute("saleDtl", new SaleDtl());	
		
		//Section#4
		//Show parts added to order based on orderId
		List<SaleDtl> dtls=service. findByOrderId(id);//orderId
		//send data to ui
		model.addAttribute("dtls", dtls);
		return "SaleOrderParts";
	}
	
	/*
	 * OnClick add part button,
	 * Read Form data as SaleDtl,
	 * save into DB using service method,
	 * redirect to same UI With /parts?id=<orderId>
	 */
	@PostMapping("/add")//Section#3
	public String addPart(@ModelAttribute SaleDtl SaleDtl) {
		
		Integer Orderid=SaleDtl.getOrder().getId();
		
		service.saveSaleDtl(SaleDtl);		
		
		//Update orderStatus
		service.updateStatus(Orderid, "SALE-READY");
		
		//From SaleDtl->get Order->From order get Id(ie order id)
		return "redirect:parts?id="+SaleDtl.getOrder().getId();//Order id(PO-ID)
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
		service.removeSaleDtl(dtlId);
		if(service.findCountByOrderId(orderId)==0) {
			service.updateStatus(orderId, "SALE-OPEN");
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
		 service.updateStatus(orderId, "SALE-CONFIRMED");
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
		m.setView(new CustomerInvoicePdfView());
		
		m.addObject("so",service.getSaleOrder(id));//also m.addObject("po",service.getOneSaleOrder(id));
		m.addObject("dtls",service.findByOrderId(id));//m.addObject("dtls",service.getSaleOrderDtlsByOrderId(id))
		return m;
	}
	
}
