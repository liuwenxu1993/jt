package com.jt.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.vo.SysResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public SysResult doHandlerException(RuntimeException e) {
		e.printStackTrace();
		return new SysResult().setStatus(201).setMsg(e.getMessage());
		
	}
}
