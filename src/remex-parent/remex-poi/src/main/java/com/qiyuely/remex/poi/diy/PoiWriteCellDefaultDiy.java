package com.qiyuely.remex.poi.diy;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import com.qiyuely.remex.poi.interfaces.IWriteCellDiy;

/**
 * 写入列的默认的自定义处理
 * 
 * @author Qiaoxin.Hong
 *
 */
public class PoiWriteCellDefaultDiy implements IWriteCellDiy {
	
	/** 单元格样式 */
	protected CellStyle cellStyle;

	public PoiWriteCellDefaultDiy() {
		super();
	}

	public PoiWriteCellDefaultDiy(CellStyle cellStyle) {
		super();
		this.cellStyle = cellStyle;
	}

	/**
	 * 自定义处理列对象
	 */
	@Override
	public void diyCell(Row row, Cell cell, Object cellVal, int dataIndex, int dataCellIndex, int rowRollIndex, int cellRollIndex) {
		//赋值
		CellValFillUtils.fillCellValue(cell, cellVal);
		
		//设置单元格样式
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}
	
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	
	public CellStyle getCellStyle() {
		return cellStyle;
	}
}
