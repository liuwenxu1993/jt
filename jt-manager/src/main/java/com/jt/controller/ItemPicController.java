package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;

@RestController
@RequestMapping("/pic")
public class ItemPicController {

	@Autowired
	FileService fileService;
	
	@RequestMapping("/upload")
	public EasyUIImage upload(@RequestParam("uploadFile")MultipartFile file) throws Exception{
		System.out.println(file.getSize());
		return fileService.fileUpload(file);
		
	}
}
