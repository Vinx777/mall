package com.ivinx.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivinx.mall.MallApplicationTests;
import com.ivinx.mall.enums.ResponseEnum;
import com.ivinx.mall.form.CartAddForm;
import com.ivinx.mall.form.CartUpdateForm;
import com.ivinx.mall.vo.CartVo;
import com.ivinx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Vinx
 */
@Slf4j
public class ICartServiceTest extends MallApplicationTests {

	@Autowired
	private ICartService cartService;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private Integer uid = 1;

	private Integer productId = 26;

	@Before
	public void add() {
		log.info("【Add to shopping cart...】");
		CartAddForm form = new CartAddForm();
		form.setProductId(productId);
		form.setSelected(true);
		ResponseVo<CartVo> responseVo = cartService.add(uid, form);
		log.info("list={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void list() {
		ResponseVo<CartVo> responseVo = cartService.list(uid);
		log.info("list={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void update() {
		CartUpdateForm form = new CartUpdateForm();
		form.setQuantity(5);
		form.setSelected(false);
		ResponseVo<CartVo> responseVo = cartService.update(uid, productId, form);
		log.info("result={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@After
	public void delete() {
		log.info("【Delete from shopping cart...】");
		ResponseVo<CartVo> responseVo = cartService.delete(uid, productId);
		log.info("result={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void selectAll() {
		ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
		log.info("result={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void unSelectAll() {
		ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
		log.info("result={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void sum() {
		ResponseVo<Integer> responseVo = cartService.sum(uid);
		log.info("result={}", gson.toJson(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}
}