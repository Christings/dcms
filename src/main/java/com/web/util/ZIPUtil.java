package com.web.util;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
		while (entrys.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entrys.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			InputStream inputStream = zipFile.getInputStream(entry);
			File file1 = new File(outPath);
			//创建目录
			ZIPUtil.mkDir(file1);
			String ext = entry.getName().substring(entry.getName().lastIndexOf(".") + 1, entry.getName().length());
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

	/**
	 * 递归创建文件夹
	 * @param file 文件夹全路径
	 * */
	public static void mkDir(File file){
		if(file.getParentFile().exists()){
			file.mkdir();
		}else{
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}
}
