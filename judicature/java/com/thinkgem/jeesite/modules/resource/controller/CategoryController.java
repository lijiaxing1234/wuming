package com.thinkgem.jeesite.modules.resource.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.UploadUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;
import com.thinkgem.jeesite.modules.resource.service.CategoryService;

/**
 * 分类管理
 */
@Controller(value="tableCategoryController")
@RequestMapping("${adminPath}/category")
public class CategoryController extends BaseController {
	@Autowired
	private CategoryService categoryService;
	
	@RequiresPermissions("questionlib:category:view")
	@RequestMapping("list")
	public String categoryList(TableCategory category,Model model) {
		List<TableCategory> list=categoryService.getCategoryAll();
		model.addAttribute("list",list);
		return "modules/resource/category/categoryList";
	}
	
	@RequiresPermissions("questionlib:category:edit")
	@RequestMapping("form")
	public String categoryForm(TableCategory category,Model model) {
		if(StringUtils.isEmpty(category.getId())){
			category=new TableCategory();
		}else{
			category=categoryService.getTableCategoryById(category.getId());
		}
		List<TableCategory> list=categoryService.getCategoryAll();
		model.addAttribute("list",list);
		model.addAttribute("category",category);
		return "modules/resource/category/categoryForm";
	}
	
	/**
	 * 分类树
	 * @param extId
	 * @param courseId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TableCategory> list=categoryService.getCategoryAll();
		//list.add(courseKnowledgeService.get("1"));
		for (int i=0; i<list.size(); i++){
			TableCategory e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
	
	@RequiresPermissions("questionlib:category:edit")
	@RequestMapping("save")
	public String categorySave(MultipartFile files,TableCategory category,Model model,HttpServletRequest request) {
		UploadUtils upload = new UploadUtils();
		try {
				if (files.getBytes().length != 0) {
					String filePath = Global.getConfig("category.uploadDir");
					String originalFileName = files.getOriginalFilename();
					SimpleDateFormat sdf = new SimpleDateFormat("HHmmssS");
					Date date = new Date();
					File file1 = new File(filePath);
					if (!file1.exists()) {
						file1.mkdir();
					}
						FileOutputStream fileOutputStream = null;
						try {
							fileOutputStream = new FileOutputStream(
									filePath + Global.getConfig("category.path")
										+ sdf.format(date)
											+category.getId()
											+ originalFileName.substring(
													originalFileName.lastIndexOf('.'),
													originalFileName.length()));
							fileOutputStream.write(files.getBytes());
							fileOutputStream.flush();
							fileOutputStream.close();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}catch (IOException e) {
							e.printStackTrace();
						}
						String imageUrl =Global.getConfig("category.path")+
								sdf.format(date)
								+ category.getId()
								+ originalFileName.substring(
										originalFileName.lastIndexOf('.'),
										originalFileName.length());
						category.setImage(imageUrl);
					
				}
				categoryService.insertCategory(category);
		}  catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:"+adminPath+"/category/list";
	}
	
	@RequiresPermissions("questionlib:category:edit")
	@RequestMapping("delete")
	public String categoryDeletee(String cateId,Model model,HttpServletRequest request) {
		categoryService.delete(cateId);
		return "redirect:"+adminPath+"/category/list";
	}
}
