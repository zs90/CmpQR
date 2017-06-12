package com.QRCloud.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitStatistic{
	private Date startDate;
	private Date endDate;
	private long Id;
	
	private List<Map<String, Integer>> provinceDataMap;
	/*
	private static String[] provinceList = new String[]{
												"beijing","tianjin","shanghai",
												"chongqing","heibei","shanxi",
												"liaoning","jilin","heilongjiang",
												"jiangsu","zhejiang","anhui",
												"fujian","jiangxi","shandong",
												"henan","hubei","hunan",
												"guangdong","hainan","sichuan",
												"guizhou","yunan","shanxi",
												"gansu","qinghai","taiwan",
												"guangxi","neimenggu","xizang",
												"ningxia","xinjiang","hongkong",
												"macau"
											};
	*/

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Map<String, Integer>> getProvinceDataMap() {
		return provinceDataMap;
	}

	public void setProvinceDataMap(List<Map<String, Integer>> provinceDataMap) {
		this.provinceDataMap = provinceDataMap;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}
	
}
