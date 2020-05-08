package com.thinkgem.jeesite.modules.teacher.web;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.TemplateService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;

@Controller("teacherTemplateController")
@RequestMapping("${teacherPath}/template")
public class TemplateController extends BaseController{
	
	@Autowired
	TemplateService templateService;
	@Autowired
	ExaminationService examService;
	@Autowired
	TestPaperService testPaperService;
	
	
	@ModelAttribute
	public Examination get(String id){
		Examination exam=null;
		if(StringUtils.isNotBlank(id)){
			exam=templateService.get(new Examination(id));
		}
		if(exam==null){
	       exam=new Examination();
		}
		return exam;
	}
	
	/**
	 * 所有的模板
	 * @param exam
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String list(Examination exam,HttpServletRequest request,HttpServletResponse response,Model model){
	   Page<Examination> page=templateService.findExaminationPage(new Page<Examination>(request, response), exam);
	   model.addAttribute("page",page);
	   return "teacher/template/list";
	}
	
	/**
	 * 适用模板
	 */
	@RequestMapping("usetemplate")
	public String  useTemplate(Examination examination,Model model){
		if(examination!=null && StringUtils.isNotBlank(examination.getUseTemplatId())){
			/*String examId=examination.getId();
			String[] split = examId.split(",");
			if(split!=null && split.length>1){
				examination.setId(split[1]);
			}*/
			String examType=examination.getExamType();
			examination=examService.getExam(new Examination(examination.getUseTemplatId()));
			examination.setExamType(examType);
	        model.addAttribute("examination", examination);	
		}//为了在线考试的调用模板
	
		return "teacher/template/details";
	}
	
	/**
	 * 生成试卷
	 */
	@RequestMapping("saveTemplate")
	public String saveTemplate(Examination examination,String specilityDataRelation,String classDataRelation,Model model,HttpServletRequest request){
		
		Examination newExam=(Examination) ObjectUtils.deeplyCopy(examination);
		newExam.setId(IdGen.uuid());
		newExam.setIstemplate(Examination.EXAM_TEMPLATE_NO);
		newExam.setDelFlag("0");
		
		String publishAnswerTime=request.getParameter("request");
		
		if(StringUtils.isNoneBlank(publishAnswerTime)){
			newExam.setPublishAnswerTime(DateUtils.parseDate(publishAnswerTime));
		}else{
			Date d=examination.getPublishAnswerTime();
			 if(d==null){
				Calendar calendar = Calendar.getInstance();
		        Date date = new Date(System.currentTimeMillis());
		        calendar.setTime(date);
		        calendar.add(Calendar.YEAR, +100);
		        date = calendar.getTime();
		        newExam.setPublishAnswerTime(date);
			 }
		}
		templateService.saveUseTemplateGroupQuestions(newExam,examination,specilityDataRelation,classDataRelation);
		
		return "redirect:"+teacherPath+"/examination/adjustExam?id="+newExam.getId();
	}
	/**
	 * 删除模板
	 */@RequestMapping("deleteTemplate")
		public String deleteTemplate(Examination examination){
			String examId=examination.getId();
			testPaperService.deleteExam2(examId);
			return "redirect:"+teacherPath+"/template/list";
		}
	

}
