package com.qiyuely.remex.dict.data;

import java.util.HashMap;
import java.util.Map;

import com.qiyuely.remex.dict.interfaces.DictSource;

/**
 * 基础字典数据源实现类
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseDictSource implements DictSource {

	/** 字典数据 */
	protected Map<Object, Map<Object, Object>> dictGroupMap = new HashMap<>();
	
	public BaseDictSource() {
		super();
	}

	public BaseDictSource(Map<Object, Map<Object, Object>> dictGroupMap) {
		super();
		this.dictGroupMap = dictGroupMap;
	}

	public Map<Object, Map<Object, Object>> getDictGroupMap() {
		return dictGroupMap;
	}

	public void setDictGroupMap(Map<Object, Map<Object, Object>> dictGroupMap) {
		this.dictGroupMap = dictGroupMap;
	}
}
