package com.rk.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

import com.rk.model.OrderMethod;
import com.rk.service.IOrderMethodService;
import com.rk.spec.OrderMethodSpec;
import com.rk.util.OrderMethodUtil;
import com.rk.view.OrderMethodExcelView;
import com.rk.view.OrderMethodPdfView;

@Controller
@RequestMapping("/ordermethod")
public class OrderMethodController {
	
	private Logger log = LoggerFactory.getLogger(OrderMethodController.class);
	
	@Autowired
	private IOrderMethodService service;

	@Autowired
	private OrderMethodUtil util;

	@Autowired
	private ServletContext context;

	//1. show Reg page
	@GetMapping("/register")
	public String showReg(Model model) {
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}

	//2. save data
	@PostMapping("/save")
	public String save(
			@ModelAttribute OrderMethod orderMethod,
			Model model) 
	{
		Integer id=service.saveOrderMethod(orderMethod);
		String message="Order Method '"+id+"' saved";
		model.addAttribute("message", message);
		//clear form
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}

	//3. show all
	@GetMapping("/all")
	public String getAll(
			@ModelAttribute OrderMethod orderMethod, //search form data
			Model model) 
	{
		Specification<OrderMethod> spec = new OrderMethodSpec(orderMethod);
		model.addAttribute("list", service.getAllOrderMethods(spec));
		model.addAttribute("orderMethod", orderMethod); //form backing object
		return "OrderMethodData";
	}

	//4. delete
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id,Model model) {
		String message=null;
		if(service.isOrderMethodExist(id)) {
			service.deleteOrderMethod(id);
			message="Order Method '"+id+"' Deleted";
		}else {
			message="Order Method '"+id+"' Not exist";
		}
		model.addAttribute("message", message);
		model.addAttribute("list", service.getAllOrderMethods());
		return "OrderMethodData";
	}


	//5. edit page
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id ,Model model) {
		String page=null;
		Optional<OrderMethod> opt=service.getOneOrderMethod(id);
		if(opt.isPresent()) {
			model.addAttribute("orderMethod", opt.get());
			page="OrderMethodEdit";
		}else {
			page="redirect:../all";
		}
		return page;
	}

	//6. do update
	@PostMapping("/update")
	public String update(
			@ModelAttribute OrderMethod orderMethod, 
			Model model) 
	{
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "Order Method '"+orderMethod.getId()+"' Updated!");
		model.addAttribute("list", service.getAllOrderMethods());
		return "OrderMethodData";
	}


	//7. show one
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id,Model model) 
	{
		try {
			log.info("Making Service call");
			Optional<OrderMethod> opt=service.getOneOrderMethod(id);
			log.info("Reading Data from Optional Object");
			OrderMethod om=opt.get();
			model.addAttribute("ob", om);
		} catch (NoSuchElementException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "OrderMethodView";
	}

	/**
	 * On Enter Request /ordermethod/excel below method is called
	 * that calls OrderMethodExcelView which gives output of Excel file.
	 * with data.
	 * 
	 */
	//8. Export Excel
	@GetMapping("/excel")
	public ModelAndView showExcel() {
		ModelAndView m=new ModelAndView();

		//link with view class object
		m.setView(new OrderMethodExcelView()); //Output is excel

		//fetch data from db
		List<OrderMethod> list=service.getAllOrderMethods();

		//add data to ModelAndView that is shared with OrderMethodExcelView
		m.addObject("list", list);

		return m;
	}

	//9. Pdf Export
	@GetMapping("/pdf")
	public ModelAndView showPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodPdfView());

		List<OrderMethod> list = service.getAllOrderMethods();
		m.addObject("list", list);
		return m;
	}

	//10. Convert data into JFreeCharts
	@GetMapping("/charts")
	public String showCharts() {
		//Returns location of Temp folder where application is running
		String location = context.getRealPath("/");

		//Get data from Service as Object[]
		List<Object[]> list =service.getOrderModeCount();

		//call util methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);

		return "OrderMethodCharts";
	}


	//--AJAX VALIDATIONS-------------
	@GetMapping("/validatecode")
	public @ResponseBody String validateCode(
			@RequestParam String code,@RequestParam Integer id)
	{
		String msg="";
		if(id==0 && service.isOrderMethodCodeExist(code)) {
			msg = "Order Method <b>code '"+code+"' already exist</b>!";
		}else if(service.isOrderMethodCodeExistEdit(code,id)) {
			msg = "Order Method <b>code '"+code+"' already exist</b>!";
		}
		return msg;
	}

	@GetMapping("/gcharts")
	public @ResponseBody List<Object[]> showGoogleCharts() {
		//Get data from Service as Object[]
		List<Object[]> list =service.getOrderModeCount();
		return list;
	}		
}




