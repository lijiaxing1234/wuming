<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
		function check(){
			var str = $("#classStudentsFile").val();
		    if(str.length=="")
		    {
		        alertx("请选择上传文件");
		        return false;
		    }
		    return true;
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/questionlib/schoolClass/importClassStudents" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="classStudentsFile" name="classStudentsFile" type="file" style="width:330px;text-indent: 0px;line-height: 0px;" /><br/><br/>　　
				<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " />
			<a href="${ctxStatic}/excelTemplate/studentMessage.xlsx">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/student/">学生列表</a></li>
		<shiro:hasPermission name="questionlib:student:edit"><li><a href="${ctx}/questionlib/student/form">学生添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="student" action="${ctx}/questionlib/student/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>班级：</label>
				<form:select path="schoolClass.id" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${questionlib:getClassBySchoolId()}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>学号：</label>
				<form:input path="stdCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="stdPhone" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<shiro:hasPermission name="questionlib:schoolClass:download">
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>姓名</th>
				<th>专业</th>
				<th>班级</th>
				<th>学号(登录名)</th>
				<th>手机号码</th>
				<th>邮箱</th>
				<th>修改时间</th>
				<shiro:hasPermission name="questionlib:student:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="student" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td><a href="${ctx}/questionlib/student/form?id=${student.id}">
					${student.name}
				</a></td>
				
				<td>
					${student.schoolClass.specialtyTitle}
				</td>
				<td>
					${student.schoolClass.title}
				</td>
				<td>
					${student.stdCode}
				</td>
				<td>
					${student.stdPhone}
				</td>
				<td>
					${student.stdEmail}
				</td>
				<td>
					<fmt:formatDate value="${student.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="questionlib:student:edit"><td>
    				<a href="${ctx}/questionlib/student/form?id=${student.id}">修改</a>
    				<shiro:hasPermission name="questionlib:student:delete">
						<a href="${ctx}/questionlib/student/delete?id=${student.id}" onclick="return confirmx('确认要删除该学生吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		})
		
	</script>
</body>
</html>