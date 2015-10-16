package com.farm.doc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class DocI18N {
	private static String PROPERTY_FILE = DocI18N.class.getResource("/")
			.getPath().toString()
			+ "doc-I18N.properties";

	/**
	 * 根据Key 读取Value
	 */
	public static String getData(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					PROPERTY_FILE));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改或添加键值对 如果key存在，修改 反之，添加。
	 */
	public static void setData(String key, String value) {
		Properties prop = new Properties();
		try {
			File file = new File(PROPERTY_FILE);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			fis.close();// 一定要在修改值之前关闭fis
			OutputStream fos = new FileOutputStream(PROPERTY_FILE);
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
			prop.store(fos, "just for test");

			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + PROPERTY_FILE + " for updating "
					+ value + " value error");
		}
	}
}
