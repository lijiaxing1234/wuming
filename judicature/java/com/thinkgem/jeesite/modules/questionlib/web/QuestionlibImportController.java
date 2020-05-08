/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.QuestionlibImport;
import com.thinkgem.jeesite.modules.questionlib.entity.TableQuestionlibImportApply;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.QuestionlibImportService;
import com.thinkgem.jeesite.modules.questionlib.service.TableQuestionlibImportApplyService;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 试题导入题库-批量导入试题Controller
 * @author ryz
 * @version 2016-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/questionlibImport")
public class QuestionlibImportController extends BaseController {

	@Autowired
	private QuestionlibImportService questionlibImportService;
	
	@Autowired
	private VersionQuestionService versionQuestionService;
	@Autowired
	TableQuestionlibImportApplyService quesImportApplyService;
	
	@ModelAttribute
	public QuestionlibImport get(@RequestParam(required=false) String id) {
		QuestionlibImport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionlibImportService.get(id);
		}
		if (entity == null){
			entity = new QuestionlibImport();
		}
		return entity;
	}
	
	@RequiresPermissions("questionlib:questionlibImport:view")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionlibImport questionlibImport,@RequestParam(required=false) String delFlag1, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null==delFlag1 || "".equals(delFlag1)){
			if(questionlibImport!=null && questionlibImport.getDelFlag()!=null){
				delFlag1 = questionlibImport.getDelFlag();
			}else{
				delFlag1 = "2";
				
			}
		}
		questionlibImport.setDelFlag(delFlag1);
		/*if("0".equals(delFlag1)){
			delFlag1 = "2";	//打回重审、批量重审
		}else if("1".equals(delFlag1)){
			delFlag1 = "1";	//重新审核、批量重审
		}else if("2".equals(delFlag1)){
			delFlag1 = "0";	//审核通过、批量审核
		}*/
		questionlibImport.setOffice(UserUtils.getUser().getCompany());
		Page<QuestionlibImport> page = questionlibImportService.findPage(new Page<QuestionlibImport>(request, response), questionlibImport); 
		model.addAttribute("page", page);
		model.addAttribute("delFlag1", delFlag1);
		
		return "modules/questionlib/questionlibImportList";
	}

	@RequiresPermissions("questionlib:questionlibImport:view")
	@RequestMapping(value = "form")
	public String form(QuestionlibImport questionlibImport, Model model) {
		model.addAttribute("questionlibImport", questionlibImport);
		return "modules/questionlib/questionlibImportForm";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "save")
	public Map<String,String> save(@RequestParam(required=false) String courseQuestionlibId, QuestionlibImport questionlibImport, Model model, HttpServletResponse response) {
		HashMap<String, String> map = Maps.newHashMap();
		if(StringUtils.isNotBlank(courseQuestionlibId)){
			questionlibImport.setQuestionlibId(courseQuestionlibId);
		}else{
			map.put("message", "保存失败！");
			map.put("importId", "");
			return map;
		}
		if (null != questionlibImport.getSpecialty()) {
			questionlibImport.setSpecialtyId(questionlibImport.getSpecialty().getId());
		}
		questionlibImportService.save(questionlibImport);
		map.put("message", "保存成功！");
		map.put("importId", questionlibImport.getId());
		return map;
	}
	
	@RequiresPermissions({"questionlib:questionlibImport:delete","questionlib:questionlibImport:auditor"})
	@RequestMapping(value = "delete")
	public String delete(QuestionlibImport questionlibImport,String[] ids,@RequestParam(required=false) String delFlag1, RedirectAttributes redirectAttributes) {
		 
	    String status="0";
	     
		for (int i = 0, len = ids.length; i < len; i++) {
			QuestionlibImport qli = questionlibImportService.get(ids[i]);
			status =qli.getDelFlag();
			qli.setDelFlag(delFlag1);
			questionlibImportService.save(qli);
			VersionQuestion versionQuestion = new VersionQuestion();
			versionQuestion.setImportId(ids[i]);
			versionQuestion.setDelFlag(delFlag1);
			versionQuestionService.updateQuestionDelFlagByImportId(versionQuestion);
		}
	 
		/*if("0".equals(delFlag)){
			delFlag = "2";	//打回重审、批量重审
		}else if("1".equals(delFlag)){
			delFlag = "2";	//重新审核、批量重审
		}else if("2".equals(delFlag)){
			delFlag = "0";	//审核通过、批量审核
		}*/
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/questionlibImport/?repage&delFlag1="+status;
	}

	
	
	@RequiresPermissions({"questionlib:questionlibImport:intoPlatform"})
	@RequestMapping("intoPlatform")
	public String  intoPlatformBtn(String quesImportId,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		try{
			if(StringUtils.isNotBlank(quesImportId)){
				TableQuestionlibImportApply tableQuestionlibImportApply =new TableQuestionlibImportApply();
				tableQuestionlibImportApply.setCreateDate(new Date());
				tableQuestionlibImportApply.setSchoolId(UserUtils.getUser().getCompany().getId());
				tableQuestionlibImportApply.setQueslibImportId(quesImportId);
				tableQuestionlibImportApply.setStatus(1);
				quesImportApplyService.save(tableQuestionlibImportApply);
				addMessage(redirectAttributes, "操作成功");
			}else{
				addMessage(redirectAttributes, "参数丢失");
			}
		}catch(Exception e){
		  addMessage(redirectAttributes, "操作失败");
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/questionlibImport/?repage";
	}
	
	
	
	
	
	
	/*@ResponseBody
	@RequestMapping(value = "loadWordDoc")
	public String loadWordDoc(QuestionlibImport questionlibImport,HttpServletRequest request, HttpServletResponse response) {
		OutputStream os = null;  
		HashMap<String, String> map = Maps.newHashMap();
		if(questionlibImport==null || questionlibImport.getId()==null || questionlibImport.getFilePath()==null){
			map.put("message", "1");	//未发现该文档，请重新选择！
			return JSON.toJSONString(map);
		}
		String prefixPath = request.getSession().getServletContext().getRealPath("/");
		String filePath = prefixPath + questionlibImport.getFilePath();
		String docName = questionlibImport.getDocName();
		String contentType = "";
		System.err.println(filePath+docName);
		try{
			if(StringUtils.isBlank(docName)){
				docName = filePath.substring(0,filePath.lastIndexOf('/'));
				docName = docName.substring(docName.lastIndexOf('/')+1) + ".docx";
			}
//			os = response.getOutputStream();
			contentType = docName.substring(docName.lastIndexOf('.'));
//		     response.setHeader("Content-Disposition", "attachment; filename="+docName); 
//		     if(".doc".equals(contentType) || ".docx".equals(contentType)){
//		    	 response.setContentType("application/msword"); 
//		     }
//	        os.write(FileUtils.readFileToByteArray(new File(filePath,docName)));              
//	        os.flush();  
	        map.put("message", "2");	//下载成功！
	        map.put("file", filePath+docName);
	        System.err.println(filePath+docName);
//	        return JSON.toJSONString(map);
		}catch(Exception e){
//			map.put("message", "下载成功！");	//下载成功！
//	        map.put("file", filePath+docName);
//	        return JSON.toJSONString(map);
//			try {
//				os = response.getOutputStream();
//				contentType = docName.substring(docName.lastIndexOf('.'));
//				 response.setHeader("Content-Disposition", "attachment; filename="+docName); 
//				 if(".doc".equals(contentType) || ".docx".equals(contentType)){
//				 response.setContentType("application/msword"); 
//				 }
//				os.write(FileUtils.readFileToByteArray(new File(filePath,docName)));              
//				os.flush();
//				map.put("message", "1");	//下载成功！
////				return JSON.toJSONString(map);
//			} catch (IOException e1) {
//				map.put("message", "2");	//下载文档不存在！
//			}  
		}finally {  
//	        if (os != null) {  
//	            try {os.close();} catch (IOException e) {e.printStackTrace();}  
//	        }  
	    }
		map.put("message", "3");	//下载失败！
		return JSON.toJSONString(map);
	}*/
	/*@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }*/
	@RequiresPermissions("questionlib:questionlibImport:download")
	@ResponseBody
	@RequestMapping(value = "loadWordDoc")
	public String loadWordDoc(QuestionlibImport questionlibImport,HttpServletRequest request, HttpServletResponse response) {
		OutputStream os = null;  
		HashMap<String, String> map = Maps.newHashMap();
		if(questionlibImport==null || questionlibImport.getId()==null || questionlibImport.getFilePath()==null){
			map.put("message", "1");	//未发现该文档，请重新选择！
			return JSON.toJSONString(map);
		}
		String prefixPath = request.getSession().getServletContext().getRealPath("");
		String filePath = prefixPath + questionlibImport.getFilePath();
		String docName = questionlibImport.getTitle();
		String contentType = "";
		System.err.println(filePath+docName);
		try{
			if(StringUtils.isBlank(docName)){
				docName = filePath.substring(0,filePath.lastIndexOf('/'));
				//docName = docName.substring(docName.lastIndexOf('/')+1) + ".docx";
			}
		//	docName = docName.substring(docName.lastIndexOf('/')+1) + ".docx";
			contentType = docName.substring(docName.lastIndexOf('.'));
	        map.put("message", "2");	//下载成功！
	        map.put("file", filePath+docName);
	        System.err.println(filePath+docName);
			return JSON.toJSONString(map);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println();
		}finally {  
	    }
		map.put("message", "3");	//下载失败！
		return JSON.toJSONString(map);
	}
}