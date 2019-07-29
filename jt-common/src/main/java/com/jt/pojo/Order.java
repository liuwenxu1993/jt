package com.jt.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@TableName("tb_order")
@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
public class Order extends BasePojo{
	
	private static final long serialVersionUID = 2479778235154508795L;
	@TableField(exist=false)	//入库操作忽略该字段
	private OrderShipping orderShipping;
								//封装订单商品信息  一对多
	@TableField(exist=false)	//入库操作忽略该字段
	private List<OrderItem> orderItems;
	
	@TableId
    private String orderId;
    private String payment;
    private Integer paymentType;
    private String postFee;
    private Integer status;//6种状态：1.未付款，2.已付款，3.未发货，4.已发货，5.交易完成，6.交易取消
    private Date paymentTime;
    private Date consignTime;
    private Date endTime;
    private Date closeTime;
    private String shippingName;
    private String shippingCode;
    private Long userId;
    private String buyerMessage;
    private String buyerNick;
    private Integer buyerRate;

}