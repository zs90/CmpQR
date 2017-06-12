package com.CmpBookResource.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CmpBookResource.mappers.ItemMapper;
import com.CmpBookResource.model.Item;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public Item getItemById(String itemId) {
		// TODO Auto-generated method stub
		return itemMapper.selectItem(itemId);
	}

	@Override
	public void incrementPVByOne(String itemId) {
		// TODO Auto-generated method stub
		itemMapper.updatePV(itemId);
	}

	@Override
	public Map<String, Object> getAccessLimitById(String itemId) {
		// TODO Auto-generated method stub
		return itemMapper.selectAccessThreshold(itemId);
	}

	@Override
	public boolean isPublished(String itemId) {
		// TODO Auto-generated method stub
		if(itemMapper.getCheckStatus(itemId) == 2)
			return true;
		else
			return false;
	}

}
