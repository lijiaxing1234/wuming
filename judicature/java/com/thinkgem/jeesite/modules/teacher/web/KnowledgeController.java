package com.thinkgem.jeesite.modules.teacher.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.Message;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;
import com.thinkgem.jeesite.modules.teacher.entity.StaticKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;
import com.thinkgem.jeesite.modules.teacher.service.KnowledgeService;
import com.thinkgem.jeesite.modules.teacher.service.QuestionslibSplitService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;

/**
 * 老师知识点管理
 *
 */
@Controller("teacherKnowledgeController")
@RequestMapping("${teacherPath}/knowledge")
public class KnowledgeController extends BaseController{
	
	@Autowired
	KnowledgeService knowledgeService;
	
	@Autowired
	QuestionslibSplitService splitService;
	@Autowired
	TestPaperService testPaperService;
	
	@Autowired
	CourseKnowledgeService courseKnowledgeService; //课程版本知识点
	
	@ModelAttribute
    public TeacherKnowledge get(String id){
		return new TeacherKnowledge();
    }
	
	
	
	@RequestMapping(value={"index",""})
	public String index(){
		return "teacher/knowledge/index";
	}
	
	
	@RequestMapping("list")
	public String list(TeacherKnowledge teacherKnowledge,Model model){
		
		List<TeacherKnowledge> list =Lists.newArrayList();
		
		List<TeacherKnowledge> sourcelist=  knowledgeService.findTeacherKnowledgesList(teacherKnowledge);
		
		if(teacherKnowledge.getCourseKnowledge()==null||teacherKnowledge.getCourseKnowledge().getId()==null){
		TeacherKnowledge.sortList(list, sourcelist,"1", true);
		}else{
			TeacherKnowledge.sortList(list, sourcelist,teacherKnowledge.getCourseKnowledge().getId(), true);
		}
		
		/*Collections.sort(list,new Comparator<TeacherKnowledge>() {

			@Override
			public int compare(TeacherKnowledge o1, TeacherKnowledge o2) {
				if(o1!=null && o2 !=null){
					CourseKnowledge c1= o1.getCourseKnowledge();
					CourseKnowledge c2=o2.getCourseKnowledge();
					
					if(c1!=null && c2!=null){
						
						Date d1=c1.getCreateDate();
						Date d2=c2.getCreateDate();
						
						if(d1!=null && d2 !=null){
							
							return d1.compareTo(d2);
						}
					}
					
				}
				return 0;
			}
		});*/
		
		model.addAttribute("list", list);
		model.addAttribute("teacherKnowledge", teacherKnowledge);
		
		Double percent=splitService.getPracticePercentByTeacher();
		if(percent==null){
			percent=100D;
			splitService.insertPracticePercentByTeacher(percent+"");//默认100
		}
		model.addAttribute("percent", percent);
		return "teacher/knowledge/list";
	}
	
	
	/**
	 * 知识点统计
	 * @return
	 */
	@RequestMapping(value={"statistics",""})
	public String statistics(){
		return "teacher/knowledge/statistics";
	}
	
