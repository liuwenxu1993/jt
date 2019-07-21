package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
	
	@RequestMapping("/user/{moduleName}") 
	public String findItems(@PathVariable String moduleName) {
		
		return moduleName;
	}

}
