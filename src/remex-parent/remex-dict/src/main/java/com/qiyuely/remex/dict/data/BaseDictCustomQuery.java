package com.qiyuely.remex.dict.data;

import com.qiyuely.remex.dict.interfaces.IDictCustomQuery;

/**
 * 基础的字典数据查询方式
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseDictCustomQuery implements IDictCustomQuery {
	
	public BaseDictCustomQuery() {
		//将字典数据查询方式设置到上下文中
		DictCustomQueryContext.addDictCustomQuery(getDictGroupKey(), this);
	}

	/**
	 * 取得此组字典数据的key
	 * @return
	 */
	public abstract Object getDictGroupKey();
}
