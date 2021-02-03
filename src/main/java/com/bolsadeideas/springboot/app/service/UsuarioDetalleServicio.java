package com.bolsadeideas.springboot.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.app.dao.IUsuarioRolesDao;
import com.bolsadeideas.springboot.app.entity.UsuarioRoles;

@Service("jdbcUserDetailsService")
public class UsuarioDetalleServicio implements UserDetailsService {

	@Autowired
	IUsuarioRolesDao usersDetails;
	
	private String nombre;
	private String password;
	private Boolean enabled; 
	
	private Logger logger = LoggerFactory.getLogger(UsuarioDetalleServicio.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UsuarioRoles> usuario = usersDetails.findByName(username);
		
		 if(usuario == null) {
	        	logger.error("Error en el Login: no existe el usuario '" + username);
	        	throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
	        }
		 
		 
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 
		 for (int i = 0; i < usuario.size(); i++) {
			 
			 if(i== 0) {
					nombre = usuario.get(i).getUsuarios().getUsername();
					password = usuario.get(i).getUsuarios().getPassword();
					enabled = usuario.get(i).getUsuarios().getEnabled();
				} 
			 
			 logger.info("Role: ".concat(usuario.get(i).getRoles().getAuthority()));
	        	authorities.add(new SimpleGrantedAuthority(usuario.get(i).getRoles().getAuthority()));
			 	 
		 }
		 
		 if(authorities.isEmpty()) {
	        	logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
	        	throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
	        }
		 
			return new User(nombre, password, enabled, true, true, true, authorities);
			
	        
	}

}
