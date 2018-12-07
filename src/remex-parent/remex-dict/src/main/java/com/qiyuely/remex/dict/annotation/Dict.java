package com.qiyuely.remex.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译配置
 * 
 * @author Qiaoxin.Hong
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

	/**
	 * 字典key
	 * @return
	 */
	String key();
	
	/**
	 * 字典组名
	 * @return
	 */
	String group();
}
