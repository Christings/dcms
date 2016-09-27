package com.web.util.validation.bean.date;

import com.web.util.validation.Validation;
import com.web.util.validation.bean.AbstractValidationGroup;
import com.web.util.validation.bean.BooleanValidation;
import com.web.util.validation.bean.NotNull;
import com.web.util.validation.bean.Null;

import java.util.Date;

/**
 * 日期 验证类组合
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class DateValidationGroup extends AbstractValidationGroup {
    /**
     * 要验证的对象
     */
    protected Date date ;

    public DateValidationGroup(Date date) {
        this.date = date;
    }

    @Override
    public boolean validate() {
        if (validationList.size() > 0) {
            for (Validation v : validationList) {
                if (!v.validate()) {
                    return false ;
                }
            }
        }

        return true ;
    }

    /**
     * 验证条件 日期对象 为 null
     * @return 日期 验证类组合对象
     */
    public DateValidationGroup isNull() {
        validationList.add(new Null(date, errorTip)) ;
        return getSelf() ;
    }

    /**
     * 验证条件 日期对象 不为 null
     * @return 日期 验证类组合对象
     */
    public DateValidationGroup notNull() {
        validationList.add(new NotNull(date, errorTip)) ;
        return getSelf() ;
    }

    /**
     * 验证条件 日期对象 before 给定的日期
     * @param afterDate 比较的日期对象
     * @return 日期 验证类组合对象
     */
    public DateValidationGroup before(Date afterDate) {
        validationList.add(new DateBeforeValidation(date, afterDate, errorTip)) ;
        return getSelf() ;
    }

    /**
     * 验证条件 日期对象 after 给定的日期
     * @param beforeDate 比较的日期对象
     * @return 日期 验证类组合对象
     */
    public DateValidationGroup after(Date beforeDate) {
        validationList.add(new DateAfterValidation(date, beforeDate, errorTip)) ;
        return getSelf() ;
    }

    /**
     * 验证条件， 布尔表达式，验证表达式约定要和要验证的对象有关
     * 此方法作为一个扩展来使用
     * @param exp 布尔表达式验证
     * @return 日期 验证类组合对象
     */
    public DateValidationGroup expression(boolean exp) {
        validationList.add(new BooleanValidation(exp, errorTip)) ;
        return getSelf() ;
    }

    public DateValidationGroup getSelf() {
        return this ;
    }
}
