package com.jt.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@TableName("tb_order_item")
@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
public class OrderItem extends BasePojo{
	private static final long serialVersionUID = 3987582162486261713L;

	@TableId
    private String itemId;
	
	@TableId	
    private String orderId;

    private Integer num;

    private String title;

    private Long price;

    private Long totalFee;

    private String picPath;
}