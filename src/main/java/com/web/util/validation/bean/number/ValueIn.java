package com.web.util.validation.bean.number;

import com.web.util.validation.bean.AbstractValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含数值的验证
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class ValueIn<T extends Number> extends AbstractValidation<T> {
    /**
     * 数值集合
     */
    private List<T> values ;

    public ValueIn(T object, String errorTip, T... values) {
        super(object, errorTip);

        if (null != values) {
            this.values = new ArrayList<>(values.length) ;
            for (T t : values) {
                this.values.add(t);
            }
        }
    }

    @Override
    public boolean validate() {
        if (null != values && values.size() > 0) {
            for (T t : values) {
                if (t.equals(object)) {
                    return true ;
                }
            }
        }
        return false;
    }
}
