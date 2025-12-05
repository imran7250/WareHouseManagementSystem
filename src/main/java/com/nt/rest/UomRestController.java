package com.nt.rest;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.Uom;
import com.nt.service.IUomService;
import com.nt.service.exception.UomNotFoundException;
import com.nt.util.UomUtil;

@RestController
@RequestMapping("/rest/uom")
public class UomRestController {
	
	private static final Logger log=LoggerFactory.getLogger(UomRestController.class);
    
	@Autowired
	private IUomService service;
	
	@Autowired
	private UomUtil uomUtil;
	//1.Fetch all rows
	@GetMapping("/all")
	public ResponseEntity<?> getAllUoms(@PageableDefault (page=0,size=3)Pageable p){
		log.info("ENTERD INTO GET ALL METHOD");
		ResponseEntity<?> resp=null;
		try {
			Page<Uom> page=service.getAllUoms(p);
			log.info("SERVICE METHOD CALLED FOR ALL DATA FETCH");
			resp=  new ResponseEntity<Page<Uom>>(page,HttpStatus.OK);
			log.info("SUCCESS RESPONSE CREATED");
		}
		catch(Exception e) {
			log.error("PROBLEM IN FETCHING DATA{}",e.getMessage());
			resp=  new ResponseEntity<String>("Unable to fetch Uoms",HttpStatus.OK);
		  e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM GET ALL METHOD");
		return resp;
	}
	
	
	//2.INSERT DATA
	@PostMapping("/save")
	public ResponseEntity<String> saveUom(@RequestBody Uom uom){
		log.info("ENTERD INTO SAVE METHOD");
		ResponseEntity<String> resp=null;
		try {
			log.info("ENTERD OPERATION IS CALLING");
			Integer id=service.saveUom(uom);
			log.debug("SAVED WITH ID",id);
			String message="uom"+id+"saved";			
			resp=  new ResponseEntity<String>(message,HttpStatus.CREATED);
			log.info("SUCCESS RESPONSE CONSTRUCT");
		}catch(Exception e) {
			log.error("UNABLE TO SAVE UOM{}",e.getMessage());
			resp=  new ResponseEntity<String>("Unable to fetch Uoms",HttpStatus.INTERNAL_SERVER_ERROR);
		  e.printStackTrace();
		}
		log.info("ABOUT TO RETURN FROM SAVE METHOD");
		return resp;
	}
	
	//3.Fetch one row by id
	@GetMapping("/one/{id}")
	public ResponseEntity<?> fetchUomById(			
			@PathVariable Integer id
			){		
		ResponseEntity<?> resp=null;
		try {
		Uom uom=service.getOneUom(id);
		resp=ResponseEntity.ok(uom);
		}catch(UomNotFoundException unfe) {
			throw unfe;
		}
		catch(Exception e) {
			resp=  new ResponseEntity<String>("Unable to fetch Uoms",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resp;
	}
	
	//4.Delete one row by id
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> removeUomById(
			@PathVariable Integer id
			){
		ResponseEntity<String> resp=null;
		try {
			service.deleteUom(id);
			resp=ResponseEntity.ok("Uom Deleted Succesfully");
		}catch(UomNotFoundException unfe) {
			throw unfe;
		}
		catch(Exception e) {
			resp=  new ResponseEntity<String>("Unable to delete Uoms",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
	
	//5.Update data
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> getOneUom(
			@PathVariable Integer id,
			@RequestBody Uom uom
			){
		ResponseEntity<String>resp=null;
		try {
			Uom dbuom=service.getOneUom(id);
			uomUtil.copyNonNullValues(dbuom,uom);
			service.updateUom(dbuom);
			resp=new ResponseEntity<String>("Uom Updated! Successfully",HttpStatus.OK);
		}catch(UomNotFoundException unfe) {
			throw unfe;		
		}catch(Exception e) {
			resp=  new ResponseEntity<String>("Unable to update Uoms",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
	
}
