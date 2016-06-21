package com.web.util;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 自定义返回前端 格式数据
 * 
 * @author 杜延雷
 * @date 2016-06-20
 */
public class AllResult {
	/**
	 * 响应业务状态，0：表示不成功,1：表示成功
	 */
	@JSONField(name = "status")
	private int status = 0;

	/**
	 * 响应消息，成功返回OK，否者返回出错消息
	 */
	@JSONField(name = "msg")
	private String msg;

	/**
	 * 响应中的数据
	 */
	private Object data;

	public static AllResult build(int status, String msg) {
		return new AllResult(status, msg, null);
	}

	public static AllResult build(int status, String msg, Object data) {
		return new AllResult(status, msg, data);
	}

	public static AllResult ok() {
		return new AllResult(null);
	}

	public static Object okJSON(Object data) {
		AllResult result = new AllResult(data);
		return result;
	}

	public static Object buildJSON(int status, String msg) {
		return buildJSON(status, msg, null);
	}

	public static Object buildJSON(int status, String msg, Object data) {
		AllResult result = new AllResult(status, msg, data);
		return result;
	}

	public AllResult() {
	}

	public AllResult(int status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public AllResult(Object data) {
		this.status = 1;
		this.msg = "OK";
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
