package com.thinkgem.jeesite.modules.student.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.IdGen;

@Controller
@RequestMapping(value = "/fileUploadUtil")
@SessionAttributes("loginUser")
public class FileUploadController {
	/**
	 * 
	 * 
	 * 上传文件
	 *  参数：上传的目录 uploadFolder
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*@RequestMapping(value = {"/upoladFile"})
	public void upoladFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html; charset=UTF-8");
		
		MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;

		//转换request为MultipartHttpServletRequest

		Map<String, MultipartFile> map=re.getFileMap();

		//key 是input框中的 name 属性 value 得到的MultipartFile对象

		ArrayList<MultipartFile> filelist =new ArrayList<MultipartFile>();

		for( Object obj :map.values()){

			MultipartFile file=(MultipartFile)obj;

			filelist.add(file);

		}
		
		JSONObject obj = new JSONObject();
	  if(filelist.size()>0)
	   {

//		    for(int i=0; i<filelist.size();i++){
//
//		    MultipartFile fj=filelist.get(i);
//		 }
		
		    MultipartFile	file=filelist.get(0);
	
		System.out.println("===================================");
		    String newsPicUrl=null;
			 if(!file.isEmpty())
			 {
			        Date dt=new Date();
			        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd"); 
			        String gettime=df.format(dt);
			        
					String uploadpath=request.getSession().getServletContext().getRealPath("/")+"eyx-assets"+File.separator+"uploadFile"+File.separator+request.getParameter("uploadFolder")+File.separator+gettime+File.separator;
					System.out.println("uploadpath=========="+uploadpath);
			        //如果相应不存在，新建。
			        if(!new File(uploadpath).isDirectory())
			            new File(uploadpath).mkdirs();
			 
			        //将标准request转换为Multipart类型的
			        
			        
			    	String fileName=file.getOriginalFilename();
		     		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		     		String newfileName=IdGen.uuid()+"."+fileExt;
			        try {
			             //新建一个文件
			            File source = new File(uploadpath,newfileName);
			            //用MultipartFile的transferTo将流导入到指定文件
			            file.transferTo(source);   
			            newsPicUrl="/eyx-assets/uploadFile/"+request.getParameter("uploadFolder")+"/"+gettime+"/"+newfileName;
			        } catch (IOException e) {
			                System.out.print("上传出错");	
			                newsPicUrl=null;
			        }   
			        if(StringUtils.isBlank(newsPicUrl))
			        {
			        	
			        	obj.put("message", "没有找到上传文件");
			         	obj.put("status", "0");
			        	System.out.println("newsPicUrl==========为空或null");
			        }
			        else
			        {
				    	System.out.println("newsPicUrl=========="+newsPicUrl);
				    	obj.put("message", "上传成功");
				      	obj.put("status", "1");
				      	obj.put("picurl",newsPicUrl);
			        }
			 }
			 else
			 {
				 System.out.println("没有上传文件");
					obj.put("message", "没有找到上传文件");
		         	obj.put("status", "0");
			 }
	
		}
		else
		{
			 System.out.println("没有上传文件");
			 obj.put("message", "没有找到上传文件");
	         obj.put("status", "0");
		}
	  System.out.println(obj.toString());
	   PrintWriter writer=response.getWriter();
	    writer.write(obj.toJSONString());
	    writer.close();
	}*/

