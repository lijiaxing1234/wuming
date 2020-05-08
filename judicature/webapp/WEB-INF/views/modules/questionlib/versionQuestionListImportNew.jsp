<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
    <head>
       <title>试题导入</title>
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
	 
		
		
		 <form id="saveImportDocMessage" action="${ctx}/questionlib/versionQuestion/importQuestionFileNew?courseId=${courseId}" method="post" enctype="multipart/form-data">
			<table style="margin: 0 auto;" id="importTable">
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
					<td><input name="writer" type="text" value="${questionlibImport.writer }" class="input-medium"  style="width:210px;"/></td>
				</tr>
				
				
				<%-- <tr>
					<th class="control-label">建设院校：</th>
					<td>
						<sys:treeselect id="school" name="school.id" value="${questionlibImport.school.id}" labelName="school.name" labelValue="${questionlibImport.school.name}"
					title="学校" url="/sys/office/treeData?type=2" cssClass="input-medium required" allowClear="true" notAllowSelectParent="fasle"/><br>
					</td>
					<th></th>
					<td></td>
				</tr> --%>
				
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
					<th class="control-label">上传excel文件：</th>
					<td style="height: 40px;"><input name="wordFile" type="file" class="input-medium"  style="width:210px;"/></td>
				</tr>
				<tr>
				<td></td>
					<td style="height: 40px;align:center;"><input id="saveImportMessage" class="btn btn-primary" type="submit" value="开始导入"/>
					<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="closeImportPage();"/></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>