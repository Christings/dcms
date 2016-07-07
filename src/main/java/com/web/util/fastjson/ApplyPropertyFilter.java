package com.web.util.fastjson;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要返回JSON的字段
 *
 * @author 杜延雷
 * @date 2016/7/6.
 */
public class ApplyPropertyFilter implements PropertyFilter{

    /**
     * 要序列化的属性名称集合
     */
    private List<String> applyPropertis = null ;

    public ApplyPropertyFilter(String... propertyNames) {
        if(propertyNames.length > 0) {
            applyPropertis = new ArrayList<String>(propertyNames.length) ;

            for(String s : propertyNames) {
                applyPropertis.add(s) ;
            }
        }
    }

    @Override
    public boolean apply(Object object, String name, Object value) {
        if(null == applyPropertis) {
            return false ;
        }

        if(applyPropertis.contains(name)) {
            return true ;
        }

        return false ;
    }
}
