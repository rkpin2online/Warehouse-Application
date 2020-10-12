package com.rk.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rk.model.User;
import com.rk.service.IUserService;

@Controller
@RequestMapping("/user")
@SessionAttributes("userOb")
public class UserController {
	@Autowired
	private IUserService service; //HAS-A
	
	/**
	 * 1. On Enter /register URL
	 *    Display UserRegister.html
	 *    Page in browser
	 */
	@GetMapping("/register")
	public String showReg(Model model) {
		model.addAttribute("user", new User());
		return "UserRegister";
	}
	
	/**
	 * 2. On click Submit(Register),
	 *    Read Form (ModelAttribute)
	 *    Save in Database using Service
	 *    Return Message to UI
	 *    URL :  /save + POST     
	 */
	@PostMapping("/save")
	public String saveUser(
			@ModelAttribute User user,
			Model model) 
	{
		Integer id = service.saveUser(user);
		String message = "User '"+id+"' saved";
		model.addAttribute("user", new User());
		model.addAttribute("message", message);
		//also send email with password (text:name,roles,password)
		return "UserRegister";
	}
	
	/***
	 * 3. Fetch all users from DB
	 */
	@GetMapping("/all")
	public String getAllUsers(Model model)
	{
		List<User> list = service.getAllUsers();
		model.addAttribute("list", list);
		return "UserData";
	}
	//for status update
	//activate
	@GetMapping("/activate/{id}")
	public String activateUser(@PathVariable Integer id)
	{
		service.updateUserStatus(1, id);
		return "redirect:../all";
	}
	//inactive
	@GetMapping("/inactive/{id}")
	public String deActivateUser(@PathVariable Integer id)
	{
		service.updateUserStatus(0, id);
		return "redirect:../all";
	}
	
	@GetMapping("/login")
	public String showLogin() {
		return "UserLogin";
	}
	
	@GetMapping("/setup")
	public String doSetup(Principal p,Model model,HttpSession session) 
	{
		String email = p.getName();
		System.out.println("--------->"+email);
		User user= service.findByEmail(email).get();
		model.addAttribute("userOb",user);
		//session.setMaxInactiveInterval(30);
		return "redirect:../uom/all";
	}
	
}