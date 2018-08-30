package com.qiyuely.remex.poi.interfaces;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 写入列的自定义处理
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface IWriteCellDiy {
	
	/**
	 * 自定义处理列对象
	 * @param row 行对象
	 * @param cell 列对象
	 * @param cellVal 单元格值
	 * @param dataIndex 当前对象对应数据集行下标，不代表当前excel所在行数
	 * @param dataCellIndex 当前对象对应数据集列下标，不代表当前excel所在列数
	 * @param rowRollIndex 当前滚动的行下标，对应当前excel所在行数
	 * @param cellRollIndex 当前滚动的列下标，对应当前excel所在列数
	 */
	public void diyCell(Row row, Cell cell, Object cellVal, int dataIndex, int dataCellIndex, int rowRollIndex, int cellRollIndex);
}
