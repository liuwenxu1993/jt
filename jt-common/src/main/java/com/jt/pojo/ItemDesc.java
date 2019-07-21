package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@TableName("tb_item_desc")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItemDesc extends BasePojo{
	private static final long serialVersionUID = -7507003963591785513L;
	@TableId
	private Long itemId;
	private String itemDesc;
}
