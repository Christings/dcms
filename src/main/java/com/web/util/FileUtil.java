package com.web.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.bean.util.FileUtilBean;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 文件相关操作辅助类。
 *
 * @author 童云鹏
 * @date 2016-06-05
 */
public class FileUtil {
	private static final String FOLDER_SEPARATOR = "/";
	private static final char EXTENSION_SEPARATOR = '.';

	/**
	 * 功能：复制文件或者文件夹。
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param inputFile
	 *            源文件
	 * @param outputFile
	 *            目的文件
	 * @param isOverWrite
	 *            是否覆盖(只针对文件)
	 * @throws IOException
	 */
	public static void copy(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
		if (!inputFile.exists()) {
			throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
		}
		copyPri(inputFile, outputFile, isOverWrite);
	}

	/**
	 * 功能：为copy 做递归使用。
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param inputFile
	 * @param outputFile
	 * @param isOverWrite
	 * @throws IOException
	 */
	private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
		// 是个文件。
		if (inputFile.isFile()) {
			copySimpleFile(inputFile, outputFile, isOverWrite);
		} else {
			// 文件夹
			if (!outputFile.exists()) {
				outputFile.mkdir();
			}
			// 循环子文件夹
			for (File child : inputFile.listFiles()) {
				copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
			}
		}
	}

	/**
	 * 功能：copy单个文件
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param inputFile
	 *            源文件
	 * @param outputFile
	 *            目标文件
	 * @param isOverWrite
	 *            是否允许覆盖
	 * @throws IOException
	 */
	private static void copySimpleFile(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		// 目标文件已经存在
		if (outputFile.exists()) {
			if (isOverWrite) {
				if (!outputFile.delete()) {
					throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
				}
			} else {
				// 不允许覆盖
				return;
			}
		}
		InputStream in = new FileInputStream(inputFile);
		OutputStream out = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
		out.close();
	}

	/**
	 * 功能：删除文件
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param file
	 *            文件
	 */
	public static void delete(File file) {
		deleteFile(file);
	}

	/**
	 * 功能：删除文件，内部递归使用
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param file
	 *            文件
	 * @return boolean true 删除成功，false 删除失败。
	 */
	private static void deleteFile(File file) {
		if (file == null || !file.exists()) {
			return;
		}
		// 单文件
		if (!file.isDirectory()) {
			boolean delFlag = file.delete();
			if (!delFlag) {
				throw new RuntimeException(file.getPath() + "删除失败！");
			} else {
				return;
			}
		}
		// 删除子目录
		for (File child : file.listFiles()) {
			deleteFile(child);
		}
		// 删除自己
		file.delete();
	}

	/**
	 * 从文件路径中抽取文件的扩展名, 例如. "mypath/myfile.txt" -> "txt". * @author 童云鹏
	 *
	 * @date 2016-06-05
	 * @param path
	 *            文件路径
	 * @return 如果path为null，直接返回null。
	 */
	public static String getFileExt(String path) {
		if (path == null) {
			return null;
		}
		int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		if (extIndex == -1) {
			return null;
		}
		int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (folderIndex > extIndex) {
			return null;
		}
		return path.substring(extIndex + 1);
	}

	/**
	 * 从文件路径中抽取文件名, 例如： "mypath/myfile.txt" -> "myfile.txt"。 * @author 童云鹏
	 *
	 * @date 2016-06-05
	 * @param path
	 *            文件路径。
	 * @return 抽取出来的文件名, 如果path为null，直接返回null。
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	/**
	 * 功能：保存文件。
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param content
	 *            字节
	 * @param file
	 *            保存到的文件
	 * @throws IOException
	 */
	public static void save(byte[] content, File file) throws IOException {
		if (file == null) {
			throw new RuntimeException("保存文件不能为空");
		}
		if (content == null) {
			throw new RuntimeException("文件流不能为空");
		}
		InputStream is = new ByteArrayInputStream(content);
		save(is, file);
	}

	/**
	 * 功能：保存文件
	 *
	 * @author 童云鹏
	 * @date 2016-06-05
	 * @param streamIn
	 *            文件流
	 * @param file
	 *            保存到的文件
	 * @throws IOException
	 */
	public static void save(InputStream streamIn, File file) throws IOException {
		if (file == null) {
			throw new RuntimeException("保存文件不能为空");
		}
		if (streamIn == null) {
			throw new RuntimeException("文件流不能为空");
		}
		// 输出流
		OutputStream streamOut = null;
		// 文件夹不存在就创建。
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		streamOut = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
			streamOut.write(buffer, 0, bytesRead);
		}
		streamOut.close();
		streamIn.close();
	}

	/**
	 * 解压文件到指定文件夹
	 */
	public static Map<String, File> unZipFile(File file, String outPath) throws IOException {
		Map<String, File> map = new HashMap<String, File>();
		ZipFile zipFile = new ZipFile(file);
		Enumeration entrys = zipFile.entries();
		while (entrys.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entrys.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			InputStream inputStream = zipFile.getInputStream(entry);
			File file1 = new File(outPath);
			// 创建目录
			FileUtil.mkDir(file1);
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
			map.put(FileUtil.getFilename(entry.getName()), file1);
			inputStream.close();
			outputStream.close();
		}
		zipFile.close();
		return map;
	}

	/**
	 * 递归创建文件夹
	 * 
	 * @param file
	 *            文件夹全路径
	 */
	public static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param filePath
	 *            需要下载的文件路径
	 * @param fileName
	 *            下载后的文件名称
	 */
	public static boolean downloadFile(HttpServletResponse response, String filePath, String fileName) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String path = Constant.FILE_UPLOAD_PATH + File.separator + filePath;// 获取路径
		try {
			if (StringUtil.isEmpty(fileName)) {
				fileName = FileUtil.getFilename(path);
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			inputStream = new FileInputStream(new File(path));
			outputStream = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int lenth;
			while ((lenth = inputStream.read(bytes)) > 0) {
				outputStream.write(bytes, 0, lenth);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				outputStream.close();
				inputStream.close();
				return true;
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param targetPath
	 *            文件转存的目录
	 * @param needUnZIP
	 *            是否需要解压文件
	 */
	public static ArrayList<FileUtilBean> uploadFiles(MultipartHttpServletRequest request, String targetPath, boolean needUnZIP)
			throws IOException {
		ArrayList<FileUtilBean> beans = new ArrayList<FileUtilBean>();
		Iterator<String> iterator = request.getFileNames();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			MultipartFile file = request.getFile(fileName);
			if (null != file && file.getSize() > 0) {
				String ext = FileUtil.getFileExt(file.getOriginalFilename());
				String path = Constant.FILE_UPLOAD_PATH + File.separator + targetPath;// 获取路径
				if (!"zip".equalsIgnoreCase(ext)) {
					needUnZIP = false;
				}
				File inFile = new File(path);
				if (!inFile.exists()) {
					FileUtil.mkDir(inFile);
				}
				String newFileName = String.valueOf(DateUtil.getMillis(new Date())) + "." + ext;
				inFile = new File(path + "/" + newFileName);
				file.transferTo(inFile);
				if (needUnZIP) {
					Map<String, File> files = FileUtil.unZipFile(inFile, path);
					Iterator it = files.keySet().iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						ext = FileUtil.getFileExt(key);
						File newFile = (File) files.get(key);
						FileUtilBean bean = new FileUtilBean();
						bean.setFileName(key);
						bean.setNewFileName(newFile.getName());
						bean.setFileExt(ext);
						bean.setFileRealPath(targetPath + "/" + newFile.getName());
						bean.setFileParentPath(targetPath);
						beans.add(bean);
					}
					if (inFile.exists()) {
						inFile.getAbsoluteFile().delete();
					}
				} else {
					FileUtilBean bean = new FileUtilBean();
					bean.setFileName(file.getName());
					bean.setNewFileName(newFileName);
					bean.setFileExt(ext);
					bean.setFileRealPath(targetPath + "/" + newFileName);
					bean.setFileParentPath(targetPath);
					beans.add(bean);
				}
			}
		}
		return beans;
	}

	/**
	 * 获取文本文件编码级（txt/json/yml/xml）
	 * 
	 * @param file
	 *            文件对象
	 **/
	public static String getFileCharset(File file) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			if (isUTF8(file.getAbsolutePath())) {
				code = "UTF-8";
			} else {
				code = "GBK";
			}
		}
		return code;
	}

	/**
	 * 删除多个文件
	 */
	public static int deleteFiles(ArrayList<FileUtilBean> files) {
		int count = 0;
		for (FileUtilBean bean : files) {
			String path = bean.getFileRealPath();
			File file = new File(path);
			if (file.exists()) {
				file.getAbsoluteFile().delete();
				count++;
			}
		}
		return count;
	}

	/**
	 * 检查文件是否存在
	 */
	public static boolean checkFileExist(String path) {
		if (StringUtil.isEmpty(path)) {
			return false;
		}
		path = Constant.FILE_UPLOAD_PATH+File.separator;
		File file = new File(path);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件是否是无BOM的UTF-8编码
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isUTF8(String fileName) {
		boolean state = true;
		BufferedInputStream bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(fileName));
			int count = 10;// 设置判断的字节流的数量
			int firstByte = 0;
			// 根据字节流是否符合UTF-8的标准来判断
			while (true) {
				if (count-- < 0 || (firstByte = bin.read()) == -1) {
					break;
				}
				// 判断字节流
				if ((firstByte & 0x80) == 0x00) {
					// 字节流为0xxxxxxx
					continue;
				} else if ((firstByte & 0xe0) == 0xc0) {
					// 字节流为110xxxxx 10xxxxxx
					if ((bin.read() & 0xc0) == 0x80)
						continue;
				} else if ((firstByte & 0xf0) == 0xe0) {
					// 字节流为1110xxxx 10xxxxxx 10xxxxxx
					if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80)
						continue;
				} else if ((firstByte & 0xf8) == 0xf0) {
					// 字节流为11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
					if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80)
						continue;
				} else if ((firstByte & 0xfc) == 0xf8) {
					// 字节流为111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
					if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80
							&& (bin.read() & 0xc0) == 0x80)
						continue;
				} else if ((firstByte & 0xfe) == 0xfc) {
					// 字节流为1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
					if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80
							&& (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80)
						continue;
				}
				state = false;
				break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			state = false;
			e.printStackTrace();
		} finally {
			try {
				if (bin != null)
					bin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return state;
	}

	/**
	 * 读取excel数据
	 * 
	 * @param inputStream
	 * @param isFromOne
	 *            是否从第一行读取
	 */
	public static ArrayList<String[]> getExcelData(InputStream inputStream, boolean isFromOne) throws Exception {
		Workbook workbook = Workbook.getWorkbook(inputStream);
		Sheet[] sheets = workbook.getSheets();
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		System.out.println("sheets.length:" + sheets.length);
		for (int i = 0; i < sheets.length; i++) {
			Sheet sheet = sheets[i];
			int startValue = 1;
			if (!isFromOne) {
				startValue = 2;
			}
			System.out.println("sheet.getRows():" + sheet.getRows());
			for (int j = startValue; j < sheet.getRows(); j++) {
				Cell[] row = sheet.getRow(j);
				ArrayList<String> valueList = new ArrayList<String>();
				for (int k = 0; k < row.length; k++) {
					String value = row[k].getContents();
					valueList.add(value);
				}
				String[] rowValues = valueList.toArray(new String[valueList.size()]);
				dataList.add(rowValues);
			}
		}
		inputStream.close();
		return dataList;
	}
}