	/**
	 * 
	 * 
	 * KindEditor富文本文件管理相关
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/kindeditorfilemanager")
	@SuppressWarnings("unchecked")
	public void  KindEditorFileManager(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		System.out.println("===========kindeditorfilemanager===========");
	  	//文件保存目录路径
		String rootPath=request.getSession().getServletContext().getRealPath("/")+"eyx-assets"+File.separator+"uploadFile"+File.separator+"kindeditor"+File.separator;
		//文件保存目录URL
		String rootUrl  = request.getContextPath() + "/eyx-assets/uploadFile/kindeditor/";
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

		String dirName = request.getParameter("dir");
		if (!StringUtils.isBlank(dirName)) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
				write(response,"Invalid Directory name.");
				return;
			}
			rootPath += dirName + File.separator;
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		
		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			write(response,"Access is not allowed.");
			return;
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			write(response,"Parameter is not valid.");
			return;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			write(response,"Directory does not exist.");
			return;
		}

		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		
		

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		response.setContentType("application/json; charset=UTF-8");
		write(response,result.toJSONString());
	
	}
	
	/**
	 * 
	 * 
	 * KindEditor富文本框上传相关
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/kindeditorupload")
	public void  KindEditorUpload (HttpServletRequest request,  HttpServletResponse response)throws IOException{	
		System.out.println("========kindeditorupload===========");
		
		response.setContentType("text/html; charset=UTF-8");
		//最大文件大小
		long maxSize = 1000000;
	    //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
        
    	//文件保存目录路径
		String uploadpath=request.getSession().getServletContext().getRealPath("/")+"eyx-assets"+File.separator+"uploadFile"+File.separator+"kindeditor"+File.separator;
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/eyx-assets/uploadFile/kindeditor/";
         
		MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
		//转换request为MultipartHttpServletRequest
		Map<String, MultipartFile> map=re.getFileMap();
		//key 是input框中的 name 属性 value 得到的MultipartFile对象
		ArrayList<MultipartFile> filelist =new ArrayList<MultipartFile>();
		for( Object obj :map.values()){
			MultipartFile file=(MultipartFile)obj;
			filelist.add(file);
		}
		JSONObject obj = new JSONObject();
	  if(filelist.size()>0)
	  {
		  
		//检查目录
//		  File uploadDir = new File(uploadpath);
		//检查目录写权限
//		  if(!uploadDir.canWrite()){
//			obj.put("error", 1);
//			obj.put("message","上传目录没有写权限");
//			 write(response,obj.toJSONString());
//		  	return;
//		  }
		  String dirName = request.getParameter("dir");
		  if (StringUtils.isBlank(dirName)) {
		  	dirName = "image";
		  }
		  if(!extMap.containsKey(dirName)){
				obj.put("error", 1);
				obj.put("message","目录名不正确。");
				 write(response,obj.toJSONString());
				return;
		  }
		  
		 //创建文件夹
		  uploadpath += dirName + File.separator;
		  saveUrl += dirName + "/";
	    
		  File saveDirFile = new File(uploadpath);
		  if (!saveDirFile.exists()) {
		  	saveDirFile.mkdirs();
		  }
	  	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		  String ymd = sdf.format(new Date());
		  uploadpath += ymd + File.separator;
		  saveUrl += ymd + "/";
		  File dirFile = new File(uploadpath);
		  if (!dirFile.exists()) {
		  	dirFile.mkdirs();
		  }
		  
		  
		  for(int i=0; i<filelist.size();i++){
	         MultipartFile file=filelist.get(i);
		    
	         if(!file.isEmpty())
	         {
	        	//检查文件大小
	     		if(file.getSize() > maxSize){
					obj.put("error", 1);
					obj.put("message","上传文件大小超过限制。");
					 write(response,obj.toJSONString());
					return;
	     		}
	     		//检查扩展名
	     		System.out.println(file.getOriginalFilename());
	     		String fileName=file.getOriginalFilename();
	     		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	     		ArrayList<String> list = new ArrayList<String>(Arrays.asList(extMap.get(dirName).split(",")));
	     		if(!list.contains(fileExt)){
	     			obj.put("error", 1);
					obj.put("message","上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
					 write(response,obj.toJSONString());
	     			return;
	     		}

	     		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	     		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
	     		saveUrl+=newFileName;
	     		System.out.println(uploadpath+newFileName);
	     		System.out.println(saveUrl);
	     		 //将标准request转换为Multipart类型的
		        try {
		             //新建一个文件
		            File source = new File(uploadpath,newFileName);
		            //用MultipartFile的transferTo将流导入到指定文件
		            file.transferTo(source);   
		        } catch (IOException e) {
		              
	     			obj.put("error", 1);
					obj.put("message","上传失败");
					 write(response,obj.toJSONString());
					 return;
		        }   
		        
		        
				
				obj.put("error", 0);
				obj.put("url", saveUrl);
				write(response,obj.toJSONString());
	        	 
	         }
		  }
	
        
        
    
	  }
	  else
	  {
		//	out.println(getError("请选择文件。"));
			obj.put("error", 1);
			obj.put("message","请选择文件");
			write(response,obj.toJSONString());
			return;
	  }
	}
	
	/**
	 * 输出流到页面
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	protected void write(HttpServletResponse response,String message)throws IOException{
		  System.out.println(message);
		   PrintWriter writer=response.getWriter();
		    writer.write(message);
		    writer.close();
	}
	@SuppressWarnings("rawtypes")
	public class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}
}
