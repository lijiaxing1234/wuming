package com.thinkgem.jeesite.modules.teacher.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.entity.ExamSpecility;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.service.ExamDetailService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.KnowledgeService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

@Controller("teacherCommonController")
@RequestMapping("${teacherPath}/common")
public class CommonController extends BaseController {

	@Autowired
	ExaminationService examService; //试卷service
	@Autowired
	KnowledgeService knowledgeService; //知识点
	
	@Autowired
	CourseQuestionlibService courseQuestionlibService;
	@Autowired
	ExamDetailService examDetailService;
	
	/**
	 * 老师教授版本下的全部知识
	 * @param extId
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "courseKnowledgeByVersionTreeData")
	public List<Map<String, Object>> courseKnowledgeByVersionTreeData(@RequestParam(required=false) String extId,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseKnowledge> list =knowledgeService.findCourseKnowledgeByVersion(new CourseKnowledge());
		for (int i=0; i<list.size(); i++){
			CourseKnowledge e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 获得某次考试的所考的知识
	 * @param examId
	 * @param extId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "examKnowledgeListByExamId")
	public List<Map<String, Object>> examKnowledgeListByExamId(String examId,@RequestParam(required=false) String extId,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseKnowledge> list =knowledgeService.findCourseKnowledgeByExamId(examId);
		for (int i=0; i<list.size(); i++){
			CourseKnowledge e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	
	/*选择专业*/
	@RequestMapping(value ="specilityList")
	public String specilityList(Specialty specialty, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(specialty ==null){
			specialty=new Specialty();
		}
		Page<Specialty> page = examService.findSpecilityList(new Page<Specialty>(request, response), specialty); 
		model.addAttribute("page", page);
		model.addAttribute("specialty", specialty);
		
		return "teacher/examination/specilitySelectList";
	}
	
	
	/**
	 * 全部专业json集合
	 * @param extId  不显示的专业id
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "specilityListTreeData")
	public List<Map<String, Object>> specilityListTreeData(@RequestParam(required=false) String extId,HttpServletResponse response) {
		long beginTime = System.currentTimeMillis();
		System.out.println("=======================选择专业 ->开始======================="+beginTime);
		
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Specialty> list =examService.findSpecilityList(new Page<Specialty>(0,-1,-1), new Specialty()).getList(); 
		if(list!=null){
			for (int i=0; i<list.size(); i++){
				Specialty e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("name", e.getTitle());
					mapList.add(map);
				}
			}
			return mapList;
		}
		
		  long endTime = System.currentTimeMillis();
			System.out.println("=======================选择专业->结束=====================用时="+DateUtils.formatDateTime(endTime - beginTime));
		return null ;
	}
	
	
	
	
	
	
	/*选择班级*/
	@RequestMapping(value ="classList")
	public String classList(SchoolClass  schoolClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		long beginTime = System.currentTimeMillis();
		System.out.println("=======================选择班级 ->开始======================="+beginTime);
		
		if(schoolClass==null){
			schoolClass=new SchoolClass();
		}
		
		Page<SchoolClass> page = examService.findClassList(new Page<SchoolClass>(request, response), schoolClass); 
		model.addAttribute("page", page);
		
         long endTime = System.currentTimeMillis();
		System.out.println("=======================选择班级->结束=====================用时="+DateUtils.formatDateTime(endTime - beginTime));
		return "teacher/examination/classSelectList";
	}
	
	
    /**
     * 查询选中的专业
     * @param examId
     */
	@ResponseBody
	@RequestMapping(value = "findSpecilityByExamId")
	public String findSpecilityByExamId(String examId) {
		String reuslt="";
		if(StringUtils.isNoneBlank(examId)){
			List<Map<String,Object>> mapList=Lists.newArrayList();
			List<ExamSpecility> list = examService.getExamSpecilities(examId);
			for(ExamSpecility item:list){
				Specialty s=item.getSpecialty();
				if(s!=null){
					Map<String,Object> map=Maps.newHashMap();
					map.put("id", s.getId());
					map.put("title", s.getTitle());
					mapList.add(map);
				}
			}
			reuslt= JsonMapper.toJsonString(mapList);
		}
		return reuslt;
	}
	
