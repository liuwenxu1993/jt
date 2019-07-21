package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_item_param_item")
@EqualsAndHashCode(callSuper = false)
public class ItemParam extends BasePojo{
	private static final long serialVersionUID = -5453693474542233187L;
	private Long id;
	private Long itemId;
	private String paramData;
}
