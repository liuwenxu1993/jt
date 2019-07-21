package com.jt.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HttpClientService {
	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig config;
	
	/**
	 *	参数说明:
	 *	1.url地址
	 *	2.Map封装参数
	 *	3.指定编码格式 
	 */
	public String doGet(String url,Map<String, String> params,String charset) {
		//1.校验字符集
		if(StringUtils.isEmpty(charset)) {
			charset = "utf-8";
		}
		//2.校验param是否为空
		if(!StringUtils.isEmpty(params)) {
			url +="?";
			Set<Entry<String, String>> entrySet = params.entrySet();
			for(Entry<String, String> entry:entrySet) {
				url +=entry.getKey()+"="+entry.getValue()+"&";
			}
			url.substring(0, url.length()-1);
		}
		//3.发起get请求
		HttpGet get = new HttpGet(url);
		get.setConfig(config);
		
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode()==200) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity,charset);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public String doGet(String url) {
		return doGet(url, null, null);
	}
	
	public String doGet(String url,Map<String, String> params) {
		return doGet(url, params, null);
	}
}
