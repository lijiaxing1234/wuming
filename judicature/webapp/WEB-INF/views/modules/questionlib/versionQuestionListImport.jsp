<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
    <head>
       <title>试题管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#importTable tr td{
			height: 30px;
			vertical-align: top;
		}
		table tr td input{
			vertical-align: middle;
		}
	</style>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//打开试题导入窗口
		function showImportPage(){
			$("#importForm").show();
		}
		//关闭试题导入窗口
		function closeImportPage(){
			$("#importForm").hide();
		} 
	</script>
</head>
<body>
	<form action="">
	<ul>
	<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入试题按钮" onclick="showImportPage();"/></li>
	</ul>
	</form>
	<!-- 试题导入界面 -->
	<div id="importForm" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<div style="width: 850px;min-height: 450px;position: absolute;top: 150px;left: 300px;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
		<form id="saveImportDocMessage" action="${ctx}/questionlib/versionQuestion/saveQuestionlibImport?courseQuestionlibId=${courseQuestionlibId}" method="post">
			<table style="margin: 0 auto;" id="importTable">
				<tr>
					<th colspan="4" class="control-label"><h3>试题导入</h3></th>
				</tr>
				<tr>
				<td colspan="4" style="height: 10px;"> </td>
				</tr>
				<tr>
				<th class="control-label">文档名称：</th>
					<td><input name="title" type="text" value="${questionlibImport.title }" class="input-medium"  style="width:210px;"/></td>
					<%--  --%>
					<%-- <th class="control-label">上传者电话：</th>
					<td>
						<input name="phone" type="text" value="${questionlibImport.phone }" class="input-medium"  style="width:220px;"/>
					</td> --%>
				</tr>
				<tr>
					<%-- <th>上传者部门：</th>
					<td>
						<sys:treeselect id="office" name="office.id" value="${questionlibImport.office.id}" labelName="office.name" labelValue="${questionlibImport.office.name}"
						title="部门" url="/sys/office/treeData?type=2" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>
					</td> --%>
					<th class="control-label">主编：</th>
					<td><input name="writer" type="text" value="${questionlibImport.writer }" class="input-medium"  style="width:210px;"/></td>
				</tr>
				<tr>
					<th class="control-label">专业：</th>
					<td>
						<sys:treeselect id="specialty" ontextchange="false" name="specialty.id" value="${questionlibImport.specialty.id}" labelName="questionlibImport.specialty.title" labelValue="${questionlibImport.specialty.title}"
						title="专业" url="/questionlib/specialty/treeData" cssClass="input-medium" notAllowSelectParent="true" />
						<%-- <select id="specialtyId"  name="specialtyId" class="input-medium" style="width:220px;">
						 <c:forEach items="${questionlib:getSpecialty()}" var="specialty">
						   	<option value="${specialty.id}" ${questionlibImport.specialtyId==specialty.id?"selected":""} >${specialty.title} </option>
						 </c:forEach>
					</select> --%>
					</td>
				</tr>
				<tr>
					<th class="control-label">建设院校：</th>
					<td>
						<sys:treeselect id="school" name="school.id" value="${questionlibImport.school.id}" labelName="school.name" labelValue="${questionlibImport.school.name}"
					title="学校" url="/sys/office/treeData?type=2" cssClass="input-medium" allowClear="true" notAllowSelectParent="fasle"/><br>
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th class="control-label">上传者姓名：</th>
					<td>
						${fns:getUser().name}
						<%-- <sys:treeselect id="user" name="user.id" value="${questionlibImport.user.id}" labelName="user.name" labelValue="${questionlibImport.user.name}"
					title="上传者姓名" url="/sys/office/treeData?type=3" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/> --%>
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th class="control-label">层次：</th>
					<td>
						<input name="coursePhase" type="radio" value="1" class="input-medium"/>高职
						<input name="coursePhase" type="radio" value="2" class="input-medium"/>培训
						<input name="coursePhase" type="radio" value="3" class="input-medium"/>本科
					</td>
					<td></td>
					<!--  <td><input id="saveImportMessage" class="btn btn-primary" type="submit" value="保存信息"/></td>onclick="saveImportMessage();" -->
				</tr>
			</table>
			</form>
			<div>
			<hr>
			<object name="MyActiveX" classid="clsid:BF4C592B-6856-4C01-90B8-75B8155F5D96" codebase="${ctxStatic}/MyActiveX/MyActiveX.ocx" style="height:0;width:0;"></object>
			<script type="text/javascript">
				function add(){
					 var isSelectDoc = MyActiveX.FunChooseDoc();//选择word
					// alert(isSelectDoc);
					 var importDataId = "";
					 if(isSelectDoc==0){
						 var courseQuestionlibId = $("#questionlibId").val();
						 $('#saveImportDocMessage').ajaxSubmit({
							 type: "POST",
							 url: "${ctx}/questionlib/versionQuestion/saveQuestionlibImport?courseQuestionlibId="+courseQuestionlibId,
							 dataType: 'json',
							 success: function(data){
								 //0  不成功  1  成功 
								 if(data.message==0){    
								      alert("保存失败！请刷新页面重试!");   
								 } else{
									 importDataId = data.importDataId;
									 $("#importDataId").val(importDataId);
								 }
							 },
							 clearForm: true,
							 resetForm: true
						});  
						/*  $("#saveImportDocMessage").post("${ctx}/questionlib/versionQuestion/saveQuestionlibImport",{"courseQuestionlibId":$("#questionlibId").val()},function(data){
								 //0  成功  1  不成功  2 手机号码格式不对
							 if(data.message==0){    
							      alert("保存失败！请刷新页面重试!");   
							 } else{
								 importDataId = data.importDataId;
								 $("#importDataId").val(importDataId);
							 }
							    
						 }); */
						 /*		
								var courseQuestionlibId = $("#questionlibId").val();
								$.ajax({
							         url:"${ctx}/questionlib/versionQuestion/saveQuestionlibImport?courseQuestionlibId="+courseQuestionlibId,  
							         type:"post", 
							         cache:false,
							         data:$("#saveImportDocMessage").serialize(),  
							         dataType:"text",
							         success:function(data) { 
							        	  alert("保存成功!");
							        	  importDataId = data.importDataId;
											 $("#importDataId").val(importDataId);
							         },  
							         error:function(data) {  
							              alert("保存失败!");  
							         }
							    });  
							 */ 
						 
				 		 var questionJsonData = MyActiveX.FunAnalysisWordReturn();//解析word，并返回json字符串
				 	//	 alert(questionJsonData);//&questionJsonData="+questionJsonData
				 		 if(""==importDataId){
				 		 	importDataId = $("#importDataId").val();
				 		 }
						// alert(importDataId);
						 var returnstr = MyActiveX.FunSendWord("127.0.0.1",8080,"${ctx}/questionlib/versionQuestion/importQuestionFile?importDataId="+importDataId);//发送包含上传的word和保存图片的压缩文件
						 //alert(returnstr);
						 $.ajax({
								url:"${ctx}/questionlib/versionQuestion/importQuestions",
								type:"post",
								cache:false,
								data:{"questionJsonData":questionJsonData,"importDataId":importDataId},
								dataType:"text",
								success:function (data){
									//alert("returnsuccess"+data);
								},
								error:function(data){
									//alert("returnerror"+data);
								}
						 });
					 }
			    }
		    </script>
		    <form action="${ctx}/questionlib/versionQuestion/importQuestionFile" method="post" enctype="multipart/form-data">
				<input id="importDataId" type="hidden" name="importDataId">
	            <input type="button" value="添加word文档" onclick="add()" class="btn btn-primary">
				<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="closeImportPage();"/>
			</form>
		</div>
	</div>
	</div>
</body>
</html>