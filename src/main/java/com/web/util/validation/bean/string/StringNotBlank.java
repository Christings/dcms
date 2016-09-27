package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

/**
 * 字符串不为  null， 不为空字符串"" 不都为空格 验证
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringNotBlank extends AbstractValidation<String> {
    public StringNotBlank(String property, String errorTip) {
        this.object = property ;
        this.errorTip = errorTip ;
    }

    @Override
    public boolean validate() {
        return !(new StringBlank(object, errorTip).validate()) ;
    }
}
