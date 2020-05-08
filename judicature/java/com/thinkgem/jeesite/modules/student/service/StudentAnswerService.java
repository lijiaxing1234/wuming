package com.thinkgem.jeesite.modules.student.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.VersionQuestionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.dao.StudentAnswerDao;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentExamDetail;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

@Service
@Transactional(readOnly = true)
public class StudentAnswerService {
	
	@Autowired
	private StudentAnswerDao dao;
	@Autowired
	private VersionQuestionDao versionQuestionDao;

	/**
	 * 为学生添加任务
	 * 从首页进入到答题页面
	 * 学生开始答题table_student_task 添加记录
	 * @param examDetailId
	 * @param exam
	 * @param studentId2 
	 */
	@Transactional(readOnly = false)
	public void addStudentTask(String examDetailId, SExam exam, String studentId2) {
		String studentId = "";
		StudentTask task = new StudentTask();
		task.setId(IdGen.uuid());
		if(null == studentId2){
			studentId  = StudentUserUtils.getUser().getId();
		}else{
			studentId = studentId2;
		}
		
		task.setStudent(new Student(studentId));
		task.setTotalfraction(exam.getTotalScore());
		task.setStarttime(new Date());
		StudentExamDetail examDetail = new StudentExamDetail();
		examDetail.setId(examDetailId);
		task.setExamDetail(examDetail);
		dao.addStudentTask(task);
	}

