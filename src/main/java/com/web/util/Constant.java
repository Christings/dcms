package com.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 常量
 */
public class Constant {
	// 系统基础目录
	public static String FILE_UPLOAD_PATH = null;
	// 3source文件目录
	public static String SOURCE_PATH = "3source";
	// 3source文件密码
	public static String SOURCE_PASS = "3source.cn";

	static {
		Properties properties = new Properties();
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("dcms.properties");
		try {
			properties.load(inputStream);
			FILE_UPLOAD_PATH = (String) properties.get("file.base.path");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
