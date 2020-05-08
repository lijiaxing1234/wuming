/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.VersionQuestionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;

/**
 * 试题Service
 * @author webcat
 * @version 2016-09-09
 */
@Service
@Transactional(readOnly = true)
public class VersionQuestionService extends CrudService<VersionQuestionDao, VersionQuestion> {

	@Autowired
	protected VersionQuestionDao versionQuestionDao;
	
	public VersionQuestion get(String id) {
		return super.get(id);
	}
	
	public List<VersionQuestion> findList(VersionQuestion versionQuestion) {
		return super.findList(versionQuestion);
	}
	
	public Page<VersionQuestion> findPage(Page<VersionQuestion> page, VersionQuestion versionQuestion) {
		return super.findPage(page, versionQuestion);
	}
//	public Page<VersionQuestion> findPageByList(Page<VersionQuestion> page, List<VersionQuestion> list) {
//		return versionQuestionDao.findPage(page, list);
//	}
	
	@Transactional(readOnly = false)
	public void save(VersionQuestion versionQuestion) {
		if(StringUtils.isNotBlank(versionQuestion.getTitle())){
			versionQuestion.setTitle(Encodes.unescapeHtml(versionQuestion.getTitle()));
		}
		if(StringUtils.isNotBlank(versionQuestion.getAnswer0())){
			versionQuestion.setAnswer0(Encodes.unescapeHtml(versionQuestion.getAnswer0()));
		}
		if(StringUtils.isNotBlank(versionQuestion.getDescription())){
			versionQuestion.setDescription(Encodes.unescapeHtml(versionQuestion.getDescription()));
		}
		super.save(versionQuestion);
	}
	
	
	public int findCount(VersionQuestion versionQuestion)
	{
		return versionQuestionDao.findCount(versionQuestion);
	}
	
	@Transactional(readOnly = false)
	public void delete(VersionQuestion versionQuestion) {
		super.delete(versionQuestion);
	}
	
	public List<String> findQuestionIdList(VersionQuestion versionQuestion) {
		return versionQuestionDao.findQuestionIdList(versionQuestion);
	}
	
	public List<VersionQuestion> findQuestionList(VersionQuestion versionQuestion){
		return versionQuestionDao.findQuestionList(versionQuestion);
	}
	
	public List<String> findKnowledgeIdList(VersionQuestion versionQuestion) {
		return versionQuestionDao.findKnowledgeIdList(versionQuestion);
	}
	
	public List<CourseKnowledge> findKnowledgeKist(VersionQuestion versionQuestion){
		return versionQuestionDao.findKnowledgeList(versionQuestion);
	}
	
	@Transactional(readOnly = false)
	public void saveKnowledgeIdAndQuestionId(VersionQuestion versionQuestion){
		versionQuestionDao.saveKnowledgeIdAndQuestionId(versionQuestion);
	}

	@Transactional(readOnly = false)
	public void deleteKnowledgeIdAndQuestionId(VersionQuestion versionQuestion) {
		versionQuestionDao.deleteKnowledgeIdAndQuestionId(versionQuestion);
		
	}

	@Transactional(readOnly = false)
	public void saveImportIdAndQuestionId(VersionQuestion versionQuestion) {
		versionQuestionDao.saveImportIdAndQuestionId(versionQuestion);
	}
	
	@Transactional(readOnly = false)
	public void deleteImportIdAndQuestionId(VersionQuestion versionQuestion){
		versionQuestionDao.deleteImportIdAndQuestionId(versionQuestion);
	}
	//替换题目
	@Transactional(readOnly = false)
	public void updateQuestionExamInfo(String examdetailId,String questionId,String replaceQuestionId){
		versionQuestionDao.updateQuestionExamInfo(examdetailId,questionId,replaceQuestionId);
	}

	//根据试题删除状态查找试题集合（通过删除状态del_flag/知识点名称/题型/难度/题库 字段查询试题   0-正常(审核通过，可使用)  1-已删除   2-未审核）
	public List<VersionQuestion> findListByDelFlagAndTypeAndLevelAndLibId(VersionQuestion versionQuestion) {
		return versionQuestionDao.findListByDelFlagAndTypeAndLevelAndLibId(versionQuestion);
	}
	
	//根据试题id和delFlag查找试题
	public VersionQuestion findByIdAndDelFlag(VersionQuestion versionQuestion){
		return versionQuestionDao.findByIdAndDelFlag(versionQuestion);
	}
	
	//更改试题的删除状态
	@Transactional(readOnly = false)
	public void updateQuestionDelFlagByImportId(VersionQuestion versionQuestion){
		versionQuestionDao.updateQuestionDelFlagByImportId(versionQuestion);
	}
	
	//通过导入文档id查询该文档中试题id集合
	public List<String> findByImportId(VersionQuestion versionQuestion){
		return versionQuestionDao.findByImportId(versionQuestion);
	}

