package com.thinkgem.jeesite.modules.resource.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;
import com.thinkgem.jeesite.modules.resource.entity.TablePublicationResource;
import com.thinkgem.jeesite.modules.resource.service.CategoryService;
import com.thinkgem.jeesite.modules.resource.service.ResourceService;
import com.thinkgem.jeesite.modules.resource.util.StringUtils;

/**
 * 分类管理
 */
@Controller(value="tableResourceController")
@RequestMapping("${adminPath}/resource")
public class ResourceController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private CategoryService categoryService;
	
	
	@RequiresPermissions("questionlib:resource:view")
	@RequestMapping("index")
	public String index(){
		return "modules/resource/res/resIndex";
	}
	
	@RequiresPermissions("questionlib:resource:view")
	@RequestMapping("list")
	public String list(TablePublicationResource resource,HttpServletRequest request, HttpServletResponse response, Model model){
		List<TableCategory> list=categoryService.getCategoryAll();//所有资源分类
		model.addAttribute("list",list);
		Page<TablePublicationResource> page = resourceService.getResourceList(new Page<TablePublicationResource>(request, response),resource);
		model.addAttribute("page",page);
		model.addAttribute("resource",resource);
		return "modules/resource/res/resList";
	}

	@RequiresPermissions("questionlib:resource:view")
	@RequestMapping("form")
	public String form(TablePublicationResource resource,HttpServletRequest request, HttpServletResponse response, Model model){
		if(StringUtils.isNotEmpty(resource.getId())){
			resource=resourceService.selectResourceById(resource.getId());
		}
		List<TableCategory> list=categoryService.getCategoryAll();//所有资源分类
		model.addAttribute("list",list);
		model.addAttribute("resource",resource);
		return "modules/resource/res/resForm";
	}
	
	@RequiresPermissions("questionlib:resource:edit")
	@RequestMapping("save")
	public String categorySave(TablePublicationResource resource,Model model,HttpServletRequest request) {
		resourceService.insertCategory(resource);
/*		UploadUtils upload = new UploadUtils();
		try {
				if (files.getBytes().length != 0) {
					String filePath = PropertieUtils.getProperty("category.uploadDir");
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
									filePath+ PropertieUtils.getProperty("resource.path")
										+ sdf.format(date)
											+resource.getId()
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
						String imageUrl =PropertieUtils.getProperty("resource.path") +
								sdf.format(date)
								+ resource.getId()
								+ originalFileName.substring(
										originalFileName.lastIndexOf('.'),
										originalFileName.length());
						resource.setFilePath(imageUrl);
						resource.setFileSize(files.getSize()+"");
					
				}
				resourceService.insertCategory(resource);
		}  catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return "redirect:"+adminPath+"/resource/list?categoryId="+resource.getCategoryId();
	}
	
	@RequiresPermissions("questionlib:resource:edit")
	@RequestMapping("delete")
	public String categoryDeletee(String resId,Model model,HttpServletRequest request) {
		resourceService.delete(resId);
		return "redirect:"+adminPath+"/resource/list";
	}
}
