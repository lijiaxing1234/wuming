/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 试题Entity
 * 
 * @author webcat
 * @version 2016-09-09
 */
public class VersionQuestion extends DataEntity<VersionQuestion> {

	/*@Override
	public void preInsert() {
		// TODO Auto-generated method stub
		super.preInsert();
		
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}*/

	private static final long serialVersionUID = 1L;
	private String examCode; // 考点
	private String quesType; // 题型
	private String examType; // 所属考试类型
	private String quesLevel; // 难度
	private String quesPoint; // 分值
	private String quesRealPoint; 
	private String title; // 题目
	private String questionCode; // 试题编码
	private String choice0; // 选项A
	private String choice1; // 选项B
	private String choice2; // 选项C
	private String choice3; // 选项D
	private String choice4; // 选项E
	private String choice5; // 选项F
	private String choice6; // 选项G
	private String choice7; // 选项H
	private String choice8; // 选项I
	private String choice9; // 选项J

	private String answer0; // 答案 单选题、多选题、填空题、计算题、简答题答案
	private String answer1; // 答案 填空题答案
	private String answer2; // 答案 填空题答案
	private String answer3; // 答案 填空题答案
	private String answer4; // 答案 填空题答案
	private String answer5; // 答案 填空题答案
	private String answer6; // 答案 填空题答案
	private String answer7; // 答案 填空题答案
	private String answer8; // 答案 填空题答案
	private String answer9; // 答案 填空题答案

	private String myAnswer0;
	private String myAnswer1;
	private String myAnswer2;
	private String myAnswer3;
	private String myAnswer4;
	private String myAnswer5;
	private String myAnswer6;
	private String myAnswer7;
	private String myAnswer8;
	private String myAnswer9;

	private String count; // 选择题选项或填空题答案的数目
	private String description; // 讲解
	private String writer; // 命题人
	private String checker; // 审题人
	private Office office; // 单位
	private String tearchId; // 所属老师
	private String versionId; // 版本
	private String questionlibId; // 题库
	private CourseKnowledge courseKnowledge; // 知识点
	private String material; // 材料
	private String courseId="";
	private String chapterId ="";
	private String nodeId="";
	private String courseName;
	private String chapterName;
	private String nodeName;
private Integer sort; 	// 排序
	
	
	public String getCourseName() {
	return courseName;
}

public void setCourseName(String courseName) {
	this.courseName = courseName;
}

public String getChapterName() {
	return chapterName;
}

public void setChapterName(String chapterName) {
	this.chapterName = chapterName;
}

public String getNodeName() {
	return nodeName;
}

public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
}

	public String getCourseId() {
	return courseId;
}

public void setCourseId(String courseId) {
	this.courseId = courseId;
}

public String getChapterId() {
	return chapterId;
}

public void setChapterId(String chapterId) {
	this.chapterId = chapterId;
}

public String getNodeId() {
	return nodeId;
}

