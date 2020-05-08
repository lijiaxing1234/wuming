<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
	
	<sys:message content="${message}"/>
		<form id="saveImportDocMessage" action="" method="post">
			<input type="hidden" name="id" value="${questionlibImport.id}">
			<input type="hidden" id="questionlibId" name="questionlibId" value="${courseQuestionlibId }">
			<table id="importTable" style="margin: 0 auto;" >
				<tr>
					<th colspan="4" class="control-label"><h3>试题导入</h3></th>
				</tr>
				<tr>
				<td colspan="4" style="height: 10px;"> </td>
				</tr>
				<tr>
				<th class="control-label">文档名称：</th>
					<td><input id="title" name="title" type="text" value="${questionlibImport.title }" class="input-medium"  style="width:220px;"/>
					<font id="titleMessage" color="red">* </font>
					</td>
				</tr>
				<tr>
					<th class="control-label">主编：</th>
					<td><input id="writer" name="writer" type="text" value="${questionlibImport.writer }" class="input-medium"  style="width:220px;"/>
					<font id="writerMessage" color="red">* </font>
					</td>
				</tr>
				<tr>
					<th class="control-label">建设院校：</th>
					<td>
						<sys:treeselect id="school" name="school.id" value="${questionlibImport.school.id}" labelName="school.name" labelValue="${questionlibImport.school.name}"
					title="学校" url="/sys/office/treeData?type=2" cssClass="input-medium" allowClear="true" notAllowSelectParent="fasle"/>
					<font id="schoolMessage" color="red">* </font>
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
			</table>
			</form>
			<hr>
			<div>
			<object name="MyActiveX" classid="clsid:BF4C592B-6856-4C01-90B8-75B8155F5D96" codebase="${ctxStatic}/MyActiveX/MyActiveX.ocx" style="height:0;width:0;">
            </object> 
            <script type="text/javascript">
				function add() {
					var courseQuestionlibId = $("#questionlibId").val();
					//alert(courseQuestionlibId);
					//alert($("#specialtyId").val());
					//alert($("#title").val());
					if(null==$("#title").val() || ""==$("#title").val()){
						$("#titleMessage").append("请填写文档名称！");
						return;
					}
					//alert($("#writer").val());
					if(null==$("#writer").val() || ""==$("#writer").val()){
						$("#writerMessage").append("请填写主编姓名！");
						return;
					}
					//alert("schoolId"+$("#schoolId").val());
					if(null==$("#schoolId").val() || ""==$("#schoolId").val()){
						$("#schoolMessage").append("请选择建设学校！");
						return;
					}
					//alert("11");
            		$.ajax({
				         url:"${ctx}/questionlib/versionQuestion/saveQuestionlibImport?courseQuestionlibId="+courseQuestionlibId,  
				         type:"post",  
				         cache:false,
				         data:$("#saveImportDocMessage").serialize(),  
				         dataType:"json",
				         success:function(data) { 
				        	  var importDataId = data.importDataId;
							  $("#importDataId").val(importDataId);
							  importDoc();
				         },  
				         error:function(data) {  
				              alert("保存失败!");  
				         }
				    }); 
			    }
            	function importDoc(){
            		var importDataId = $("#importDataId").val();
            		/* if(importDataId==null || importDataId==""){
            			importDataId = $("#importId").val();
            			alert("importId:"+importDataId);
            		} */
					//alert("importDataId:"+importDataId);
					if(importDataId==null || importDataId==""){
						 alert("请先正确填写上传文档信息!");
						 return;
					} else{
						 alert("请选择待导入的word文档！")
						 var filePath = MyActiveX.FunChooseDoc();//选择word
						// alert("路径："+filePath);
						 if(filePath!=null && filePath!="" && filePath!=0){
		            		 alert("开始导入试题！");
					 		 var questionJsonData = MyActiveX.FunAnalysisWordReturn();//解析word
							 //alert(questionJsonData);
							// alert("${ctx}/questionlib/versionQuestion/importQuestionFile?importDataId="+importDataId);
							 var returnstr = MyActiveX.FunSendWord("127.0.0.1",8080,"${ctx}/questionlib/versionQuestion/importQuestionFile?importDataId="+importDataId);//发送word和pic
							 //alert(returnstr);
							 if(returnstr==0){
								alert("保存导入试题文档成功！");
								 $.ajax({
										url:"${ctx}/questionlib/versionQuestion/importQuestions",
										type:"post",
										cache:false,
										data:{"questionJsonData":questionJsonData,"importDataId":importDataId},
										dataType:"json",
										success:function (data){
										//	alert("导入试题成功数："+data.saveSuccess);
											if(data.saveFail>0){
												alert("导入试题成功数："+data.saveSuccess+",导入试题失败数："+data.saveFail+","+data.failMessage);
											}else{
												alert("导入试题成功数："+data.saveSuccess+",导入试题失败数："+data.saveFail);
											}
											if("success"==data.message){
	//											alert("returnsuccess:"+data.message);
												alert("试题导入成功!");
											}
										},
										error:function(data){
											alert("returnerror:"+data.message);
										}
								 });
							 }else{
								alert("导入试题文档失败！");
							 }
						 }
					 }
            	}
	        </script>
		    <form action="" method="post" enctype="multipart/form-data">
		    	<!-- <div id="returnImportId"></div> -->
		    <table style="margin: 0 auto;" id="importTable">
		    	<tr>
				 <td style="height: 40px;align:center;">
				<input id="importDataId" type="hidden" name="importDataId" value="${questionlibImport.id }">
	            <input type="button" value="添加word文档" onclick="add()" class="btn btn-primary control-label">
				<input id="closeImportQuestion" class="btn btn-primary control-label" type="button" value="返回" onclick="history.go(-1)"/>
				 </td>
				</tr>
			</table>
			</form>
		</div>
</body>
</html>