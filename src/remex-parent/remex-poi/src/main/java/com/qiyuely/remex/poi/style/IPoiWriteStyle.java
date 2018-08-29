package com.qiyuely.remex.poi.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * poi导出样式
 *
 * @author Qiaoxin.Hong
 *
 */
public interface IPoiWriteStyle {
	
	/**
	 * 取得默认的单元格样式
	 * @param workbook
	 * @return
	 */
	public CellStyle getCellStyle(Workbook workbook);
	
	/**
	 * 取得默认的单元格字体样式
	 * @param workbook
	 * @return
	 */
	public Font getCellFont(Workbook workbook);

	/**
	 * 取得内容样式
	 * @param workbook
	 * @return
	 */
	public default CellStyle getDataStyle(Workbook workbook) {
		return getCellStyle(workbook);
	}
	
	/**
	 * 取得内容字体样式
	 * @param workbook
	 * @return
	 */
	public default Font getDataFont(Workbook workbook) {
		return getCellFont(workbook);
	}
	
	/**
	 * 取得列标题样式
	 * @param workbook
	 * @return
	 */
	public default CellStyle getCellTitleStyle(Workbook workbook) {
		return getCellStyle(workbook);
	}
	
	/**
	 * 取得列标题字体样式
	 * @param workbook
	 * @return
	 */
	public default Font getCellTitleFont(Workbook workbook) {
		return getCellFont(workbook);
	}
	
	/**
	 * 取得标题样式
	 * @param workbook
	 * @return
	 */
	public default CellStyle getTitleStyle(Workbook workbook) {
		return getCellStyle(workbook);
	}
	
	/**
	 * 取得标题字体样式
	 * @param workbook
	 * @return
	 */
	public default Font getTitleFont(Workbook workbook) {
		return getCellFont(workbook);
	}
}
