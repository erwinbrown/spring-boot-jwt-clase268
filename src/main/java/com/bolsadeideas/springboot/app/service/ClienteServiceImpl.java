package com.bolsadeideas.springboot.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.dao.IClienteDao;
import com.bolsadeideas.springboot.app.dao.IFacturaDao;
import com.bolsadeideas.springboot.app.dao.IProductoDao;
import com.bolsadeideas.springboot.app.entity.Cliente;
import com.bolsadeideas.springboot.app.entity.Factura;
import com.bolsadeideas.springboot.app.entity.Producto;


@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		
		return (List<Cliente>) clienteDao.findAll();
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		
		return clienteDao.findAll(pageable);
	}

	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		
		clienteDao.save(cliente);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		
		 log.info("cantidad: " + term); 
		 
		return productoDao.findByNombre("%" + term + "%");
		
	}

	@Override
	@Transactional
	public void saveFactura(Factura fact) {
		
		facturaDao.save(fact);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		
		return facturaDao.findById(id).orElse(null);
		
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
			
		 
			facturaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Factura detalleCompletoFactura(Long id) {
		return facturaDao.fetchByIdFacturasClientesItemsProductos(id);
	}

	

}
