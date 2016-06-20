package com.web.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.web.core.interceptors.DateConvertEditor;
import com.web.core.util.ContextHolderUtils;
import com.web.core.util.HashList;
import com.web.core.util.page.Page;
import com.web.service.UserService;
import com.web.util.JSONUtil;

/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * 
 * @author qgl
 * 
 */
@Controller
public class BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class);

	@Resource
	protected UserService userService;

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	/**
	 * 向前台写入JSON数据
	 * 
	 * @param str
	 * @param response
	 */
	protected void writerResponse(String str, HttpServletResponse response) {
		logger.info(str);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out)
				out.close();
		}
	}

	/**
	 * 说明：把列表与分页招照easyui的datagrid数据格式返回到前台
	 * 
	 * @param list
	 * @param page
	 * @param response
	 * 
	 * @author 曲国龙
	 * @date 2014年8月27日
	 */
	protected void writerResponse(HashList list, Page page, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		String str = JSONUtil.list2Json(list);

		sb.append("{");
		sb.append("\"total\":").append(page == null ? list.size() : page.getCount()).append(",");
		sb.append("\"pageCount\":").append(page == null ? 0 : page.getPageCount()).append(",");
		sb.append("\"rows\":").append(str);
		sb.append("}");
		writerResponse(sb.toString(), response);
	}

	/**
	 * 说明： 处理页面变量的工具包，把从Request中得到的页面变量转化为合适的类型
	 * 
	 * @author QGL
	 * @version 创建时间：2013-08-23 13:31
	 */
	protected static class R {
		public static final HttpServletRequest getRequest() {
			return ContextHolderUtils.getRequest();
		}

		// 项目根路径
		public static final String getRoot() {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			return (String) request.getSession().getServletContext().getAttribute("root");
		}

		@SuppressWarnings("rawtypes")
		public static final Map getParameterMap() {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			return request.getParameterMap();
		}

		@SuppressWarnings("rawtypes")
		public static final Map<String, String> getMap() {
			Map map = getParameterMap();
			Map<String, String> newMap = new HashMap<String, String>();
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				String strKey = (String) element.getKey();
				String strObj = ((String[]) element.getValue())[0];
				newMap.put(strKey, strObj);
			}
			return newMap;
		}

		/**
		 * 读取前台请求对象中指定名称的参数值，并转化指定名称的变量为字符串型,如果该参数值? 请求对象中不存在，返回默认值。
		 * 
		 * @param paramName
		 *            指定名称
		 * @param defaultValue
		 *            默认值
		 * @return java.lang.String
		 * 
		 */
		public static final String get(String paramName, String defaultValue) {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			String value = request.getParameter(paramName);
			if (value == null) {
				return defaultValue;
			} else {
				return value.trim();
			}
		}

		public static final String get(String paramName) {
			return get(paramName, null);
		}

		/**
		 * 读取前台请求对象中指定名称的参数值，并转化指定名称的变量为单精度浮点型,如果该参? 值在请求对象中不存在，返回默认值。
		 * 
		 * 前台请求对象
		 * 
		 * @param paramName
		 *            参数名称
		 * @param defaultValue
		 *            默认值
		 * @return float
		 * @roseuid 3E7B102300FD
		 */
		public static final float getFloat(String paramName, float defaultValue) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			try {
				return Float.parseFloat(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return defaultValue;
		}

		/**
		 * 读取前台请求对象中指定名称的参数值，并转化指定名称的变量为布尔型,如果该参数值在? 求对象中不存在，返回默认值。
		 * 
		 * 前台请求对象
		 * 
		 * @param paramName
		 *            指定名称
		 * @param defaultValue
		 *            默认值
		 * @return boolean
		 * @roseuid 3E7B10230111
		 */
		public static final boolean getBoolean(String paramName, boolean defaultValue) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			if (temp == null) {
				return defaultValue;
			}
			temp = temp.toLowerCase();
			if (temp.equals("true")) {
				return true;
			}
			if (temp.equals("false")) {
				return false;
			}
			return defaultValue;
		}

		/**
		 * 读取前台请求对象中指定名称的参数值，并转化指定名称的变量为双精度浮点型,如果该参? 值在请求对象中不存在，返回默认值。
		 * 
		 * 前台请求对象
		 * 
		 * @param paramName
		 *            指定名称
		 * @param defaultValue
		 *            默认值
		 * @return double
		 * @roseuid 3E7B10230126
		 */
		public static final double getDouble(String paramName, double defaultValue) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			try {
				if (temp != null) {
					return Double.parseDouble(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return defaultValue;
		}

		/**
		 * 读取前台请求对象中指定名称的参数值，并转化指定名称的变量为整型,如果该参数值在请? 对象中不存在，返回默认值。
		 * 
		 * 前台请求对象
		 * 
		 * @param paramName
		 *            指定名称
		 * @param defaultValue
		 *            默认值
		 * @return int
		 * @roseuid 3E7B1023013B
		 */
		public static final int getInt(String paramName, int defaultValue) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			try {
				return Integer.parseInt(temp);
			} catch (Exception e) {
			}
			return defaultValue;
		}

		/**
		 * 读取前台请求对象中指定名称的参数值，并转化指定名称的变量为长整型,如果该参数值在? 求对象中不存在，返回默认值。
		 * 
		 * 前台请求对象
		 * 
		 * @param paramName
		 *            指定名称
		 * @param defaultValue
		 *            默认值
		 * @return long
		 * @roseuid 3E7B10230157
		 */
		public static final long getLong(String paramName, long defaultValue) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			try {
				return Long.parseLong(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return defaultValue;
		}

		public static final String[] getArray(String paramName) {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			String[] arr = request.getParameterValues(paramName);
			if (arr == null) {
				return new String[0];
			}
			return arr;
		}

		/**
		 * 读取前台对象客户指定名称的参数值，并转化为日期类型
		 * 
		 * @param paramName
		 * @return
		 */
		public static final Date getDate(String paramName) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			if (temp != null && !"".equals(temp)) {
				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return df.parse(temp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 读取前台对象客户指定名称的参数值，并转化为日期类型
		 * 
		 * @param paramName
		 * @param pattern
		 * @return
		 */
		public static final Date getDate(String paramName, String pattern) {
			HttpServletRequest req = ContextHolderUtils.getRequest();
			String temp = req.getParameter(paramName);
			if (StringUtils.isEmpty(temp)) {
				return null;
			}
			try {
				DateFormat df = new SimpleDateFormat(pattern);
				return df.parse(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
