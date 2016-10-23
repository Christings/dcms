package com.web.util;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.web.core.util.ContextHolderUtils;
import com.web.entity.User;

/**
 * 解压工具类
 *
 * @author 田军兴
 * @date 2016-10-22
 */
public class ZIPUtil {
	public static Map<String, File> unZipFile(File file, String outPath) throws IOException {
		ZipFile zipFile = new ZipFile(file);
		Enumeration entrys = zipFile.entries();
		Map<String, File> map = new HashMap<String, File>();
		User user = WebUtils.getUser(ContextHolderUtils.getRequest());
		String tempPath = outPath + "/" + user.getId();// 临时文件夹
		File tempFile = new File(tempPath);
		tempFile.mkdir();
		while (entrys.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entrys.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			InputStream inputStream = zipFile.getInputStream(entry);
			File file1 = new File(outPath);
			if (!file1.exists()) {
				file1.mkdir();
			}
			String ext = file1.getName().substring(file1.getName().lastIndexOf(".") + 1, file1.getName().length());
			String newFileName = String.valueOf(DateUtil.getMillis(new Date())) + "." + ext;// 转存文件的新名称
			String filePath = outPath + "/" + newFileName;
			OutputStream outputStream = new FileOutputStream(filePath);
			file1 = new File(filePath);
			byte[] buf = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
			inputStream.close();
			outputStream.close();
			map.put(entry.getName(), file1);
		}
		return map;
	}
}
