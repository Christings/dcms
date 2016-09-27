package com.web.util.validation.bean;

import com.web.util.validation.Validation;
import com.web.util.validation.ValidationGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证组类抽象实现
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public abstract class AbstractValidationGroup implements ValidationGroup {
    /**
     * 验证对象集合
     */
    protected List<Validation> validationList = new ArrayList<>();

    /**
     * 验证未通过提示消息
     */
    protected String errorTip ;

    @Override
    public String getErrorTip() {
        return errorTip;
    }

    @Override
    public void setErrorTip(String errorTip) {
        this.errorTip = errorTip;
    }
}
