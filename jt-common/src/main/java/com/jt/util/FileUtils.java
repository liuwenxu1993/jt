package com.jt.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtils {
	public static String localDir="E:/image/";
	
	public static String getFilePath(String imageName) {
		String datePathDir =  new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		String path = localDir+datePathDir+"/";
		File file = new File(path);
		if(!file.exists()) file.mkdirs();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		imageName = imageName.substring(imageName.lastIndexOf("."));
		path = path+uuid+imageName;
		System.out.println(path);
		return path;
	}

}
