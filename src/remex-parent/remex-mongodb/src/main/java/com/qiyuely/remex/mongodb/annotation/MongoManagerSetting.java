package com.qiyuely.remex.mongodb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * mongodb管理配置项
 * 
 * @author Qiaoxin.Hong
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoManagerSetting {

	/**
	 * 数据集名称
	 * @return
	 */
	@AliasFor("name")
	String value() default "";
	
	/**
	 * 数据集名称
	 * @return
	 */
	@AliasFor("value")
	String name() default "";
}
