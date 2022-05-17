package com.curso.spring.data.jpa.service;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.spring.data.jpa.entity.Cliente;

public interface IClienteService {

	void save(Cliente cliente);

	Iterable<Cliente> findAll();

	Optional<Cliente> findOne(Long id);

	void delete(Long id);
	
	Page<Cliente> findAll(Pageable pageable);
}
