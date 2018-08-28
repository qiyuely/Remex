package com.qiyuely.remex.poi.interfaces;

/**
 * 取得当前单元格数据的调度器
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T> 数据对象
 */
public interface ICellValFetch<T> {

	/**
	 * 提取当前单元格数据
	 * @param obj 当前对象
	 * @param dataIndex 当前对象对应数据集行下标，不代表当前excel所在行数
	 * @param dataCellIndex 当前对象对应数据集列下标，不代表当前excel所在列数
	 * @param rowRollIndex 当前滚动的行下标，对应当前excel所在行数
	 * @param cellRollIndex 当前滚动的列下标，对应当前excel所在列数
	 * @return
	 */
	public Object fetch(T obj, int dataIndex, int dataCellIndex, int rowRollIndex, int cellRollIndex);
}