	public List<VersionQuestion> findByQuestionLibIdAndQuesTypeAndQuesLevel(VersionQuestion versionQuestion) {
		return versionQuestionDao.findByQuestionLibIdAndQuesTypeAndQuesLevel(versionQuestion);
	}

	////根据试题删除状态查找试题分页集合（通过删除状态del_flag/知识点名称/题型/难度/题库 字段查询试题   0-正常(审核通过，可使用)  1-已删除   2-未审核）
	public Page<VersionQuestion> findPageByDelFlagAndTypeAndLevelAndLibId(Page<VersionQuestion> page,VersionQuestion versionQuestion) {
		versionQuestion.setPage(page);
		page.setList(versionQuestionDao.findListByDelFlagAndTypeAndLevelAndLibId(versionQuestion));
		return page;
	}

	public Page<VersionQuestion> findPageByKnowledgeId(Page<VersionQuestion> page, VersionQuestion versionQuestion) {
		versionQuestion.setPage(page);
		page.setList(versionQuestionDao.findQuestionList(versionQuestion));
		return page;
	}

	public VersionQuestion getQuestionMsg(String questionId, String examId) {
		if(StringUtils.contains(questionId, ",")){
			questionId = StringUtils.substringBefore(questionId, ",");
		}
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		paraMap.put("questionId", questionId);
		VersionQuestion questionMsg = versionQuestionDao.getQuestionMsg(paraMap);
		String quesType = questionMsg.getQuesType();
		if(quesType.equals("11")){
			if(questionMsg.getAnswer0().equals("1")){
				questionMsg.setAnswer0("正确");
			}
			if(questionMsg.getAnswer0().equals("2")){
				questionMsg.setAnswer0("错误");
			}
		}
		String answer0 = questionMsg.getAnswer0();
		if(StringUtils.contains(answer0, "&lt;p&gt;") && StringUtils.contains(answer0, "&lt;/p&gt;")){
			answer0 = answer0.replace("&lt;p&gt;", "");
			answer0 = answer0.replace("&lt;/p&gt;", "");
			questionMsg.setAnswer0(answer0);
		}
		return questionMsg;
	}

	public VersionQuestion getQuestionByQuestionId(String questionId) {
		return versionQuestionDao.getQuestionByQuestionId(questionId);
	}

	public VersionQuestion getQuestionMsg(String questionId) {
		if(StringUtils.contains(questionId, ",")){
			questionId = StringUtils.substringBefore(questionId, ",");
		}
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("questionId", questionId);
		VersionQuestion questionMsg = versionQuestionDao.getQuestionMsgByQuestionId(paraMap);
		String quesType = questionMsg.getQuesType();
		if(quesType.equals("11")){
			if(questionMsg.getAnswer0().equals("1")){
				questionMsg.setAnswer0("正确");
			}
			if(questionMsg.getAnswer0().equals("2")){
				questionMsg.setAnswer0("错误");
			}
		}
		String answer0 = questionMsg.getAnswer0();
		if(StringUtils.contains(answer0, "&lt;p&gt;") && StringUtils.contains(answer0, "&lt;/p&gt;")){
			answer0 = answer0.replace("&lt;p&gt;", "");
			answer0 = answer0.replace("&lt;/p&gt;", "");
			questionMsg.setAnswer0(answer0);
		}
		return questionMsg;
	}
	
	public List<Course> getAllCourse(){
		return versionQuestionDao.getAllCourse();
	}
	public List<Knowledge> getChapters(String versionId){
		return versionQuestionDao.getChapters(versionId);
	}
	public List<Knowledge> getNodes(String parentId){
		return versionQuestionDao.getNodes(parentId);
	}
	
	public String getPastYearId(){
		
		return versionQuestionDao.getPastYearId();
	}
	
	@Transactional(readOnly = false)
	public boolean inKnowQue(String knowledgeId,String questionId){
		int i = versionQuestionDao.inKnowQue(knowledgeId, questionId);
		return i == 1;
	}
	
	public int hasKQ(String knowledgeId,String questionId){
		int hasKQ = versionQuestionDao.hasKQ(knowledgeId, questionId);
		return hasKQ;
	}
	
	public VersionQuestion getQueTrueCourse(String id){
		VersionQuestion queTrueCourse = versionQuestionDao.getQueTrueCourse(id);
		String courseId = queTrueCourse.getCourseId();
		String chapterId = queTrueCourse.getChapterId();
		String nodeId = queTrueCourse.getNodeId();
		if (StringUtils.isNotBlank(courseId)) {
			String course = versionQuestionDao.getCourse(courseId);
			queTrueCourse.setCourseName(course);
		}
		if (StringUtils.isNotBlank(chapterId)) {
			String getck = versionQuestionDao.getck(chapterId);
			queTrueCourse.setChapterName(getck);
		}
		if (StringUtils.isNotBlank(nodeId)) {
			String getnodeId = versionQuestionDao.getck(nodeId);
			queTrueCourse.setNodeName(getnodeId);
		}
		
		return queTrueCourse;
	}
}