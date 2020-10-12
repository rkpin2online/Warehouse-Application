package com.rk.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rk.model.PurchaseDtl;
import com.rk.model.PurchaseOrder;
import com.rk.service.IPartService;
import com.rk.service.IPurchaseOrderService;
import com.rk.service.IShipmentTypeService;
import com.rk.service.IWhUserTypeService;
import com.rk.util.PurchaseOrderUtil;
import com.rk.view.PurchaseOrderExcelView;
import com.rk.view.PurchaseOrderPdfView;
import com.rk.view.VendorInvoicePdf;

@Controller
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

	@Autowired
	private IPurchaseOrderService service;

	@Autowired
	private ServletContext context;

	@Autowired
	private PurchaseOrderUtil util;

	@Autowired
	private IShipmentTypeService shipmentTypeservice;

	@Autowired
	private IWhUserTypeService whUserTypeService;

	@Autowired
	private IPartService partService;

	//call this method inside other controller methods
	// where return page is Register or Edit.
	private void addDorpDownUi(Model model) {
		model.addAttribute("shipmentTypes",shipmentTypeservice.getShipmentIdAndCode());
		model.addAttribute("whUserTypes",whUserTypeService.getWhUserTypeIdAndCode("Vendor"));
	}

	// 1. Show Register Page
	@GetMapping("/register")
	public String showRegister(Model model) {

		model.addAttribute("purchaseOrder", new PurchaseOrder());
		addDorpDownUi(model);
		return "PurchaseOrderRegister";
	}

	// 2. save : on click submit

	@PostMapping("/save")
	public String save(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {

		Integer id = null;
		String message = null;
		// peform save operation
		id = service.savePurchaseOrder(purchaseOrder);
		// construct one message
		message = "purchase Order '" + id + "' saved successfully";
		// send message to UI
		model.addAttribute("message", message);

		model.addAttribute("purchaseOrder", new PurchaseOrder());
		addDorpDownUi(model);
		// got to Page
		return "PurchaseOrderRegister";
	}

	// 3.Displaying data:
	@GetMapping("/all")
	public String fetchAll(Model model) {
		List<PurchaseOrder> list = service.getAllPurchaseOrders();
		model.addAttribute("list", list);
		return "PurchaseOrderData";
	}

	// 4.delete record
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		// invoke service
		if (service.isPurchaseOrderExist(id)) {
			service.deletePurchaseOrder(id);
			msg = "Purchase Order '" + id + "' Type deleted !";
		} else {

			msg = "Purchase Order'" + id + "' Not Existed !";
		}
		// display other records
		List<PurchaseOrder> list = service.getAllPurchaseOrders();
		model.addAttribute("list", list);

		// send confirmation to UI
		model.addAttribute("message", msg);
		return "PurchaseOrderData";
	}

	// 5.Edit form
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			PurchaseOrder order = opt.get();			
			model.addAttribute("purchaseOrder", order);
			addDorpDownUi(model);
			page = "PurchaseOrderEdit";
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 6.update method
	@PostMapping("/update")
	public String update(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
		String msg = null;
		service.updatePurchaseOrder(purchaseOrder);
		msg = "Purchase Order '" + purchaseOrder.getId() + "' updated successfully..";
		model.addAttribute("message", msg);

		// display other records
		List<PurchaseOrder> list = service.getAllPurchaseOrders();
		model.addAttribute("list", list);
		return "PurchaseOrderData";
	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			PurchaseOrder order = opt.get();
			model.addAttribute("ob", order);
			page = "PurchaseOrderView";
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new PurchaseOrderExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllPurchaseOrders());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderExcelView());
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 10. Export Data to Pdf File
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new PurchaseOrderPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllPurchaseOrders());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderPdfView());
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 12. ---AJAX VALIDATION----------
	// .. /shipmenttype/validatecode?code=ABCD
	@GetMapping("/validatecode")
	public @ResponseBody String validatePurchaseOrderCode(@RequestParam String code) {
		String message = "";
		if (service.isPurchaseOrderCodeExist(code)) {
			message = "Purchase Order Code <b>'" + code + "' Already exist</b>!";
		}
		return message;
	}

	// 12. Generate Chart

	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getPurchaseOrderQualityCheckCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "PurchaseOrderCharts";
	}

	//*************************************************************************//
	//**                    SCREEN#2 OPERATIONS                             ***//
	//*************************************************************************//

	private void addDorpDownUiForDtls(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	//1. show Dtls page
	@GetMapping("/dtls/{id}")
	public String showDtls(
			@PathVariable Integer id, //PO Id,
			Model model) 
	{
		String page=null;
		//get PO using id
		Optional<PurchaseOrder> po = service.getOnePurchaseOrder(id);
		if(po.isPresent()) {
			model.addAttribute("po", po.get());
			//It will show PartsDropDown
			addDorpDownUiForDtls(model); 
			//form backing Object for Adding PART + Linked with PO
			PurchaseDtl purchaseDtl = new PurchaseDtl();
			purchaseDtl.setPo(po.get());
			model.addAttribute("purchaseDtl",purchaseDtl);

			//Display All Added Parts as HTML table
			model.addAttribute("dtlList",service.getPurchaseDtlWithPoId(po.get().getId()));

			page ="PurchaseDtls";

		}else {
			page ="redirect:../all";
		}

		return page;
	}

	//2. on click add button 
	/**
	 * Read PurchaseDtl object and save DB
	 * redirect to /dtls/{id} -> showDtl() method
	 */
	@PostMapping("/addPart")
	public String addPartToPo(
			@ModelAttribute PurchaseDtl purchaseDtl) 
	{
		service.addPartToPo(purchaseDtl);
		Integer poId = purchaseDtl.getPo().getId();
		service.updatePurchaseOrderStatus("PICKING",poId);
		return "redirect:dtls/"+poId; //POID
	}

	//3 on click delete remove one dtl from PurchaseDtls table
	@GetMapping("/removePart")
	public String removePart(
			@RequestParam Integer dtlId,
			@RequestParam Integer poId
			)
	{
		service.deletePurchaseDtl(dtlId);
		Integer dtlCount = service.getPurchaseDtlCountWithPoId(poId);
		if(dtlCount==0) {
			service.updatePurchaseOrderStatus("OPEN",poId);
		}
		return "redirect:dtls/"+poId; //POID
	}

	//4. On click conform button change status from PICKING to ORDERED
	@GetMapping("/conformOrder/{id}")
	public String placeOrder(@PathVariable Integer id)
	{
		Integer dtlCount = service.getPurchaseDtlCountWithPoId(id);
		if(dtlCount>0) {
			service.updatePurchaseOrderStatus("ORDERED",id);
		}
		return "redirect:../dtls/"+id; //POID
	}

	//5. chnage status from ORDERED to INVOICED
	@GetMapping("/invoiceOrder/{id}")
	public String invoiceOrder(@PathVariable Integer id)
	{
		service.updatePurchaseOrderStatus("INVOICED",id);
		return "redirect:../all"; //POID
	}


	//6. chnage status from ORDERED to INVOICED
	@GetMapping("/printInvoice/{id}")
	public ModelAndView printInvoice(@PathVariable Integer id)
	{
		ModelAndView m = new ModelAndView();
		m.setView(new VendorInvoicePdf());
		m.addObject("po", service.getOnePurchaseOrder(id).get());
		return m;
	}
}
