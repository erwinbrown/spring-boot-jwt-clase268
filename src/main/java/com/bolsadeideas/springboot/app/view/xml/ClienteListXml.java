package com.bolsadeideas.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bolsadeideas.springboot.app.entity.Cliente;


@XmlRootElement(name = "clientes")
public class ClienteListXml {
     
	@XmlElement(name="cliente")
	private List<Cliente> clientes;
	
	
	public ClienteListXml() {

	}

	public ClienteListXml(List<Cliente> cliente) {

		this.clientes = cliente;
	}

	public List<Cliente> getCliente() {
		return clientes;
	}

	
	
	

}
