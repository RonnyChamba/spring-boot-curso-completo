package com.curso.spring.data.jpa.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.spring.data.jpa.entity.Cliente;
import com.curso.spring.data.jpa.entity.Factura;
import com.curso.spring.data.jpa.entity.ItemFactura;
import com.curso.spring.data.jpa.entity.Producto;
import com.curso.spring.data.jpa.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/form/{clienteId}")
	public String create(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {

		Optional<Cliente> clienteOptional = clienteService.findOne(clienteId);

		if (clienteOptional.isEmpty()) {

			flash.addFlashAttribute("error", "Cliente no registrado en la Base de Datos");

			return "redirect:/listar";

		}

		Factura factura = new Factura();

		factura.setCliente(clienteOptional.get());

		model.addAttribute("factura", factura);
		model.addAttribute("title", "Crear factura");

		return "factura/form";
	}

	@GetMapping(value = "/cargar-productos/{nombre}", produces = { "application/json" })
	public @ResponseBody List<Producto> buscarProducto(@PathVariable String nombre) {

		/*
		 * @ResponseBody, suprime el cargar una vista de thymeleaf, y lo qe hara es
		 * transforma la salida en eun json, eso lo poblara dentro del body de la
		 * respuesta
		 */
		return clienteService.findByNombre(nombre);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult bindingResult ,  @RequestParam(name="item_id[]", required = false) Long[] itemsPro, 
			@RequestParam( name="cantidad[]", required = false) Integer[] cantidad, 
			SessionStatus status,
			RedirectAttributes flash,
			Model model) {

		
		
		/**
		 * Observacion sobre los parametros tipo arreglo recibidos 
		 * Cuando teneemos en un formulario varios input con el mismo valor en su atributo 'name', a lo que
		 * se envia el formulario, esos paramtros se enviaran contenido en un arreglo.
		 * 
		 */
		
		
		
		if (bindingResult.hasErrors()) {
			
			model.addAttribute("title", "Crear factura");
			
			return "factura/form";
			
		}
		
		if (itemsPro == null || itemsPro.length <1) {
			model.addAttribute("title", "Crear factura");
			model.addAttribute("info", "La factura no puede estar vacia, agrega productos");
			return "factura/form";
		}
		
		
		for (int i = 0; i < itemsPro.length; i++) {

			Optional<Producto> prodOptional = clienteService.findProductoById(itemsPro[i]);

			if (prodOptional.isPresent()) {

				ItemFactura itemFactura = new ItemFactura();
				itemFactura.setProducto(prodOptional.get());
				itemFactura.setCantidad(cantidad[i]);
				//Agregar a la factura la linea de factura
				factura.addItem(itemFactura);
			}

		}
		// Peristir la factura y sus items 
		clienteService.saveFactura(factura);
		
		// Completar la sesion del atributo factura
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con exíto");

		return "redirect:/ver/"+ factura.getCliente().getId();
	}

}
