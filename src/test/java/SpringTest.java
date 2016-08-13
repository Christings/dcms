import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.web.service.DataDictService;
import com.web.service.UserService;

/**
 * 
 * @ClassName: SpringTest
 * @Description: Spring框架项目测试类
 * @author 童云鹏
 * @date 2016年7月11日 上午10:49:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		// "classpath:/META-INF/spring/applicationContext.xml",
		"classpath*:spring*.xml"
		// "file:src/main/webapp/WEB-INF/spring/webmvc-config.xml"
})
@WebAppConfiguration
public class SpringTest {

	@Autowired
	private UserService uservice;

	@Autowired
	private DataDictService dataDictService;

	@Test
	public void test1() {

	}
}
