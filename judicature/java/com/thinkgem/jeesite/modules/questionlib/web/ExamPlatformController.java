package com.thinkgem.jeesite.modules.questionlib.web;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.dto.ExamDTO;
import com.thinkgem.jeesite.modules.questionlib.service.ExamPlatformService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;

/**
 * 平台及学校端试卷及测验查询
 * 
 * @author Lenovo
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/exam")
public class ExamPlatformController extends BaseController {

	@Autowired
	ExamPlatformService examService;
	@Autowired
	ExaminationService examService2; //试卷service

	@Autowired
	private OfficeService officeService;

	

	@RequestMapping(value = { "examPlatform" })
	public String examPlatform(ExamDTO examDTO, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (null == examDTO) {
			examDTO = new ExamDTO();
		}
		Page<ExamDTO> page = new Page(request, response);

		examDTO.setStart((page.getPageNo() - 1) * page.getPageSize());
		/*examDTO.setPageSize(page.getPageSize());*/

		page.setCount(examService.getExamStaticsCount(examDTO));
		page.setList(examService.getExamStatics(examDTO));

		model.addAttribute("page", page);
		model.addAttribute("exam", examDTO);
		return "modules/questionlib/exam/examplatform";
	}

	@RequestMapping(value = { "examPlatformSchool" })
	public String examPlatformSchool(ExamDTO examDTO,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (null == examDTO) {
			examDTO = new ExamDTO();
		}

		examDTO.setSchoolId(UserUtils.getUser().getCompany().getId());
		Page<ExamDTO> page = new Page(request, response);

		examDTO.setStart((page.getPageNo() - 1) * page.getPageSize());
		/*examDTO.setPageSize(page.getPageSize());*/

		page.setCount(examService.getExamStaticsCount(examDTO));
		page.setList(examService.getExamStatics(examDTO));

		model.addAttribute("page", page);
		model.addAttribute("exam", examDTO);
		return "modules/questionlib/exam/examplatformschool";
	}
	
	
	@RequestMapping(value = {"index",""})
	public String index(Model model) {
		Office office = new Office();
		office.setType("1");	//1代表office类型为机构，2代表部门，学校属于机构类型
		 List<String> List1=new ArrayList<String>();
		 List1.add("1");
		 List1.add("2");
		 Parameter param=  office.getSqlParam();
		   param.put("ids", List1);
		List<Office> schoolList = officeService.findSchool(office);
		model.addAttribute("schoolList", schoolList);
		return "modules/questionlib/testPaper";
	}
	
	/**
	 * 根据学校id查询试卷
	 * 
	 * 搜索条件有：学校，老师，考试名称，课程名称，版本名称
	 * @param request
	 * @param response
	 * @param model
	 * @param examDTO
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(String a,HttpServletRequest request,HttpServletResponse response,Model model,ExamDTO examDTO){
		
		User user=UserUtils.getUser();
		
		String userType=user.getUserType();
		
		if("1".equals(userType)||StringUtils.isBlank(userType)){ //系统管理员用户
			return "modules/questionlib/exam/examplatformschool";
			
		}else if("2".equals(userType)){//出版社管理员用户
			
			Office office = new Office();
			office.setType("1");	//1代表office类型为机构，2代表部门，学校属于机构类型
			 List<String> List1=new ArrayList<String>();
			 List1.add("1");
			 List1.add("2");
			 Parameter param=  office.getSqlParam();
			   param.put("ids", List1);
			List<Office> schoolList = officeService.findSchool(office);
			model.addAttribute("schoolList", schoolList);
			
			@SuppressWarnings("unchecked")
			List<String> companyIds=Collections3.extractToList(schoolList, "id");
			Map<String,Object> sqlParam= examDTO.getSqlParam();
			sqlParam.put("companyIds", companyIds);
			
		}else { //学校用户
			examDTO.setSchoolId(user.getCompany().getId());
		}
		
	
		if(("1").equals(a)){
			model.addAttribute("message", "该试卷没有出完!");
		}
	
		
		/*String schoolId = examDTO.getSchoolId();
		if(null == schoolId || "".equals(schoolId)){
			String companyId = UserUtils.getUser().getCompany().getId();
			examDTO.setSchoolId(companyId);
		}
		//根据company_id 查询名称
		try {
			String schoolName = examService.getSchoolName(UserUtils.getUser().getCompany().getId());
			examDTO.setSchoolName(schoolName);
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		if(("1").equals(a)){
			model.addAttribute("message", "该试卷没有出完!");
		}
		 String  specialtyId =request.getParameter("specialty.id");
		 if(StringUtils.isNotBlank(specialtyId))
		 {
			 examDTO.setSpecialtyId(specialtyId);
		 }
		 try {
			    examDTO.setCourseId(examDTO.getCourseId().replace("请选择",""));
				examDTO.setVersionId(examDTO.getVersionId().replace("请选择",""));
				examDTO.setSpecialtyId(examDTO.getSpecialtyId().replace("请选择",""));
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		 
		 

		
		Page<ExamDTO> page = examService.findExamBySchoolId(new Page<ExamDTO>(request, response),examDTO);
		model.addAttribute("page", page);
		model.addAttribute("examDTO", examDTO);
		return "modules/questionlib/exam/examplatformschool";
	}
	
	
	@Autowired
	CommonController  commonController;
	@Autowired
	SpecialtyController SpecialtyController;
	
	
	@ResponseBody
	@RequestMapping("findSchoolSpecialty")
	public  String   findSchoolSpecialty(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response){
		
		List<Map<String,Object>> list=null;
		User user=UserUtils.getUser();
		String userType=user.getUserType();
		
		if("1".equals(userType)||StringUtils.isBlank(userType)){
			list=Lists.newArrayList();
			return  JSON.toJSONString(list);
		}else if("2".equals(userType)){
			return JSON.toJSONString(SpecialtyController.treeData(extId,type,grade,isAll,response));
		}else{
			return commonController.findSchoolSpecialty();
		}
	}
	
	@ResponseBody
	@RequestMapping("findSchoolCourseBySpecialtyId")
	public String  findSchoolCourseBySpecialtyId(String specialtyId,HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> list=null;
		User user=UserUtils.getUser();
		String userType=user.getUserType();
		
		if("1".equals(userType)||StringUtils.isBlank(userType)){
			list=Lists.newArrayList();
			return  JSON.toJSONString(list);
		}else if("2".equals(userType)){
			return commonController.getCourseBySpecialtyId(specialtyId, request, response);
		}else{
			return commonController.findSchoolCourseBySpecialtyId(specialtyId);
		}
		
	}
	
	@ResponseBody
	@RequestMapping("findSchoolCourseVersionByCourseId")
	public String findSchoolCourseVersionByCourseId(String courseId, HttpServletResponse response){	
		List<Map<String,Object>> list=null;
		User user=UserUtils.getUser();
		String userType=user.getUserType();
		
		if("1".equals(userType)||StringUtils.isBlank(userType)){
			list=Lists.newArrayList();
			return  JSON.toJSONString(list);
		}else if("2".equals(userType)){
			return commonController.getCourseQuestionByCouresId(courseId, response);
		}else{
			return commonController.findSchoolCourseVersionByCourseId(courseId);
		}
		
	}
	
	
	
	/**
	 * 查看试卷
	 * A/B卷的选择
	 */
	
	@RequestMapping(value = "check")
	public String check(String examId,HttpServletRequest request,HttpServletResponse response,Model model){
		Examination exam=new Examination(examId);
		Examination exam2 = examService2.getExam(exam);
		List<Examdetail> examDetailByExamId = examService2.getExamDetailByExamId(exam2);
		if(examDetailByExamId==null){
			return "redirect:"+Global.getAdminPath()+"/questionlib/exam/list?a="+1;
		}
		model.addAttribute("exam", exam2);
		model.addAttribute("examdetails",examDetailByExamId);//区分A B卷还是一般卷的
		return "modules/questionlib/exam/exam_testpaper_examTitle";
	}
	/**
	 * 试卷具体情况
	 * @param examdetailId
	 * @param examId
	 * @param beginTime
	 * @param endTime
	 * @param examType
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("adjustExamList")
	public  String  adjustExamList(String examdetailId,String examId,Date beginTime,Date endTime,String examType, Model model,HttpServletRequest request,HttpServletResponse response){
		Map<String,List<ExamdetailQuestion>> dataMap=examService2.getExamdetailQuestionByExamdetailIdGroupbyQuesType(examdetailId,beginTime,endTime,examType);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("examId", examId);
		model.addAttribute("examdetailId", examdetailId);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("examType", examType);
		return "modules/questionlib/exam/exam_testpaper_adjustExamList";
	}
}
