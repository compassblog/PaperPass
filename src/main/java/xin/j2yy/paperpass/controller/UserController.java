package xin.j2yy.paperpass.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xin.j2yy.paperpass.entity.User;
import xin.j2yy.paperpass.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("/showUser")
	public String getUser(HttpServletRequest request) {
		User user = userService.findById(1);
		request.setAttribute("user", user);
		return "user";
	}
}
