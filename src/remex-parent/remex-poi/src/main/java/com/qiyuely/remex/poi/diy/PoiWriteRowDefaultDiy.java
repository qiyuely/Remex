package com.qiyuely.remex.poi.diy;

import org.apache.poi.ss.usermodel.Row;

import com.qiyuely.remex.poi.interfaces.IWriteRowDiy;

/**
 * 写入行的默认的自定义处理
 * 
 * @author Qiaoxin.Hong
 *
 */
public class PoiWriteRowDefaultDiy implements IWriteRowDiy {

	/**  默认行高度，默认乘256 */
	protected double rowHeight = 1;
	
	public PoiWriteRowDefaultDiy() {
		super();
	}

	public PoiWriteRowDefaultDiy(double rowHeight) {
		super();
		this.rowHeight = rowHeight;
	}

	/**
	 * 自定义处理行对象
	 */
	@Override
	public void diyRow(Row row, Object[] rowCellVals, int dataIndex, int rowRollIndex) {
		if (rowHeight > 0) {
			short height = (short) (rowHeight * 256);
			row.setHeight(height);
		}
	}
	
	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
	}
	
	public double getRowHeight() {
		return rowHeight;
	}
}
