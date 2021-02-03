package com.bolsadeideas.springboot.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UpLoadServiceImpl implements IUploadFileService {

	private final static String UPLOADS_FOLDER = "C://erwincosas//cursoSpring5//archivos";

	@Override
	public String copyFile(MultipartFile file) throws IOException {

		String fotoNombre = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		Path pathFoto = getPath(fotoNombre);

		Files.copy(file.getInputStream(), pathFoto);

		return fotoNombre;

	}

	@Override
	public boolean deleteFile(String filename) {
		
		Path pathFoto  = getPath(filename);
		
		if(pathFoto.toFile().exists() && pathFoto.toFile().canRead()) {
			
			  pathFoto.toFile().delete();
			  
			  return true;
			  
			} else {
				
				return false;
				
			}
		
		
	}

	@Override
	public Path getPath(String filename) {

		return Paths.get(UPLOADS_FOLDER + "//" + filename);
	}

}
