<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	    var reloadParent ="${reloadParent}";
	    if(!!reloadParent){
	    	 alertx("导入试题成功数：${saveSuccess},导入试题失败数：${saveFail}  ,  ${failMessage} ",function(){
	 		  /*  try{
	 		    	parent.$.jBox.closeTip();
	 		    	top.$.jBox.close(true);
	 		    }catch(e){
	 		    	
	 		    } */
	 		    
	 		   try{  var obj=top.frames['teacherMainFrame']; 	
	 		         if(!!!obj){	obj=top;}	
	 		         obj.$.jBox.closeTip();
	 		         obj.$.jBox.close(true);
	 		   }catch(e){ }
	    		 
	    	 });
		}else{
		   if("${message}"=="success") {
			   alertx("导入试题成功数：${saveSuccess},导入试题失败数：${saveFail}  ,  ${failMessage} ",function(){
				   try{  var obj=top.frames['teacherMainFrame']; 	
		 		         if(!!!obj){	obj=top;}	
		 		         obj.$.jBox.closeTip();
		 		         obj.$.jBox.close(true);
		 		   }catch(e){ }
		       });
		   }
		}
	
	
		$(document).ready(function() {
			
			$("#title").focus();
			$("#saveImportDocMessage").validate({
				submitHandler: function(form){
				 
					if($("#wordFile").val()==""){
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
					}else if(element.parent().is("span")){
						error.appendTo(element.parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	 <form id="saveImportDocMessage" action="${ctx}/examQues/importQuestionFileNew" method="post" enctype="multipart/form-data">
		<table  style="margin: 0 auto;">
			<tr>
				<th colspan="4" class="control-label"><h3>试题导入</h3></th>
			</tr>
			<tr>
				<td colspan="4" style="height: 10px;"></td>
			</tr>
			<tr>
				<th align="right">题库：</th>
				<td>
				  
				  <span>
					  <select id="questionlibId" name="questionlibId" class="input-medium required" style="width: 220px;margin-bottom: 10px;">
							<c:forEach items="${quesLibs}" var="specialty">
								<option value="${specialty.id}">${specialty.title}</option>
							</c:forEach>
					  </select>
					  <span class="help-inline"><font color="red">*</font> </span>
				  </span>
			   </td>
			</tr>
			<tr>
				<th align="right">文档名称：</th>
				<td>
				     <span>
					  <input id="title" name="title" type="text" value="" class="input-medium required" style="width: 220px;" />
						 <span class="help-inline"><font color="red">*</font> </span>
					  </span>
				</td>
			</tr>
			<tr>
				<th align="right">主编：</th>
				<td>
					<span>
					    <input id="writer" name="writer" type="text"  value="" class="input-medium required" style="width: 220px;" />
					    <span class="help-inline"><font color="red">*</font> </span>
					</span>
				</td>
			</tr>
			<tr>
				<th align="right">上传者姓名：</th>
				<td>
					<input name="user.name" value="${questionlibImport.user.name}" type="text" 	readonly="readonly"  class="input-medium requried"/>
				</td>
			</tr>
			<tr>
				<th align="right">层次：</th>
				<td>
				    <input name="coursePhase" type="radio" value="1"  />高职 
				    <input name="coursePhase" type="radio" value="2"  />培训
				    <input name="coursePhase" type="radio" value="3" />本科
				</td>
				<td></td>
			</tr>
			
			<tr>
				<th class="control-label">选择试题文件：</th>
				<td>
				<input id="wordFile" name="wordFile" type="file" class="input-medium"  accept=".doc"  style="width:210px;"   class="input-medium required" />
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
		</table>

		<table style="margin: 0 auto;" id="importTable">
			<tr>
				<td style="height: 40px; align: center;">
				 <input type="submit"  value="保存" class="btn tbtn-primary control-label">
				 <input id="closeImportQuestion" class="btn tbtn-primary control-label" 	type="button" value="返回"
					onclick=" try{  var obj=top.frames['teacherMainFrame']; 	if(!!!obj){	obj=top;}	obj.$.jBox.closeTip();obj.$.jBox.close(true); }catch(e){ }" /></td>
			</tr>
		</table>
	</form>
	<hr>
</body>
</html>