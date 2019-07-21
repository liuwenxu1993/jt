package com.jt.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

//pojo基类，完成2个任务，2个日期，实现序列化
@Data
@Accessors(chain=true)
@ToString
public class BasePojo implements Serializable{
	private static final long serialVersionUID = -3121669440061630976L;
	private Date created;
	private Date updated;

}
