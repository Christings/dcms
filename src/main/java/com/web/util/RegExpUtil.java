package com.web.util;

/**
 * 自定义正则表达式  工具类
 *
 * @author 杜延雷
 * @date 2016-06-21
 */
public class RegExpUtil {

    //静态资源 正则
    public static String resourceFile = "(.+\\.js|.+\\.png|.+\\.css|.+\\.jpg|.+\\.gif|.+\\.ico)$";

    //邮箱
    public static String email = "\\w+@\\w+(\\.\\w+)+";

    //汉字
    public static String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

    //正整数
    public static String number = "[0-9]+";

    //字母或数字
    public static String e_n = "^[a-zA-Z0-9]+";
}
