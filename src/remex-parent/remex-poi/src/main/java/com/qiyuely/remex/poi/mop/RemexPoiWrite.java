package com.qiyuely.remex.poi.mop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qiyuely.remex.core.constant.BConst;
import com.qiyuely.remex.poi.enums.PoiSupport;
import com.qiyuely.remex.poi.exception.RemexPoiException;
import com.qiyuely.remex.poi.interfaces.ICellValFetch;
import com.qiyuely.remex.poi.val.CellValPropertyFetch;
import com.qiyuely.remex.utils.CollectionUtils;
import com.qiyuely.remex.utils.SourceCloseUtils;
import com.qiyuely.remex.utils.ValidateUtils;

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
	
	
	/** 当前滚动的行下标 */
	private int rowRollIndex = 0;
			
	/** 当前滚动的列下标 */
	private int cellRollIndex = 0;
	
	/** poi的支持操作类型，默认XSSF */
	private PoiSupport poiSupport = PoiSupport.XSSF;
	
	/**  默认列宽度 */
	private int colDefaultWidth = 8;
	
	public static void main(String[] args) throws Exception {
		FileOutputStream os = new FileOutputStream(new File("E://b.xlsx"));
		List<UrlEntity> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			UrlEntity entity = new UrlEntity();
			entity.setId("id:" + i);
			entity.setName("name:" + i);
			list.add(entity);
		}
		
		String[] colNames = new String[] {"id", "名称", "url"};
		String[] properties = new String[] {"id", "name", "url"};
		
		RemexPoiWrite remexPoiWrite = new RemexPoiWrite();
		remexPoiWrite.write(colNames).write(list, properties).output(os);
		System.out.println("======  end   ======");
	}
	
	/**
	 * 构建poi导出excel处理器
	 */
	public RemexPoiWrite build() {
		if (!isBuild ) {
			//根据不同支持构建workbook
			workbook = buildWorkbook();
			
			sheet = workbook.createSheet();
			
			//默认列宽度
			sheet.setDefaultColumnWidth(colDefaultWidth);
			
			isBuild = true;
		}
		return this;
	}
	
	/**
	 * 以当前滚动的行下标创建行对象
	 * @return
	 */
	public Row createRow() {
		Row row = sheet.createRow(rowRollIndex);
		return row;
	}
	
	/**
	 * 创建行对象
	 * @param rowIndex 行下标
	 * @return
	 */
	public Row createRow(int rowIndex) {
		Row row = sheet.createRow(rowIndex);
		return row;
	}
	
	/**
	 * 以当前滚动的行下标及列下标创建列对象
	 * @return
	 */
	public Cell createCell() {
		Row row = createRow();
		Cell cell = createCell(row, cellRollIndex);
		return cell;
	}
	
	/**
	 * 创建列对象
	 * @param row 行对象
	 * @param cellIndex 列下标
	 * @return
	 */
	public Cell createCell(int rowIndex, int cellIndex) {
		Row row = createRow(rowIndex);
		Cell cell = createCell(row, cellIndex);
		return cell;
	}
	
	/**
	 * 创建列对象
	 * @param row 行对象
	 * @param cellIndex 列下标
	 * @return
	 */
	public Cell createCell(Row row, int cellIndex) {
		Cell cell = row.createCell(cellIndex);
		return cell;
	}
	
	/**
	 * 写入
	 */
	public <T> RemexPoiWrite write(String[] cellVals) {
		write(cellVals, 0);
		
		return this;
	}
	
	/**
	 * 写入
	 */
	public <T> RemexPoiWrite write(List<T> dataList, String[] properties) {
		int cellCount = properties == null ? 0 : properties.length;
		ICellValFetch<T> cellValFetch = new CellValPropertyFetch<>(properties);
		write(dataList, cellValFetch, cellCount);
		return this;
	}
			
	/**
	 * 写入
	 */
	public <T> RemexPoiWrite write(List<T> dataList, ICellValFetch<T> cellValFetch, int cellCount) {
		try {
			if (CollectionUtils.isNotEmpty(dataList) && cellCount >= 0) {
				//行循环
				for (int i = 0; i < dataList.size(); i++) {
					T data = dataList.get(i);
					Object[] cellVals = new Object[cellCount];
					
					int itemCellRollIndex = cellRollIndex;
					//列循环
					for (int j = 0; j < cellCount; j++) {
						//提取字段值
						Object val = null;
						if (cellValFetch == null) {
							val = data;
						} else {
							val = cellValFetch.fetch(data, i, j, rowRollIndex, itemCellRollIndex);
						}
						
						cellVals[j] = val;
						
						itemCellRollIndex++;
					}
					
					write(cellVals, i);
				}
			}
			
			return this;
		} catch (Exception e) {
			throw new RemexPoiException("remex poi write error!", e);
		}
	}
	
	/**
	 * 写入
	 */
	private <T> RemexPoiWrite write(Object[] cellVals, int dataIndex) {
		try {
			//构建处理器
			build();
			
			if (ValidateUtils.isNotEmpty(cellVals)) {
				Row row = createRow();
				
				int oldCellRollIndex = cellRollIndex;
				//列循环
				for (int i = 0; i < cellVals.length; i++) {
					Object val = cellVals[i];
					Cell cell = createCell(row, cellRollIndex); 

					//赋值
					setCellValue(cell, val);

					// 列下标的滚动标识向前推一列
					cellRollIndex++;
				}
				
				// 列下标的滚动标识重置加原有下标
				cellRollIndex = oldCellRollIndex;
				// 行下标的滚动标识向前推一行
				rowRollIndex++; 
			}
			
			return this;
		} catch (Exception e) {
			throw new RemexPoiException("remex poi write error!", e);
		}
	}
	
	/**
	 * 文件输出
	 * @param os
	 */
	public void output(OutputStream os) {
		try {
			//导出
			workbook.write(os);
		} catch (Exception e) {
			throw new RemexPoiException("remex poi output error!", e);
		} finally {
			SourceCloseUtils.close(os);
		}
	}
	
	
	/**
	 * 根据不同支持构建workbook
	 * @return
	 */
	private Workbook buildWorkbook() {
		return PoiSupport.HSSF.equals(poiSupport) ? new HSSFWorkbook() : new XSSFWorkbook();
	}
	
	/**
	 * 给单元格赋值
	 * @param cell
	 * @param val
	 */
	private void setCellValue(Cell cell, Object val) {
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
