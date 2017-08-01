package com.CmpBookResource.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	private BufferedWriter bufferedWriter;

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
		bufferedWriter = new BufferedWriter(fw);
		bufferedWriter.write(message + System.getProperty("line.separator"));

		bufferedWriter.close();
		fw.close();
	}

}
