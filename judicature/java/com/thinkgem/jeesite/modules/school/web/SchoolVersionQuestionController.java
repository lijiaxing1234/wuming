/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.HTMLTagDel;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.word.ExportWord;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.QuestionlibImport;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.entity.question.Cell;
import com.thinkgem.jeesite.modules.questionlib.entity.question.Qtable;
import com.thinkgem.jeesite.modules.questionlib.entity.question.Questions;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.QuestionlibImportService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.student.utils.FastJsonUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;

/**
 * 试题Controller
 * @author webcat
 * @version 2016-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/school/schoolversionQuestion")
public class SchoolVersionQuestionController extends BaseController {

	@Autowired
	private VersionQuestionService versionQuestionService;
	
	@Autowired
	private CourseKnowledgeService courseKnowledgeService;
	
	@Autowired
	private QuestionlibImportService questionlibImportService;
	@Autowired
	private  SpecialtyService specialtyService ;
	@Autowired
	private CourseQuestionlibService courseQuestionlibService;
	
	@Autowired
	private CourseVesionService  courseVesionService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private OfficeService officeService ;
	
	
	@ModelAttribute
	public VersionQuestion get(@RequestParam(required=false) String id) {
		VersionQuestion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = versionQuestionService.get(id);
		}
		if (entity == null){
			entity = new VersionQuestion();
		}
		return entity;
	}
	
	@RequiresPermissions("school:schoolversionQuestion:view")
	@RequestMapping(value = {"index",""})
	public String index(VersionQuestion versionQuestion,@RequestParam(required=false) String courseQuestionlibId, Model model) {
	  
		CourseQuestionlib	courseQuestionlib =new CourseQuestionlib();
		courseQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
		List<CourseQuestionlib> list = courseQuestionlibService.findList(courseQuestionlib); 
		model.addAttribute("list", list);
		model.addAttribute("courseQuestionlibId", (list!=null&&list.size()>0)?list.get(0).getId():"");
		
		return "modules/school/schoolversionQuestionIndex";
	}
	
	@RequiresPermissions("school:schoolnlib:versionQuestion:view")
	@RequestMapping(value = {"list", ""})
	public String list(VersionQuestion versionQuestion, @RequestParam(required=false) String extId, @RequestParam(required=false) String courseQuestionlibId,  @RequestParam(required=false) String qdelFlag, HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		List<VersionQuestion> list = new ArrayList<VersionQuestion>();
		model.addAttribute("courseQuestionlibId", courseQuestionlibId);
		Page<VersionQuestion> page = new Page<VersionQuestion>(request, response);
		
		versionQuestion.setPage(page);
		
		if(StringUtils.isBlank(extId) ){
			return "modules/school/schoolversionQuestionList";
		}else{
			versionQuestion.setCourseKnowledge(new CourseKnowledge(extId));
			versionQuestion.setQuestionlibId(courseQuestionlibId);
			list = versionQuestionService.findQuestionList(versionQuestion);
		}
		
		 
		
		model.addAttribute("extId", extId);
		//model.addAttribute("list", list);
		page = page.setList(list);
		
		model.addAttribute("page", page);
		try {
			model.addAttribute("versionQuestion", versionQuestion);
			CourseQuestionlib	courseQuestionlib =courseQuestionlibService.get(courseQuestionlibId);
			model.addAttribute("courseVersionId",courseQuestionlib==null?"":courseQuestionlib.getVersionId() );
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return "modules/school/schoolversionQuestionList";
	}
	
	private static String getString(String str,String replace1,String replace2,String replace){
		StringBuffer sb = new StringBuffer();
		int length = str.length();
		int begin = 0;
		int end = 0;
		String subString = "";
		String s = "";
		while(begin<length && StringUtils.isNotBlank(str) &&str.length()>replace1.length()+replace2.length()){
			if(str.contains(replace1) && str.contains(replace2)){
				end = begin+str.indexOf(replace1);
				sb.append(str.substring(begin,end));	//-replace1.length()
				s = str.substring(end);
			}else{
				break;
			}
			subString = getSubString(s, replace1, replace2);
			if("".equals(subString)){
				break;
			}
			begin = begin + subString.length();
			s= str.substring(begin);
			sb.append(replace);
		}
		sb.append(s);
		return sb.toString();
	}
	
	private static String getSubString(String str,String replace1,String replace2){
		if(str.length()>replace1.length() && str.length()>replace2.length() && str.contains(replace1) && str.contains(replace2)){
			return str.substring(str.indexOf(replace1), str.indexOf(replace2)+replace2.length());
		}
		return "";
	}

 
	@RequestMapping(value = "form")
	public String form(VersionQuestion versionQuestion, @RequestParam(required=false) String extId,  @RequestParam(required=false) String courseQuestionlibId, @RequestParam(required=false) String operation, HttpServletRequest request,  HttpServletResponse response,  Model model) {
		 
			try {
				
				if(StringUtils.isNotBlank(versionQuestion.getTitle())){
					versionQuestion.setTitle(Encodes.unescapeHtml(versionQuestion.getTitle()));
					versionQuestion.setTitle(versionQuestion.getTitle().trim());	//<p>  </p>
					versionQuestion.setTitle(versionQuestion.getTitle().trim());
				}
				if(StringUtils.isNotBlank(versionQuestion.getAnswer0())){
					versionQuestion.setAnswer0(Encodes.unescapeHtml(versionQuestion.getAnswer0()));
					versionQuestion.setAnswer0(versionQuestion.getAnswer0().trim());	//<p>  </p>
					versionQuestion.setAnswer0(versionQuestion.getAnswer0().trim());
				}
				if(StringUtils.isNotBlank(versionQuestion.getDescription())){
					versionQuestion.setDescription(Encodes.unescapeHtml(versionQuestion.getDescription()));
					versionQuestion.setDescription(versionQuestion.getDescription().trim());	//<p>  </p>
					versionQuestion.setDescription(versionQuestion.getDescription().trim());
				}
				
				if(StringUtils.isNotBlank(versionQuestion.getId())){
					   List<CourseKnowledge> ckList=versionQuestionService.findKnowledgeKist(versionQuestion);
					   model.addAttribute("ckIds", Collections3.extractToString(ckList,"id", ","));
					   model.addAttribute("ckTitles", Collections3.extractToString(ckList,"title", ","));
					}
				
				model.addAttribute("extId", extId);
				model.addAttribute("versionQuestion", versionQuestion);
				model.addAttribute("courseQuestionlibId", courseQuestionlibId);
				String courseVersionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
				model.addAttribute("courseVersionId", courseVersionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if("view".equals(operation)){
				return "modules/school/schoolversionQuestionView";
			}else {
				return "modules/school/schoolversionQuestionForm";
			}
		 
	}

	@RequiresPermissions("school:schoolversionQuestion:edit")
	@RequestMapping(value = "save")
	public String save(VersionQuestion versionQuestion, @RequestParam(required=false) String courseQuestionlibId, @RequestParam(required=false) String extId,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(versionQuestion.getTitle())){
			versionQuestion.setTitle(Encodes.unescapeHtml(versionQuestion.getTitle()));
			versionQuestion.setTitle(versionQuestion.getTitle().trim());	//<p>  </p>
			versionQuestion.setTitle(versionQuestion.getTitle().trim());
		}
		if(StringUtils.isNotBlank(versionQuestion.getAnswer0())){
			versionQuestion.setAnswer0(Encodes.unescapeHtml(versionQuestion.getAnswer0()));
			versionQuestion.setAnswer0(versionQuestion.getAnswer0().trim());	//<p>  </p>
			versionQuestion.setAnswer0(versionQuestion.getAnswer0().trim());
		}
		if(StringUtils.isNotBlank(versionQuestion.getDescription())){
			versionQuestion.setDescription(Encodes.unescapeHtml(versionQuestion.getDescription()));
			versionQuestion.setDescription(versionQuestion.getDescription().trim());	//<p>  </p>
			versionQuestion.setDescription(versionQuestion.getDescription().trim());
		}
		if (!beanValidator(model, versionQuestion)){
			return form(versionQuestion,extId, courseQuestionlibId,"modify", request, response, model);
		}
		String examCode = versionQuestion.getCourseKnowledge().getTitle();
		String[] examCodes = examCode.split(",");
		StringBuffer sb = new StringBuffer();
//		for (String ec : examCodes) {
//			if(!StringUtils.isBlank(ec)){
//				sb.append(ec+",");
//			}
//		}
//		examCode = sb.toString();
		versionQuestion.setExamCode(examCodes[0]);
		if(versionQuestion!=null && StringUtils.isNotBlank(versionQuestion.getQuestionlibId())){
			courseQuestionlibId = versionQuestion.getQuestionlibId();
		}else{
			versionQuestion.setQuestionlibId(courseQuestionlibId);
		}
		String courseVersionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
		versionQuestion.setVersionId(courseVersionId);
		if(versionQuestion.getOffice()==null){
			versionQuestion.setOffice(UserUtils.getUser().getCompany());
		}
		versionQuestionService.save(versionQuestion);
		versionQuestionService.deleteKnowledgeIdAndQuestionId(versionQuestion);
		String knowledgeId = versionQuestion.getCourseKnowledge().getId();
		if(StringUtils.isNotBlank(knowledgeId))
		{
			String[] ids = knowledgeId.split(",");
			for (String kid : ids) {
				if(StringUtils.isBlank(kid)){
					continue;
				}
				versionQuestion.setCourseKnowledge(new CourseKnowledge(kid));
				versionQuestionService.saveKnowledgeIdAndQuestionId(versionQuestion);
			}
		}
		
		addMessage(redirectAttributes, "保存试题成功");
		return "redirect:"+Global.getAdminPath()+"/school/schoolversionQuestion/list?repage&extId="+extId+"&courseQuestionlibId="+courseQuestionlibId;
	}
	
	@RequiresPermissions("school.web:schoolversionQuestion:delete")
	@RequestMapping(value = "delete")
	public String delete(VersionQuestion versionQuestion,String[] ids, @RequestParam(required=false) String extId, @RequestParam(required=false) String courseQuestionlibId,@RequestParam(required=false) String qdelFlag, RedirectAttributes redirectAttributes) {
		if(qdelFlag==null){
			qdelFlag = versionQuestion.getDelFlag();
		}
		if(qdelFlag==null){
			qdelFlag = "1";
		}
		if(ids ==null){
			versionQuestionService.delete(versionQuestion);
		}else{
			for (String id : ids) {
				VersionQuestion vq = versionQuestionService.get(id);
				vq.setDelFlag(qdelFlag);
				versionQuestionService.save(vq);
			}
		}
		if("0".equals(qdelFlag)){
			qdelFlag = "2";	//打回重审、批量重审
		}else if("1".equals(qdelFlag)){
			qdelFlag = "2";	//重新审核、批量重审
		}else if("2".equals(qdelFlag)){
			qdelFlag = "0";	//审核通过、批量审核
		}
		addMessage(redirectAttributes, "操作成功！");
		return "redirect:"+Global.getAdminPath()+"/school/schoolversionQuestion/list?repage&extId="+extId+"&courseQuestionlibId="+courseQuestionlibId+"&qdelFlag="+qdelFlag;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String courseQuestionlibId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		CourseKnowledge ckl =new CourseKnowledge();
		if(StringUtils.isBlank(courseQuestionlibId)){
			return null;
		}
		String courseVesionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
		ckl.setVersionId(courseVesionId);
		List<CourseKnowledge> list = courseKnowledgeService.findList(ckl);
		//list.add(courseKnowledgeService.get("1"));
		for (int i=0; i<list.size(); i++){
			CourseKnowledge e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getTitle());
			mapList.add(map);
		}
		return mapList;
	}
	
	@RequiresPermissions("school:schoolversionQuestion:edit")
	@ResponseBody
	@RequestMapping(value = "importQuestions")
	public String importQuestions(@RequestParam(required=false) String importDataId, @RequestParam(required=false) String questionJsonData, HttpServletRequest request, HttpServletResponse response, Model model){
		if(StringUtils.isNotBlank(questionJsonData)){
			questionJsonData = (String) request.getParameter("questionJsonData");
		}
		System.err.println("questionJsonData"+questionJsonData);
		//datastr = Encodes.unescapeHtml(datastr);
		//irTitle  irTables  irUrlDate  irContent  irState
		//System.err.println("htmldatastr"+datastr);
		questionJsonData = questionJsonData.replace("&lt;o:p&gt;", "");	//&lt;o:p&gt;  &lt;/o:p&gt;
		questionJsonData = questionJsonData.replace("&lt;/o:p&gt;", "");
		System.err.println("替换前:"+questionJsonData);
		questionJsonData = questionJsonData.replaceAll(" ", " ");	//中文空格 替换为 英文空格
		System.err.println("替换后："+questionJsonData);
//		questionJsonData = Encodes.unescapeHtml(questionJsonData);
		System.err.println("rpdatastr"+questionJsonData);
		//datastr = Encodes.escapeHtml(datastr);
		if(StringUtils.isBlank(importDataId)){
			importDataId = (String)request.getParameter("importDataId");
		}
		QuestionlibImport questionlibImport = questionlibImportService.get(importDataId);
		System.err.println(questionlibImport.getId());
		String filePath = questionlibImport.getFilePath();
		String courseQuestionlibId = questionlibImport.getQuestionlibId();
		String versionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
		System.err.println(courseQuestionlibId);
		System.err.println(versionId);
		List<VersionQuestion> list = Lists.newArrayList();	//最终将json数据存入到list中，再保存
		Map<String,String> tableMap = Maps.newHashMap();	//存储试题保存信息，保存失败和成功的试题数量
		try{
			JSONObject obj = new JSONObject(questionJsonData); 
            String state = obj.getString("irState");
            if("0".equals(state)){
            	//读取表内容
                JSONArray contents = obj.getJSONArray("irContent");
                String urlDate = obj.getString("irUrlDate");
                //读取文档信息
                JSONObject tables = obj.getJSONObject("irTables");
                int tableCount = Integer.parseInt(tables.getString("count")); 
                //读取表信息
                JSONArray table = tables.getJSONArray("table");
                int recordCount = 0;
                int saveSuccess = 0;
                int saveFail = 0;
                StringBuffer sb = new StringBuffer("试题序号分别是：");
                for (int i = 0; i < tableCount; i++) {
                    JSONObject tb = table.getJSONObject(i);
                    int rowcount = Integer.parseInt(tb.getString("rowcount"));
                    int columncount = Integer.parseInt(tb.getString("columncount"));
//                	int rowcount = 20;
//                	int columncount = 13;
                    int beginIndex = recordCount;
                    int finalIndex = recordCount + rowcount*columncount;
                    List<Map<String,String>> dataList = Lists.newArrayList();
                    for (int j = beginIndex; j < finalIndex; j++) {
                        String name = contents.getJSONObject(j).getString("name");
                        JSONArray content = contents.getJSONObject(j).getJSONArray("content");
                        String columnContent = "";
                        for (int k = 0; k < content.length(); k++) {
                            String type = content.getJSONObject(k).getString("type");
                            if("1".equals(type)){	//文本
                                columnContent += Encodes.unescapeHtml(content.getJSONObject(k).getString("content")).replaceAll(" ", " ").trim();
                            }else if("2".equals(type)){	//图片保存路径
                            	String cContent = Encodes.unescapeHtml(content.getJSONObject(k).getString("content")).replaceAll(" ", " ").trim();
                            	System.err.println(cContent);
                            	System.err.println("filePath:"+filePath);
                            	columnContent += "<img alt=\"\" src=\""+filePath.replaceAll("\\\\", "/") + "picfile/" + cContent.substring(cContent.lastIndexOf("/")+1)+"\" style=\"width: 300px;\"/>";
                            	System.err.println(columnContent);
                            }else if("3".equals(type)){	//表格保存格式
                            	//columnContent += "<img alt=\"\" src=\""+filePath + content.getJSONObject(k).getString("content").trim()+"\" style=\"width: 120px; height: 90px; float: left;\"/>";
                            	columnContent += Encodes.unescapeHtml(content.getJSONObject(k).getString("content")).replaceAll(" ", " ").trim();
                            }else{
                                
                            }
                        }
                        Map<String,String> map = Maps.newHashMap();
                        map.put("name", name);
                        map.put("content", columnContent);
                        dataList.add(map);
                    }
                    
                    //保存试题
                    /**
                     * columncount为15时，按旧模板解析，旧模板不保存“试题编号”，
                     * columncount为16时，按新模板解析，新模板保存“试题编号”。
                     */
                    if(columncount==15){
	                    /**
	                	 * 旧模板
	                	 * 获取单元格内容		name x-x-x
	                	 * x-x-1:试题序号，x-x-2:知识点代号编码，x-x-3:测点（知识点），x-x-4:难度，x-x-5:题型，x-x-6:分值，
	                	 * x-x-7:答案(选择题/判断题答案，其他题型不填)，x-x-8:试题描述(题干)，
	                	 * x-x-9:A内容(选择题A选项，填空题第一空答案，判断题选项1，其他题型答案)，
	                	 * x-x-10:B内容(选择题B选项，填空题第二空答案，判断题选项2，其他题型不填),
	                	 * x-x-11:C内容(选择题C选项，填空题第三空答案，其他题型不填),
	                	 * x-x-12:D内容(选择题D选项，填空题第四空答案，其他题型不填),
	                	 * x-x-13:试题讲解，x-x-14:命题人，x-x-15:审题人
	                	 * 综上，所有题型均为15列
	                	 * 
	                	 */
	                    for (int j = beginIndex+columncount; j < finalIndex; j+=columncount) {
	                        VersionQuestion vq = new VersionQuestion();
	                        int count = 0;
	                        //1.获取知识点
	                        	//获取知识点编号，并反查知识点id
	                        String knowledgeCode = dataList.get(j+1).get("content").trim();
	                        String[] knowledgeCodes = knowledgeCode.split(",");
	                        List<String> knowledgeIds = Lists.newArrayList();
	                        for (String kc : knowledgeCodes) {
	                        	CourseKnowledge courseKnowledge = new CourseKnowledge();
	                        	courseKnowledge.setKnowledgeCode(kc.trim());
	                        	courseKnowledge.setVersionId(versionId);
	                            CourseKnowledge ck = courseKnowledgeService.getByKnowledgeCode(courseKnowledge);
	                            if(ck==null || ck.getId()==null){
	                            	continue;
	                            }else{
	                            	knowledgeIds.add(ck.getId());
	                            }
							}
	                        	//判断是否找到对应知识点，找到则继续下去，未找到则中止该试题的保存
	                        if(knowledgeIds.size()<1){
	                        	saveFail++;
	                        	String questionIndex = dataList.get(j).get("content").trim();
	                        	sb.append(questionIndex+",");
	                        	continue;
	                        }
	                        //知识点赋值
	                        String examCode = dataList.get(j+2).get("content").trim();
	                        vq.setExamCode(examCode);
	                        //2.获取题型   并赋值
	                        String quesType = dataList.get(j+4).get("content").trim();
	                        vq.setQuesType(setNewQuesType(quesType));
	                        //3.获取难度   并赋值
	                        String quesLevel = dataList.get(j+3).get("content").trim();
	                        vq.setQuesLevel(setNewQuesLevel(quesLevel));
	                        //4.获取分值   并赋值
	                        String quesPoint = dataList.get(j+5).get("content").trim();
	                        vq.setQuesPoint(quesPoint);
	                        //5.获取试题描述(即题干) 并赋值
	                        String title = dataList.get(j+7).get("content").trim();
	                        vq.setTitle(title);
	                        //单/多选择题	1/5
	                        if("S".equals(quesType) || "D".equals(quesType)){
	                            //6.获取4个选项   并赋值
	                            String choice0 = dataList.get(j+8).get("content").trim();  //A
	                            String choice1 = dataList.get(j+9).get("content").trim();  //B
	                            String choice2 = dataList.get(j+10).get("content").trim();  //C
	                            String choice3 = dataList.get(j+11).get("content").trim();  //D
	                            vq.setChoice0(choice0);
	                            vq.setChoice1(choice1);
	                            vq.setChoice2(choice2);
	                            vq.setChoice3(choice3);
	                            count = 4;
	                            //7.获取答案   并赋值
	                            String answer0 = dataList.get(j+6).get("content").trim();
	                            vq.setAnswer0(answer0);
	                            //8.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //9.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //10.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                        }//填空题	2
	                        else if("T".equalsIgnoreCase(quesType)){
	                        	//6.获取4个答案   并赋值
	                            String answer0 = dataList.get(j+8).get("content").trim();  //空1
	                            String answer1 = dataList.get(j+9).get("content").trim();  //空2
	                            String answer2 = dataList.get(j+10).get("content").trim();  //空3
	                            String answer3 = dataList.get(j+11).get("content").trim();  //空4
//	                            if(StringUtils.isNotBlank(answer0)){
//	                            	vq.setChoice0(answer0);
//	                            	count++;
//	                            }
//	                            if(StringUtils.isNotBlank(answer0)){
//	                            	vq.setChoice1(answer1);
//	                            	count++;
//	                            }
//	                            if(StringUtils.isNotBlank(answer0)){
//	                            	vq.setChoice2(answer2);
//	                            	count++;
//	                            }
//	                            if(StringUtils.isNotBlank(answer0)){
//	                            	vq.setChoice3(answer3);
//	                            	count++;
//	                            }
	                            if(StringUtils.isNotBlank(answer0)){
	                            	vq.setAnswer0(answer0);
	                            	count++;
	                            }
	                            if(StringUtils.isNotBlank(answer1)){
	                            	vq.setAnswer1(answer1);
	                            	count++;
	                            }
	                            if(StringUtils.isNotBlank(answer2)){
	                            	vq.setAnswer2(answer2);
	                            	count++;
	                            }
	                            if(StringUtils.isNotBlank(answer3)){
	                            	vq.setAnswer3(answer3);
	                            	count++;
	                            }
	                            //7.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //8.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //9.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                        }
	                        //计算题/简单题/概念题/综合题/作图题/制表题/识图题/判断题		3/4/6/7/8/9/10/11
	                        else if("J".equals(quesType) || "E".equals(quesType) || "M".equals(quesType) || "L".equals(quesType) || "H".equals(quesType) || "B".equals(quesType) || "G".equals(quesType)){
	                            //6.获取答案   并赋值
	                            String answer0 = dataList.get(j+8).get("content").trim();
	                            vq.setAnswer0(answer0);
	                            //7.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //8.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //9.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                            count = 1;
	                        }
	                        else if("P".equals(quesType)){
	                        	//6.获取答案   并赋值
	                            String answer0 = dataList.get(j+6).get("content").trim();
	                            vq.setAnswer0(answer0);
	                            //7.获取2个选项   并赋值
	                            String choice0 = dataList.get(j+8).get("content").trim();  //A
	                            String choice1 = dataList.get(j+9).get("content").trim();  //B
	                            vq.setChoice0(choice0);
	                            vq.setChoice1(choice1);
	                            //8.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //9.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //10.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                            count = 2;
	                        }
	                        vq.setQuestionlibId(courseQuestionlibId);
	                        vq.setVersionId(versionId);
	                        vq.setCount(count+"");
	                        vq.setDelFlag("0");
	                        vq.setOffice(UserUtils.getUser().getCompany());
	                        list.add(vq);
	                        //保存试题
	                        versionQuestionService.save(vq);
	                        System.err.println(knowledgeIds);
	                        //保存知识点和试题的对应关系
	                        for (String kid : knowledgeIds) {
	                            if(StringUtils.isBlank(kid)){
	                                continue;
	                            }
	                            vq.setCourseKnowledge(new CourseKnowledge(kid));
	                            versionQuestionService.saveKnowledgeIdAndQuestionId(vq);
	                        }
	                        //保存试题和导入文档的对应关系
	                        vq.setImportId(importDataId);
	                        versionQuestionService.saveImportIdAndQuestionId(vq);
	                        saveSuccess++;
	                    }
                    }else if(columncount==16){
	                    /**
	                	 * 新模板
	                	 * 获取单元格内容		name x-x-x
	                	 * x-x-1:测点（知识点），x-x-2:知识点编码，x-x-3:题型，x-x-4:难度，x-x-5:试题序号，x-x-6:试题编码，
	                	 * x-x-7:分值，x-x-8:试题描述(题干)，
	                	 * x-x-9:A内容(选择题A选项，填空题第一空答案，判断题选项1，其他题型答案)，
	                	 * x-x-10:B内容(选择题B选项，填空题第二空答案，判断题选项2，其他题型不填),
	                	 * x-x-11:C内容(选择题C选项，填空题第三空答案，其他题型不填),
	                	 * x-x-12:D内容(选择题D选项，填空题第四空答案，其他题型不填),
	                	 * x-x-13:答案(选择题/判断题答案，其他题型不填)，
	                	 * x-x-14:试题讲解，x-x-15:命题人，x-x-16:审题人
	                	 * 综上，所有题型均为16列
	                	 * 
	                	 */
	                    for (int j = beginIndex+columncount; j < finalIndex; j+=columncount) {
	                    	VersionQuestion vq = new VersionQuestion();
	                    	int count = 0;
	                    	//获取试题编码
	                    	String questionCode = dataList.get(j+5).get("content").trim();
	                    	vq.setQuestionCode(questionCode);
	                    	//1.获取知识点
	                    	//获取知识点编号，并反查知识点id
	                    	String knowledgeCode = dataList.get(j+1).get("content").trim();
	                    	String[] knowledgeCodes = knowledgeCode.split(",");
	                    	List<String> knowledgeIds = Lists.newArrayList();
	                    	for (String kc : knowledgeCodes) {
	                    		CourseKnowledge courseKnowledge = new CourseKnowledge();
	                    		courseKnowledge.setKnowledgeCode(kc.trim());
	                    		courseKnowledge.setVersionId(versionId);
	                    		CourseKnowledge ck = courseKnowledgeService.getByKnowledgeCode(courseKnowledge);
	                    		if(ck==null || ck.getId()==null){
	                    			continue;
	                    		}else{
	                    			knowledgeIds.add(ck.getId());
	                    		}
	                    	}
	                    	//判断是否找到对应知识点，找到则继续下去，未找到则中止该试题的保存
	                    	if(knowledgeIds.size()<1){
	                    		saveFail++;
	                    		String questionIndex = dataList.get(j+4).get("content").trim();
	                        	sb.append(questionIndex+",");
	                    		continue;
	                    	}
	                    	//知识点赋值
	                    	String examCode = dataList.get(j).get("content").trim();
	                    	vq.setExamCode(examCode);
	                    	//2.获取题型   并赋值
	                    	String quesType = dataList.get(j+2).get("content").trim();
	                    	vq.setQuesType(setNewQuesType(quesType));
	                    	//3.获取难度   并赋值
	                    	String quesLevel = dataList.get(j+3).get("content").trim();
	                    	vq.setQuesLevel(setNewQuesLevel(quesLevel));
	                    	//4.获取分值   并赋值
	                    	String quesPoint = dataList.get(j+6).get("content").trim();
	                    	vq.setQuesPoint(quesPoint);
	                    	//5.获取试题描述(即题干) 并赋值
	                    	String title = dataList.get(j+7).get("content").trim();
	                    	vq.setTitle(title);
	                    	//单/多选择题	1/5
	                    	if("S".equals(quesType) || "D".equals(quesType)){
	                    		//6.获取4个选项   并赋值
	                    		String choice0 = dataList.get(j+8).get("content").trim();  //A
	                    		String choice1 = dataList.get(j+9).get("content").trim();  //B
	                    		String choice2 = dataList.get(j+10).get("content").trim();  //C
	                    		String choice3 = dataList.get(j+11).get("content").trim();  //D
	                    		vq.setChoice0(choice0);
	                    		vq.setChoice1(choice1);
	                    		vq.setChoice2(choice2);
	                    		vq.setChoice3(choice3);
	                    		count = 4;
	                    		//7.获取答案   并赋值
	                    		String answer0 = dataList.get(j+12).get("content").trim();
	                    		vq.setAnswer0(answer0);
	                    		//8.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//9.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//10.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    	}//填空题	2
	                    	else if("T".equalsIgnoreCase(quesType)){
	                    		//6.获取4个答案   并赋值
	                    		String answer0 = dataList.get(j+8).get("content").trim();  //空1
	                    		String answer1 = dataList.get(j+9).get("content").trim();  //空2
	                    		String answer2 = dataList.get(j+10).get("content").trim();  //空3
	                    		String answer3 = dataList.get(j+11).get("content").trim();  //空4
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice0(answer0);
	                    			count++;
	                    		}
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice1(answer1);
	                    			count++;
	                    		}
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice2(answer2);
	                    			count++;
	                    		}
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice3(answer3);
	                    			count++;
	                    		}
	                    		//7.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//8.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//9.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    	}
	                    	//计算题/简单题/概念题/综合题/作图题/制表题/识图题/判断题		3/4/6/7/8/9/10/11
	                    	else if("J".equals(quesType) || "E".equals(quesType) || "M".equals(quesType) || "L".equals(quesType) || "H".equals(quesType) || "B".equals(quesType) || "G".equals(quesType)){
	                    		//6.获取答案   并赋值
	                    		String answer0 = dataList.get(j+8).get("content").trim();
	                    		vq.setAnswer0(answer0);
	                    		//7.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//8.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//9.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    		count = 1;
	                    	}
	                    	else if("P".equals(quesType)){
	                    		//6.获取答案   并赋值
	                    		String answer0 = dataList.get(j+12).get("content").trim();
	                    		vq.setAnswer0(answer0);
	                    		//7.获取2个选项   并赋值
	                    		String choice0 = dataList.get(j+8).get("content").trim();  //A
	                    		String choice1 = dataList.get(j+9).get("content").trim();  //B
	                    		vq.setChoice0(choice0);
	                    		vq.setChoice1(choice1);
	                    		//8.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//9.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//10.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    		count = 2;
	                    	}
	                    	vq.setQuestionlibId(courseQuestionlibId);
	                    	vq.setVersionId(versionId);
	                    	vq.setCount(count+"");
	                    	vq.setDelFlag("0");
	                    	vq.setOffice(UserUtils.getUser().getCompany());
	                    	list.add(vq);
	                    	//保存试题
	                    	versionQuestionService.save(vq);
	                    	System.err.println(knowledgeIds);
	                    	//保存知识点和试题的对应关系
	                    	for (String kid : knowledgeIds) {
	                    		if(StringUtils.isBlank(kid)){
	                    			continue;
	                    		}
	                    		vq.setCourseKnowledge(new CourseKnowledge(kid));
	                    		versionQuestionService.saveKnowledgeIdAndQuestionId(vq);
	                    	}
	                    	//保存试题和导入文档的对应关系
	                    	vq.setImportId(importDataId);
	                    	versionQuestionService.saveImportIdAndQuestionId(vq);
	                    	saveSuccess++;
	                    }
                    }
                    recordCount = finalIndex;
                }
                String failMessage = sb.toString();
                tableMap.put("saveSuccess", saveSuccess+"");		// int saveSuccess = 0;  int saveFail = 0;
                tableMap.put("saveFail", saveFail+"");
                tableMap.put("message", "success");
                if(saveFail>0){
                	tableMap.put("failMessage", failMessage.substring(0, failMessage.lastIndexOf(',')));
                }
                System.err.println(JSON.toJSON(tableMap).toString());
                return JSON.toJSON(tableMap).toString();
