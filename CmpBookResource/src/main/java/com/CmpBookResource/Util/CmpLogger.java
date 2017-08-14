package com.CmpBookResource.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CmpLogger类，用于记录访问日志
 * 
 * @author Shane
 * @version 1.0
 */
@Service
public class CmpLogger {
	private File file;
	private String filePrePath;

	@Autowired
	private ServletContext context;

	/**
	 * bean初始化时，建立相关文件夹
	 */
	@PostConstruct
	public void init() {
		filePrePath = context.getRealPath("/") + "logs/";
		File path = new File(filePrePath);
		if (!path.exists()) {
			path.mkdirs();
		}
	}

	/**
	 * 线程安全方法，用于记录访问日志
	 * 
	 * @param message
	 *            需要记录的字符串
	 * @param fileName
	 *            记录的文件
	 * @throws IOException
	 *             IO异常
	 */
	public synchronized void LogInfo(String message, String fileName) throws IOException {
		file = new File(filePrePath + fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fw);
		bufferedWriter.write(message + System.getProperty("line.separator"));

		bufferedWriter.close();
		fw.close();
	}
	
	/**
	 * 用于记录运行时异常 
	 * @param e 异常
	 * @param fileName 记录文件名
	 * @throws IOException IO异常
	 */
	public synchronized void LogException(Throwable e, String fileName) throws IOException{
		file = new File(filePrePath + fileName);
		
		if (!file.exists()) {
			file.createNewFile();
		}		
		
		Date date = new Date();
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		
		bufferedWriter.write("[ " + dateString + " ]" + System.getProperty("line.separator"));
		e.printStackTrace(printWriter);
		
		printWriter.close();
		bufferedWriter.close();
		fileWriter.close();
	}

}
