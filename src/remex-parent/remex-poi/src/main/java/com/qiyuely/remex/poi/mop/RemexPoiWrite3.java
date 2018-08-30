//package com.qiyuely.remex.poi.mop;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.qiyuely.remex.core.constant.BConst;
//import com.qiyuely.remex.poi.enums.PoiSupport;
//import com.qiyuely.remex.poi.exception.RemexPoiException;
//import com.qiyuely.remex.poi.interfaces.ICellValFetch;
//import com.qiyuely.remex.poi.style.IPoiWriteStyle;
//import com.qiyuely.remex.poi.style.PoiWriteDefaultStyle;
//import com.qiyuely.remex.poi.val.CellValPropertyFetch;
//import com.qiyuely.remex.utils.CollectionUtils;
//import com.qiyuely.remex.utils.SourceCloseUtils;
//import com.qiyuely.remex.utils.ValidateUtils;
//
///**
// * poi导出excel
// * 
// * @author Qiaoxin.Hong
// *
// */
//public class RemexPoiWrite3 {
//	
//	/** poi workbook */
//	private Workbook workbook;
//	
//	/** poi sheet */
//	private Sheet sheet;
//	
//	
//	
//	/** poi的支持操作类型，默认XSSF */
//	private PoiSupport poiSupport = PoiSupport.XSSF;
//	
//	/** poi导出样式设置 */	
//	private IPoiWriteStyle poiWriteStyle = new PoiWriteDefaultStyle();
//	
//	/**  默认列宽度 */
//	private int cellDefaultWidth = 8;
//	
//	/** 列宽度集，默认乘256 */
//	protected int[] cellWidths;
//	
//	
//	/** 当前滚动的行下标 */
//	private int rowRollIndex = 0;
//	
//	/** 当前滚动的列下标 */
//	private int cellRollIndex = 0;
//	
//	/** 当前所使用的行下标 */
//	private int rowUseIndex = 0;
//	
//	/** 使用中的行对象 */
//	private Row rowUsed = null;
//	
//	public static void main(String[] args) throws Exception {
//		FileOutputStream os = new FileOutputStream(new File("E://b.xlsx"));
//		List<UrlEntity> list = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			UrlEntity entity = new UrlEntity();
//			entity.setId("id:" + i);
//			entity.setName("name:" + i);
//			list.add(entity);
//		}
//		String title = "excel导出";
//		int[] cellWidths = new int[] {8, 14, 18};
//		String[] colNames = new String[] {"id", "名称", "url"};
//		String[] properties = new String[] {"id", "name", "url"};
//		
//		RemexPoiWrite3 remexPoiWrite = new RemexPoiWrite3().setCellWidths(cellWidths).build();
//		remexPoiWrite
//			.writeDefaultTitle(title, colNames.length)
//			.writeDefaultCellTitle(colNames)
//			.writeDefaultDataList(list, properties)
//			.output(os);
//		System.out.println("======  end   ======");
//	}
//	
//	/**
//	 * 构建poi导出excel处理器
//	 */
//	public RemexPoiWrite3 build() {
//		//根据不同支持构建workbook
//		workbook = buildWorkbook();
//
//		sheet = workbook.createSheet();
//
//		//默认列宽度
//		sheet.setDefaultColumnWidth(cellDefaultWidth);
//		//自定义列宽度
//		if (ValidateUtils.isNotEmpty(cellWidths)) {
//			for (int i = 0; i < cellWidths.length; i++) {
//				int width = cellWidths[i];
//				if (width <= 0) {
//					width = cellDefaultWidth;
//				}
//				sheet.setColumnWidth(i, cellWidths[i] * 256);
//			}
//		}
//			
//		return this;
//	}
//	
//	
//	/**
//	 * 写入默认的标题
//	 * @param title 标题值
//	 * @param cellCount 合并的列数量 >=1
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDefaultTitle(String title, int cellCount) {
//		writeMerged(title, 1, cellCount, poiWriteStyle.getTitleStyle(workbook));
//		
//		return this;
//	}
//	
//	/**
//	 * 写入默认的列标题
//	 * @param cellTitles 列标题值数组
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDefaultCellTitle(String[] cellTitles) {
//		writeRow(cellTitles, poiWriteStyle.getCellTitleStyle(workbook));
//		
//		return this;
//	}
//	
//	/**
//	 * 写入默认的列表数据
//	 * @param dataList
//	 * @param properties
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDefaultDataList(List<T> dataList, String[] properties) {
//		writeDataList(dataList, properties, poiWriteStyle.getDataStyle(workbook));
//		
//		return this;
//	}
//	
//	/**
//	 * 写入列表数据
//	 * @param dataList 数据列表
//	 * @param cellValFetch 数据提取器
//	 * @param cellCount 数据的列数量
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDefaultDataList(List<T> dataList, ICellValFetch<T> cellValFetch, int cellCount) {
//		writeDataList(dataList, cellValFetch, cellCount, poiWriteStyle.getDataStyle(workbook));
//		
//		return this;
//	}
//	
//	/**
//	 * 写入列表数据
//	 * @param dataList 数据列表
//	 * @param properties 属性集
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDataList(List<T> dataList, String[] properties) {
//		writeDataList(dataList, properties, null);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入列表数据
//	 * @param dataList 数据列表
//	 * @param properties 属性集
//	 * @param cellStyle 单元格样式
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDataList(List<T> dataList, String[] properties, CellStyle cellStyle) {
//		int cellCount = properties == null ? 0 : properties.length;
//		ICellValFetch<T> cellValFetch = new CellValPropertyFetch<>(properties);
//		
//		writeDataList(dataList, cellValFetch, cellCount, cellStyle);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入列表数据
//	 * @param dataList 数据列表
//	 * @param cellValFetch 数据提取器
//	 * @param cellCount 数据的列数量
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDataList(List<T> dataList, ICellValFetch<T> cellValFetch, int cellCount) {
//		writeDataList(dataList, cellValFetch, cellCount, null);
//		return this;
//	}
//	
//	/**
//	 * 写入列表数据
//	 * @param dataList 数据列表
//	 * @param cellValFetch 数据提取器
//	 * @param cellCount 数据的列数量
//	 * @param cellStyle 单元格样式
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeDataList(List<T> dataList, ICellValFetch<T> cellValFetch, int cellCount, CellStyle cellStyle) {
//		if (CollectionUtils.isNotEmpty(dataList) && cellCount >= 0) {
//			//行循环
//			for (int i = 0; i < dataList.size(); i++) {
//				T data = dataList.get(i);
//				Object[] cellVals = new Object[cellCount];
//
//				int itemCellRollIndex = cellRollIndex;
//				//列循环
//				for (int j = 0; j < cellCount; j++) {
//					//提取字段值
//					Object val = null;
//					if (cellValFetch == null) {
//						val = data;
//					} else {
//						val = cellValFetch.fetch(data, i, j, rowRollIndex, itemCellRollIndex);
//					}
//
//					cellVals[j] = val;
//
//					itemCellRollIndex++;
//				}
//
//				writeRow(cellVals, cellStyle);
//			}
//		}
//
//		return this;
//	}
//	
//	/**
//	 * 写入一行数据
//	 * @param rowCellVals 行单元格数据集
//	 */
//	public <T> RemexPoiWrite3 writeRow(Object[] rowCellVals) {
//		writeRow(rowCellVals, null);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入一行数据
//	 * @param rowCellVals 行单元格数据集
//	 * @param cellStyle 单元格样式
//	 */
//	public <T> RemexPoiWrite3 writeRow(Object[] rowCellVals, CellStyle cellStyle) {
//		writeRow(rowCellVals, cellStyle, true);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入一行数据
//	 * @param rowCellVals 行单元格数据集
//	 * @param isRollRow 是否滚动到下一行
//	 */
//	public <T> RemexPoiWrite3 writeRow(Object[] rowCellVals, boolean isRollRow) {
//		writeRow(rowCellVals, null, isRollRow);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入一行数据
//	 * @param rowCellVals 行单元格数据集
//	 * @param cellStyle 单元格样式
//	 * @param isRollRow 是否滚动到下一行
//	 */
//	public <T> RemexPoiWrite3 writeRow(Object[] rowCellVals, CellStyle cellStyle, boolean isRollRow) {
//		if (ValidateUtils.isNotEmpty(rowCellVals)) {
//			
//			int oldCellRollIndex = cellRollIndex;
//			
//			//列循环
//			for (int i = 0; i < rowCellVals.length; i++) {
//				Object cellVal = rowCellVals[i];
//				//写入单元格
//				writeCell(cellVal, cellStyle, false);
//			}
//			
//			//滚动到下一行
//			if (isRollRow) {
//				// 行下标的滚动标识向前推一行
//				rowRollIndex++; 
//				// 列下标的滚动标识重置加原有下标
//				cellRollIndex = oldCellRollIndex;
//			}
//		}
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格，合并单元格
//	 * @param cellVal 单元格值
//	 * @param rowCount 合并的行数量 >=1
//	 * @param cellCount 合并的列数量 >=1
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeMerged(Object cellVal, int rowCount, int cellCount) {
//		writeMerged(cellVal, rowCount, cellCount, null);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格，合并单元格
//	 * @param cellVal 单元格值
//	 * @param rowCount 合并的行数量 >=1
//	 * @param cellCount 合并的列数量 >=1
//	 * @param cellStyle 单元格样式
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeMerged(Object cellVal, int rowCount, int cellCount, CellStyle cellStyle) {
//		writeMerged(cellVal, rowCount, cellCount, cellStyle, true);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格，合并单元格
//	 * @param cellVal 单元格值
//	 * @param rowCount 合并的行数量 >=1
//	 * @param cellCount 合并的列数量 >=1
//	 * @param isRollRow 是否滚动到下一行
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeMerged(Object cellVal, int rowCount, int cellCount, boolean isRollRow) {
//		writeMerged(cellVal, rowCount, cellCount, null, isRollRow);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格，合并单元格
//	 * @param cellVal 单元格值
//	 * @param rowCount 合并的行数量 >=1
//	 * @param cellCount 合并的列数量 >=1
//	 * @param cellStyle 单元格样式
//	 * @param isRollRow 是否滚动到下一行
//	 * @return
//	 */
//	public <T> RemexPoiWrite3 writeMerged(Object cellVal, int rowCount, int cellCount, CellStyle cellStyle, boolean isRollRow) {
//		rowCount = rowCount < 1 ? 1 : rowCount;
//		cellCount = cellCount < 1 ? 1 : cellCount;
//		
//		//合并单元格
//		sheet.addMergedRegion(new CellRangeAddress(rowRollIndex, rowRollIndex + rowCount - 1
//				, cellRollIndex, cellRollIndex + cellCount - 1));
//		
//		writeCell(cellVal, cellStyle, isRollRow);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格
//	 * @param cellVal 单元格值
//	 */
//	public <T> RemexPoiWrite3 writeCell(Object cellVal) {
//		writeCell(cellVal, null);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格
//	 * @param cellVal 单元格值
//	 * @param cellStyle 单元格样式
//	 */
//	public <T> RemexPoiWrite3 writeCell(Object cellVal, CellStyle cellStyle) {
//		writeCell(cellVal, cellStyle, true);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格
//	 * @param cellVal 单元格值
//	 * @param isRollRow 是否滚动到下一行
//	 */
//	public <T> RemexPoiWrite3 writeCell(Object cellVal, boolean isRollRow) {
//		writeCell(cellVal, null, isRollRow);
//		
//		return this;
//	}
//	
//	/**
//	 * 写入单元格
//	 * @param cellVal 单元格值
//	 * @param cellStyle 单元格样式
//	 * @param isRollRow 是否滚动到下一行
//	 */
//	public <T> RemexPoiWrite3 writeCell(Object cellVal, CellStyle cellStyle, boolean isRollRow) {
//		Row row = createRowIfExists();
//		Cell cell = createCell(row, cellRollIndex);
//		
//		//赋值
//		setCellValue(cell, cellVal);
//		
//		//设置单元格样式
//		if (cellStyle == null) {
//			cellStyle = poiWriteStyle.getCellStyle(workbook); 
//		}
//		cell.setCellStyle(cellStyle);
//		
//		//滚动到下一行，列下标不动
//		if (isRollRow) {
//			// 行下标的滚动标识向前推一行
//			rowRollIndex++; 
//		} else {  //滚动到下一列，行下标不动
//			// 列下标的滚动标识向前推一列
//			cellRollIndex++;
//		}
//		
//		return this;
//	}
//	
//	
//
//	/**
//	 * 创建列对象
//	 * @param row 行对象
//	 * @param cellIndex 列下标
//	 * @return
//	 */
//	public Cell createCell(Row row, int cellIndex) {
//		Cell cell = row.createCell(cellIndex);
//		return cell;
//	}
//	
//	/**
//	 * 以当前滚动的行下标创建行对象
//	 * @return
//	 */
//	public Row createRow() {
//		Row row = sheet.createRow(rowRollIndex);
//		return row;
//	}
//	
//	/**
//	 * 如果滚动的行下标改变了，则重新创建行对象，否则返回当前行对象
//	 * @return
//	 */
//	public Row createRowIfExists() {
//		if (rowUsed == null || rowUseIndex != rowRollIndex) {
//			rowUsed = createRow();
//		}
//		
//		rowUseIndex = rowRollIndex;
//		
//		return rowUsed;
//	}
//	
//	/**
//	 * 创建字段对象
//	 * @return
//	 */
//	public Font createFont() {
//		Font font = workbook.createFont();
//		return font;
//	}
//	
//	/**
//	 * 创建单元格样式
//	 */
//	public CellStyle createCellStyle() {
//		CellStyle style = workbook.createCellStyle();
//		return style;
//	}
//	
//	
//	/**
//	 * 文件输出
//	 * @param os
//	 */
//	public void output(OutputStream os) {
//		try {
//			//导出
//			workbook.write(os);
//		} catch (Exception e) {
//			throw new RemexPoiException("remex poi output error!", e);
//		} finally {
//			SourceCloseUtils.close(os);
//		}
//	}
//	
//	
//	/**
//	 * 根据不同支持构建workbook
//	 * @return
//	 */
//	private Workbook buildWorkbook() {
//		return PoiSupport.HSSF.equals(poiSupport) ? new HSSFWorkbook() : new XSSFWorkbook();
//	}
//	
//	/**
//	 * 给单元格赋值
//	 * @param cell
//	 * @param val
//	 */
//	private void setCellValue(Cell cell, Object val) {
//		//根据数据类型绑入单元格中
//		if (val == null) {
//			cell.setCellValue(BConst.EMPTY);
//		} else if (val instanceof Date) {
//			cell.setCellValue((Date) val);
//		} else if (val instanceof Integer) {
//			cell.setCellValue((Integer) val);
//		} else if (val instanceof Double) {
//			cell.setCellValue((Double) val);
//		} else if (val instanceof Float) {
//			cell.setCellValue((Float) val);
//		} else if (val instanceof Long) {
//			cell.setCellValue((Long) val);
//		} else {
//			cell.setCellValue(val.toString());
//		}
//	}
//	
//	/**
//	 * 设置poi导出样式
//	 * @param poiWriteStyle
//	 */
//	public RemexPoiWrite3 setPoiWriteStyle(IPoiWriteStyle poiWriteStyle) {
//		if (poiWriteStyle == null) {
//			this.poiWriteStyle = new PoiWriteDefaultStyle();
//		} else {
//			this.poiWriteStyle = poiWriteStyle;
//		}
//		
//		return this;
//	}
//	
//	/**
//	 * 设置列宽度集，默认值{@link #cellDefaultWidth}
//	 * @param cellWidths
//	 */
//	public RemexPoiWrite3 setCellWidths(int[] cellWidths) {
//		this.cellWidths = cellWidths;
//		
//		return this;
//	}
//	
//	/**
//	 * 设置默认列宽度
//	 * @param cellDefaultWidth
//	 */
//	public RemexPoiWrite3 setCellDefaultWidth(int cellDefaultWidth) {
//		if (cellDefaultWidth > 0) {
//			this.cellDefaultWidth = cellDefaultWidth;
//		}
//		
//		return this;
//	}
//}
