package com.web.util.validation.bean.number;


import com.web.util.validation.Validation;

/**
 * 数值验证类组合，验证条件之间是或的关系
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class NumberOrValidationGroup<T extends Number> extends NumberValidationGroup<T> {
    public NumberOrValidationGroup(T object) {
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
