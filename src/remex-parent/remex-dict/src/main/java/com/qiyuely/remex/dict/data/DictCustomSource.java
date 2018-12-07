package com.qiyuely.remex.dict.data;

import java.util.Map;

import com.qiyuely.remex.dict.interfaces.IDictCustomQuery;

/**
 * <pre>
 * 字典数据源，自定义方式，动态获取字典数据
 * 从字典数据查询方式上下文DictCustomQueryContext，取得查询方式IDictCustomQuery，来查询字典数据，
 * 可以考虑自定义的查询方式从BaseDictCustomQuery继承下来，来得到自动将查询方式注入上下文的能力
 * </pre>
 * 
 * @see com.qiyuely.remex.dict.data.DictCustomQueryContext 字典数据查询方式上下文
 * @see com.qiyuely.remex.dict.interfaces.IDictCustomQuery 字典数据查询方式
 * @see com.qiyuely.remex.dict.data.BaseDictCustomQuery 基础的字典数据查询方式
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DictCustomSource extends BaseDictSource {

	/**
	 * 取得一组字典数据
	 * @return
	 */
	@Override
	public Map<Object, Object> getDict(Object dictGroupKey) {
		Map<Object, Object> dictMap = null;
		
		if (dictGroupKey != null) {
			dictMap = dictGroupMap.get(dictGroupKey);
			
			if (dictMap == null) {
				synchronized (this) {
					if (dictMap == null) {
						//字典数据查询方式
						IDictCustomQuery  dictCustomQuery = DictCustomQueryContext.getDictCustomQuery(dictGroupKey);
						if (dictCustomQuery != null) {
							dictMap = dictCustomQuery.queryDict();
						}
						
						dictGroupMap.put(dictGroupKey, dictMap);
					}
				}
			}
		}
		
		return dictMap;
	}
	
	/**
	 * 是否有这一组字典数据
	 * @param dictGroupKey
	 * @return
	 */
	@Override
	public boolean existDictGroup(Object dictGroupKey) {
		boolean exists = dictGroupMap.containsKey(dictGroupKey);
		
		//如果不存在，则查询一次
		if (!exists) {
			getDict(dictGroupKey);
			
			exists = dictGroupMap.containsKey(dictGroupKey);
		}
		
		return exists;
	}
}
