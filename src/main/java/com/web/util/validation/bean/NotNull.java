package com.web.util.validation.bean;

import com.web.util.validation.Validation;

/**
 * 验证对象不为null
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class NotNull implements Validation {
    /**
     * 要验证的属性
     */
    private Object object;

    /**
     * 错误提示信息
     */
    private String errorTip ;

    public NotNull(Object object, String errorTip) {
        this.object = object;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        return object != null;
    }

    @Override
    public String getErrorTip() {
        return errorTip;
    }
}
