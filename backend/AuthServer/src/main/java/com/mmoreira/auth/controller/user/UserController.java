package com.mmoreira.auth.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmoreira.auth.repository.entity.User;
import com.mmoreira.auth.service.UserService;

@Controller

public class UserController {
	@Autowired
	@Qualifier("UserService")
	private UserService userService;
	
	@RequestMapping("/login")
    public String loginPage() {
        return "login-form.html";
    }
	
	@RequestMapping("/register")
	@ResponseBody
	public Map<String, Object> register(@RequestBody User user) {
		Map<String, Object> map = userService.saveUser(user);
		return map;
	}
}
