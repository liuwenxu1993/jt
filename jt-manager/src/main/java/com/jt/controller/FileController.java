package com.jt.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
	
	@RequestMapping("file")
	public String file(@RequestParam("fileImage") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		
		File path = new File("E:/image/"+fileName);
		file.transferTo(path);
		return "redirect:/file.jsp";
	}

}
