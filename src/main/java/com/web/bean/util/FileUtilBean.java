package com.web.bean.util;

/**
 * 文件上传工具类实体
 *
 * @author 田军兴
 * @date 2016-10-24
 */
public class FileUtilBean {

	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 重命名后的新文件名
	 */
	private String newFileName;
	/**
	 * 文件路径
	 */
	private String fileRealPath;
	/**
	 * 文件扩展名
	 */
	private String fileExt;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
}
