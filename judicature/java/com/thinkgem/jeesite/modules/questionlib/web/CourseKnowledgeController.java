/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import sun.swing.StringUIClientPropertyKey;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;

/**
 * 知识点Controller
 * @author webcat
 * @version 2016-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/courseKnowledge")
public class CourseKnowledgeController extends BaseController {

	@Autowired
	private CourseKnowledgeService courseKnowledgeService;
	
	@ModelAttribute
	public CourseKnowledge get(@RequestParam(required=false) String id) {
		CourseKnowledge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseKnowledgeService.get(id);
		}
		if (entity == null){
			entity = new CourseKnowledge();
		}
		return entity;
	}
	
	@RequiresPermissions("questionlib:courseKnowledge:view")
	@RequestMapping(value = {"index",""})
	public String index(CourseKnowledge courseKnowledge,@RequestParam(required=false) String courseVesionId, Model model) {
		/*String specialtyId  = "";
		String courseId = "";*/
		
		List<CourseVesion> courseVesionList	=null;
		
		/*if (!StringUtils.isBlank(courseVesionId)) {
			courseId = QuestionlibTld.getCourseVersionByVersionId(courseVesionId).getCourseId();
			specialtyId  =QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
			courseVesionList = QuestionlibTld.getCourseVersionByCourseId(courseId);
		}else{
			*/
			courseVesionList = QuestionlibTld.getCourseVersion() ;
			
			if (courseVesionList!=null&&courseVesionList.size()>0) {
				
				//courseVesionId= courseVesionList.get(0).getId() ;
				
				CourseVesion courseVersion=courseVesionList.get(0);
				courseKnowledge.setCourseVesionId(courseVersion.getId());
				courseKnowledge.setCourseVersionName(courseVersion.getTitle());
				
				courseKnowledge.setCourseId(courseVersion.getCourse().getId());
				courseKnowledge.setCourseName(courseVersion.getCourse().getTitle());
				
				courseKnowledge.setSpecialtyId(courseVersion.getSpecialty().getId());
				courseKnowledge.setSpecialtyName(courseVersion.getSpecialty().getTitle());
				
				
				//courseVersion.getCourseId();
				
			  /*  courseId =courseVesionList.get(0).getCourseId() ;
			    Course coures =	QuestionlibTld.getCourseByID(courseId);
			    specialtyId  =coures.getSpecialtyId();
			    courseVesionList = QuestionlibTld.getCourseVersionByCourseId(courseId);*/
			}
			/*}*/
		/* model.addAttribute("courseVesionList", courseVesionList);
		 model.addAttribute("specialtyId", specialtyId);
		 model.addAttribute("courseId", courseId);
		 model.addAttribute("courseVesionId", courseVesionId);*/
	     
		 model.addAttribute("courseKnowledge", courseKnowledge);
		 
		return "modules/questionlib/courseKnowledgeIndex";
	}
	
	@RequiresPermissions("questionlib:courseKnowledge:view")
	@RequestMapping(value = {"list", ""})
	public String list(CourseKnowledge courseKnowledge, @RequestParam(required=false) String extId, @RequestParam(required=false) String courseId, HttpServletRequest request, HttpServletResponse response, Model model) {
		//显示所有子节点
		if(StringUtils.isBlank(courseId)){
			if ( !StringUtils.isBlank(courseKnowledge.getCourseId()) ) {
				courseId =courseKnowledge.getCourseId();
			}else {
				 List<Course> couresList =	QuestionlibTld.getCourse();
				  if (couresList!=null&&couresList.size()>0) {
					  courseId =couresList.get(0).getId();
			     }
			}
		}
		List<CourseKnowledge> list = new ArrayList<CourseKnowledge>();
		CourseKnowledge csk = new CourseKnowledge();
		csk.setCourseId(courseId);
		//csk.setParentIds(extId);
		list = courseKnowledgeService.findList(csk);
		if(list.size()<1){
			model.addAttribute("message", courseKnowledge.getTitle()==null?"":courseKnowledge.getTitle() + "无子知识点或无相关知识点！");
		}
		
		courseKnowledge.setCourseId(courseId);
		model.addAttribute("courseKnowledge", courseKnowledge);
		model.addAttribute("courseId", courseId);
		model.addAttribute("extId",extId);
		model.addAttribute("list", list);
		System.err.println("list"+model.toString());
		return "modules/questionlib/courseKnowledgeList";
	}
	@RequiresPermissions("questionlib:courseKnowledge:view")
	@RequestMapping(value = "form")
	public String form(CourseKnowledge courseKnowledge, Model model, @RequestParam(required=false) String extId,@RequestParam(required=false) String courseId, HttpServletRequest request, HttpServletResponse response) {
		 
		if (StringUtils.isNotBlank(courseId)) {
			 courseKnowledge.setCourseId(courseId);
		}
		model.addAttribute("courseId", courseId);
		model.addAttribute("courseKnowledge", courseKnowledge);
		
		return "modules/questionlib/courseKnowledgeForm";
	}

	@RequiresPermissions("questionlib:courseKnowledge:edit")
	@RequestMapping(value = "save")
	public String save(CourseKnowledge courseKnowledge, @RequestParam(required=false) String extId, Model model, RedirectAttributes redirectAttributes,@RequestParam(required=false) String courseVesionId, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, courseKnowledge)){
			return form(courseKnowledge, model, extId, courseVesionId, request, response);
		}
		
		model.addAttribute("courseId", courseKnowledge.getCourseId());
		System.err.println(courseKnowledge.getParentIds());
		courseKnowledgeService.save(courseKnowledge);
		addMessage(redirectAttributes, "保存知识点成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseId="+courseKnowledge.getCourseId();
	}
	
	@RequiresPermissions("questionlib:courseKnowledge:delete")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required=false) String id, @RequestParam(required=false) String extId, CourseKnowledge courseKnowledge, RedirectAttributes redirectAttributes) {
		//查找知识点下是否有试题
		
		
	 try {
		 List<String> List1=	courseKnowledgeService.findIdListByParentIds(courseKnowledge);
		   List1.add(id);
		  /* String ids ="";
		    for (String string : List1) {
				if(ids.equals(""))
				{
					ids  = "'"+string+"'";
				}else {
					ids += ",'"+string+"'";
				}
			}*/
		   
		   Parameter param=  courseKnowledge.getSqlParam();
		   param.put("ids", List1);
		   for (int i = 0; i < List1.size(); i++) {
			   	CourseKnowledge ck = new CourseKnowledge();
			   	ck.setId(List1.get(i));
				courseKnowledgeService.delete(ck);
		   }
		   addMessage(redirectAttributes, "删除知识点成功");
			/*
			 * //Integer
			int count= courseKnowledgeService.HadQuestionsCount(courseKnowledge);  //获取知识点下试题的个数
			String courseId =courseKnowledge.getCourseVesionId();
			
			if(count>0)
			{
				addMessage(redirectAttributes, "此知识点含有试题，不能删除");
				return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseId="+courseId;
			}
			
			//查找知识点是否有下一级节点
			CourseKnowledge csk = new CourseKnowledge();
			csk.setParentIds(id+",");
			List<CourseKnowledge> list = courseKnowledgeService.findList(csk);
			if(list!=null&&list.size()>0)
			{
				addMessage(redirectAttributes, "此知识点含有下一级节点，不能删除");
				return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseVesionId="+courseKnowledge.getVersionId();
			}
			
			
		    csk = new CourseKnowledge();
			csk.setId(id);
			courseKnowledgeService.delete(csk);
			addMessage(redirectAttributes, "删除知识点成功");*/
	} catch (Exception e) {
		addMessage(redirectAttributes, "不能删除此知识点,其他地方还在使用!");
		e.printStackTrace();
	}
		return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseVesionId="+courseKnowledge.getVersionId();
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,@RequestParam(required=false) String courseId,HttpServletResponse response) {
		//验证前台数据
		if("请选择".equals(courseId)){
			return null;
		}else{
			List<Map<String, Object>> mapList = Lists.newArrayList();
			CourseKnowledge ckl =new CourseKnowledge();
			ckl.setCourseId(courseId);
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
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseQuestionByCouresId")
	public String getCourseQuestionByCouresId(@RequestParam(required=false) String id, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseVesion> list = QuestionlibTld.getCourseVersionByCourseId(id );
		for (int i=0; i<list.size(); i++){
			CourseVesion e = list.get(i);
			 {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("value", e.getTitle());
				mapList.add(map);
			}
		}
		return JSON.toJSONString(mapList);
	}
	
	//导入excel表格
	//@ResponseBody
	@RequiresPermissions("questionlib:courseKnowledge:download")
	@RequestMapping(value="importExcel")
	public String importExcel(@RequestParam(value = "file", required = false) MultipartFile excelFile, @RequestParam(required=false) String courseVesionId, @RequestParam(required=false) String extId,HttpServletRequest request, HttpServletResponse response, Model model){
		if(StringUtils.isBlank(extId)){
	    	extId = "1";
	    }
		if(StringUtils.isBlank(courseVesionId)){
			courseVesionId = request.getParameter("courseVesionId");
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String questionPath = request.getSession().getServletContext().getRealPath("/userfiles/1/images/upload/knowledgeFile/");
		System.err.println(questionPath);
		if(null==excelFile){
			System.err.println("我是空值");
			excelFile = multipartRequest.getFile("excelFile");
		}else{
			System.err.println("我不是空值");
		}
		String excelFileName = excelFile.getOriginalFilename();
		System.err.println(excelFileName);
		String newExcelFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(1000) + "-" + excelFileName;
		String finalPath = questionPath+"\\"+newExcelFileName;
		finalPath = finalPath.replaceAll("\\*", "/");
		System.err.println(newExcelFileName);
		System.err.println(finalPath);
		File targetQuestionFile = new File(questionPath,newExcelFileName);
		if(!targetQuestionFile.exists()){
			targetQuestionFile.mkdirs();
		}
	    try {  
	    	excelFile.transferTo(targetQuestionFile);
		    //文件流指向excel文件  
			FileInputStream fin=new FileInputStream(finalPath); 
			String fileExtend = newExcelFileName.substring(newExcelFileName.lastIndexOf('.')).toLowerCase();
			Workbook workbook = null;//创建工作薄  
			Sheet sheet = null;//得到工作表  
			Row row = null;//对应excel的行  
			Cell cell = null;//对应excel的列  
			if(".xls".equals(fileExtend)){
				workbook=new HSSFWorkbook(fin);//创建工作薄  
				sheet=workbook.getSheetAt(0);//得到工作表  
				row=(HSSFRow)row;//对应excel的行  
				cell=(HSSFCell)cell;//对应excel的列  
			}else{
				try {
					workbook=new XSSFWorkbook(fin);//创建工作薄  
					
				
				} catch (Exception e) {
					model.addAttribute("message",  "不能读取文档，请确认文档是否可用!");
					 return "modules/questionlib/tips";
				}
			
				sheet=workbook.getSheetAt(0);//得到工作表  
				row=(XSSFRow)row;//对应excel的行  
				cell=(XSSFCell)cell;//对应excel的列  
			}
		    int totalRow=sheet.getLastRowNum();//得到excel的总记录条数 
		    //System.err.println("记录行数："+totalRow);
		    int column = sheet.getRow(1).getPhysicalNumberOfCells();
		    //System.err.println("每行列数："+column);
		    //以下的字段一一对应数据库表的字段  
		    List<Map<String,String>> mapList = Lists.newArrayList();
		    for(int i=2;i<=totalRow;i++){  
			     Map<String,String> map =Maps.newTreeMap();
			     map.put("index", "第"+i+"行");
			     row=sheet.getRow(i);
			     int cancel = 0;
			     if(null==row){
			    	 continue;
			     }else{
			    	// System.err.println("第"+i+"行列数："+row.getPhysicalNumberOfCells());
				   //  System.err.println("第"+i+"行："+row);
				     for (int j = 0; j < 9; j++) {
				    	 cell=row.getCell(j);
				    	 //System.err.println("第"+j+"列："+cell);
				    	 if(null!=cell){
				    		 String record = getCellValue(cell);
				    		 System.err.println("第"+j+"列："+record);
				    		 if(StringUtils.isBlank(record)){
				    			 cancel++;
				    			 map.put("key"+j, "");
				    		 }else{
				    			 map.put("key"+j, record);
				    		 }
				    	 }else{
				    		 cancel++;
				    		 map.put("key"+j, "");
				    		 continue;
				    	 }
					}
			     }
			     if(cancel<6){
			    	 mapList.add(map);
			     }
		    }
		    CourseKnowledge root = courseKnowledgeService.get("1");	//根知识点
		    List<String> errorList = Lists.newArrayList();
		    int fail = 0;
		    int success = 0;
		    CourseKnowledge courseKnowledge = new CourseKnowledge();
	    	CourseKnowledge firstParent = new CourseKnowledge();	//parentIds
	    	CourseKnowledge secondParent = new CourseKnowledge();	//parent parentIds
	    	
	    	Integer maxSort=courseKnowledgeService.selectMaxSort(courseVesionId);
	    	
		    for (Map<String, String> map : mapList) {
		    	 
		    	 courseKnowledge = new CourseKnowledge();
		    	  firstParent = new CourseKnowledge();	//parentIds
		    	  secondParent = new CourseKnowledge();	//parent parentIds
		    	  
		    	String key0 = map.get("key0");	//一级知识点		title
		    	String key1 = map.get("key1");	//一级知识点代码	firstLevel
		    	String key2 = map.get("key2");	//学时数			creditHours
		    	String key3 = map.get("key3");	//二级知识点		title
		    	String key4 = map.get("key4");	//二级知识点代码	secondLevel
		    	String key5 = map.get("key5");	//三级知识点		title
		    	String key6 = map.get("key6"); 	//三级知识点代码	thirdLevel
		    	String key7 = map.get("key7");	//测点级别		testLevel	level
		    	String key8 = map.get("key8");	//试题代码编号/知识点编号	  knowledgeCode
		    	if(key8.length()!=6){
		    		errorList.add(key6+"_"+key8);
		    		fail++;
		    		System.err.println(key8+"--信息不全，导入失败！");
		    		continue;
		    	}
		    	if(StringUtils.isBlank(key1)){
		    		key1 = key8.substring(0, 2);
		    	}
		    	if(StringUtils.isBlank(key4)){
		    		key4 = key8.substring(2, 4);
		    	}
		    	if(StringUtils.isBlank(key6)){
		    		key6 = key8.substring(4, 6);
		    	}
		    	//查找、保存一级知识点
		    	String firstCode = key1 + "0000";
		    	firstParent.setKnowledgeCode(firstCode);
		    	firstParent.setVersionId(courseVesionId);
		    	firstParent = courseKnowledgeService.getByKnowledgeCode(firstParent);
		    	if(firstParent==null){
		    		firstParent = new CourseKnowledge();
		    		firstParent.setVersionId(courseVesionId);
		    		firstParent.setKnowledgeCode(firstCode);
			    	firstParent.setParent(root);
			    	//firstParent.setParentIds(root.getId());
			    	firstParent.setLevel("5");
			    	
			    	if(StringUtils.isNotBlank(key0)){
			    		firstParent.setTitle(key0);
			    	}
			    	if(StringUtils.isNotBlank(key2)){
			    		firstParent.setCreditHours(key2);
			    	}
			    	firstParent.setSort(maxSort);
			    	courseKnowledgeService.save(firstParent);
			    	 
		    	}else{
		    		
		    		if(StringUtils.isNotBlank(key0)&&!key0.equals(firstParent.getTitle())){
		    			if(StringUtils.isNotBlank(key2)){
				    		firstParent.setCreditHours(key2);
				    	}
			    		firstParent.setTitle(key0);
			    		firstParent.setSort(maxSort);
			    		courseKnowledgeService.save(firstParent);
			    	}
		    		
		    	}
		    	
		    	firstParent = courseKnowledgeService.getByKnowledgeCode(firstParent);
		    	
		    	//查找、保存二级知识点
		    	String secondCode = key1 + key4 + "00";
				secondParent.setKnowledgeCode(secondCode);
				secondParent.setVersionId(courseVesionId);
				secondParent = courseKnowledgeService.getByKnowledgeCode(secondParent);
				if(secondParent==null){
					secondParent = new CourseKnowledge();
					secondParent.setKnowledgeCode(secondCode);
					secondParent.setVersionId(courseVesionId);
					secondParent.setLevel("5");
					secondParent.setKnowledgeCode(secondCode);
					secondParent.setParent(firstParent);
					
					//secondParent.setParentIds(firstParent.getParentIds()+firstParent.getId());
					if(StringUtils.isNotBlank(key3)){
						secondParent.setTitle(key3);
					}
					secondParent.setSort(++maxSort);
					courseKnowledgeService.save(secondParent);
					
		    	}else {
		    		if(StringUtils.isNotBlank(key3)&&!key3.equals(secondParent.getTitle())){
						secondParent.setTitle(key3);
						secondParent.setSort(++maxSort);
						courseKnowledgeService.save(secondParent);
					}
					 
				}
				secondParent = courseKnowledgeService.getByKnowledgeCode(secondParent);
				//查找、保存三级知识点
				if(StringUtils.isBlank(key8)){
					key8 = key1 + key4 + key6;
				}
				courseKnowledge.setKnowledgeCode(key8);
				courseKnowledge.setVersionId(courseVesionId);
				courseKnowledge = courseKnowledgeService.getByKnowledgeCode(courseKnowledge);
				if(courseKnowledge==null){
					courseKnowledge = new CourseKnowledge();
					courseKnowledge.setKnowledgeCode(key8);
					courseKnowledge.setVersionId(courseVesionId);
					courseKnowledge.setParent(secondParent);
					//courseKnowledge.setParentIds(secondParent.getParentIds()+secondParent.getId());
					courseKnowledge.setLevel(key7);
					if(StringUtils.isNotBlank(key5)){
						courseKnowledge.setTitle(key5);
					}
					courseKnowledge.setSort(++maxSort);
					courseKnowledgeService.save(courseKnowledge);
		    	}else {
		    		if(StringUtils.isNotBlank(key5)&&!key5.equals(courseKnowledge.getTitle())){
						courseKnowledge.setTitle(key5);
						courseKnowledge.setSort(++maxSort);
						courseKnowledgeService.save(courseKnowledge);
					}
					
				}
				
				System.err.println(courseKnowledge.getKnowledgeCode()+":"+courseKnowledge.getParentIds());
				success++;
			}
		    Map<String,Object> errorMap = Maps.newHashMap();
		    errorMap.put("fail", fail);
		    errorMap.put("errorList", errorList);
		    if(fail==0){
		    	model.addAttribute("message", success+"个三级知识点全部上传成功！");
		    }else{
		    	model.addAttribute("message", success+"个三级知识点上传成功，"+fail+"个三级知识点上传失败条，分别是："+errorList.toString());
		    }
//		    return index(new CourseKnowledge(), courseVesionId, model);
		    return "modules/questionlib/tips";
		    //return list(null, extId, courseVesionId, request, response, model); 
		    //return JSON.toJSONString(errorMap)+"------"+JSON.toJSONString(mapList).toString();
//		    return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseVesionId="+courseVesionId;
	   } catch (FileNotFoundException e) {  
		    e.printStackTrace();  
	   } catch(IOException ex){  
		    ex.printStackTrace();  
	   } 
	   model.addAttribute("message", "导入失败！");
//	   return index(new CourseKnowledge(), courseVesionId, model);
	   return "modules/questionlib/tips";
	   //return list(null, extId, courseVesionId, request, response, model);  
//	   return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseVesionId="+courseVesionId;
	}
	
	//根据cell的类型获取cell值
	private static String getCellValue(Cell cell){   
        String value = null;   
        //简单的查检列类型   
        switch(cell.getCellType())   
        {   
            case Cell.CELL_TYPE_STRING://字符串   
                value = cell.getRichStringCellValue().getString();   
                break;   
            case Cell.CELL_TYPE_NUMERIC://数字   
                long dd = (long)cell.getNumericCellValue();   
                value = dd+"";   
                break;   
            case Cell.CELL_TYPE_BLANK:   
                value = "";   
                break;      
            case Cell.CELL_TYPE_FORMULA:   
                value = String.valueOf(cell.getCellFormula());   
                break;   
            case Cell.CELL_TYPE_BOOLEAN://boolean型值   
                value = String.valueOf(cell.getBooleanCellValue());   
                break;   
            case Cell.CELL_TYPE_ERROR:   
                value = String.valueOf(cell.getErrorCellValue());   
                break;   
            default:   
                break;   
        }   
        return value;   
    }  

	@RequiresPermissions("questionlib:courseKnowledge:download")
	@RequestMapping(value = "courseKnowledgeImportForm")
	public String courseKnowledgeImportForm(CourseKnowledge courseKnowledge, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("courseKnowledge", courseKnowledge);
		return "modules/questionlib/courseKnowledgeImportForm";
	}
	
	
	@RequiresPermissions("questionlib:courseKnowledge:view")
	@RequestMapping(value = "zhentilist")
	public String zhentilist(CourseKnowledge courseKnowledge, @RequestParam(required=false) String extId,HttpServletRequest request, HttpServletResponse response, Model model) {
		String courseId="676e2e40b9ee4eaa8e247db91580f858";
		//显示所有子节点
		if(StringUtils.isBlank(courseId)){
			if ( !StringUtils.isBlank(courseKnowledge.getCourseId()) ) {
				courseId =courseKnowledge.getCourseId();
			}else {
				 List<Course> couresList =	QuestionlibTld.getCourse();
				  if (couresList!=null&&couresList.size()>0) {
					  courseId =couresList.get(0).getId();
			     }
			}
		}
		List<CourseKnowledge> list = new ArrayList<CourseKnowledge>();
		CourseKnowledge csk = new CourseKnowledge();
		csk.setCourseId(courseId);
		//csk.setParentIds(extId);
		list = courseKnowledgeService.findList(csk);
		for (int i = 0; i < list.size(); i++) {
			int recommend = courseKnowledgeService.getRecommend(list.get(i).getId());
			list.get(i).setRecommend(recommend);
		}
		if(list.size()<1){
			model.addAttribute("message", courseKnowledge.getTitle()==null?"":courseKnowledge.getTitle() + "无子知识点或无相关知识点！");
		}
		
		courseKnowledge.setCourseId(courseId);
		model.addAttribute("courseKnowledge", courseKnowledge);
		model.addAttribute("courseId", courseId);
		model.addAttribute("extId",extId);
		model.addAttribute("list", list);
		System.err.println("list"+model.toString());
		return "modules/questionlib/zhentiList";
	}
	
	@RequestMapping("recommend")
	@ResponseBody
	public String recommend(String id,int status,HttpServletResponse response) throws IOException{
		int recommend = courseKnowledgeService.getRecommend(id);
		String s = "0";
		boolean b = courseKnowledgeService.recommendKnowledge(id,status);
			if (b) {
				s = "1";
				return s;
			}else {
				return s;
			}
		}
	
	@RequestMapping("sortZT")
	@ResponseBody
	public String sortZT(String id,int sort,HttpServletResponse response) throws IOException{
		boolean b = courseKnowledgeService.sortZT(sort,id);
		String s = "0";
			if (b) {
				s = "1";
				return s;
			}else {
				return s;
			}
		}
}