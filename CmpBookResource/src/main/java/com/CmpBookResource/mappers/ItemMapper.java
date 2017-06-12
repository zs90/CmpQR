package com.CmpBookResource.mappers;

import java.util.Map;

import com.CmpBookResource.model.Item;

public interface ItemMapper {
	Item selectItem(String tid);
	void updatePV(String tid);
	Map<String, Object> selectAccessThreshold(String tid);
	int getCheckStatus (String tid);
}
