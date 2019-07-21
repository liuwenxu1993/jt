package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringBootClient {
	@Autowired
	private HttpClientService service;
	
	@Test
	public void doGet() {
		String url = "http://www.baidu.com";
		String res = service.doGet(url);
		System.out.println(res);
	}
}
