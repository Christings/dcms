package com.web.util.validation.bean.number;

import com.web.util.validation.bean.AbstractValidation;

/**
 * 数值区间验证
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public class RangeValue<T extends Number> extends AbstractValidation<T> {
    /**
     * 最小值
     */
    private T minValue ;

    /**
     * 最大值
     */
    private T maxValue ;

    public RangeValue(T object, T minValue, T maxValue, String errorTip) {
        super(object, errorTip);
        this.minValue = minValue ;
        this.maxValue = maxValue ;
    }

    @Override
    public boolean validate() {
        if (null != object && null != minValue) {
            if (object instanceof Byte) {
                return object.byteValue() >= minValue.byteValue() && object.byteValue() <= maxValue.byteValue() ;
            } else if (object instanceof Short) {
                return object.shortValue() >= minValue.shortValue() && object.shortValue() <= maxValue.shortValue();
            } else if (object instanceof Integer) {
                return object.intValue() >= minValue.intValue() && object.intValue() <= maxValue.intValue();
            } else if (object instanceof Long) {
                return object.longValue() >= minValue.longValue() && object.longValue() <= maxValue.longValue();
            } else if (object instanceof Float) {
                return object.floatValue() >= minValue.floatValue() && object.floatValue() <= maxValue.floatValue();
            } else if (object instanceof Double) {
                return object.doubleValue() >= minValue.doubleValue() && object.doubleValue() <= maxValue.doubleValue();
            }
        }
        return false;
    }
}
