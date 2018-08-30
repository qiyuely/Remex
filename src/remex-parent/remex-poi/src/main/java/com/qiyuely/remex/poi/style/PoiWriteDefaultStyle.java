package com.qiyuely.remex.poi.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * poi导出默认样式
 * 
 * @author Qiaoxin.Hong
 *
 */
public class PoiWriteDefaultStyle implements IPoiWriteStyle {
	
	/**
	 * 取得默认的单元格样式
	 */
	@Override
	public CellStyle getCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFont(getCellFont(workbook));  //设置内容字体样式
		return style;
	}
	
	/**
	 * 取得默认的单元格字体样式
	 */
	@Override
	public Font getCellFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);  //设置字体大小
		return font;
	}

	/**
	 * 取得内容样式
	 */
	@Override
	public CellStyle getDataStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFont(getDataFont(workbook));  //设置内容字体样式
		style.setAlignment(HorizontalAlignment.CENTER);  //居中
		return style;
	}

	/**
	 * 取得内容字体样式
	 */
	@Override
	public Font getDataFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);  //设置字体大小
		return font;
	}

	/**
	 * 取得列标题样式
	 */
	@Override
	public CellStyle getCellTitleStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFont(getCellTitleFont(workbook));  //设置标题字体样式
		style.setAlignment(HorizontalAlignment.CENTER);  //居中
//		style.setBorderTop(BorderStyle.THIN);
//		style.setBorderBottom(BorderStyle.THIN);
//		style.setBorderLeft(BorderStyle.THIN);
//		style.setBorderRight(BorderStyle.THIN);
		return style;
	}

	/**
	 * 取得列标题字体样式
	 */
	@Override
	public Font getCellTitleFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);  //设置字体大小
		font.setBold(true);  //粗体显示
		return font;
	}
	
	/**
	 * 取得标题样式
	 */
	@Override
	public CellStyle getTitleStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFont(getTitleFont(workbook));  //设置标题字体样式
		style.setAlignment(HorizontalAlignment.CENTER);  //居中
//		style.setBorderTop(BorderStyle.THIN);
//		style.setBorderBottom(BorderStyle.THIN);
//		style.setBorderLeft(BorderStyle.THIN);
//		style.setBorderRight(BorderStyle.THIN);
		return style;
	}

	/**
	 * 取得标题字体样式
	 */
	@Override
	public Font getTitleFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);  //设置字体大小
		font.setBold(true);  //粗体显示
		return font;
	}
}
