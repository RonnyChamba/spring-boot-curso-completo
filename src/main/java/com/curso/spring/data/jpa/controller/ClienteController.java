package com.curso.spring.data.jpa.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.spring.data.jpa.entity.Cliente;
import com.curso.spring.data.jpa.service.IClienteService;
import com.curso.spring.data.jpa.util.paginator.PageRender;

@Controller

/*
 * Guardando atributos a@SessionAttributes evitamos tener que tener campos hidden en la vistas
 * donde por lo general guardamos los id.
 */
@SessionAttributes("cliente")
public class ClienteController {
	@Autowired
	private IClienteService clienteService;

	private static final Logger LOGGER = LogManager.getLogger(ClienteController.class);
	
	@GetMapping({ "/listar", "/" })
	public String listar( @RequestParam(defaultValue = "0") int page,  Model model) {
		LOGGER.info(" info():  En el metodo listar()");	
		
		int cantidadRegistroPorPgina = 5; 
		Pageable pageableRequest = PageRequest.of(page, cantidadRegistroPorPgina);
		
		Page<Cliente> clientes = clienteService.findAll(pageableRequest);
		//Iterable<Cliente> clientes = clienteService.findAll();
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("listClientes", clientes);
		model.addAttribute("title", "Lista de Clientes");
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@ModelAttribute(name = "title")
	public String titulo() {

		return "App Data JPA";
	}

	@GetMapping("/form")
	public String crear(Model model) {
		
		LOGGER.info("En el metodo crear()");
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("title", "Formulario Cliente");
		
		return "form";
	}

	@GetMapping("/form/{id}")
	public String editrar(@PathVariable String id, Model model, RedirectAttributes flash) {

		LOGGER.info("Cliente a editar : {}", id);
		if (id.matches("\\d+") && Long.parseLong(id) > 0) {

			Long idCliente = Long.parseLong(id);
			Optional<Cliente> optionalCliente = clienteService.findOne(idCliente);

			if (optionalCliente.isPresent()) {

				Cliente cliente = optionalCliente.get();
				model.addAttribute("title", "Formulario Editar Cliente");
				model.addAttribute("cliente", cliente);
				return "form";

			} else
				flash.addFlashAttribute("warning", String.format("Cliente con id %s no esta registrado", id));

		} else
			flash.addFlashAttribute("warning", String.format("El id %s ingresado incorrecto", id));
		return "redirect:/";
	}

	@PostMapping("/form")
	/**
	 * El objeto BindingResult siempre va despues del objeto que se esta validando
	 * 
	 * @param cliente
	 * @param bindingResult
	 * @return
	 */
	public String procesar(@Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes flash, SessionStatus sessionStatus) {

		
		if (bindingResult.hasErrors()) {
			/**
			 * El cliente se pasa automatico, siempre y cuando el objeto que se envia
			 * 'cliente' y el nombre de la clase se llaman iguales , 'Cliente'
			 */
			return "form";

		}
		
		
		if (cliente.getId()==null) {
			flash.addFlashAttribute("success", "Cliente creado correctamente");
		}else flash.addFlashAttribute("success", "Cliente actualizado correctamente");
		clienteService.save(cliente);
		
		/*
		 * Finalizamos los atribuos guardados en el  @SessionAttributes("cliente"),eliminamos los atrinbutos 
		 * guardado alli
		 * */
		sessionStatus.setComplete();
		
		return "redirect:/listar";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id, RedirectAttributes flash) {

		LOGGER.info("Cliente eliminar : {}", id);
		if (id.matches("\\d+") && Long.parseLong(id) > 0) {

			Long idCliente = Long.parseLong(id);
			Optional<Cliente> optionalCliente = clienteService.findOne(idCliente);

			if (optionalCliente.isPresent()) {
				clienteService.delete(optionalCliente.get().getId());
				flash.addFlashAttribute("success", "Cliente Eliminado correctamente");			
			} else
				flash.addFlashAttribute("warning", String.format("Cliente con id %s no esta registrado", idCliente));

		} else
			flash.addFlashAttribute("warning", String.format("El id %s ingresado incorrecto", id));
		
		return "redirect:/";
	}
}
