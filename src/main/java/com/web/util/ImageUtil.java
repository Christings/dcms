package com.web.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static void main(String[] args) {

	}

	/**
	 * 解密数据库字段extra为JSON
	 * 
	 * @param extra
	 * @return
	 * @throws Exception
	 */
	public static String decryptionExtra(String extra) throws Exception {
		byte[] data = Base64Util.getStrByet(extra);
		byte[] output = BZip2Utils.decompress(data);
		String outputStr = new String(output);
		return outputStr;
	}

	/**
	 * 根据url获取路径文件
	 */
	public static void getImage(String url,String fileName, HttpServletResponse response) {
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		OutputStream outputStream = null;
		try {
			byte[] data = ImageUtil.getImageByte(url);
			outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			logger.error("读取图片文件失败!");
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件路径获取文件流
	 */
	public static byte[] getImageByte(String filePath) {
		FileInputStream inputStream = null;
		byte[] bytes = null;
		File file = new File(filePath);
		boolean isex = file.exists();
		try {
			inputStream = new FileInputStream(file);
			bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
		} catch (Exception e) {
			logger.error("获取图片流失败！");
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 获取数据库图片文件并显示到页面
	 */
	public static void getDBImage(byte[] image, HttpServletResponse response) {
		try {
			OutputStream outputStream = response.getOutputStream();
			response.setContentType("image/jpeg");
			outputStream.write(image);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			logger.error("数据库图片文件转换错误！");
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是图片文件
	 */
	public static boolean checkIsImage(String fileName) {
		boolean isImage = false;
		if (!StringUtil.isEmpty(fileName)) {
			fileName = fileName.toUpperCase();
			String extName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if ("JPEG".equals(extName) || "JPG".equals(extName) || "BMP".equals(extName) || "GIF".equals(extName)
					|| "PNG".equals(fileName)) {
				isImage = true;
			}
		}
		return isImage;
	}

}
