package com.jt.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


//@Component
//@Aspect
public class AuthenticatorAspect {
	
	@Around("bean(*Controller)")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("拦截controller成功");
		Object result = jp.proceed();
		System.out.println(result);
		return result;
	}

}
