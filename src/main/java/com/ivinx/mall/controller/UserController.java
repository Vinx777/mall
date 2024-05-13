package com.ivinx.mall.controller;

import com.ivinx.mall.consts.MallConst;
import com.ivinx.mall.form.UserLoginForm;
import com.ivinx.mall.form.UserRegisterForm;
import com.ivinx.mall.pojo.User;
import com.ivinx.mall.service.IUserService;
import com.ivinx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Vinx
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/user/register")
	public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userForm) {
		User user = new User();
		BeanUtils.copyProperties(userForm, user);
		//dto
		return userService.register(user);
	}

	@PostMapping("/user/login")
	public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
								  HttpSession session) {
		ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

		//设置Session
		session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
		log.info("/login sessionId={}", session.getId());

		return userResponseVo;
	}

	@GetMapping("/user")
	public ResponseVo<User> userInfo(HttpSession session) {
		log.info("/user sessionId={}", session.getId());
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return ResponseVo.success(user);
	}

	/**
	 * {@link TomcatServletWebServerFactory} getSessionTimeoutInMinutes
	 */
	@PostMapping("/user/logout")
	public ResponseVo logout(HttpSession session) {
		log.info("/user/logout sessionId={}", session.getId());
		session.removeAttribute(MallConst.CURRENT_USER);
		return ResponseVo.success();
	}
}