	@ResponseBody
	@RequestMapping("someKnowledgeStatistic")
	public String someKnowledgeStatistic(){
		List<TeacherKnowledge> sourcelist=  knowledgeService.findTeacherKnowledgesListWithPassingRate(new TeacherKnowledge());
		JSONObject jsonObject = new JSONObject();
		List<Double> ylist = new ArrayList<Double>();
		List<String> xlist = new ArrayList<String>();
		if(sourcelist != null && sourcelist.size() >= 3){
			for(int i = 0; i < 3; i++){
				String passingRate = sourcelist.get(i).getPassingRate();
				String knowledgeTitle = sourcelist.get(i).getCourseKnowledge().getTitle();
				ylist.add(Double.parseDouble(passingRate));
				xlist.add(knowledgeTitle);
			}
			
			
			JSONArray xArray = (JSONArray) JSONArray.toJSON(xlist);
			JSONArray yArray = (JSONArray) JSONArray.toJSON(ylist);
			jsonObject.put("xArray", xArray);
			jsonObject.put("yArray", yArray);
			return jsonObject.toString();
		}else if(sourcelist != null){
			for (TeacherKnowledge teacherKnowledge : sourcelist) {
				String passingRate = teacherKnowledge.getPassingRate();
				String title = teacherKnowledge.getCourseKnowledge().getTitle();
				ylist.add(Double.parseDouble(passingRate));
				xlist.add(title);
			}
			JSONArray xArray = (JSONArray) JSONArray.toJSON(xlist);
			JSONArray yArray = (JSONArray) JSONArray.toJSON(ylist);
			jsonObject.put("xArray", xArray);
			jsonObject.put("yArray", yArray);
			return jsonObject.toString();
		}else{
			return null;
		}
		
	}
	
	
	@RequestMapping("statisticsList")
	public String statisticsList(TeacherKnowledge teacherKnowledge,Date beginDate,Date endDate, Model model){
		
		 if(beginDate !=null && endDate !=null){
		   Map<String,Object> param=teacherKnowledge.getSqlParam();
		   param.put("beginDate",beginDate);
		   param.put("endDate", endDate);
		 }
		 List<TeacherKnowledge> list =Lists.newArrayList();
		List<TeacherKnowledge> sourcelist=  knowledgeService.findTeacherKnowledgesListWithPassingRate(teacherKnowledge);
		
		
		if(sourcelist!=null&&sourcelist.size()>0){
			TeacherKnowledge tk=sourcelist.get(0);
			if(tk!=null&&tk.getCourseKnowledge()!=null){
			  CourseKnowledge parent=tk.getCourseKnowledge();
			  TeacherKnowledge.sortList(list, sourcelist,parent.getParentId(), true);
			  model.addAttribute("parent", parent);
			}
		}
		List<String> list1 = new ArrayList<String>();
		List<Double> list2 = new ArrayList<Double>();
		for(int i = 0; i < list.size(); i++){
			String title = list.get(i).getCourseKnowledge().getTitle();
			if(list.get(i).getPassingRate() != null){
				double passingRate = Double.parseDouble(list.get(i).getPassingRate());
				list2.add(passingRate);
			}else{
				list2.add(0.00);
			}
			list1.add(title+"\n");
		}
		JSONArray arr1 = (JSONArray) JSONArray.toJSON(list1);
		JSONArray arr2 = (JSONArray) JSONArray.toJSON(list2);
		model.addAttribute("arr1", arr1);
		model.addAttribute("arr2", arr2);
		model.addAttribute("list", list);
		model.addAttribute("teacherKnowledge", teacherKnowledge);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		return "teacher/knowledge/statisticsList";
	}
	
	@RequestMapping("statisticsList2")
	public String statisticsList2(TeacherKnowledge teacherKnowledge,HttpServletRequest request,HttpServletResponse response,Model model){
		List<TeacherKnowledge> sourcelist = knowledgeService.getKnowledgesPassingRate2(teacherKnowledge);
		model.addAttribute("list", sourcelist);
		return "teacher/knowledge/statisticsList";
	}
	
	
	
	
	@RequestMapping("save")
	public String save(String percent,String ckIdArr,String levelArr,TeacherKnowledge teacherKnowledge,Model model,RedirectAttributes redirectAttributes){
		
		 try{
			 knowledgeService.batchAddTeacherKnowledge(ckIdArr,levelArr);
			 redirectAttributes.addFlashAttribute("message",new Message("保存成功!", Message.Type.SUCCESS));
		 }catch(Exception e){
			 redirectAttributes.addFlashAttribute("message",new Message(e.getMessage(), Message.Type.ERROR));
		 }
		 Object id=Reflections.invokeGetter(teacherKnowledge,"courseKnowledge.id");
		 redirectAttributes.addFlashAttribute("percent", percent);
		return "redirect:" + teacherPath + "/knowledge/list?courseKnowledge.id="+id;
	}
	
	
	@RequestMapping("savePercent")
	public String savePercent(String percent,TeacherKnowledge teacherKnowledge,Model model,RedirectAttributes redirectAttributes){
		
		try{
			//User teacher=TearcherUserUtils.getUser();
			splitService.saveQuestonPercent(percent);
			//redirectAttributes.addFlashAttribute("percent", percent);
			redirectAttributes.addFlashAttribute("message",new Message("保存成功!", Message.Type.SUCCESS));
		}catch(Exception e){
			redirectAttributes.addFlashAttribute("message",new Message(e.getMessage(), Message.Type.ERROR));
		}
		Object id=Reflections.invokeGetter(teacherKnowledge,"courseKnowledge.id");
		return "redirect:" + teacherPath + "/knowledge/list?courseKnowledge.id="+id;
	}
	
	

	/**
	 * 老师知识点
	 * @param extId
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "teacherKnowledgeTreeData")
	public List<Map<String, Object>> teacherKnowledgeTreeData(@RequestParam(required=false) String extId,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TeacherKnowledge> list =knowledgeService.findTeacherKnowledgeList(new TeacherKnowledge());
		for (int i=0; i<list.size(); i++){
			TeacherKnowledge tk = list.get(i);
			CourseKnowledge e=tk.getCourseKnowledge();
			if(e!=null){
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("name", e.getTitle());
					
					if(StringUtils.isNotBlank(tk.getLevel())){
					   map.put("level", tk.getLevel());
					}else{
					   map.put("level", e.getLevel());
					}
					
					mapList.add(map);
				}
			}
		}
		return mapList;
	}
}
