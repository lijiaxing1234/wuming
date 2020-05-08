package com.thinkgem.jeesite.modules.teacher.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.teacher.dto.Exam2;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.dto.ScoreDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentTaskDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.StaticKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassService;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassWorkService;
import com.thinkgem.jeesite.modules.teacher.service.ExamScoreService;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.KnowledgeService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;
/**
 * 统计分析
 */
@Controller("teacherStatisticsController")
@RequestMapping("${teacherPath}/statistics")
public class StatisticsController extends BaseController {
	@Autowired
	ExamService examService;
	@Autowired
	private ExamClassService examClassService;
	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ExamClassWorkService examClassWorkService;
	@Autowired
	KnowledgeService knowledgeService;
	@Autowired
	ExaminationService  examinationService;
	@Autowired
	ExamScoreService examScoreService;

	
	/**
	 * 教师以及版本下的所有试卷列表
	 * @param exam
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String  list(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//根据教师id以及版本id查询所有的班级进而查询所有的学生
		Page<Exam> page= examService.getExamByTeacherIdAndVersionId(new Page<Exam>(request,response),exam,userId,versionId);
		model.addAttribute("examList", page);
		model.addAttribute("exam", exam);
		return "teacher/statistics/list";
	}
	
	/**
	 * 某一试卷下的某一班级内的学生列表
	 * @param exam
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("studentList")
	public String  studentList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
		//获取作业名称
		Exam exam2=examService.get(exam.getId());
		//获取班级名称
		String classId=exam.getExamClass().getId();
		SchoolClass school = schoolClassService.get(classId);
		//班级内的学生
		ExamStudentDTO examStudentDTO=new ExamStudentDTO();
		
		Student student=new Student();
		examStudentDTO.setTableClassId(classId);
		examStudentDTO.setExam(exam);
		examStudentDTO.setStudent(student);
		Page<ExamStudentDTO> page = examClassService.findExamStudent(new Page<ExamStudentDTO>(request, response), examStudentDTO);
		
		model.addAttribute("student", page);
		model.addAttribute("exam", exam2);
		model.addAttribute("school", school);
		if(("1").equals(a)){
			model.addAttribute("message", "该考试没有人参加！");
		}
		
		return "teacher/statistics/classDetailList";
	}
	/**
	 * 某一特定学生在该试卷中的具体答题情况
	 * @param studentId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequestMapping("questionList")
	public String  questionList(StudentQuestionDTO studentQuestionDTO,String studentId,String examId,String examClassId,HttpServletRequest request, HttpServletResponse response, Model model){
		//获取作业名称
		Exam exam=examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		//获得学生姓名
		Student student = studentService.get(studentId);
		model.addAttribute("exam", exam);
		model.addAttribute("school", school);
		model.addAttribute("student", student);
		 
		Page<StudentQuestionDTO> studentQuestion=examService.queryPersondetail(new Page<StudentQuestionDTO>(request, response),studentQuestionDTO,examId,examClassId,studentId);
		model.addAttribute("studentQuestion", studentQuestion);
		return "teacher/statistics/exam_person_detail";
	}*/
	/**
	 * 用于跳转页面
	 */
	@RequestMapping("personList")
	public String personList(Exam exam,String studentId,HttpServletRequest request, HttpServletResponse response, Model model){
		//获得学生姓名
		Student student = studentService.get(studentId);
		model.addAttribute("student", student);
		model.addAttribute("exam", exam);
		return "teacher/statistics/person_detail";
	}
	/**
	 * 统计某一特定学生在该教师所教版本下所有测试的分数变化
	 * @throws Exception 
	 */
	/*@ResponseBody
	@RequestMapping("personScoreList")
	public void personScoreList(Exam exam,String studentId,String examClassId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		TableClass examClass=new TableClass();
		examClass.setId(examClassId);
		exam.setExamClass(examClass);
		request.setCharacterEncoding("UTF-8");    //设定客户端提交给servlet的内容按UTF-8编码
        response.setCharacterEncoding("UTF-8");    //设定servlet传回给客户端的内容按UTF-8编码
        response.setContentType("text/html;charset=UTF-8");    //告知浏览器用UTF-8格式解析内容
		//获得所有测试
		List<Exam> examList = examService.getExamByTeacherIdAndVersionIdList(exam,userId,versionId);
		List<ExamScoreDTO> list=new ArrayList<ExamScoreDTO>();
		
		for (Exam exam2 : examList) {
			ExamScoreDTO examScoreDTO=new ExamScoreDTO();
			if(exam2!=null && exam2.getId()!=null && !(("").equals(exam2.getId())) && exam2.getTitle()!=null && !(("").equals(exam2.getTitle()))){
				String str=exam2.getTitle();
			
				examScoreDTO.setExamTitle(str);
			}
			if(exam2!=null && exam2.getId()!=null && !(("").equals(exam2.getId()))){
				String examId=exam2.getId();
				String score = examService.studentTotalScore(studentId,examId);//获取测试的总分
				if(score==null||("").equals(score)){
					examScoreDTO.setScore("0");
				}else{
					examScoreDTO.setScore(score);
				}
				
			}
			list.add(examScoreDTO);
		}
		
		String json2 = (String)JSON.toJSONString(list);
		//将json数据返回给客户端
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(json2);
	}*/
	/**
	 * 获取该学生所在班级下所有试卷的所有学生的列表
	 */
	@ResponseBody
	@RequestMapping("personScoreList")
	public List<Map<String,Object>> personScoreList(Exam2 exam,String studentId,String examClassId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		//cookie中的教师id以及版本id
		List<Map<String,Object>>  list =Lists.newArrayList();
		
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		TableClass examClass=new TableClass();
		examClass.setId(examClassId);
		exam.setExamClass(examClass);
		request.setCharacterEncoding("UTF-8");    //设定客户端提交给servlet的内容按UTF-8编码
        response.setCharacterEncoding("UTF-8");    //设定servlet传回给客户端的内容按UTF-8编码
        response.setContentType("text/html;charset=UTF-8");    //告知浏览器用UTF-8格式解析内容
		//获得所有测试
        List<Exam2> examList = examService.getExamByTeacherIdAndVersionIdList(exam,userId,versionId,studentId);
        Map<String,Object> map=null;
        
        Integer countPerson=examService.getPersonTotal(examClassId);//全班总人数
		
        try{
	        for (Exam2 exam2 : examList) {
				if(exam2!=null){
					map=Maps.newHashMap();
					
					 String examId=exam2.getId();
					 String ranking=examService.getAllExamAndAllStudentScore(examId,studentId,examClassId);
					 String[] split = ranking.split(",");
					 exam2.setRanking(split[0]);
					 exam2.setTotal_score(split[1]);
					 
					 map.put("title", exam2.getTitle());
					 map.put("ranking", exam2.getRanking());
					 map.put("totalScore", (StringUtils.isBlank(exam2.getTotal_score()) || "null".equals(exam2.getTotal_score())) ? 0 : exam2.getTotal_score());
					 map.put("count", countPerson);
					 list.add(map);
				}
			}
        }catch(Exception e){
			 
		}
        
		return list;
//		String json2 = (String)JSON.toJSONString(examList);
//		//将json数据返回给客户端
//        response.setContentType("text/html; charset=utf-8");
//        response.getWriter().write(json2);
	}
	
//	@RequestMapping("staticKnowledgeByexamIdAndClassId")
//	public  String  staticKnowledgeByexamIdAndClassId(String examId,String classId,String backUrl,StaticKnowledge staticKnowledge,Model model){
//		List<StaticKnowledge> list=Lists.newArrayList();
//		Parameter param= staticKnowledge.getSqlParam();
//		param.put("examId", examId);
//		param.put("classId", classId);
//		
//		
//		
//		List<StaticKnowledge> sourcelist=knowledgeService.staticKnowledgeByexamIdAndClassId(staticKnowledge);
//		
//		if(sourcelist!=null && sourcelist.size()>0){
//			StaticKnowledge parent=sourcelist.get(0);
//			StaticKnowledge.sortList(list, sourcelist,parent.getKnowParentId(), true);
//			model.addAttribute("parent", parent);
//		}
//		
//		
//		model.addAttribute("list", list);
//		model.addAttribute("examId", examId);
//		model.addAttribute("classId", classId);
//		model.addAttribute("backUrl", backUrl);
//		model.addAttribute("staticKnowledge", staticKnowledge);
//	   return "teacher/statistics/statisticsKnowledgeList";
//	}getKnowleages
	
