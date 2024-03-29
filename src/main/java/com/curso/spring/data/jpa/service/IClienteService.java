package com.curso.spring.data.jpa.service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.spring.data.jpa.entity.Cliente;
import com.curso.spring.data.jpa.entity.Factura;
import com.curso.spring.data.jpa.entity.Producto;

public interface IClienteService {

	void save(Cliente cliente);

	Iterable<Cliente> findAll();

	Optional<Cliente> findOne(Long id);

	void delete(Long id);
	
	Page<Cliente> findAll(Pageable pageable);
	
	List<Producto> findByNombre(String name);
	
	void saveFactura(Factura factura);
	
	Optional<Producto> findProductoById(Long id);
	
	Optional<Factura> findFacturaById(Long id);
	
	Optional<Factura> findByIdWithClienteWithItemWithProducto(Long id);
	
	void deleteFactura(Long id);
	
	
}

