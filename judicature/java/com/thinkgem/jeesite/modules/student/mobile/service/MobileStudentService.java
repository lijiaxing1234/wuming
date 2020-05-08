package com.thinkgem.jeesite.modules.student.mobile.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.HTMLTagDel;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.student.dao.StudentBbsDao;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.mobile.dao.MobileStudentDao;
import com.thinkgem.jeesite.modules.student.mobile.domain.Answer;
import com.thinkgem.jeesite.modules.student.mobile.domain.CommentVo;
import com.thinkgem.jeesite.modules.student.mobile.domain.Question;
import com.thinkgem.jeesite.modules.student.mobile.domain.QuestionVo;
import com.thinkgem.jeesite.modules.student.mobile.domain.StudentVo;
import com.thinkgem.jeesite.modules.student.utils.MobileStringUtils;
import com.thinkgem.jeesite.modules.student.utils.PageMobileUtils;

@Service
public class MobileStudentService {
	
	@Autowired
	MobileStudentDao dao;
	@Autowired
	private StudentBbsDao studentBbsDao;

	/**
	 * 根据学号获取学生
	 * 
	 * @param loginUser
	 * @return
	 */
	public StudentVo getStudentByCode(StudentVo loginUser) {
		return dao.getStudentByCode(loginUser);
	}

	/**
	 * 根据学生的id获取课程
	 * @param stdId
	 * @return
	 */
	public List<Map<String, String>> getCoursesByStdId(String stdId) {
		return dao.getCoursesByStdId(stdId);
	}

