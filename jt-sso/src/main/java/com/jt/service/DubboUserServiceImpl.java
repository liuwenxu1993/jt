package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.exception.ServiceException;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.JsonUtils;

import redis.clients.jedis.JedisCluster;

@Service(timeout = 3000)  //阿里的包
public class DubboUserServiceImpl implements DubboUserService {
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public void doRegister(User user) {
		//1.对密码加密
		String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(password);
		//2.封装数据
		user.setEmail("liu@jt.com");
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//3.入库
		int count = mapper.insert(user);
		if(count==0) throw new ServiceException("入库失败");
	}
	
	//用户登录,并返回token
	@Override
	public String login(User user) {
		//1.对密码进行加密
		String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(password);
		
		//2.进入数据库进行匹配
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDB = mapper.selectOne(queryWrapper);
		if(StringUtils.isEmpty(userDB)) throw new ServiceException("登录失败");
		
		//3.生成token存入jedis
		String tokenTemp = "JT_TICKET_"+System.nanoTime()+user.getUsername();
		tokenTemp=DigestUtils.md5DigestAsHex(tokenTemp.getBytes());
		
		//4.生成value数据 userJson,并对用户信息进行脱敏处理
		userDB.setPassword("nmlgb");
		String userJson = JsonUtils.toJson(userDB);
		
		//5.存入redis缓存,并设置超时时间
		jedisCluster.setex(tokenTemp, 3600, userJson);
		
		String token =tokenTemp;
		
		return token;
	}
	
	//清除redis缓存中的token
	@Override
	public void clearToken(String token) {
		if(StringUtils.isEmpty(token))return;
		jedisCluster.del(token);
	}
}
