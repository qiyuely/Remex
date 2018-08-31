package com.qiyuely.remex.poi.diy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.qiyuely.remex.poi.exception.RemexPoiException;
import com.qiyuely.remex.poi.interfaces.ICellValFetch;
import com.qiyuely.remex.utils.StringUtils;

/**
 * 以数据对象属性通过反射取得当前单元格数据的调度器
 * 
 * @author Qiaoxin.Hong
 * 
 * @param <T> 数据对象
 *
 */
public class CellValPropertyFetch<T> implements ICellValFetch<T> {
	
	/** 属性集 */
	protected String[] properties;
	
	/** 缓存属性解析后的method */
	protected Map<String, Method> propertyMethodMap = new HashMap<String, Method>();
	
	/**
	 * 以数据对象属性通过反射取得当前单元格数据的调度器
	 * @param properties
	 */
	public CellValPropertyFetch(String[] properties) {
		this.properties = properties == null ? new String[0] : properties;
	}

	/**
	 * 提取当前单元格数据
	 */
	@Override
	public Object fetch(T obj, int dataIndex, int dataCellIndex, int rowRollIndex, int cellRollIndex) {
		if (dataCellIndex > properties.length) {
			return null;
		}
		
		String property = StringUtils.defaultString(properties[dataCellIndex]);
		try {
			Method method = propertyMethodMap.get(property);
			//method不存在，生成method
			if (method == null) {
				PropertyDescriptor pdes = new PropertyDescriptor(property, obj.getClass());
				method = pdes.getReadMethod();
				propertyMethodMap.put(property, method);
			}
			
			Object val = method.invoke(obj);
			
			return val;
		} catch (Exception e) {
			throw new RemexPoiException("remex poi fetch property val error! proprety:" + property);
		}
	}
}
