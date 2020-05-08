<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
	 
	 var reloadParent ="${reloadParent}";
	 
	    if(!!reloadParent)
		{
	    	 alertx("导入试题成功数：${saveSuccess},导入试题失败数：${saveFail}  ,  ${failMessage} ",function(){
	    		   
	 		    try{
	 		    	top.$.jBox.closeTip();
	 		    	top.$.jBox.close(true);
	 		    }catch(e){
	 		    	
	 		    }
	    		 
	    	 });
	    	// alert("试题导入成功 ！");
		   // window.parent.window.jBox.close();
		 
		    
		    
		}else
		{
			
		   if("${message}"=="success")
			   {
					   alertx("导入试题成功数：${saveSuccess},导入试题失败数：${saveFail}  ,  ${failMessage} ",function(){
	    		   
			 		    try{
			 		    	top.$.jBox.closeTip();
			 		    	top.$.jBox.close(true);
			 		    }catch(e){
			 		    	
			 		    }
		    		 
		    	 });
			   }
		}
	   
		$(document).ready(function() {
			$("#title").focus();
			$("#saveImportDocMessage").validate({
				submitHandler: function(form){
				 
					if($("#wordFile").val()=="")
					{
					   alertx("请选择试题文件！");
					    return ;
					}
				 
					loading('正在上传文件，请稍等...');
					form.submit();
					$("#importTable").hide();
					
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	
	<%-- <sys:message content="${message}"/> --%>
		   <form id="saveImportDocMessage" action="${ctx}/school/schoolversionQuestion/importQuestionFileNew?courseQuestionlibId=${courseQuestionlibId}" method="post" enctype="multipart/form-data">
			<table style="margin: 0 auto;" id="importTable" >
				<tr>
					<th colspan="2" class="control-label"><h3>专业/课程/版本/题库导入</h3></th>
				</tr>
				<tr>
					<td colspan="2" style="height: 10px;"> </td>
				</tr>
				
				<tr>
					<%-- <th>上传者部门：</th>
					<td>
						<sys:treeselect id="office" name="office.id" value="${questionlibImport.office.id}" labelName="office.name" labelValue="${questionlibImport.office.name}"
						title="部门" url="/sys/office/treeData?type=2" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>
					</td> --%>
					<th class="control-label">主编：</th>
					<td><input id ="writer" name="writer" type="text" value="${questionlibImport.writer }" class="input-medium required"  style="width:210px;"/></td>
				</tr>
				 
				<tr>
					<th class="control-label">建设院校：</th>
					<td>
						<sys:treeselect id="school" name="school.id" value="${questionlibImport.school.id}" labelName="school.name" labelValue="${questionlibImport.school.name}"
					title="学校" url="/sys/office/treeData?type=2" cssClass="input-medium required" allowClear="true" notAllowSelectParent="fasle"/>
					</td>
					<th></th>
					<td></td>
				</tr>
				
					<tr>
					<th class="control-label">上传者姓名：</th>
					<td>
						${fns:getUser().name}
						 
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th class="control-label">选择试题文件：</th>
					<td>
					<input id="wordFile" name="wordFile" type="file" class="input-medium"  accept=".doc"  style="width:160px !important;text-indent: 0px;line-height: 0;padding-top: 14px!important"   class="input-medium required" />
					<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
				<td></td>
					<td style="height: 40px;align:center;"><input id="saveImportMessage" class="btn btn-primary" type="submit" value="开始导入"/>
					<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="window.parent.window.jBox.close();"/></td>
				</tr>
			</table>
		</form>
</body>
</html>