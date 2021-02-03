package com.bolsadeideas.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.entity.Cliente;
import com.bolsadeideas.springboot.app.entity.Factura;
import com.bolsadeideas.springboot.app.entity.Producto;

public interface ClienteService {
	
	
   public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura fact);
	
	public Producto findProductoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFacturaById(Long id);
	
	public Factura detalleCompletoFactura(Long id);

}
