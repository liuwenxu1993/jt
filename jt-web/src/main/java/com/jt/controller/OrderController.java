package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.interceptor.UserThreadLocal;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Reference(timeout = 3000,check = true)
	private DubboCartService cartService;
	
	@Reference(timeout = 3000,check = true)
	private DubboOrderService orderService;
	
	@RequestMapping("/create")
	public String createOrder(Model model) {
		//获取用户购物车信息
		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> carts = cartService.findCartListByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	
	//提交订单
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult submit(Order order) {
		Long userId = UserThreadLocal.getUser().getId();
		order.setUserId(userId);
		
		//1.业务要求 返回orderId
		String orderId = orderService.saveOrder(order);
		
		//2.校验orderId是否有值
		if(StringUtils.isEmpty(orderId)) {
			return SysResult.fail();
		}
		return SysResult.success(orderId);
	}
	
	//回显订单
	@RequestMapping("/success")
	public String success(String id,Model model) {
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	//我的订单
	@RequestMapping("/myOrder")
	public String showMyOrder() {
		
		return "my-orders";
	}
}