	@ResponseBody
	@RequestMapping("getKnowleages")
	public  String  getKnowleages(String examId,String examClassId,StaticKnowledge staticKnowledge,Model model){
		List<StaticKnowledge> list=Lists.newArrayList();
		Parameter param= staticKnowledge.getSqlParam();
		param.put("examId", examId);
		param.put("classId", examClassId);
		
		
		
		List<StaticKnowledge> sourcelist=knowledgeService.staticKnowledgeByexamIdAndClassId(staticKnowledge);
		
		if(sourcelist!=null && sourcelist.size()>0){
			StaticKnowledge parent=sourcelist.get(0);
			StaticKnowledge.sortList(list, sourcelist,parent.getKnowParentId(), true);
			model.addAttribute("parent", parent);
		}
		List<String> xList = new ArrayList<String>();
		List<String> yList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			xList.add(list.get(i).getCorrectRate());
			yList.add(list.get(i).getKnowTitle());
		}
		JSONArray xarray = (JSONArray) JSONArray.toJSON(xList);
		JSONArray yarray = (JSONArray) JSONArray.toJSON(yList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("xarray", xarray);
		jsonObject.put("yarray", yarray);
	   return jsonObject.toString();
	}
	
	@RequestMapping("staticKnowledgeByexamIdAndClassId")
	public  String  staticKnowledgeByexamIdAndClassId(String examId,String classId,String backUrl,StaticKnowledge staticKnowledge,Model model,HttpServletRequest request,HttpServletResponse response,StudentTaskDTO studentTaskDTO){
		model.addAttribute("examId", examId);
		model.addAttribute("examClassId",classId);
		try {
			List<StaticKnowledge> list=Lists.newArrayList();
			Parameter param= staticKnowledge.getSqlParam();
			param.put("examId", examId);
			param.put("classId", classId);
			param.put("teacherId", TearcherUserUtils.getUser().getId());
			
			List<StaticKnowledge> sourcelist=knowledgeService.staticKnowledgeByexamIdAndClassId2(staticKnowledge);
			
			if(sourcelist!=null && sourcelist.size()>0){
				StaticKnowledge parent=sourcelist.get(0);
				StaticKnowledge.sortList(list, sourcelist,parent.getKnowParentId(), true);
				model.addAttribute("parent", parent);
			}
			
			
			model.addAttribute("list", list);
			model.addAttribute("examId", examId);
			model.addAttribute("classId", classId);
			model.addAttribute("backUrl", backUrl);
			model.addAttribute("staticKnowledge", staticKnowledge);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//cookie中的教师id以及版本id
			String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
			String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
			
			HttpSession session = request.getSession();
			if(classId!=null){
				String[] split = classId.split(",");
				classId=split[0];
			}
			//获取到试卷详情
			Exam exam = examService.get(examId);
			//获取班级名称
			SchoolClass school = schoolClassService.get(classId);
			model.addAttribute("exam", exam);
			model.addAttribute("school", school);
			//列表
			Page<StudentTaskDTO> page = examScoreService.queryOnLineScore(new Page<StudentTaskDTO>(request, response), studentTaskDTO,examId,classId,userId,versionId);
			//根据examId查询examDetailId
			List<String> examDetailIds=examService.getExamDetailIdByExamId(examId);
			String AB=null;
			if(examDetailIds.size()>1){
				/*String detailIdA=examService.getExamDetailId(examId,"A");
				String detailIdB=examService.getExamDetailId(examId,"B");*/
				AB="2";
			}
			String detailId=examService.getExamDetailId(examId,"A");
			model.addAttribute("AB", AB);
			model.addAttribute("detailId", detailId);
			model.addAttribute("examDetailType", "A");
			model.addAttribute("examList", page);
			//获取班级学生所有人的分数
			List<StudentTask> list=examService.getAllStudentScore(exam.getId(),classId);
			Integer countPerson=examClassWorkService.totalPerson(classId);//班内总人数
			if(list.size()>0){
				StudentTask studentTaskMax=list.get(0);//班级第一
				Float scoreMax=studentTaskMax.getScore();
				if(scoreMax==null||("").equals(scoreMax)){
					scoreMax=0.0f;
				}
				model.addAttribute("scoreMax", scoreMax);
				Float scoreMin=0.0f;
				if(list.size() != countPerson){
					model.addAttribute("scoreMin", 0);
				}else{
					for(int i=list.size()-1;i>=0;i--){
						StudentTask studentTaskMin=list.get(i);//班级倒数第一
						Float score2=studentTaskMin.getScore();
						if(score2!=null){
							scoreMin=score2;
							break;
						}
					}
					model.addAttribute("scoreMin", scoreMin);
				}
				
				Float scoreTotal =0.0f;//全班总分
				for (StudentTask studentTask : list) {
					if(studentTask!=null&&studentTask.getScore()!=null){
						Float score=studentTask.getScore();
						scoreTotal=scoreTotal+score;
					}
				}
				
				if(countPerson!=0){
					Float avgScore=scoreTotal/countPerson;
					model.addAttribute("avgScore", avgScore);//平均分
				}
				Integer totalScore=examService.getExamTotalScore(exam.getId());//试卷总分
				model.addAttribute("totalScore", totalScore);
			}else{
				model.addAttribute("avgScore", 0);
				model.addAttribute("scoreMin", 0);
				model.addAttribute("scoreMax", 0);
			}
		} catch (Exception e) {
		}
	   return "teacher/statistics/statisticsKnowledgeList2";
	}
	
	
	@RequestMapping("staticKnowledgeByexamIdAndClassIdInfo")
	public  String  staticKnowledgeByexamIdAndClassIdInfo(String examId,String classId,String knowId,String backUrl,Model model){
		model.addAttribute("examId", examId);
		model.addAttribute("classId", classId);
		model.addAttribute("knowId", knowId);
		model.addAttribute("backUrl", backUrl);
		return "teacher/statistics/statisticsKnowledgeInfo";
	}
	
	
	
