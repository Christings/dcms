package com.web.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取项目配置文件
 *
 * @author 田军兴
 * @date 2016-10-31
 */
public class PropertiesUtil {

	public static final String FILE_UPLOAD_PATH = "fileUpload.path";

	public static String getProperty(String propKey) {
		try {
			Properties properties = new Properties();
			InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("dcms.properties");
			properties.load(inputStream);
			return (String) properties.get(propKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
