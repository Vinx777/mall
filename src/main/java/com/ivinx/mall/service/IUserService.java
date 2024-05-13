package com.ivinx.mall.service;

import com.ivinx.mall.pojo.User;
import com.ivinx.mall.vo.ResponseVo;

/**
 * Created by Vinx
 */
public interface IUserService {

	/**
	 * register
	 */
	ResponseVo<User> register(User user);

	/**
	 * Log in
	 */
	ResponseVo<User> login(String username, String password);
}
