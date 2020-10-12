package com.rk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rk.model.Grn;
import com.rk.model.GrnDtl;
import com.rk.model.PurchaseDtl;
import com.rk.service.IGrnService;
import com.rk.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {
	@Autowired
	private IGrnService service;
	@Autowired
	private IPurchaseOrderService poservice;
	
	private void addDorpDownUi(Model model) {
		model.addAttribute("pos", poservice.getPoIdAndCodeByStatus("INVOICED"));
	}
	
	//1. show GrnReg page
	@GetMapping("/register")
	public String showReg(Model model) 
	{
		model.addAttribute("grn",new Grn());
		addDorpDownUi(model);
		return "GrnReg";
	}
	
	//2. save Grn
	@PostMapping("/save")
	public String saveGrn(
			@ModelAttribute Grn grn,Model model) 
	{
		Integer id = service.saveGrn(grn);
		model.addAttribute("message","GRN SAVED WITH ID:"+id);
		model.addAttribute("grn",new Grn());
		if(id!=null) {
			Integer poId = grn.getPo().getId();
			//PO is used in GRN. So, it is received.
			poservice.updatePurchaseOrderStatus("RECEIVED", poId);
			//map PurchaseDtls as GrnDtl object
			convertPurchaseDtlToGrnDtl(id, poId);
		}
		addDorpDownUi(model);
		return "GrnReg";
	}
	
	//3. fetch data to UI
	@GetMapping("/all")
	public String showData(Model model) 
	{
		model.addAttribute("list", service.getAllGrns());
		return "GrnData";
	}
	
	//----------code for Scrren#2-----------
	private void convertPurchaseDtlToGrnDtl(
			Integer grnId,Integer poId) 
	{
		//1. Get PurchaseDtls List using PoId
		List<PurchaseDtl> poDtlsList = poservice.getPurchaseDtlWithPoId(poId);
		
		//2. convert one PoDtl object to one GrnDtlObject
		for(PurchaseDtl poDtl:poDtlsList) {
			GrnDtl grnDtl = new GrnDtl();
			grnDtl.setPartCode(poDtl.getPart().getPartCode());
			grnDtl.setBaseCost(poDtl.getPart().getBaseCost());
			grnDtl.setQty(poDtl.getQty());
			grnDtl.setLineValue(grnDtl.getQty()* grnDtl.getBaseCost());
			
			//link with grnDtl with Grn object (Parent)
			Grn grn = new Grn(); 
			grn.setId(grnId); //only ID is OK.
			grnDtl.setGrn(grn);
			
			//save in Database
			service.saveGrnDtl(grnDtl);
			
		}
	}
	
	
	//Display all Grn Dtls here
	@GetMapping("/viewDtls/{id}")
	public String showDtls(
			@PathVariable Integer id, //grnId
			Model model) 
	{
		List<GrnDtl> dtls = service.getAllDtlsByGrnId(id);
		model.addAttribute("dtls", dtls);
		return "GrnDtlView";
	}
	
	// ../status?grnId=_&dtlId=_&status=__
	@GetMapping("/status")
	public String updateDtlStatus(
			@RequestParam Integer grnId,
			@RequestParam Integer dtlId,
			@RequestParam String status
			) 
	{
		service.updateStatusByGrnDtlId(status, dtlId);
		return "redirect:viewDtls/"+grnId;
	}
}


