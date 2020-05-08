/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, @RequestParam(required=false) String extId, Model model) {
		List<Office> list = null;
		
		
		
		if(StringUtils.isBlank(office.getParentIds())){
			if(StringUtils.isBlank(extId)){
				if(!UserUtils.getUser().getId().equals("1")) //非超级管理员
				{
					extId = UserUtils.getUser().getOffice().getParentIds();
				}else
				{
					extId = "0,";
				}
				
				
			}
			office.setParentIds(extId);
			list = officeService.findList(office);
		}else{
			System.out.println(extId);
			if(StringUtils.isBlank(extId) && !"0".equals(extId)){
				extId = office.getParentIds()+office.getId();
			}
			System.out.println(extId);
			Office offices = new Office();
			offices.setParentIds(extId);
			list = officeService.findList(offices);
		}
		if(list.size()<1){
			if(office.getName()!=null)
			{
				model.addAttribute("message", office.getName()+"下无分支机构！");
			}
			
		}else{
			for (Office office2 : list) {
				String type=office2.getType();
				if(("1").equals(type)){
					office2.setType("机构");
				}
				if(("2").equals(type)){
					office2.setType("部门");
				}
				
				office2.setUseable(DictUtils.getDictLabel(office2.getUseable(), "yes_no", ""));
				
				
			}
		}
		model.addAttribute("extId", extId);
        model.addAttribute("list", list);
		return "modules/sys/officeList";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office,@RequestParam(required=false) String extId, Model model) {
		/*User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			//office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
		//	office.setArea(user.getOffice().getArea());
		}*/
		// 自动获取排序号
	/*	if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}*/
		
		try{
		    if(office.getParent()!=null && StringUtils.isNotBlank(office.getParent().getId())&&!"0".equals(office.getParent().getId())){//根节点没有父id
				Office parentOffice=officeService.get(office.getParent().getId());
				if(parentOffice!=null){
					office.setParent(parentOffice);
					office.setArea(parentOffice.getArea());
				}
			}
		}catch(Exception e){
			
		}
		model.addAttribute("extId", extId);
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, @RequestParam(required=false) String extId, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office,extId, model);
		}
		officeService.save(office);
		
		/*if(office.getChildDeptList()!=null){
			Office childOffice = null;
			int sort =1;
			try {
				sort =office.getSort();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				childOffice.setSort(sort);
				officeService.save(childOffice);
			}
		}else
		{
			officeService.save(office);	

		}*/
		
		System.out.println(extId);
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?repage&extId="+extId;
/*		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds()+"&extId="+extId;
*/	}
	
	@RequiresPermissions("sys:office:delete")
	@RequestMapping(value = "delete")
	public String delete(Office office, @RequestParam(required=false) String extId, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
/*//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除"+office.getName()+"成功");
//		}
*/		
		int result=officeService.checkAndDelete(office);
		if(result>0){
			addMessage(redirectAttributes, "删除机构成功");
		}else{
			addMessage(redirectAttributes,"删除机构失败,请先删除【"+office.getName()+"】下及所有子机构下的用户后重试");
		}
		
		return "redirect:" + adminPath + "/sys/office/list?repage&extId="+extId;
/*		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds()+"&extId="+extId;
*/	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,  @RequestParam(required=false) String pId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response, HttpServletRequest request) {
		List<Office> list = null;
	  
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if (StringUtils.isNotBlank(pId)&&StringUtils.isNotBlank(type)&&"2".equals(type)) {
			
			 list = officeService.findList(isAll,pId);
		}else {
			 list = officeService.findList(isAll);
		}
		 
	 
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					){//&& Global.YES.equals(e.getUseable())
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	public static void sortList(List<Office> list, List<Office> sourcelist,
			String string) {
		for (int i = 0; i < sourcelist.size(); i++) {
			Office e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null
					&& e.getParent().getId().equals(string)) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					Office child = sourcelist.get(j);
					if (child.getParent() != null
							&& child.getParent().getId() != null
							&& child.getParent().getId().equals(e.getId())) {
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
}
