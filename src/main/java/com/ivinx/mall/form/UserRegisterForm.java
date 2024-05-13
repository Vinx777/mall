package com.ivinx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Vinx
 */
@Data
public class UserRegisterForm {

	//@NotBlank  String
	//@NotEmpty Collections
	//@NotNull
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String email;
}
