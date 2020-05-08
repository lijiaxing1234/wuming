package com.thinkgem.jeesite.modules.resource.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.thinkgem.jeesite.common.config.Global;


/**
 * 文件上传工具类
 * <pre>
 * </pre>
 */
public class UploadUtils {
	
	public static final String  DOCREALPATH=Global.getUserfilesBaseDir();
	private static final String  DOCSERVERPATH=Global.getUserfilesServer();
	
	
	// 定义允许上传的文件扩展名
	private Map<String, String> extMap = new HashMap<String, String>();
	
	// 文件保存目录相对路径
	private String basePath = "uploadFile";
	// 文件的目录名
	private String dirName = "files";
	
	// 文件保存目录路径
	private String savePath;
	// 文件保存目录url
	private String saveUrl;
	// 文件最终的url包括文件名
	private String fileUrl;
	
	
	
	
	private MultipartFile multipartFile=null;
	
	
	
	private String extension;
	private Long   fileSize;
	private String originalFilename;
	

	public UploadUtils() {
		// 其中images,flashs,medias,files,对应文件夹名称,对应dirName
		// key文件夹名称
		// value该文件夹内可以上传文件的后缀名
		extMap.put("images", ".gif,.jpg,.jpeg,.png,.bmp");
		extMap.put("flashs", ".swf,.flv");
		extMap.put("medias", ".swf,.flv,.mp3,.wav,.wma,.wmv,.mid,.avi,.mpg,.asf,.rm,.rmvb");
		extMap.put("files", ".png,.doc,.docx,.xls,.xlsx,.ppt,.htm,.html,.txt,.zip,.rar,.gz,.bz2");
	}
	public UploadUtils(MultipartFile multipartFile) {
		this();
		this.multipartFile=multipartFile;
	}
	
	public Boolean  hasUploadFile() throws IllegalStateException, IOException{
		if(multipartFile==null || multipartFile.isEmpty()){
			return false;
		}
		this.uploadFile();
		return true;
	}
	
	
	private void  uploadFile() throws IllegalStateException, IOException{
	
		
	    originalFilename= multipartFile.getOriginalFilename();
		extension="."+FilenameUtils.getExtension(originalFilename);
		fileSize=multipartFile.getSize();
		
	/*   if(!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(extension)) {
			  throw new RuntimeException("上传文件扩展名是不允许的扩展名。<br/>只允许" + extMap.get(dirName) + "格式。");
		} else {*/
			
			createPath();
		
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + extension;
		  
			File uploadedFile = new File(savePath, newFileName);
			
			fileUrl="/"+saveUrl+newFileName;
			saveUrl=DOCSERVERPATH+saveUrl+"/"+newFileName;
			
			multipartFile.transferTo(uploadedFile);
	
	  /* }*/
		
	}
	
	
	
	
	private void  createPath(){
		
		 savePath = DOCREALPATH +File.separator+ basePath + File.separator;
		 
	     saveUrl =basePath + "/";
		 
	     savePath += dirName + File.separator;
	     
		 saveUrl  += dirName + "/";
		
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		
		// .../basePath/dirName/yyyyMMdd/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		
		savePath += ymd + File.separator;
		
		saveUrl += ymd + "/";
		
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
	}
	
	
	public String getSavePath() {
		return savePath;
	}
	
	public String getSaveUrl() {
		return saveUrl;
	}
	public String getFileUrl(){
		return fileUrl;
	}
	
	public Long getFileSize(){
		return fileSize;
	}
	
	public String getFileExtension(){
		return extension;
	}
	public String getFileName(){
		return originalFilename;
	}
	
	
	
	/** **********************get/set方法********************************* */

	public Map<String, String> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

   

}
