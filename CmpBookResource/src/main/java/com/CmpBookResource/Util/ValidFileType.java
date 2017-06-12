package com.CmpBookResource.Util;

import java.util.HashSet;

import org.springframework.stereotype.Service;

@Service
public class ValidFileType {
	HashSet<String> showSet = new HashSet<>();	
	HashSet<String> linkSet = new HashSet<>();
	
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
	
	public boolean isShowable(String fileType){
		fileType = fileType.toLowerCase();
		if(showSet.contains(fileType)){
			return true;
		}
		
		return false;
	}
	
	public boolean isLinkType(String fileType){
		fileType = fileType.toLowerCase();
		
		if(linkSet.contains(fileType)){
			return true;
		}
		
		return false;		
	}
	
	public boolean isDownable(String fileType){
		fileType = fileType.toLowerCase();
		
		if(linkSet.contains(fileType)){
			return false;
		}
		
		return true;
	}
}
