package com.web.core.util.fastjson;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 忽略集合中字段返回JSON的字段
 *
 * @author 杜延雷
 * @date 2016/7/6.
 */
public class IgnorePropertyFilter implements PropertyFilter{

    /**
     * 要忽略的属性名称集合
     */
    private List<String> ignorePropertis = null ;

    public IgnorePropertyFilter(String... propertyNames) {
        if(propertyNames.length > 0) {
            ignorePropertis = new ArrayList<String>(propertyNames.length) ;

            for(String s : propertyNames) {
                ignorePropertis.add(s) ;
            }
        }
    }

    @Override
    public boolean apply(Object object, String name, Object value) {
        if(null == ignorePropertis) {
            return true ;
        }

        if(ignorePropertis.contains(name)) {
            return false ;
        }

        return true ;
    }
}
