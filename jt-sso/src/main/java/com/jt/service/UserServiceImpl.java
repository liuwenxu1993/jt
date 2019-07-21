package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public SysResult checkUserInfo(String info, Integer id) {
		SysResult sysResult = new SysResult();
		sysResult.setStatus(200).setMsg("ok");
		
		if(id==1) {
			QueryWrapper<User> query = new QueryWrapper<>();
			query.eq("username", info);
			Integer count = userMapper.selectCount(query);
			return count>=1 ? sysResult.setData(true):sysResult.setData(false);
		}else if(id==2){
			QueryWrapper<User> query = new QueryWrapper<>();
			query.eq("phone", info);
			Integer count = userMapper.selectCount(query);
			return count>=1 ? sysResult.setData(true):sysResult.setData(false);
		}
		
		return sysResult.setData(false);
	}
	
}
