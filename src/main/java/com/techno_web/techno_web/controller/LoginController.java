package com.techno_web.techno_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping(path = {"/", "/loginPage"})
	public String getLoginPage(Model model)
	{
		return "login";
	}
	
	

}
