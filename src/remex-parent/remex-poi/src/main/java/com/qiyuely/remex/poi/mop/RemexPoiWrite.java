package com.qiyuely.remex.poi.mop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.record.ObjectProtectRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qiyuely.remex.core.constant.BConst;
import com.qiyuely.remex.poi.enums.PoiSupport;
import com.qiyuely.remex.poi.exception.RemexPoiException;
import com.qiyuely.remex.poi.interfaces.ICellValFetch;
import com.qiyuely.remex.poi.interfaces.IWriteCellDiy;
import com.qiyuely.remex.poi.interfaces.IWriteRowDiy;
import com.qiyuely.remex.poi.style.IPoiWriteStyle;
import com.qiyuely.remex.poi.style.PoiWriteDefaultStyle;
import com.qiyuely.remex.poi.val.CellValPropertyFetch;
import com.qiyuely.remex.poi.val.PoiWriteCellDefaultDiy;
import com.qiyuely.remex.poi.val.PoiWriteRowDefaultDiy;
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
	
	/** poi workbook */
	private Workbook workbook;
	
	/** poi sheet */
	private Sheet sheet;
	
	
	
	/** poi的支持操作类型，默认XSSF */
	private PoiSupport poiSupport = PoiSupport.XSSF;
	
	/** poi导出样式设置 */	
	private IPoiWriteStyle poiWriteStyle = new PoiWriteDefaultStyle();
	
	/** 写入行的默认的自定义处理 */
	private IWriteRowDiy poiWriteRowDefaultDiy = null;
	
	/** 写入列的默认的自定义处理 */
	private IWriteCellDiy poiWriteCellDefaultDiy = null;
	
	/**  默认行高度，默认乘256 */
	private int rowDefaultHeight = 1;
	
	/**  默认列宽度 */
	private int cellDefaultWidth = 8;
	
	/** 列宽度集，默认乘256 */
	protected int[] cellWidths;
	
	
	/** 当前滚动的行下标 */
	private int rowRollIndex = 0;
	
	public static void main(String[] args) throws Exception {
		FileOutputStream os = new FileOutputStream(new File("E://b.xlsx"));
		List<UrlEntity> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			UrlEntity entity = new UrlEntity();
			entity.setId("id:" + i);
			entity.setName("name:" + i);
			list.add(entity);
		}
		String title = "excel导出";
		int[] cellWidths = new int[] {8, 14, 18};
		String[] colNames = new String[] {"id", "名称", "url"};
		String[] properties = new String[] {"id", "name", "url"};
		
		RemexPoiWrite remexPoiWrite = new RemexPoiWrite().setCellWidths(cellWidths).build();
		remexPoiWrite
			.writeMerged(title, colNames.length)
			.writeRow(colNames)
			.writeDataList(list, properties)
