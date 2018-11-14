package com.qiyuely.remex.utils;

/**
 * 验证工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ValidateUtils {
	
	/**
	 * <pre>
	 * 是否为空
	 * null    =>  true
	 * arr.length == 0    =>  true
	 * </pre>
	 * @param coll 数组
	 * @return true：为空   false：不为空
	 */
	public static boolean isEmpty(Object[] arr) {
		return arr == null || arr.length ==0;
	}
	
	/**
	 * <pre>
	 * 是否不为空
	 * null    =>  false
	 * arr.length == 0    =>  false
	 * </pre>
	 * @param coll 数组
	 * @return true：不为空   false：为空
	 */
	public static boolean isNotEmpty(Object[] arr) {
		return !isEmpty(arr);
	}
	
	/**
	 * <pre>
	 * 是否为空
	 * null    =>  true
	 * arr.length == 0    =>  true
	 * </pre>
	 * @param coll 数组
	 * @return true：为空   false：不为空
	 */
	public static boolean isEmpty(int[] arr) {
		return arr == null || arr.length ==0;
	}
	
	/**
	 * <pre>
	 * 是否不为空
	 * null    =>  false
	 * arr.length == 0    =>  false
	 * </pre>
	 * @param coll 数组
	 * @return true：不为空   false：为空
	 */
	public static boolean isNotEmpty(int[] arr) {
		return !isEmpty(arr);
	}
	
	/**
	 * <pre>
	 * 是否为空
	 * null    =>  true
	 * arr.length == 0    =>  true
	 * </pre>
	 * @param coll 数组
	 * @return true：为空   false：不为空
	 */
	public static boolean isEmpty(String[] arr) {
		return arr == null || arr.length ==0;
	}
	
	/**
	 * <pre>
	 * 是否不为空
	 * null    =>  false
	 * arr.length == 0    =>  false
	 * </pre>
	 * @param coll 数组
	 * @return true：不为空   false：为空
	 */
	public static boolean isNotEmpty(String[] arr) {
		return !isEmpty(arr);
	}
}
