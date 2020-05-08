<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>选择专业</title>
	<meta name="decorator" content="teacher_blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				
				var obj=parent.frames['teacherMainFrame'];
				if(!!!obj){
					obj=parent;
				}
				
				var specilitySelect =obj.specilitySelect;
			    for (var i=0; i<specilitySelect.length; i++){
					if (specilitySelect[i][0]==$(this).val()){
						this.checked = true;
					}
				} 
				$(this).click(function(){
					var id = $(this).val(), title = $(this).attr("title");
					obj.specilitySelectAddOrDel(id, title);
				});
			});
		});
		
	/* 	function view(href){
			top.$.jBox.open('iframe:'+href,'查看文章',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
				}
			});
			return false;
		} */
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div style="margin:10px;">
	<form:form id="searchForm" modelAttribute="specialty" action="${ctx}/common/specilityList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th style="text-align: center;">选择</th>
					<th>标题</th>
					<th>描述</th>
				</tr>
			</thead>
			<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td style="text-align: center;">
				   <input type="checkbox"  name="id" value="${item.id}" 	title="${item.title}" />
				</td>
				<td>${item.title}</td>
				<td>${item.desciption}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>