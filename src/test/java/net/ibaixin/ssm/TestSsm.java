package net.ibaixin.ssm;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.model.UserDto;
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
	
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	/*
	@Test
	public void testAdd() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername("李四");
		user.setPassword("123456");
		userService.add(user);
		logger.info(user.toString());
	}
	
	@Test
	public void testGetUser() {
		User user = userService.getUser("a1d7769c-8223-428f-967b-14055b1bb5d6");
		logger.info(user);
	}
	
	@Test
	public void testDeleteUser() {
		userService.delete("a1d7769c-8223-428f-967b-14055b1bb5d6");
		User user = userService.getUser("a1d7769c-8223-428f-967b-14055b1bb5d6");
		logger.info(user);
	}
	
	@Test
	public void testGetUsers() {
		List<User> users = userService.getUsers();
		logger.info(users);
	}
	
	@Test
	public void testGetUsersByPage() {
		UserDto userDto = new UserDto();
		userDto.setPageOffset(0);
		userDto.setPageSize(1);
		List<User> users = userService.getUsers(userDto);
		logger.info(users);
	}*/
}
