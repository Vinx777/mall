package com.ivinx.mall.service.impl;

import com.ivinx.mall.dao.UserMapper;
import com.ivinx.mall.enums.ResponseEnum;
import com.ivinx.mall.enums.RoleEnum;
import com.ivinx.mall.pojo.User;
import com.ivinx.mall.service.IUserService;
import com.ivinx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.ivinx.mall.enums.ResponseEnum.*;

/**
 * Created by Vinx
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * @param user
	 */
	@Override
	public ResponseVo<User> register(User user) {
//		error();

		int countByUsername = userMapper.countByUsername(user.getUsername());
		if (countByUsername > 0) {
			return ResponseVo.error(USERNAME_EXIST);
		}

		int countByEmail = userMapper.countByEmail(user.getEmail());
		if (countByEmail > 0) {
			return ResponseVo.error(EMAIL_EXIST);
		}

		user.setRole(RoleEnum.CUSTOMER.getCode());
		user.setPassword(DigestUtils.md5DigestAsHex(
				user.getPassword().getBytes(StandardCharsets.UTF_8)
		));

		int resultCount = userMapper.insertSelective(user);
		if (resultCount == 0) {
			return ResponseVo.error(ERROR);
		}

		return ResponseVo.success();
	}

	@Override
	public ResponseVo<User> login(String username, String password) {
		User user = userMapper.selectByUsername(username);
		if (user == null) {
			return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
		}

		if (!user.getPassword().equalsIgnoreCase(
				DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
			return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
		}

		user.setPassword("");
		return ResponseVo.success(user);
	}

	private void error() {
		throw new RuntimeException("unexpected error");
	}
}
