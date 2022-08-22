package com.curso.spring.data.jpa;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	/**
	 * Principal es una interface propia de Spring Security
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping("/login")
	public String login(Model model, Principal principal, RedirectAttributes flash) {
		
		
		if (principal !=null) {
			
			flash.addFlashAttribute("info", "Ya has iniciado sesion anteriormente");
			return "redirec:/";
		}
		
		
		
		
		return "login";
	}

}
