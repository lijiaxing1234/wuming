/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.dto.SchoolStaticsDTO;
import com.thinkgem.jeesite.modules.questionlib.dto.StaticDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;
import com.thinkgem.jeesite.modules.questionlib.service.StatisticsService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
 

/**
 *  Controller
 * @author cq
 * @version 2016-10-10
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/statistics")
public class StatisticsController extends BaseController {

	@Autowired
	private StatisticsService service;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private CourseKnowledgeService courseKnowledgeService;
	
 
	/**
	 * 用户统计
	 */
	@RequestMapping(value = {"school", ""})
	public String school( SchoolStaticsDTO school,HttpServletRequest request, HttpServletResponse response, Model model) {
		if (null == school) {
			school = new SchoolStaticsDTO();
		}
		Page<SchoolStaticsDTO> page = new Page(request,response); 
//		school.setStart((page.getPageNo()-1) * page.getPageSize());
//		school.setPageSize(page.getPageSize());
		school.setPage(page);
 		page.setList(service.getSchoolStatics(school));
		 
		model.addAttribute("page", page);
		model.addAttribute("school", school);
		
		Office office = new Office();
		office.setType("1");	//1代表office类型为机构，2代表部门，学校属于机构类型
		 List<String> List1=new ArrayList<String>();
		 List1.add("1");
		 List1.add("2");
		 Parameter param=  office.getSqlParam();
		   param.put("ids", List1);
		List<Office> schoolList = officeService.findSchool(office);
		model.addAttribute("schoolList", schoolList);
		
		return "modules/questionlib/statistics/school";
	}
    
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("questionlib:statistics:school:export")
    @RequestMapping(value = "schoolExport")
    public String exportFile(SchoolStaticsDTO school, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学校统计数据导出"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<SchoolStaticsDTO> list =service.getSchoolStatics(school);
    		new ExportExcel("学校统计数据导出", SchoolStaticsDTO.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/statistics";
    }
	 
	/**
	 * 试题统计
	 */
	@RequestMapping(value = {"question"})
	public String question( StaticDTO staticDTO,HttpServletRequest request, HttpServletResponse response, Model model) {
		 String  specialtyId =request.getParameter("specialty.id");
		 if(StringUtils.isNotBlank(specialtyId)){
			 staticDTO.setSpecialtyId(specialtyId);
		 }
		 staticDTO.setOfficeId(UserUtils.getUser().getCompany().getId());
		Page<StaticDTO> page = new Page<StaticDTO>(request,response); 
		staticDTO.setPage(page);
		page.setList(service.getQuestionStatics(staticDTO));
		model.addAttribute("page", page);
		model.addAttribute("staticDTO", staticDTO);
		
		
		model.addAttribute("courseId", staticDTO.getCourseId());
		model.addAttribute("versionId", staticDTO.getVersionId());
		
		
		return "modules/questionlib/statistics/courseQuesiton";
	}
	
	
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("questionlib:statistics:question:export")
    @RequestMapping(value = "questionExport")
    public String questionExport(StaticDTO staticDTO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String  specialtyId =request.getParameter("specialty.id");
			 if(StringUtils.isNotBlank(specialtyId)){
				 staticDTO.setSpecialtyId(specialtyId);
			 }
            String fileName = "试题数量统计导出"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            staticDTO.setOfficeId(UserUtils.getUser().getCompany().getId());
            List<StaticDTO> list =service.getQuestionStatics(staticDTO);
    		new ExportExcel("试题数量统计导出", StaticDTO.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/statistics/question";
    }
	
	
	
	
	
	/**
	 * 知识点统计
	 */
  
	 
	@RequestMapping(value = {"knowledgePointsIndex",""})
	public String knowledgePointsIndex(CourseKnowledge courseKnowledge,@RequestParam(required=false) String courseVesionId, Model model) {
		String specialtyId  = "";
		String courseId = "";
		/*try {
			List<CourseVesion> courseVesionList	=null;
			if (!StringUtils.isBlank(courseVesionId)) {
				courseId = QuestionlibTld.getCourseVersionByVersionId(courseVesionId).getCourseId();
				specialtyId  =QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
				courseVesionList = QuestionlibTld.getCourseVersionByCourseId(courseId);
			}else{
				courseVesionList = QuestionlibTld.getCourseVersion() ;
				if (courseVesionList!=null&&courseVesionList.size()>0) {
				    courseId =courseVesionList.get(0).getCourseId() ;
				    courseVesionId= courseVesionList.get(0).getId() ;
					Course coures =	QuestionlibTld.getCourseByID("03354d9e35eb4bbb9caccc3ee7eb3733");
				    specialtyId  =coures.getSpecialtyId();
				    courseVesionList = QuestionlibTld.getCourseVersionByCourseId("03354d9e35eb4bbb9caccc3ee7eb3733");
				}
			}
			 model.addAttribute("courseVesionList", courseVesionList);
			 model.addAttribute("specialtyId", specialtyId);
			 model.addAttribute("courseId", courseId);
			 model.addAttribute("courseVesionId", courseVesionId);
			 
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		return "modules/questionlib/statistics/knowledgePointsIndex";
	}
	
 
	@RequestMapping(value = {"knowledgePointsList", ""})
	public String knowledgePointsList(CourseKnowledge courseKnowledge, String parentIds,   String id,String versionId,   HttpServletRequest request, HttpServletResponse response, Model model) {
		//显示所有子节点
		 
		if (!StringUtils.isBlank(id)) {
			
			courseKnowledge.setId(id);
			courseKnowledge.setParentIds(parentIds);
			courseKnowledge.setVersionId(versionId);
		    String pl =request.getParameter("pl");
		    if ( StringUtils.isBlank(pl)) {
		    	pl ="1";
		    }
		    
		    Date date = new Date();//当前日期
		    {
		    	model.addAttribute("pl", pl);
		    	pl ="-"+pl;
		    	int m =Integer.parseInt(pl);
		    	
				 Calendar calendar = Calendar.getInstance();//日历对象
				 calendar.setTime(date);//设置当前日期
				 calendar.add(Calendar.MONTH, m);//月份减 
				 courseKnowledge.setStartDt(calendar.getTime());
				 courseKnowledge.setEndDt(date);
			}
		     
			 
		    List<CourseKnowledge> list=	service.getKnowledgePointsStatics(courseKnowledge);
		    model.addAttribute("list", list);
		    model.addAttribute("parentIds", parentIds);
		    model.addAttribute("id", id);
		    model.addAttribute("versionId", versionId);
		}
		
		
		return "modules/questionlib/statistics/knowledgePointsList";
	}
	
	@RequestMapping("accreditStatistics")
	public String accreditStatistics(SchoolQuestionlib schoolQuestionlib,HttpServletRequest request,HttpServletResponse response,Model model){
		schoolQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
		Page<SchoolQuestionlib> page = service.accreditStatistics(new Page<SchoolQuestionlib>(request,response),schoolQuestionlib);
		model.addAttribute("schoolQuestionlib", schoolQuestionlib);
		model.addAttribute("page", page);
		return "modules/questionlib/statistics/questionLibStatistics";
	}
	
	@RequestMapping("exportQuestionLibStatistics")
	public String exportQuestionLibStatistics(SchoolQuestionlib schoolQuestionlib,HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes){
		try {
			Page<SchoolQuestionlib> page = service.accreditStatistics(new Page<SchoolQuestionlib>(request,response),schoolQuestionlib);
			String fileName = "questionLibStatistics" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("题库统计数据",SchoolQuestionlib.class).setDataList(page.getList()).write(response,fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出题库统计分析失败！失败信息" + e.getMessage());
		}
		return "redirect" +adminPath + "/questionlib/statistics/accreditStatistics";
	}
	
	
	
}