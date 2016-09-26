package com.web.util.validation.bean.string;

import com.web.util.validation.RegExpConstants;
import com.web.util.validation.bean.AbstractValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串为数值，负数和小数
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringIsNumber extends AbstractValidation<String> {

    public StringIsNumber(String property, String errorTip) {
        this.object = property ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        if (null == object || object.length() == 0) {
            return false ;
        }

        Pattern pattern = Pattern.compile(RegExpConstants.REGEXP_NUMBER);
        Matcher matcher = pattern.matcher(object);
        return matcher.matches();
    }
}
