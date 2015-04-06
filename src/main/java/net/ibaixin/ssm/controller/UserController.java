package net.ibaixin.ssm.controller;

import java.util.List;

import javax.annotation.Resource;

import net.ibaixin.ssm.model.User;
import net.ibaixin.ssm.service.IUserService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = Logger.getLogger(UserController.class); 
	
	private IUserService userService;
	
	@Resource(name = "userService")
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("hello", "你好");
		return "user/showUser";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String show(Model model) {
		List<User> users = userService.getUsers();
		logger.info(users);
		return "user/showUser";
	}
}
