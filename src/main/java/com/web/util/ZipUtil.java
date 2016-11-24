package com.web.util;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * zip 压缩工具
 * <p>
 * Created by damog on 2016/9/3.
 */
public class ZipUtil {

	public static void main(String[] args) throws ZipException {
		unZip(new File("d:/opt/IBM Power 550.zip"), "d:/opt/", "ims3d.3source@syhz[zly]");
	}

	public static File zipDir(String zipDir, String zipFile, String password) throws Exception {
		if (StringUtil.isEmpty(zipDir)) {
			throw new Exception("zip目录为空");
		}
		ZipFile zFile = new ZipFile(zipFile);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(true);
		parameters.setIncludeRootFolder(false);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
		parameters.setPassword(password);

		zFile.addFolder(new File(zipDir), parameters);

		return zFile.getFile();
	}

	public static void unZip(File zipFile, String destDir, String password) throws ZipException, ZipException {
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("GBK"); // 设置文件名编码，在GBK系统中需要设置
		if (!zFile.isValidZipFile()) { // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
			System.out.println("此文件是7z格式，使用7z进行解压");
			return;
		}
		File destFile = new File(destDir); // 解压目录
		if (destFile.isDirectory() && !destFile.exists()) {
			destFile.mkdir();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(password.toCharArray()); // 设置密码
		}
		zFile.extractAll(destDir); // 将文件抽出到解压目录(解压)
	}

}