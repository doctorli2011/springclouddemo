package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

	@Resource
	private PaymentService paymentService;

	@Resource
	private DiscoveryClient discoveryClient;

	@Value("${server.port}")
	private String serverPort;

	@PostMapping(value = "/payment/create")
	public CommonResult create (@RequestBody Payment payment) {
		final int result = paymentService.create(payment);

		if (result > 0) {
			return new CommonResult(200, "插入数据成功, serverPort:" + serverPort, result);
		} else {
			return new CommonResult(444, "插入数据失败", null);
		}
	}

	@GetMapping(value = "/payment/get/{id}")
	public CommonResult getPaymentById (@PathVariable Long id) {
		final Payment payment = paymentService.getPaymentById(id);

		if (payment != null) {
			return new CommonResult(200, "查询成功, serverPort:" + serverPort, payment);
		} else {
			return new CommonResult(444, "没有对应记录，查询 ID：" + id, null);
		}
	}

	@GetMapping(value = "/payment/discovery")
	public Object discovery() {
		final List<String> services = discoveryClient.getServices();
		for (final String element : services) {
			log.info("*****element:" + element);
		}

		final List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
		for (final ServiceInstance instance : instances) {
			log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
		}
		return this.discoveryClient;
	}

	@GetMapping(value = "/payment/lb")
	public String getPaymentLB() {
		return serverPort;
	}

	@GetMapping(value = "/payment/feign/timeout")
	public String timeout () {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return serverPort;
	}
}
