package com.rk.controller;

import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rk.model.WhUserType;
import com.rk.service.IWhUserTypeService;
import com.rk.util.EmailUtil;
import com.rk.view.WhUserTypeExcelView;

@Controller // =Object + HTTP Request
@RequestMapping("/whusertype")
public class WhUserTypeController {
	@Autowired
	private IWhUserTypeService service;

	@Autowired
	private EmailUtil emailUtil;

	//1. Show Register Page
	/**
	 *  URL :/register, Type:GET 
	 *  Goto Page WhUserTypeRegister.html
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		//Form backing Object 
		model.addAttribute("whUserType", new WhUserType());
		return "WhUserTypeRegister";
	}


	//2. save : on click submit
	/**
	 * URL: /save, Type: POST
	 * Goto : WhUserTypeRegister
	 */
	@PostMapping("/save")
	public String save(
			//read from Data from UI(given by container)
			@ModelAttribute WhUserType whUserType,
			@RequestParam("fileOb") MultipartFile fileOb,
			Model model //to send data to UI
			)
	{
		//perform save operation
		Integer id=service.saveWhUserType(whUserType);

		//construct one message
		String message="WhUserType '"+id+"' saved successfully";

		//send email on save

		boolean flag=emailUtil.send(
				whUserType.getUserMail(), 
				"WELCOME", 
				"Hello User:"+whUserType.getUserCode() 
				+", You are type:"+whUserType.getUserIdType(),
				fileOb);
		System.out.println(flag);

		//if(flag) message+=", Email also sent!";
		//else message+=", Email is not sent!";

		//send message to UI
		model.addAttribute("message", message);
		//Form backing Object 
		model.addAttribute("whUserType", new WhUserType());
		//Goto Page
		return "WhUserTypeRegister";
	}

	//3. Display data
	@GetMapping("/all")
	public String fetchAll(Model model){
		List<WhUserType> list=service.getAllWhUserTypes();
		model.addAttribute("list", list);
		return "WhUserTypeData";
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
		String msg=null;
		if(service.isWhUserTypeExist(id)) {

			service.deleteWhUserType(id);
			msg="WhUserType '"+id+"' deleted!";

		}else {
			msg="WhUserType '"+id+"' Not exist!";
		}

		model.addAttribute("message", msg);
		//show other rows
		List<WhUserType> list=service.getAllWhUserTypes();
		model.addAttribute("list", list);

		return "WhUserTypeData";
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
		String page=null;
		Optional<WhUserType> opt=service.getOneWhUserType(id);

		if(opt.isPresent()) {
			WhUserType st=opt.get();
			model.addAttribute("whUserType", st);
			page="WhUserTypeEdit";
		}else {
			page="redirect:../all";
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
			@ModelAttribute WhUserType whUserType,
			Model model)
	{
		service.updateWhUserType(whUserType);
		String msg="WhUserType '"+whUserType.getId()+"' Updated!";
		model.addAttribute("message", msg);

		//new data from Db
		List<WhUserType> list=service.getAllWhUserTypes();
		model.addAttribute("list", list);
		return "WhUserTypeData";
	}

	//7. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView excelExport() {
		ModelAndView m=new ModelAndView();
		m.setView(new WhUserTypeExcelView());
		m.addObject("list",service.getAllWhUserTypes());
		return m;
	}

	//----------AJAX Validation--------------
	@GetMapping("/mailcheck")
	public @ResponseBody String validateEmail(@RequestParam String mail) 
	{
		String message="";
		if(service.isWhUserTypeEmailExist(mail)) {
			message = mail+" <b>already exist!</b>";
		}
		return message;
	}
}
