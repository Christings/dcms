

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

//	@Autowired
//	private UserService uservice;
//	@Autowired
//	private DataDictService ;
//	@Test
//	public void test1(){
//		Page<User> page=new Page<User>();
//		page.setPageNo(1);
//		page.setPageSize(10);
//		try {
//
//			uservice.getUserPage(page);
//			System.out.println(JSON.toJSONString(page));
//			System.out.println(JSON.toJSONString(uservice.getAllUsers()));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//	}
}
