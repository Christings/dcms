package com.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static Calendar calendar = null;

	/**
	 * yyyy-MM-dd 转 yyyyMMdd
	 * 
	 * @param sourceDate
	 * @param targetDate
	 * @return
	 */
	public static String s2s(String date, String sourceFormat, String targetFormat) {
		if (date == null) {
			return "";
		}
		return getDate(parseDateTime(date, sourceFormat), targetFormat);
	}

	/**
	 * 把Date格式化String
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 把String格式化Date
	 * 
	 * @param sDateTime
	 *            日期时间字符串
	 * @param sFormat
	 *            日期时间字符串格式
	 * @return Date
	 */
	public static Date parseDateTime(final String sDateTime, final String sFormat) {
		Date date = null;
		if ((null != sDateTime) && (0 <= sDateTime.length())) {
			try {
				date = (Date) (new SimpleDateFormat(sFormat)).parseObject(sDateTime);
			} catch (final ParseException e) {
				return date;
				// 不需要抛出异常
			}
		}
		return date;
	}

	/**
	 * 把String格式化Date
	 * 
	 * @param sDate
	 * @return
	 */
	public static Date ParseDate(final String sDate) {
		return parseDateTime(sDate, "yyyy-MM-dd");
	}

	/**
	 * 格式化时间 默认yyyy-MM-dd HH:mm:ss
	 * 
	 * @param 日期
	 * @return 字符串
	 */
	public static String format(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sf.format(date);
		return dateStr;
	}

	/**
	 * 格式化时间_给定格式
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @return 字符串
	 */
	public static String format(Date date, String format) {
		if (null == format || "".equals(format))
			format = "yyyy-MM-dd";
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转时间YYYY-MM-DD HH:mm:ss
	 * 
	 * @param str
	 * @return 日期
	 */
	public static Date parse(String str) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = df.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转时间 给定格式
	 * 
	 * @param str
	 * @param format
	 * @return 日期
	 */
	public static Date parse(String str, String format) {
		try {
			DateFormat df = new SimpleDateFormat(format);
			Date date = df.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 天数加减_时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDayToDate(Date date, Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 天数加减_字符串
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String addDayToString(Date date, Integer day, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return df.format(cal.getTime());
	}

	/**
	 * 月数加减_字符串
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String addMonthToString(Date date, Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return DateUtil.format(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 月数加减_时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonthToDate(Date date, Integer month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 小时的加减---Date
	 * 
	 * @param @param date
	 * @param @param hour
	 * @return Date
	 * @throws
	 */
	public static Date addHourTDate(Date date, Integer hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return cal.getTime();
	}

	/**
	 * 小时的加减 ---字符串
	 * 
	 * @param @param date
	 * @param @param hour
	 * @return String
	 * @throws
	 */
	public static String addHourTString(Date date, Integer hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return DateUtil.format(cal.getTime());
	}

	/**
	 * 分的加减 ---字符串
	 * 
	 * @param @param date
	 * @param @param hour
	 * @return String
	 * @throws
	 */
	public static String addMinuteTString(Date date, Integer minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return DateUtil.format(cal.getTime());
	}

	/**
	 * 分的加减---Date
	 * 
	 * @param @param date
	 * @param @param hour
	 * @return Date
	 * @throws
	 */
	public static Date addMinuteTDate(Date date, Integer minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 秒的加减---Date
	 * 
	 * @param @param date
	 * @param @param hour
	 * @return Date
	 * @throws
	 */
	public static Date addSecondTDate(Date date, Integer second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 秒的加减 ---字符串
	 * 
	 * @param @param date
	 * @param @param hour
	 * @return String
	 * @throws
	 */
	public static String addSecondTString(Date date, Integer second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return DateUtil.format(cal.getTime());
	}

	/**
	 * 当前月的最后一天
	 * 
	 * @param @param date
	 * @return String
	 */
	public static String monthLastDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTimeStr = DateUtil.format(endTime, "yyyy-MM-dd") + " 23:59:59";
		return endTimeStr;

	}

	/**
	 * 当前月的第一天
	 * 
	 * @param @param date
	 * @return String
	 */
	public static String monthBeginDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTimeStr = DateUtil.format(beginTime, "yyyy-MM-dd") + " 00:00:00";
		return beginTimeStr;
	}

	/**
	 * 当天开始时间
	 * 
	 * @param @param date
	 * @return Date
	 */
	public static Date dayBeginTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 当天结束时间
	 * 
	 * @param @param date
	 * @return Date
	 */
	public static Date dayLastTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 得到当前月的天数
	 * 
	 * @param @param month 代表月份：如：2013-12 yyyy-MM return int
	 */
	public static int getDaysByMonth(String month) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM").parse(month));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int theMonth = calendar.get(Calendar.MONTH) + 1;
		int theYear = calendar.get(Calendar.YEAR);
		int day = 0;
		if (theMonth == 1) {
			day = 31;
		} else if (theMonth == 2) {
			if ((theYear % 4 == 0 && theYear % 100 != 0) || theYear % 400 == 0) {// 闰年判断
				day = 29;
			} else {
				day = 28;
			}
		} else if (theMonth == 3) {
			day = 31;
		} else if (theMonth == 4) {
			day = 30;
		} else if (theMonth == 5) {
			day = 31;
		} else if (theMonth == 6) {
			day = 30;
		} else if (theMonth == 7) {
			day = 31;
		} else if (theMonth == 8) {
			day = 31;
		} else if (theMonth == 9) {
			day = 30;
		} else if (theMonth == 10) {
			day = 31;
		} else if (theMonth == 11) {
			day = 30;
		} else if (theMonth == 12) {
			day = 31;
		}
		return day;
	}

	/**
	 * 根据当前月得到上个月的第一天日期时间字符串
	 */
	public static String getLastMonthFirstDay(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		Date theDate = calendar.getTime();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = df.format(gcLast.getTime());
		return firstDay;
	}

	/**
	 * 根据当前月得到上个月的最后一天日期时间字符串
	 */
	public static String getlastMonthEndDay(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		String endDay = df.format(calendar.getTime());
		return endDay;
	}

	/**
	 * 功能描述：返回年份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 功能描述：返回月份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 功能描述：返回日份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 功能描述：返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期 yyyy/MM/dd 格式
	 */
	public static String getDate(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：返回字符型时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型时间 HH:mm:ss 格式
	 */
	public static String getTime(Date date) {
		return format(date, "HH:mm:ss");
	}
}
