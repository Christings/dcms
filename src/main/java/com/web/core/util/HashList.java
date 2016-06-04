package com.web.core.util;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明： 封装结果集
 * 
 * @author QGL
 * @version 创建时间：2013-08-23 13:31
 */
public class HashList extends ArrayList<Map<String, Object>> {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(HashList.class);

	public HashList() {
		super(50);
	}

	public HashList(List<Map<String, Object>> list) {
		super(list);
	}

	public HashList(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 记录条数
	 * 
	 * @return 返回记录条数
	 */
	public int size() {
		return super.size();
	}

	/**
	 * 取指定行，指定列的 值
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @return String值
	 */
	public String get(int row, String name) {
		return get(row, name, "");
	}

	/**
	 * 取指定行，指定列的 值
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @param defaultValue
	 *            当值为null或""时，返回defaultValue
	 * @return String值
	 */
	public String get(int row, String name, String defaultValue) {
		if (row >= size()) {
			return "";
		}
		Object obj = super.get(row).get(name);

		if (obj != null && obj instanceof java.sql.Date) {
			return ((java.sql.Date) obj).toString();
		} else if (obj != null && obj instanceof java.sql.Time) {
			return ((java.sql.Time) obj).toString();
		} else if (obj != null && obj instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp) obj).toString();
		} else if (obj != null && obj instanceof java.util.Date) {
			return getDate14(row, name);
		} else if (obj != null && obj instanceof Clob) {
			Clob c = ((Clob) obj);
			try {
				return c.getSubString(1, (int) c.length());
			} catch (SQLException e) {
				logger.error("操作Clob时异常", e);
			}
		}
		String s = String.valueOf(obj);
		if (s == null || "".equals(s) || s.contains("null")) {
			return defaultValue;
		}
		return s;
	}

	/**
	 * 取指定行，指定列的 日期,
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @param format
	 *            日期的格式 ，例如yyyy-mm-dd hh:mm:ss
	 * @return String表示的日期
	 */
	public String getDate(int row, String name, String format) {
		Date date = getDate(row, name);
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 返回日期，格式"yyyy-MM-DD"
	 */
	public String getDate8(int row, String name) {
		return getDate(row, name, "yyyy-MM-dd");
	}

	public String getDate8(int row, String name, String defaultValue) {
		String s = getDate(row, name, "yyyy-MM-dd");
		if (s == null || "".equals(s)) {
			return defaultValue;
		}
		return s;
	}

	/**
	 * 返回日期，格式"yyyy-MM-DD HH:mm:ss"
	 */
	public String getDate14(int row, String name) {
		return getDate(row, name, "yyyy-MM-dd HH:mm:ss");
	}

	public String getDate14(int row, String name, String defaultValue) {
		String s = getDate(row, name, "yyyy-MM-dd HH:mm:ss");
		if (s == null || "".equals(s)) {
			return defaultValue;
		}
		return s;
	}

	/**
	 * 返回日期，格式"HH:mm:ss"
	 */
	public String getDate6(int row, String name) {
		return getDate(row, name, "HH:mm:ss");
	}

	public String getDate6(int row, String name, String defaultValue) {
		String s = getDate(row, name, "HH:mm:ss");
		if (s == null || "".equals(s)) {
			return defaultValue;
		}
		return s;
	}

	/**
	 * 取指定行，指定列的 日期
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @return Date对象
	 */
	public Date getDate(int row, String name) {
		if (row >= size()) {
			return null;
		}
		Object obj = super.get(row).get(name);
		if (obj != null && obj instanceof java.sql.Date) {
			return new java.util.Date(((java.sql.Date) obj).getTime());
		} else if (obj != null && obj instanceof java.sql.Time) {
			String sDateTime = ((java.sql.Time) obj).toString();
			Date date = null;
			if ((null != sDateTime) && (0 <= sDateTime.length())) {
				try {
					date = (Date) (new SimpleDateFormat("HH:mm:ss")).parseObject(sDateTime);
				} catch (final ParseException e) {
					return date;
				}
			}
			return date;
		} else if (obj != null && obj instanceof java.sql.Timestamp) {
			return new java.util.Date(((java.sql.Timestamp) obj).getTime());
		} else if (obj != null && obj instanceof java.util.Date) {
			return (java.util.Date) obj;
		}
		return null;
	}

	/**
	 * 取指定行，指定列的Timestamp， 格式是：1970-01-01 08:02:03.000
	 * 
	 * @param row
	 * @param name
	 * @return
	 */
	public Timestamp getTimestamp(int row, String name) {
		if (row >= size()) {
			return null;
		}
		Object obj = super.get(row).get(name);
		if (obj != null && obj instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp) obj);
		}
		return null;
	}

	/**
	 * 取指定行，指定列的 值
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @return int值
	 */
	public int getInt(int row, String name) {
		String s = get(row, name);
		return Integer.valueOf(s);
	}

	/**
	 * 说明：取指定行，指定列的值
	 * 
	 * @param row
	 * @param name
	 * @return
	 * 
	 * @author 曲国龙
	 * @date 2014年8月21日
	 */
	public Short getShort(int row, String name) {
		String s = get(row, name);
		if (null == s || "".equalsIgnoreCase(s) || s.length() <= 0)
			return null;
		else
			return Short.valueOf(s);
	}

	/**
	 * 取指定行，指定列的 值
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @return long值
	 */
	public long getLong(int row, String name) {
		String s = get(row, name);
		return Long.valueOf(s);
	}

	/**
	 * 取指定行，指定列的 值
	 * 
	 * @param row
	 *            指定行等号，从0开始
	 * @param name
	 *            指定列的名字（不区分大小写）
	 * @return double值
	 */
	public double getDouble(int row, String name) {
		String s = get(row, name);
		return Double.valueOf(s);
	}

	public byte[] getBytes(int row, String name) {
		Object obj = super.get(row).get(name);
		if (obj != null && obj instanceof Blob) {
			Blob b = ((Blob) obj);
			try {
				return b.getBytes(1, (int) b.length());
			} catch (SQLException e) {
				logger.error("操作Blob时异常", e);
			}
		}
		if (obj != null && obj instanceof byte[]) {
			return ((byte[]) obj);
		}
		return null;
	}

	/**
	 * 取某一行记录,具体的类型的转换由使用者决定
	 * 
	 * @param row
	 *            行号
	 * @return 一行记录，Map类型，实际是LinkedHashMap,是有顺序的。
	 */
	public Map<String, Object> getRow(int row) {
		if (row >= size()) {
			return null;
		}
		return super.get(row);
	}

	/**
	 * 取字段的名字，与数据库中的字段顺序一致。
	 * 
	 * @return
	 */
	public String[] getKeyNames() {
		if (size() > 0) {
			Map<String, Object> map = getRow(0);
			Set<String> set = map.keySet();
			String[] keys = new String[set.size()];
			int i = 0;
			for (String key : set) {
				keys[i] = key;
				i++;
			}
			return keys;
		}
		return new String[0];
	}

	public Object getObject(int row, String name) {
		if (row >= size()) {
			return "";
		}
		return super.get(row).get(name);
	}

	/**
	 * 向集合的末尾添加一行记录
	 * 
	 * @param map
	 * @return
	 */
	public boolean add(Map<String, Object> map) {
		return super.add(map);
	}

	/**
	 * toString()方法
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder strb = new StringBuilder();
		String[] keys = getKeyNames();
		for (int i = 0; i < size(); i++) {
			strb.append("第");
			strb.append(i);
			strb.append("行:");
			for (int j = 0; j < keys.length; j++) {
				String k = keys[j];
				strb.append(k);
				strb.append(":");
				strb.append(get(i, k));
				if (j != keys.length - 1) {
					strb.append(",");
				}
			}
			if (i != size() - 1) {
				strb.append("\r\n");
			}
		}
		return strb.toString();
	}

	public void print() {
		System.out.println(toString());
	}

}
