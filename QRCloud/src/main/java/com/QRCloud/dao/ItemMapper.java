package com.QRCloud.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.QRCloud.domain.Item;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;

/**
 * ItemMapper接口类，集合了所有和item相关的数据库操作
 * 
 * @author Shane
 * @version 1.0
 */
public interface ItemMapper {
	/**
	 * 根据ProjectId提取所有的Item信息
	 * 
	 * @param ProjectId
	 *            Project的Id值
	 * @return 包含Item对象的列表
	 */
	List<Item> getItemList(@Param("projectId") long ProjectId);

	/**
	 * 根据Item信息和User信息添加新的Item行，自增的item_id键值将被放置于item对象中
	 * 
	 * @param item
	 *            Item对象
	 * @param user
	 *            User对象
	 * @return 添加结果
	 */
	public int addItem(@Param("item") Item item, @Param("user") User user);

	/**
	 * 根据item_id选择一个Item对象
	 * 
	 * @param ItemId
	 *            Item的Id值
	 * @return 选取的Item对象
	 */
	public Item selectOneItem(@Param("itemId") long ItemId);

	/**
	 * 根据Item对象更新对应的Item的URL,size和type
	 * 
	 * @param item
	 *            需要更新的Item对象
	 * @return 更新结果
	 */
	public int updateItemSizeTypeUrl(@Param("item") Item item);

	/**
	 * 根据Item对象更新对应的Item的name,object_size和object_type字段
	 * 
	 * @param item
	 *            需要更新的Item对象
	 * @return 更新结果
	 */
	public int updateItemNameSizeType(@Param("item") Item item);

	/**
	 * 根据Item对象更新对应的Item的resource_s3_url字段
	 * 
	 * @param item
	 *            需要更新的Item对象
	 * @return 更新结果
	 */
	public int updateItemResourceUrl(@Param("item") Item item);

	/**
	 * 根据Item对象更新对应的Item的name和comment字段
	 * 
	 * @param item
	 *            需要更新的Item对象
	 * @return 更新结果
	 */
	public int updateItemNameComment(@Param("item") Item item);

	/**
	 * 根据Item对象删除对应的Item行
	 * 
	 * @param item
	 *            需要删除的Item对象
	 * @return 删除结果
	 */
	public int deleteItem(@Param("item") Item item);

	/**
	 * 根据Item信息和User信息新增Link类型的Item
	 * 
	 * @param item
	 *            用于插入时的item对象，插入成功后，新获取的ID将会放置到该item对象的itemId域中
	 * @param user
	 *            用于插入时的user对象
	 * @return 插入结果
	 */
	public int addLinkItem(@Param("item") Item item, @Param("user") User user);

	/**
	 * 根据Item对象更新对应的Link类型的Item行
	 * 
	 * @param item
	 *            需要更新的Item对象
	 * @return 更新结果
	 */
	public int updateLinkItem(@Param("item") Item item);

	/**
	 * 根据vs对象所提供的查询条件，查询和统计指定时间段不同城市访问某个Item次数的分布情况
	 * 
	 * @param vs
	 *            查询条件包装对象
	 * @return 查询结果，为一个List列表，每一个列表项包含省份和PV两个字段
	 */
	List<Map<String, Integer>> getVisitData(@Param("vs") VisitStatistic vs);

	/**
	 * 设置item的最新修改状态
	 * 
	 * @param item
	 *            需要修改的item对象
	 * @param status
	 *            最新修改状态，1是有修改，0是无修改
	 */
	public void setItemChanged(@Param("item") Item item, @Param("status") int status, @Param("info") String info);

	/**
	 * 设置item的最新修改状态，不包括备注
	 * 
	 * @param item
	 *            需要修改的item对象
	 * @param status
	 *            最新修改状态，1是有修改，0是无修改
	 */
	public void setItemChangedNoInfo(@Param("item") Item item, @Param("status") int status);

}
