package com.curso.spring.data.jpa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.data.jpa.dao.IClienteDao;
import com.curso.spring.data.jpa.entity.Cliente;


@Service
public class ClienteServiceImpl  implements IClienteService{

	
	@Autowired
	private IClienteDao clienteRepository;
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Cliente> findAll() {
		
		return  clienteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> findOne(Long id) {
	
		return  clienteRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		
		return clienteRepository.findAll(pageable);
	}

}
