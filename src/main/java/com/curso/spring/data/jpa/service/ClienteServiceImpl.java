package com.curso.spring.data.jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.data.jpa.dao.IClienteDao;
import com.curso.spring.data.jpa.dao.IFacturaRepository;
import com.curso.spring.data.jpa.dao.IProductoDAO;
import com.curso.spring.data.jpa.entity.Cliente;
import com.curso.spring.data.jpa.entity.Factura;
import com.curso.spring.data.jpa.entity.Producto;


@Service
public class ClienteServiceImpl  implements IClienteService{

	
	@Autowired
	private IClienteDao clienteRepository;
	
	@Autowired
	private IProductoDAO productoDAO;
	
	@Autowired
	private IFacturaRepository facturaRepository;
	
	
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

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String nombre) {
			
	//	return  productoDAO.buscarPorNombre(name);
		
		return  productoDAO.buscarPorNombre(nombre);
	}

	@Transactional
	@Override
	public void saveFactura(Factura factura) {
		 facturaRepository.save(factura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findProductoById(Long id) {
	
		return productoDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Factura> findFacturaById(Long id) {
	
		return  facturaRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaRepository.deleteById(id);
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Factura> findByIdWithClienteWithItemWithProducto(Long id) {
		
		Factura factura = facturaRepository.findByIdWithClienteWithItemWithProducto(id);
		//return  facturaRepository.findByIdWithClienteWithItemWithProducto(id);
	
		return  Optional.ofNullable(factura);
	}

}
