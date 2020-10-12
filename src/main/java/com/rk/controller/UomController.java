package com.rk.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.servlet.ModelAndView;

import com.rk.model.Uom;
import com.rk.service.IUomService;
import com.rk.view.UomExcelView;
import com.rk.view.UomPdfView;

@Controller
@RequestMapping("/uom")
public class UomController {

	@Autowired
	private IUomService service;

	// 1.Get form
	// show form
	// by using get ruquest

	/*
	 * @GetMapping("/register") public String showRegister() {
	 * 
	 * return "UomRegister"; }
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("uom", new Uom());
		return "UomRegister";
	}

	// 2.save operation
	// by using Post request
	@PostMapping("/save")
	public String save(@ModelAttribute Uom uom, Model model) {

		Integer id = null;
		String message = null;
		// perform save operation
		id = service.saveUom(uom);

		// generate confirmation message
		message = "Uom '" + id + "' saved successfully";
		// send to confirmation message to UI
		model.addAttribute("message", message);
		model.addAttribute("uom", new Uom());
		// Go to page
		// return "UomRegister";
		return "UomRegister";

	}

	// 3.Displaying data
	//all?page=3&size=4
	@GetMapping("/all")
	public String fetchAll(
			@PageableDefault(page = 0,size = 3)Pageable pageable,
			Model model) 
	{
		Page<Uom> page = service.getAllUoms(pageable);
		model.addAttribute("page", page);
		return "UomData";
	}

	// 4.Deleting record

	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		if (service.isUomExist(id)) { // check record existence
			// invoke delete method
			try {
				service.deleteUom(id);
				msg = "Uom '" + id + "' is deleted!";
			} catch (DataIntegrityViolationException e) {
				msg = "Uom '" + id + "' can not be deleted! It is used by Part";
				e.printStackTrace();
			}
		} else {
			msg = "Uom '" + id + "' Not Existed!";
		}

		// display other records
		Page<Uom> page = service.getAllUoms(PageRequest.of(0, 3));
		model.addAttribute("page", page);

		// send confirmation message to UI
		model.addAttribute("message", msg);

		// Got to page
		return "UomData";
	}

	/*
	 * @GetMapping("/shipmenttype/all") public String deletePage() { return
	 * "delete"; }
	 */

	// 5.Edit form

	@GetMapping("edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<Uom> opt = service.getOneUom(id);
		if (opt.isPresent()) {
			Uom u = opt.get();
			page = "UomEdit";
			model.addAttribute("uom", u);
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 6.update method
	@PostMapping("/update")
	public String update(@ModelAttribute Uom uom, Model model) {
		String msg = null;
		service.updateUom(uom);
		msg = "Uom '" + uom.getId() + "' updated successfully..";
		model.addAttribute("message", msg);
		// display other records
		Page<Uom> page = service.getAllUoms(PageRequest.of(0, 3));
		model.addAttribute("page", page);
		// Go to page
		// return "UomData";
		return "UomData";

	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<Uom> opt = service.getOneUom(id);
		if (opt.isPresent()) {
			Uom um = opt.get();
			model.addAttribute("ob", um);
			page = "UomView";
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
		m.setView(new UomExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllUoms());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new UomExcelView());
		Optional<Uom> opt = service.getOneUom(id);
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
		m.setView(new UomPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllUoms());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new UomPdfView());
		Optional<Uom> opt = service.getOneUom(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}
}