//			.writeDefaultTitle(title, colNames.length)
//			.writeDefaultCellTitle(colNames)
//			.writeDefaultDataList(list, properties)
			.output(os);
		System.out.println("======  end   ======");
	}
	
	/**
	 * 构建poi导出excel处理器
	 */
	public RemexPoiWrite build() {
		//根据不同支持构建workbook
		workbook = buildWorkbook();
		
		//写入行的默认的自定义处理
		if (poiWriteRowDefaultDiy == null) {
			poiWriteRowDefaultDiy = new PoiWriteRowDefaultDiy(rowDefaultHeight);
		}
		
		//写入列的默认的自定义处理
		if (poiWriteCellDefaultDiy == null) {
			poiWriteCellDefaultDiy = new PoiWriteCellDefaultDiy(poiWriteStyle.getCellStyle(workbook));
		}

		sheet = workbook.createSheet();

		//默认列宽度
		sheet.setDefaultColumnWidth(cellDefaultWidth);
		//自定义列宽度
		if (ValidateUtils.isNotEmpty(cellWidths)) {
			for (int i = 0; i < cellWidths.length; i++) {
				int width = cellWidths[i];
				if (width <= 0) {
					width = cellDefaultWidth;
				}
				sheet.setColumnWidth(i, cellWidths[i] * 256);
			}
		}
			
		return this;
	}
	
	
	
	/**
	 * 写入默认的标题
	 * @param title 标题值
	 * @param cellCount 合并的列数量 >=1
	 * @return
	 */
	public <T> RemexPoiWrite writeDefaultTitle(String title, int cellCount) {
		writeMerged(title, cellCount);
		
		return this;
	}
	
	
	
	
	/**
	 * 写入列表数据
	 * @param dataList 数据列表
	 * @param properties 属性集
	 * @return
	 */
	public <T> RemexPoiWrite writeDataList(List<T> dataList, String[] properties) {
		int cellCount = properties == null ? 0 : properties.length;
		ICellValFetch<T> cellValFetch = new CellValPropertyFetch<>(properties);
		
		writeDataList(dataList, cellValFetch, cellCount);
		
		return this;
	}
	
	/**
	 * 写入列表数据
	 * @param dataList 数据列表
	 * @param cellValFetch 数据提取器
	 * @param cellCount 数据的列数量
	 * @return
	 */
	public <T> RemexPoiWrite writeDataList(List<T> dataList, ICellValFetch<T> cellValFetch, int cellCount) {
		if (CollectionUtils.isNotEmpty(dataList) && cellCount >= 0) {
			//行循环
			for (int i = 0; i < dataList.size(); i++) {
				T data = dataList.get(i);
				Object[] cellVals = new Object[cellCount];

				//列循环
				for (int j = 0; j < cellCount; j++) {
					//提取字段值
					Object val = null;
					if (cellValFetch == null) {
						val = data;
					} else {
						val = cellValFetch.fetch(data, i, j, rowRollIndex, j);
					}

					cellVals[j] = val;

				}

				writeRowHandle(cellVals, i, rowRollIndex, poiWriteRowDefaultDiy, poiWriteCellDefaultDiy, true);
			}
		}
		
		return this;
	}
	
	/**
	 * 写入一行数据
	 */
	public <T> RemexPoiWrite writeRow(Object[] rowCellVals) {
		writeRowHandle(rowCellVals, 0, rowRollIndex, poiWriteRowDefaultDiy, poiWriteCellDefaultDiy, true);
		
		return this;
	}
	
	/**
	 * 写入一行数据
	 */
	public <T> RemexPoiWrite writeRowOneCell(Object cellVal) {
		Object[] rowCellVals = new Object[] {cellVal};
		
		writeRow(rowCellVals);
		
		return this;
	}
	
	/**
	 * 写入单元格，合并单元格
	 * @param cellVal 单元格值
	 * @param cellCount 合并的列数量 >=1
	 * @return
	 */
	public <T> RemexPoiWrite writeMerged(Object cellVal, int cellCount) {
		writeMerged(cellVal, 1, cellCount);
		
		return this;
	}
	
	/**
	 * 写入单元格，合并单元格
	 * @param cellVal 单元格值
	 * @param rowCount 合并的行数量 >=1
	 * @param cellCount 合并的列数量 >=1
	 * @return
	 */
	public <T> RemexPoiWrite writeMerged(Object cellVal, int rowCount, int cellCount) {
		writeMerged(cellVal, rowRollIndex, rowCount, cellCount);
		
		return this;
	}
	
	/**
	 * 写入单元格，合并单元格
	 * @param cellVal 单元格值
	 * @param rowCount 合并的行数量 >=1
	 * @param cellCount 合并的列数量 >=1
	 * @return
	 */
	public <T> RemexPoiWrite writeMerged(Object cellVal, int curRowRollIndex, int rowCount, int cellCount) {
		writeMerged(cellVal, curRowRollIndex, 0, rowCount, cellCount);
		
		return this;
	}
	
	/**
	 * 写入单元格，合并单元格
	 * @param cellVal 单元格值
	 * @param rowCount 合并的行数量 >=1
	 * @param cellCount 合并的列数量 >=1
	 * @return
	 */
	public <T> RemexPoiWrite writeMerged(Object cellVal, int curRowRollIndex, int curCellRollIndex, int rowCount, int cellCount) {
		rowCount = rowCount < 1 ? 1 : rowCount;
		cellCount = cellCount < 1 ? 1 : cellCount;
		
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(curRowRollIndex, curRowRollIndex + rowCount - 1
				, curRowRollIndex, curCellRollIndex + cellCount - 1));
		
		writeRowOneCell(cellVal);
		
		return this;
	}
	
	/**
	 * 写入一行数据
	 */
	protected <T> RemexPoiWrite writeRowHandle(Object[] rowCellVals, int dataIndex, int curRowRollIndex
			, IWriteRowDiy poiWriteRowDiy, IWriteCellDiy poiWriteCellDiy, boolean isRollRow) {
		Row row = createRow(curRowRollIndex);
		
		poiWriteRowDiy.diyRow(row, rowCellVals, dataIndex, curRowRollIndex);
		
		if (ValidateUtils.isNotEmpty(rowCellVals)) {
			//列循环
			for (int i = 0; i < rowCellVals.length; i++) {
				Object cellVal = rowCellVals[i];
				
				writeCellHandle(row, dataIndex, i, curRowRollIndex, i, cellVal, poiWriteCellDiy);
			}
		}
		
		if (isRollRow) {
			rowRollIndex++;
		}
		
		return this;
	}
	
	/**
	 * 写入单元格
	 */
	protected <T> RemexPoiWrite writeCellHandle(Row row, int dataIndex, int dataCellIndex, int rowRollIndex, int curCellRollIndex
			, Object cellVal, IWriteCellDiy poiWriteCellDiy) {
		Cell cell = createCell(row, curCellRollIndex);
		
		//自定义处理列对象
		poiWriteCellDiy.diyCell(row, cell, cellVal, dataIndex, dataCellIndex, rowRollIndex, curCellRollIndex);
		
		return this;
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
	 * 创建行对象
	 * @return
	 */
	public Row createRow(int rowIndex) {
		Row row = sheet.createRow(rowIndex);
		return row;
	}
	
	/**
	 * 创建字段对象
	 * @return
	 */
	public Font createFont() {
		Font font = workbook.createFont();
		return font;
	}
	
	/**
	 * 创建单元格样式
	 */
	public CellStyle createCellStyle() {
		CellStyle style = workbook.createCellStyle();
		return style;
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
	 * 设置poi导出样式
	 * @param poiWriteStyle
	 */
	public RemexPoiWrite setPoiWriteStyle(IPoiWriteStyle poiWriteStyle) {
		if (poiWriteStyle == null) {
			this.poiWriteStyle = new PoiWriteDefaultStyle();
		} else {
			this.poiWriteStyle = poiWriteStyle;
		}
		
		return this;
	}
	
	/**
	 * 设置列宽度集，默认值{@link #cellDefaultWidth}
	 * @param cellWidths
	 */
	public RemexPoiWrite setCellWidths(int[] cellWidths) {
		this.cellWidths = cellWidths;
		
		return this;
	}
	
	/**
	 * 设置默认列宽度
	 * @param cellDefaultWidth
	 */
	public RemexPoiWrite setCellDefaultWidth(int cellDefaultWidth) {
		if (cellDefaultWidth > 0) {
			this.cellDefaultWidth = cellDefaultWidth;
		}
		
		return this;
	}
}
