package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.exception.ServiceException;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.RequestUtils;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Reference(timeout = 3000,check = true,loadbalance = "consisten")
	private DubboUserService userService;
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		try {
			userService.doRegister(user);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return SysResult.fail();
		}
		return SysResult.success();
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		String token = userService.login(user);
		if(StringUtils.isEmpty(token))
			return SysResult.fail();
		Cookie cookie = new Cookie("JT_TICKET", token);
		cookie.setMaxAge(3600);
		cookie.setPath("/");//当前域名下 的所有资源都能使用此cookie
		cookie.setDomain("jt.com");
		response.addCookie(cookie);
		System.out.println(user.getUsername()+"登录完成");
		return SysResult.success();
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.获取cookie和token
		Cookie ck = RequestUtils.getCookie(request);
		if(StringUtils.isEmpty(ck)) throw new ServiceException("尚未登录");
		
		String token=ck.getValue();
		
		StringBuffer url = RequestUtil.getRequestURL(request);
		System.out.println(url);
		System.out.println(token);
		//2.清除Redis中的token缓存
		userService.clearToken(token);
		
		//3.清除浏览器cookie
		Cookie cookie = new Cookie(ck.getName(), "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setDomain("jt.com");
		response.addCookie(cookie);
		return "redirect:index";
	}
}
