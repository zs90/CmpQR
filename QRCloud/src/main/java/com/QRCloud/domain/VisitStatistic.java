package com.QRCloud.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 读者用户访问数据查询条件定义类，用于封装查询条件，例如日期区间，所属ID等等。
 * 
 * @author Shane
 * @version 1.0
 */
public class VisitStatistic {
	private Date startDate; // 起始日期
	private Date endDate; // 终止日期
	private long Id; // ID，可以是Item的Id，也可以是Project的Id

	private List<Map<String, Integer>> provinceDataMap;
	/*
	 * private static String[] provinceList = new String[]{
	 * "beijing","tianjin","shanghai", "chongqing","heibei","shanxi",
	 * "liaoning","jilin","heilongjiang", "jiangsu","zhejiang","anhui",
	 * "fujian","jiangxi","shandong", "henan","hubei","hunan",
	 * "guangdong","hainan","sichuan", "guizhou","yunan","shanxi",
	 * "gansu","qinghai","taiwan", "guangxi","neimenggu","xizang",
	 * "ningxia","xinjiang","hongkong", "macau" };
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
