
package com.thinkgem.jeesite.modules.student.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentExamDetail;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.service.SExamService;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

/**
 * 答题页面初始化
 * 学生作答
 * @author .36
 *
 */
@Controller
@RequestMapping(value = "${studentPath}/studentAnswer")
public class StudentAnswerController extends BaseController{

	@Autowired
	private StudentAnswerService studentAnswerService;
	@Autowired
	private SExamService sExamService;
	
	
	/**
	 * 进入答题页面
	 * @param model
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form (Model model,String examId,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes,String returnUrl,String examType){
		//验证是否已经提交
		boolean isSubmit = sExamService.isSubmit(StudentUserUtils.getUser().getId(), examId, null);
		if(isSubmit){
			return "redirect:"+studentPath+"?answerFlag=2";
		}
		//1、table_student_task添加数据
		//根据测试id 查询测试对象
		SExam exam = sExamService.getSExamEntityById(examId);
		Date beginTime = exam.getBeginTime();
		Date now = new Date();
		//只有考试要验证是否到达开始时间
		if("5".equals(exam.getExamType())){
			if(beginTime.compareTo(now) > 0){
				//考试开始时间小于现在的时间不允许进入考试
				addMessage(redirectAttributes, "未到考试的开始时间，请等待");
				return "redirect:"+studentPath+"?answerFlag=1";
			}else{
				//根据测试id 查询测试详情id
				List<String> examDetailIdList = sExamService.getExamDetailIdByExamId(examId);
				String examDetailId = "";
				if(null == examDetailIdList || examDetailIdList.isEmpty()){
					addMessage(redirectAttributes, "数据异常，table_exam_detail");
					return "redirect:"+studentPath;
				}else{
					if(examDetailIdList.size() == 1){
						examDetailId = examDetailIdList.get(0);
						model.addAttribute("examDetailId", examDetailId);
						boolean flag0 = studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(examDetailId);
						if(flag0){
							addMessage(redirectAttributes, "您已经参加过本次考试，请勿重复进入");
							return "redirect:"+studentPath+"?answerFlag=0";
						}
						
						//根据学生和考试详情查询学生任务ID
						boolean flag = getStudentTaskIdByStudentIdAndExamDetailId(examDetailId);
						if(!flag){
							studentAnswerService.addStudentTask(examDetailId,exam,null);
						}
					}else if(examDetailIdList.size() == 2){
						//有两条数据说明是ab卷
						String examDetailId0 = examDetailIdList.get(0);
						String examDetailId1 = examDetailIdList.get(1);
						if(studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(examDetailId0) || studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(examDetailId1)){
							addMessage(redirectAttributes, "您已经参加过本次考试，请勿重复进入");
							return "redirect:"+studentPath+"?answerFlag=0";
						}else{
							// 判断是否要添加学生任务
							if(getStudentTaskIdByStudentIdAndExamDetailId(examDetailId0) || getStudentTaskIdByStudentIdAndExamDetailId(examDetailId1)){
								//根据学生id  和  考试详情id 查询exam_detail_id
								String reExamDetailId = studentAnswerService.getExamDetailIdByExamDeatilId(examDetailId0);
								if(StringUtils.isNotBlank(reExamDetailId)){
									examDetailId = reExamDetailId;
								}
								
								String reExamDetailId1 = studentAnswerService.getExamDetailIdByExamDeatilId(examDetailId1);
								if(StringUtils.isNotBlank(reExamDetailId1)){
									examDetailId = reExamDetailId1;
								}
								
							}else{
								Random random = new Random();
								examDetailId = examDetailIdList.get(random.nextInt(examDetailIdList.size()));
								model.addAttribute("examDetailId", examDetailId);
								studentAnswerService.addStudentTask(examDetailId,exam,null);
							}
						}
					}
				}
				//2、根据 examDetailId 和题型  查询所有试题 单选题 1 填空题 2 计算题 3 简答题 4 多选题 5 概念题 6 综合题 7 制图题 8 制表题 9 识图题 10 判断题 11
				List<VersionQuestion> sQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"1",examId);
				List<VersionQuestion> fQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"2",examId);
				List<VersionQuestion> cQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"3",examId);
				List<VersionQuestion> sAQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"4",examId);
				List<VersionQuestion> sMQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"5",examId);
				List<VersionQuestion> gainianQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"6",examId);
				List<VersionQuestion> zongheQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"7",examId);
				List<VersionQuestion> zhituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"8",examId);
				List<VersionQuestion> zhibiaoQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"9",examId);
				List<VersionQuestion> shituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"10",examId);
				List<VersionQuestion> panduanQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"11",examId);
				try {
					int sQuestionPoint = 0;
					for (VersionQuestion versionQuestion : sQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							sQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("sQuestionPoint", sQuestionPoint);
					int fQuestionPoint = 0;
					for (VersionQuestion versionQuestion : fQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							fQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("fQuestionPoint", fQuestionPoint);
					int cQuestionPoint = 0;
					for (VersionQuestion versionQuestion : cQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							cQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("cQuestionPoint", cQuestionPoint);
					int sAQuestionPoint = 0;
					for (VersionQuestion versionQuestion : sAQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							sAQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("sAQuestionPoint", sAQuestionPoint);
					int sMQuestionPoint = 0;
					for (VersionQuestion versionQuestion : sMQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							sMQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("sMQuestionPoint", sMQuestionPoint);
					int gainianQuestionPoint = 0;
					for (VersionQuestion versionQuestion : gainianQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							gainianQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("gainianQuestionPoint", gainianQuestionPoint);
					int zongheQuestionPoint = 0;
					for (VersionQuestion versionQuestion : zongheQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							zongheQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("zongheQuestionPoint", zongheQuestionPoint);
					int zhituQuestionPoint = 0;
					for (VersionQuestion versionQuestion : zhituQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							zhituQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("zhituQuestionPoint", zhituQuestionPoint);
					int zhibiaoQuestionPoint = 0;
					for (VersionQuestion versionQuestion : zhibiaoQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							zhibiaoQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("zhibiaoQuestionPoint", zhibiaoQuestionPoint);
					int shituQuestionPoint = 0;
					for (VersionQuestion versionQuestion : shituQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							shituQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("shituQuestionPoint", shituQuestionPoint);
					int panduanQuestionPoint = 0;
					for (VersionQuestion versionQuestion : panduanQuestionList) {
						String quesPoint = versionQuestion.getQuesPoint();
						if(StringUtils.isNotBlank(quesPoint)){
							panduanQuestionPoint += Integer.parseInt(quesPoint);
						}
					}
					model.addAttribute("panduanQuestionPoint", panduanQuestionPoint);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model.addAttribute("sQuestionList", getSingleObjectList(sQuestionList));
				model.addAttribute("fQuestionList", getSingleObjectList(fQuestionList));
				model.addAttribute("cQuestionList", getSingleObjectList(cQuestionList));
				model.addAttribute("sAQuestionList", getSingleObjectList(sAQuestionList));
				model.addAttribute("sMQuestionList", getSingleObjectList(sMQuestionList));
				model.addAttribute("gainianQuestionList", getSingleObjectList(gainianQuestionList));
				model.addAttribute("zongheQuestionList", getSingleObjectList(zongheQuestionList));
				model.addAttribute("zhituQuestionList", getSingleObjectList(zhituQuestionList));
				model.addAttribute("zhibiaoQuestionList", getSingleObjectList(zhibiaoQuestionList));
				model.addAttribute("shituQuestionList", getSingleObjectList(shituQuestionList));
				model.addAttribute("panduanQuestionList", getSingleObjectList(panduanQuestionList));
				if("1".equals(exam.getExamType())){
					exam.setDurationTime(exam.getExamHours());
				}
				//计算该试卷共多少道试题
				int examQuestionCount = sQuestionList.size() + fQuestionList.size() + cQuestionList.size() + sAQuestionList.size() + sMQuestionList.size() + gainianQuestionList.size() + zongheQuestionList.size() + zhituQuestionList.size() + zhibiaoQuestionList.size() + shituQuestionList.size() + panduanQuestionList.size();
				model.addAttribute("examQuestionCount",examQuestionCount);
				exam.setResidueTime(Integer.parseInt(exam.getResidueTime())/60 + "");
				model.addAttribute("exam", exam);
				Student student = StudentUserUtils.getUser();
				model.addAttribute("student", student);
				model.addAttribute("user", StudentUserUtils.getUser());
				model.addAttribute("returnUrl", returnUrl);
				model.addAttribute("examType", examType);
				return "student/pages/answerForm";
			}
		}else{
			//根据测试id 查询测试详情id
			List<String> examDetailIdList = sExamService.getExamDetailIdByExamId(examId);
			String examDetailId = "";
			if(null == examDetailIdList || examDetailIdList.isEmpty()){
				addMessage(redirectAttributes, "您已经参加过本次考试，请勿重复进入");
				return "redirect:"+studentPath+"?answerFlag=0";
			}else{
				if(examDetailIdList.size() == 1){
					examDetailId = examDetailIdList.get(0);
					model.addAttribute("examDetailId", examDetailId);
					boolean flag3 = studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(examDetailId);
					if(flag3){
						addMessage(redirectAttributes, "您已经参加过本次考试，请勿重复进入");
						return "redirect:"+studentPath+"?answerFlag=0";
					}
					//根据学生和考试详情查询学生任务ID
					boolean flag = getStudentTaskIdByStudentIdAndExamDetailId(examDetailId);
					if(!flag){
						studentAnswerService.addStudentTask(examDetailId,exam,null);
					}
				}else if(examDetailIdList.size() == 2){
					//有两条数据说明是ab卷
					String examDetailId0 = examDetailIdList.get(0);
					String examDetailId1 = examDetailIdList.get(1);
					if(studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(examDetailId0) || studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(examDetailId1)){
						addMessage(redirectAttributes, "您已经参加过本次考试，请勿重复进入");
						return "redirect:"+studentPath+"?answerFlag=0";
					}else{
						// 判断是否要添加学生任务
						if(getStudentTaskIdByStudentIdAndExamDetailId(examDetailId0) || getStudentTaskIdByStudentIdAndExamDetailId(examDetailId1)){
							//根据学生id  和  考试详情id 查询exam_detail_id
							String reExamDetailId = studentAnswerService.getExamDetailIdByExamDeatilId(examDetailId0);
							if(StringUtils.isNotBlank(reExamDetailId)){
								examDetailId = reExamDetailId;
							}
							String reExamDetailId1 = studentAnswerService.getExamDetailIdByExamDeatilId(examDetailId1);
							if(StringUtils.isNotBlank(reExamDetailId1)){
								examDetailId = reExamDetailId1;
							}
						}else{
							Random random = new Random();
							examDetailId = examDetailIdList.get(random.nextInt(examDetailIdList.size()));
							model.addAttribute("examDetailId", examDetailId);
							studentAnswerService.addStudentTask(examDetailId,exam,null);
						}
					}
				}
			}
			//2、根据 examDetailId 和题型  查询所有试题 单选题 1 填空题 2 计算题 3 简答题 4 多选题 5 概念题 6 综合题 7 制图题 8 制表题 9 识图题 10 判断题 11
			List<VersionQuestion> sQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"1",examId);
			List<VersionQuestion> fQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"2",examId);
			List<VersionQuestion> cQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"3",examId);
			List<VersionQuestion> sAQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"4",examId);
			List<VersionQuestion> sMQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"5",examId);
			List<VersionQuestion> gainianQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"6",examId);
			List<VersionQuestion> zongheQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"7",examId);
			List<VersionQuestion> zhituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"8",examId);
			List<VersionQuestion> zhibiaoQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"9",examId);
			List<VersionQuestion> shituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"10",examId);
			List<VersionQuestion> panduanQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"11",examId);
			model.addAttribute("sQuestionList", getSingleObjectList(sQuestionList));
			model.addAttribute("fQuestionList", getSingleObjectList(fQuestionList));
			model.addAttribute("cQuestionList", getSingleObjectList(cQuestionList));
			model.addAttribute("sAQuestionList", getSingleObjectList(sAQuestionList));
			model.addAttribute("sMQuestionList", getSingleObjectList(sMQuestionList));
			model.addAttribute("gainianQuestionList", getSingleObjectList(gainianQuestionList));
			model.addAttribute("zongheQuestionList", getSingleObjectList(zongheQuestionList));
			model.addAttribute("zhituQuestionList", getSingleObjectList(zhituQuestionList));
			model.addAttribute("zhibiaoQuestionList", getSingleObjectList(zhibiaoQuestionList));
			model.addAttribute("shituQuestionList", getSingleObjectList(shituQuestionList));
			model.addAttribute("panduanQuestionList", getSingleObjectList(panduanQuestionList));
			try {
				int sQuestionPoint = 0;
				for (VersionQuestion versionQuestion : sQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						sQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("sQuestionPoint", sQuestionPoint);
				int fQuestionPoint = 0;
				for (VersionQuestion versionQuestion : fQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						fQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("fQuestionPoint", fQuestionPoint);
				int cQuestionPoint = 0;
				for (VersionQuestion versionQuestion : cQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						cQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("cQuestionPoint", cQuestionPoint);
				int sAQuestionPoint = 0;
				for (VersionQuestion versionQuestion : sAQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						sAQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("sAQuestionPoint", sAQuestionPoint);
				int sMQuestionPoint = 0;
				for (VersionQuestion versionQuestion : sMQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						sMQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("sMQuestionPoint", sMQuestionPoint);
				int gainianQuestionPoint = 0;
				for (VersionQuestion versionQuestion : gainianQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						gainianQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("gainianQuestionPoint", gainianQuestionPoint);
				int zongheQuestionPoint = 0;
				for (VersionQuestion versionQuestion : zongheQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						zongheQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("zongheQuestionPoint", zongheQuestionPoint);
				int zhituQuestionPoint = 0;
				for (VersionQuestion versionQuestion : zhituQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						zhituQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("zhituQuestionPoint", zhituQuestionPoint);
				int zhibiaoQuestionPoint = 0;
				for (VersionQuestion versionQuestion : zhibiaoQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						zhibiaoQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("zhibiaoQuestionPoint", zhibiaoQuestionPoint);
				int shituQuestionPoint = 0;
				for (VersionQuestion versionQuestion : shituQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						shituQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("shituQuestionPoint", shituQuestionPoint);
				int panduanQuestionPoint = 0;
				for (VersionQuestion versionQuestion : panduanQuestionList) {
					String quesPoint = versionQuestion.getQuesPoint();
					if(StringUtils.isNotBlank(quesPoint)){
						panduanQuestionPoint += Integer.parseInt(quesPoint);
					}
				}
				model.addAttribute("panduanQuestionPoint", panduanQuestionPoint);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//计算该试卷共多少道试题
			int examQuestionCount = sQuestionList.size() + fQuestionList.size() + cQuestionList.size() + sAQuestionList.size() + sMQuestionList.size() + gainianQuestionList.size() + zongheQuestionList.size() + zhituQuestionList.size() + zhibiaoQuestionList.size() + shituQuestionList.size() + panduanQuestionList.size();
			model.addAttribute("examQuestionCount",examQuestionCount);
			if("1".equals(exam.getExamType())){
				exam.setDurationTime(Integer.parseInt(exam.getExamHours())*60 + "");
				exam.setResidueTime(exam.getExamHours());
			}else{
				exam.setResidueTime(Integer.parseInt(exam.getResidueTime())/60+"");
			}
			model.addAttribute("exam", exam);
			Student student = StudentUserUtils.getUser();
			model.addAttribute("student", student);
			model.addAttribute("user", StudentUserUtils.getUser());
			model.addAttribute("returnUrl", returnUrl);
			model.addAttribute("examType", examType);
			return "student/pages/answerForm";
		}
		
	}
	
	private List<VersionQuestion> getSingleObjectList(List<VersionQuestion> sQuestionList) {
		List<VersionQuestion> list = new ArrayList<VersionQuestion>();
		for (VersionQuestion versionQuestion : sQuestionList) {
			if(!list.contains(versionQuestion)){
				list.add(versionQuestion);
			}
		}
		return list;
	}

	/**
	 * 根据学生ID和考试详情查询学生任务ID
	 * @param examDetailId
	 * @return
	 */
	public boolean getStudentTaskIdByStudentIdAndExamDetailId(String examDetailId){
		return studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailId(examDetailId,null);
	}
	
