<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
			$("input[id^='comm_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				 $.jBox("iframe:${ctx}/examination/stuReviewForm?studentId="+id,{
					title:"填写评语",
					width: 800,
				    height: 350,
					buttons:{}, 
				    bottomText:""
				});  
			});
		});
	</script>
<title>学生管理</title>
</head>
<body>
    <form:form id="searchForm" modelAttribute="student" action="${ctx}/examination/student" method="post" class="breadcrumb form-search " cssStyle="margin:0px;padding: 25px 0px 5px 50px;">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>班级名称：</label>
				<form:select path="schoolClass.id">
				    <form:option value="">请选择</form:option>
				    <form:options items="${teacherClasss}"  itemLabel="title" itemValue="id"/>
				</form:select>
			</li>
			<li>
			  <label>姓名：</label><input name="name" class="input-medium" value="${name}"/>
			</li>
			<li><label>学号：</label><input name="stdCode" value="${stdCode}" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		
		
		<%-- <label>专业名称：</label>
		<form:select path="">
		    <form:options items="${questionlib:getSpecialty()}"  itemLabel="title" itemValue="id"/>
		</form:select> --%>
		
	</form:form>
	<sys:message content="${message}"/>
	<form id="listForm">
		<table id="tre" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th style="width:20px;">序号</th>
					<th>班级</th>
					<th>名称</th>
					<th>学号</th>
					<th width="25%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${page.list}" varStatus="index">
					<tr>
						<td>${(index.index+1) + (page.pageNo-1)*page.pageSize }</td>
						<td>${item.schoolClass.title}</td>
						<td>${item.name}</td>
						<td>${item.stdCode}</td>
						<td>
							<!-- <input id="btnImport" class="btn btn-primary" type="button" value="操作" /> -->
							<input id="comm_${item.id}" studentName="${item.name }" style="background: url(${ctxStatic}/icon/py.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="学生评语"/>
							 <a href="${ctx }/examination/reviewList?studentId=${item.id}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="已评学生评语列表"></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pagination">
	  		${page}
	  	</div>
	 </form>
</body>
</html>