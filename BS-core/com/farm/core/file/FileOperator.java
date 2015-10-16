package com.farm.core.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**文件操作类
 * @author wangdong
 *
 */
public class FileOperator {
	static Logger log = Logger.getLogger(FileOperator.class);

	/**
	 * 拷贝文件 
	 * 
	 * @param upload文件流
	 *            
	 * @param newPath新文件路径和名称
	 *         
	 * @throws Exception
	 *          
	 */
	public static void copy(File upload, String newPath) throws Exception {
		FileOutputStream fos = new FileOutputStream(newPath);
		FileInputStream fis = new FileInputStream(upload);
		try {
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} finally {
			fos.close();
			fis.close();
		}
	}

	/**向文件中添加字符
	 * @param file 文件
	 * @param text 字符
	 * @throws Exception
	 */
	public static void append(File file, String text) throws Exception {
		FileWriter writer = null;
		try {
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			writer = new FileWriter(file, true);
			writer.append(text);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
			throw ex;
		}
	}

	/**向文件中写入字符
	 * @param file 文件
	 * @param text 字符
	 * @throws Exception
	 */
	public static void write(File file, String text) throws Exception {
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter writer = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream,
					"UTF-8");
			writer = new BufferedWriter(outputStreamWriter);
			writer.write(text);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				writer.flush();
				if (writer != null) {
					try {
						writer.close();
					} catch (Exception e) {
					}
				}
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (Exception e) {
					}
				}
				if (outputStreamWriter != null) {
					try {
						outputStreamWriter.close();
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			}
		}
	}

	/**读取文件
	 * @param file 文件
	 * @return
	 * @throws Exception
	 */
	public static String read(File file) throws Exception {
		StringBuffer lines = new StringBuffer();

		InputStream inputStream = null;
		BufferedReader reader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.append(line).append("\n");
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				inputStreamReader.close();
				inputStream.close();
				reader.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return lines.toString();
	}

	/**读取XML文件
	 * @param filePath 文件对象
	 * @return
	 */
	public static org.dom4j.Document readXmlByDom4j(File filePath) {
		InputStream inputStream = null;
		InputStreamReader bufferedreader = null;
		SAXReader saxreader = new SAXReader();
		org.dom4j.Document doc = null;
		try {
			inputStream = new FileInputStream(filePath);
			bufferedreader = new InputStreamReader(inputStream, "UTF-8");
			doc = (org.dom4j.Document) saxreader.read(bufferedreader);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				inputStream.close();
				bufferedreader.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return doc;
	}

	/**读取XML字符
	 * @param xmlStr 字符串
	 * @return
	 */
	public static org.dom4j.Document readXmlByDom4j(String xmlStr) {
		BufferedReader bufferedreader = null;
		SAXReader saxreader = new SAXReader();
		org.dom4j.Document doc = null;
		StringReader reader = null;
		try {
			reader = new StringReader(xmlStr);
			bufferedreader = new BufferedReader(reader);
			doc = (org.dom4j.Document) saxreader.read(bufferedreader);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				reader.close();
				bufferedreader.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return doc;
	}

	/**读取XML文件
	 * @param filePath 文件对象
	 * @return
	 */
	public static org.w3c.dom.Document readXmlByW3c(File filePath) {
		BufferedReader bufferedReader = null;
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			bufferedReader = new BufferedReader(new FileReader(filePath));
			InputSource inputsource = new InputSource(bufferedReader);
			doc = builder.parse(inputsource);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		return doc;
	}

	/**删除文件
	 * @param file 文件对象
	 * @throws Exception
	 */
	public static void delete(File file) throws Exception {
		file.delete();
	}
}
