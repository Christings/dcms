package com.web.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 对在线用户的管理
 * 
 * @author qgl
 * @date 2015-1-9
 * @version 1.0
 */
public class ClientManager {

	private static ClientManager instance = new ClientManager();
	private Map<String, Object> map = new HashMap<String, Object>();

	private ClientManager() {

	}

	public static ClientManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param sessionId
	 * @param client
	 */
	public void addClinet(String sessionId, Object client) {
		map.put(sessionId, client);
	}

	/**
	 * sessionId
	 */
	public void removeClinet(String sessionId) {
		map.remove(sessionId);
	}

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public Object getClient(String sessionId) {
		return map.get(sessionId);
	}

	/**
	 * 
	 * @return
	 */
	public Collection<Object> getAllClient() {
		return map.values();
	}

}
