package com.bolsadeideas.springboot.app.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsuarioRolesRowMapper implements RowMapper<UsuarioRoles> {

	@Override
	public UsuarioRoles mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		   UsuarioRoles usuarioRoles = new UsuarioRoles();
		   Usuario usuario = new Usuario();
		   Role role = new Role();
		   
		   usuario.setUsername(rs.getString("username"));
		   usuario.setPassword(rs.getString("password"));
		   usuario.setEnabled(rs.getBoolean("enabled"));
		   
		   role.setAuthority(rs.getString("authority"));
		   
		   usuarioRoles.setUsuarios(usuario);
		   usuarioRoles.setRoles(role);
		   
		return  usuarioRoles;
	}

}
