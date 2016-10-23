package com.web.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.web.core.util.ContextHolderUtils;

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
	 * 存储图片文件到指定目录
	 *
	 * @author 田军兴
	 * @param url
	 *            文件需要存储的文件夹路径
	 */
	public static ArrayList<String> uploadImage(String url) {
		ArrayList<String> names = new ArrayList<String>();
		try {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (resolver.isMultipart(request)) {// 检查是否有需要上传的文件
				MultipartHttpServletRequest request1 = (MultipartHttpServletRequest) request;
				Iterator<String> iterator = request1.getFileNames();
				while (iterator.hasNext()) {// 迭代取出文件名
					String fileName = iterator.next();
					if (ImageUtil.checkIsImage(fileName)) {
						String extName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());// 获取文件后缀名
						MultipartFile file = request1.getFile(fileName);
						ImageUtil.resolveFilePath(url);// 检查路径是否存在，如果不存在就创建文件路径
						String realFileName = String.valueOf(DateUtil.getMillis(new Date()));
						url = url + "/" + realFileName + "." + extName;
						File outFile = new File(url);
						file.transferTo(outFile);// 生成文件到指定目录
						names.add(realFileName + "." + extName);
					} else {
						logger.info(fileName + "不是一个图片文件，未进行存储！");
					}
				}
			}
		} catch (Exception e) {
			logger.error("图片转存失败");
		}
		return names;
	}

	/**
	 * 检查文件路径是否存在，如果不存在就进行创建
	 *
	 * @param url
	 */
	private static boolean resolveFilePath(String url) {
		boolean result = false;
		if (!StringUtil.isEmpty(url)) {
			try {
				String contextRootPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
				String[] paths = url.split("/");
				String rPaths = contextRootPath;
				for (int i = 1; i < paths.length; i++) {
					String rPath = paths[i];
					File file = new File(rPaths + "/" + rPath);
					if (!file.exists()) {
						file.mkdir();
						rPaths = rPaths + "/" + rPath;
					}
					result = true;
				}
			} catch (Exception e) {
				logger.error("处理文件路径出错！url:" + url);
			}
		} else {
			logger.error("保存的文件路径不能为空！");
		}
		return result;
	}

	/**
	 * 根据url获取路径文件
	 */
	public static void getImage(String url, HttpServletResponse response) {
		response.setContentType("application/octet-stream;charset=UTF-8");
		try {
			String realPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
			byte[] data = ImageUtil.getImageByte(realPath + "/" + url);
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
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
		try {
			inputStream = new FileInputStream(file);
			int i = inputStream.available();
			bytes = new byte[i];
			inputStream.read(bytes);
			inputStream.close();
		} catch (Exception e) {
			logger.error("获取图片流失败！");
			e.printStackTrace();
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
