package com.bolsadeideas.springboot.app.auth.filtro;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTAutorizacionFiltro extends BasicAuthenticationFilter {

	public static final SecretKey secretKey = Keys.hmacShaKeyFor(
			"/\\^v~5gvtj/\\5dk\"k.G<J8zULM6E@UY6CJ:C_@Wd@v>Tq{dz,Kbd[sXBUm7JWd`'E$ar4NvS8}<,=-^Yq=3`c~xWb*,P&)nbEns@"
					.getBytes());

	public JWTAutorizacionFiltro(AuthenticationManager authenticationManager) {
		super(authenticationManager);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (!requiereAuthentication(header)) {

			chain.doFilter(request, response);
			return;

		}

		boolean validToken;

		Claims token = null;

		try {

			token = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(header.replace("Bearer ", ""))
					.getBody();

			validToken = true;

		} catch (JwtException | IllegalArgumentException e) {

			validToken = false;

		}

		UsernamePasswordAuthenticationToken authentication = null;

		if (validToken) {

			String username = token.getSubject();

			Object roles = token.get("authorities");

			Collection<? extends GrantedAuthority> authorities = Arrays

					.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)

							.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);

	}

	protected boolean requiereAuthentication(String header) {

		if (header == null || header.startsWith("Bearer ")) {
			return false;
		}
		return true;

	}

}
