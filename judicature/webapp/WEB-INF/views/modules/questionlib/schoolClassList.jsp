<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/questionlib/schoolClass/importClasses" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="classesFile" name="classesFile" type="file" style="width:330px;text-indent: 0px;line-height: 0px;" /><br/><br/>　　
			<input id="btnImportSubmit"  class="btn btn-primary" type="submit" value="   导    入   " />
			<a href="${ctxStatic}/excelTemplate/schoolClassMessage.xlsx">下载模板</a>
		</form>
	</div>
	<div id="showHideDiv" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<iframe id="classStudentPage" src="" style="width:1000px;min-height: 600px;position: absolute;top: 200px;left: 400px;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
			
		</iframe>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/schoolClass/">班级列表</a></li>
		<shiro:hasPermission name="questionlib:schoolClass:edit"><li><a href="${ctx}/questionlib/schoolClass/form">班级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolClass" action="${ctx}/questionlib/schoolClass/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业名称：</label>
				<form:input path="specialtyTitle" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>班级名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th>班级名称</th>
				<th>专业名称
				<th>在校开始时间</th>
				<th>在校结束时间</th>
				<shiro:hasPermission name="questionlib:schoolClass:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolClass" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td><a href="${ctx}/questionlib/schoolClass/form?id=${schoolClass.id}">
					${schoolClass.title}
				</a></td>
				<td>${schoolClass.specialtyTitle}
				<td>
					<fmt:formatDate value="${schoolClass.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${schoolClass.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="questionlib:schoolClass:edit"><td>
    				<a href="${ctx}/questionlib/schoolClass/form?id=${schoolClass.id}">修改</a>
    				<shiro:hasPermission name="questionlib:schoolClass:delete">
						<a href="${ctx}/questionlib/schoolClass/delete?id=${schoolClass.id}" onclick="return confirmx('确认要删除该班级吗？', this.href)">删除</a>
					</shiro:hasPermission>
					<%-- <a href="javascript:void(0);" onclick="openDiv('${schoolClass.id}')">学生管理</a> --%>
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
	
		function openDiv(classId){
		/* 	$("#classStudentPage").attr("src","${ctx}/questionlib/student/findClassStudentByClassId?schoolClass.id="+classId);
		    $("#showHideDiv").show();
		 */	
			 
		    var src ="iframe:${ctx}/questionlib/student/findClassStudentByClassId?schoolClass.id="+classId;
		    var title ="学生管理";	
			top.$.jBox(src,{
				title:title,
				width: 1050,
			    height: 750,
				buttons:{}, 
			    bottomText:"",
			    loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
			    closed:function(){
					$("#searchForm").submit();
					//window.location.href="${ctx}/questionlib/courseKnowledge/list";
				}
	     }); 
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</body>
</html>