package com.qiyuely.remex.utils;

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
}
