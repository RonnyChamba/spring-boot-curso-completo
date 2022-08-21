package com.curso.spring.data.jpa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.curso.spring.data.jpa.entity.Factura;

public interface IFacturaRepository  extends CrudRepository<Factura, Long>{
	
	
	/*
	 * join fetch  se tranforma en un inner join
	 * podemos utilizar left join fetch = left inner join 
	 *  
	 */
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id =?1")
	Factura  findByIdWithClienteWithItemWithProducto(Long id);

}
