package com.web.util;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.bean.util.FileUtilBean;
import com.web.core.util.ContextHolderUtils;

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
	public static String getFilenameExtension(String path) {
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
			inputStream.close();
			outputStream.close();
			map.put(FileUtil.getFilename(entry.getName()), file1);
		}
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
		try {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			String path = request.getSession().getServletContext().getRealPath("/");
			inputStream = new FileInputStream(new File(path + filePath));
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
	 * @param isZIP
	 *            是否是压缩文件
	 */
	public static ArrayList<FileUtilBean> uploadFiles(MultipartHttpServletRequest request, String targetPath, boolean isZIP)
			throws IOException {
		ArrayList<FileUtilBean> beans = new ArrayList<FileUtilBean>();
		Iterator<String> iterator = request.getFileNames();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			MultipartFile file = request.getFile(fileName);
			if (null != file && file.getSize() > 0) {
				String ext = FileUtil.getFilenameExtension(file.getOriginalFilename());
				String path = request.getSession().getServletContext().getRealPath("/") + targetPath;// 获取路径
				File inFile = new File(path);
				if (!inFile.exists()) {
					FileUtil.mkDir(inFile);
				}
				String newFileName = String.valueOf(DateUtil.getMillis(new Date())) + "." + ext;
				inFile = new File(path + "/" + newFileName);
				file.transferTo(inFile);
				if (isZIP) {
					Map<String, File> files = FileUtil.unZipFile(inFile, path);
					Iterator it = files.keySet().iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						ext = FileUtil.getFilenameExtension(key);
						File newFile = (File) files.get(key);
						FileUtilBean bean = new FileUtilBean();
						bean.setFileName(key);
						bean.setNewFileName(newFile.getName());
						bean.setFileExt(ext);
						bean.setFileRealPath(targetPath + "/" + newFile.getName());
						beans.add(bean);
					}
					if (inFile.exists()) {
						inFile.delete();
					}
				} else {
					FileUtilBean bean = new FileUtilBean();
					bean.setFileName(file.getName());
					bean.setNewFileName(newFileName);
					bean.setFileExt(ext);
					bean.setFileRealPath(targetPath + "/" + newFileName);
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
			code = "GBK";
		}
		return code;
	}

	/**
	 * 删除多个文件
	 */
	public static int deleteFiles(ArrayList<FileUtilBean> files) {
		int count = 0;
		String contextPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
		for (FileUtilBean bean : files) {
			String path = bean.getFileRealPath();
			File file = new File(contextPath + path);
			if (file.exists()) {
				file.delete();
				count++;
			}
		}
		return count;
	}
}
