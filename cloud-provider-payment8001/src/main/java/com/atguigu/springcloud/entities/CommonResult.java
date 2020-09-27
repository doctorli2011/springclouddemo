package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
	private Integer code;
	private String message;
	private T data;

	public CommonResult(final Integer code, final String message) {
		this(code, message, null);
	}
}
