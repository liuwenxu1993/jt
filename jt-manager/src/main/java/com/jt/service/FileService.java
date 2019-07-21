package com.jt.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUIImage;

public interface FileService {

	EasyUIImage fileUpload(MultipartFile file);

}
