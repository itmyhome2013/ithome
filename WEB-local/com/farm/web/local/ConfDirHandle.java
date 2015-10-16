package com.farm.web.local;

import java.io.File;
import java.util.List;

public class ConfDirHandle {
	/**
	 * 对配置文件做处理
	 * 
	 * @param strPath
	 *            配置文件夹路径
	 * @param index
	 *            配置文件关键字
	 * @param handle
	 * @param context
	 */
	public static void findDirForConf(String strPath, String[] indexs,
			ConfHandleInter handle, Object context) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				findDirForConf(files[i].getAbsolutePath(), indexs, handle,
						context);
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				for (String index : indexs) {
					if (strFileName.replace("\\", ".").replace("/", ".")
							.indexOf(index) >= 0) {
						handle.execute(context, files[i]);
					}
				}
			}
		}
	}
}
