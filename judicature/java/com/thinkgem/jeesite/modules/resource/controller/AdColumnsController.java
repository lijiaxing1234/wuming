package com.thinkgem.jeesite.modules.resource.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.resource.entity.LVCourse;
import com.thinkgem.jeesite.modules.resource.entity.TableAD;
import com.thinkgem.jeesite.modules.resource.entity.TableADColumns;
import com.thinkgem.jeesite.modules.resource.service.ADService;
import com.thinkgem.jeesite.modules.resource.service.LVCourseService;
import com.thinkgem.jeesite.modules.web.dto.MTCourse;
import com.thinkgem.jeesite.modules.web.dto.MTJSON;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.service.CourseWebService;
import com.thinkgem.jeesite.modules.web.util.mtcloud.MTCloud;

@Controller(value="adColumnsController")
@RequestMapping(value ="${adminPath}/ad")
public class AdColumnsController extends BaseController {
	@Autowired
	private ADService adService;
	
	@Autowired
	CourseWebService courseService;
	
	@Autowired
	LVCourseService lVcourseService;
	
	@RequiresPermissions("nas:ad:view")
	@RequestMapping(value = "adColumnlist")
	public String list(TableADColumns adColumn, Model model) {
		List<MTCourse> mtList = new ArrayList<MTCourse>();
		MTCloud client = new MTCloud(); 
		MTJSON<MTCourse>  mtJson=null;
		List<Course> allCourse = courseService.getAllCourse();
		List<String> allLvId = lVcourseService.getAllLvId();
		if (allLvId.size()>0) {
			for (int i = 0; i < allLvId.size(); i++) {
				String courseGet;
				try {
					courseGet = client.courseGet(allLvId.get(i));
					mtJson = JSON.parseObject(courseGet, new TypeReference<MTJSON<MTCourse>>() {});
					MTCourse data = mtJson.getData();
					mtList.add(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("LVList", mtList);
		model.addAttribute("adColumn", adColumn);
		TableAD ad=new TableAD();
		model.addAttribute("ad",ad);
		model.addAttribute("course",allCourse);
		return "modules/resource/ad/adColumnList";
	}
	
	@RequiresPermissions("nas:ad:view")
	@RequestMapping(value = "getAdColumnAjax")
	public String getAdColumnAjax(TableADColumns adColumn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TableADColumns> page = adService.find(new Page<TableADColumns>(request,response), adColumn);
		model.addAttribute("page", page);
		return "modules/resource/ad/adColumnAjax";
	}
	
	@RequiresPermissions("nas:ad:edit")
	@RequestMapping(value = "form")
	public String form(TableADColumns adColumn, Model model) throws Exception {
		if(!(StringUtils.isEmpty(adColumn.getId()))){
			adColumn=adService.selectADById(adColumn.getId());
		}
		model.addAttribute("adColumn", adColumn);
		return "modules/resource/ad/adColumnForm";
	}
	
	@RequiresPermissions("nas:ad:edit")
	@RequestMapping(value = "addADColumn")
	public String addADColumn(TableADColumns adColumn) {
		if (adColumn != null) {
			if (StringUtils.isEmpty(adColumn.getId())) {
				adService.addADColumn(adColumn);		
			} else {
				adService.updateADColumn(adColumn);
			}
		}else{
			return "redirect:" +adminPath+ "/ad/form";
		}
		return "redirect:" +adminPath+ "/ad/adColumnlist";
	}
	/**
	 * 判断该广告位是否存在
	 */
	@ResponseBody
	@RequestMapping(value ="checkADColumnCode")
	public String checkADColumnCode(String adCode) {
		TableADColumns adColumn = adService.getADColumnByCode(adCode);
		if (adColumn == null) {
			return "true";
		} else {
			return "false";
		}
	}
	@RequiresPermissions("nas:ad:edit")
	@ResponseBody
	@RequestMapping(value = "deleteADColumn")
	public void deleteADColumn(TableADColumns adColumn,HttpServletRequest request, HttpServletResponse response) throws IOException {
		int sum = adService.selectADCountById(adColumn.getId());
		String str = "";
		if (sum == 0) {
			int n = adService.deleteADColumn(adColumn.getId());
			if (n > 0) {
				str = "true";
			} else {
				str = "false";
			}
		} else {
			str = "false";
		}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(str);
	}
	
	@RequiresPermissions("nas:ad:edit")
	@RequestMapping(value = "adlist")
	public String adlist(TableAD ad, Model model,HttpServletRequest request, HttpServletResponse response) {
		Page<TableAD> page = adService.SelectADList(new Page<TableAD>(request, response),ad);
		model.addAttribute("page", page);
		model.addAttribute("ad", ad);
		return "modules/resource/ad/addList";
	}
	
	@RequiresPermissions("nas:ad:edit")
	@RequestMapping(value = "addAD", method = RequestMethod.POST)
	public String addAD(@RequestParam(value = "uploadFile", required = false) MultipartFile file,@ModelAttribute TableAD ad)throws IOException {
		String target = ad.getTarget();
		int indexOf = target.indexOf(",");
		if (indexOf != -1) {
			String[] split = target.split(",");
			for (int i = 0; i < split.length; i++) {
				if (split[i].length()>1) {
					ad.setTarget(split[i]);
				}
			}
		}
		try {
			if (file.getBytes().length != 0) {
				String filePath = Global.getConfig("category.uploadDir")
						+ Global.getConfig("adname");
				String originalFileName = file.getOriginalFilename();
				SimpleDateFormat sdf = new SimpleDateFormat("HHmmssS");
				Date date = new Date();
				File file1 = new File(filePath);
				if (!file1.exists()) {
					file1.mkdir();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(
					filePath+ad.getColId()+sdf.format(date)+originalFileName.substring(originalFileName.lastIndexOf('.'),originalFileName.length()));
				fileOutputStream.write(file.getBytes());
				fileOutputStream.flush();
				fileOutputStream.close();
				String imageUrl =Global.getConfig("adname")+ad.getColId()+sdf.format(date)+originalFileName.substring(originalFileName.lastIndexOf('.'),originalFileName.length());
				ad.setImageUrl(imageUrl);
			}
		this.adService.addAd(ad);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "modules/resource/ad/adUrl";
	}
	
	@RequiresPermissions("nas:ad:edit")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = "delete")
	public void delete(String adId){
		adService.delete(adId);
	}
	
	@ResponseBody
	@RequestMapping(value="updateUpad")
	public String updateUpad(String str1,String str2){	
		TableAD adFirst=new TableAD();
		String[] arr1=str1.split(",");	
		String[] arr2=str2.split(",");	
		adFirst.setId(arr1[0]);
		adFirst.setSeq(Integer.parseInt(arr2[1]));
		adService.update(adFirst);
		
		TableAD adSecond=new TableAD();			
		adSecond.setId(arr2[0]);
		adSecond.setSeq(Integer.parseInt(arr1[1]));
		adService.update(adSecond);
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="updateDownad")
	public String updateDownad(String str1,String str2){	
		TableAD adFirst=new TableAD();
		String[] arr1=str1.split(",");	
		String[] arr2=str2.split(",");	
		adFirst.setId(arr1[0]);
		adFirst.setSeq(Integer.parseInt(arr2[1]));
		adService.update(adFirst);
		
		TableAD adSecond=new TableAD();			
		adSecond.setId(arr2[0]);
		adSecond.setSeq(Integer.parseInt(arr1[1]));
		adService.update(adSecond);
		
		return "success";
	}
}
