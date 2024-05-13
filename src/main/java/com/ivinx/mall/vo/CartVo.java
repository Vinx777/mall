package com.ivinx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Vinx
 */
@Data
public class CartVo {

	private List<CartProductVo> cartProductVoList;

	private Boolean selectedAll;

	private BigDecimal cartTotalPrice;

	private Integer cartTotalQuantity;
}
