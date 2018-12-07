package com.qiyuely.remex.dict.interfaces;

import java.util.Map;

/**
 * 字典数据源
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface DictSource {

	/**
	 * 取得一组字典数据
	 * @return
	 */
	public Map<Object, Object> getDict(Object dictGroupKey);
	
	/**
	 * 是否存在这一组字典数据
	 * @param dictGroupKey
	 * @return
	 */
	public boolean existDictGroup(Object dictGroupKey);
}
