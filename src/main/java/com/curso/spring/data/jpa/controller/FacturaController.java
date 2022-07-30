package com.curso.spring.data.jpa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.spring.data.jpa.entity.Cliente;
import com.curso.spring.data.jpa.entity.Factura;
import com.curso.spring.data.jpa.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	
	
	@Autowired
	private IClienteService clienteService;
	
	
	
	
	@GetMapping("/form/{clienteId}")
	public String create (  @PathVariable Long  clienteId, Model model, RedirectAttributes flash) {
		
		
		Optional<Cliente> clienteOptional = clienteService.findOne(clienteId);
		
		if (clienteOptional.isEmpty()) {
			
			flash.addFlashAttribute("error", "Cliente no registrado en la Base de Datos");

			return "redirect:/listar";
			
		}
		
		
		Factura factura = new Factura();
		
		factura.setCliente( clienteOptional.get());
		
		model.addAttribute("factura", factura);
		model.addAttribute("title", "Crear factura");
		
		return "factura/form";
	}
	

}
