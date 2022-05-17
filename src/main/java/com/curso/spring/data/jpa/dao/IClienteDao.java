package com.curso.spring.data.jpa.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.curso.spring.data.jpa.entity.Cliente;

public interface IClienteDao  extends PagingAndSortingRepository<Cliente, Long>{	
	
}