	/**
	 * 查询选中的班级
	 * @param examId
	 */
	@ResponseBody
	@RequestMapping(value = "findClassByExamId")
	public String findClassByExamId(String examId) {
		long beginTime = System.currentTimeMillis();
		
		String reuslt="";
		if(StringUtils.isNotBlank(examId)){
			List<Map<String,Object>> mapList=Lists.newArrayList();
			List<ExamClass> list = examService.getExamClass(examId);
			for(ExamClass item:list){
				SchoolClass s=item.getSchoolClass();
				if(s!=null){
					Map<String,Object> map=Maps.newHashMap();
					map.put("id", s.getId());
					map.put("title", s.getTitle());
					mapList.add(map);
				}
			}
			reuslt=JsonMapper.toJsonString(mapList);
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println(DateUtils.formatDateTime(endTime - beginTime));
		return reuslt;
	}
	
	
	
   /**
    * 得到老师创建的题库
    * @return
    */
   public  List<CourseQuestionlib> teacherQuesLib(){
		 User teacher=TearcherUserUtils.getUser();
		 Map<String,String> map=TearcherUserUtils.getTeacherIdAndCourseVersionId();
		 
		 CourseQuestionlib courseQuestionlib=new CourseQuestionlib();
		 courseQuestionlib.setUser(teacher);
		 courseQuestionlib.setVersionId(map.get("versionId"));
		 courseQuestionlib.setSchoolId(teacher.getCompany().getId());
	   return courseQuestionlibService.findList(courseQuestionlib);
   }
	   
   
  public List<SchoolClass>  teacherClasss(){
	  Page<SchoolClass> page = examService.findClassList(new Page<SchoolClass>(0,-1,-1), new SchoolClass()); 
      if(page !=null)
        return page.getList();
      
      return null;
  }
  
    /**
     * 下载试题组卷试题
     * @param request
     * @param response
     */
	@RequestMapping("downLoad/questionsById")
	public  void  downLoadWord(HttpServletRequest request,HttpServletResponse response){
		String quesDetailId=request.getParameter("quesDetailId");
		String mainTitle=request.getParameter("mainTitle");
		String subTitle =request.getParameter("subTitle");
		
		if(StringUtils.isNotBlank(quesDetailId)){
			String  quesJson=examService.findQuesTionsByExamDetailId2Json(quesDetailId,mainTitle,subTitle);
			
			
			Examdetail examdetail=new Examdetail();
			examdetail.setMainTitle(mainTitle);
			examdetail.setSubTitle(subTitle);
			examdetail.setId(quesDetailId);
			examDetailService.updateSelectById(examdetail);
			
			//PrintWriter writer=null;
			BufferedReader br=null;
			BufferedWriter bw=null;
			OutputStream out=null;
			try{
				 User  teacher=TearcherUserUtils.getUser();
					
				 String questionPath = request.getSession().getServletContext().getRealPath("/questionFile");
	               
				 
				 System.out.println("************************************"+questionPath);
				 
				 String newQuestionFileName = teacher.getCompany().getId()  +File.separator
							       +teacher.getId()+File.separator
							       + new SimpleDateFormat("yyyy-MM").format(new Date())  +File.separator;
					
					
					
				File targetQuestionFile = new File(questionPath,newQuestionFileName);
				if(!targetQuestionFile.exists()){
					targetQuestionFile.mkdirs();
				}
				
				 String txtPath = targetQuestionFile.toString()+File.separator+quesDetailId+".txt";
				 File txtFile =new File(txtPath);
				 bw = new BufferedWriter(new FileWriter(txtFile));
				 bw.write(quesJson);
				 bw.flush();
				 bw.close();
				 
				 
				 
					
				 String wordName=quesDetailId+".docx";
				 String exePath =  questionPath+File.separator+"outWord"+File.separator+"ExportExamConsole.exe" ;
				 //第一个是txt文件名，第二个是word文件名，
				 String[] cmds = {"cmd.exe","/C",exePath, txtFile.toString(),targetQuestionFile.toString()+File.separator+wordName};//quesJson,targetQuestionFile.toString()+File.separator+wordName
				 Process  process=Runtime.getRuntime().exec(cmds);
				 
				InputStreamReader reader= new InputStreamReader(process.getInputStream());
				br=new BufferedReader(reader);
				String temp=null;
				while((temp=br.readLine())!=null){
					System.out.println(temp);
				}
				br.close();
				 
				File filePath=new File(targetQuestionFile,wordName);
				/*boolean   okFlag =false;
				for (int i = 0; i < 10; i++) {
					Thread.sleep(5000);
					if(filePath.exists()){
						Thread.sleep(2000);
						okFlag =true;
						break;
					}
					okFlag =false;
				}
					*/
				
				
				// 创建输出流
				out = response.getOutputStream();
				if(!filePath.exists()){
					out.write("下载失败".getBytes());
					out.close();
					out.flush();
				 return;
				}
			
				
				//response.reset();
				response.setContentType("application/octet-stream; charset=utf-8");
				
				
				boolean isMSIE = HttpUtils.isMSBrowser(request);
				Exam exam=examService.getExamByExamDetailId(quesDetailId);
				//String realname="组卷试题.docx";
				String realname=exam.getTitle()+".docx";
				if (isMSIE) {
					realname = URLEncoder.encode(realname, "UTF-8");
				}else {
					realname = new String(realname.getBytes("UTF-8"), "ISO-8859-1");
				}
				
				response.setHeader("Content-Disposition", "attachment;filename="+realname);
				// 读取要下载的文件，保存到文件输入流
				FileInputStream in = new FileInputStream(filePath);
				// 创建缓冲区
				byte buffer[] = new byte[1024];
				int len = 0;
				// 循环将输入流中的内容读取到缓冲区当中
				while ((len = in.read(buffer)) > 0) {
					// 输出缓冲区的内容到浏览器，实现文件下载
					out.write(buffer, 0, len);
				}
				// 关闭文件输入流
				in.close();
				// 关闭输出流
				out.close();
				out.flush();
		       return;
			} catch (Exception e) {
				response.setContentType("text/html;charset=UTF-8");
				try{
					out.write("下载失败".getBytes());
					out.close();
					out.flush();
				}catch(Exception ex){
					
				}
				
			}finally{
				try {
					if(out!=null){
						out.close();
					}
					if(br !=null){
					   br.close();
					}
					if(bw !=null){
						bw.flush();
						bw.close();
					}
				} catch (IOException e) {
				}		
			}
				
			
			
		}
		
	}
	
	
	

		public  static class HttpUtils {//工具类
		    private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
		
		    public static boolean isMSBrowser(HttpServletRequest request) {
		        String userAgent = request.getHeader("User-Agent");
		        for (String signal : IEBrowserSignals) {
		            if (userAgent.contains(signal))
		                return true;
		        }
		        return false;
		    }
		}
	
	
	/**
	 * 下载试题组卷答案
	 * @param request
	 * @param response
	 */
	@RequestMapping("downLoad/questionsAnswerById")
	public  void  downLoadQuestionsAnswerById(HttpServletRequest request,HttpServletResponse response){
		
		String quesDetailId=request.getParameter("quesDetailId");
		String mainTitle=request.getParameter("mainTitle");
		String subTitle =request.getParameter("subTitle");
		
		if(StringUtils.isNotBlank(quesDetailId)){
			Examdetail examdetail=new Examdetail();
			examdetail.setMainTitle(mainTitle);
			examdetail.setSubTitle(subTitle);
			examdetail.setId(quesDetailId);
			examDetailService.updateSelectById(examdetail);
			
			String  quesJson=examService.findQuesTionsAnswerByExamDetailId2Json(quesDetailId,mainTitle,subTitle);
			
			//PrintWriter writer=null;
			BufferedReader br=null;
			BufferedWriter bw=null;
			
			OutputStream out=null;
			
			try{
				 User  teacher=TearcherUserUtils.getUser();
					
				 String questionPath = request.getSession().getServletContext().getRealPath("/questionFile");
	               
				 String newQuestionFileName = teacher.getCompany().getId()  +File.separator
							       +teacher.getId()+File.separator
							       + new SimpleDateFormat("yyyy-MM").format(new Date())  +File.separator;
					
					
					
				File targetQuestionFile = new File(questionPath,newQuestionFileName);
				if(!targetQuestionFile.exists()){
					targetQuestionFile.mkdirs();
				}
				
				 String txtPath = targetQuestionFile.toString()+File.separator+"answer_"+quesDetailId+".txt";
				 File txtFile =new File(txtPath);
				 bw = new BufferedWriter(new FileWriter(txtFile));
				 bw.write(quesJson);
				 bw.flush();
				 bw.close();
				 
				 
				 
					
				 String wordName="answer_"+quesDetailId+".docx";
				 String exePath =  questionPath+File.separator+"outWord"+File.separator+"ExportExamConsole.exe" ;
				 //第一个是txt文件名，第二个是word文件名，
				 String[] cmds = {"cmd.exe","/C",exePath, txtFile.toString(),targetQuestionFile.toString()+File.separator+wordName};//quesJson,targetQuestionFile.toString()+File.separator+wordName
				 Process  process=Runtime.getRuntime().exec(cmds);
				 
				InputStreamReader reader= new InputStreamReader(process.getInputStream());
				br=new BufferedReader(reader);
				String temp=null;
				while((temp=br.readLine())!=null){
					System.out.println(temp);
				}
				br.close();
				 
				
				File filePath=new File(targetQuestionFile,wordName);
				/*boolean   okFlag =false;
				for (int i = 0; i < 10; i++) {
					Thread.sleep(5000);
					if(filePath.exists()){
						Thread.sleep(2000);
						okFlag =true;
						break;
					}
					okFlag =false;
				}
					*/
			
				out = response.getOutputStream();
				if(!filePath.exists()){
					out.write("下载失败".getBytes());
					out.close();
					out.flush();
				 return;
				}
				 
				//response.reset();
				response.setContentType("application/octet-stream; charset=utf-8");
				
				
				boolean isMSIE = HttpUtils.isMSBrowser(request);
				
				String realname="答案.docx";
				if (isMSIE) {
					realname = URLEncoder.encode(realname, "UTF-8");
				}else {
					realname = new String(realname.getBytes("UTF-8"), "ISO-8859-1");
				}
				
				response.setHeader("Content-Disposition", "attachment;filename="+realname);
				// 读取要下载的文件，保存到文件输入流
				FileInputStream in = new FileInputStream(filePath);
				// 创建缓冲区
				byte buffer[] = new byte[1024];
				int len = 0;
				// 循环将输入流中的内容读取到缓冲区当中
				while ((len = in.read(buffer)) > 0) {
					// 输出缓冲区的内容到浏览器，实现文件下载
					out.write(buffer, 0, len);
				}
				// 关闭文件输入流
				in.close();
				// 关闭输出流
				out.close();
		       return;
			} catch (Exception e) {
				//response.reset();
				response.setContentType("text/html;charset=UTF-8");
				try{
					out.write("下载失败".getBytes());
					out.close();
					out.flush();
				}catch(Exception ex){
					
				}
			}finally{
				try {
					if(out!=null){
						out.close();
					}
					if(br !=null){
					   br.close();
					}
					if(bw !=null){
						bw.flush();
						bw.close();
					}
				} catch (IOException e) {
				}		
			}
				
			
		}
	}
  
  
  
  
  
  
  
  
  
	
}
