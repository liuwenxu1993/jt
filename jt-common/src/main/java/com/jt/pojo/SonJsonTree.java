package com.jt.pojo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class SonJsonTree {
	private Integer id;
	private String text;
}
