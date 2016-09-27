package com.web.util.validation.bean.string;

import com.web.util.validation.RegExpConstants;
import com.web.util.validation.bean.AbstractValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串为数字字符串
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringIsDigits extends AbstractValidation<String> {

    public StringIsDigits(String property, String errorTip) {
        this.object = property ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        if (null == object || object.length() == 0) {
            return false ;
        }

        Pattern pattern = Pattern.compile(RegExpConstants.REGEXP_DIGITS);
        Matcher matcher = pattern.matcher(object);
        return matcher.matches();
    }
}
