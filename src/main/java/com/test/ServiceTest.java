package com.test;

import com.alibaba.fastjson.JSON;
import com.web.entity.Cabinet;
import com.web.example.CabinetExample;
import com.web.service.CabinetService;
import com.web.util.AllResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

/**
 * ${description}
 *
 * @author 田军兴
 * @date ${date}
 */
public class ServiceTest {

    @Test
    public void test(){
        ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/resources/spring*.xml");
        CabinetService service = (CabinetService) ac.getBean("cabinetService");
        CabinetExample example = new CabinetExample();
        CabinetExample.Criteria criteria = example.createCriteria();
        criteria.andRoomIdEqualTo("5407d75f28eb49fa976848a4435ebdf0");
        List<Cabinet> cabinetList = service.selectCodesByExample(example);
        System.out.println(JSON.toJSON(AllResult.okJSON(cabinetList)));
    }
}
