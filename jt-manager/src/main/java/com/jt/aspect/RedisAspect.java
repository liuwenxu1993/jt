package com.jt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.Cache;
import com.jt.annotation.CacheUpdate;
import com.jt.util.JsonUtils;

import redis.clients.jedis.JedisCluster;

@Aspect
@Component
public class RedisAspect {
	
	@Autowired(required = false)
	private JedisCluster cluster;
	
	//定义切面
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Around("@annotation(cache)")
	public Object around(ProceedingJoinPoint jp,Cache cache) throws Throwable{
		//通过签名获取方法名
		Signature signature = jp.getSignature();
		String name = signature.getName();
		
		//根据方法名和参数值定义key
		String key = (name +getParamValue(jp)).toUpperCase();
		System.out.println(key);
		
		//从集群中获取缓存
		String result = cluster.get(key);
		
		//获取方法的返回值类型
		MethodSignature methodSignature = (MethodSignature)jp.getSignature();
		Class returnType = methodSignature.getReturnType();
		
		//判断缓存是否存在
		if(StringUtils.isEmpty(result)) {
			System.out.println("查数据库");
			Object res = jp.proceed();
			String json = JsonUtils.toJson(res);
			cluster.set(key, json);
			return res;
		}else {
			System.out.println("查缓存");
			return JsonUtils.toObject(result, returnType);
		}
	}

	private String getParamValue(ProceedingJoinPoint jp) {
		Object[] args = jp.getArgs();
		String param = new String();
		for (int i = 0; i < args.length; i++) {
			param=param+args[i];
		}
		return param;
	}
	
	/** 
	 * 	另一种通知的写法
	 *  :可以直接获取到注解的类型
	 */
	//@Around(value="@annotation(cacheUpdate)")
	public Object aroundUpdate(ProceedingJoinPoint jp,CacheUpdate cacheUpdate) {
		
		
		return null;
	}


}
