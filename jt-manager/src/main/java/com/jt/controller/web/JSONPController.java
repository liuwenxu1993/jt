package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.util.JsonUtils;

@RestController
@RequestMapping("/web")
public class JSONPController {
	
	/**
	 * jsonp返回值结果,必须经过特殊处理
	 */
	@RequestMapping("/testJSONP")
	public String testJSONP(String callback) {
		Item item = new Item();
		item.setId(1000l).setImage("jsonp跨域测试");
		String json = JsonUtils.toJson(item);
		return callback+"("+json+")";
	}

}
