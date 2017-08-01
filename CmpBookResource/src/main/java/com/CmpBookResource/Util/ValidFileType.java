package com.CmpBookResource.Util;

import java.util.HashSet;

import org.springframework.stereotype.Service;

/**
 * 检查item的资源类型
 * @author Shane
 * @version 1.0
 */
@Service
public class ValidFileType {
	HashSet<String> showSet = new HashSet<>();	
	HashSet<String> linkSet = new HashSet<>();
	
	/**
	 * 初始化语句块，初始化所有的类型参数。
	 * mp4 mp3 gif jpg gif png bmp pdf都是可在线预览的资源，它们也可以下载。
	 * paper web是链接类型资源，只能在线预览不能下载。
	 * 剩下所有资源都可以下载，也就是说zip，docx等其他未覆盖到的类型的文件都默认支持下载，但不支持在线预览。
	 */
	{
		showSet.add("mp4");
		showSet.add("mp3");
		showSet.add("jpg");
		showSet.add("gif");
		showSet.add("png");
		showSet.add("bmp");
		showSet.add("paper");
		showSet.add("web");
		showSet.add("pdf");
		linkSet.add("paper");
		linkSet.add("web");
	}
	
	/**
	 * 判断该item类型是否可预览
	 * @param fileType 文件后缀
	 * @return 可预览就返回true，否则返回false
	 */
	public boolean isShowable(String fileType){
		fileType = fileType.toLowerCase();
		if(showSet.contains(fileType)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断item类型是否为链接类型
	 * @param fileType 文件后缀
	 * @return 是链接类型就返回true，否则返回false
	 */
	public boolean isLinkType(String fileType){
		fileType = fileType.toLowerCase();
		
		if(linkSet.contains(fileType)){
			return true;
		}
		
		return false;		
	}
	
	/**
	 * 判断item类型是否为可下载类型
	 * @param fileType 文件后缀
	 * @return 可下载就返回true，否则返回false
	 */
	public boolean isDownable(String fileType){
		fileType = fileType.toLowerCase();
		
		if(linkSet.contains(fileType)){
			return false;
		}
		
		return true;
	}
}
