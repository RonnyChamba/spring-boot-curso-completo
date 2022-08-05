package com.curso.spring.data.jpa.dao;

import org.springframework.data.repository.CrudRepository;

import com.curso.spring.data.jpa.entity.Factura;

public interface IFacturaRepository  extends CrudRepository<Factura, Long>{

}
