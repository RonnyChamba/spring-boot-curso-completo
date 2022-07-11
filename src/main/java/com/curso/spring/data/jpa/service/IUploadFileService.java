package com.curso.spring.data.jpa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {
	
	String save(MultipartFile namePhoto) throws IOException;
	
	boolean delete(String namePhoto);
	
}
