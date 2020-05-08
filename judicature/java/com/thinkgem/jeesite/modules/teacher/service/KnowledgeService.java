package com.thinkgem.jeesite.modules.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseKnowledgeDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.dao.TeacherKnowledgeDao;
import com.thinkgem.jeesite.modules.teacher.entity.StaticKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 *老师端知识点(Service层)
 */
@Service("teacherKnowledgeService")
@Transactional(readOnly=true)
public class KnowledgeService{

	@Autowired
	CourseKnowledgeDao courseKnowledgeDao; //课程知识点Dao
	
	@Autowired
	TeacherKnowledgeDao teacherKnowledgeDao; 
	
	
	/**
	 * 查询老师标识过的知识点
	 */
   public List<TeacherKnowledge> findTeacherKnowledgeList(TeacherKnowledge teacherKnowledge){
	   teacherKnowledge.setTeacher(TearcherUserUtils.getUser());
	   String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
	   Map<String, String> sqlMap = teacherKnowledge.getSqlMap();
	   sqlMap.put("versionId", versionId);
	   return teacherKnowledgeDao.findTeacherKnowledgeList(teacherKnowledge);
   }
	
	
	/**
	 * 得到课程版本全部知识点
	 */
	public List<CourseKnowledge> findCourseKnowledgeByVersion(CourseKnowledge courseKnowledge){
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		Map<String, String> sqlMap = courseKnowledge.getSqlMap();
		sqlMap.put("versionId",versionId);
		return courseKnowledgeDao.findAllList(courseKnowledge);
	}

	/**
	 * 根据老师Id查询、选中节点以及所有子节点课程版本知识点 且带有老师标识过知识点难易程度
	 */
	public List<TeacherKnowledge> findTeacherKnowledgesList(TeacherKnowledge teacherKnowledge){
		teacherKnowledge.setTeacher(TearcherUserUtils.getUser());
		Parameter sqlParam=teacherKnowledge.getSqlParam();
		sqlParam.put("mapFilter", TearcherUserUtils.getTeacherIdAndCourseVersionId());
		return teacherKnowledgeDao.findTeacherKnowledgeByCourseKnowledgeIdOrLikeParentIds(teacherKnowledge);
	}
	/**
	 * 根据老师Id查询、选中节点以及所有子节点课程版本知识点
	 * <br/> 携带当前知识点下出过总题数、回答正确的数。 
	 * @param teacherKnowledge
	 * @return
	 */
	public List<TeacherKnowledge> findTeacherKnowledgesListWithPassingRate(TeacherKnowledge teacherKnowledge){
		teacherKnowledge.setTeacher(TearcherUserUtils.getUser());
		Parameter sqlParam=teacherKnowledge.getSqlParam();
		sqlParam.put("mapFilter", TearcherUserUtils.getTeacherIdAndCourseVersionId());
		return teacherKnowledgeDao.findTeacherKnowledgesListWithPassingRate(teacherKnowledge);
	}
	
	/**
	 * 先删除后添加
	 * 
	 * 批量添加老师标识过的知识点
	 * @param list 
	 */
	@Transactional(readOnly=false)
	public void batchAddTeacherKnowledge(String ckIds,String levels){
		 if(StringUtils.isNotBlank(ckIds)&&StringUtils.isNotBlank(levels)){
			 //  设置当前老师id
			 //User user=new User("1");
			 User user=TearcherUserUtils.getUser();
			 List<TeacherKnowledge> list =Lists.newArrayList();
			 
			 String[] ckIdArr=ckIds.split(",");
			 String[] levelArr=levels.split(",");
			 
			 for(int i=0,len=ckIdArr.length;i<len;i++){
				 TeacherKnowledge  tk=new TeacherKnowledge();
				 tk.setLevel(levelArr[i]);
				 tk.setCourseKnowledge(new CourseKnowledge(ckIdArr[i]));
				 tk.setTeacher(user);
				 list.add(tk);
			 }
			 if(list.size()>0){
				 Parameter param=new Parameter(new Object[][]{
				    {"teacherId",user.getId()},
				    {"ckIdArr",ckIdArr}
				 });
				 teacherKnowledgeDao.batchDeleteTeacherKnowledge(param);
				 teacherKnowledgeDao.batchAddTeacherKnowledge(list);  //插入
			 }
		 }
	}

    
	/**
	 * 某次组卷或在线考试所考知识点，某班级的答题情况
	 * @param staticKnowledge
	 * @return
	 */
	public List<StaticKnowledge> staticKnowledgeByexamIdAndClassId(StaticKnowledge staticKnowledge) {
		return teacherKnowledgeDao.staticKnowledgeByexamIdAndClassId(staticKnowledge);
	}

	
	/**
	 *根据组卷或在线考试id 查询所考知识点
	 */
	public List<CourseKnowledge> findCourseKnowledgeByExamId(String examId) {
		return teacherKnowledgeDao.findCourseKnowledgeByExamId(examId);
	}
   

	
	/**
	 * 根据知识点Id 查询其下所有的试题
	 * @return
	 */
	public List<TeacherVersionQuestion> selectQuestionBykonwId(TeacherVersionQuestion question) {
		
		return teacherKnowledgeDao.selectQuestionBykonwId(question);
	}
	
	
	/**
	 * 查询打错题的学生集合 只用于统计
	 * @param examId 测评的Id
	 * @param classId 班级的Id
	 * @param quesId  试题的Id
	 * 
	 * @return List<Map<String,Object>>  学生的结果集
	 */
	public List<Map<String,Object>>  findWrongAnswerStudents(String examId,String classId,String quesId){
		
		
		Parameter param=new Parameter(new Object[][]{
			{"examId",examId},
			{"classId",classId},
			{"quesId",quesId}
		});
		
		List<Map<String,Object>> dataList=teacherKnowledgeDao.findWrongAnswerStudents(param);
		return dataList;
	}


	public List<TeacherKnowledge> getKnowledgesPassingRate(TeacherKnowledge teacherKnowledge) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("teacherId", TearcherUserUtils.getUser().getId());
		paraMap.put("versionId", TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId"));
		return teacherKnowledgeDao.getKnowledgesPassingRate(paraMap);
	}


	public List<TeacherKnowledge> getKnowledgesPassingRate2(TeacherKnowledge teacherKnowledge) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("teacherId", TearcherUserUtils.getUser().getId());
		paraMap.put("knowledgeId", teacherKnowledge.getCourseKnowledge().getId());
		return teacherKnowledgeDao.getKnowledgesPassingRate2(paraMap);
	}


	public List<StaticKnowledge> staticKnowledgeByexamIdAndClassId2(StaticKnowledge staticKnowledge) {
		return teacherKnowledgeDao.staticKnowledgeByexamIdAndClassId2(staticKnowledge);
	}

	
   	
}
