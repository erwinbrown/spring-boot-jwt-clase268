package com.bolsadeideas.springboot.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.bolsadeideas.springboot.app.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {
	
	@Query(value = "select * from productos where nombre like ?1", nativeQuery = true)
	public List<Producto> findByNombre(String term);
	
	/* @Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term); */
	
	/*public List<Producto> findByNombreLikeIgnoreCase(String term);*/
	

	
}
