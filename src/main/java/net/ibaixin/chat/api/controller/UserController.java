package net.ibaixin.chat.api.controller;

import java.util.List;

import net.ibaixin.chat.api.model.User;
import net.ibaixin.chat.api.service.IUserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = Logger.getLogger(UserController.class); 
	
	private IUserService userService;
	
	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("hello", "你好");
		return "user/showUser";
	}
	
	@ResponseBody
	@RequestMapping(value = "/users")
	public List<User> show(Model model) {
		List<User> users = userService.getUsers();
		logger.info(users);
		return users;
	}
}
