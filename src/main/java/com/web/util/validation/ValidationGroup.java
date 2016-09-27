package com.web.util.validation;

/**
 * 验证组接口
 *
 * @author 杜延雷
 * @date 2016/9/26.
 */
public interface ValidationGroup extends Validation {
    /**
     * 设置验证未通过提示
     * @return 验证未通过提示
     */
    void setErrorTip(String errorTip) ;
}
