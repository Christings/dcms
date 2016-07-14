package com.web.core.util;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;

/**
 * Mybatis 插件工具类
 *
 * @author 杜延雷
 * @date 2016-07-14
 */
public final class PluginUtil {
	
	private PluginUtil() {} // private constructor

	public static Object processTarget(Object target) {
		if(Proxy.isProxyClass(target.getClass())) {
			MetaObject mo = SystemMetaObject.forObject(target);
			return processTarget(mo.getValue("h.target"));
		}
		return target;
	}
	
}
