package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

/**
 * 控制字符串校验
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringEmpty extends AbstractValidation<String> {
    public StringEmpty(String object, String errorTip) {
        super(object, errorTip);
    }

    @Override
    public boolean validate() {
        if (null == object || object.length() == 0) {
            return true ;
        }
        return false;
    }
}
