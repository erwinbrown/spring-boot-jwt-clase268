package com.bolsadeideas.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.entity.Cliente;
import com.bolsadeideas.springboot.app.entity.Factura;
import com.bolsadeideas.springboot.app.entity.ItemFactura;
import com.bolsadeideas.springboot.app.entity.Producto;
import com.bolsadeideas.springboot.app.service.ClienteService;
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/verfact/{facturaid}")
	public String verfactura(@PathVariable(value = "facturaid") Long facturaid, Model model, RedirectAttributes flash) {

		Factura fact = clienteService.detalleCompletoFactura(facturaid);
				
				/*clienteService.findFacturaById(facturaid);*/

		if (fact == null) {

			flash.addFlashAttribute("error", "El número de factura no existe!");

			return "redirect:/listar";

		}

		model.addAttribute("factura", fact);
		model.addAttribute("titulo", "Factura: ".concat(fact.getDescripcion()));

		return "/factura/verfact";

	}

	@GetMapping("/form/{clienteId}")
	public String crearFactura(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> mod,
			RedirectAttributes flash) {

		if (clienteId > 0) {

			Cliente client = clienteService.findOne(clienteId);

			Factura fact = new Factura();

			fact.setCliente(client);

			mod.put("factura", fact);

			if (client == null) {

				flash.addFlashAttribute("error", "Cliente no existe en la base de datos!");

				return "redirect:/listar";

			}

		}

		mod.put("titulo", "Crear Factura");
		return "/factura/form";

	}

	@GetMapping(path = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {

		return clienteService.findByNombre(term);

	}

	@PostMapping("/form")
	public String guardarFact(@Valid Factura factura, BindingResult result, Model mod,
			@RequestParam(name = "item_id[]", required = false) Long[] itemid,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {

			mod.addAttribute("titulo", "Crear Nueva Factura");
			return "/factura/form";

		}

		if (itemid == null || itemid.length == 0) {

			mod.addAttribute("titulo", "Crear Nueva Factura");
			mod.addAttribute("error", "La factura requiere de algún producto o servicio seleccionado");
			return "/factura/form";

		}

		for (int z = 0; z < itemid.length; z++) {

			Producto prod = clienteService.findProductoById(itemid[z]);

			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[z]);
			linea.setProducto(prod);
			factura.addItemFactura(linea);

		}

		clienteService.saveFactura(factura);

		status.setComplete();

		flash.addFlashAttribute("success", "Se guardo la factura con éxito!");

		return "redirect:/ver/" + factura.getCliente().getId();
	}

	@GetMapping("/eliminar/{id}")
	public String borrarFactura(@PathVariable(value ="id" ) Long id, RedirectAttributes flash) {

		Factura factu = clienteService.findFacturaById(id);

		if (factu != null) {
			clienteService.deleteFacturaById(id);

			flash.addFlashAttribute("success", "Se elimino factura con éxito");

			return "redirect:/ver/" + factu.getCliente().getId();

		}

		flash.addFlashAttribute("error", "La factura no existe en la base de datos!");

		return "redirect:/listar";

	}

}
