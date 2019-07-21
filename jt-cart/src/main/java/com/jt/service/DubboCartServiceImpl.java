package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}
	
	@Override
	public void deleteCart(Cart cart) {
		System.out.println(cart);
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
	}

	@Override
	public void saveCart(Cart cart) {
		//1.根据user_id item_id查询数据库
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("item_id", cart.getItemId());
		queryWrapper.eq("user_id", cart.getUserId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			//更新数量
			int num = cartDB.getNum()+cart.getNum();
			Cart cartUp = new Cart();
			cartUp.setId(cartDB.getId());
			cartUp.setUpdated(new Date());
			cartUp.setNum(num);
			cartMapper.updateById(cartUp);
		}
	}
	
	@Override
	public void UpdateCartNum(Cart cart) {
		Cart cartTemp = new Cart();
		cartTemp.setNum(cart.getNum())
				.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId());
		updateWrapper.eq("item_id", cart.getItemId());
		
		cartMapper.update(cartTemp, updateWrapper);
	}
	
	
}
