package com.jt.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

//@ControllerAdvice //针对controller层生效
@RestControllerAdvice
public class SysResultAspect {
	
	@ExceptionHandler(RuntimeException.class)
	public SysResult doHandlerException(Throwable e) {
		return new SysResult().setStatus(201).setMsg(e.getMessage());
	}
}
