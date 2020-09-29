package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
	@Override
	public String paymentInfo_OK(final Integer id) {
		return "--------------PyamentFallBackService fall back-paymentInfo_OK, o(╥﹏╥)o-----------------";
	}

	@Override
	public String paymentInfo_TimeOut(final Integer id) {
		return "--------------PyamentFallBackService fall back-paymentInfo_TimeOut, o(╥﹏╥)o------------------";
	}
}
