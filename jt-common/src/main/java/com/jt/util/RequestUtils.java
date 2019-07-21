package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class RequestUtils {
	
	public static Cookie getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies.length>0) {
			Cookie ck=null;
			for(Cookie cookie:cookies) {
				if("JT_TICKET".equals(cookie.getName())) {
					//token=cookie.getValue();
					ck=cookie;
					break;
				}
			}
			return ck;
		}else {
			return null;
		}
	}
}
