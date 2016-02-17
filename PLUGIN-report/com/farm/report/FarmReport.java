package com.farm.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.Region;

import com.farm.plugin.jxls.ReportException;
import com.farm.plugin.jxls.ReportManagerInter;
import com.farm.web.spring.BeanFactory;
import com.farm.web.string.StringUtils;

public class FarmReport {
	private Map<String, Object> para = null;
	private String templeteFileName = null;

	private FarmReport() {
	}

	public static FarmReport newInstance(String templeteFileName) {
		FarmReport obj = new FarmReport();
		obj.para = new HashMap<String, Object>();
		obj.templeteFileName = templeteFileName;
		return obj;
	}

	public FarmReport addParameter(String key, Object val) {
		para.put(key, val);
		return this;
	}

	public InputStream generate() throws Exception {
		reportIMP.generate(templeteFileName, para);
		return new FileInputStream(new File(reportIMP
				.getReportPath(templeteFileName)));
	}

	/**
	 * 生成Excel类 
	 * @param sheetName 报表名称
	 * @param titleName 标题
	 * @param path 文件路径
	 * @param headName  头部列名
	 * @param contentName 显示内容字段
	 * @param resultList  结果集
	 */
	public static void generateExcel(String sheetName, String titleName,String path,
			Map<String,Integer> headName, List<String> contentName,
			List<Map<String, Object>> resultList) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName);
			sheet.setDefaultColumnWidth(20); // 默认列宽

			HSSFFont font = wb.createFont();
			font.setFontName("黑体");
			font.setFontHeightInPoints((short) 13);// 设置字体大小
			font.setColor(HSSFColor.WHITE.index);

			HSSFCellStyle titleStyle = wb.createCellStyle(); // 标题样式
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
			titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			titleStyle.setFillForegroundColor(HSSFColor.TEAL.index);// 设置前景色
			titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			titleStyle.setFont(font);// 选择需要用到的字体格式

			HSSFCellStyle headStyle = wb.createCellStyle(); // 头部样式
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
			headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			headStyle.setFillForegroundColor(HSSFColor.TEAL.index);// 设置背景色
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headStyle.setFont(font);// 选择需要用到的字体格式

			HSSFCellStyle contentStyle = wb.createCellStyle(); // 内容样式
			contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

			HSSFRow row_h = sheet.createRow((short) 0);
			HSSFCell ch = row_h.createCell(0);
			ch.setCellValue(titleName);
			ch.setCellStyle(titleStyle);
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (headName.size()-1)));// 指定合并区域

			HSSFRow row = sheet.createRow((short) 1);
			

			Iterator<Map.Entry<String, Integer>> iterator = headName.entrySet().iterator();
			int k = 0;
			while(iterator.hasNext()){
			    Map.Entry<String, Integer> m = iterator.next();
			    HSSFCell cell = row.createCell(k);
				cell.setCellValue(m.getKey());
				cell.setCellStyle(headStyle);
				if(m.getValue()!=null){ // 如果有值设置列宽，没有则为默认
					sheet.setColumnWidth(k, m.getValue()); 	
				}
				k++;
			}
			
			for (int i = 0; i < resultList.size(); i++) {
				Map<String, Object> map = resultList.get(i);
				HSSFRow rows = sheet.createRow((short) i + 2);
				for (int j = 0; j < contentName.size(); j++) {
					HSSFCell cell = rows.createCell(j);
					cell.setCellValue(StringUtils.isNull(map.get(contentName.get(j))).toString());
					cell.setCellStyle(contentStyle);
				}
			}
			
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private final static ReportManagerInter reportIMP = (ReportManagerInter) BeanFactory
			.getBean("excelReportId");
}
