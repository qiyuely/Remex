package com.qiyuely.remex.utils;

import java.io.Closeable;

/**
 * 资源释放工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SourceCloseUtils {
	
	/**
	 * 资源释放
	 * @param sources
	 */
	public static void close(Closeable...sources) {
		for (Closeable source : sources) {
			if (source != null) {
				try {
					source.close();
				} catch (Exception e) {
					System.err.println("Source close error!");
				}
			}
		}
	}
}
