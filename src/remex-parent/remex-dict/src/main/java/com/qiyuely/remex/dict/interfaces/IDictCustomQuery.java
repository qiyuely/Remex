package com.qiyuely.remex.dict.interfaces;

import java.util.Map;

/**
 * 字典数据查询方式
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface IDictCustomQuery {

	/**
	 * 取得当前组字典数据
	 * @return
	 */
	public Map<Object, Object> queryDict(); 	
}