	/**
	 * ajax  异步提交答案
	 * @param model
	 * @param studentAnswer
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "answerQuestion")
	public void answerQuestion(Model model,StudentAnswer studentAnswer,HttpServletResponse response){
		// 判断某考试 老师是否已经对该班级已经进行了收卷操作
		boolean flag = false;
		try {
			 flag = studentAnswerService.isTakeUpTestPaper(studentAnswer);
		} catch (Exception e) {
			logger.error("判断是否收卷程序错误，该功能失效");
			e.printStackTrace();
		}
		//判断  table_student_answer 中有无相应记录 若有记录则跳出该考试
		boolean isExistAnswer = false;
		isExistAnswer = studentAnswerService.isExistAnswer(studentAnswer);
		if(isExistAnswer){
			try {
				response.getWriter().write("isAnswer");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!flag){
			studentAnswerService.answerQuestion(studentAnswer);
		}else{
			try {
				response.getWriter().write("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "submitAnswer")
	public String submitAnswer(Model model,RedirectAttributes redirectAttributes,String examId,String examDetailId,HttpServletRequest request,String returnUrl,String examType){
		//验证是否已经提交
		boolean isSubmit = sExamService.isSubmit(StudentUserUtils.getUser().getId(), examId, null);
		if(isSubmit){
			addMessage(redirectAttributes, "您已参加过本次考试，请勿重复参加");
			return "redirect:"+studentPath+"?answerFlag=0";
		}
		
		StudentTask studentTask = new StudentTask();
		model.addAttribute("user", StudentUserUtils.getUser());
		StudentExamDetail examDetail = new StudentExamDetail();
		examDetail.setId(examDetailId);
		studentTask.setExamDetail(examDetail);
		studentTask.setExamid(examId);
		studentTask.setStudentId(StudentUserUtils.getUser().getId());
		studentAnswerService.updateStudentTask(studentTask);
		addMessage(redirectAttributes, "提交试卷成功");
		if(StringUtils.isBlank(examType)){
			return "redirect:" + studentPath;
		}
		return "redirect:" + studentPath;
		
	}
}
