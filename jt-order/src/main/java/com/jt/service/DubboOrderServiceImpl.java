package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service(timeout = 3000)
public class DubboOrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	/*
	 * 	三表入库  
	 * 	6种状态：1.未付款，2.已付款，3.未发货，4.已发货，5.交易完成，6.交易取消
	 * 	事务控制 @Transactional
	 * 	可以将多表操作转化为单表操作，简化代码  
	 * 	多表操作-->单表操作？？
	 */
	@Transactional
	@Override
	public String saveOrder(Order order) {
		//1.order的预处理
		String orderId = System.currentTimeMillis()+""+order.getUserId();
		order.setOrderId(orderId);
		Date now = new Date();
		order.setCreated(now);
		order.setUpdated(now);
		order.setStatus(1);
		
		//2.订单物流信息入库
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(now);
		orderShipping.setUpdated(now);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功");
		
		//3.订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		orderItems.forEach(e ->{
			e.setOrderId(orderId);
			e.setCreated(now);
			e.setUpdated(now);
			orderItemMapper.insert(e);
		});
		System.out.println("订单商品入库成功");
		
		//4.订单信息入库
		orderMapper.insert(order);
		System.out.println("订单信息入库成功");
		
		return orderId;
	}
	
	//回显订单
	@Override
	public Order findOrderById(String id) {
		
		Order order = orderMapper.selectById(id);
		OrderShipping orderShipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id", order.getOrderId());
		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
		
		order.setOrderShipping(orderShipping);
		order.setOrderItems(orderItems);
		
		return order;
	}
	
}
