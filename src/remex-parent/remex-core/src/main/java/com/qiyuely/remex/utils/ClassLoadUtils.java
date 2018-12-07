package com.qiyuely.remex.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 类型加载工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ClassLoadUtils {
	
	public static void main(String[] args) {
		findPackageClass("");
	}
	
	/**
	 * 加载指定包下的Class
	 * @param pagePath
	 */
	public static List<String> findPackageClass(String packagePath) {
		if (StringUtils.isBlank(packagePath)) {
			packagePath = "";
		}
		
		packagePath = packagePath.replace(".", File.separator);
		
		//根路径
		String rootPath = ClassLoadUtils.class.getResource("/").getPath();
		//指定包的绝对路径
		String searchPath = rootPath + packagePath;
		
		List<String> classList = new ArrayList<>();
		
		//解析路径下的所有Class，包括子文件夹
		resolveFile(rootPath, new File(searchPath), classList);
		
		return classList;
	}
	
	/**
	 * 解析路径下的所有Class，包括子文件夹
	 * @param file
	 * @param classPaths
	 */
	private static void resolveFile(String rootPath, File file, List<String> classList) {
		//文件夹
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				resolveFile(rootPath, subFile, classList);
			}
		} else {
			//类文件
			if (file.getName().endsWith(".class")) {
				String classPath = file.getPath();
				classPath = classPath.replace(rootPath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".")
						.replace(".class","");
				classList.add(classPath);
			}
		}
	}
}
