package com.web.util;

import com.web.entity.User;

import javax.servlet.http.HttpServletRequest;

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

}
