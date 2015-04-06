package net.ibaixin.ssm;

import java.util.UUID;

import javax.annotation.Resource;

import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.service.IUserService;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestSsm {
	private static Logger logger = Logger.getLogger(TestSsm.class);
	
	private IUserService userService;
	
	@Resource(name = "userService")
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void testAdd() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername("张三");
		user.setPassword("123456");
		userService.add(user);
		logger.info(user.toString());
	}
}
