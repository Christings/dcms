package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

/**
 * 字符串为  null， 为空字符串"" 都为空格 验证
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringBlank extends AbstractValidation<String> {
    public StringBlank(String property, String errorTip) {
        this.object = property ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        int strLen;
        if (object == null || (strLen = object.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(object.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
