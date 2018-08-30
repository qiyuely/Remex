package com.qiyuely.remex.poi.val;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import com.qiyuely.remex.core.constant.BConst;

/**
 * 单元格赋值工具方法
 * 
 * @author Qiaoxin.Hong
 *
 */
public class CellValFillUtils {
	
	/**
	 * 给单元格赋值
	 * @param cell
	 * @param val
	 */
	public static void fillCellValue(Cell cell, Object val) {
		//根据数据类型绑入单元格中
		if (val == null) {
			cell.setCellValue(BConst.EMPTY);
		} else if (val instanceof Date) {
			cell.setCellValue((Date) val);
		} else if (val instanceof Integer) {
			cell.setCellValue((Integer) val);
		} else if (val instanceof Double) {
			cell.setCellValue((Double) val);
		} else if (val instanceof Float) {
			cell.setCellValue((Float) val);
		} else if (val instanceof Long) {
			cell.setCellValue((Long) val);
		} else {
			cell.setCellValue(val.toString());
		}
	}
}
