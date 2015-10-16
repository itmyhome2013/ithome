package com.farm.core.file;

import java.io.File;
import org.apache.log4j.Logger;

public class Files {
	static Logger log = Logger.getLogger(Files.class);

	/**格式化文件分割符号
	 * @param path 路径
	 * @return
	 */
	public static String initSeparator(String path) {
		int i = 0;
		while (path.indexOf("\\") >= 0&&!File.separator.equals("\\")) {
			path = path.replace("\\", File.separator);
			i++;
			if (i > 100) {
				throw new RuntimeException("while too long Is a Exception");
			}
		}
		i = 0;
		while (path.indexOf("/") >= 0&&!File.separator.equals("/")) {
			path = path.replace("/", File.separator);
			i++;
			if (i > 100) {
				throw new RuntimeException("while too long Is a Exception");
			}
		}
		return path;
	}
	/**格式化文件分割符号为\
	 * @param path 路径
	 * @return
	 */
	public static String initSeparatorLeft(String path) {
		int i = 0;
		String separator="\\";
		while (path.indexOf("\\") >= 0&&!separator.equals("\\")) {
			path = path.replace("\\", separator);
			i++;
			if (i > 100) {
				throw new RuntimeException("while too long Is a Exception");
			}
		}
		i = 0;
		while (path.indexOf("/") >= 0&&!separator.equals("/")) {
			path = path.replace("/", separator);
			i++;
			if (i > 100) {
				throw new RuntimeException("while too long Is a Exception");
			}
		}
		return path;
	}
	/**格式化文件分割符号为/
	 * @param path 路径
	 * @return
	 */
	public static String initSeparatorRight(String path) {
		int i = 0;
		String separator="/";
		while (path.indexOf("\\") >= 0&&!separator.equals("\\")) {
			path = path.replace("\\", separator);
			i++;
			if (i > 100) {
				throw new RuntimeException("while too long Is a Exception");
			}
		}
		i = 0;
		while (path.indexOf("/") >= 0&&!separator.equals("/")) {
			path = path.replace("/", separator);
			i++;
			if (i > 100) {
				throw new RuntimeException("while too long Is a Exception");
			}
		}
		return path;
	}
}
