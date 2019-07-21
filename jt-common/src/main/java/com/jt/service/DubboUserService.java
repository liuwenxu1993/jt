package com.jt.service;

import com.jt.pojo.User;

//中立的第三方接口
public interface DubboUserService {

	void doRegister(User user);

	String login(User user);

	void clearToken(String token);
}
