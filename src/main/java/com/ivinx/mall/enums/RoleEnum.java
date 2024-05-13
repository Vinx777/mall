package com.ivinx.mall.enums;

import lombok.Getter;

/**
 * role 0-admin,1-customer
 * Created by Vinx
 */
@Getter
public enum RoleEnum {
	ADMIN(0),

	CUSTOMER(1),

	;

	Integer code;

	RoleEnum(Integer code) {
		this.code = code;
	}
}
