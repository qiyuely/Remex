package com.qiyuely.remex.dict.mop;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.qiyuely.remex.dict.annotation.Dict;
import com.qiyuely.remex.dict.annotation.DictSub;
import com.qiyuely.remex.dict.interfaces.DictSource;
import com.qiyuely.remex.utils.CollectionUtils;
import com.qiyuely.remex.utils.StringUtils;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * 字典管理类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DictMop {
	
	/** 字典数据源 */
	protected DictSource dictSource = new NotDefineDictSource();
	
	public DictMop() {
		super();
	}

	public DictMop(DictSource dictSource) {
		super();
		this.dictSource = dictSource;
	}

	/**
	 * 字典翻译
	 * @param dictGroupKey
	 * @param dictKey
	 * @return
	 */
	public Object tran(Object dictGroupKey, Object dictKey) {
		Object dictVal = null;
		
		if (dictGroupKey != null && dictKey != null) {
			Map<Object, Object> dictMap = dictSource.getDict(dictGroupKey);
			
			if (CollectionUtils.isNotEmpty(dictMap)) {
				dictVal = dictMap.get(dictKey);
			}
		}
		
		return dictVal;
	}

	/**
	 * 字典翻译
	 * @param dictGroupKey
	 * @param dictKey
	 * @return
	 */
	public String tranString(Object dictGroupKey, Object dictKey) {
		Object dictVal = tran(dictGroupKey, dictKey);
		return dictVal == null ? null : dictVal.toString();
	}
	
	/**
	 * 是否存在这一组字典数据
	 * @param dictGroupKey
	 * @return
	 */
	public boolean existDictGroup(Object dictGroupKey) {
		return dictSource.existDictGroup(dictGroupKey);
	}
	
	/**
	 * 以字典翻译配置进行bean的字典翻译
	 * @see com.qiyuely.remex.dict.annotation.Dict
	 * @param bean
	 */
	public void tranBeanList(Collection<?> beanList) {
		if (CollectionUtils.isNotEmpty(beanList)) {
			for (Object bean : beanList) {
				tranBean(bean);
			}
		}
	}
	
	/**
	 * 以字典翻译配置进行bean的字典翻译
	 * @see com.qiyuely.remex.dict.annotation.Dict
	 * @param bean
	 */
	public void tranBean(Object bean) {
		if (bean == null) {
			return;
		}
		
		try {
			Class<?> clazz = bean.getClass();
			Field[] fieldArr = clazz.getDeclaredFields();
			
			if (ValidateUtils.isNotEmpty(fieldArr)) {
				for (Field field : fieldArr) {
					Class<?> fieldClass = field.getType();
					Object fieldValue = field.get(bean);
					
					//启用了字典翻译配置
					if (field.isAnnotationPresent(Dict.class)) {
						
						Dict dict = field.getAnnotation(Dict.class);
						String dictGroupKey = dict.group();
						String dictKeyProperty = dict.key();
						
						if (StringUtils.isBlank(dictGroupKey) || StringUtils.isBlank(dictKeyProperty)) {
							//存在这一组字典数据
							if (existDictGroup(dictGroupKey)) {
								//取得字典key的值
								PropertyDescriptor pd = new PropertyDescriptor(dictKeyProperty, clazz);
								Object dictKeyFieldValue = pd.getReadMethod().invoke(bean);
								if (dictKeyFieldValue != null) {
									Object dictVal = tran(dictGroupKey, dictKeyFieldValue);
									field.setAccessible(true);
									field.set(bean, dictVal);
								}
							}
						}
					} else if (field.isAnnotationPresent(DictSub.class)) {  //子对象进行字典翻译
						//处理子对象
						if (fieldValue != null) {
							//集合
							if (Collection.class.equals(fieldClass)) {
								Collection<?> coll = (Collection<?>) fieldValue;
								for (Object item : coll) {
									tranBean(item);
								}
							} else {  //普通对象
								tranBean(fieldValue);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public DictSource getDictSource() {
		return dictSource;
	}

	public void setDictSource(DictSource dictSource) {
		this.dictSource = dictSource;
	}
}

/**
 * 未定义的字典数据源
 * 
 * @author Qiaoxin.Hong
 *
 */
class NotDefineDictSource implements DictSource {

	/**
	 * 取得一组字典数据
	 * @return
	 */
	@Override
	public Map<Object, Object> getDict(Object dictGroupKey) {
		return null;
	}
	
	/**
	 * 是否有这一组字典数据
	 * @param dictGroupKey
	 * @return
	 */
	@Override
	public boolean existDictGroup(Object dictGroupKey) {
		return false;
	}
}
