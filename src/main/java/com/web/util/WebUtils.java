package com.web.util;

import com.web.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Web相关的工具类
 *
 * @author 杜延雷
 * @date 2016-06-20
 */
public class WebUtils {

    /**
     * Session 保存用户登录
     */
    public final static String SESSION_KEY_USER = "session_key_user";

    /**
     * Session 保存用户权限URL
     */
    public final static String SESSION_KEY_PRIVILEGES = "session_key_privileges";

    /**
     * Session 保存用户MenuIDs
     */
    public final static String SESSION_KEY_MENU_IDS = "session_key_menu_ids";

    /**
     * 从Session中获取登录用户
     */
    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(SESSION_KEY_USER);
    }

    /**
     * 将用户登录存到session中
     *
     * @param request Http请求
     * @param user   登录的用户
     */
    public static void addUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(SESSION_KEY_USER, user);
    }

    /**
     * 将登录用户从session中删除
     *
     * @param request
     */
    public static void removeUser(HttpServletRequest request){
        request.getSession().removeAttribute(SESSION_KEY_USER);
    }

    /**
     * 从Session中获取登录用户权限
     */
    public static Set<String> getPrivilege(HttpServletRequest request) {
        return (Set<String>) request.getSession().getAttribute(SESSION_KEY_PRIVILEGES);
    }

    /**
     * 将用户权限URL存到session中
     *
     * @param request Http请求
     * @param privilege   登录的用户
     */
    public static void addPrivilege(HttpServletRequest request, Set<String> privilege) {
        request.getSession().setAttribute(SESSION_KEY_PRIVILEGES, privilege);
    }

    /**
     * 将登录用户权限从session中删除
     *
     * @param request
     */
    public static void removePrivilege(HttpServletRequest request){
        request.getSession().removeAttribute(SESSION_KEY_PRIVILEGES);
    }

    /**
     * 从Session中获取登录用户菜单IDs
     */
    public static Set<String> getMenuIds(HttpServletRequest request) {
        return (Set<String>) request.getSession().getAttribute(SESSION_KEY_MENU_IDS);
    }

    /**
     * 将用户菜单ID存到session中
     *
     * @param request Http请求
     * @param privilege   登录的用户
     */
    public static void addMenuIds(HttpServletRequest request, Set<String> privilege) {
        request.getSession().setAttribute(SESSION_KEY_MENU_IDS, privilege);
    }

    /**
     * 将登录用户菜单ID从session中删除
     *
     * @param request
     */
    public static void removeMenuIds(HttpServletRequest request){
        request.getSession().removeAttribute(SESSION_KEY_MENU_IDS);
    }
}
