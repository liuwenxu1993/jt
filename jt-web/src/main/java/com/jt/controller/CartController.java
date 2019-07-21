package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.interceptor.UserThreadLocal;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout = 3000,check = true,loadbalance = "consistent")
	private DubboCartService cartService;
	
	@RequestMapping("/show")
	public String showCart(HttpServletRequest request,HttpServletResponse response) {
		User user = UserThreadLocal.getUser();
		Long userId = user.getId();
		System.out.println("userId:"+userId);
		List<Cart> carts = cartService.findCartListByUserId(userId);
		request.setAttribute("cartList", carts);
		return "cart";
		
	}
	
	//如果restful风格接收参数时，与对象的属性一致时，可用对象接收，可以不用写@PathVariable注解
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(HttpServletRequest request,@PathVariable Long itemId) {
		User user = UserThreadLocal.getUser();
		
		Long userId = user.getId();
		
		Cart cart = new Cart().setItemId(itemId).setUserId(userId);
		
		cartService.deleteCart(cart);
		
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		User user = UserThreadLocal.getUser();
		cart.setUserId(user.getId());
		cartService.saveCart(cart);
		
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		User user = UserThreadLocal.getUser();
		cart.setUserId(user.getId());
		cartService.UpdateCartNum(cart);
		
		return SysResult.success();
	}
}
