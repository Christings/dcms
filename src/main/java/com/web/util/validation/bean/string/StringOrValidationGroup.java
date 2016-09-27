package com.web.util.validation.bean.string;

import com.web.util.validation.Validation;

/**
 * 符串验证类组合，验证条件之间是或的关系
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class StringOrValidationGroup extends StringValidationGroup {
    public StringOrValidationGroup(String object) {
        super(object);
    }

    @Override
    public boolean validate() {
        if (validationList.size() > 0) {
            for (Validation v : validationList) {
                if (v.validate()) {
                    return true ;
                }
            }
            return false;
        }

        return true ;
    }
}
