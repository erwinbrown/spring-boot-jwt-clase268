package com.bolsadeideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.app.service.ClienteService;
import com.bolsadeideas.springboot.app.view.xml.ClienteListXml;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "/listar")
	public ClienteListXml listar() {
		return new ClienteListXml(clienteService.findAll());
	}

}
