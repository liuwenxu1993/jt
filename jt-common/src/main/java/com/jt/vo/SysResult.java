package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	private Integer status;
	private String msg;
	private Object data;

	public static SysResult success() {
		return new SysResult(200,"调用成功",null);
	}
	
	public static SysResult success(Object data) {
		return new SysResult(200,"调用成功",data);
	}
	
	public static SysResult success(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	
	public static SysResult fail() {
		return new SysResult(201,"调用失败!!!",null);
	}
	
}
