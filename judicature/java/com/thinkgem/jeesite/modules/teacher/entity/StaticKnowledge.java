package com.thinkgem.jeesite.modules.teacher.entity;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 知识点统计
 *@author flychao
 */
public class StaticKnowledge extends DataEntity<StaticKnowledge>{

	private static final long serialVersionUID = 1L;
	
	String  knowId;
	String  knowTitle;
	String  knowParentId;
	String  correctRate; //全班的答对率
	Integer totalCount;  //总题数
 	Integer correctCount; //答对的题数
	
	
	public String getKnowId() {
		return knowId;
	}
	public void setKnowId(String knowId) {
		this.knowId = knowId;
	}
	public String getKnowTitle() {
		return knowTitle;
	}
	public void setKnowTitle(String knowTitle) {
		this.knowTitle = knowTitle;
	}
	public String getCorrectRate() {
		return correctRate;
	}
	public void setCorrectRate(String correctRate) {
		this.correctRate = correctRate;
	}
	
	public String getKnowParentId() {
		return knowParentId;
	}
	public void setKnowParentId(String knowParentId) {
		this.knowParentId = knowParentId;
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
	
	
	public static void sortList(List<StaticKnowledge> list, List<StaticKnowledge> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			StaticKnowledge e = sourcelist.get(i);
			if (StringUtils.isNoneBlank(e.getKnowParentId())&& e.getKnowParentId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						StaticKnowledge child = sourcelist.get(j);
						if (StringUtils.isNoneBlank(child.getKnowParentId())
								&& child.getKnowParentId().equals(e.getKnowId())){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}
	
	
}
