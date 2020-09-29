package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {
	@Resource
	private PaymentHystrixService paymentHystrixService;

	@GetMapping("/consumer/payment/hystrix/ok/{id}")
	public String paymentInfo_OK (@PathVariable("id") Integer id) {
		return paymentHystrixService.paymentInfo_OK(id);
	}

	@GetMapping("/consumer/payment/hystrix/timeout/{id}")
//	@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//	})
	@HystrixCommand
	public String paymentInfo_TimeOut (@PathVariable("id") Integer id) {
		return paymentHystrixService.paymentInfo_TimeOut(id);
	}

	public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
		return "我是消费者 80， 对方支付系统繁忙，请 10 秒钟后再试; id:" + id;
	}

	// 全局 fallback
	public String payment_Global_FallbackMethod() {
		return "Global 异常处理信息，请稍后再试，o(╥﹏╥)o";
	}
}
