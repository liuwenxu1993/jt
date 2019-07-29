package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_cart")
@EqualsAndHashCode(callSuper = false)
public class Cart extends BasePojo{
	private static final long serialVersionUID = 8209689274704358116L;
	@TableId(type=IdType.AUTO)
	private Long id;		//购物车主键
	private Long userId;	
	private Long itemId;
	private String itemTitle;	//商品的标题
	private String itemImage;	//商品图片
	private Long itemPrice;		//商品价格
	private Integer num;		//商品数量
}
