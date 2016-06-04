package com.web.util;

public class ImageUtil {

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

}
