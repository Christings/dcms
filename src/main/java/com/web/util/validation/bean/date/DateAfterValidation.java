package com.web.util.validation.bean.date;

import com.web.util.validation.bean.AbstractValidation;

import java.util.Date;

/**
 * 日期在给定条件日期之后 验证类
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class DateAfterValidation extends AbstractValidation<Date> {

    /**
     * 条件 date
     */
    private Date beforeDate;

    public DateAfterValidation(Date object, Date beforeDate, String errorTip) {
        super(object, errorTip);
        this.beforeDate = beforeDate;
    }

    @Override
    public boolean validate() {
        if (null == object || null == beforeDate) {
            return false ;
        }
        return object.after(beforeDate);
    }
}
