package com.thinkgem.jeesite.modules.student.mobile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentExamDetail;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.mobile.domain.QuestionVo;
import com.thinkgem.jeesite.modules.student.mobile.domain.StudentVo;
import com.thinkgem.jeesite.modules.student.mobile.service.MobileStudentService;
import com.thinkgem.jeesite.modules.student.service.SExamService;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.student.utils.FastJsonUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 移动端接口
 * 
 * @author Lenovo
 * 
 */
@Controller
@RequestMapping(value = "${studentPath}/mobile/")
public class MobileStudentController extends BaseController {

	@Autowired
	MobileStudentService mobileStudentService;
	@Autowired
	private SExamService sExamService;
	@Autowired
	private StudentAnswerService studentAnswerService;
	
	@ResponseBody
	@RequestMapping("getAllSchool")
	public String getAllSchool(){
		List<Map<String, String>> list = mobileStudentService.getAllSchool();
		return FastJsonUtils.addSMA(1, "", list);
	}

	/**
	 * 学生手机端登录
	 * 需要验证	学号、密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public String login(String stdCode, String stdPassword,String schoolId) {
		if(!StringUtils.isBlank(stdCode) && !StringUtils.isBlank(stdPassword)){
			StudentVo loginUser = new StudentVo();
			loginUser.setStdCode(stdCode);
			loginUser.setSchoolId(schoolId);
			//根据学号查询学生  返回的状态码 1：登录成功 2：学生不错在 3：密码错误
			StudentVo reStudent = mobileStudentService.getStudentByCode(loginUser);
			Date now = new Date();
			if(null == reStudent){
				//学生不存在
				return FastJsonUtils.addSMA(2,"学生不存在", null);
			}else if(reStudent.getEndDate().getTime() < now.getTime()){
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}else if(!SystemService.validatePassword(stdPassword, reStudent.getStdPassword())){
				//密码错误
				return FastJsonUtils.addSMA(3,"密码错误", null);
			}else{
				//登录成功
				List<StudentVo> list = new ArrayList<StudentVo>();
				list.add(reStudent);
				return FastJsonUtils.addSMA(1,"登录成功", list);
			}
		}else{
			return FastJsonUtils.addSMA(0, "传入参数有空值", null);
		}
	}
	
	/**
	 * 用户是否存在
	 * 
	 */
	public boolean isExistStudent(String studentId){
		StudentVo reStudent = mobileStudentService.isExistStudent(studentId);
		if(null == reStudent){
			//学生不存在
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 学生登录后进入的主页面下拉列表 
	 * 学生所学课程的id与名称
	 * @param StdId
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping("getCoursesByStdId")
	public String getCoursesByStdId(String studentId){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				List<Map<String, String>> courseList = mobileStudentService.getCoursesByStdId(studentId);
				return FastJsonUtils.addSMA(1,"", courseList);
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	/**
	 * 根据课程和测试类型和学生id  获取对应的测试简要信息	返回的考试状态码为：1：已经参加了的考试	2：没有参加的考试：已经错过了		3：没有参加的考试：没有错过	正常的考试
	 * 分页参数：page:当前页为第几页	pageSize:当前页的记录数
	 * @param examType
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getExamsByTypeAndCourse")
	public String getExamsByTypeAndCourse(String examType,String courseId,String studentId,String page,String pageSize){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				List<Map<String, Object>> examBaseList = mobileStudentService.getExamsByTypeAndCourse(examType,courseId,studentId,page,pageSize);
				return FastJsonUtils.addSMA(1,"", examBaseList);
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	
	/**
	 * 根据学生id和考试id和我的考试状态码  获取该测试的所有试题	1：已经参加了的考试	2：没有参加的考试：已经错过了		3：没有参加的考试：没有错过 	正常的考试
	 * 返回的结果	当考试状态码为	1:试卷 + 答案 + 讲解	+ 我的答案 	2：可以查看 验证公布答案的时间	3：试卷
	 * @param studentId
	 * @param examId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getExamQuestions")
	public String getExamQuestions (String studentId,String examId,String examStatus,Model model){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				if("2".equals(examStatus)){
					List<QuestionVo> testPaperList = mobileStudentService.getMissedTestPaper(examId);
					return FastJsonUtils.addSMA(1,"", testPaperList);
				}else if("3".equals(examStatus)){
					//判断该试卷是否已经提交
					boolean isSubmit = sExamService.isSubmit(studentId,examId,null);
					if(isSubmit){
						return FastJsonUtils.addSMA(111, "该考试已提交答案，请勿重复提交！", null);
					}
					//添加学生任务
					//根据测试id 查询测试对象
					try{
						SExam exam = sExamService.getSExamEntityById(examId);
						//根据测试id 查询测试详情id
						List<String> examDetailIdList = sExamService.getExamDetailIdByExamId(examId);
						String examDetailId = "";
						if(null == examDetailIdList || examDetailIdList.isEmpty()){
							addMessage(model, "数据异常，table_exam_detail");
							return "student/pages/index";
						}else{
							if(examDetailIdList.size() == 1){
								examDetailId = examDetailIdList.get(0);
								model.addAttribute("examDetailId", examDetailId);
								//根据学生和考试详情查询学生任务ID
								boolean flag = getStudentTaskIdByStudentIdAndExamDetailId(examDetailId,studentId);
								if(!flag){
									studentAnswerService.addStudentTask(examDetailId,exam,studentId);
								}
							}else if(examDetailIdList.size() == 2){
								String examDetailId0 = examDetailIdList.get(0);
								String examDetailId1 = examDetailIdList.get(1);
								//有两条数据说明是ab卷
								// 判断是否要添加学生任务
								if(getStudentTaskIdByStudentIdAndExamDetailId(examDetailId0,studentId) || getStudentTaskIdByStudentIdAndExamDetailId(examDetailId1,studentId)){
									//根据学生id  和  考试详情id 查询exam_detail_id
									String reExamDetailId = studentAnswerService.getExamDetailIdByExamDeatilId(examDetailId0,studentId);
									if(StringUtils.isNotBlank(reExamDetailId)){
										examDetailId = reExamDetailId;
									}
									
									String reExamDetailId1 = studentAnswerService.getExamDetailIdByExamDeatilId(examDetailId1,studentId);
									if(StringUtils.isNotBlank(reExamDetailId1)){
										examDetailId = reExamDetailId1;
									}
									
								}else{
									Random random = new Random();
									examDetailId = examDetailIdList.get(random.nextInt(examDetailIdList.size()));
									model.addAttribute("examDetailId", examDetailId);
									studentAnswerService.addStudentTask(examDetailId,exam,studentId);
								}
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					List<QuestionVo> testPaperList = mobileStudentService.getTestPaper(studentId,examId);
					String addSMA = FastJsonUtils.addSMA(1,"", testPaperList);
					return addSMA;
				}else if("1".equals(examStatus)){
					List<QuestionVo> testPaperList = mobileStudentService.getTestPaperAllMessage(studentId,examId);
					List<Map<String, String>> isCorrentList = new ArrayList<Map<String,String>>();
					for (QuestionVo question : testPaperList) {
						String questionId = question.getId();
						String isCorrent = question.getIsCorrent();
						Map<String, String> map = new HashMap<String, String>();
						map.put("questionId", questionId);
						map.put("isCorrent", isCorrent);
						isCorrentList.add(map);
					}
					//根据学生id 和考试id 查询某试卷教师是否判卷
					boolean isGradeTestPaper = mobileStudentService.isGradeTestPaper(studentId,examId);
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("status", 1+"");
					jsonObject.put("messaeg", "");
					JSONArray array = (JSONArray) JSON.toJSON(testPaperList);
					jsonObject.put("data", array);
					JSONArray array1 = (JSONArray) JSON.toJSON(isCorrentList);
					if(isGradeTestPaper){
						jsonObject.put("isCorrentList", array1);
					}else{
						List arrayList = new ArrayList();
						array = (JSONArray) JSON.toJSON(arrayList);
						jsonObject.put("isCorrentList", array);
					}
					String string = jsonObject.toString();
					return string;
				}else{
					return FastJsonUtils.addSMA(0,"考试状态码错误", null);
				}
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	public boolean getStudentTaskIdByStudentIdAndExamDetailId(String examDetailId){
		return studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailId(examDetailId,null);
	}
	
	/**
	 * 根据学生ID和考试详情查询学生任务ID
	 * @param examDetailId
	 * @return
	 */
	public boolean getStudentTaskIdByStudentIdAndExamDetailId(String examDetailId,String studentId){
		return studentAnswerService.getStudentTaskIdByStudentIdAndExamDetailId(examDetailId,studentId);
	}
	
	/**
	 * 提交试卷
	 * @param a
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submitExam")
	public String insertStudentTaskAndAnswer(@RequestBody String studentAnswer){
		try{
			//studentAnswer = "{\"student_id\":\"1qasdfgh\",\"exam_id\":\"rsedsfghj\",\"exam_detail_id\":\"rsedsfghj\",\"startTime\":\"2016-09-09 12:12:00\",\"data\":[{\"question_id\":\"jhhjk\",\"answer\":\"jjhjk\",\"quesType\":\"1\"}]}";
			JSONObject jsonObjcet = (JSONObject)JSONObject.parse(studentAnswer);
			String examId0 = jsonObjcet.getString("exam_id");
			String studentId0 = jsonObjcet.getString("student_id");
			boolean isSubmit = sExamService.isSubmit(studentId0,examId0,null);
			if(isSubmit){
				return FastJsonUtils.addSMA(111, "该考试已提交答案，请勿重复提交！", null);
			}
			//判断单选题  多选题 判断题 对错
			//根据exam_detail_id 获取该场考试的问题id与正确答案
			List<Map<String, String>> questionIdAndAnswerList = mobileStudentService.getQuestionIdAndAnswerByExamDetilId(jsonObjcet.getString("exam_detail_id"));
			String studentId = jsonObjcet.getString("student_id");
			if(isExistStudent(studentId)){
				if(!mobileStudentService.isGraduate(studentId)){
					//1、提交答案
					List<StudentAnswer> list = new ArrayList<StudentAnswer>();
					String substringBetween = "[" + StringUtils.substringBetween(studentAnswer, "[", "]") + "]";
					JSONArray jsonArray = JSONArray.parseArray(substringBetween);
					if(jsonArray.size() > 0){
						for(int i = 0;i<jsonArray.size();i++){
							JSONObject jobj =  (JSONObject) jsonArray.get(i);
							String question_id = (String)jobj.get("question_id");
							String answer = (String)jobj.get("answer");
							String quesType = (String)jobj.get("quesType");
							String answerImg = (String)jobj.get("answerImg");
							if("2".equals(quesType)){
								StudentAnswer studentAnswer1 = new StudentAnswer(); 
							    studentAnswer1.setId(IdGen.uuid());
							    studentAnswer1.setDelFlag("0");
								studentAnswer1.setCreateDate(new Date());
								studentAnswer1.setUpdateDate(new Date());
								studentAnswer1.setStudentId(jsonObjcet.getString("student_id"));
								studentAnswer1.setExamId(jsonObjcet.getString("exam_id"));
								studentAnswer1.setQuestionId(question_id);
								studentAnswer1.setQuestionType(quesType);
								studentAnswer1.setAnswerImg(answerImg);
								String[] split = answer.split("\\*,\\*");
								String[] copyOf = Arrays.copyOf(split, 10);
								studentAnswer1.setAnswer0(copyOf[0]);
								studentAnswer1.setAnswer1(copyOf[1]);
								studentAnswer1.setAnswer2(copyOf[2]);
								studentAnswer1.setAnswer3(copyOf[3]);
								studentAnswer1.setAnswer4(copyOf[4]);
								studentAnswer1.setAnswer5(copyOf[5]);
								studentAnswer1.setAnswer6(copyOf[6]);
								studentAnswer1.setAnswer7(copyOf[7]);
								studentAnswer1.setAnswer8(copyOf[8]);
								studentAnswer1.setAnswer9(copyOf[9]);
								list.add(studentAnswer1);
							}else{
								StudentAnswer studentAnswer1 = new StudentAnswer(); 
							    studentAnswer1.setId(IdGen.uuid());
							    studentAnswer1.setDelFlag("0");
								studentAnswer1.setCreateDate(new Date());
								studentAnswer1.setUpdateDate(new Date());
								studentAnswer1.setStudentId(jsonObjcet.getString("student_id"));
								studentAnswer1.setExamId(jsonObjcet.getString("exam_id"));
								studentAnswer1.setQuestionId(question_id);
								studentAnswer1.setQuestionType(quesType);
								if(StringUtils.isNotBlank(answerImg)){
									if(StringUtils.isBlank(answer)){
										answer = "";
									}
									studentAnswer1.setAnswer0(answer + "<img src=\""+answerImg+"\"/>");
								}else{
									studentAnswer1.setAnswer0(answer);
								}
								for (Map<String, String> map : questionIdAndAnswerList) {
									if(map.get("id").equals(question_id)){
										String rigthAnswer = map.get("answer");
										if(quesType.equals("1")){
											if(rigthAnswer.equals(answer)){
												studentAnswer1.setIsCorrect(1);
												studentAnswer1.setScore(Float.valueOf("1"));
											}else{
												studentAnswer1.setIsCorrect(0);
												studentAnswer1.setScore(null);
											}
										}
										if(quesType.equals("5")){
											if(rigthAnswer.equals(answer)){
												studentAnswer1.setIsCorrect(2);
												studentAnswer1.setScore(Float.valueOf("1"));
											}else{
												studentAnswer1.setIsCorrect(0);
												studentAnswer1.setScore(null);
											}
										}
										if(quesType.equals("11")){
											if(StringUtils.isNotBlank(answer)){
												if(answer.equals("正确")){
													answer = "1";
												}else{
													answer = "2";
												}
												if(answer.equals(rigthAnswer)){
													studentAnswer1.setIsCorrect(1);
													studentAnswer1.setScore(Float.valueOf("1"));
												}else{
													studentAnswer1.setIsCorrect(0);
													studentAnswer1.setScore(null);
												}
											}
										}
									}
								}
								list.add(studentAnswer1);
							}
						}
						mobileStudentService.addStudentAnswerList(list);
					}
					//2、更改学生任务
					StudentTask studentTask = new StudentTask();
					StudentExamDetail examDetail = new StudentExamDetail();
					examDetail.setId(jsonObjcet.getString("exam_detail_id"));
					studentTask.setExamDetail(examDetail);
					studentTask.setSubmittime(new Date());
					studentTask.setStudentId(jsonObjcet.getString("student_id"));
					studentTask.setExamId(jsonObjcet.getString("exam_id"));
					mobileStudentService.updateMobileStudentTask(studentTask);
					return FastJsonUtils.addSMA(1,"提交成功", null);
				}else{
					return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
				}
			}else{
				return FastJsonUtils.addSMA(110, "学生不存在", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return FastJsonUtils.addSMA(0,"服务器内部错误", null);
		}
	}
	
	
	@ResponseBody
	@RequestMapping("getQuestionLibQuesTypesAndQuesCounts")
	public String getQuestionLibQuesTypesAndQuesCounts(String studentId,String versionId){
		Date date = new Date();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("=======================================");
		System.out.println("getQuestionLibQuesTypesAndQuesCounts====接收到请求的时间=====" + date.toString() + "学生id==="+ studentId + "版本id====" + versionId);
		logger.info("getQuestionLibQuesTypesAndQuesCounts====接收到请求的时间=====" + date.toString() + "学生id==="+ studentId + "版本id====" + versionId);
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				String addSMA = FastJsonUtils.addSMA(1, "", mobileStudentService.getQuestionLibQuesTypesAndQuesCounts(studentId,versionId));
				Date date2 = new Date();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println("getQuestionLibQuesTypesAndQuesCounts===请求结束返回数据的时间=====" + date2.toString() + "返回的数据=====" + addSMA);
				logger.info("getQuestionLibQuesTypesAndQuesCounts===请求结束返回数据的时间=====" + date2.toString() + "返回的数据=====" + addSMA);
				return addSMA;
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	
	/**
	 * 我的题库
	 * @param studentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMyQuestionLib")
	public String getMyQuestionLib(String studentId,String versionId,String page,String pageSize,String questionTypeId){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				String addSMA = FastJsonUtils.addSMA(1,"", mobileStudentService.getMyQuestionLib(questionTypeId,studentId,versionId,page,pageSize));
				return addSMA;
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("browseQuestion")
	public String browseQuestion(String studentId,String questionId,String versionId){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				mobileStudentService.browseQuestion(studentId,questionId,versionId);
				return FastJsonUtils.addSMA(1, "", null);
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
		
	}
	
	//测试类别
	@ResponseBody
	@RequestMapping("getExamType")
	public String getExamType(Model model,HttpServletRequest request,HttpServletResponse response){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String,String>();
		map.put("examType", "1");
		map.put("examName", "随堂练习");
		
		Map<String, String> map1 = new HashMap<String,String>();
		map1.put("examType", "3");
		map1.put("examName", "课后作业");
		
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("examType", "5");
		map2.put("examName", "在线考试");
		list.add(map);
		list.add(map1);
		list.add(map2);
		return FastJsonUtils.addSMA(1, "", list);
	}
	
	/**
	 * 错题集中不同类型试题的数量
	 * 
	 */
	@ResponseBody
	@RequestMapping("getWrongQuestionLibQuesTypeAndQuesCount")
	public String getWrongQuestionLibQuesTypeAndQuesCount(String studentId,String examType,String versionId){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				return FastJsonUtils.addSMA(1, "", mobileStudentService.getWrongQuestionLibQuesTypeAndQuesCount(studentId,examType,versionId));
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	
	/**
	 * 我做错了的题目
	 * @param studentId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWrongQuestionLib")
	public String getWrongQuestionLib(String studentId,String page,String pageSize,String examType,String questionTypeId,String versionId){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				return FastJsonUtils.addSMA(1,"", mobileStudentService.getWrongQuestionLib(questionTypeId,studentId,examType,page,pageSize,versionId));
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	/**
	 * 获取我的评价
	 * @param studentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMyComment")
	public String getMyComment(String studentId, String page,String pageSize){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				return FastJsonUtils.addSMA(1,"", mobileStudentService.getMyComment(studentId,page,pageSize));
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	//修改密码
	@ResponseBody
	@RequestMapping("updatePassword")
	public String updatePassword(String studentId,String oldPassword,String newPassword){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				//判断旧密码是否正确
				StudentVo studentVo = mobileStudentService.getStudentById(studentId);
				if(SystemService.validatePassword(oldPassword, studentVo.getStdPassword())){
					//判断新旧密码是否相同
					if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
						return FastJsonUtils.addSMA(0, "新旧密码均不能为空", null);
					}
					if(oldPassword.equals(newPassword)){
						return FastJsonUtils.addSMA(3, "新密码与旧密码相同", null);
					}
					mobileStudentService.updatePassword(studentId, SystemService.entryptPassword(newPassword));
					return FastJsonUtils.addSMA(1,"更改密码成功", null);
				}else{
					return FastJsonUtils.addSMA(2,"旧密码填写错误", null);
				}
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	//问答 获取问题列表
	@ResponseBody
	@RequestMapping("getQuestionList")
	public String getQuestionList(String studentId,String page,String pageSize){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				return FastJsonUtils.addSMA(1, "",  mobileStudentService.getQuestionList(studentId,page,pageSize));
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
		
	}
	
	//问答 获取某问题的回答列表
	@ResponseBody
	@RequestMapping("getAnswerList")
	public String getAnswerList(String studentId,String questionId,String page,String pageSize){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				return FastJsonUtils.addSMA(1, "", mobileStudentService.getAnswerList(questionId,page,pageSize));
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	//问答 学生发起问答
	@ResponseBody
	@RequestMapping("addQuestion")
	public String addQuestion(String studentId,String questionTitle){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				mobileStudentService.addQuestion(studentId,questionTitle);
				return FastJsonUtils.addSMA(1, "发起互问互答成功，等待审核", null);
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
			
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	//问答  学生提交问答
	@ResponseBody
	@RequestMapping("submitAnswer")
	public String submitAnswer(String studentId,String questionId,String answerMsg){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				mobileStudentService.submitAnswer(studentId,questionId,answerMsg);
				return FastJsonUtils.addSMA(1, "提交问答成功，等待审核", null);
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
	}
	
	/**
	 * 手机拍照上传图片
	 * 
	 * @param file	上传的文件
	 * @param studentId	答题的学生id
	 * @param examId 考试的id
	 * @param questionId 回答的问题id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mobileUploadPicture")
	public String mobileUploadPicture(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile uploadFile,String studentId,String examId,String questionId,String questionType){
		if(isExistStudent(studentId)){
			if(!mobileStudentService.isGraduate(studentId)){
				String httpUrl = "";
				//将当前上下文环境初始化为 CommonsMutipartResolver (多部分解析器)
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
				//检查form表单中是否有 enctype="multipart/form-data"
				try {
					if(multipartResolver.isMultipart(request)){
						//将request变成多部分request
						MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
						Iterator<String> iter = multiRequest.getFileNames();
						while (iter.hasNext()) {
							//一次遍历所有的文件
							MultipartFile file = multiRequest.getFile(iter.next().toString());
							if(null != file){
								String realPath = request.getSession().getServletContext().getRealPath("/");
								Calendar cal = Calendar.getInstance();
								String path = realPath + "userfiles"+File.separator + studentId +File.separator 
											+ "images"+File.separator+"questionlib"+File.separator+"answer"+File.separator 
											+ cal.get(Calendar.YEAR) +"-"+ cal.get(Calendar.MONTH)+File.separator + examId;
								File dir = new File(path);
								if(!dir.exists()){
									dir.mkdirs();
								}
								//上传
								String originalFilename = file.getOriginalFilename();
								System.out.println(originalFilename);
								file.transferTo(new File(path + "/" + questionId + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".")));
								httpUrl = "/userfiles/" + studentId + "/images/questionlib/answer/" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH)+ "/" + examId + "/" +  questionId + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
							}
						}
						List<Map<String, String>> list = new ArrayList<Map<String, String>>();
						Map<String, String> map = new HashMap<String,String>();
						map.put("fileurl", httpUrl);	//文件的相对路径
						map.put("success", "1");	//文件上传成功
						list.add(map);
						String addSMA = FastJsonUtils.addSMA(1,"文件上传成功", list);
						return addSMA;
					}else{
						return FastJsonUtils.addSMA(0,"", null);//form表单中没有 enctype=\"multipart/form-data\"
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return FastJsonUtils.addSMA(-1,"服务器内部错误", null);
				} catch (IOException e) {
					e.printStackTrace();
					return FastJsonUtils.addSMA(-1,"服务器内部错误", null);
				}
			}else{
				return FastJsonUtils.addSMA(4, "您所在班级已毕业", null);
			}
		}else{
			return FastJsonUtils.addSMA(110, "学生不存在", null);
		}
		
	}
	
}