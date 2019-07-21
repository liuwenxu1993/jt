package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.util.JsonUtils;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedis;
	
	@RequestMapping("/check/{info}/{id}")
	public String doCheckUserInfo(@PathVariable String info,@PathVariable Integer id,String callback) {
		SysResult sysResult = userService.checkUserInfo(info,id);
		String json = JsonUtils.toJson(sysResult);
		return callback+"("+json+")";
	}
	
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(String callback,@PathVariable String token) {
		String json = jedis.get(token);
		if(json!=null) {
			return new JSONPObject(callback, SysResult.success(json));
		}else {
			return new JSONPObject(callback, SysResult.fail());
		}
		
	}
}
