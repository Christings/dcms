package com.web.util.validation.bean.date;

import com.web.util.validation.bean.AbstractValidation;

import java.util.Date;

/**
 * 日期在给定条件日期之前 验证类
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class DateBeforeValidation extends AbstractValidation<Date> {

    /**
     * 条件 date
     */
    private Date afterDate ;

    public DateBeforeValidation(Date object, Date afterDate, String errorTip) {
        super(object, errorTip);
        this.afterDate = afterDate;
    }

    @Override
    public boolean validate() {
        if (null == object || null == afterDate) {
            return false ;
        }
        return object.before(afterDate);
    }
}
