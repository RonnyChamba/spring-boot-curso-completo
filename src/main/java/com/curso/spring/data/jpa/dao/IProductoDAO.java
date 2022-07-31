package com.curso.spring.data.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.curso.spring.data.jpa.entity.Producto;

public interface IProductoDAO extends  CrudRepository<Producto, Long>{


	// La consulta es a nivel del objeto, no de tabla, osea se hace referenci a alos nombres y campos de las clase java
	@Query("select p from Producto p  where p.nombre like %?1%")
	List<Producto>  buscarPorNombre(String nombre); 
	
	
}
