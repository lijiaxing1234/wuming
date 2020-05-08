<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题库</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				 return confirmx("确认要导出题库统计数据吗？",function(){
						$("#searchForm").attr("action","${ctx}/questionlib/statistics/exportQuestionLibStatistics");
						$("#searchForm").submit(); 
					});
				});
			$("#btnSubmit").click(function(){
						$("#searchForm").attr("action","${ctx}/questionlib/statistics/accreditStatistics");
						$("#searchForm").submit(); 
				});
			});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	/* 	function exportQuestionLibStatistics"){
			top.$.jBox.confirm("确认要导出题库统计数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$.post("${ctx}/questionlib/statistics/exportQuestionLibStatistics"); 
					window.location.href = "${ctx}/questionlib/statistics/exportQuestionLibStatistics";
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		} */
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/statistics/accreditStatistics">题库列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolQuestionlib" action="${ctx}/questionlib/statistics/accreditStatistics" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题库名称：</label>
				<form:select path="questionlibId" class="input-medium">
					<form:option value="">请选择</form:option>
					<form:options items="${questionlib:getCourseQuestionlibByCurrentUserCompanyId()}" itemLabel="title" itemValue="id"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
			    <th>学校名称</th>
				<th>学校负责人</th>
				<th>题库名称</th>
				<th>题库授权开始时间</th>
				<th>题库授权结束时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolQuestionLib" varStatus="i">
			<tr>
				<td>${i.count}
				<td>${schoolQuestionLib.schoolName}
				<td>${schoolQuestionLib.master}
				<td>${schoolQuestionLib.questionLibName }
				<td><fmt:formatDate value="${schoolQuestionLib.validStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				<td><fmt:formatDate value="${schoolQuestionLib.validEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				<td>  
				     <c:choose>
				       <c:when test="${now lt schoolQuestionlib.validStartDate }">未启用</c:when>
				       <c:when test="${now le schoolQuestionlib.validEndDate }"><font color="green">正常</font></c:when>
				       <c:otherwise><font color="red">过期</font></c:otherwise>
				    </c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>