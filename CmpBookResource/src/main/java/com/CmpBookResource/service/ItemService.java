package com.CmpBookResource.service;

import java.util.Map;

import com.CmpBookResource.model.Item;

public interface ItemService {
	Item getItemById(String itemId);
	void incrementPVByOne(String itemId);
	Map<String, Object> getAccessLimitById(String itemId);
	boolean isPublished(String itemId);
}