//                response.getWriter().write(JSON.toJSON(tableMap).toString());
            }else{
            	tableMap.put("message", "fail");
            	System.err.println(JSON.toJSON(tableMap).toString());
//            	response.getWriter().write(JSON.toJSON(tableMap).toString());
            	return JSON.toJSON(tableMap).toString();
            }
		}catch(Exception e){
			e.printStackTrace();
		} 
		tableMap.put("message", "fail");
		System.err.println(JSON.toJSON(tableMap).toString());
//		try {
//			response.getWriter().write(JSON.toJSON(tableMap).toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return JSON.toJSON(tableMap).toString();
	}
	
	//试题类型
	private  String setNewQuesType(String quesType) {
		if("S".equals(quesType)){
			return "1";		//单选题，大写S
		}else if("T".equals(quesType)){
			return "2";		//填空题，大写T
		}else if("J".equals(quesType)){
			return "3";		//计算题，大写J
		}else if("E".equalsIgnoreCase(quesType)){
			return "4";		//简答题，大写E
		}else if("D".equals(quesType)){
			return "5";		//多选题，大写D
		}else if("M".equals(quesType)){
			return "6";		//概念题（名词解释），大写M
		}else if("L".equals(quesType)){
			return "7";		//综合题（论述题），大写L
		}else if("H".equals(quesType)){
			return "8";		//作图题（画图），大写H
		}else if("B".equals(quesType)){
			return "9";		//制表题，大写B
		}else if("G".equals(quesType)){
			return "10";		//识图题(Graph)，大写G
		}else if("P".equals(quesType)){
			return "11";		//判断题，大写P
		}else{
			return "";
		}
	}
	
	public String getNewQuesType(String quesType,boolean bl){
		if("1".equals(quesType)){
			return bl?"S":"单选题S";		//单选题，大写S
		}else if("2".equals(quesType)){
			return bl?"T":"填空题T";		//填空题，大写T
		}else if("3".equals(quesType)){
			return bl?"J":"计算题J";		//计算题，大写J
		}else if("4".equalsIgnoreCase(quesType)){
			return bl?"E":"简答题E";		//简答题，大写E
		}else if("5".equals(quesType)){
			return bl?"D":"多选题D";		//多选题，大写D
		}else if("6".equals(quesType)){
			return bl?"M":"概念题M";		//概念题（名词解释），大写M
		}else if("7".equals(quesType)){
			return bl?"L":"综合题L";		//综合题（论述题），大写L
		}else if("8".equals(quesType)){
			return bl?"H":"作图题H";		//作图题（画图），大写H
		}else if("9".equals(quesType)){
			return bl?"B":"制表题B";		//制表题，大写B
		}else if("10".equals(quesType)){
			return bl?"G":"识图题G";		//识图题(Graph)，大写G
		}else if("11".equals(quesType)){
			return bl?"P":"判断题P";		//判断题，大写P
		}else{
			return "";
		}
	}

	//试题难度
	private String setNewQuesLevel(String quesLevel){
		if("a".equalsIgnoreCase(quesLevel)){
			return "1";	//简单 容易
		}else if("b".equalsIgnoreCase(quesLevel)){
			return "2";	//一般 中档
		}else if("c".equalsIgnoreCase(quesLevel)){
			return "3";//困难 难题
		}else{
			return "";
		}
	}
	
	//试题难度
	private String getNewQuesLevel(String quesLevel,boolean bl){
		if("1".equalsIgnoreCase(quesLevel)){
			return bl?"a":"简单a";	//简单 容易 a
		}else if("2".equalsIgnoreCase(quesLevel)){
			return bl?"b":"一般b";	//一般 中档 b
		}else if("3".equalsIgnoreCase(quesLevel)){
			return bl?"c":"困难c";//困难 难题 c
		}else{
			return "";
		}
	}
	
	/**
	 * 保存导入试题word文档
	 * @param importId					导入文档id
	 * @param file						压缩文件
	 * @param request					请求
	 * @param response					响应
	 * @param model						
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
//	@RequiresPermissions("school:schoolversionQuestion:edit")
	@ResponseBody
	@RequestMapping(value = "importQuestionFile")
	public String importQuestionFile(@RequestParam(required=false) String importDataId,@RequestParam(required=false) String questionJsonData, @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, Model model){
		//将request转化为multipart request
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//1.保存上传文档
		String path = request.getSession().getServletContext().getRealPath("/");
		System.err.println(path);
		String questionPath = request.getSession().getServletContext().getRealPath("/userfiles/1/images/upload/questionFile/");
		System.err.println(questionPath);
		if(file==null){
			System.err.println("我是空值");
			file = multipartRequest.getFile("file");
		}else{
			System.err.println("我不是空值");
		}
		String questionFileName = file.getOriginalFilename();
		System.err.println(questionFileName);
		String newQuestionFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(1000) + "-" + questionFileName;
		String finalPath = questionPath+newQuestionFileName;
		finalPath = finalPath.replaceAll("\\*", "/");
		System.err.println(newQuestionFileName);
		System.err.println(finalPath);
		File targetQuestionFile = new File(questionPath,newQuestionFileName);
		if(!targetQuestionFile.exists()){
			targetQuestionFile.mkdirs();
		}
			//保存
		try{
			file.transferTo(targetQuestionFile);
			//使用FileUtils包下的unZipFiles方法解压
			String descFileName = finalPath.substring(0, finalPath.lastIndexOf('.'));
			System.err.println(descFileName);
			FileUtils.unZipFiles(finalPath, descFileName);
	        System.out.println("******************解压完毕********************");  
		}catch(Exception e){
			e.printStackTrace();
		}
		//2.保存上传文档的相关信息
		QuestionlibImport questionlibImport = questionlibImportService.get(importDataId);
		finalPath = request.getContextPath()+ "/" + finalPath.substring(finalPath.lastIndexOf("userfiles"), finalPath.lastIndexOf('.')).replaceAll("\\\\", "/") +"/" + questionFileName.substring(0, questionFileName.lastIndexOf('.')) + "/";	//"/school/school" + 
		questionlibImport.setFilePath(finalPath);
		questionlibImport.setDocName(questionFileName);
		System.err.println(questionlibImport.getFilePath());
		questionlibImportService.save(questionlibImport);
		return "{\"importDataId\":importDataId}";
	}
	
	public static void main(String[] args) {
		String filePath="D:\\java\\workneon\\questionlib\\webapp\\questionFile\\20160930104601559file.zip";
		String targetPath="D:\\java\\workneon\\questionlib\\webapp\\questionFile\\20160930104601559file";
		FileUtils.unZipFiles(filePath, targetPath);
	}
	
	@RequiresPermissions("school:schoolversionQuestion:view")
	@RequestMapping(value = "importDoc")
	public String importDoc(VersionQuestion versionQuestion,QuestionlibImport questionlibImport, @RequestParam(required=false) String extId, @RequestParam(required=false) String courseQuestionlibId, HttpServletRequest request, HttpServletResponse response,  Model model) {
		if(StringUtils.isBlank(courseQuestionlibId)){
			model.addAttribute("message", "操作有误，请刷新页面！");
			return list(versionQuestion, extId, courseQuestionlibId,"0", request, response, model);
		}else{
			String courseVersionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
			model.addAttribute("courseVersionId", courseVersionId);
			model.addAttribute("courseQuestionlibId", courseQuestionlibId);
			model.addAttribute("extId", extId);
			model.addAttribute("versionQuestion", versionQuestion);
			model.addAttribute("questionlibImport", questionlibImport);
			return "modules/school/schoolversionQuestionSaveImportDoc";
		}
	}
	
	@RequiresPermissions("school:schoolversionQuestion:edit")
	@ResponseBody
	@RequestMapping(value = "saveQuestionlibImport")
	public String saveQuestionlibImport(@RequestParam(required=false) String courseQuestionlibId, QuestionlibImport questionlibImport, Model model) {
		Map<String,String> map = Maps.newHashMap();
		System.err.println(courseQuestionlibId);
		if(StringUtils.isNotBlank(courseQuestionlibId)){
			questionlibImport.setQuestionlibId(courseQuestionlibId);
		}else{
			map.put("message", "0");	//保存失败！请刷新页面重试!
			map.put("importDataId","");
			return JSON.toJSON(map).toString();
		}
		User user = UserUtils.getUser();
		questionlibImport.setUser(user);
		questionlibImport.setPhone(user.getPhone());
		Office office = user.getOffice();
		questionlibImport.setOffice(office);
		String specialtyId = QuestionlibTld.getCourseByID(QuestionlibTld.getCourseVersionByVersionId(QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId()).getCourseId()).getSpecialtyId();
		questionlibImport.setSpecialtyId(specialtyId);
		questionlibImport.setDelFlag("0");
		questionlibImportService.save(questionlibImport);
		map.put("message", "1");		//保存成功！
		map.put("importDataId", questionlibImport.getId());
		return JSON.toJSON(map).toString();
	}

	@ResponseBody
	@RequestMapping(value = "emportQuestionWord")
	public String emportQuestionWord(VersionQuestion versionQuestion ,@RequestParam(required=false) String courseQuestionlibId, @RequestParam(required=false) String delFlag, HttpServletRequest request, HttpServletResponse response, Model model){

		String knowledgeId = "";
		String quesType = versionQuestion.getQuesType();
		String quesLevel = versionQuestion.getQuesLevel();
		String docTitle = "";	//文档标题
		String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> knowledgeIds = Lists.newArrayList();
		List<VersionQuestion> versionQuestionList = Lists.newArrayList();
		String outPath = ""; //导出路径
		String docName = "";	//导出word文档名
		int success = 0;	//成功导出试题数
		Map<String,String> messageMap = Maps.newHashMap();
		System.err.println("courseQuestionlibId"+courseQuestionlibId);
		//1.从数据库获取待导出的试题集合versionQuestionList
		if(StringUtils.isBlank(courseQuestionlibId)){
			messageMap.put("message", "操作有误，请刷新页面后重试！");
			messageMap.put("outPath", "");
			return JSON.toJSONString(messageMap);
		}else{
			if(StringUtils.isBlank(quesType)){
				quesType = "";
			}
			if(StringUtils.isBlank(quesLevel)){
				quesLevel = "";
			}
			String typeLevel = getNewQuesLevel(quesType,false) ;//+ getNewQuesLevel(quesLevel,false);
			if(StringUtils.isBlank(typeLevel)){
				typeLevel = "所有题型和难度";
			}
			if(versionQuestion!=null && versionQuestion.getCourseKnowledge()!=null && StringUtils.isNoneBlank(versionQuestion.getCourseKnowledge().getId())){
				knowledgeId = versionQuestion.getCourseKnowledge().getId();
			}else{
				knowledgeId = "1";
			}
			System.err.println("knowledgeId"+knowledgeId);
			if("1".equals(knowledgeId)){
				versionQuestion.setQuestionlibId(courseQuestionlibId);
				versionQuestion.setDelFlag("0");
				System.err.println("versionQuestion"+versionQuestion);
				versionQuestionList = versionQuestionService.findByQuestionLibIdAndQuesTypeAndQuesLevel(versionQuestion);
				//docTitle = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getTitle() +"中"+getDelFlagStr(delFlag)+"的试题("+ typeLevel +")";
				
				docTitle = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getTitle() +"-"+ typeLevel +"";
			}else{
				knowledgeId = knowledgeId.substring(knowledgeId.lastIndexOf(',')+1);
//				docTitle = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getTitle() + "中"+getDelFlagStr(delFlag)+"的涉及知识点\""+ courseKnowledgeService.get(knowledgeId).getTitle() +"\"的试题("+ typeLevel +")";
				docTitle = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getTitle() + "-"+courseKnowledgeService.get(knowledgeId).getTitle() +"-"+ typeLevel +"";
				CourseKnowledge courseKnowledge = new CourseKnowledge();
				courseKnowledge.setParentIds(knowledgeId);
				knowledgeIds = courseKnowledgeService.findIdListByParentIds(courseKnowledge);
				for (String id : knowledgeIds) {
					VersionQuestion vq = new VersionQuestion();
					vq.setCourseKnowledge(new CourseKnowledge(id));
					List<String> versionQuestionIds  = versionQuestionService.findQuestionIdList(vq);
					for (String versionQuestionId : versionQuestionIds) {
						VersionQuestion vqk = versionQuestionService.get(versionQuestionId);
						if(vqk!=null){
							if(StringUtils.isNotBlank(quesType) && !quesType.equals(vqk.getQuesType())){
								continue;
							}
							if(StringUtils.isNotBlank(quesLevel) && !quesLevel.equals(vqk.getQuesLevel())){
								continue;
							}
							if(!delFlag.equals(vqk.getDelFlag())){
								continue;
							}
							versionQuestionList.add(vqk);
						}
					}
				}
			}
			//2.导出word
				//2.1获取模板文档
					//模板文档exportQuestionsWord_temp.docx的路径
			String srcPath = request.getSession().getServletContext().getRealPath("/static/template/exportQuestionsWord_newtemp.docx");
			outPath = request.getSession().getServletContext().getRealPath("/static/template/loadFile/")+"\\"+UserUtils.getUser().getId();
			 
			File dirFile = new File(outPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			System.err.println("srcPath"+srcPath);
				//2.2获取表头信息headerMap
			Map<String,String> headerMap = Maps.newHashMap();
			headerMap.put("key0", "知识点");
			headerMap.put("key1", "知识点编码");
			headerMap.put("key2", "题型");
			headerMap.put("key3", "难度");
			headerMap.put("key4", "试题序号");
			headerMap.put("key5", "试题编码");
			headerMap.put("key6", "分值");
			headerMap.put("key7", "题干");
			headerMap.put("key8", "A内容");
			headerMap.put("key9", "B内容");
			headerMap.put("key10", "C内容");
			headerMap.put("key11", "D内容");
			headerMap.put("key12", "答案");
			headerMap.put("key13", "试题讲解");
			headerMap.put("key14", "命题人");
			headerMap.put("key15", "审题人");
				//2.3将待导出的试题集合versionQuestionList处理后转换为List<Map>集合
			List<Map<String,String>> mapList = Lists.newArrayList();
			mapList.add(headerMap);
			int length = versionQuestionList.size();
			System.err.println("versionQuestionList"+versionQuestionList.toString());
			for (int i = 0; i < length; i++) {
				VersionQuestion vq = versionQuestionList.get(i);
				List<String> knowledgeids = versionQuestionService.findKnowledgeIdList(vq);
				if(knowledgeids.size()<1){
					continue;
				}
				String knowledgeCode = "";
				StringBuffer questionCode = new StringBuffer();
				for (String id : knowledgeids) {
					knowledgeCode = knowledgeCode + courseKnowledgeService.get(id).getKnowledgeCode()+",";
				}
				knowledgeCode = knowledgeCode.substring(0, knowledgeCode.lastIndexOf(','));
				String getQuesType = vq.getQuesType();
				Map<String,String> map = Maps.newHashMap();
				map.put("key0", vq.getExamCode());
				map.put("key1", knowledgeCode);
				map.put("key2", getNewQuesType(getQuesType, true));
				map.put("key3", getNewQuesLevel(vq.getQuesLevel(), true));
				map.put("key4", ""+i);
				if(vq.getQuestionCode()==null){
					questionCode.append(knowledgeCode);
					questionCode.append(getNewQuesType(getQuesType, true));
					questionCode.append(getNewQuesLevel(vq.getQuesLevel(), true));
					questionCode.append(i+"");
				}
				map.put("key5", vq.getQuestionCode()==null?questionCode.toString():vq.getQuestionCode());
				map.put("key6", vq.getQuesPoint()==null?"":vq.getQuesPoint());
				map.put("key7", vq.getTitle()==null?"":vq.getTitle());
				if("1".equals(getQuesType) || "5".equals(getQuesType)){
					map.put("key8", vq.getChoice0()==null?"":vq.getChoice0());
					map.put("key9", vq.getChoice1()==null?"":vq.getChoice1());
					map.put("key10", vq.getChoice2()==null?"":vq.getChoice2());
					map.put("key11", vq.getChoice3()==null?"":vq.getChoice3());
					map.put("key12", vq.getAnswer0()==null?"":vq.getAnswer0());
				}else if("2".equals(getQuesType)){
					map.put("key8", vq.getAnswer0()==null?"":vq.getAnswer0());
					map.put("key9", vq.getAnswer1()==null?"":vq.getAnswer1());
					map.put("key10", vq.getAnswer2()==null?"":vq.getAnswer2());
					map.put("key11", vq.getAnswer3()==null?"":vq.getAnswer3());
					map.put("key12", "");
				}else if("11".equals(getQuesType)){
					map.put("key8", vq.getChoice0()==null?"":vq.getChoice0());
					map.put("key9", vq.getChoice1()==null?"":vq.getChoice1());
					map.put("key10", "");
					map.put("key11", "");
					map.put("key12", vq.getAnswer0()==null?"":vq.getAnswer0());
				}else{
					map.put("key8", vq.getAnswer0()==null?"":vq.getAnswer0());
					map.put("key9", "");
					map.put("key10", "");
					map.put("key11", "");
					map.put("key12", "");
				}
				map.put("key13", vq.getDescription()==null?"":vq.getDescription());
				map.put("key14", vq.getWriter()==null?"":vq.getWriter());
				map.put("key15", vq.getChecker()==null?"":vq.getChecker());
				mapList.add(map);
				success++;
			}
			
			//替换文本param
//			Map<String,String> param = Maps.newHashMap();
//			param.put("docTitle", docTitle);
//			param.put("dateTime", dateTime);
//			System.err.println(docTitle);
			//导出word表格
			System.err.println("srcPath2"+srcPath);
			String prefixPath = request.getSession().getServletContext().getRealPath("/");
			System.err.println("prefixPath" + prefixPath);
			XWPFDocument doc = ExportWord.replaceDoc(srcPath, headerMap, mapList,prefixPath);
			FileOutputStream out = null;
			docName =   docTitle+  ".docx";
			outPath = outPath +"\\"+docName;
			try {
				out	=new FileOutputStream(outPath);
				doc.write(out);
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				messageMap.put("message", "导出试题失败，请刷新页面后重试！");
				messageMap.put("docName", "");
				//return JSON.toJSONString(messageMap);
				   return  null;
			} catch (IOException e) {
				//e.printStackTrace();
				messageMap.put("message", "导出试题失败，请刷新页面后重试！");
				messageMap.put("docName", "");
				//return JSON.toJSONString(messageMap);
				   return  null;
			}finally{
				try {out.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
		
		
		File file = new File(outPath);
		// 取得文件名。
		String filename = file.getName();
		// 取得文件的后缀名。
		String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

		// 以流的形式下载文件。
		InputStream fis;
		try {
			fis = new BufferedInputStream(new FileInputStream(outPath));
			
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			
			filename =java.net.URLEncoder.encode(filename,"UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	 
	   return  null;
		
		
		
//		messageMap.put("success", ""+success);
//		messageMap.put("message", "导出成功，请保存！");
//		messageMap.put("docName", docName);
//		return JSON.toJSONString(messageMap);
	
	}
	
	private String getDelFlagStr(String delFlag){
		if("0".equals(delFlag)){
			return "审核通过";
		}else if("1".equals(delFlag)){
			return "已删除";
		}else if("2".equals(delFlag)){
			return "未审核";
		}else{
			return "";
		}
	}
	@RequiresPermissions("school.web:schoolversionQuestion:download")
	@RequestMapping(value = "importQuestionWordForm")
	public String importQuestionWordForm(VersionQuestion versionQuestion,@RequestParam(required=false) String courseQuestionlibId, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("versionQuestion", versionQuestion);
		model.addAttribute("courseQuestionlibId", courseQuestionlibId);
		return "modules/school/schoolversionQuestionImportFormNew";
	}
	
	
	@RequestMapping(value = "importQuestionFileNew")
	public String importQuestionFileNew(@RequestParam(required=false) String courseQuestionlibId, @RequestParam(value = "file", required = false) MultipartFile wordFile, HttpServletRequest request, HttpServletResponse response, Model model){
	
		boolean okFlag =false;
		if(StringUtils.isBlank(courseQuestionlibId)){
			return "";
		} 
		
		String id=UUID.randomUUID().toString().replace("-","");
		String companyId =UserUtils.getUser().getCompany().getId();
		String userId =UserUtils.getUser().getId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String questionPath = request.getSession().getServletContext().getRealPath("\\questionFile\\");
		System.err.println(questionPath);
		if(null==wordFile){
			wordFile = multipartRequest.getFile("wordFile");
		} 
		String wordFileName = wordFile.getOriginalFilename();
		System.err.println(wordFileName);
		String writer =multipartRequest.getParameter("writer");
		String schoolId =multipartRequest.getParameter("school.id");
	 
		String questionFileName = wordFile.getOriginalFilename();
		String suffix =questionFileName.substring(questionFileName.indexOf("."));
		System.err.println(questionFileName);
		 
		String newQuestionFileName = companyId +"\\"+userId+"\\" + new SimpleDateFormat("yyyy-MM").format(new Date()) +"\\"+id +  suffix;  //保存文件
		
		String imgPath ="\\questionFile\\" + companyId +"\\"+userId+"\\" + new SimpleDateFormat("yyyy-MM").format(new Date());  //图片文件夹路径
		 String finalPath = questionPath+"\\"+newQuestionFileName;
		finalPath = finalPath.replaceAll("\\*", "/");
		System.err.println(newQuestionFileName);
		System.err.println(finalPath);
		File targetQuestionFile = new File(questionPath,newQuestionFileName);
		if(!targetQuestionFile.exists()){
			targetQuestionFile.mkdirs();
		}
			//保存
		try{
			wordFile.transferTo(targetQuestionFile);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		 //找到专业
		 Specialty  specialty =null;
		 CourseQuestionlib  courseQuestionlib =courseQuestionlibService.get(courseQuestionlibId);
		 if (courseQuestionlib!=null) {
			 
			 CourseVesion vesion =courseVesionService.get(courseQuestionlib.getVersionId());
			 if(vesion!=null)
			 {
				 Course course = courseService.get(vesion.getCourseId());
				 if(course!=null)
				 {
					 specialty =specialtyService.get(course.getSpecialtyId());
				 }
			 }  
		}
		
		 QuestionlibImport questionlibImport =new QuestionlibImport();
		 questionlibImport.setIsNewRecord(true);
		 questionlibImport.setId(id);
		 questionlibImport.setQuestionlibId(courseQuestionlibId);
		 questionlibImport.setSpecialtyId(specialty==null?"":specialty.getId());
		 questionlibImport.setUser(UserUtils.getUser());
		 questionlibImport.setCreateBy(UserUtils.getUser());
		 questionlibImport.setUpdateBy(UserUtils.getUser());
		 questionlibImport.setOffice(UserUtils.getUser().getCompany());
		 questionlibImport.setSchool(officeService.get(schoolId));
		 questionlibImport.setCreateDate(new Date());
		 questionlibImport.setUpdateDate(new Date());
		 questionlibImport.setTitle(questionFileName);
		 questionlibImport.setWriter(writer);
		 questionlibImport.setDocName(questionFileName);
		 questionlibImport.setFilePath("\\questionFile\\"+newQuestionFileName);
		 questionlibImport.setDelFlag("0");
		 questionlibImportService.save(questionlibImport);
		//解析文档
		
		try {
			//调用word解析，
			
			// String[] cmds = {"cmd.exe","/C"," F:\\新媒科创\\在线教育\\doc\\插件\\word.exe","$"+finalPath+"$"};
//			 String exePath =  questionPath+"\\word.exe" ;
//			 String[] cmds = {"cmd.exe","/C", exePath," runAs /user:administrator  ","$"+finalPath+"$"};
			 String exePath =  questionPath+"\\word2html.exe" ;
			 String[] cmds = {"cmd.exe","/C", exePath,finalPath};
			 Runtime.getRuntime().exec(cmds);
			  
			  
			  //监听 文件是否生成
			  String txt =newQuestionFileName.replace(".docx",".doc").replace(".doc", ".txt") ;
			 targetQuestionFile = new File(questionPath,txt);
			for (int i = 0; i < 20; i++) {
				Thread.sleep(5000);
				if(targetQuestionFile.exists()){
					Thread.sleep(2000);
					okFlag =true;
					break;
				}
				okFlag =false;
			}
			if (okFlag) {
				//解析完成，读取解析文件，存入数据库
				// okFlag=	importQuestions(id,finalPath.replace(".docx", ".txt").replace(".doc", ".txt"),model ,imgPath );
				
				okFlag=	importQuestionsNew(id,finalPath.replace(".docx", ".txt").replace(".doc", ".txt"),model ,imgPath );
			}
			  
			   
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			okFlag =false;
		}
		
		model.addAttribute("reloadParent", okFlag);
		
		return "modules/school/schoolversionQuestionImportFormNew";
		 
	}
	  boolean importQuestionsNew(String importDataId,  String filePath,Model model,String imgPath){
			 
		   try {
				String questionJsonData  = FileUtils.readerTXT(filePath); //读取txt文件，
				questionJsonData = questionJsonData.replace("&lt;o:p&gt;", "");	//&lt;o:p&gt;  &lt;/o:p&gt;
				questionJsonData = questionJsonData.replace("&lt;/o:p&gt;", "");
				questionJsonData = questionJsonData.replaceAll(" ", " ");	//中文空格 替换为 英文空格
				Questions questions =  JSON.parseObject(questionJsonData, Questions.class);  //json 转成对象
				
				QuestionlibImport questionlibImport = questionlibImportService.get(importDataId);
				String courseQuestionlibId = questionlibImport.getQuestionlibId();
				String versionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
				
				List<VersionQuestion> list = Lists.newArrayList();	 
				
				List<Cell> cellList= questions.getIrcontent();
				
				Qtable qtable=questions.getIrtables().getTable().get(0);
				int columncount=qtable.getColumncount();  //取得列数
				int rowcount =qtable.getRowcount(); //行数
			    int recordCount = 0;
               int saveSuccess = 0;
               int saveFail = 0;
			    int count=0;
			    int tatalCount =cellList.size();
			    int startIndex =15; //第二行开始
			    List<String> knowledgeIds = Lists.newArrayList();
			    StringBuffer sb = new StringBuffer("试题序号分别是：");
			    for (int i = 1; i < rowcount; i++) {
			    	
			    	  if(startIndex>=tatalCount)
			    	  {
			    		 break;
			    	  }
			    	try {

				    	VersionQuestion vq =new VersionQuestion();
				    	knowledgeIds.clear();
				    	count =1;
				    	
				    	vq.setExamCode( HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+1).getContent().replaceAll(" ", " ").trim())));
				    	
				    	String knowledgeCode = HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+2).getContent().replaceAll(" ", " ").trim()));
				    	
				    	
	                	String[] knowledgeCodes = knowledgeCode.split(",");
	                	
	                	for (String kc : knowledgeCodes) {
	                		CourseKnowledge courseKnowledge = new CourseKnowledge();
	                		courseKnowledge.setKnowledgeCode(kc.trim());
	                		courseKnowledge.setVersionId(versionId);
	                		CourseKnowledge ck = courseKnowledgeService.getByKnowledgeCode(courseKnowledge);
	                		if(ck==null || ck.getId()==null){
	                			continue;
	                		}else{
	                			knowledgeIds.add(ck.getId());   //属于多个知识点
	                		}
	                	}
	                	
	                	if(knowledgeIds.size()<1){
	                		try {
	                			String questionIndex = HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+5).getContent().replaceAll(" ", " ").trim())) ; 
	                			if (saveFail%10==0) {
		                    		sb.append(" <br/>");
								}
		                		sb.append(questionIndex+",");
		                		saveFail++;
		                		 
							} catch (Exception e2) {
								e2.printStackTrace();
							}
	                		
	                		startIndex+=16;
	                    	continue;
	                	}
	                	
	                	String quesType=HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+3).getContent().replaceAll(" ", " ").trim()));
	                	vq.setQuesType(setNewQuesType(quesType));
	                	vq.setQuesLevel(setNewQuesLevel(HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+4).getContent().replaceAll(" ", " ").trim()))));
	                	
	                	vq.setQuestionCode(HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+6).getContent().replaceAll(" ", " ").trim())));
	                	vq.setQuesPoint(HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+7).getContent().replaceAll(" ", " ").trim())));
	                	vq.setTitle(Encodes.unescapeHtml(cellList.get(startIndex+8).getContent().replaceAll(" ", " ").trim()));
	                	String  choice0 =Encodes.unescapeHtml(cellList.get(startIndex+9).getContent().replaceAll(" ", " ").trim());
	                	if (choice0.indexOf("A.")==0||choice0.indexOf("A、")==0) {
	                		choice0=choice0.substring(2);
						}
	                	vq.setChoice0(choice0);
	                	
	                	String  choice1 = Encodes.unescapeHtml(cellList.get(startIndex+10).getContent().replaceAll(" ", " ").trim());
	                	if (choice1.indexOf("B.")==0||choice1.indexOf("B、")==0) {
	                		choice1=choice1.substring(2);
						}
	                	vq.setChoice1(choice1);
	                	
	                	
	                	String  choice2 =Encodes.unescapeHtml(cellList.get(startIndex+11).getContent().replaceAll(" ", " ").trim());
	                	if (choice2.indexOf("C.")==0||choice2.indexOf("C、")==0) {
	                		choice2=choice2.substring(2);
						}
	                	vq.setChoice2(choice2);
	                	
	                	String  choice3=Encodes.unescapeHtml(cellList.get(startIndex+12).getContent().replaceAll(" ", " ").trim());
	                	if (choice3.indexOf("D.")==0||choice3.indexOf("D、")==0) {
	                		choice3=choice3.substring(2);
						}
	                	vq.setChoice3(choice3);
	                	vq.setAnswer0(Encodes.unescapeHtml(cellList.get(startIndex+13).getContent().replaceAll(" ", " ").trim()));
	                	vq.setDescription(Encodes.unescapeHtml(cellList.get(startIndex+14).getContent().replaceAll(" ", " ").trim()));
	                	vq.setWriter(HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+15).getContent().replaceAll(" ", " ").trim())));
	                	vq.setChecker(HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+16).getContent().replaceAll(" ", " ").trim())));
	                	
	                	if(quesType.equals("P"))
	                	{
	                		count =2;
	                	}
	                	
	                	if(quesType.equals("T"))
	                	{
	                		if(StringUtils.isNotBlank(vq.getChoice1()))
	                		 count++;
	                		if(StringUtils.isNotBlank(vq.getChoice2()))
	                   		 count++;
	                		if(StringUtils.isNotBlank(vq.getChoice3()))
	                   		 count++;
	                		//填空要把答案放到anser中
	                		vq.setAnswer0(vq.getChoice0());
	                		vq.setChoice0("");
	                		
	                		vq.setAnswer1(vq.getChoice1());
	                		vq.setChoice1("");
	                		
	                		vq.setAnswer2(vq.getChoice2());
	                		vq.setChoice2("");
	                		
	                		vq.setAnswer3(vq.getChoice3());
	                		vq.setChoice3("");
	                		
	                	}
	                	
	                	if("J".equals(quesType) || "E".equals(quesType) || "M".equals(quesType) || "L".equals(quesType) || "H".equals(quesType) || "B".equals(quesType) || "G".equals(quesType)){
                    		//6.获取答案   并赋值
	                		vq.setAnswer0(vq.getChoice0());
	                		vq.setChoice0("");
                    		count = 1;
                    	}
	                	if(quesType.equals("S")||quesType.equals("D"))
	                	{
	                		 count=0;
	                		   if(StringUtils.isNotBlank(vq.getChoice0()))
		                		 count++;
	                		   if(StringUtils.isNotBlank(vq.getChoice1()))
		                		 count++;
		                		if(StringUtils.isNotBlank(vq.getChoice2()))
		                   		 count++;
		                		if(StringUtils.isNotBlank(vq.getChoice3()))
		                   		 count++;
	                	}
	                	
	                	vq.setCount(count+"");
	                	
	                	vq.setQuestionlibId(courseQuestionlibId);
	                	vq.setVersionId(versionId);
	                	vq.setDelFlag("0");
	                	vq.setOffice(UserUtils.getUser().getCompany());
	                	
	                	
	                	//验证信息完整性
	                	try {
	                		
	                		if (StringUtils.isBlank(vq.getTitle())||StringUtils.isBlank(vq.getQuesPoint())||StringUtils.isBlank(vq.getAnswer0())||StringUtils.isBlank(vq.getQuesType())||StringUtils.isBlank(vq.getQuestionCode())) {
	                			saveFail++;
		                		String questionIndex = HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+5).getContent().replaceAll(" ", " ").trim())) ; 
		                		if (saveFail%10==0) {
		                    		sb.append(" <br/>");
								}
		                    	sb.append(questionIndex+",");
		                    	
		                    	startIndex+=16;
		                    	continue;
							}
	                		
						} catch (Exception e) {
							startIndex+=16;
	                    	continue;
						}
	                	
	                	try {
	                		versionQuestionService.save(vq);
	                		list.add(vq);
						} catch (Exception e) {
							saveFail++;
	                		String questionIndex = HTMLTagDel.delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+5).getContent().replaceAll(" ", " ").trim())) ; 
	                		if (saveFail%10==0) {
	                    		sb.append(" <br/>");
							}
	                		sb.append(questionIndex+",");
	                		startIndex+=16;
	                    	continue;
						}
	                	
	                 
	                	//保存知识点和试题的对应关系
	                	for (String kid : knowledgeIds) {
	                		if(StringUtils.isBlank(kid)){
	                			continue;
	                		}
	                		vq.setCourseKnowledge(new CourseKnowledge(kid));
	                		versionQuestionService.saveKnowledgeIdAndQuestionId(vq);
	                	}
	                	//保存试题和导入文档的对应关系
	                	vq.setImportId(importDataId);
	                	versionQuestionService.saveImportIdAndQuestionId(vq);
	                	saveSuccess++;
	                	
	                	
					} catch (Exception e) {
//						saveFail++;
//               		String questionIndex = delHTMLTag(Encodes.unescapeHtml(cellList.get(startIndex+5).getContent().replaceAll(" ", " ").trim())) ; 
//                   	sb.append(questionIndex+",");
						
                   	continue;
                   	
					}
			    	
			    	startIndex+=16;
				}
				
			    
			    String failMessage = sb.toString();
               model.addAttribute("saveSuccess", saveSuccess+"");		// int saveSuccess = 0;  int saveFail = 0;
               model.addAttribute("saveFail", saveFail+"");
               model.addAttribute("message", "success");
               if(saveFail>0){
               	model.addAttribute("failMessage", failMessage.substring(0, failMessage.lastIndexOf(',')));
               }
               
               return true;
				
		} catch (Exception e) {
		   e.printStackTrace();
		}
		   model.addAttribute("message", "fail");
		   return false;
			
			 
		}
	
	 boolean importQuestions(String importDataId,  String filePath,Model model,String imgPath){
		 
		  //读取txt文件，
		String questionJsonData  = FileUtils.readerTXT(filePath);
		//String imgPath = filePath.substring(filePath.indexOf("questionFile"));
		
		//imgPath=imgPath.substring(0,imgPath.indexOf(new SimpleDateFormat("yyyy-MM").format(new Date())+7))  ;
				
		System.err.println("questionJsonData"+questionJsonData);
		 
		questionJsonData = questionJsonData.replace("&lt;o:p&gt;", "");	//&lt;o:p&gt;  &lt;/o:p&gt;
		questionJsonData = questionJsonData.replace("&lt;/o:p&gt;", "");
		questionJsonData = questionJsonData.replaceAll(" ", " ");	//中文空格 替换为 英文空格
		 
		QuestionlibImport questionlibImport = questionlibImportService.get(importDataId);
		System.err.println(questionlibImport.getId());
	//	String filePath = questionlibImport.getFilePath();
		String courseQuestionlibId = questionlibImport.getQuestionlibId();
		String versionId = QuestionlibTld.getCourseQuestionlibById(courseQuestionlibId).getVersionId();
		System.err.println(courseQuestionlibId);
		System.err.println(versionId);
		List<VersionQuestion> list = Lists.newArrayList();	//最终将json数据存入到list中，再保存
		Map<String,String> tableMap = Maps.newHashMap();	//存储试题保存信息，保存失败和成功的试题数量
		try{
			JSONObject obj = new JSONObject(questionJsonData); 
           String state = obj.getString("irState");
           if("0".equals(state)){
           	//读取表内容
               JSONArray contents = obj.getJSONArray("irContent");
               String urlDate = obj.getString("irUrlDate");
               //读取文档信息
               JSONObject tables = obj.getJSONObject("irTables");
               int tableCount = Integer.parseInt(tables.getString("count")); 
               //读取表信息
               JSONArray table = tables.getJSONArray("table");
               int recordCount = 0;
               int saveSuccess = 0;
               int saveFail = 0;
               StringBuffer sb = new StringBuffer("试题序号分别是：");
               for (int i = 0; i < tableCount; i++) {
                   JSONObject tb = table.getJSONObject(i);
                   int rowcount = Integer.parseInt(tb.getString("rowcount"));
                   int columncount = Integer.parseInt(tb.getString("columncount"));
//               	int rowcount = 20;
//               	int columncount = 13;
                   int beginIndex = recordCount;
                   int finalIndex = recordCount + rowcount*columncount;
                   List<Map<String,String>> dataList = Lists.newArrayList();
                   for (int j = beginIndex; j < finalIndex; j++) {
                       String name = contents.getJSONObject(j).getString("name");
                       JSONArray content = contents.getJSONObject(j).getJSONArray("content");
                       String columnContent = "";
                       for (int k = 0; k < content.length(); k++) {
                           String type = content.getJSONObject(k).getString("type");
                           if("1".equals(type)){	//文本
                               columnContent += Encodes.unescapeHtml(content.getJSONObject(k).getString("content")).replaceAll(" ", " ").trim();
                           }else if("2".equals(type)){	//图片保存路径
                           	String cContent = Encodes.unescapeHtml(content.getJSONObject(k).getString("content")).replaceAll(" ", " ").trim();
                           	System.err.println(cContent);
                           	 
                           	columnContent += "<img alt=\"\" src=\""+imgPath+ "\\" + cContent+"\" style=\"width: 300px;\"/>";
                           	System.err.println(columnContent);
                           }else if("3".equals(type)){	//表格保存格式
                           	//columnContent += "<img alt=\"\" src=\""+filePath + content.getJSONObject(k).getString("content").trim()+"\" style=\"width: 120px; height: 90px; float: left;\"/>";
                           	columnContent += Encodes.unescapeHtml(content.getJSONObject(k).getString("content")).replaceAll(" ", " ").trim();
                           }else{
                               
                           }
                       }
                       Map<String,String> map = Maps.newHashMap();
                       map.put("name", name);
                       map.put("content", columnContent);
                       dataList.add(map);
                   }
                   
                   //保存试题
                   /**
                    * columncount为15时，按旧模板解析，旧模板不保存“试题编号”，
                    * columncount为16时，按新模板解析，新模板保存“试题编号”。
                    */
                   if(columncount==15){
	                    /**
	                	 * 旧模板
	                	 * 获取单元格内容		name x-x-x
	                	 * x-x-1:试题序号，x-x-2:知识点代号编码，x-x-3:测点（知识点），x-x-4:难度，x-x-5:题型，x-x-6:分值，
	                	 * x-x-7:答案(选择题/判断题答案，其他题型不填)，x-x-8:试题描述(题干)，
	                	 * x-x-9:A内容(选择题A选项，填空题第一空答案，判断题选项1，其他题型答案)，
	                	 * x-x-10:B内容(选择题B选项，填空题第二空答案，判断题选项2，其他题型不填),
	                	 * x-x-11:C内容(选择题C选项，填空题第三空答案，其他题型不填),
	                	 * x-x-12:D内容(选择题D选项，填空题第四空答案，其他题型不填),
	                	 * x-x-13:试题讲解，x-x-14:命题人，x-x-15:审题人
	                	 * 综上，所有题型均为15列
	                	 * 
	                	 */
	                    for (int j = beginIndex+columncount; j < finalIndex; j+=columncount) {
	                        VersionQuestion vq = new VersionQuestion();
	                        int count = 0;
	                        //1.获取知识点
	                        	//获取知识点编号，并反查知识点id
	                        String knowledgeCode = dataList.get(j+1).get("content").trim();
	                        String[] knowledgeCodes = knowledgeCode.split(",");
	                        List<String> knowledgeIds = Lists.newArrayList();
	                        for (String kc : knowledgeCodes) {
	                        	CourseKnowledge courseKnowledge = new CourseKnowledge();
	                        	courseKnowledge.setKnowledgeCode(kc.trim());
	                        	courseKnowledge.setVersionId(versionId);
	                            CourseKnowledge ck = courseKnowledgeService.getByKnowledgeCode(courseKnowledge);
	                            if(ck==null || ck.getId()==null){
	                            	continue;
	                            }else{
	                            	knowledgeIds.add(ck.getId());
	                            }
							}
	                        	//判断是否找到对应知识点，找到则继续下去，未找到则中止该试题的保存
	                        if(knowledgeIds.size()<1){
	                        	saveFail++;
	                        	String questionIndex = dataList.get(j).get("content").trim();
	                        	sb.append(questionIndex+",");
	                        	continue;
	                        }
	                        //知识点赋值
	                        String examCode = dataList.get(j+2).get("content").trim();
	                        vq.setExamCode(examCode);
	                        //2.获取题型   并赋值
	                        String quesType = dataList.get(j+4).get("content").trim();
	                        vq.setQuesType(setNewQuesType(quesType));
	                        //3.获取难度   并赋值
	                        String quesLevel = dataList.get(j+3).get("content").trim();
	                        vq.setQuesLevel(setNewQuesLevel(quesLevel));
	                        //4.获取分值   并赋值
	                        String quesPoint = dataList.get(j+5).get("content").trim();
	                        vq.setQuesPoint(quesPoint);
	                        //5.获取试题描述(即题干) 并赋值
	                        String title = dataList.get(j+7).get("content").trim();
	                        vq.setTitle(title);
	                        //单/多选择题	1/5
	                        if("S".equals(quesType) || "D".equals(quesType)){
	                            //6.获取4个选项   并赋值
	                          
	                        	String choice0 = dataList.get(j+8).get("content").trim().replace("A.", "");  //A
	                    		String choice1 = dataList.get(j+9).get("content").trim().replace("B.", "");  //B
	                    		String choice2 = dataList.get(j+10).get("content").trim().replace("C.", "");  //C
	                    		String choice3 = dataList.get(j+11).get("content").trim().replace("D.", "");  //D
	                            vq.setChoice0(choice0);
	                            vq.setChoice1(choice1);
	                            vq.setChoice2(choice2);
	                            vq.setChoice3(choice3);
	                            count = 4;
	                            //7.获取答案   并赋值
	                            String answer0 = dataList.get(j+6).get("content").trim();
	                            vq.setAnswer0(answer0);
	                            //8.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //9.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //10.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                        }//填空题	2
	                        else if("T".equalsIgnoreCase(quesType)){
	                            //6.获取4个答案   并赋值
	                            String answer0 = dataList.get(j+8).get("content").trim();  //空1
	                            String answer1 = dataList.get(j+9).get("content").trim();  //空2
	                            String answer2 = dataList.get(j+10).get("content").trim();  //空3
	                            String answer3 = dataList.get(j+11).get("content").trim();  //空4
	                            if(StringUtils.isNotBlank(answer0)){
	                            	vq.setChoice0(answer0);
	                            	count++;
	                            }
	                            if(StringUtils.isNotBlank(answer0)){
	                            	vq.setChoice1(answer1);
	                            	count++;
	                            }
	                            if(StringUtils.isNotBlank(answer0)){
	                            	vq.setChoice2(answer2);
	                            	count++;
	                            }
	                            if(StringUtils.isNotBlank(answer0)){
	                            	vq.setChoice3(answer3);
	                            	count++;
	                            }
	                            //7.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //8.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //9.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                        }
	                        //计算题/简单题/概念题/综合题/作图题/制表题/识图题/判断题		3/4/6/7/8/9/10/11
	                        else if("J".equals(quesType) || "E".equals(quesType) || "M".equals(quesType) || "L".equals(quesType) || "H".equals(quesType) || "B".equals(quesType) || "G".equals(quesType)){
	                            //6.获取答案   并赋值
	                            String answer0 = dataList.get(j+8).get("content").trim();
	                            vq.setAnswer0(answer0);
	                            //7.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //8.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //9.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                            count = 1;
	                        }
	                        else if("P".equals(quesType)){
	                        	//6.获取答案   并赋值
	                            String answer0 = dataList.get(j+6).get("content").trim();
	                            vq.setAnswer0(answer0);
	                            //7.获取2个选项   并赋值
	                            String choice0 = dataList.get(j+8).get("content").trim();  //A
	                            String choice1 = dataList.get(j+9).get("content").trim();  //B
	                            vq.setChoice0(choice0);
	                            vq.setChoice1(choice1);
	                            //8.获取讲解   并赋值
	                            String description = dataList.get(j+12).get("content").trim();
	                            vq.setDescription(description);
	                            //9.获取命题人  并赋值
	                            String writer = dataList.get(j+13).get("content").trim();
	                            vq.setWriter(writer);
	                            //10.获取审题人   并赋值
	                            String checker = dataList.get(j+14).get("content").trim();
	                            vq.setChecker(checker);
	                            count = 2;
	                        }
	                        vq.setQuestionlibId(courseQuestionlibId);
	                        vq.setVersionId(versionId);
	                        vq.setCount(count+"");
	                        vq.setDelFlag("0");
	                        vq.setOffice(UserUtils.getUser().getCompany());
	                        list.add(vq);
	                        //保存试题
	                        versionQuestionService.save(vq);
	                        System.err.println(knowledgeIds);
	                        //保存知识点和试题的对应关系
	                        for (String kid : knowledgeIds) {
	                            if(StringUtils.isBlank(kid)){
	                                continue;
	                            }
	                            vq.setCourseKnowledge(new CourseKnowledge(kid));
	                            versionQuestionService.saveKnowledgeIdAndQuestionId(vq);
	                        }
	                        //保存试题和导入文档的对应关系
	                        vq.setImportId(importDataId);
	                        versionQuestionService.saveImportIdAndQuestionId(vq);
	                        saveSuccess++;
	                    }
                   }else if(columncount==16){
	                    /**
	                	 * 新模板
	                	 * 获取单元格内容		name x-x-x
	                	 * x-x-1:测点（知识点），x-x-2:知识点编码，x-x-3:题型，x-x-4:难度，x-x-5:试题序号，x-x-6:试题编码，
	                	 * x-x-7:分值，x-x-8:试题描述(题干)，
	                	 * x-x-9:A内容(选择题A选项，填空题第一空答案，判断题选项1，其他题型答案)，
	                	 * x-x-10:B内容(选择题B选项，填空题第二空答案，判断题选项2，其他题型不填),
	                	 * x-x-11:C内容(选择题C选项，填空题第三空答案，其他题型不填),
	                	 * x-x-12:D内容(选择题D选项，填空题第四空答案，其他题型不填),
	                	 * x-x-13:答案(选择题/判断题答案，其他题型不填)，
	                	 * x-x-14:试题讲解，x-x-15:命题人，x-x-16:审题人
	                	 * 综上，所有题型均为16列
	                	 * 
	                	 */
	                    for (int j = beginIndex+columncount; j < finalIndex; j+=columncount) {
	                    	VersionQuestion vq = new VersionQuestion();
	                    	int count = 0;
	                    	//获取试题编码
	                    	String questionCode = dataList.get(j+5).get("content").trim();
	                    	vq.setQuestionCode(questionCode);
	                    	//1.获取知识点
	                    	//获取知识点编号，并反查知识点id
	                    	String knowledgeCode = dataList.get(j+1).get("content").trim();
	                    	String[] knowledgeCodes = knowledgeCode.split(",");
	                    	List<String> knowledgeIds = Lists.newArrayList();
	                    	for (String kc : knowledgeCodes) {
	                    		CourseKnowledge courseKnowledge = new CourseKnowledge();
	                    		courseKnowledge.setKnowledgeCode(kc.trim());
	                    		courseKnowledge.setVersionId(versionId);
	                    		CourseKnowledge ck = courseKnowledgeService.getByKnowledgeCode(courseKnowledge);
	                    		if(ck==null || ck.getId()==null){
	                    			continue;
	                    		}else{
	                    			knowledgeIds.add(ck.getId());
	                    		}
	                    	}
	                    	//判断是否找到对应知识点，找到则继续下去，未找到则中止该试题的保存
	                    	if(knowledgeIds.size()<1){
	                    		saveFail++;
	                    		String questionIndex = dataList.get(j+4).get("content").trim();
	                        	sb.append(questionIndex+",");
	                    		continue;
	                    	}
	                    	//知识点赋值
	                    	String examCode = dataList.get(j).get("content").trim();
	                    	vq.setExamCode(examCode);
	                    	//2.获取题型   并赋值
	                    	String quesType = dataList.get(j+2).get("content").trim();
	                    	vq.setQuesType(setNewQuesType(quesType));
	                    	//3.获取难度   并赋值
	                    	String quesLevel = dataList.get(j+3).get("content").trim();
	                    	vq.setQuesLevel(setNewQuesLevel(quesLevel));
	                    	//4.获取分值   并赋值
	                    	String quesPoint = dataList.get(j+6).get("content").trim();
	                    	vq.setQuesPoint(quesPoint);
	                    	//5.获取试题描述(即题干) 并赋值
	                    	String title = dataList.get(j+7).get("content").trim();
	                    	vq.setTitle(title);
	                    	//单/多选择题	1/5
	                    	if("S".equals(quesType) || "D".equals(quesType)){
	                    		//6.获取4个选项   并赋值
	                    		String choice0 = dataList.get(j+8).get("content").trim().replace("A.", "");  //A
	                    		String choice1 = dataList.get(j+9).get("content").trim().replace("B.", "");  //B
	                    		String choice2 = dataList.get(j+10).get("content").trim().replace("C.", "");  //C
	                    		String choice3 = dataList.get(j+11).get("content").trim().replace("D.", "");  //D
	                    		vq.setChoice0(choice0);
	                    		vq.setChoice1(choice1);
	                    		vq.setChoice2(choice2);
	                    		vq.setChoice3(choice3);
	                    		count = 4;
	                    		//7.获取答案   并赋值
	                    		String answer0 = dataList.get(j+12).get("content").trim();
	                    		vq.setAnswer0(answer0);
	                    		//8.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//9.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//10.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    	}//填空题	2
	                    	else if("T".equalsIgnoreCase(quesType)){
	                    		//6.获取4个答案   并赋值
	                    		String answer0 = dataList.get(j+8).get("content").trim();  //空1
	                    		String answer1 = dataList.get(j+9).get("content").trim();  //空2
	                    		String answer2 = dataList.get(j+10).get("content").trim();  //空3
	                    		String answer3 = dataList.get(j+11).get("content").trim();  //空4
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice0(answer0);
	                    			count++;
	                    		}
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice1(answer1);
	                    			count++;
	                    		}
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice2(answer2);
	                    			count++;
	                    		}
	                    		if(StringUtils.isNotBlank(answer0)){
	                    			vq.setChoice3(answer3);
	                    			count++;
	                    		}
	                    		//7.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//8.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//9.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    	}
	                    	//计算题/简单题/概念题/综合题/作图题/制表题/识图题/判断题		3/4/6/7/8/9/10/11
	                    	else if("J".equals(quesType) || "E".equals(quesType) || "M".equals(quesType) || "L".equals(quesType) || "H".equals(quesType) || "B".equals(quesType) || "G".equals(quesType)){
	                    		//6.获取答案   并赋值
	                    		String answer0 = dataList.get(j+8).get("content").trim();
	                    		vq.setAnswer0(answer0);
	                    		//7.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//8.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//9.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    		count = 1;
	                    	}
	                    	else if("P".equals(quesType)){
	                    		//6.获取答案   并赋值
	                    		String answer0 = dataList.get(j+12).get("content").trim();
	                    		vq.setAnswer0(answer0);
	                    		//7.获取2个选项   并赋值
	                    		String choice0 = dataList.get(j+8).get("content").trim();  //A
	                    		String choice1 = dataList.get(j+9).get("content").trim();  //B
	                    		vq.setChoice0(choice0);
	                    		vq.setChoice1(choice1);
	                    		//8.获取讲解   并赋值
	                    		String description = dataList.get(j+13).get("content").trim();
	                    		vq.setDescription(description);
	                    		//9.获取命题人  并赋值
	                    		String writer = dataList.get(j+14).get("content").trim();
	                    		vq.setWriter(writer);
	                    		//10.获取审题人   并赋值
	                    		String checker = dataList.get(j+15).get("content").trim();
	                    		vq.setChecker(checker);
	                    		count = 2;
	                    	}
	                    	vq.setQuestionlibId(courseQuestionlibId);
	                    	vq.setVersionId(versionId);
	                    	vq.setCount(count+"");
	                    	vq.setDelFlag("0");
	                    	vq.setOffice(UserUtils.getUser().getCompany());
	                    	list.add(vq);
	                    	//保存试题
	                    	versionQuestionService.save(vq);
	                    	System.err.println(knowledgeIds);
	                    	//保存知识点和试题的对应关系
	                    	for (String kid : knowledgeIds) {
	                    		if(StringUtils.isBlank(kid)){
	                    			continue;
	                    		}
	                    		vq.setCourseKnowledge(new CourseKnowledge(kid));
	                    		versionQuestionService.saveKnowledgeIdAndQuestionId(vq);
	                    	}
	                    	//保存试题和导入文档的对应关系
	                    	vq.setImportId(importDataId);
	                    	versionQuestionService.saveImportIdAndQuestionId(vq);
	                    	saveSuccess++;
	                    }
                   }
                   recordCount = finalIndex;
               }
               String failMessage = sb.toString();
               model.addAttribute("saveSuccess", saveSuccess+"");		// int saveSuccess = 0;  int saveFail = 0;
               model.addAttribute("saveFail", saveFail+"");
               model.addAttribute("message", "success");
               if(saveFail>0){
               	model.addAttribute("failMessage", failMessage.substring(0, failMessage.lastIndexOf(',')));
               }
               System.err.println(JSON.toJSON(tableMap).toString());
               return true;
//               response.getWriter().write(JSON.toJSON(tableMap).toString());
           }else{
           	model.addAttribute("message", "fail");
           	System.err.println(JSON.toJSON(tableMap).toString());
//           	response.getWriter().write(JSON.toJSON(tableMap).toString());
           	return false;
           }
		}catch(Exception e){
			e.printStackTrace();
		} 
		model.addAttribute("message", "fail");
		System.err.println(JSON.toJSON(tableMap).toString());
//		try {
//			response.getWriter().write(JSON.toJSON(tableMap).toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return false;
	}
	
	@RequiresPermissions("school.web:schoolversionQuestion:download")
	@RequestMapping(value = "emportQuestionWordForm")
	public String emportQuestionWordForm(VersionQuestion versionQuestion,@RequestParam(required=false) String courseQuestionlibId, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("versionQuestion", versionQuestion);
		model.addAttribute("courseQuestionlibId", courseQuestionlibId);
		return "modules/school/schoolversionQuestionEmportForm";
	}
	 
	
}