	/**
	 * 根据测试详情id和题目类型 获取测试题目
	 * @param examDetailId
	 * @param quesType
	 * @return
	 */
	public List<VersionQuestion> getQuestionListByExamDetailIdAndQuesType2(String examId, String quesType) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("examId", examId);
		paraMap.put("quesType", quesType);
		List<VersionQuestion> questionListByExamDetailIdAndQuesType = dao.getQuestionListByExamDetailIdAndQuesType2(paraMap);
		return questionListByExamDetailIdAndQuesType;
	}
	
	
	public List<VersionQuestion> getQuestionListByExamDetailIdAndQuesType(String examDetailId, String quesType) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("examDetailId", examDetailId);
		paraMap.put("quesType", quesType);
		List<VersionQuestion> questionListByExamDetailIdAndQuesType = dao.getQuestionListByExamDetailIdAndQuesType(paraMap);
		return questionListByExamDetailIdAndQuesType;
	}
	
	/**
	 * 查询在线开始试卷
	 * @param examDetailId  A或B 卷 唯一标识
	 * @param quesType    
	 * @param examId
	 * @return  List<VersionQuestion>  考试试题和当前登录学生的答案
	 */
	public List<VersionQuestion> getQuestionListByExamDetailIdAndQuesType(String examDetailId, String quesType,String examId) {
		List<VersionQuestion> questionListByExamDetailIdAndQuesType =getQuestionListByExamDetailIdAndQuesType(examDetailId,quesType);
		
		List<VersionQuestion> list = new ArrayList<VersionQuestion>();
		for (VersionQuestion versionQuestion : questionListByExamDetailIdAndQuesType) {
			if(!list.contains(versionQuestion)){
				list.add(versionQuestion);
			}
		}
		
		
		//绑定答案
		String studentId = StudentUserUtils.getUser().getId();
		StudentAnswer studentAnswer =null;
		
		for(VersionQuestion versionQuestion:list){
			
			studentAnswer=new StudentAnswer();
			studentAnswer.setExamId(examId);
			studentAnswer.setStudentId(studentId);
			studentAnswer.setQuestionType(quesType);
			studentAnswer.setQuestionId(versionQuestion.getId());
			
			List<StudentAnswer>  studentAnswers=dao.getStudentAnswerByExample(studentAnswer);
			if(studentAnswers!=null&&studentAnswers.size()>0){
				versionQuestion.setStudentAnswer(studentAnswers.get(0));
			}
		}
		
		return questionListByExamDetailIdAndQuesType;
	}

	/**
	 * ajax异步提交问题的答案
	 * @param studentAnswer
	 */
	@Transactional(readOnly = false)
	public synchronized void  answerQuestion(StudentAnswer studentAnswer) {
		String mhtmlAnswer = studentAnswer.getAnswer0();
		studentAnswer.setAnswer0(Encodes.unescapeHtml(mhtmlAnswer));
		
		String studentId = StudentUserUtils.getUser().getId();
		studentAnswer.setStudentId(studentId);
		StudentAnswer reObj = dao.isAnswered(studentAnswer);
		String questionType = studentAnswer.getQuestionType();
		//通过题目id获取题目的详细信息
		String questionId = studentAnswer.getQuestionId();
		VersionQuestion versionQuestion = versionQuestionDao.get(questionId);
		//判断是否为多选题
		if("5".equals(questionType)){
			//该多选题的答案，选项用逗号分割
			String mAnswer = studentAnswer.getAnswer0();
			if(null == reObj){
				studentAnswer.setId(IdGen.uuid());
				//设置每个答案
				studentAnswer = setAnswers(studentAnswer,mAnswer);
				dao.insertAnswer(studentAnswer);
			}else{
				studentAnswer.setId(reObj.getId());
				//设置每个答案
				studentAnswer = setAnswers(studentAnswer,mAnswer);
				dao.updateAnswer(studentAnswer);
			}
			try{
				//多选题 有一个选项错误就不得分
				if("5".equals(questionType)){
					System.err.println("进入多选题判断正误");
					String vAnswer0 = versionQuestion.getAnswer0();
					String sAnswer0 = studentAnswer.getAnswer0();
					String sAnswer1 = studentAnswer.getAnswer1();
					String sAnswer2 = studentAnswer.getAnswer2();
					String sAnswer3 = studentAnswer.getAnswer3();
					String sAnswer4 = studentAnswer.getAnswer4();
					String sAnswer5 = studentAnswer.getAnswer5();
					String sAnswer6 = studentAnswer.getAnswer6();
					String sAnswer7 = studentAnswer.getAnswer7();
					String sAnswer8 = studentAnswer.getAnswer8();
					String sAnswer9 = studentAnswer.getAnswer9();
					String sAnswer = sAnswer0 + sAnswer1 + sAnswer2 + sAnswer3 + sAnswer4 + sAnswer5 + sAnswer6 + sAnswer7 + sAnswer8 + sAnswer9;
					sAnswer = sAnswer.replace("null", "");
					vAnswer0 = StringUtils.upperCase(vAnswer0);
					sAnswer = StringUtils.upperCase(sAnswer);
					if(anagram(vAnswer0,sAnswer)){
						studentAnswer.setIsCorrect(1);
						studentAnswer.setScore((float)2);
						dao.updateAnswrByCorrentOrNot(studentAnswer);
					}else{
						studentAnswer.setIsCorrect(0);
						studentAnswer.setScore(null);
						dao.updateAnswrByCorrentOrNot(studentAnswer);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.toString());
			}
			
		}else if("2".equals(questionType)){
			String htmlAnswer = studentAnswer.getAnswer0();
			String orderNo = studentAnswer.getOrderNo();
			
			if(null == reObj){
				studentAnswer.setId(IdGen.uuid());
				studentAnswer = setTianKongAnswer(studentAnswer,htmlAnswer,orderNo);
				dao.insertAnswer(studentAnswer);
			}else{
				studentAnswer.setId(reObj.getId());
				studentAnswer = setTianKongAnswer(studentAnswer,htmlAnswer,orderNo);
				dao.updateAnswer(studentAnswer);
			}
		}else if("11".equals(questionType)){
			String vAnswer0 = versionQuestion.getAnswer0();
			String answer0 = studentAnswer.getAnswer0();
			if("0".equals(answer0)){
				studentAnswer.setAnswer0("2");
			}else if("1".equals(answer0)){
				studentAnswer.setAnswer0("1");
			}
			if(null == reObj){
				studentAnswer.setId(IdGen.uuid());
				dao.insertAnswer(studentAnswer);
			}else{
				studentAnswer.setId(reObj.getId());
				dao.updateAnswer(studentAnswer);
			}
			//判断正误
			if(vAnswer0.equals(studentAnswer.getAnswer0())){
				studentAnswer.setIsCorrect(1);
				studentAnswer.setScore((float)1);
				dao.updateAnswrByCorrentOrNot(studentAnswer);
			}else{
				studentAnswer.setIsCorrect(0);
				studentAnswer.setScore(null);
				dao.updateAnswrByCorrentOrNot(studentAnswer);
			}
		}else{
			StudentAnswer reObj2= dao.isAnswered(studentAnswer);
			if(null == reObj2){
				studentAnswer.setId(IdGen.uuid());
				dao.insertAnswer(studentAnswer);
			}else{
				studentAnswer.setId(reObj.getId());
				dao.updateAnswer(studentAnswer);
			}
			//判断正误
			try{
				//单选题 quesType = 1
				if("1".equals(questionType)){
					System.err.println("进入单选题判断正误");
					//通过比较获取正确答案  若答对则返回正确答案，否则结果集为空
					String correntAnswer = dao.getCorrectAnswerByCompare(studentAnswer);
					if(null != correntAnswer){
						//正确	修改为正确   修改题目得分
						studentAnswer.setIsCorrect(1);
						studentAnswer.setScore((float)1);
						dao.updateAnswrByCorrentOrNot(studentAnswer);
					}else{
						//错误	修改为错误	修改题目得分
						studentAnswer.setIsCorrect(0);
						studentAnswer.setScore(null);
						dao.updateAnswrByCorrentOrNot(studentAnswer);
					}
				}
				//计算题 填空题 简答题无法判断
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private boolean anagram(String s, String t) {
		// 若两个字符串的长度不相等直接返回false
		if (s.length() != t.length()) {
			return false;
		}
		// 定义一个存储字符ASSIC值的数组
		int[] count = new int[256];
		for (int i = 0; i < s.length(); i++) {
			// 遍历字符串，将每一个字符的ASSIC码值作为数组下标的索引，并将对应的数组值+1
			count[(int) s.charAt(i)]++;
		}
		for (int j = 0; j < t.length(); j++) {
			count[(int) t.charAt(j)]--;
			if (count[(int) t.charAt(j)] < 0) {
				// 若存在任一对应位置的的值小于0，返回false
				return false;
			}
		}
		return true;
	}

	private StudentAnswer setTianKongAnswer(StudentAnswer studentAnswer, String htmlAnswer, String orderNo) {
		if(null == htmlAnswer || "".equals(htmlAnswer)){
			studentAnswer.setAnswer0(null);
			return studentAnswer;
		}else{
			studentAnswer.setAnswer0(null);
			String[] newAnswerVlaArr = htmlAnswer.split(",");
			String[] answerValArr = Arrays.copyOf(newAnswerVlaArr, 10);
			
			for(int i = 0; i <= 9 ;i++){
				if(0 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer0(null);
					}else{
						studentAnswer.setAnswer0(answerValArr[i]);
					}
				}else if(1 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer1(null);
					}else{
						studentAnswer.setAnswer1(answerValArr[i]);
						
					}
				}else if(2 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer2(null);
					}else{
						studentAnswer.setAnswer2(answerValArr[i]);
					}
				}else if(3 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer3(null);
					}else{
						studentAnswer.setAnswer3(answerValArr[i]);
					}
				}else if(4 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer4(null);
					}else{
						studentAnswer.setAnswer4(answerValArr[i]);
					}
				}else if(5 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer5(null);
					}else{
						studentAnswer.setAnswer5(answerValArr[i]);
					}
				}else if(6 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer6(null);
					}else{
						studentAnswer.setAnswer6(answerValArr[i]);
					}
				}else if(7 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer7(null);
					}else{
						studentAnswer.setAnswer7(answerValArr[i]);
					}
				}else if(8 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer8(null);
					}else{
						studentAnswer.setAnswer8(answerValArr[i]);
					}
				}else if(9 == i){
					if(null == answerValArr[i] || " ".equals(answerValArr[i])){
						studentAnswer.setAnswer9(null);
					}else{
						studentAnswer.setAnswer9(answerValArr[i]);
					}
				}
			}
			return studentAnswer;
		}
	}

	private StudentAnswer setAnswers(StudentAnswer studentAnswer, String mAnswer) {
		if(null == mAnswer || "".equals(mAnswer)){
			studentAnswer.setAnswer0(null);
			return studentAnswer;
		}else{
			studentAnswer.setAnswer0(null);
			String[] answerArr = mAnswer.split(",");
			for (String answer : answerArr) {
				if("A".equals(answer)){
					studentAnswer.setAnswer0("A");
				}else if ("B".equals(answer)){
					studentAnswer.setAnswer1("B");
				}else if ("C".equals(answer)){
					studentAnswer.setAnswer2("C");
				}else if ("D".equals(answer)){
					studentAnswer.setAnswer3("D");
				}else if ("E".equals(answer)){
					studentAnswer.setAnswer4("E");
				}else if ("F".equals(answer)){
					studentAnswer.setAnswer5("F");
				}else if ("G".equals(answer)){
					studentAnswer.setAnswer6("G");
				}else if ("H".equals(answer)){
					studentAnswer.setAnswer7("H");
				}else if ("I".equals(answer)){
					studentAnswer.setAnswer8("I");
				}else if ("J".equals(answer)){
					studentAnswer.setAnswer9("J");
				}
			}
			return studentAnswer;
		}
	}

	/**
	 * 学生提交试卷
	 * 更新学生任务表中的数据
	 * @param studentTask
	 */
	@Transactional(readOnly = false)
	public void updateStudentTask(StudentTask studentTask) {
		studentTask.setSubmittime(new Date());
		studentTask.setStudent(new Student(StudentUserUtils.getUser().getId()));
		dao.updateStudentTask(studentTask);
	}
	
	public boolean getStudentTaskIdByStudentIdAndExamDetailId(String examDetailId, String rStudentId) {
		String studentId = "";
		if(null == rStudentId){
			studentId = StudentUserUtils.getUser().getId();
		}else{
			studentId = rStudentId;
		}
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examDetailId", examDetailId);
		String studentTaskId = dao.getStudentTaskIdByStudentIdAndExamDetailId(paraMap);
		if(null == studentTaskId || "".equals(studentTaskId)){
			return false;
		}else{
			return true;
		}
	}

	public List<VersionQuestion> getQuestionListByExamIdAndQuestionType(String examId, String questionType) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		paraMap.put("quesType", questionType);
		return dao.getQuestionList(paraMap);
	}

	public List<VersionQuestion> getExampleQuestionList(String examDetailId) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examDetailId", examDetailId);
		return dao.getExampleQuestionList(paraMap);
	}

	public List<VersionQuestion> getMissQuestions(String examId, String questionType) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("examId", examId);
		paraMap.put("quesType", questionType);
		return dao.getMissQuestions(paraMap);
	}

	public boolean getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(String examDetailId) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examDetailId", examDetailId);
		String studentTaskId = dao.getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(paraMap);
		if(null == studentTaskId || "".equals(studentTaskId)){
			return false;
		}else{
			return true;
		}
	}

	public boolean isTakeUpTestPaper(StudentAnswer studentAnswer) {
		String studentId = StudentUserUtils.getUser().getId();
		studentAnswer.setStudentId(studentId);
		//根据学生id和考试id 查询该考试是否已经收卷
		String status = dao.getTestPaperStatus(studentAnswer);
		if("2".equals(status)){
			return true;
		}else{
			return false;
		}
	}

	public String getExamDetailIdByExamDeatilId(String examDetailId) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examDetailId", examDetailId);
		return dao.getExamDetailIdByExamDeatilId(paraMap);
	}

	public String getExamDetailIdByExamDeatilId(String examDetailId, String studentId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examDetailId", examDetailId);
		return dao.getExamDetailIdByExamDeatilId(paraMap);
	}

	public boolean isExistAnswer(StudentAnswer studentAnswer) {
		String id = dao.isExistAnswer(studentAnswer);
		if(StringUtils.isBlank(id)){
			return false;
		}else{
			return true;
		}
	}

}
