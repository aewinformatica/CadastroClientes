package br.com.sapejtb.sapejtb_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class DashboardController {
	
	@GetMapping("/")
	ModelAndView home(){
		ModelAndView mv = new ModelAndView("Dashboard");
		
		return mv;
		
	}

}