	/**
	 * 我的题库
	 * @param studentId
	 * @param pageSize 
	 * @param page 
	 * @param pageSize2 
	 * @param pageSize2 
	 * @return
	 */
	public List<QuestionVo> getMyQuestionLib(String questionTypeId,String studentId,String versionId,String page, String pageSize) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("studentId", studentId);
		paraMap.put("versionId", versionId);
		paraMap.put("questionTypeId", questionTypeId);
		Map<String, Object> pageMap = PageMobileUtils.getPageMap(page, pageSize, paraMap);
		List<QuestionVo> myQuestionLibList = dao.getMyQuestionLib(pageMap);
		for (QuestionVo question : myQuestionLibList) {
			String title = question.getTitle();
			if(StringUtils.isNotBlank(title)){
				question.setStrTitle(MobileStringUtils.removeImgAndTable(HTMLTagDel.delHTMLTag(title)));
			}
			
			if(StringUtils.isNotBlank(title)){
				question.setTitle(MobileStringUtils.getImgUrl(title));
			}
			String answer0 = question.getAnswer0();
			if(StringUtils.isNotBlank(answer0)){
				question.setAnswer0(MobileStringUtils.getImgUrl(answer0));
			}
			String description = question.getDescription();
			if(StringUtils.isNotBlank(description)){
				question.setDescription(MobileStringUtils.getImgUrl(description));
			}
			
			
			String choice0 = question.getChoice0();
			String choice1 = question.getChoice1();
			String choice2 = question.getChoice2();
			String choice3 = question.getChoice3();
			String choice4 = question.getChoice4();
			String choice5 = question.getChoice5();
			String choice6 = question.getChoice6();
			String choice7 = question.getChoice7();
			String choice8 = question.getChoice8();
			String choice9 = question.getChoice9();
			String[] imgUrlArr = MobileStringUtils.getImgUrlArr(choice0,choice1,choice2,choice3,choice4,choice5,choice6,choice7,choice8,choice9);
			question.setChoiceImgArr(imgUrlArr);
		}
		return myQuestionLibList;
	}
	
	/**
	 * 我的错题集
	 * @param studentId
	 * @param page
	 * @param pageSize
	 * @param examType 
	 * @param versionId 
	 * @param pageSize2 
	 * @return
	 */
	public List<QuestionVo> getWrongQuestionLib(String questionTypeId,String studentId, String examType, String page, String pageSize, String versionId) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("studentId", studentId);
		paraMap.put("examType", examType);
		paraMap.put("questionTypeId", questionTypeId);
		paraMap.put("versionId", versionId);
		List<QuestionVo> wrongQuestionList = dao.getWrongQuestionLib(PageMobileUtils.getPageMap(page, pageSize, paraMap));
		for (QuestionVo question : wrongQuestionList) {
			String title = question.getTitle();
			question.setStrTitle(MobileStringUtils.removeImgAndTable(title));
			if(StringUtils.isNotBlank(title)){
				question.setTitle(MobileStringUtils.getImgUrl(title));
			}
			String answer01 = question.getAnswer0();
			if(StringUtils.isNotBlank(answer01)){
				question.setAnswer0(MobileStringUtils.getImgUrl(answer01));
			}
			String description = question.getDescription();
			if(StringUtils.isNotBlank(description)){
				question.setDescription(MobileStringUtils.getImgUrl(description));
			}
			String choice0 = question.getChoice0();
			String choice1 = question.getChoice1();
			String choice2 = question.getChoice2();
			String choice3 = question.getChoice3();
			String choice4 = question.getChoice4();
			String choice5 = question.getChoice5();
			String choice6 = question.getChoice6();
			String choice7 = question.getChoice7();
			String choice8 = question.getChoice8();
			String choice9 = question.getChoice9();
			String[] imgUrlArr = MobileStringUtils.getImgUrlArr(choice0,choice1,choice2,choice3,choice4,choice5,choice6,choice7,choice8,choice9);
			question.setChoiceImgArr(imgUrlArr);
			
			String quesType = question.getQuesType();
			if("1".equals(quesType)){
				//单选题
				question.setAnswerArr(new String[]{question.getMyAnswer().replace("*,*", "")});
			}else if("11".equals(quesType)){
				String answer0 = question.getMyAnswer().replace("*,*", "");
				if("正确".equals(answer0)){
					question.setMyAnswer("正确");
					question.setAnswer0("正确");
					question.setAnswerArr(new String[]{"正确"});
				}else if("错误".equals(answer0)){
					question.setAnswer0("错误");
					question.setMyAnswer("错误");
					question.setAnswerArr(new String[]{"错误"});
				}
				
				if("1".equals(answer0)){
					question.setAnswer0("正确");
					question.setAnswerArr(new String[]{"正确"});
				}else if("2".equals(answer0)){
					question.setAnswer0("错误");
					question.setAnswerArr(new String[]{"错误"});
				}
			}else if("2".equals(quesType)){
				//填空题
				question.setAnswerArr(question.getMyAnswer().split("\\*,\\*"));
			}else if("5".equals(quesType)){
				//多选题
				byte[] bytes = question.getMyAnswer().replace("*,*", "").getBytes();
				String[] answerArr = new String[bytes.length];
				for(int i = 0;i < bytes.length; i++){
					answerArr[i] = String.valueOf((char)bytes[i]);
				}
				question.setAnswerArr(answerArr);
			}else{
				question.setAnswerArr(new String[]{question.getMyAnswer().replace("*,*", "")});
			}
		}
		return wrongQuestionList;
	}

	/**
	 * 我的评论
	 * 学生的评论
	 * @param studentId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<CommentVo> getMyComment(String studentId, String page, String pageSize) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("studentId", studentId);
		return dao.getMyComment(PageMobileUtils.getPageMap(page, pageSize, paraMap));
	}
	
	/**
	 * 根据课程和测试类型获取测试
	 * @param examType
	 * @param courseId
	 * @param studentId 
	 * @return
	 */
	public List<Map<String, Object>> getExamsByTypeAndCourse(String examType, String courseId, String studentId,String page,String pageSize) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("examType", examType);
		paraMap.put("courseId", courseId);
		paraMap.put("studentId", studentId);
		//根据课程id考试类型examType 学生id 查询测试的简要信息
		List<Map<String, Object>> examList = dao.getExamsByExamTypeAndCourseIdAndStudentId(PageMobileUtils.getPageMap(page, pageSize, paraMap));
		return examList;
	}

	/**
	 * 获取考试试卷
	 * @param studentId
	 * @param examId
	 * @return
	 */
	public List<QuestionVo> getTestPaper(String studentId, String examId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		List<QuestionVo> testPaperList = dao.getTestPaper(paraMap);
		for (QuestionVo question : testPaperList) {
			String answer02 = question.getAnswer0();
			if(StringUtils.isNotBlank(answer02)){
				question.setAnswer0(MobileStringUtils.getImgUrl(question.getAnswer0()));
			}
			String description = question.getDescription();
			if(StringUtils.isNotBlank(description)){
				question.setDescription(MobileStringUtils.getImgUrl(question.getDescription()));
			}
			
			
			if(question.getQuesType() != null){
				if(question.getQuesType().equals("2")){
					String answer0 = question.getAnswer0();
					String answer1 = question.getAnswer1();
					String answer2 = question.getAnswer2();
					String answer3 = question.getAnswer3();
					String answer4 = question.getAnswer4();
					String answer5 = question.getAnswer5();
					String answer6 = question.getAnswer6();
					String answer7 = question.getAnswer7();
					String answer8 = question.getAnswer8();
					String answer9 = question.getAnswer9();
					String[] tiankongCount = new String[]{answer0,answer1,answer2,answer3,answer4,answer5,answer6,answer7,answer8,answer9};
					List<String> strlist=Lists.newArrayList();
					for (String string : tiankongCount) {
						if(StringUtils.isBlank(string)){
						}else{
							strlist.add(string);
						}
					}
					String[] newArr = (String[]) strlist.toArray(new String[strlist.size()]);
					question.setTiankongCount(newArr);
				}else{
					question.setTiankongCount(new String[]{});
				}
			}
			
			
			String choice0 = question.getChoice0();
			String choice1 = question.getChoice1();
			String choice2 = question.getChoice2();
			String choice3 = question.getChoice3();
			String choice4 = question.getChoice4();
			String choice5 = question.getChoice5();
			String choice6 = question.getChoice6();
			String choice7 = question.getChoice7();
			String choice8 = question.getChoice8();
			String choice9 = question.getChoice9();
			String[] imgUrlArr = MobileStringUtils.getImgUrlArr(choice0,choice1,choice2,choice3,choice4,choice5,choice6,choice7,choice8,choice9);
			question.setChoiceImgArr(imgUrlArr);
			String title = question.getTitle();
			if(StringUtils.isNotBlank(title)){
				question.setTitle(MobileStringUtils.getImgUrl(title));
			}
		}
		return testPaperList;
	}
	
	
	
	

	/**
	 * 获取考试试卷的全部信息
	 * 试卷 +答案+讲解+我的答案
	 * @param studentId
	 * @param examId
	 * @return
	 * 
	 */
	public List<QuestionVo> getTestPaperAllMessage(String studentId, String examId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		List<QuestionVo> testPaperAllMessage = dao.getTestPaperAllMessage(paraMap);
		for (QuestionVo question : testPaperAllMessage) {
			if(StringUtils.isNotBlank(question.getAnswer0())){
				question.setAnswer0(MobileStringUtils.getImgUrl(question.getAnswer0()));
			}
			String description = question.getDescription();
			if(StringUtils.isNotBlank(description)){
				question.setDescription(MobileStringUtils.getImgUrl(description));
			}

			String choice0 = question.getChoice0();
			String choice1 = question.getChoice1();
			String choice2 = question.getChoice2();
			String choice3 = question.getChoice3();
			String choice4 = question.getChoice4();
			String choice5 = question.getChoice5();
			String choice6 = question.getChoice6();
			String choice7 = question.getChoice7();
			String choice8 = question.getChoice8();
			String choice9 = question.getChoice9();
			String[] imgUrlArr = MobileStringUtils.getImgUrlArr(choice0,choice1,choice2,choice3,choice4,choice5,choice6,choice7,choice8,choice9);
			question.setChoiceImgArr(imgUrlArr);
			String title = question.getTitle();
			if(StringUtils.isNotBlank(title)){
				question.setTitle(MobileStringUtils.getImgUrl(title));
			}
			
			//处理题型为填空题的我的答案
			String quesType = question.getQuesType();
			if("1".equals(quesType)){
				//单选题
				String replace = question.getMyAnswer().replace("*,*", "");
				question.setAnswerArr(new String[]{replace});
			}else if("11".equals(quesType)){
				String answer0 = question.getMyAnswer().replace("*,*", "");
				if("正确".equals(answer0) || "1".equals(answer0)){
					question.setAnswer0("正确");
					question.setMyAnswer("正确");
					question.setAnswerArr(new String[]{"正确"});
				}else if("错误".equals(answer0) || "2".equals(answer0)){
					question.setAnswer0("错误");
					question.setMyAnswer("错误");
					question.setAnswerArr(new String[]{"错误"});
				}
			}else if("2".equals(quesType)){
				//填空题
				question.setAnswerArr(question.getMyAnswer().split("\\*,\\*"));
				question.setTiankongCount(question.getMyAnswer().split("\\*,\\*"));
			}else if("5".equals(quesType)){
				//多选题
				byte[] bytes = question.getMyAnswer().replace("*,*", "").getBytes();
				String[] answerArr = new String[bytes.length];
				for(int i = 0;i < bytes.length; i++){
					answerArr[i] = String.valueOf((char)bytes[i]);
				}
				question.setAnswerArr(answerArr);
			}else{
				String myAnswer = question.getMyAnswer();
				myAnswer = myAnswer.replace("*,*", "");
				myAnswer = MobileStringUtils.getImgUrl(myAnswer);
				question.setMyAnswer(myAnswer);
				question.setAnswerArr(new String[]{myAnswer});
			}
		}
		return testPaperAllMessage;
	}

	public StudentAnswer isAnswered(String studentId, String examId, String questionId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		paraMap.put("questionId", questionId);
		return dao.mobileIsAnswered(paraMap);
	}

	public void addStudentAnswer(StudentAnswer studentAnswer) {
		dao.addStudentAnswer(studentAnswer);
	}

	public void updateStudentAnswer(StudentAnswer studentAnswer) {
		dao.updateStudentAnswer(studentAnswer);
	}

	public int addStudentTask(StudentTask studentTask) {
		return dao.addStudentTask(studentTask);
	}

	public void updateMobileStudentTask(StudentTask studentTask) {
		dao.updateMobileStudentTask(studentTask);
	}

	public void updatePassword(String studentId, String newPassword) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("studentId", studentId);
		paraMap.put("password", newPassword);
		dao.updatePassword(paraMap);
	}

	public StudentVo getStudentById(String studentId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("studentId", studentId);
		return dao.getStudentById(paraMap);
	}

	public void addStudentAnswerList(List<StudentAnswer> list) {
		dao.addStudentAnswerList(list);
	}

	public List<QuestionVo> getMissedTestPaper(String examId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("examId", examId);
		List<QuestionVo> missedTestPaper = dao.getMissedTestPaper(paraMap);
		for (QuestionVo question : missedTestPaper) {
			if(null == question.getDescription() || "null".equals(question.getDescription())){
				question.setDescription("");
			}
			if(StringUtils.isNotBlank(question.getAnswer0())){
				question.setAnswer0(MobileStringUtils.getImgUrl(question.getAnswer0()));
			}
			if(StringUtils.isNotBlank(question.getDescription())){
				question.setDescription(question.getDescription());
			}
			String choice0 = question.getChoice0();
			String choice1 = question.getChoice1();
			String choice2 = question.getChoice2();
			String choice3 = question.getChoice3();
			String choice4 = question.getChoice4();
			String choice5 = question.getChoice5();
			String choice6 = question.getChoice6();
			String choice7 = question.getChoice7();
			String choice8 = question.getChoice8();
			String choice9 = question.getChoice9();
			String[] imgUrlArr = MobileStringUtils.getImgUrlArr(choice0,choice1,choice2,choice3,choice4,choice5,choice6,choice7,choice8,choice9);
			question.setChoiceImgArr(imgUrlArr);
			
			String title = question.getTitle();
			if(StringUtils.isNotBlank(title)){
				question.setTitle(MobileStringUtils.getImgUrl(title));
			}
				
		}
		return missedTestPaper;
	}

	public List<Map<String, String>> getQuestionIdAndAnswerByExamDetilId(String examDetailId) {
		return dao.getQuestionIdAndAnswerByExamDetilId(examDetailId);
	}

	public void browseQuestion(String studentId, String questionId, String versionId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("questionId", questionId);
		paraMap.put("versionId", versionId);
		dao.browseQuestion(paraMap);
	}

	public List<Question> getQuestionList(String studentId,String page,String pageSize) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("studentId", studentId);
		return dao.getQuestionList(PageMobileUtils.getPageMap(page, pageSize, paraMap));
	}

	public List<Answer> getAnswerList(String questionId,String page,String pageSize) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("questionId", questionId);
		return dao.getAnswerList(PageMobileUtils.getPageMap(page, pageSize, paraMap));
	}

	public void addQuestion(String studentId, String questionTitle) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String officeId = getOfficeIdByStudentId(studentId);
		paraMap.put("officeId", officeId);
		paraMap.put("studentId", studentId);
		paraMap.put("questionId", IdGen.uuid());
		paraMap.put("content", questionTitle);
		paraMap.put("createTime", new Date());
		dao.addQuestion(paraMap);
	}
	private String getOfficeIdByStudentId(String studentId) {
		return studentBbsDao.getOfficeIdByStudentId(studentId);
	}

	public void submitAnswer(String studentId,String questionId, String answerMsg) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("answerId", IdGen.uuid());
		paraMap.put("questionId", questionId);
		paraMap.put("studentId", studentId);
		String officeId = getOfficeIdByStudentId(studentId);
		paraMap.put("officeId", officeId);
		paraMap.put("detail", answerMsg);
		paraMap.put("createTime", new Date());
		dao.submitAnswer(paraMap);
	}

	public StudentVo isExistStudent(String studentId) {
		return dao.isExistStudent(studentId);
	}

	public List<Map<String, String>> getAllSchool() {
		return dao.getAllSchool();
	}

	public List<Map<String, String>> getQuestionLibQuesTypesAndQuesCounts(String studentId, String versionId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("versionId", versionId);
		return dao.getQuestionLibQuesTypesAndQuesCounts(paraMap);
	}

	public List<Map<String, String>> getWrongQuestionLibQuesTypeAndQuesCount(String studentId, String examType, String versionId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examType", examType);
		paraMap.put("versionId", versionId);
		return dao.getWrongQuestionLibQuesTypeAndQuesCount(paraMap);
	}

	public boolean isGradeTestPaper(String studentId, String examId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		String isGradeTestPaper = dao.isGradeTestPaper(paraMap);
		if(StringUtils.isBlank(isGradeTestPaper)){
			return false;
		}
		if("1".equals(isGradeTestPaper)){
			return true;
		}else{
			return false;
		}
	}

	public Boolean isGraduate(String studentId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		StudentVo reStudent = dao.isGraduate(paraMap);
		Date now = new Date();
		if(reStudent.getEndDate().getTime() < now.getTime()){
			return true;
		}else{
			return false;
		}
	}
	
	
}
