package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface DubboCartService {
	public List<Cart> findCartListByUserId(Long userId);

	public void deleteCart(Cart cart);

	public void saveCart(Cart cart);

	public void UpdateCartNum(Cart cart);
}
