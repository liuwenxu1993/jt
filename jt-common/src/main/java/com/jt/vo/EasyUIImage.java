package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIImage { //多系统之间对象直接传递时必须序列化
	
	private Integer error=0;
	private String url;
	private Integer width;
	private Integer height;

}
