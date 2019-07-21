package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo{
	private static final long serialVersionUID = 5006550716849464495L;
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long parentId;
	private String name;
	private Integer status;
	private Integer sortOrder;
	private Boolean isParent;
	
}
