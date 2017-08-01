package com.CmpBookResource.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CmpBookResource.mappers.ItemMapper;
import com.CmpBookResource.model.Item;

/**
 * itemService服务层实现类
 * @author Shane
 * @version 1.0
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * 通过itemId获取Item对象
	 */
	@Override
	public Item getItemById(String itemId) {
		// TODO Auto-generated method stub
		return itemMapper.selectItem(itemId);
	}

	/**
	 * 通过itemId将对应的pv值加一
	 */
	@Override
	public void incrementPVByOne(String itemId) {
		// TODO Auto-generated method stub
		itemMapper.updatePV(itemId);
	}

	/**
	 * 通过itemId获取对应的project的id以及访问上限
	 */
	@Override
	public Map<String, Object> getAccessLimitById(String itemId) {
		// TODO Auto-generated method stub
		return itemMapper.selectAccessThreshold(itemId);
	}

	/**
	 * 通过itemId获取对应的project的审核状态
	 */
	@Override
	public boolean isPublished(String itemId) {
		// TODO Auto-generated method stub
		if(itemMapper.getCheckStatus(itemId) == 2)
			return true;
		else
			return false;
	}

}
