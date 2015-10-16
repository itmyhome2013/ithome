package com.farm.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.farm.plugin.jxls.ReportException;
import com.farm.plugin.jxls.ReportManagerInter;
import com.farm.web.spring.BeanFactory;

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

	private final static ReportManagerInter reportIMP = (ReportManagerInter) BeanFactory
			.getBean("excelReportId");
}
