package com.web.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * 说明： 把执行的sql语句，准备记录日志
 * 
 * @author QGL
 * @version 创建时间：2011-3-7 上午09:59:05
 */
public class LogUtil {
	/**
	 * 过滤时使用的包名，方法栈中的类全限定名包含以下字符串，就输出到日志
	 */
	static final String[] packageName = { "com.gzhpower", "org.jeecgframework", "test" };

	/**
	 * 返回方法调用栈信息
	 * 
	 * @return 栈调用层次信息
	 */
	public static String getStackTrace() {
		return getStackTrace(false);
	}

	/**
	 * 返回方法调用栈信息
	 * 
	 * @param isOutCGLIBCall
	 *            是否记录CGLIB产生的代理类这一层次栈调用的信息
	 * @return 栈调用层次信息
	 */
	public static String getStackTrace(boolean isOutCGLIBCall) {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		StringBuilder strb = new StringBuilder();
		int ix = stack.length - 1;
		int k = 1;
		while (ix >= 0) {
			StackTraceElement frame = stack[ix];
			String className = frame.getClassName();
			String methodName = frame.getMethodName();
			int lingNumber = frame.getLineNumber();
			boolean is = false;
			for (String n : packageName) {
				if (className.startsWith(n) && ix != 0) {// 最后一次调用是本方法，要去掉
					is = true;
					break;
				}
			}
			if (!isOutCGLIBCall && -1 != className.indexOf("CGLIB")) {
				// 不记录CGLIB产生的代理类
			} else {
				if (is) {
					strb.append("  ");
					strb.append("(");
					strb.append(k);
					strb.append(")");
					strb.append(className);
					strb.append(".");
					strb.append(methodName);
					strb.append("()");
					strb.append(" ");
					strb.append("[Line:");
					strb.append(lingNumber);
					strb.append("]");
					strb.append("\r\n");
					k++;
				}
			}

			ix--;
		}
		int i = strb.lastIndexOf("\r\n");
		return strb.substring(0, i);
	}

	/**
	 * 准备要输出到日志的SQL，把SQL中的？替换为参数。如把select * from t1 where
	 * id=?中的？使用args数组中的值按顺序替换
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public static String logSql(String sql, Object... args) {
		StringBuilder strb = new StringBuilder();
		if (sql != null) {
			StringTokenizer st = new StringTokenizer(sql, "?");
			int count = 0;
			StringBuilder sbf = new StringBuilder(40);
			while (st.hasMoreElements()) {
				strb.append(st.nextToken());
				strb.append(getSqlValue(sbf, count, args));
				sbf.delete(0, sbf.length());
				count++;
			}
		}
		return strb.toString();
	}

	private static String getSqlValue(StringBuilder sbf, int index, Object... args) {
		if (args != null && index < args.length) {
			Object obj = args[index];
			if (obj != null && obj instanceof java.sql.Date) {
				sbf.append("'");
				sbf.append(((java.sql.Date) obj).toString());
				sbf.append("'");
			} else if (obj != null && obj instanceof java.sql.Time) {
				sbf.append("'");
				sbf.append(((java.sql.Time) obj).toString());
				sbf.append("'");
			} else if (obj != null && obj instanceof java.sql.Timestamp) {
				sbf.append("'");
				sbf.append(((java.sql.Timestamp) obj).toString());
				sbf.append("'");
			} else if (obj != null && obj instanceof java.util.Date) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sbf.append("'");
				sbf.append(formatter.format((Date) obj));
				sbf.append("'");
			} else if (obj != null && obj instanceof Calendar) {
				Calendar cal = (Calendar) obj;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sbf.append("'");
				sbf.append(formatter.format(cal.getTime()));
				sbf.append("'");

			} else if (obj != null && obj instanceof String) {
				int a = obj.toString().indexOf("\r\n");
				int b = obj.toString().indexOf("\n");
				int c = obj.toString().indexOf("\r");
				int r = Math.max(Math.max(a, b), c);
				sbf.append("'");
				if (r != -1) {
					// 处理记录日志时，值的内容太长其中有换行时，从换行从截断
					sbf.append(obj.toString().substring(0, r)).append("...");
				} else {
					// 默认走这里
					sbf.append(obj.toString());
				}
				sbf.append("'");
			} else if (obj == null) {
				sbf.append("null");
			} else {
				sbf.append(obj.toString());
			}
			return sbf.toString();
		}
		return "";
	}
}