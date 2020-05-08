/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 专业Controller
 * @author webcat
 * @version 2016-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/specialty")
public class SpecialtyController extends BaseController {

	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseVesionService courseVesionService;
	
	@Autowired
	private CourseQuestionlibService courseQuestionlibService;
	
	@ModelAttribute("specialty")
	public Specialty get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return specialtyService.get(id);
		}else{
			return new Specialty();
		}
	}

	@RequiresPermissions("questionlib:specialty:view")
	@RequestMapping(value = {""})
	public String index(Specialty specialty, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/questionlib/specialtyIndex";
	}

	@RequiresPermissions("questionlib:specialty:view")
	@RequestMapping(value = {"list"})
	public String list(Specialty specialty, Model model) {
		if (StringUtils.isNotBlank(specialty.getParentIds())) {
			specialty.setParentIds(specialty.getParentIds() + specialty.getId() );
			 model.addAttribute("list", specialtyService.findAllByParentIds(specialty));
			 
		} else {
			model.addAttribute("specialtyId",specialty.getId() );
			 model.addAttribute("list", specialtyService.findList(new Specialty(specialty.getId())));
		}
       
		return "modules/questionlib/specialtyList";
	}

	@RequiresPermissions("questionlib:specialty:view")
	@RequestMapping(value = "form")
	public String form(Specialty specialty, Model model) {
		if (specialty.getParent()==null || specialty.getParent().getId()==null){
			Specialty a = new Specialty();
			a.setId("0");
			specialty.setParent(a);
		}
		specialty.setParent(specialtyService.get(specialty.getParent().getId()));
	
	
		model.addAttribute("specialty", specialty);
		return "modules/questionlib/specialtyForm";
	}
	
	@RequiresPermissions("questionlib:specialty:edit")
	@RequestMapping(value = "save")
	public String save(Specialty specialty, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, specialty)){
			return form(specialty, model);
		}
		specialtyService.save(specialty);
	
		
		addMessage(redirectAttributes, "保存专业'" + specialty.getTitle() + "'成功");
		
		return "redirect:" + adminPath + "/questionlib/specialty/list?id="+specialty.getId()+"&parentIds="+specialty.getParentIds();
	}
	
	@RequiresPermissions("questionlib:specialty:delete")
	@RequestMapping(value = "delete")
	public String delete(Specialty specialty, RedirectAttributes redirectAttributes) {
		
		
		if("1".equals(specialty.getId())){
			addMessage(redirectAttributes, "删除专业失败,不能删除根节点！");
			return "redirect:" + adminPath + "/questionlib/specialty/list";
		}
		
		//检查专业下有没有下一级专业
		 List<String> List1=	specialtyService.findIdListByParentIds(specialty);
		 if(List1!=null){
			 List1.add(specialty.getId());
		 }
		 if (List1!=null&&List1.size()>0) {
			  
				 Course course =new Course();
				   Parameter param=  course.getSqlParam();
				   param.put("ids", List1);
				    
				Integer courseCount=courseService.countCourseBySpecialtyId(course);  //检查专业下是否有课程
				if(courseCount>0){
					addMessage(redirectAttributes, "删除专业失败,请先删除专业下的课程后重试！");
					return "redirect:" + adminPath + "/questionlib/specialty/list";
				}
			 
		}
		 
		//先删除所有的子节点
	//	Specialty specialtyParent=new Specialty();
	//	specialtyParent.setParentIds(specialty.getId());
		
	//	specialtyService.deleteAllChild(specialtyParent);
		 
		if(List1!=null&&List1.size()==1)
		{
			specialtyService.delete(specialty);
			
			addMessage(redirectAttributes, "删除专业成功 ！");
		}else
		{
			addMessage(redirectAttributes, "删除专业失败,请先删除下级节点！");
		}
		return "redirect:" + adminPath + "/questionlib/specialty/list";
	}
	
	
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("questionlib:specialty:export")
    @RequestMapping(value = "export")
    public String exportFile(Specialty specialty, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "专业数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<Specialty> page ;
            if(specialty!=null){
            	page=specialtyService.findList(new Specialty(specialty.getId()));
            }else{
            	page = specialtyService.findAll();
            }
    		new ExportExcel("专业数据", Specialty.class).setDataList(page).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/questionlib/specialty/list";
    }
	
	
	
	
	
	

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Specialty> list = specialtyService.findAll();
		for (int i=0; i<list.size(); i++){
			Specialty e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParent().getId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getTitle());
			
			mapList.add(map);
		}
		return mapList;
	}
	
	//导入excel表格
		//@ResponseBody
		@RequiresPermissions("questionlib:specialty:download")
		@RequestMapping(value="importExcel")
		public String importExcel(@RequestParam(value = "file", required = false) MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response, Model model){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String questionPath = request.getSession().getServletContext().getRealPath("/userfiles/1/images/upload/importExcelFile/");
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
					workbook=new XSSFWorkbook(fin);//创建工作薄  
					sheet=workbook.getSheetAt(0);//得到工作表  
					row=(XSSFRow)row;//对应excel的行  
					cell=(XSSFCell)cell;//对应excel的列  
				}
			    int totalRow=sheet.getLastRowNum();//得到excel的总记录条数 
			    System.err.println("记录行数："+totalRow);
			    int column = sheet.getRow(1).getPhysicalNumberOfCells();
			    System.err.println("每行列数："+column);
			    //以下的字段一一对应数据库表的字段  
			    List<Map<String,String>> mapList = Lists.newArrayList();
			    for(int i=2;i<=totalRow;i++){  
				     Map<String,String> map = Maps.newHashMap();
				     map.put("index", "第"+i+"行");
				     row=sheet.getRow(i);
				     int cancel = 0;
				     if(null==row){
				    	 continue;
				     }else{
				    	 System.err.println("第"+i+"行列数："+row.getPhysicalNumberOfCells());
					     System.err.println("第"+i+"行："+row);
					     for (int j = 0; j < 10; j++) {
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
					    	 }
						}
				     }
				     if(cancel<=8){
				    	 mapList.add(map);
				     }
			    }
			    Specialty specialtyRoot = specialtyService.get("1");
			    if(specialtyRoot==null){
			    	specialtyRoot = new Specialty();
			    	specialtyRoot.setIsNewRecord(true);
			    	specialtyRoot.setId("1");
			    	specialtyRoot.setTitle("根专业");
			    	specialtyRoot.setDesciption("根专业");
			     
			    	specialtyRoot.setParentId("0");
			    	specialtyRoot.setParentIds("0,");
			    	specialtyRoot.setDelFlag("0");
			    	
			    	 
			    	
			    	specialtyService.save(specialtyRoot);
			    }
			    System.err.println(specialtyRoot);
			    List<String> errorList = Lists.newArrayList();
			    int fail = 0;
			    int specialty0Success = 0;
			    int specialtySuccess = 0;
			    int courseSuccess = 0;
			    int versionSuccess = 0;
			    int libSuccess = 0;
			    for (Map<String, String> map : mapList) {
			    	boolean isNull = false;
			    	Specialty sp = null;
			    	Specialty specialty0 = new Specialty();	//专业类别
			    	Specialty specialty = new Specialty();	//专业
			    	Course course = new Course();	//课程
			    	CourseVesion courseVesion = new CourseVesion();	//版本
			    	CourseQuestionlib courseQuestionlib = new CourseQuestionlib();	//题库
			    	String specialty0Title = map.get("key0");	//专业类别名称		specialty0.title	
			    	String specialty0Code = map.get("key1");	//专业类别代码  如：GZ001 是5位代码	specialty0.specialtyCode	
			    	String specialtyTitle = map.get("key2");	//专业代码		specialty.title
			    	String specialtyCode = map.get("key3");	//专业代码  如：GZ001S001 是9位代码   specialty.specialtyCode
			    	String courseTitle = map.get("key4");	//课程名称		course.title
			    	String courseCode = map.get("key5");	//课程代码  如：GZ001S001C003 是13位代码	course.courseCode
			    	String versionTitle = map.get("key6"); 	//版本名称		courseVersion.title
			    	String versionCode = map.get("key7");	//版本代码  如：GZ001S001C003V001 是17位代码	courseVersion.versionCode
			    	String libTitle = map.get("key8");	//题库名称		courseQuestionlib.title
			    	String libCode = map.get("key9");	//题库代码 如：GZ001S001C003V01L001 是21位代码		courseQuestionlib.libCode
			    	//查找、保存专业类别
			    	if(StringUtils.isNotBlank(specialty0Title) && StringUtils.isNotBlank(specialty0Code) && specialty0Code.length()==5){
			    		specialty0.setTitle(specialty0Title);
			    		specialty0.setSpecialtyCode(specialty0Code);
			    		sp = specialtyService.getSpecialtyBySpecialtyCode(specialty0);
			    		if(sp==null){
			    			specialty0.setParent(specialtyRoot);
			    			specialty0.setParentIds(specialtyRoot.getParentIds()+specialtyRoot.getId());
			    			specialtyService.save(specialty0);
			    			System.err.println("specialty0"+specialty0);
			    			specialty0Success++;
			    		}else{
			    			specialty0 = sp;
			    			System.err.println("sp"+sp);
			    		}
			    	}else{
			    		isNull = true;
			    	}
			    	
			    	//查询、保存专业
			    	if(StringUtils.isNotBlank(specialtyTitle) && StringUtils.isNotBlank(specialtyCode) && specialtyCode.length()==9){
			    		specialty.setTitle(specialtyTitle);
			    		specialty.setSpecialtyCode(specialtyCode);
			    		sp = specialtyService.getSpecialtyBySpecialtyCode(specialty);
			    		if(sp==null){
			    			if(isNull){
			    				specialty0Code = specialtyCode.substring(0, 5);
			    				specialty0.setSpecialtyCode(specialty0Code);
			    				specialty0 = specialtyService.getSpecialtyBySpecialtyCode(specialty0);
			    				isNull = false;
			    			}
			    			specialty.setParent(specialty0);
			    			specialty.setParentIds(specialty0.getParentIds()+specialty0.getId());
			    			specialtyService.save(specialty);
			    			System.err.println("specialty"+specialty);
			    			specialtySuccess++;
			    		}else{
			    			specialty = sp;
			    		}
			    	}else{
			    		isNull = true;
			    	}
			    	
			    	//查询、保存课程
			    	if(StringUtils.isNotBlank(courseTitle) && StringUtils.isNotBlank(courseCode) && courseCode.length()==13){
			    		course.setTitle(courseTitle);
			    		course.setCourseCode(courseCode);
			    		Course cr = courseService.getCourseByCourseCode(course);
			    		if(cr==null){
			    			if(isNull){
			    				specialtyCode = courseCode.substring(0, 9);
			    				specialty.setSpecialtyCode(specialtyCode);
			    				specialty = specialtyService.getSpecialtyBySpecialtyCode(specialty);
			    				isNull = false;
			    			}
			    			course.setSpecialtyId(specialty.getId());
			    			courseService.save(course);
			    			System.err.println("course"+course);
			    			courseSuccess++;
			    		}else{
			    			course = cr;
			    		}
			    	}else{
			    		isNull = true;
			    	}
			    	
			    	//查询、保存版本
			    	if(StringUtils.isNotBlank(versionTitle) && StringUtils.isNotBlank(versionCode) && versionCode.length()==17){
			    		courseVesion.setTitle(versionTitle);
			    		courseVesion.setVersionCode(versionCode);
			    		CourseVesion cv = courseVesionService.getVersionByVersionCode(courseVesion);
			    		if(cv==null){
			    			if(isNull){
			    				courseCode = versionCode.substring(0, 13);
			    				course.setCourseCode(courseCode);
			    				course = courseService.getCourseByCourseCode(course);
			    				isNull = false;
			    			}
			    			courseVesion.setCourseId(course.getId());
			    			courseVesionService.save(courseVesion);
			    			System.err.println("courseVesion"+courseVesion);
			    			versionSuccess++;
			    		}else{
			    			courseVesion = cv;
			    		}
			    	}else{
			    		isNull = true;
			    	}
			    	
			    	//查询、保存题库
			    	if(StringUtils.isNotBlank(libTitle) && StringUtils.isNotBlank(libCode) && libCode.length()==21){
			    		courseQuestionlib.setTitle(libTitle);
			    		courseQuestionlib.setLibCode(libCode);
			    		CourseQuestionlib cq = courseQuestionlibService.getQuestionlibByLibCode(courseQuestionlib);
			    		if(cq==null){
			    			if(isNull){
			    				versionCode = libCode.substring(0, 17);
			    				courseVesion.setVersionCode(versionCode);
			    				courseVesion = courseVesionService.getVersionByVersionCode(courseVesion);
			    				isNull = false;
			    			}
			    			courseQuestionlib.setOwnerType("1");
			    			courseQuestionlib.setVersionId(courseVesion.getId());
			    			User user = UserUtils.getUser();
			    			String schoolId = user.getCompany().getId();
			    			courseQuestionlib.setSchoolId(schoolId);
			    			courseQuestionlibService.save(courseQuestionlib);
			    			System.err.println("courseQuestionlib"+courseQuestionlib);
			    			libSuccess++;
			    		}
			    	}
				}
			    Map<String,Object> errorMap = Maps.newHashMap();
			    errorMap.put("fail", fail);
			    errorMap.put("errorList", errorList);
			    if(fail==0){
			    	model.addAttribute("message", "新增专业类别"+specialty0Success+"个，新增专业"+specialtySuccess+"个，新增课程"+courseSuccess+"个，新增版本"+versionSuccess+"个，新增题库"+libSuccess+"个！");
			    }else{
			    	 model.addAttribute("message", "数据格式错误，文档无法解析，导入失败！");
			    }
			//    System.err.println("specialtyRoot"+specialtyRoot);
//			    return index(specialtyRoot, model);
//			    return list(new Specialty(), model); 
			   // return "modules/questionlib/tips";
			    //return JSON.toJSONString(errorMap)+"------"+JSON.toJSONString(mapList).toString();
//			    return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseVesionId="+courseVesionId;
		   } catch ( Exception e) {  
			   model.addAttribute("message", "数据格式错误，文档无法解析，导入失败！");
		   }  
		   
//		   return index(new Specialty(), model);
//		   return list(new Specialty(), model);  
		  // return "modules/questionlib/tips";
		   //return list(null, extId, courseVesionId, request, response, model);  
//		   return "redirect:"+Global.getAdminPath()+"/questionlib/courseKnowledge/list?repage&extId="+extId+"&courseVesionId="+courseVesionId;
		   
		   
		    
			return "modules/questionlib/specialtyImportForm";
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
		@RequiresPermissions("questionlib:specialty:download")
		@RequestMapping(value = "importForm")
		public String importForm(Specialty specialty, HttpServletRequest request, HttpServletResponse response, Model model) {
			model.addAttribute("specialty", specialty);
			return "modules/questionlib/specialtyImportForm";
		}

}