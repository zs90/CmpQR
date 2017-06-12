package com.CmpBookResource.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmpLogger {
	private File file;
	private String filePrePath;
	private BufferedWriter bufferedWriter;

	@Autowired
	private ServletContext context;
	
	@PostConstruct
	public void init(){
		filePrePath = context.getRealPath("/") + "logs/";
		File path = new File(filePrePath);
		if(!path.exists()){
			path.mkdirs();
		}
	}
	
	public synchronized void LogInfo(String message, String fileName) throws IOException{
		file = new File(filePrePath + fileName);
		
		if(!file.exists()){
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		bufferedWriter = new BufferedWriter(fw);			
		bufferedWriter.write(message + System.getProperty("line.separator"));
		
		bufferedWriter.close();
		fw.close();
	}
	
}
