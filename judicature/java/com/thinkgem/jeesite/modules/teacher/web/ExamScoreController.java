package com.thinkgem.jeesite.modules.teacher.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentTaskDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassWorkService;
import com.thinkgem.jeesite.modules.teacher.service.ExamScoreService;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;
/**
 * 成绩管理
 */

@Controller
@RequestMapping("${teacherPath}/examScore")
public class ExamScoreController extends BaseController{
	
	@Autowired
	TestPaperService testPaperService;
	@Autowired
	ExamScoreService examScoreService;
	@Autowired
	ExamService examService;
	@Autowired
	ExamClassWorkService examClassWorkService;
	@Autowired
	SchoolClassService schoolClassService;
	
	@ModelAttribute
	public Exam get(@RequestParam(required=false) String id) {
		Exam entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examService.getExamDetail(id);
		}
		if (entity == null){
			entity = new Exam();
		}
		return entity;
	}
	
	/**
	 * 线下成绩列表
	 */
	@RequestMapping("examScoreList")
	public String examScoreList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		//获得教师所任课程的所有试卷成绩的信息
		Page<Exam> page = testPaperService.getExamByTeacherIdAndVersionId(new Page<Exam>(request, response), exam,userId,versionId);
		model.addAttribute("examList", page);
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}

		if(("1").equals(a)){
			model.addAttribute("message","该班没有成绩");
		}
		return "teacher/ExamScore/exam_score";
	}
	/**
	 * 在线成绩列表
	 */
	@RequestMapping("examOnLineScoreList")
	public String examOnLineScoreList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		//获得教师所任课程的所有试卷成绩的信息
		Page<Exam> page = testPaperService.getOnLineExamByTeacherIdAndVersionId(new Page<Exam>(request, response), exam,userId,versionId);
		model.addAttribute("examList", page);
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}
		if(("1").equals(a)){
			model.addAttribute("message","该班没有成绩");
		}
		return "teacher/ExamScore/exam_score2";
	}
	/**
	 * 线上成绩确认
	 */
	@RequestMapping("examOnLineScore")
	public String examOnLineScore(String a,StudentTaskDTO studentTaskDTO,String examDetailType,String examId,String examClassId,String type,String status,HttpServletRequest request, HttpServletResponse response,Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		HttpSession session = request.getSession();
		if(examClassId!=null){
			String[] split = examClassId.split(",");
			examClassId=split[0];
		}
		/*if(examId==null||("").equals(examId)){
			 examId=(String) session.getAttribute("examId");
			if(examClassId==null||("").equals(examClassId)){
				examClassId=(String) session.getAttribute("examClassId");
			}
		}else{
			session.setAttribute("examId", examId);
			session.setAttribute("examClassId", examClassId);
		}*/
		
		//获取到试卷详情
		Exam exam = examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("exam", exam);
		model.addAttribute("school", school);
		//列表
		Page<StudentTaskDTO> page = examScoreService.queryOnLineScore(new Page<StudentTaskDTO>(request, response), studentTaskDTO,examId,examClassId,userId,versionId);
		//根据examId查询examDetailId
		List<String> examDetailIds=examService.getExamDetailIdByExamId(examId);
		String AB=null;
		if(examDetailIds.size()>1){
			/*String detailIdA=examService.getExamDetailId(examId,"A");
			String detailIdB=examService.getExamDetailId(examId,"B");*/
			AB="2";
		}
		if(examDetailType==null||(("").equals(examDetailType))){
			examDetailType="A";
		}
		String detailId=examService.getExamDetailId(examId,examDetailType);
		model.addAttribute("AB", AB);
		model.addAttribute("detailId", detailId);
		model.addAttribute("examDetailType", examDetailType);
		model.addAttribute("examList", page);
		model.addAttribute("type", type);
		/*if(("1").equals(a)){
			model.addAttribute("message", "该班没有成绩");
		}*/
		if(("score").equals(status)){
			return "teacher/ExamScore/exam_detail";
		}
		//获取班级学生所有人的分数
				List<StudentTask> list=examService.getAllStudentScore(exam.getId(),examClassId);
				if(list.size()>0){
					StudentTask studentTaskMax=list.get(0);//班级第一
					Float scoreMax=studentTaskMax.getScore();
					if(scoreMax==null||("").equals(scoreMax)){
						scoreMax=0.0f;
					}
					model.addAttribute("scoreMax", scoreMax);
					Float scoreMin=0.0f;
					for(int i=list.size()-1;i>=0;i--){
						StudentTask studentTaskMin=list.get(i);//班级倒数第一
						Float score2=studentTaskMin.getScore();
						if(score2!=null){
							scoreMin=score2;
							break;
						}
					}
					model.addAttribute("scoreMin", scoreMin);
					Float scoreTotal =0.0f;//全班总分
					for (StudentTask studentTask : list) {
						if(studentTask!=null&&studentTask.getScore()!=null){
							Float score=studentTask.getScore();
							scoreTotal=scoreTotal+score;
						}
					}
					Integer countPerson=examClassWorkService.totalPerson(examClassId);//班内总人数
					if(countPerson!=0){
						Float avgScore=scoreTotal/countPerson;
						model.addAttribute("avgScore", avgScore);//平均分
					}
					Integer totalScore=examService.getExamTotalScore(exam.getId());//试卷总分
					model.addAttribute("totalScore", totalScore);
				}else{
					if(("no").equals(type)){//线下成绩
						return "redirect:"+teacherPath+"/testPaper/testPaperList?a=1";
					}else{//在线成绩
						return "redirect:"+teacherPath+"/testPaper/testPaperOnLineList?a=1";
						
					}
				}
				
		return "teacher/ExamScore/examScore_onLine_detail";
	}
	/**
	 * 班级人员列表
	 */
	@RequestMapping("allStudent")
	public String allStudent(StudentTaskDTO studentTaskDTO,String examId,String examClassId,String examDetailType,HttpServletRequest request, HttpServletResponse response,Model model){
		
		//获取到试卷详情
		Exam exam = examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("exam", exam);
		model.addAttribute("school", school);
		//列表
		if(studentTaskDTO==null){
			studentTaskDTO=new StudentTaskDTO();
		}
		Page<StudentTaskDTO> page = examScoreService.getAllStudent(new Page<StudentTaskDTO>(request, response),studentTaskDTO,examClassId,examId);
		model.addAttribute("examList", page);
		//根据examId查询examDetailId
		List<String> examDetailIds=examService.getExamDetailIdByExamId(examId);
		String AB=null;
		if(examDetailIds.size()>1){
			/*String detailIdA=examService.getExamDetailId(examId,"A");
			String detailIdB=examService.getExamDetailId(examId,"B");*/
			AB="2";
		}
		if(examDetailType==null||(("").equals(examDetailType))){
			examDetailType="A";
		}
		if(examDetailType!=null){
			String[] split = examDetailType.split(",");
			if(split.length>=1){
				examDetailType=split[0];
			}
		}
		Integer totalScore=examService.getExamTotalScore(exam.getId());//试卷总分
		String detailId=examService.getExamDetailId(examId,examDetailType);
		model.addAttribute("AB", AB);
		model.addAttribute("detailId", detailId);
		model.addAttribute("examDetailType", examDetailType);
		model.addAttribute("totalScore", totalScore);
		return "teacher/ExamScore/exam_detail";
		
	}
	/**
	 * 成绩模板导出
	 */
	 @RequestMapping(value = "exportClassStudent")
	    public String exportClassStudent(StudentTaskDTO studentTask,String examDetailId,String examId,String examClassId,String examDetailType,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
			try {
				String[] split = examClassId.split(",");
				if(split.length>=1){
					examClassId=split[0];
				}
				String[] split2 = examId.split(",");
				if(split2.length>=1){
					examId=split2[0];
				}
				Exam exam = get(examId);
	            String fileName ="exam"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
	            Page<StudentTaskDTO> page = examScoreService.getAllClassStudent(new Page<StudentTaskDTO>(request, response),studentTask,examClassId,examId);
	    		new ExportExcel(exam.getTitle()+"成绩录入列表", StudentTaskDTO.class).setDataList(page.getList()).write(response, fileName).dispose();
	    		return null;
			} catch (Exception e) {
				addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
				e.printStackTrace();
			}
			return "redirect:"+teacherPath+"/examScore/allStudent?examId="+examId+"&examClassId="+examClassId;
	    }
	
	
	/**
	 * 线下成绩导出
	 */
	 @RequestMapping(value = "export")
	    public String exportFile(StudentTaskDTO studentTask,String examDetailId,String examId,String examClassId,String examDetailType,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
			try {
				String[] split = examClassId.split(",");
				if(split.length>=1){
					examClassId=split[0];
				}
				String[] split2 = examId.split(",");
				if(split2.length>=1){
					examId=split2[0];
				}
				Exam exam = get(examId);
	            String fileName ="exam"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
	            Page<StudentTaskDTO> page = examScoreService.getAllStudent(new Page<StudentTaskDTO>(request, response),studentTask,examClassId,examId);
	    		new ExportExcel(exam.getTitle()+"成绩录入列表", StudentTaskDTO.class).setDataList(page.getList()).write(response, fileName).dispose();
	    		return null;
			} catch (Exception e) {
				addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
				e.printStackTrace();
			}
			return "redirect:"+teacherPath+"/examScore/allStudent?examId="+examId+"&examClassId="+examClassId;
	    }
	/**
	 * 线下成绩导入功能
	 */
    @RequestMapping(value = "import")
    public String importFile(@RequestParam("file")MultipartFile file,String examDetailId,String examId,String examClassId,String examDetailType,RedirectAttributes redirectAttributes) {
    	String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
    	String schoolId=examService.getTeacherCompanyId(userId);
    	try {
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StudentTaskDTO> list = ei.getDataList(StudentTaskDTO.class);
			Float totalScore=examService.getExamTotalScoreByExamDetailId(examDetailId);//试卷总分
			for (StudentTaskDTO studentTaskDTO : list){
				try{
					if(studentTaskDTO!=null){
						String studentCode=studentTaskDTO.getStdCode();
						Student student=examService.getStudent(studentCode,schoolId);
						if(student!=null){
							String studentId=student.getId();
							Float score=studentTaskDTO.getScore();
							if(totalScore<score){
								 throw new Exception("导入的成绩大于试卷总分！");
							}
							String id=examService.queryStudentTask(studentId,examDetailId);
							if(id==null){
								examScoreService.saveScore(score,studentId,examDetailId);
								testPaperService.submitExamClassId(examId,examClassId);
							}else{
								String s=score+"";
								examService.updateTask(studentId,examDetailId,s);
								testPaperService.submitExamClassId(examId,examClassId);
							}
						}
					}else{
						failureMsg.append("<br/>学生"+studentTaskDTO.getName()+" 导入失败：");
						failureNum++;
					}
				}catch(ConstraintViolationException e){
					e.printStackTrace();
					failureMsg.append("<br/>学生"+studentTaskDTO.getName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(e, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					ex.printStackTrace();
					failureMsg.append("<br/>学生"+studentTaskDTO.getName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学生，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 学生成绩"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学生成绩失败！失败信息："+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:"+teacherPath+"/examScore/allStudent?examId="+examId+"&examClassId="+examClassId;
    }
		
	/**
	 * 线下成绩录入功能
	 */
    @ResponseBody
	@RequestMapping("saveScore")
	public void saveScore(Float score,String studentId,String examDetailId){
		String id=examService.queryStudentTask(studentId,examDetailId);
		if(score==null){
			score=0.0F;
		}
		if(id==null){
			examScoreService.saveScore(score,studentId,examDetailId);
		}else{
			String s=score+"";
			examService.updateTask(studentId,examDetailId,s);
		}
		
	}
	/**
	 * 教师成绩录入完毕
	 */
	@RequestMapping("updateStatus")
	public String updateStatus(String examId,String examClassId){
		testPaperService.submitExamClassId(examId,examClassId);
		return "redirect:"+teacherPath+"/testPaper/testPaperList";
	}
	/**
	 * 补考成绩（缺补考成绩）
	 */
	/*@RequestMapping("examScore")
	public String examScore(HttpServletRequest request, HttpServletResponse response,Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		HttpSession session = request.getSession();
		String examId=(String) session.getAttribute("examId");
		String examClassId=(String) session.getAttribute("examClassId");
		//为了从补考页面跳转回成绩页面
		session.setAttribute("examId", examId);
		session.setAttribute("examClassId", examClassId);
		//获取到试卷详情
		Exam exam = examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("exam", exam);
		model.addAttribute("school", school);
		StudentQuestionDTO studentQuestionDTO=new StudentQuestionDTO();
		Page<StudentQuestionDTO> page = examScoreService.queryOnLineScore(new Page<StudentQuestionDTO>(request, response), studentQuestionDTO,examId,examClassId,userId,versionId);
		model.addAttribute("examList", page);
		return "teacher/ExamScore/examScore_detail";
	}
	*/
	
}
