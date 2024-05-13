package com.ivinx.mall.service.impl;

import com.ivinx.mall.MallApplicationTests;
import com.ivinx.mall.enums.ResponseEnum;
import com.ivinx.mall.enums.RoleEnum;
import com.ivinx.mall.pojo.User;
import com.ivinx.mall.service.IUserService;
import com.ivinx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vinx
 */
@Transactional
public class UserServiceImplTest extends MallApplicationTests {

	public static final String USERNAME = "jack";

	public static final String PASSWORD = "123456";

	@Autowired
	private IUserService userService;

	@Before
	public void register() {
		User user = new User(USERNAME, PASSWORD, "jack@gmail.com", RoleEnum.CUSTOMER.getCode());
		userService.register(user);
	}

	@Test
	public void login() {
		ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}
}