package com.jt.interceptor;

import com.jt.pojo.User;

public class UserThreadLocal {
	private static ThreadLocal<User> threadLocal = new ThreadLocal<>();
	
	public static void set(User user) {
		threadLocal.set(user);
	}
	
	public static User getUser() {
		return threadLocal.get();
	}
	
	public static void remove() {
		threadLocal.remove();
	}
	

}
