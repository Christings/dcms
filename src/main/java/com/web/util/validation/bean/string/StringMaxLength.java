package com.web.util.validation.bean.string;


import com.web.util.validation.bean.AbstractValidation;

/**
 * 字符串最大长度验证
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringMaxLength extends AbstractValidation<String> {

    /**
     * 长度
     */
    private int maxLength;

    public StringMaxLength(String property, int maxLength, String errorTip) {
        this.object = property ;
        this.maxLength = maxLength;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        if (null != object) {
            return object.length() <= maxLength;
        }
        return false;
    }
}
