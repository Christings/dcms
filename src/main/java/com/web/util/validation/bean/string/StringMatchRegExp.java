package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串匹配正则表达式
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringMatchRegExp extends AbstractValidation<String> {
    /**
     * 正则表达式
     */
    private String regExp ;

    public StringMatchRegExp(String object, String regExp, String errorTip) {
        this.object = object;
        this.regExp = regExp ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        if (null == object || object.length() == 0) {
            return false ;
        }

        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(object);
        return matcher.matches();
    }
}