	@RequestMapping(value="staticKnowledgeByexamIdAndClassIdInfoList")
	public String selectQuestion(String examId,String classId,TeacherVersionQuestion question,String knowId,Model model){
		
		 if(StringUtils.isNotBlank(knowId)){
			 question.setCourseKnowledge(new CourseKnowledge(knowId));
		 }
		Parameter param=question.getSqlParam();
		param.put("mapFilter",TearcherUserUtils.getTeacherIdAndCourseVersionId());
		param.put("examId",examId);
		param.put("classId", classId);
		
		 List<TeacherVersionQuestion> examList = knowledgeService.selectQuestionBykonwId(question);
		 /*Map<String,List<Map<String,Object>>> map=knowledgeService.findWrongAnswerStudents(examId,classId);
		 model.addAttribute("stuWrongList", map);*/
		 model.addAttribute("list", examList);
		 model.addAttribute("question",question);
		 model.addAttribute("examId", examId);
		 model.addAttribute("classId", classId);
		return "teacher/statistics/statisticsKnowledgeInfoList";
	}
	
	/**
	 * 查询试题中 答错的学生集合
	 * 学生未提交试卷 也是 错误
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findWrongStudentListByClassIdAndExamIdAndQuestionId")
	public String  findWrongStudentListByClassIdAndExamIdAndQuestionId(HttpServletRequest request,HttpServletResponse response){
		String classId=request.getParameter("classId");
		String examId=request.getParameter("examId");
		String quesId=request.getParameter("quesId");
		
	    List<Map<String,Object>> map=knowledgeService.findWrongAnswerStudents(examId,classId,quesId);
		return JSON.toJSONString(map);
	}
	
	
	/**
	 * 在线考试成绩统计分析柱状显示
	 */
	@RequestMapping("personList2")
	public String personList2(Exam exam,String classId,HttpServletRequest request, HttpServletResponse response, Model model){
		//获得班级名称
		Exam exam2 = examService.get(exam);
		//获取班级名称
		SchoolClass school = schoolClassService.get(classId);
		model.addAttribute("school", school);
		model.addAttribute("exam", exam2);
		//获取班级学生所有人的分数
		List<StudentTask> list=examService.getAllStudentScore(exam.getId(),classId);
		if(list.size()>0){
			StudentTask studentTaskMax=list.get(0);//班级第一
			Float scoreMax=studentTaskMax.getScore();
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
				if(studentTask!=null){
					Float score=studentTask.getScore();
					if(score==null){
						score=0.0f;
					}
					scoreTotal=scoreTotal+score;
				}
			}
			Integer countPerson=examClassWorkService.totalPerson(classId);//班内总人数
			if(countPerson!=0){
				Float avgScore=scoreTotal/countPerson;
				model.addAttribute("avgScore", avgScore);//平均分
			}
			Integer totalScore=examService.getExamTotalScore(exam.getId());//试卷总分
			model.addAttribute("totalScore", totalScore);
		}else{
			return "redirect:"+teacherPath+"/examScore/examOnLineScore?examId="+exam.getId()+"&examClassId="+classId+"&a=1";
		}
		
