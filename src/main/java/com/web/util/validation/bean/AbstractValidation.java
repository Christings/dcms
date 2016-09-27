package com.web.util.validation.bean;


import com.web.util.validation.Validation;

/**
 * 验证类抽象实现
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public abstract class AbstractValidation<T> implements Validation {
    /**
     * 验证的对象
     */
    protected T object;
    /**
     * 验证未通过提示消息
     */
    protected String errorTip ;

    public AbstractValidation() {
    }

    public AbstractValidation(T object, String errorTip) {
        this.object = object;
        this.errorTip = errorTip;
    }

    @Override
    public String getErrorTip() {
        return errorTip;
    }

    public void setErrorTip(String errorTip) {
        this.errorTip = errorTip;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
