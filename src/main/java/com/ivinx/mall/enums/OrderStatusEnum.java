package com.ivinx.mall.enums;

import lombok.Getter;

/**
 * order status:0-canceled-10-no_pay，20-paid，40-shipped，50-trade_success，60-trade_close
 * Created by Vinx
 */
@Getter
public enum OrderStatusEnum {

	CANCELED(0, "canceled"),

	NO_PAY(10, "no_pay"),

	PAID(20, "paid"),

	SHIPPED(40, "shipped"),

	TRADE_SUCCESS(50, "trade_success"),

	TRADE_CLOSE(60, "trade_close"),
	;

	Integer code;

	String desc;

	OrderStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
