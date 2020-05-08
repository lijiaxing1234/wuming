package com.thinkgem.jeesite.modules.resource.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alipay.a.a.e;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.resource.entity.ColumnKey;
import com.thinkgem.jeesite.modules.resource.entity.LVCourse;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplement;
import com.thinkgem.jeesite.modules.resource.service.LVCourseService;
import com.thinkgem.jeesite.modules.resource.util.UploadUtils;
import com.thinkgem.jeesite.modules.web.dto.MTCourse;
import com.thinkgem.jeesite.modules.web.dto.MTJSON;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;
import com.thinkgem.jeesite.modules.web.util.PropertieUtils;
import com.thinkgem.jeesite.modules.web.util.mtcloud.MTCloud;

/**
 * 直播相关Conroller
 */

@Controller
@RequestMapping(value ="${adminPath}/admin/lv")
public class LVCourseController extends BaseController{
	
	Logger  logger=LoggerFactory.getLogger(LVCourseController.class);
	
	@Autowired
	LVCourseService  courseService;
	
	@Autowired
	CourseService  sysCourseService; //系统中课程
	
	
	@RequestMapping({ "columnlist" })
	public String getShelfColumnfList(Model model) {
		
		return "modules/lv/columneList";
	}
	
	
	
	@RequestMapping({ "columneAjaxList" })
	public String columneAjaxList(@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			                    @RequestParam(value="pageSize",required=false,defaultValue="20")Integer pageSize,Model model) {
		
		/*请求第三方"欢拓开放平台"课程列表 */
		
		Page<MTCourse> page=new Page<MTCourse>(pageNo,pageSize);
		page.setFuncName("columneAjaxPage");
		MTJSON<List<MTCourse>>  mtJson=null;
		MTCloud client = new MTCloud(); 
		try {
			  String start_time=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "mtcloud.courselist.start_time");
		      String end_time=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "mtcloud.courselist.end_time");
			
			 //status：课程状态(0为正常状态，-1为已删除)，expire:回放地址有效期(如传3600表示小时)
			  HashMap<Object,Object> options = new HashMap<Object,Object>();
			  options.put("status", 0);
			  
			  String  result= client.courseList(start_time, end_time, page.getPageNo(), page.getPageSize(), options);
			  
			/*  logger.warn(result);
			  
			  System.out.println(result);*/
			    //方法一
			    //  Gson gson =new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();  
				//  mtJson= gson.fromJson(result,new TypeToken<MTJSON<List<MTCourse>>>() {}.getType());
			    //方法二
			  
			    /**
			     * SerializeConfig.getGlobalInstance().setAsmEnable(false); // 序列化的时候关闭ASM  
				   ParserConfig.getGlobalInstance().setAsmEnable(false); // 反序列化的时候关闭ASM  
			     */
			    mtJson = JSON.parseObject(result, new TypeReference<MTJSON<List<MTCourse>>>() {});
			
			  
			  if(mtJson!=null){
			     page.setCount(mtJson.getTotal());
			     page.setList(mtJson.getData());
			  }
			  
