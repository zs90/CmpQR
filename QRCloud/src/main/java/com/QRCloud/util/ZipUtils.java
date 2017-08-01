package com.QRCloud.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩打包文件工具类，主要负责将文件打包成zip格式，用于批量下载功能
 * 
 * @author Shane
 *
 */
public class ZipUtils {
	private List<String> fileList;
	private String OUTPUT_ZIP_FILE;
	private String SOURCE_FOLDER;

	public ZipUtils(String out, String in) {
		fileList = new ArrayList<String>();
		this.OUTPUT_ZIP_FILE = out;
		this.SOURCE_FOLDER = in;
	}

	public void zipIt(String zipFile) {
		byte[] buffer = new byte[1024];
		String source = "";
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			try {
				source = SOURCE_FOLDER.substring(SOURCE_FOLDER.lastIndexOf("/") + 1, SOURCE_FOLDER.length());
			} catch (Exception e) {
				source = SOURCE_FOLDER;
			}
			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			FileInputStream in = null;

			for (String file : this.fileList) {
				ZipEntry ze = new ZipEntry(source + File.separator + file);
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					in.close();
				}
			}

			zos.closeEntry();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void generateFileList(File node) {
		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.toString()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}
	}

	private String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length() + 1, file.length());
	}
}
