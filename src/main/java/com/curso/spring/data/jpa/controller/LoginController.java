package com.curso.spring.data.jpa.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	/**
	 * Principal es una interface propia de Spring Security
	 * @param error: parametro que envia automaticamente Spring security, cuando la autenticacion en incorrecta, osea las
	 * credencias son incorrectas
	 * @param model
	 * @param principal
	 * @param flash
	 * @return
	 */
	@GetMapping("/login")
	public String login(@RequestParam(value = "error" , required = false)  String error, Model model, Principal principal, RedirectAttributes flash) {

		if (principal != null) {

			flash.addFlashAttribute("info", "Ya has iniciado sesion anteriormente");
			return "redirect:/";
		}
		model.addAttribute("title", "Inicio de Sesion");
		
		if (error  !=null) {
			
			model.addAttribute("error", "Usuario o contraseña incorrecto");
		}
		return "login";
	}

}
