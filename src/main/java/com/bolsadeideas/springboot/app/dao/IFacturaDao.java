package com.bolsadeideas.springboot.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
	
@Query(value = "select * from "
		+          "facturas f LEFT JOIN clientes c on f.cliente_id = c.id "
		+ "LEFT JOIN items_facturas i on f.id = i.factura_id "
		+ "LEFT JOIN productos p on i.producto_id = p.id "
		+ "where f.id = ?1", nativeQuery = true)
  public Factura fetchByIdFacturasClientesItemsProductos(Long id);

}
