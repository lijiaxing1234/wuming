<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>题库管理</title>
	<meta name="decorator" content="teacher_default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		(function(){
		    $(function(){
		    	$("#btnAdd").on("click",function(){
		    		openJbox("iframe:${ctx}/questionlib/form","题库添加");
		    		return false;
		    	});
		    	
		    	$("#contentTable tr").find("a[id^='eidt_']").on("click",function(){
		    		var src=$(this).attr("href");
		    		openJbox("iframe:"+src,"题库修改");
		    		return false;
		    	});
		    	
		    	function openJbox(src,title){
		    		 top.$.jBox(src,{
							title:title,
							width: 450,
						    height: 250,
							buttons:{}, 
						    bottomText:"",
						    loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							},
						    closed:function(){
								$("#searchForm").submit();
							}
				     }); 
		    	}
		        	
		    });
		})();
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/list?courseVesionId=${courseVesionId}&courseId=${courseId}&specialtyId=${specialtyId}&ownerType=${ownerType}">题库列表</a></li>
	    <li><a href="${ctx}/questionlib/form?courseVesionId=${courseVesionId}&courseId=${courseId}&specialtyId=${specialtyId}&ownerType=${ownerType}">题库添加</a></li>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="courseQuestionlib" action="${ctx}/questionlib/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题库名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/></li>
			<li>
			     <input id="btnAdd" class="btn tbtn-primary" type="button" value="题库添加"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>题库名称</th>
				<th>版本</th>
				<th>所属类型</th>
				<!-- <th>状态</th> -->
			    <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="courseQuestionlib"  varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>	
				<td>
					${courseQuestionlib.title}
				<td>
					${questionlib:getCourseVersionByVersionId(courseQuestionlib.versionId).title}
				</td>
				<td>
					${fns:getDictLabel(courseQuestionlib.ownerType, 'questionlibowner', '')}
				</td>
			<%-- 	<td>
					${courseQuestionlib.state}
				</td> --%>
				<td>
					<c:if test="${courseQuestionlib.user.id eq teacher.id}">
    				    <a href="${ctx}/questionlib/form?id=${courseQuestionlib.id}" id="eidt_${courseQuestionlib.id}" style="background: url(${ctxStatic}/icon/xg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="修改"></a>
						<a href="${ctx}/questionlib/delete?id=${courseQuestionlib.id}" onclick="return confirmx('确认要删除该题库吗？', this.href)" style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="删除"></a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>