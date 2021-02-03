package com.bolsadeideas.springboot.app.controllers;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolsadeideas.springboot.app.entity.Factura;
import com.bolsadeideas.springboot.app.service.ClienteService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/report")
public class ReportController {
	
	  @Autowired
	  private DataSource dataSource;
	   
	  @Autowired
	  private ClienteService dataService;
      
	   private List<String> respuesta = new ArrayList<String>();
	   
	   private final static String savedir = "C://erwincosas//cursoSpring5//reportes";
	   private final static String sourcedir = "C://erwincosas//cursoSpring5//archivos";
	
	   private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	@GetMapping("/view/{idfact}")
	public String reportVisor(@PathVariable(value = "idfact") Long idfact, Map<String, Object> model) {

		model.put("idfactura", idfact);
		
		return "/factura/report";

	}
	
	@GetMapping(path = "/loadreport/{numfact}", produces = { "application/json" })
	public @ResponseBody List<String> crearReporte(@PathVariable(value = "numfact") Long numfact) throws FileNotFoundException, JRException {
		
	   String namereport = "FacturaReporte";
	   
	       Date dNow = new Date( );
	      SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd");

	     String pdffile = "Report"+ numfact +"_".concat(ft.format(dNow));
	     
	     
	  
	   String repo = namereport.concat(".jrxml"); 
		String repopdf = pdffile.concat(".pdf"); 
		
	   
		            Factura factura = dataService.findFacturaById(numfact);
		            
		          
		            
		            if (factura == null) {
		            	
		            	
		            	respuesta.add(0, "400");
						respuesta.add(1, "No existe ese numero de factura!");
						return respuesta;
		            	
		            	
		            }
		            
		     try {
		            File fjrxml = ResourceUtils.getFile(sourcedir + "//" + repo);
		            
		            JasperReport reporteCompilado = JasperCompileManager.compileReport(fjrxml.getAbsolutePath());
		            
		            Map<String, Object> parameters = new HashMap<String, Object>();
		            
		        	parameters.put("facturaId", factura.getId().intValue());
		        	
					parameters.put("ParamTitle", factura.getCliente().getApellido().concat(" ")
							                             .concat(factura.getCliente().getNombre()));
					
					parameters.put("ParamOther", factura.getDescripcion());
		            
					
					JasperPrint jasperPrint = JasperFillManager.fillReport(reporteCompilado, parameters,
							dataSource.getConnection());
		         
					  
					JasperExportManager.exportReportToPdfFile(jasperPrint, savedir + "//" + repopdf);
		            
					Path pathReport = Paths.get(savedir + "//" + repopdf);
					
					
					if (pathReport.toFile().exists()) {
						
						respuesta.add(0, "200");
						respuesta.add(1, namereport);
						respuesta.add(2, repopdf);
						return respuesta;
						
						
					} else {
						
						respuesta.add(0, "400");
						respuesta.add(1, "Error");
						log.info("No se creo el reporte");
						return respuesta;

					
					
					}
		            
		            
		            
		         } catch (Exception e) {
		 			System.out.println("Exception: " + e.getMessage());
		 			return null;
		 		}
		
	
		
		
	}
	
	
	

}
