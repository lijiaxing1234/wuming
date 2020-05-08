<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题库管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript" src="${ctxStatic}/modules/questionlib/js/questionlibSelect.js"></script>
	<script type="text/javascript">
	
		 function chengeCourse(ids,names){
			 $.utils.BindSelect("courseSelect",[]);
			 $.utils.BindSelect("vesionSelect",[]);
			 $.utils.BindSelect("courseSelect", "/questionlib/common/getCourseBySpecialtyId?specialtyId="+ ids[0]);
		 }
	
		$(document).ready(function() {
		 
			 $("#courseSelect").off("change").on("change",function(e){
					 var selectId=e.val;
					 $.utils.BindSelect("vesionSelect", "/questionlib/common/getCourseVersionByCouresId?courseId="+selectId);
		     }); 
			
			
			$("#inputForm").validate({
				submitHandler: function(form){
					
					var specialtyId =$("#specialtyIdId").val();
				 
					if(specialtyId==null||specialtyId=="")
					{
					   alertx("请选择专业");
					   return null;
					}
					
					var courseSelect =$("#courseSelect").val();
				 
					if(courseSelect==null||courseSelect=="")
					{
					   alertx("请选择课程");
					   return null;
					}
					
					
					var vesionSelect =$("#vesionSelect").val();
				  
					if(vesionSelect==null||vesionSelect=="")
					{
					   alertx("请选择版本");
					   return null;
					}
					
					 
					if($("#excelFile").val()=="")
					{
					   alertx("请选择EXCEL文件！");
					    return ;
					}
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
 <style type="text/css">
 	.form-horizontal{ margin-top: 40px;}
 </style>
</head>
<body>
	<form id="inputForm" action="${ctx}/questionlib/courseKnowledge/importExcel" method="post" enctype="multipart/form-data">
			<table style="margin: 0 auto;" id="importTable">
				<tr>
					<td colspan="2" style="height: 10px;"> </td>
				</tr>
				<tr>
					<th class="control-label">专业：</th>
					<td style="height: 40px;"><sys:treeselect id="specialtyId" ontextchange="true" name="specialty.id" value="${course.specialty.id}" labelName="course.specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="input-medium" notAllowSelectParent="true" /><font color="red">*</font>
				</td>
				</tr>
				<tr>
					<th class="control-label">课程：</th>
					<td style="height: 40px;">
						<select class="courseSelect" name="courseId" id="courseSelect" style="width:220px;">
						   <option value="">请选择</option>
				   		</select>
				   		<font color="red">*</font>
			   	    </td>
				</tr>
				<tr>
					<th class="control-label">版本：</th>
					<td style="height: 40px;">
						<select name="courseVesionId" id="vesionSelect" class="courseVesionSelect" style="width:220px">
						  <option value="">请选择</option>
			 			 </select> 
			            <font color="red">*</font>
			        </td>
				</tr>
				<tr>
					<th class="control-label">上传excel文件：</th>
					<td style="height: 40px;"><input id="excelFile" name="excelFile" type="file" class="input-medium"  style="width:210px;"/><font color="red">*</font></td>
				</tr>
				<tr>
				<td></td>
					<td style="height: 40px;align:center;"><input id="btnSubmit" class="btn btn-primary" type="submit" value="开始导入"/>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="top.$.jBox.close(true);"/></td>
				</tr>
			</table>
		</form>
</body>
</html>