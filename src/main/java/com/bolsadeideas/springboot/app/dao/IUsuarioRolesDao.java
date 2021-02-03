package com.bolsadeideas.springboot.app.dao;

import java.util.List;

import com.bolsadeideas.springboot.app.entity.UsuarioRoles;


public interface IUsuarioRolesDao {

	public List<UsuarioRoles> findAll();
	
	public List<UsuarioRoles> findByName(String name);
	
}
