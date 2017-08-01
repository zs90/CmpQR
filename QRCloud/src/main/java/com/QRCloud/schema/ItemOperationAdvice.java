package com.QRCloud.schema;
import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.ItemMapper;
import com.QRCloud.domain.Item;

/**
 * aop模式类，后置增强。在用户每次更新资源后，对相应的资源进行标记
 * @author Shane
 * @version 1.0
 */
@Service("ItemOperationAdvice")
public class ItemOperationAdvice implements AfterReturningAdvice{
	@Autowired
	private ItemMapper itemMapper;

	/**
	 * 后置方法，用于提取item对象，并对其进行标记
	 */
	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		// TODO Auto-generated method stub
		for(int i = 0; i < arg2.length; i++){
			if(arg2[i].getClass().getTypeName() == "com.QRCloud.domain.Item"){
				itemMapper.setItemChangedNoInfo((Item)arg2[i], 1);
				break;
			}
		}
	}
	

}
