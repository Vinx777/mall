package com.ivinx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Vinx
 */
@Data
public class CartProductVo {

	private Integer productId;

	private Integer quantity;

	private String productName;

	private String productSubtitle;

	private String productMainImage;

	private BigDecimal productPrice;

	private Integer productStatus;

	/**
	 *  quantity * productPrice
	 */
	private BigDecimal productTotalPrice;

	private Integer productStock;

	private Boolean productSelected;

	public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
		this.productId = productId;
		this.quantity = quantity;
		this.productName = productName;
		this.productSubtitle = productSubtitle;
		this.productMainImage = productMainImage;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
		this.productTotalPrice = productTotalPrice;
		this.productStock = productStock;
		this.productSelected = productSelected;
	}
}
