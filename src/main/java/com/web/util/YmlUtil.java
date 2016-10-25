package com.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * YML文件转化工具类
 *
 * @author 田军兴
 * @date 2016-10-22
 */
public class YmlUtil {

	/**
	 * 解析yml文件为JSON字符串
	 * 
	 * @param url
	 *            yml文件路径
	 */
	public static Map getYmlString(String url) throws IOException {
		Yaml yml = new Yaml();
		if (StringUtil.isNotEmpty(url)) {
			File file = new File(url);
			Map map = null;
			String charset = FileUtil.getFileCharset(file);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
			map = (Map) yml.load(reader);
			return map;
		}
		return null;
	}
}
