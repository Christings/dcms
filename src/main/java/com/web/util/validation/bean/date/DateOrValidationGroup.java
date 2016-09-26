package com.web.util.validation.bean.date;

import com.web.util.validation.Validation;

import java.util.Date;

/**
 * 日期 验证类组合
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class DateOrValidationGroup extends DateValidationGroup {

    public DateOrValidationGroup(Date date) {
        super(date);
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
