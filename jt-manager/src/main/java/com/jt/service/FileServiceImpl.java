package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.util.FileUtils;
import com.jt.vo.EasyUIImage;

@Service
public class FileServiceImpl implements FileService {

	/**
	 * 问题:文件类型的校验,确认是图片,同时也要防止恶意的文件上传,众多图片如何保存?重名怎么处理?
	 * 解决:交给专业的工具API进行校验获取宽高
	 * 分文件存储文件:按yyyy/MM/dd
	 * 自定义UUID为文件名称
	 * 思路:
	 * 	 1.获取用户文件名称做校验
	 * 	 2.校验文件名称是否为图片
	 * 	 3.利用工具api校验图片的宽高  BufferedImage
	 * */
	
	@Override
	public EasyUIImage fileUpload(MultipartFile file){
		EasyUIImage uiImage = new EasyUIImage();
		String fileName = file.getOriginalFilename();
		fileName = fileName.toLowerCase();
		if(!fileName.matches("^.+\\.(jpg|png|gif|bmp|svg)$")) {
			uiImage.setError(1);
			return uiImage;
		}
		try {
			InputStream in = file.getInputStream();
			BufferedImage bufferedImage = ImageIO.read(in);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0) {
				uiImage.setError(1); //对象转化时异常
				return uiImage;
			}
			
			String url = FileUtils.getFilePath(fileName);
			String localDir = FileUtils.localDir;
			File f = new File(url);
			file.transferTo(f);
			url = url.replace(localDir, "http://image.jt.com/");
			uiImage.setUrl(url);
			
			return uiImage;
			
		} catch (Exception e) {
			e.printStackTrace();
			uiImage.setError(1); //对象转化时异常
			return uiImage;
		}
		
		
	}


}
