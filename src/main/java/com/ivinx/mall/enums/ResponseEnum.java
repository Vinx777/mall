package com.ivinx.mall.enums;

import lombok.Getter;

/**
 * Created by Vinx
 */
@Getter
public enum ResponseEnum {

	ERROR(-1, "error"),

	SUCCESS(0, "success"),

	PASSWORD_ERROR(1,"password_error"),

	USERNAME_EXIST(2, "username_exist"),

	PARAM_ERROR(3, "param_error"),

	EMAIL_EXIST(4, "email_exist"),

	NEED_LOGIN(10, "need_login"),

	USERNAME_OR_PASSWORD_ERROR(11, "username_or_password_error"),

	PRODUCT_OFF_SALE_OR_DELETE(12, "product_off_sale_or_delete"),

	PRODUCT_NOT_EXIST(13, "product_not_exist"),

	PROODUCT_STOCK_ERROR(14, "prooduct_stock_error"),

	CART_PRODUCT_NOT_EXIST(15, "cart_product_not_exist"),

	DELETE_SHIPPING_FAIL(16, "delete_shipping_fail"),

	SHIPPING_NOT_EXIST(17, "shipping_not_exist"),

	CART_SELECTED_IS_EMPTY(18, "cart_selected_is_empty"),

	ORDER_NOT_EXIST(19, "order_not_exist"),

	ORDER_STATUS_ERROR(20, "order_status_error"),

	;

	Integer code;

	String desc;

	ResponseEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