public void setNodeId(String nodeId) {
	this.nodeId = nodeId;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	private List<CourseKnowledge> courseKnowledgeList = new ArrayList<CourseKnowledge>(); // 知识点列表
	// private QuestionlibImport questionlibImport; //导入试题文档
	private String importId; // 导入试题文档id

	private List<VersionQuestion> list = new ArrayList<VersionQuestion>(); // 记录分组的详细信息

	private String[] ids; // 试题id集合
	// delFlag= 0-正常(审核通过，可使用) 1-删除 2-未审核

	// 错题集与我的题库装载数据用无实际意义
	private Date firstTime;
	private Date secondTime;
	private User teacher;
	// 教师判卷显示学生答案
	private StudentAnswer studentAnswer;
	
	private String rightCount;
	private String falseCount;
	
	public String getRightCount() {
		return rightCount;
	}

	public void setRightCount(String rightCount) {
		this.rightCount = rightCount;
	}

	public String getFalseCount() {
		return falseCount;
	}

	public void setFalseCount(String falseCount) {
		this.falseCount = falseCount;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public VersionQuestion() {
		super();
	}

	public VersionQuestion(String id) {
		super(id);
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public Date getSecondTime() {
		return secondTime;
	}

	public void setSecondTime(Date secondTime) {
		this.secondTime = secondTime;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	@Length(min = 0, max = 100, message = "考点长度必须介于 0 和 100 之间")
	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	@Length(min = 0, max = 10, message = "题型长度必须介于 0 和 10 之间")
	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	@Length(min = 0, max = 10, message = "难度长度必须介于 0 和 10 之间")
	public String getQuesLevel() {
		return quesLevel;
	}

	public void setQuesLevel(String quesLevel) {
		this.quesLevel = quesLevel;
	}

	@Length(min = 0, max = 10, message = "分值长度必须介于 0 和 10 之间")
	public String getQuesPoint() {
		return quesPoint;
	}

	public void setQuesPoint(String quesPoint) {
		this.quesPoint = quesPoint;
	}
    
	
	public String getQuesRealPoint() {
		return quesRealPoint;
	}
	public void setQuesRealPoint(String quesRealPoint) {
		this.quesRealPoint = quesRealPoint;
	}
	
	
	
	// @Length(min=0, max=2000, message="题目长度必须介于 0 和 2000 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = Encodes.unescapeHtml(title);
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice0() {
		return choice0;
	}

	public void setChoice0(String choice0) {
		this.choice0 = choice0;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice1() {
		return choice1;
	}

	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice3() {
		return choice3;
	}

	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice4() {
		return choice4;
	}

	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice5() {
		return choice5;
	}

	public void setChoice5(String choice5) {
		this.choice5 = choice5;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice6() {
		return choice6;
	}

	public void setChoice6(String choice6) {
		this.choice6 = choice6;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice7() {
		return choice7;
	}

	public void setChoice7(String choice7) {
		this.choice7 = choice7;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice8() {
		return choice8;
	}

	public void setChoice8(String choice8) {
		this.choice8 = choice8;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getChoice9() {
		return choice9;
	}

	public void setChoice9(String choice9) {
		this.choice9 = choice9;
	}

	// @Length(min=0, max=2000, message="题目长度必须介于 0 和 2000之间")
	public String getAnswer0() {
		return answer0;
	}

	public void setAnswer0(String answer0) {
		this.answer0 = Encodes.unescapeHtml(answer0);
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer6() {
		return answer6;
	}

	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer7() {
		return answer7;
	}

	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer8() {
		return answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	// @Length(min = 0, max = 255, message = "题目长度必须介于 0 和 255之间")
	public String getAnswer9() {
		return answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	// @Length(min=0, max=2000, message="讲解长度必须介于 0 和 2000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = Encodes.unescapeHtml(description);
	}

	@Length(min = 0, max = 32, message = "命题人长度必须介于 0 和 32 之间")
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Length(min = 0, max = 32, message = "审题人长度必须介于 0 和 32 之间")
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 0, max = 32, message = "所属老师长度必须介于 0 和 32 之间")
	public String getTearchId() {
		return tearchId;
	}

	public void setTearchId(String tearchId) {
		this.tearchId = tearchId;
	}

	@Length(min = 0, max = 32, message = "版本长度必须介于 0 和 32 之间")
	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@Length(min = 0, max = 32, message = "版本长度必须介于 0 和 32 之间")
	public String getQuestionlibId() {
		return questionlibId;
	}

	public void setQuestionlibId(String questionlibId) {
		this.questionlibId = questionlibId;
	}

	public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}

	public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}

	public List<CourseKnowledge> getCourseKnowledgeList() {
		return courseKnowledgeList;
	}

	public void setCourseKnowledgeList(List<CourseKnowledge> courseKnowledgeList) {
		this.courseKnowledgeList = courseKnowledgeList;
	}

	public List<String> getCourseKnowledgeIdList() {
		List<String> courseKnowledgeIdList = Lists.newArrayList();
		for (CourseKnowledge knowledge : courseKnowledgeList) {
			courseKnowledgeIdList.add(knowledge.getId());
		}
		return courseKnowledgeIdList;
	}

	public void setCourseKnowledgeIdList(List<String> courseKnowledgeIdList) {
		courseKnowledgeList = Lists.newArrayList();
		for (String courseKnowledgeId : courseKnowledgeIdList) {
			CourseKnowledge knowledge = new CourseKnowledge();
			courseKnowledge.setId(courseKnowledgeId);
			courseKnowledgeList.add(knowledge);
		}
	}

	public String getCourseKnowledgeIds() {
		return StringUtils.join(getCourseKnowledgeIdList(), ",");
	}

	public void setCourseKnowledgeIds(String courseKnowledgeIds) {
		courseKnowledgeList = Lists.newArrayList();
		if (courseKnowledgeIds != null) {
			String[] ids = StringUtils.split(courseKnowledgeIds, ",");
			setCourseKnowledgeIdList(Lists.newArrayList(ids));
		}
	}

	public List<VersionQuestion> getList() {
		return list;
	}

	public void setList(List<VersionQuestion> list) {
		this.list = list;
	}

	/*
	 * public QuestionlibImport getQuestionlibImport() { return
	 * questionlibImport; }
	 * 
	 * public void setQuestionlibImport(QuestionlibImport questionlibImport) {
	 * this.questionlibImport = questionlibImport; }
	 * 
	 * public void setImportId(String importId) {
	 * if(this.questionlibImport==null){ this.questionlibImport = new
	 * QuestionlibImport(); } this.questionlibImport.setId(importId);
	 * 
	 * }
	 * 
	 * public String getImportId(){ return
	 * questionlibImport==null?null:questionlibImport.getId(); }
	 */

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public StudentAnswer getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(StudentAnswer studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getMyAnswer0() {
		return myAnswer0;
	}

	public void setMyAnswer0(String myAnswer0) {
		this.myAnswer0 = myAnswer0;
	}

	public String getMyAnswer1() {
		return myAnswer1;
	}

	public void setMyAnswer1(String myAnswer1) {
		this.myAnswer1 = myAnswer1;
	}

	public String getMyAnswer2() {
		return myAnswer2;
	}

	public void setMyAnswer2(String myAnswer2) {
		this.myAnswer2 = myAnswer2;
	}

	public String getMyAnswer3() {
		return myAnswer3;
	}

	public void setMyAnswer3(String myAnswer3) {
		this.myAnswer3 = myAnswer3;
	}

	public String getMyAnswer4() {
		return myAnswer4;
	}

	public void setMyAnswer4(String myAnswer4) {
		this.myAnswer4 = myAnswer4;
	}

	public String getMyAnswer5() {
		return myAnswer5;
	}

	public void setMyAnswer5(String myAnswer5) {
		this.myAnswer5 = myAnswer5;
	}

	public String getMyAnswer6() {
		return myAnswer6;
	}

	public void setMyAnswer6(String myAnswer6) {
		this.myAnswer6 = myAnswer6;
	}

	public String getMyAnswer7() {
		return myAnswer7;
	}

	public void setMyAnswer7(String myAnswer7) {
		this.myAnswer7 = myAnswer7;
	}

	public String getMyAnswer8() {
		return myAnswer8;
	}

	public void setMyAnswer8(String myAnswer8) {
		this.myAnswer8 = myAnswer8;
	}

	public String getMyAnswer9() {
		return myAnswer9;
	}

	public void setMyAnswer9(String myAnswer9) {
		this.myAnswer9 = myAnswer9;
	}

	@Override
	public String toString() {
		return "VersionQuestion [examCode=" + examCode + ", quesType=" + quesType + ", quesLevel=" + quesLevel
				+ ", quesPoint=" + quesPoint + ", title=" + title + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer0 == null) ? 0 : answer0.hashCode());
		result = prime * result + ((answer1 == null) ? 0 : answer1.hashCode());
		result = prime * result + ((answer2 == null) ? 0 : answer2.hashCode());
		result = prime * result + ((answer3 == null) ? 0 : answer3.hashCode());
		result = prime * result + ((answer4 == null) ? 0 : answer4.hashCode());
		result = prime * result + ((answer5 == null) ? 0 : answer5.hashCode());
		result = prime * result + ((answer6 == null) ? 0 : answer6.hashCode());
		result = prime * result + ((answer7 == null) ? 0 : answer7.hashCode());
		result = prime * result + ((answer8 == null) ? 0 : answer8.hashCode());
		result = prime * result + ((answer9 == null) ? 0 : answer9.hashCode());
		result = prime * result + ((checker == null) ? 0 : checker.hashCode());
		result = prime * result + ((choice0 == null) ? 0 : choice0.hashCode());
		result = prime * result + ((choice1 == null) ? 0 : choice1.hashCode());
		result = prime * result + ((choice2 == null) ? 0 : choice2.hashCode());
		result = prime * result + ((choice3 == null) ? 0 : choice3.hashCode());
		result = prime * result + ((choice4 == null) ? 0 : choice4.hashCode());
		result = prime * result + ((choice5 == null) ? 0 : choice5.hashCode());
		result = prime * result + ((choice6 == null) ? 0 : choice6.hashCode());
		result = prime * result + ((choice7 == null) ? 0 : choice7.hashCode());
		result = prime * result + ((choice8 == null) ? 0 : choice8.hashCode());
		result = prime * result + ((choice9 == null) ? 0 : choice9.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((courseKnowledge == null) ? 0 : courseKnowledge.hashCode());
		result = prime * result + ((courseKnowledgeList == null) ? 0 : courseKnowledgeList.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((examCode == null) ? 0 : examCode.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((office == null) ? 0 : office.hashCode());
		result = prime * result + ((quesLevel == null) ? 0 : quesLevel.hashCode());
		result = prime * result + ((quesPoint == null) ? 0 : quesPoint.hashCode());
		result = prime * result + ((quesType == null) ? 0 : quesType.hashCode());
		result = prime * result + ((questionlibId == null) ? 0 : questionlibId.hashCode());
		result = prime * result + ((tearchId == null) ? 0 : tearchId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((versionId == null) ? 0 : versionId.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionQuestion other = (VersionQuestion) obj;
		if (answer0 == null) {
			if (other.answer0 != null)
				return false;
		} else if (!answer0.equals(other.answer0))
			return false;
		if (answer1 == null) {
			if (other.answer1 != null)
				return false;
		} else if (!answer1.equals(other.answer1))
			return false;
		if (answer2 == null) {
			if (other.answer2 != null)
				return false;
		} else if (!answer2.equals(other.answer2))
			return false;
		if (answer3 == null) {
			if (other.answer3 != null)
				return false;
		} else if (!answer3.equals(other.answer3))
			return false;
		if (answer4 == null) {
			if (other.answer4 != null)
				return false;
		} else if (!answer4.equals(other.answer4))
			return false;
		if (answer5 == null) {
			if (other.answer5 != null)
				return false;
		} else if (!answer5.equals(other.answer5))
			return false;
		if (answer6 == null) {
			if (other.answer6 != null)
				return false;
		} else if (!answer6.equals(other.answer6))
			return false;
		if (answer7 == null) {
			if (other.answer7 != null)
				return false;
		} else if (!answer7.equals(other.answer7))
			return false;
		if (answer8 == null) {
			if (other.answer8 != null)
				return false;
		} else if (!answer8.equals(other.answer8))
			return false;
		if (answer9 == null) {
			if (other.answer9 != null)
				return false;
		} else if (!answer9.equals(other.answer9))
			return false;
		if (checker == null) {
			if (other.checker != null)
				return false;
		} else if (!checker.equals(other.checker))
			return false;
		if (choice0 == null) {
			if (other.choice0 != null)
				return false;
		} else if (!choice0.equals(other.choice0))
			return false;
		if (choice1 == null) {
			if (other.choice1 != null)
				return false;
		} else if (!choice1.equals(other.choice1))
			return false;
		if (choice2 == null) {
			if (other.choice2 != null)
				return false;
		} else if (!choice2.equals(other.choice2))
			return false;
		if (choice3 == null) {
			if (other.choice3 != null)
				return false;
		} else if (!choice3.equals(other.choice3))
			return false;
		if (choice4 == null) {
			if (other.choice4 != null)
				return false;
		} else if (!choice4.equals(other.choice4))
			return false;
		if (choice5 == null) {
			if (other.choice5 != null)
				return false;
		} else if (!choice5.equals(other.choice5))
			return false;
		if (choice6 == null) {
			if (other.choice6 != null)
				return false;
		} else if (!choice6.equals(other.choice6))
			return false;
		if (choice7 == null) {
			if (other.choice7 != null)
				return false;
		} else if (!choice7.equals(other.choice7))
			return false;
		if (choice8 == null) {
			if (other.choice8 != null)
				return false;
		} else if (!choice8.equals(other.choice8))
			return false;
		if (choice9 == null) {
			if (other.choice9 != null)
				return false;
		} else if (!choice9.equals(other.choice9))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (courseKnowledge == null) {
			if (other.courseKnowledge != null)
				return false;
		} else if (!courseKnowledge.equals(other.courseKnowledge))
			return false;
		if (courseKnowledgeList == null) {
			if (other.courseKnowledgeList != null)
				return false;
		} else if (!courseKnowledgeList.equals(other.courseKnowledgeList))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (examCode == null) {
			if (other.examCode != null)
				return false;
		} else if (!examCode.equals(other.examCode))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (office == null) {
			if (other.office != null)
				return false;
		} else if (!office.equals(other.office))
			return false;
		if (quesLevel == null) {
			if (other.quesLevel != null)
				return false;
		} else if (!quesLevel.equals(other.quesLevel))
			return false;
		if (quesPoint == null) {
			if (other.quesPoint != null)
				return false;
		} else if (!quesPoint.equals(other.quesPoint))
			return false;
		if (quesType == null) {
			if (other.quesType != null)
				return false;
		} else if (!quesType.equals(other.quesType))
			return false;
		if (questionlibId == null) {
			if (other.questionlibId != null)
				return false;
		} else if (!questionlibId.equals(other.questionlibId))
			return false;
		if (tearchId == null) {
			if (other.tearchId != null)
				return false;
		} else if (!tearchId.equals(other.tearchId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (versionId == null) {
			if (other.versionId != null)
				return false;
		} else if (!versionId.equals(other.versionId))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
		return true;
	}

	/*
	 * private List<String> choiceList=Lists.newArrayList();
	 * 
	 * private List<String> answerList=Lists.newArrayList();
	 * 
	 * 
	 * public List<String> getChoiceList() { return choiceList; } public void
	 * setChoiceList(List<String> choiceList) { this.choiceList = choiceList; }
	 */

}