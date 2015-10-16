package com.farm.plugin.jxls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;

public class ReportManagerImpl implements ReportManagerInter {

	public String path;
	public String opath;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void generate(String fileName, Map<String, Object> parameter)
			throws ReportException {
		XLSTransformer transformer = new XLSTransformer();
		Workbook wb;
		try {
			String classPath = this.getClass().getClassLoader().getResource(
					path + File.separator + fileName).getPath();
			String classPath2 = this.getClass().getClassLoader()
					.getResource("").getPath()
					+ opath;
			wb = transformer.transformXLS(new FileInputStream(new File(
					classPath)), parameter);
			File file = new File(classPath2);
			file.mkdirs();
			classPath2 = classPath2 + File.separator + fileName;
			file = new File(classPath2);
			file.createNewFile();
			wb.write(new FileOutputStream(file));
		} catch (Exception e) {
			throw new ReportException(e);
		}
	}

	public String getOpath() {
		return opath;
	}

	public void setOpath(String opath) {
		this.opath = opath;
	}

	@Override
	public String getReportPath(String fileName) {
		String classPath = this.getClass().getClassLoader().getResource(
				opath + File.separator + fileName).getPath();
		return classPath;
	}

}
