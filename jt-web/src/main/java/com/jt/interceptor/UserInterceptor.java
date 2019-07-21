package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.JsonUtils;
import com.jt.util.RequestUtils;

import redis.clients.jedis.JedisCluster;

@Component
public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/*
	 * true 表示放行
	 * false 表示拦截，一般配合重定向使用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie cookie = RequestUtils.getCookie(request);
		if(cookie!=null) {
			String token = cookie.getValue();
			if(!StringUtils.isEmpty(token)) {
				System.out.println("jedisCluster:"+jedisCluster);
				String json = jedisCluster.get(token);
				if(!StringUtils.isEmpty(json)) {
					
					//在当前线程中储存一个user
					User user = JsonUtils.toObject(json, User.class);
					UserThreadLocal.set(user);
					return true;
				}
			}
		}
		request.setAttribute("request", request);
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//执行完一次请求后删除线程中的user对象,防止内存溢出
		UserThreadLocal.remove();
	}
}
