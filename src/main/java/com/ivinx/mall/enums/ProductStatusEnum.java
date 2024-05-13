package com.ivinx.mall.enums;

import lombok.Getter;

/**
 * product status.1-on_sale 2-off_sale 3-delete
 * Created by Vinx
 */
@Getter
public enum ProductStatusEnum {

	ON_SALE(1),

	OFF_SALE(2),

	DELETE(3),



	;

	Integer code;

	ProductStatusEnum(Integer code) {
		this.code = code;
	}
}
