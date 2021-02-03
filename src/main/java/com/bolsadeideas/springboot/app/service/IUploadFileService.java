package com.bolsadeideas.springboot.app.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	
	public String copyFile(MultipartFile file) throws IOException;
	
	public boolean deleteFile(String filename);
	
	public Path getPath(String filename);

}
