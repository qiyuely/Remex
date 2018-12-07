package com.qiyuely.remex.dict.data;

import java.util.Map;

/**
 * 字典数据源，默认方法，预存所有字典数据，数据只设定一次
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DictDefaultSource extends BaseDictSource {

	public DictDefaultSource() {
		super();
	}

	public DictDefaultSource(Map<Object, Map<Object, Object>> dictGroupMap) {
		super(dictGroupMap);
	}


	/**
	 * 取得一组字典数据
	 * @return
	 */
	@Override
	public Map<Object, Object> getDict(Object dictGroupKey) {
		Map<Object, Object> dictMap = null;
		
		if (dictGroupKey != null) {
			dictMap = dictGroupMap.get(dictGroupKey);
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
		return exists;
	}
}
