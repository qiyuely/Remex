package com.qiyuely.remex.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 * 
 * @see org.apache.commons.collections.CollectionUtils
 * 
 * @author Qiaoxin.Hong
 *
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {
	
	/**
	 * 是否为空
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}
	
	/**
	 * 是否不为空
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
	
	/**
	 * 默认list，null  =>  new ArrayList<>() 
	 * @param list
	 * @return
	 */
	public static <T> List<T> defaultList(List<T> list) {
		return list == null ? new ArrayList<T>() : list;
	}
}
