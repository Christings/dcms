package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

/**
 * 字符串最小长度验证
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringMinLength extends AbstractValidation<String> {

    /**
     * 长度
     */
    private int minLength ;

    public StringMinLength(String property, int minLength, String errorTip) {
        this.object = property ;
        this.minLength = minLength ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        if (null != object) {
            return object.length() >= minLength ;
        }
        return false;
    }
}
