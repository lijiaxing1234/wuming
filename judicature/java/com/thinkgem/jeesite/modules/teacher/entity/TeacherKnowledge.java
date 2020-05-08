package com.thinkgem.jeesite.modules.teacher.entity;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 老师标识过的知识点(entity)
 */
public class TeacherKnowledge extends DataEntity<TeacherKnowledge> {

	private static final long serialVersionUID = 1L;

	private CourseKnowledge courseKnowledge; // 课程知识点Id
	private String knowledgeTitle;

	private User teacher; // 老师

	private String level; // 标识知识点的难易程度 （对字典表sys_dict 中类型为
							// 【questionlib_courseKnowledge_level】）

	private String passingRate; // 通过率

	private Integer totalCount;
	private Integer correctCount;

	String classId; // 班级id

	public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}

	public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}

	public String getKnowledgeTitle() {
		return knowledgeTitle;
	}

	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPassingRate() {
		return passingRate;
	}

	public void setPassingRate(String passingRate) {
		this.passingRate = passingRate;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(Integer correctCount) {
		this.correctCount = correctCount;
	}

	public static void sortList(List<TeacherKnowledge> list, List<TeacherKnowledge> sourcelist, String parentId,
			boolean cascade) {
		for (int i = 0; i < sourcelist.size(); i++) {
			TeacherKnowledge tk = sourcelist.get(i);
			if (tk != null && tk.getCourseKnowledge() != null) {
				CourseKnowledge e = tk.getCourseKnowledge();
				if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(parentId)) {
					list.add(tk);
					if (cascade) {
						// 判断是否还有子节点, 有则继续获取子节点
						for (int j = 0; j < sourcelist.size(); j++) {
							// Menu child = sourcelist.get(j);
							TeacherKnowledge ctk = sourcelist.get(j);
							if (ctk != null && ctk.getCourseKnowledge() != null) {
								CourseKnowledge child = ctk.getCourseKnowledge();
								if (child.getParent() != null && child.getParent().getId() != null
										&& child.getParent().getId().equals(e.getId())) {
									sortList(list, sourcelist, e.getId(), true);
									break;
								}
							}
						}
					}
				}
			}
		}
	}

}