			  model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "modules/lv/columneAjaxList";
	}
	
	
	@RequestMapping("getColumnIframe")
	public String  getColumnIframe(ColumnKey columnKey,Model model){
		model.addAttribute("columnKey", columnKey);
		return "modules/lv/columneIframe";
	}
	
	
	@RequestMapping("courseForm")
	public String  lvCourseForm(ColumnKey columnKey,LVCourse lvCourse,Model model,@RequestParam(value="type",required=false,defaultValue="1") String type){
		if (type.equalsIgnoreCase("2") || type=="2") {
			model.addAttribute("lvCourseImplement",new  LVCourseImplement());
			model.addAttribute("columnKey", columnKey);
			return "modules/courseInformation/newsForm";
		}
			/*直播对应本系统数据*/
			lvCourse= courseService.getCourseByLVCourseId(columnKey.getColId());
			model.addAttribute("lvCourse", lvCourse==null ? new LVCourse() : lvCourse);
			
			/*系统中的课程*/
			Course course=new Course();
			course.setLevel("1");//课程
			List<Course> courseList= sysCourseService.findList(course);
			model.addAttribute("courseList", courseList);
			
			model.addAttribute("lvCourseImplement",new  LVCourseImplement());
			model.addAttribute("columnKey", columnKey);
			
			return "modules/lv/lvCourseForm";
		
	}
	
	
	@RequestMapping("courseFormSave")
	public String  courseFormSave(ColumnKey columnKey,LVCourse course,Model model,@RequestParam(value="type",required=false,defaultValue="1") String type){
		
		if (type.equalsIgnoreCase("2") || type=="2") {
			 	String  colId=columnKey.getColId();
			    String  colCate=columnKey.getColCate();
			    return "redirect:"+adminPath+"/admin/lv/courseForm?colId="+colId+"&colCate="+colCate+"&type=2";
		}
		
		courseService.saveLVCourse(course);
	    String  colId=columnKey.getColId();
	    String  colCate=columnKey.getColCate();
	   // return "modules/lv/lvCourseForm";
	    return "redirect:"+adminPath+"/admin/lv/courseForm?colId="+colId+"&colCate="+colCate;
	}
	
	
	/***********************Implement 相关************************************************/
	
	@RequestMapping("courseImplementList")
	public String  courseFormImplementList(ColumnKey columnKey,LVCourseImplement courserImplement,Model model){
		
		courserImplement.setLvCourseId(columnKey.getColId());
		courserImplement.setLvCourseCate(Short.valueOf(columnKey.getColCate()));
		List<LVCourseImplement>  lvCourseImplementList= courseService.selectCourseImplementList(courserImplement);
		model.addAttribute("columnImplementList", lvCourseImplementList);
	  return "modules/lv/lvCourseImpleList";
	}
	
	/**
	 * 保存
	 * @param multipartFile
	 * @param lvCourseImple
	 * @param model
	 * @return
	 */
	@RequestMapping("courseImplementFormSave")
	public String courseImplementFormSave(@RequestParam(value = "uploadshotFile", required = false) MultipartFile multipartFile,
			                            LVCourseImplement lvCourseImple,Model model){
		if( StringUtils.isNoneBlank(lvCourseImple.getLvCourseId()) && lvCourseImple.getLvCourseCate() !=null  && StringUtils.isEmpty(lvCourseImple.getContent())){
			try {
				
			    UploadUtils  upload=new UploadUtils(multipartFile);
			    upload.setDirName("lvcourse");
				if(upload.hasUploadFile()){
					lvCourseImple.setContent(upload.getFileUrl());
					courseService.savecourseImplement(lvCourseImple);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else if ( StringUtils.isNoneBlank(lvCourseImple.getLvCourseId()) && lvCourseImple.getLvCourseCate() !=null  && StringUtils.isNotEmpty(lvCourseImple.getContent())) {
			
			lvCourseImple.setContent(delHTMLTag(StringEscapeUtils.unescapeHtml4(lvCourseImple.getContent())));
			
			courseService.savecourseImplement(lvCourseImple);
		}
	   return "modules/lv/columneImplUrl";
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@RequestMapping({ "deletImple" })
	public void deletNewImple(LVCourseImplement implement) {
		if (StringUtils.isNotEmpty(String.valueOf(implement.getId()))) {
			courseService.deleteImplement(implement.getId());
		}
	}
	
	@RequestMapping({ "getImplement" })
	public String  getImplement(LVCourseImplement implement,Model model) { 
		implement =courseService.getImplementById(implement.getId());
		model.addAttribute("implement",implement);
		return "modules/lv/lvCourseImpleUpdat";
	}
	
	
	
	
	
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@RequestMapping({ "upImpleSeq" })
	private void upNewsImpleSeq(final String str1, final String str2) {
		LVCourseImplement implementF = new LVCourseImplement();
		String[] arr1 = str1.split(",");
		implementF.setId(Long.valueOf(arr1[0]));
		implementF.setSeq(Integer.parseInt(arr1[1]) + 1);
		courseService.updateImplementByExample(implementF);
		LVCourseImplement implementS = new LVCourseImplement();
		final String[] arr2 = str2.split(",");
		implementS.setId(Long.valueOf(arr2[0]));
		implementS.setSeq(Integer.parseInt(arr2[1]) - 1);
		courseService.updateImplementByExample(implementS);
	}
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@RequestMapping({ "downImpleSeq" })
	private void downNewsImpleSeq(final String str1, final String str2) {
		LVCourseImplement implementF = new LVCourseImplement();
		final String[] arr1 = str1.split(",");
		implementF.setId(Long.valueOf(arr1[0]));
		implementF.setSeq(Integer.parseInt(arr1[1]) - 1);
		courseService.updateImplementByExample(implementF);
		LVCourseImplement implementS = new LVCourseImplement();
		final String[] arr2 = str2.split(",");
		implementS.setId(Long.valueOf(arr2[0]));
		implementS.setSeq(Integer.parseInt(arr2[1]) + 1);
		courseService.updateImplementByExample(implementS);
	}

    public static String delHTMLTag(String htmlStr) {
        final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        final String regEx_html = "<[^>]+>";
        final Pattern p_script = Pattern.compile(regEx_script, 2);
        final Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        final Pattern p_style = Pattern.compile(regEx_style, 2);
        final Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        final Pattern p_html = Pattern.compile(regEx_html, 2);
        final Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        return htmlStr.trim();
    }
	

	@RequestMapping({ "getpreViewByID" })
	public String getpreViewByID(ColumnKey columnKey, final Model model){
		
		if(StringUtils.isNotBlank(columnKey.getColId())){
		   /*直播对应本系统数据*/
		   LVCourse  lvCourse= courseService.getCourseByLVCourseId(columnKey.getColId());
		   
		   if(lvCourse !=null){
			 
			   LVCourseImplement implement=new LVCourseImplement();
			   implement.setLvCourseId(columnKey.getColId());
			   implement.setLvCourseCate(Short.valueOf(columnKey.getColCate()));
			   
			   List<LVCourseImplement>  implementList=courseService.selectCourseImplementList(implement);
			   
			   model.addAttribute("lvCourse", lvCourse);  
			   model.addAttribute("implementList", implementList);
		   }
		   
		}
		
    	return "modules/lv/lvCoursePreView";
	}
    
	@RequestMapping("LVupList")
	public String LVupList(@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
            			   @RequestParam(value="pageSize",required=false,defaultValue="20")Integer pageSize,
            			   Model model){
		/*com.thinkgem.jeesite.modules.resource.util.Page<LVCourse> lvByPage = courseService.getLVByPage(pageNB);
		model.addAttribute("page", lvByPage);*/
		Page<MTCourse> page=new Page<MTCourse>(pageNo,pageSize);
		page.setFuncName("columneAjaxPage");
		MTJSON<List<MTCourse>>  mtJson=null;
		MTCloud client = new MTCloud(); 
		try {
			  String start_time=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "mtcloud.courselist.start_time");
		      String end_time=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "mtcloud.courselist.end_time");
			
			 //status：课程状态(0为正常状态，-1为已删除)，expire:回放地址有效期(如传3600表示小时)
			  HashMap<Object,Object> options = new HashMap<Object,Object>();
			  options.put("status", 0);
			  
			  String  result= client.courseList(start_time, end_time, page.getPageNo(), page.getPageSize(), options);
			  
			/*  logger.warn(result);
			  
			  System.out.println(result);*/
			    //方法一
			    //  Gson gson =new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();  
				//  mtJson= gson.fromJson(result,new TypeToken<MTJSON<List<MTCourse>>>() {}.getType());
			    //方法二
			  
			    /**
			     * SerializeConfig.getGlobalInstance().setAsmEnable(false); // 序列化的时候关闭ASM  
				   ParserConfig.getGlobalInstance().setAsmEnable(false); // 反序列化的时候关闭ASM  
			     */
			    mtJson = JSON.parseObject(result, new TypeReference<MTJSON<List<MTCourse>>>() {});
			
			  
			  if(mtJson!=null){
				  List<MTCourse> data = mtJson.getData();
				  for (int i = 0; i < data.size(); i++) {
					LVCourse lvRecommend = courseService.getLvRecommend(data.get(i).getCourse_id());
					if (StringUtils.isNotBlank(lvRecommend.getImg())) {
						data.get(i).setIconUrl(lvRecommend.getImg());
					}
					if (lvRecommend.getRecommend()==2) {
						data.remove(data.get(i));
					}else {
						data.get(i).setRecommend(lvRecommend.getRecommend());
					}
				 }
			     page.setCount(mtJson.getTotal());
			     page.setList(mtJson.getData());
			  }
			  
			  model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "modules/lv/lvList";
	}
	
	@RequestMapping("updateRecom")
	@ResponseBody
	public String updateRecom(String lvId,int status,HttpServletResponse response) throws IOException{
		String s = "0";
		int hasRecommendLVCount = courseService.getHasRecommendLVCount();
		if (hasRecommendLVCount<3) {
			int recommendLV = courseService.recommendLV(lvId, status);
			if (recommendLV == 1) {
				s = "1";
				return s;
			}else {
				return s;
			}
		}else {
			s = "2";
			return s;
		}
	}
	
	@RequestMapping("tolvImg")
	public String toNoticeForm(Model model,String lvId){
		model.addAttribute("lvId", lvId);
		return "/modules/lv/lvImg";
	}
	
	@RequestMapping(value="addHomeImg")
	public String upHomeImg(@RequestParam(value = "uploadFile") MultipartFile file,String lvId,RedirectAttributes redirectAttributes){
		LVCourse lvCourse = new LVCourse();
		String serverPath = PropertieUtils.getProperty("icon.server");
	   	String filePath = PropertieUtils.getProperty("icon");
	 	SimpleDateFormat sdf = new SimpleDateFormat("HHmmssS");
	   	Date date = new Date();
	    long time = date.getTime();
	   		try {
				if (file.getBytes().length != 0) {
					String originalFileName = file.getOriginalFilename();
					File file1 = new File(filePath);
					if (!file1.exists()) {
						file1.mkdir();
					}
					try {
						UUID uuid = UUID.randomUUID();
						String string = uuid.toString();
						String substring = string.substring(0, 5);
						String name = sdf.format(date)+substring+originalFileName.substring(originalFileName.lastIndexOf('.'),originalFileName.length());
						String iconUrl = filePath+name;
						FileOutputStream fileOutputStream = new FileOutputStream(iconUrl);
						fileOutputStream.write(file.getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
						lvCourse.setLvCourseId(lvId);
						lvCourse.setImg(serverPath+name);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	   		boolean b = courseService.addLvImg(lvCourse);
	   		if(b){
	   			addMessage(redirectAttributes, "添加成功!");
	   			return "redirect:"+adminPath+"/admin/lv/LVupList";
	   		}else{
	   			addMessage(redirectAttributes, "添加失败!");
	   			return "redirect:"+adminPath+"/admin/lv/tolvImg?lvId="+lvId;
	   		}
	}
}
