package com.web.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	/**
	 * 把字节数组转换为字符串，并对其进行Base64编码处理
	 * 
	 * @param data
	 *            文件的字节数组
	 * 
	 * @return 字符串
	 */
	public static String getBase64Str(byte[] data) {
		if (null == data || data.length <= 0)
			return "";
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 把XML文件中的字节字符串转换为字节数组
	 * 
	 * @param base64Str
	 *            字节转化的字符串
	 * @return 字节数组
	 */
	/**
	 * @param base64Str
	 * @return
	 */
	public static byte[] getStrByet(String base64Str) {
		if (base64Str == null) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(base64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
