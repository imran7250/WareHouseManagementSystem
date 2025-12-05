package com.nt.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.nt.model.Shipping;
import com.nt.model.ShippingDtl;
import com.nt.model.SaleDtl;  // Assuming SaleDtl exists analogous to PurchaseDtl
import com.nt.model.SaleOrder;
import com.nt.service.IShippingService;
import com.nt.service.ISaleOrderService;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
 
    private static final Logger log = LoggerFactory.getLogger(ShippingController.class);
 
	@Autowired
	private IShippingService service;
	
	@Autowired
	private ISaleOrderService soService;
	
	//Register page dropdown
	private void addDynamicUiComponents(Model model) {
		Map<Integer,String> sos=soService.getSaleOrderIdAndCodeByStatus("INVOICED");  // Adjust status as needed
		log.info("Loaded {} SaleOrders with status CONFIRMED", sos.size());  // Check logs for count
		model.addAttribute("sos", sos);
	}
	
	//show shipping register page
	@GetMapping("/register")
	public String showShippingReg(Model model) {
		model.addAttribute("shipping", new Shipping());
		addDynamicUiComponents(model);
		return "ShippingRegister";
	}
	
	//save shipping
	@PostMapping("/save")
	public String saveShipping(@ModelAttribute Shipping shipping, Model model) {
		Integer id=service.saveShipping(shipping);
		model.addAttribute("message", "Shipping '"+id+"' saved");
		model.addAttribute("shipping", new Shipping());
		
		//change so status on Shipping Created successfully
		soService.updateStatus(shipping.getSo().getId(), "SHIPPED");
		
		
		addDynamicUiComponents(model);
		
		createShippingDtls(shipping);
		return "ShippingRegister";
	}
	
	private void createShippingDtls(Shipping shipping) {
		//#a read sale order id using Shipping linked so
		Integer orderId=shipping.getSo().getId();
		//#b read all saleDtls data using saleOrder orderId
		List<SaleDtl> soDtls=soService.findByOrderId(orderId);  // Adjust method name as per your ISaleOrderService
		
		for(SaleDtl soDtl:soDtls) {
			//#d create one shippingDtl object using SaleDtl Object
			ShippingDtl shippingDtl=new ShippingDtl();
			shippingDtl.setItemCode(soDtl.getPart().getPartCode());  // Adjust based on your model
			shippingDtl.setBaseCost(soDtl.getPart().getPartCost());  // Adjust based on your model
			shippingDtl.setQty(soDtl.getQty());
			shippingDtl.setItemVal(shippingDtl.getBaseCost()*shippingDtl.getQty());
			
			//#e Link ShippingDtl object to Shipping object id
			shippingDtl.setShipping(shipping);
			
			//#F save ShippingDtl object
			service.saveShippingDtl(shippingDtl);
		}
	}
	
	
	//save all Shippings
	@GetMapping("/all")
	public String showShippings(
			@PageableDefault(page=0,size=3)Pageable p,
			Model model) {
		
	   	Page<Shipping> page=service.getAllShippings(p);
    	model.addAttribute("list", page.getContent());
    	model.addAttribute("page", page);
		
		return "ShippingData";
	}
	
	//4. Show ShippingDtlsView (Screen#2) based on ShippingId
	@GetMapping("/viewParts")
	public String showShippingDtls(@RequestParam Integer shippingId, Model model) {
		//Shipping object
		Shipping shipping=service.getOneShipping(shippingId);
		model.addAttribute("shipping", shipping);
		
		List<ShippingDtl> list=service.getAllShippingDtlsByShippingID(shippingId);
		model.addAttribute("list", list);
		return "ShippingDtlView";
	}
	
	
	//5. update ShippingDtl Status
	@GetMapping("/updateStatus")
	public String updateShippingDtlStatus(
			@RequestParam Integer shippingId,
			@RequestParam Integer shippingDtlId,
			@RequestParam String status
			
			) {
		service.updateShippingDtlStatus(shippingDtlId, status);
		return "redirect:viewParts?shippingId="+shippingId;
	}
}
