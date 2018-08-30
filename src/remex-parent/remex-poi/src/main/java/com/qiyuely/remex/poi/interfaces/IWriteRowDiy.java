package com.qiyuely.remex.poi.interfaces;

import org.apache.poi.ss.usermodel.Row;

/**
 * 写入列的自定义处理
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface IWriteRowDiy {

	/**
	 * 自定义处理行对象
	 * @param row 行对象
	 * @param rowCellVals 行单元格数据集
	 * @param dataIndex 当前对象对应数据集行下标，不代表当前excel所在行数
	 */
	public void diyRow(Row row, Object[] rowCellVals, int rowRollIndex, int dataCellIndex);
}
