package com.qiyuely.remex.dict.mop;

import java.util.Collection;
import java.util.Map;

import com.qiyuely.remex.dict.data.DictCustomSource;
import com.qiyuely.remex.dict.data.DictDefaultSource;
import com.qiyuely.remex.dict.interfaces.DictSource;
import com.qiyuely.remex.exception.RemexException;

/**
 * 字典管理工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DictUtils {

	/** 字典管理类 */
	private static DictMop dictMop;
	
	/**
	 * 初始化一个字典管理对象，并设置到此工具类中，可直接用此工具类进行操作
	 * 
	 * @see com.qiyuely.remex.dict.mop.DictUtils
	 * @param dictSource
	 */
	public static void init(DictSource dictSource) {
		dictMop = new DictMop(dictSource);
	}
	
	/**
	 * 初始化默认的字典管理对象，预存所有字典数据，数据只设定一次
	 * @see com.qiyuely.remex.dict.data.DictDefaultSource
	 */
	public static void initDefault(Map<Object, Map<Object, Object>> dictGroupMap) {
		DictSource dictSource = new DictDefaultSource(dictGroupMap); 
		init(dictSource);
	}
	
	/**
	 * 初始化自定义方式的字典管理对象，动态获取字典数据
	 * @see com.qiyuely.remex.dict.data.DictCustomSource
	 */
	public static void initCustom() {
		DictSource dictSource = new DictCustomSource();
		init(dictSource);
	}
	
	/**
	 * 取得字典管理对象
	 * @return
	 */
	public static DictMop getDictMop() {
		if (dictMop == null) {
			throw new RemexException("Dict mop uninitialized");
		}
		return dictMop;
	}
	
	/**
	 * 字典翻译
	 * @param dictGroupKey
	 * @param dictKey
	 * @return
	 */
	public static Object tran(Object dictGroupKey, Object dictKey) {
		return getDictMop().tran(dictGroupKey, dictKey);
	}
	
	/**
	 * 字典翻译
	 * @param dictGroupKey
	 * @param dictKey
	 * @return
	 */
	public static String tranString(Object dictGroupKey, Object dictKey) {
		return getDictMop().tranString(dictGroupKey, dictKey);
	}
	
	/**
	 * 以字典翻译配置进行bean的字典翻译
	 * @param bean
	 */
	public static void tranBean(Object bean) {
		getDictMop().tranBean(bean);
	}
	
	/**
	 * 以字典翻译配置进行bean的字典翻译
	 * @param bean
	 */
	public static void tranBeanList(Collection<?> beanList) {
		getDictMop().tranBeanList(beanList);
	}
}
