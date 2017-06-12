package com.QRCloud.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.QRCloud.domain.Item;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;

public interface ItemMapper {
	//列出所有item
	List<Item> getItemList(@Param("projectId") long ProjectId);
	
	//上传item
	public int addItem(@Param("item") Item item, @Param("user") User user);
	
	//获取item
	public Item selectOneItem(@Param("itemId") long ItemId);
	
	//更新item的URL,size和type
	public int updateItemSizeTypeUrl(@Param("item") Item item);
		
	//更新item的name,object size和object type字段
	public int updateItemNameSizeType(@Param("item") Item item);
	
	//更新item的resource_s3_url字段
	public int updateItemResourceUrl(@Param("item") Item item);
	
	//更新item的name和comment字段
	public int updateItemNameComment(@Param("item") Item item);
	
	//删除item
	public int deleteItem(@Param("item") Item item);
	
	//新增链接资源
	public int addLinkItem(@Param("item") Item item, @Param("user") User user);
	
	//修改链接资源
	public int updateLinkItem(@Param("item") Item item);
	
	//
	List<Map<String, Integer>> getVisitData(@Param("vs") VisitStatistic vs);
	
}
