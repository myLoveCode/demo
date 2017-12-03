package com.example.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.example.core.exception.InvalidRequestException;
import com.example.core.exception.SystemException;

public class ImportUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImportUtil.class);
	
	/**
	 * 上传
	 * @param request
	 * @param transportFileService
	 * @param storageClient
	 * @return map key[String fileName, byte[] data, DubboContext dubboContext]
	 */
	public static Map<String, Object> upload(HttpServletRequest request) {
		Map<String, Object> fileData = new HashMap<String, Object>();
		String fileName = null;
		FileInputStream fis = null;
		int fileNumbers = 0;
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					if (++fileNumbers > 1) {
						throw new InvalidRequestException("只能上传一个文件");
					}
					fileName = file.getOriginalFilename();
					String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
					if (!"XLS".equalsIgnoreCase(fileType) && !"XLSX".equalsIgnoreCase(fileType)) {
						throw new InvalidRequestException("不支持的文件类型");
					}
					fis = saveUploadFile(file);
				}
			}
		}
		fileData.put("fileName", fileName);
		fileData.put("fis", fis);
		return fileData;
	}

	/**
	 * 保存上传文件
	 */
	private static FileInputStream saveUploadFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		//定义上传路径  
        String path = "D:/" + fileName;  
        File localFile = new File(path);  
        try {
			file.transferTo(localFile);
		} catch (IllegalStateException | IOException e) {
			logger.error("文件写入失败");
			logger.error(e.getMessage(),e);
			throw new SystemException("文件上传失败了");
		} 
        try {
			FileInputStream fis = new FileInputStream(localFile);
			return fis;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("文件上传失败了"); 
		}
	}
	
}
