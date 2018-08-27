package com.qiyuely.remex.poi.mop;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qiyuely.remex.poi.enums.PoiSupport;
import com.qiyuely.remex.poi.exception.RemexPoiException;

/**
 * poi导出excel
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexPoiWrite {
	
	/** 是否已构建过 */
	private boolean isBuild = false;
	
	/** poi workbook */
	private Workbook workbook;
	
	/** poi sheet */
	private Sheet sheet;
	
	
	/** poi的支持操作类型，默认XSSF */
	private PoiSupport poiSupport = PoiSupport.XSSF;
	
	/**
	 * 构建poi导出excel处理器
	 */
	public RemexPoiWrite build() {
		if (!isBuild ) {
			//根据不同支持构建workbook
			workbook = buildWorkbook();
			
			sheet = workbook.createSheet();
			
			isBuild = true;
		}
		return this;
	}
	
	/**
	 * 写入
	 */
	public void write() {
		try {
			//构建处理器
			build();
			
			
			
		} catch (Exception e) {
			throw new RemexPoiException("remex poi write error!", e);
		}
	}
	
	
	
	/**
	 * 根据不同支持构建workbook
	 * @return
	 */
	private Workbook buildWorkbook() {
		return PoiSupport.HSSF.equals(poiSupport) ? new HSSFWorkbook() : new XSSFWorkbook();
	}
}
