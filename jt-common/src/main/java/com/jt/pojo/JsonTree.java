package com.jt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class JsonTree {
	private Long id;
	private String text;
	private Object children;
	private String iconCls;
	private String state="closed";
	public JsonTree(Long id, String text) {
		this.id = id;
		this.text = text;
	}
	
}
