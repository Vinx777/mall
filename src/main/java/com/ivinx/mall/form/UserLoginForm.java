package com.ivinx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Vinx
 */
@Data
public class UserLoginForm {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
