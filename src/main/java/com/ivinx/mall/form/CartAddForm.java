package com.ivinx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vinx
 */
@Data
public class CartAddForm {

	@NotNull
	private Integer productId;

	private Boolean selected = true;
}
