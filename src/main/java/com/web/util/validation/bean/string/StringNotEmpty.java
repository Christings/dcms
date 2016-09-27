package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

/**
 * 字符串不能为null或者空字符串
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringNotEmpty extends AbstractValidation<String> {
    public StringNotEmpty(String property, String errorTip) {
        this.object = property ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        return !(object == null || object.length() == 0) ;
    }
}
