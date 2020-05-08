<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>	
	<title>资源管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<script type="text/javascript">

	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel:30}).show();
			$("#listForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			//设置关闭所有子节点
			$("select[id^='ck_']").change(function(){
				var id=$(this).attr("id").split("_")[1],
				    thisVlue=$(this).find("option:selected").val();
				if(thisVlue==4){
					$("select[id^='ck_']").each(function(key,value){
						var pIds=$(this).attr("pIds");
						if(pIds.indexOf(","+id+",")!==-1){
							$(this).val(4);
						}
					});
				}
			});
		});
	</script>
</head>
<body>
    
    <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/school/shoolsources/list?knowledgeId=${edresources.knowledgeId}">教学资源列表</a></li>
		<li style="display: none"><a href="${ctx}/school/shoolsources/form?knowledgeId=${edresources.knowledgeId}">教学资源添加</a></li>
	</ul>
	
	<%-- <c:if test="${not empty message}">
	<script type="text/javascript">top.$.jBox.closeTip();</script>
		消息类型：info、success、warning、error、loading
		<div id="messageBox" class="alert alert-${message.type}">
			<button data-dismiss="alert" class="close">×</button>
		    	${message.message}
		</div>                                                                                                                                     
	</c:if> --%>
	<sys:message content="${message }"/>
	<form:form id="searchForm" modelAttribute="edresources" action="${ctx}/school/shoolsources/" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input  name="knowledgeId" type="hidden" value="${edresources.knowledgeId}"/>
	    <label>资源名称：</label> <input type="text" name="resName" value="${resName}"/>
	    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
	</form:form>
		   <sys:message content="${message}"/>
		
		<table id="treeTable" class="table table-striped table-bordered table-condensed" style="margin-top: 20px;">
			<thead>
				<tr>
					<th width="100px;">编号</th>
					<th>知识点名称</th>
					<th>资源名称</th>
					<th>上传人姓名</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${item.courseKnowledge.title }</td>
					
					<td>${item.resName }</td>
					<td>${item.uploadUserName }</td>
					<td><fmt:formatDate value='${item.createDate}' pattern='yyyy-MM-dd'/></td>		
					<td><a class="btn btn-primary" href="${ctx}/school/shoolsources/modifyform?id=${item.id}">查看</a>
						<%-- <a class="btn btn-primary" href="${ctx}/school/shoolsources/form/delete?id=${item.id}">删除</a>--%>
					</td> 
					
				</tr>
			</c:forEach>
			</tbody>
		</table>
	<div class="pagination">${page}</div>
</body>
</html>