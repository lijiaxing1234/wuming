<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>回答管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#selectAll").click(function(){
				  if ($(this).attr("checked")){  
				        $("input[name='ids']").attr("checked", true);  
				  } else {  
				        $("input[name='ids']").attr("checked", false);  
				  } 
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function deleteAnswerList(){
			var checkedNum=$("input[name='ids']:checked").length;
			if(checkedNum==0){
				alertx("请至少选择一项");
				return;
			}else{
				return confirmx('确认要删除该提问吗？',function(){ $('#formList').submit();});
			}
		}
	function authorQuestion(){
				
			var checkedNum=$("input[name='ids']:checked").length;
			if(checkedNum==0){
				alertx("请至少选择一项");
				return;
			}else{
				$("#delFlag").val("2");
				return confirmx('确认要删除该提问吗？',function(){ $('#formList').submit();})
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/qaa/uAnswer/">回答列表</a></li>
		<%-- <shiro:hasPermission name="qaa:uAnswer:edit"><li><a href="${ctx}/qaa/uAnswer/form">回答添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="uAnswer" action="${ctx}/qaa/uAnswer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>用户登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="60" class="input-medium"/></li>
			<li><label>用户昵称：</label><form:input path="userName" htmlEscape="false" maxlength="60" class="input-medium"/></li>
			<li><label>答案：</label><form:input path="detail" htmlEscape="false" class="input-medium"/></li>
			<li><label>问题：</label><form:input path="questionDetail" htmlEscape="false" class="input-medium"/></li> --%>
			<li><label>答案：</label><form:input path="detail" htmlEscape="false" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:choose>
				<c:when test="${uQuestion.delFlag eq 1}">
					
				</c:when>
				<c:otherwise>
					<shiro:hasPermission name="questionlib:uAnswer:delete">
						<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="批量删除" onclick="deleteAnswerList()" /></li>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${uQuestion.delFlag eq 2}">
					
				</c:when>
				<c:otherwise>
					<shiro:hasPermission name="questionlib:uQuestion:auditor">
						<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="批量审核" onclick="authorQuestion()" /></li>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
			<li><label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('questionlib_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/qaa/uAnswer/delete" method="post" id="formList">
		<input type="hidden" id="delFlag" name="delFlag" value="1" />
	<table id="contentTable" class="table table-bordered table-condensed" style="background-color: initial;">
		<thead>
			<tr>
				<th style="text-align:center;"><input type="checkbox" id="selectAll"  value="全选"/></th>
				<th style="text-align:center;width: 33%">答案</th>
				<th style="text-align:center;width: 15%">回答人</th>
				<th style="text-align:center;width: 33%">问题</th>
				<th style="text-align:center;width: 15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td style="text-align:center;">
					<input type="checkbox" name="ids"   value="${item.id}" title="${fns:replaceHtml(item.detail)}" />
				</td>
				<td style="text-align:center;"><a href="javascript:" onclick="$('#a_${item.id}').toggle();$('#q_${item.question.id}').css('display','none');">${fns:abbr(fns:replaceHtml(item.detail),50)}</a></td>
				<td style="text-align:center;">
				     <c:if test="${not empty item.teacher.name }">
				      ${item.teacher.name }
				    </c:if>
				    <c:if test="${not empty item.student.name }">
				      ${item.student.name }
				    </c:if>
				</td>
				<td style="text-align:center;"><a href="javascript:" onclick="$('#q_${item.question.id}').toggle();$('#a_${item.id}').css('display','none');">${fns:abbr(fns:replaceHtml(item.question.detail),50) }</a></td>
				<td style="text-align:center;">
    				<%-- <a href="${ctx}/qaa/uAnswer/form?id=${item.id}">修改</a> --%>
    				<c:if test="${item.delFlag eq '0'}">
	    				<shiro:hasPermission name="questionlib:uAnswer:auditor">
	    					<a href="${ctx}/qaa/uAnswer/delete?ids=${item.id}&delFlag=2" 
							onclick="return confirmx('确认要审核通过吗？', this.href)">审核通过</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="questionlib:uAnswer:delete">
							<a href="${ctx}/qaa/uAnswer/delete?ids=${item.id}&delFlag=1" 
							onclick="return confirmx('确认要删除该提问吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${item.delFlag eq '1'}">
						<shiro:hasPermission name="questionlib:uAnswer:auditor">
							<a href="${ctx}/qaa/uAnswer/delete?ids=${item.id}&delFlag=0" 
							onclick="return confirmx('确认要恢撤销审核吗？', this.href)">恢复到未审核</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${item.delFlag eq '2'}">
						<shiro:hasPermission name="questionlib:uAnswer:auditor">
							<a href="${ctx}/qaa/uAnswer/delete?ids=${item.id}&delFlag=0" 
							onclick="return confirmx('确认要撤销审核吗？', this.href)">恢复到未审核</a>
						</shiro:hasPermission>
					
						<shiro:hasPermission name="questionlib:uAnswer:delete">
							<a href="${ctx}/qaa/uAnswer/delete?ids=${item.id}&delFlag=1" 
							onclick="return confirmx('确认要删除该提问吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</c:if>
				</td>
			</tr>
			<tr id="q_${item.question.id}" style="background:#fdfdfd;display:none;word-break:break-all;"><td colspan="3"></td><td colspan="2" style="padding-left: 16px;text-align: -webkit-left;">${fns:replaceHtml(item.question.detail)}</td></tr>
			<tr id="a_${item.id}" style="background:#fdfdfd;display:none;word-break:break-all;"><td colspan="3" style="padding-left: 16px;text-align: -webkit-left;">${fns:replaceHtml(item.detail)}</td><td colspan="2"></td></tr>
			<%-- <tr>
				<td id="a_${item.id}" colspan="3" style="background:#fdfdfd;word-break:break-all;display: none;text-align:-webkit-left;padding-left: 16px;">
					${fns:replaceHtml(item.detail)}
				</td>
				<td id="q_${item.question.id}" style="background:#fdfdfd;display:none;word-break:break-all;text-align:-webkit-left;padding-left: 16px;" colspan="2">
					${fns:replaceHtml(item.question.detail)}
				</td>
			</tr> --%>
		</c:forEach>
		</tbody>
	</table>
	</form>
	<div class="pagination">${page}</div>
</body>
</html>