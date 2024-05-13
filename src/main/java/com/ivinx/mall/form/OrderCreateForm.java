package com.ivinx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vinx
 */
@Data
public class OrderCreateForm {

	@NotNull
	private Integer shippingId;
}
