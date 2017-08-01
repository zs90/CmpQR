package com.CmpBookResource.mappers;

import java.util.Map;

import com.CmpBookResource.model.Item;

/**
 * itemMapper数据库访问接口类
 * 
 * @author Shane
 * @version 1.0
 */
public interface ItemMapper {
	/**
	 * 根据item的id查询item
	 * 
	 * @param tid
	 *            item的id
	 * @return item对象
	 */
	Item selectItem(String tid);

	/**
	 * 根据item的id将对应item的访问数pv加一
	 * 
	 * @param tid
	 *            item的id
	 */
	void updatePV(String tid);

	/**
	 * 根据item的id查询该item的对应的project的每日单个用户访问上限
	 * 
	 * @param tid
	 *            item的id
	 * @return 一个map，key为project的id，value为访问上限值
	 */
	Map<String, Object> selectAccessThreshold(String tid);

	/**
	 * 根据item的id查询该item对应的project的审核状态
	 * 
	 * @param tid
	 *            item的id
	 * @return 审核状态值
	 */
	int getCheckStatus(String tid);
}
