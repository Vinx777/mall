package com.ivinx.mall.service;

import com.ivinx.mall.MallApplicationTests;
import com.ivinx.mall.enums.ResponseEnum;
import com.ivinx.mall.form.ShippingForm;
import com.ivinx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Vinx
 */
@Slf4j
public class IShippingServiceTest extends MallApplicationTests {

	@Autowired
	private IShippingService shippingService;

	private Integer uid = 1;

	private ShippingForm form;

	private Integer shippingId;

	@Before
	public void before() {
		ShippingForm form = new ShippingForm();
		form.setReceiverName("Vinx");
		form.setReceiverAddress("Home");
		form.setReceiverCity("Tokyo");
		form.setReceiverMobile("18812345678");
		form.setReceiverPhone("010123456");
		form.setReceiverProvince("Tokyo");
		form.setReceiverDistrict("Minato");
		form.setReceiverZip("000000");
		this.form = form;

		add();
	}

	public void add() {
		ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
		log.info("result={}", responseVo);
		this.shippingId = responseVo.getData().get("shippingId");
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@After
	public void delete() {
		ResponseVo responseVo = shippingService.delete(uid, shippingId);
		log.info("result={}", responseVo);
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void update() {
		form.setReceiverCity("Osaka");
		ResponseVo responseVo = shippingService.update(uid, shippingId, form);
		log.info("result={}", responseVo);
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void list() {
		ResponseVo responseVo = shippingService.list(uid, 1, 10);
		log.info("result={}", responseVo);
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}
}