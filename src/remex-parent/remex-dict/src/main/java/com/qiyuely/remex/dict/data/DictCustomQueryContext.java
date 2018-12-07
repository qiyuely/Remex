package com.qiyuely.remex.dict.data;

import java.util.HashMap;
import java.util.Map;

import com.qiyuely.remex.dict.interfaces.IDictCustomQuery;

/**
 * 字典数据查询方式上下文
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DictCustomQueryContext {

	/** 字典数据查询方式列表 */
	private static Map<Object, IDictCustomQuery> dictCustomQueryMap = new HashMap<>();
	
	/**
	 * 取得字典数据查询方式
	 * @param dictGroupKey
	 * @return
	 */
	public static IDictCustomQuery getDictCustomQuery(Object dictGroupKey) {
		IDictCustomQuery dictCustomQuery = dictCustomQueryMap.get(dictGroupKey);
		return dictCustomQuery;
	}
	
	/**
	 * 设置一个字典数据查询方式
	 * @param dictGroupKey
	 * @param dictCustomQuery
	 */
	public static void addDictCustomQuery(Object dictGroupKey, IDictCustomQuery dictCustomQuery) {
		dictCustomQueryMap.put(dictGroupKey, dictCustomQuery);
	}
}
