package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.Item;

@Controller
public class IndexController {
	
	/** 动态页面跳转 
	 *  RestFul风格
	 *  1.url的参数必须使用"/" 分割
	 *  2.server服务器端获取数据时,必须使用"{}"包裹参数
	 *  3.@pathVariable注解实现数据转化
	 * */
	@RequestMapping("/page/{moduleName}")
	public String doGetPage(@PathVariable String moduleName) {
		return moduleName;
	}
	
	
	@RequestMapping("/page/{title}/{sellPoint}/{price}")
	@ResponseBody
	public Item doGetItem(Item item) {
		System.out.println(item);
		return item;
	}
	
	/**
	 * t测试负载均衡 
	 *  */
	@RequestMapping("getMsg")
	@ResponseBody
	public String getMsg() {
		return "我是8091服务器";
	}
	
}
