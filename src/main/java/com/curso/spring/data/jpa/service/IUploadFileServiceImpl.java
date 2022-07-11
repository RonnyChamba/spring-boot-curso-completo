package com.curso.spring.data.jpa.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.curso.spring.data.jpa.utils.UtilsName;

@Service
public class IUploadFileServiceImpl implements IUploadFileService {

	// guardar en directorio externo al proyecto
	private final String rootPath = "D://imgSpring//uploads";

	private static final Logger LOGGER = LogManager.getLogger(IUploadFileServiceImpl.class);

	@Override
	public String  save(MultipartFile photo) throws IOException {

		String namePhotoFinal = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename().toString();

		byte[] bytes = photo.getBytes();

		Path rutaCompleta = Paths.get(String.format("%s\\%s", rootPath, namePhotoFinal));

		Files.write(rutaCompleta, bytes);

		return namePhotoFinal;
	}

	@Override
	public boolean delete(String namePhoto) {
	
		if (!namePhoto.equals( UtilsName.FOTO_DEFAULT)) {
			
			
			Path pathAbsolute = Paths.get(rootPath).resolve(namePhoto).toAbsolutePath();

			File imgFile = pathAbsolute.toFile();

			if (imgFile.exists() && imgFile.canRead()) {

				return imgFile.delete();
			}
		
			return false;
			
		}
		
		return true;
		
	}

}
