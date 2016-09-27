package com.web.util.validation;

/**
 * 验证接口
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public interface Validation {
    /**
     * 验证方法
     * @return 验证通过返回true，否者返回false
     */
    boolean validate() ;

    /**
     * 获取错误提示，当验证失败后，可以调用该方法
     * @return 错误提示
     */
    String getErrorTip() ;
}