		return "teacher/statistics/columnar";
	}
	
	@ResponseBody
	@RequestMapping("personScoreList3")
	public String personScoreList3(String examId,String examClassId){
		//初始化所有的分数段
		JSONObject jsonObject = new JSONObject();
		Integer totalScore=examService.getExamTotalScore(examId);
		List<ScoreDTO> list=new ArrayList<ScoreDTO>();
		List<String> list3=new ArrayList<String>();
       for(int i=0;i<=totalScore;){
    	   ScoreDTO score=new ScoreDTO();
    	   if(totalScore<=10){
    		   score.setScoreRange(0+"-"+totalScore);
    		   list.add(score);
    		   break;
    	   }else{
	    	   int j=0;
	    	   if(j==0){
	    		   j=i+9;
	    	   }else{
	    		   j=j+10;
	    	   }
	    	   score.setScoreRange(i+"-"+j);
	    	   list.add(score);
	    	   list3.add(i+"-"+j);
	    	   i+=10;
    	   }
       }
       JSONArray array1 = (JSONArray) JSONArray.toJSON(list3);
       jsonObject.put("arr1", array1);
       List<String> list2 = new ArrayList<String>();
       for (ScoreDTO scoreDTO : list) {
		String score=scoreDTO.getScoreRange();
		String count=examService.getCountByExamIdAndExamClassIdAndScoreRange(examId,examClassId,score);
		list2.add(count);
       }
       JSONArray arr2 = (JSONArray) JSONArray.toJSON(list2);
       jsonObject.put("arr2", arr2);
       return jsonObject.toString();

	}
	
	/**
	 * 获取该学生所在班级下所有试卷的所有学生的列表
	 */
	@ResponseBody
	@RequestMapping("personScoreList2")
	public List<ScoreDTO> personScoreList2(Exam exam,String examClassId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");    //设定客户端提交给servlet的内容按UTF-8编码
        response.setCharacterEncoding("UTF-8");    //设定servlet传回给客户端的内容按UTF-8编码
        response.setContentType("text/html;charset=UTF-8");    //告知浏览器用UTF-8格式解析内容
		//初始化所有的分数段
        if(exam!=null){
        	Integer totalScore=examService.getExamTotalScore(exam.getId());
	       List<ScoreDTO> list=new ArrayList<ScoreDTO>();
	       for(int i=0;i<=totalScore;){
	    	   ScoreDTO score=new ScoreDTO();
	    	   if(totalScore<=10){
	    		   score.setScoreRange(0+"-"+totalScore);
	    		   list.add(score);
	    		   break;
	    	   }else{
		    	   int j=0;
		    	   if(j==0){
		    		   j=i+9;
		    	   }else{
		    		   j=j+10;
		    	   }
		    	   score.setScoreRange(i+"-"+j);
		    	   list.add(score);
		    	   i+=10;
		    	  /* int n=totalScore-i;
		    	   if(n==0){score.setScoreRange(i+"-"+i); break;}
		    	   if(n<0||(0<n&&n<10)){
		    			score.setScoreRange(j+1+"-"+totalScore);
		    			break;
		    	   }*/
	    	   }
	       }
	       for (ScoreDTO scoreDTO : list) {
			String score=scoreDTO.getScoreRange();
			String count=examService.getCountByExamIdAndExamClassIdAndScoreRange(exam.getId(),examClassId,score);
			scoreDTO.setNumber(count);
	       }
	       return list;
       }
       return null;
	}
}
