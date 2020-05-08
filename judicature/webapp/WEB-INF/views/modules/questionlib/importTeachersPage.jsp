<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教师信息导入</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
	
	<form id="inputForm" action="${ctx}/questionlib/teacher/importTeachers" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');">
			<table style="margin: 0 auto;">
				<tr>
					<th class="control-label">所属部门：</th>
					<td style="height: 40px;">
						<sys:treeselect id="office" name="officeId" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
						title="部门" url="/sys/office/treeData?type=2" cssClass="required" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<th>excel文件选择：</th>
					<td><input id="teacherFile" name="teacherFile" type="file"/></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
						<a href="${ctxStatic}/excelTemplate/teacherMessage.xlsx">下载模板</a>
					</td>
				</tr>
			</table>
		</form>

</body>
</html>