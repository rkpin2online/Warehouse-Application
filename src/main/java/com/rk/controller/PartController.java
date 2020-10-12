package com.rk.controller;

import java.util.Arrays;
import java.util.Optional;

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

import com.rk.model.Part;
import com.rk.service.IPartService;
import com.rk.service.IUomService;
import com.rk.view.PartExcelView;
import com.rk.view.PartPdfView;

@Controller
@RequestMapping("/part")
public class PartController {
	@Autowired
	private IPartService service;
	
	@Autowired
	private IUomService uomService;
	
	//call this method inside other controller methods
	// where return page is Register or Edit.
	private void addDorpDownUi(Model model) {
		model.addAttribute("uoms",uomService.getUomIdAndModel());
	}
	

	// 1.show reg page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("part", new Part());
		addDorpDownUi(model);
		return "PartRegister";
	}

	// 2.save data
	@PostMapping("/save")
	public String save(@ModelAttribute Part part, Model model) {
		Integer id = null;
		String msg = null;
		id = service.savePart(part);
		msg = "Part '" + id + "'save successfully";
		model.addAttribute("message", msg);
		model.addAttribute("part", new Part());
		addDorpDownUi(model);
		return "PartRegister";
	}

	// 3.show all
	@GetMapping("/all")
	public String fetchAll(Model model) {
		model.addAttribute("list", service.getAllParts());
		return "PartData";
	}

	// 4.delete record
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		if (service.isPartExist(id)) {
			service.deletePart(id);
			msg = "Part '" + id + "'Deleted!";
		} else {
			msg = "Part '" + id + "'Not Existed!";
		}
		model.addAttribute("list", service.getAllParts());
		model.addAttribute("message", msg);
		return "PartData";
	}

	// 5.show edit form

	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<Part> opt = service.getOnePart(id);
		if (opt.isPresent()) {
			Part p = opt.get();
			model.addAttribute("part", p);
			addDorpDownUi(model);
			page = "PartEdit";
		} else {
			page = "PartData";
		}
		return page;
	}

	// 6.update
	@PostMapping("/update")
	public String update(@ModelAttribute Part part, Model model) {
		service.updatePart(part);
		model.addAttribute("message", "Part '" + part.getId() + "' Updated!");
		model.addAttribute("list", service.getAllParts());
		return "PartData";
	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<Part> opt = service.getOnePart(id);
		if (opt.isPresent()) {
			Part om = opt.get();
			model.addAttribute("ob", om);
			page = "PartView";
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
		m.setView(new PartExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllParts());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PartExcelView());
		Optional<Part> opt = service.getOnePart(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 10. Export Data to PDF File
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new PartPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllParts());
		return m;
	}
	// 11. Export One row to PDF File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PartPdfView());
		Optional<Part> opt = service.getOnePart(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 12.Ajax Validation
	@GetMapping("/validatecode")
	public @ResponseBody String validatePartCode(
			@RequestParam String code,
			@RequestParam Integer id)
	{
		String msg = "";
		if (id==0 && service.isPartCodeExist(code)) { //register
			msg = "Part Code <b>'" + code + "' is Already exist</b>!";
		}else if (service.isPartCodeExistForEdit(code,id)) { //edit
			msg = "Part Code <b>'" + code + "' is Already exist</b>!";
		} 
		return msg;
	}
}
