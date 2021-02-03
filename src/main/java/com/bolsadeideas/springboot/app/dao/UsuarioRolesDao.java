package com.bolsadeideas.springboot.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.entity.UsuarioRoles;
import com.bolsadeideas.springboot.app.entity.UsuarioRolesRowMapper;

@Repository
public class UsuarioRolesDao implements IUsuarioRolesDao {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<UsuarioRoles> findAll() {
		
	String sqli	= "select u.username, u.password, u.enabled, a.authority " 
		           + "from users u JOIN authorities a ON u.id = a.user_id";
	
	  RowMapper<UsuarioRoles> rowMapper = new UsuarioRolesRowMapper();
	
	  List<UsuarioRoles> lista = jdbcTemplate.query(sqli, rowMapper);
	
		return lista;
		
		
	}
	
	public List<UsuarioRoles> findByName(String name) {
		

		RowMapper<UsuarioRoles> rowMapper = new UsuarioRolesRowMapper();
		  
		 List<UsuarioRoles> lista  = jdbcTemplate.query("select u.username, u.password, u.enabled, a.authority from users u JOIN authorities a ON u.id = a.user_id where u.username = ?",
		   rowMapper, name);  
		  
		
		
		return lista;
		
	}
	
	
	
}
