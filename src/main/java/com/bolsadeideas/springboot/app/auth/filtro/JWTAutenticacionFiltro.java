package com.bolsadeideas.springboot.app.auth.filtro;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bolsadeideas.springboot.app.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTAutenticacionFiltro extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	 
	public JWTAutenticacionFiltro(AuthenticationManager authManager) {
		
		this.authManager = authManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		
		
		if(username != null && password != null ) {
			
			logger.info("Username from request form data:" + username );
			logger.info("Password from request form data:" + password );
			
		} else {
			Usuario user = null;
			
			try {
				
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username from request raw data:" + username );
				logger.info("Password from request raw data:" + password );
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			
		}
		
		username = username.trim();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
	
		return authManager.authenticate(authToken);
		
	}
	

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		
		String username = ((User)authResult.getPrincipal()).getUsername();
		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
		Claims claims = Jwts.claims();
		claims.put("Authorities", new ObjectMapper().writeValueAsString(roles));
		
		/*SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); se genera automaticamente*/
		SecretKey secretKey = Keys.hmacShaKeyFor("/\\^v~5gvtj/\\5dk\"k.G<J8zULM6E@UY6CJ:C_@Wd@v>Tq{dz,Kbd[sXBUm7JWd`'E$ar4NvS8}<,=-^Yq=3`c~xWb*,P&)nbEns@".getBytes());
	
		String token = Jwts.builder()
				       .setClaims(claims)
				       .setSubject(username)
				       .signWith(secretKey)
				       .setIssuedAt(new Date())
				       .setExpiration(new Date(System.currentTimeMillis() + 14000000L))
				       .compact();
		response.addHeader("Authorization", "Bearer " + token);
		
		Map<String, Object> body = new HashMap<String, Object>();
		
		body.put("token", token);
		body.put("user", (User)authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s Inicio Sessión", username));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
		
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, Object> body = new HashMap<String, Object>();
		
		body.put("mensaje", "Incorrecto el Usuario o Clave, intente de nuevo.");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
		
	}
	
	

}
