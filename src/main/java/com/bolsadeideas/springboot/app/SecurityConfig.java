package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.app.auth.filtro.JWTAutenticacionFiltro;
import com.bolsadeideas.springboot.app.auth.filtro.JWTAutorizacionFiltro;
import com.bolsadeideas.springboot.app.service.UsuarioDetalleServicio;


/*import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;*/



@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*@Autowired
	private LoginSuccessHandler successHandler;*/
	
	@Autowired
	private UsuarioDetalleServicio userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/index", "/css/**", "/js/**", "/imagen/**", "/listar**").permitAll()
		.anyRequest().authenticated()
		/*.and()
		    .formLogin()
		        .successHandler(successHandler)
		        .loginPage("/login")
		    .permitAll()
		.and()
		.logout().permitAll()
		.and()
		.headers().frameOptions().disable()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403")*/
		.and()
		.addFilter(new JWTAutenticacionFiltro(authenticationManager()))
		.addFilter(new JWTAutorizacionFiltro(authenticationManager()))
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Autowired
	public void configGlobal(AuthenticationManagerBuilder builder) throws Exception {
     
		
		builder.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		

	}

	

}
