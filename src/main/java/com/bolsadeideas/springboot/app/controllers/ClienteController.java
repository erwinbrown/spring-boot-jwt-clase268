package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.entity.Cliente;
import com.bolsadeideas.springboot.app.service.ClienteService;
import com.bolsadeideas.springboot.app.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.PageRender;
import com.bolsadeideas.springboot.app.view.xml.ClienteListXml;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@InitBinder
	public void inicializador(WebDataBinder binder) {

		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dataFormat, true);
		binder.registerCustomEditor(Date.class, editor);

	}

	@Autowired
	private ClienteService clienteService;
	@Autowired
	private IUploadFileService uploadService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
	
	

		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}
	
	@GetMapping(value = "/listar-rest")
	public @ResponseBody ClienteListXml listarRest() {
		return new ClienteListXml(clienteService.findAll());
	}

	@RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
	public String listarCliente(@RequestParam(name = "page", defaultValue = "0") int page, Model mod) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(hasRoles("ROLE_ADMIN")) {
			log.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			log.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);

		mod.addAttribute("titulo", "Listado de clientes");
		mod.addAttribute("clientes", clientes);
		mod.addAttribute("page", pageRender);
		return "listar";

	}
    
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Registro de Cliente");

		return "/form";

	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String Guardar(@Valid Cliente cliente, BindingResult result, Model mod,@RequestParam("file") MultipartFile foto, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {

			mod.addAttribute("titulo", "Error en los datos del formulario");

			return "/form";
		}
		
		if (!foto.isEmpty()) {
			
			try {
				
				String nameFoto = uploadService.copyFile(foto);
				
				flash.addFlashAttribute("info", "Has subido correctamente '" + nameFoto + "'");

				cliente.setFoto(nameFoto);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";

		clienteService.save(cliente);

		status.setComplete();

		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/listar";

	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = new Cliente();
		
		if (id > 0) {
			
			
			cliente = clienteService.findOne(id);

			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/listar";
			}

		} else {

			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}

		model.put("cliente", cliente);
		model.put("titulo", "Editar Registro de Cliente");

		return "form";

	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			
			Cliente cliente = clienteService.findOne(id);
			
						
			if (uploadService.deleteFile(cliente.getFoto())) {
				
				flash.addFlashAttribute("info", "Imagen: " + cliente.getFoto() + " eliminada con exito!");
			
			}
			 
			
			clienteService.delete(id);
   
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		}

		return "redirect:/listar";

	}
	
private boolean hasRoles(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		/*return authorities.contains(new SimpleGrantedAuthority(role));*/
		
		
		  for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				log.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		
		return false;
	
		
	}

}
