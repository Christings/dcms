package com.web.util.validation.bean.string;

import com.web.util.validation.bean.AbstractValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含字符的验证
 *
 * @author 杜延雷
 * @date 2016/10/24.
 */
public class StringValueIn<T extends String> extends AbstractValidation<T> {
    /**
     * 数值集合
     */
    private List<T> values ;

    public StringValueIn(T object, String errorTip, T... values) {
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
