package com.rk.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.rk.model.ShipmentType;
import com.rk.service.IShipmentTypeService;
import com.rk.util.ShipmentTypeUtil;
import com.rk.view.ShipmentTypeExcelView;
import com.rk.view.ShipmentTypePdfView;

@Controller // =Object + HTTP Request
@RequestMapping("/shipmenttype")
public class ShipmentTypeController {
	
	private Logger log = LoggerFactory.getLogger(ShipmentTypeController.class);
	
	@Autowired
	private IShipmentTypeService service;
	@Autowired
	private ServletContext context;
	@Autowired
	private ShipmentTypeUtil util;
	

	//1. Show Register Page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page ShipmentTypeRegister.html
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		//Form backing Object 
		model.addAttribute("shipmentType", new ShipmentType());
		return "ShipmentTypeRegister";
	}


	//2. save : on click submit
	/**
	 * URL: /save, Type: POST
	 * Goto : ShipmentTypeRegister
	 */
	@PostMapping("/save")
	public String save(
			//read from Data from UI(given by container)
			@ModelAttribute ShipmentType shipmentType,
			Model model //to send data to UI
			)
	{
		log.info("Entered into save method");
		try {
			
			//perform save operation
			Integer id=service.saveShipmentType(shipmentType);
			//construct one message
			String message="ShipmentType '"+id+"' saved successfully";
			log.debug(message);
			
			//send message to UI
			model.addAttribute("message", message);
			//Form backing Object 
			model.addAttribute("shipmentType", new ShipmentType());
			
		} catch (Exception e) {
			log.error("Unable to save:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI Page");
		//Goto Page
		return "ShipmentTypeRegister";
	}

	//3. Display data
	@GetMapping("/all")
	public String fetchAll(
			@PageableDefault(page = 0,size = 3)Pageable pageable,
			Model model)
	{
		try {
			log.info("Entered into method");
			Page<ShipmentType> page=service.getAllShipmentTypes(pageable);
			model.addAttribute("page", page);
			log.info("Got Data from DB using service:size of List is:"+page.getContent().size());
		} catch (Exception e) {
			log.error("Unable to fetch Data from DB:"+e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI page for data display");
		return "ShipmentTypeData";
	}

	/**
	 * From Client(browser) enter URL like
	 * /delete/10 -> copy 10 into id path variable
	 * read id using @PathVariable DataType key syntax
	 * After Delete successful send message to UI
	 * Also select other rows from DB.
	 */

	//4. Remove one by Id
	@GetMapping("/delete/{id}")
	public String removeById(@PathVariable Integer id,Model model) 
	{
		log.info("Entered for Delete with ID:"+id);
		try {
			String msg=null;
			if(service.isShipmentTypeExist(id)) {
				
				service.deleteShipmentType(id);
				msg="ShipmentType '"+id+"' deleted!";
				log.debug(msg);
				
			}else {
				msg="ShipmentType '"+id+"' Not exist!";
				log.warn(msg);
			}
			
			model.addAttribute("message", msg);
			
			log.info("FETCHING NEW DATA AFTER DELETE");
			//show other rows
			Page<ShipmentType> page=service.getAllShipmentTypes(PageRequest.of(0, 3));
			model.addAttribute("page", page);
			
			
		} catch (Exception e) {
			log.error(e.getMessage() + " : Unable to Perform Delete operation");
			e.printStackTrace();
		}

		return "ShipmentTypeData";
	}

	/**
	 * On click Edit HyperLink at UI,
	 * read one PathVariable and fetch data from 
	 * service, if exist send to edit page
	 * else redirect to data page
	 */
	//5. Show Edit Page with data
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id,Model model)
	{
		log.info("Entered into EDIT Operation with id:"+id);
		String page=null;
		try {

			Optional<ShipmentType> opt=service.getOneShipmentType(id);
			log.info("Service called is made");
			
			if(opt.isPresent()) {
				log.info("Data exist with Id:"+id);
				ShipmentType st=opt.get();
				model.addAttribute("shipmentType", st);
				page="ShipmentTypeEdit";
			}else {
				log.warn("Data not exist with Id:"+id);
				page="redirect:../all";
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * On click update button,read form data and perform update operation
	 * send back to Data page with success message.
	 */
	//6. Update: on click update
	@PostMapping("/update")
	public String update(
			@ModelAttribute ShipmentType shipmentType,
			@PageableDefault(page = 0,size = 3) Pageable pageable,
			Model model)
	{
		log.info("Entered into Update operation");
		try {
			service.updateShipmentType(shipmentType);
			String msg="ShipmentType '"+shipmentType.getId()+"' Updated!";
			log.debug(msg);
			model.addAttribute("message", msg);
			
			log.info("Fetching latest data");
			//new data from Db
			Page<ShipmentType> page=service.getAllShipmentTypes(pageable);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("Back to UI page");
		return "ShipmentTypeData";
	}

	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypeExcelView());

		//send data to View class
		List<ShipmentType> list=service.getAllShipmentTypes();
		m.addObject("obs", list);
		return m;
	}

	//8. Export One row to Excel File
	@GetMapping("/excel/{id}")
	public ModelAndView exportToExcelOne(
			@PathVariable Integer id) 
	{
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypeExcelView());

		Optional<ShipmentType> opt=service.getOneShipmentType(id);
		if(opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		//send data to View class one object as List
		return m;
	}

	
	//9. Export Data to PDF
	@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		
		//get data from service
		List<ShipmentType> list=service.getAllShipmentTypes();
		m.addObject("list", list);
		
		return m;
	}
	
	//10. Export One row to PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		
		//get data from service
		Optional<ShipmentType> opt=service.getOneShipmentType(id);
		if(opt.isPresent()) {
			m.addObject("list", Arrays.asList(opt.get()));
		}
		
		return m;
	}
	
	//11. Generate Chart
	@GetMapping("/charts")
	public String generateCharts() {
		//data to show at Chart
		List<Object[]> list=service.getShipmentModeCount();

		//Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");
		log.info("CHART LOCATION IS: " + location );
		
		//call chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		
		return "ShipmentTypeCharts";
	}
	
	
	//---AJAX VALIDATION----------
	//.. /shipmenttype/validatecode?code=ABCD
	@GetMapping("/validatecode")
	public @ResponseBody String validateShipmentCode(
			@RequestParam String code,
			@RequestParam Integer id) 
	{
		String message="";
		if(id==0 && service.isShipmentTypeCodeExist(code)) { //register
			message = "Shipment Code <b>'"+code+"' Already exist</b>!";
		}else if(service.isShipmentTypeCodeExistForEdit(code,id)) { //edit process
			message = "Shipment Code <b>'"+code+"' Already exist</b>!";
		}
		return message;
	}
	
	// @ResponseBody-> converts List<T> -> JSON Format
	@GetMapping("/gcharts")
	public @ResponseBody List<Object[]> getGoogleChartsData() 
	{
		//sample output [ {'Air',10},{'Train',20},...]
		List<Object[]> list = service.getShipmentModeCount();
		return list;
	}
	
	
}
