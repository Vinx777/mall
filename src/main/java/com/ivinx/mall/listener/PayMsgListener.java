package com.ivinx.mall.listener;

import com.google.gson.Gson;
import com.ivinx.mall.pojo.PayInfo;
import com.ivinx.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Vinx
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

	@Autowired
	private IOrderService orderService;

	@RabbitHandler
	public void process(String msg) {
		log.info("【received message】=> {}", msg);

		PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
		if (payInfo.getPlatformStatus().equals("SUCCESS")) {
			orderService.paid(payInfo.getOrderNo());
		}
	}
}
