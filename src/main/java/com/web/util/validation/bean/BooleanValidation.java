package com.web.util.validation.bean;

/**
 * 布尔条件 验证
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class BooleanValidation extends AbstractValidation<Boolean> {
    public BooleanValidation(Boolean object, String errorTip) {
        super(object, errorTip);
    }

    @Override
    public boolean validate() {
        return object;
    }
}
