<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	
	<title>知识点管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %> 
	<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
		  $("#treeTable").treeTable({expandLevel:3}).show();
			
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
			$("#percentForm").validate({
				rules:{
					percent:{range:[0,100]}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					}else if(element.parent().is("span")){
						error.appendTo(element.parent());
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

	<c:if test="${not empty message}">
	<script type="text/javascript">top.$.jBox.closeTip();</script>
		<%-- 消息类型：info、success、warning、error、loading --%>
		<div id="messageBox" class="alert alert-${message.type}">
			<button data-dismiss="alert" class="close">×</button>
		    	${message.message}
		</div> 
	</c:if>
	<form:form class="form-horizontal"  modelAttribute="teacherKnowledge" id="percentForm" action="${ctx}/knowledge/savePercent">
		<form:hidden path="courseKnowledge.id"/>
		<div class="control-group" style="margin-top:10px; ">
			<label class="control-label" style="width: auto;">练习题百分比:</label>
			<div class="controls" style="float: left; margin-left:10px;">
				<span><input name="percent" value="${percent}"  style="width: 40px;border:1px solid ;"/>% </span>
				<input id="btnSubmit" class="btn tbtn-primary" type="submit"  style="margin-left: 30px;" value="保存" />
				<span class="help-inline"><font color="red">首次生成试卷需要设置练习题百分比，若不设置则默认为100.0% </font> </span>
			</div>
		</div>
		
	</form:form>
	<form:form id="listForm" modelAttribute="teacherKnowledge" action="${ctx}/knowledge/save" method="post" class="form-horizontal">
		<form:hidden path="courseKnowledge.id"/>
		<input name="percent"  type="hidden" value="${ empty percent ? '100' : percent }" class="input-mini">
		<table id="treeTable" class="table table-striped table-bordered table-condensed" style="margin-top: 20px;">
			<thead>
				<tr>
					<!-- <th>编号</th> -->
					<th>考点(知识点)</th>
					<th>建议级别</th>
					<th>课时</th>
					<th>自定义级别</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${list}" var="item" varStatus="index">
				<tr id="${item.courseKnowledge.id}" pId="${ item.courseKnowledge.parent.id ne '1' ? item.courseKnowledge.parent.id ne teacherKnowledge.courseKnowledge.id ? item.courseKnowledge.parent.id: '0' : '0' }">
					<%-- <td>${index.index+1}</td> --%>
					<td style="text-align: left;">${item.courseKnowledge.title }</td>
					<td>${fns:getDictLabels(item.courseKnowledge.level,'questionlib_courseKnowledge_level','') }</td>
					<td></td>
					<td style="text-align:center;">
					    <input name="ckIdArr" value="${item.courseKnowledge.id}" type="hidden">
					    <select name="levelArr" class="input-small" style="width:60px ! important;" id="ck_${item.courseKnowledge.id}" pIds="${item.courseKnowledge.parentIds}">
					       <c:forEach  items="${fns:getDictList('questionlib_courseKnowledge_level')}" var="dictItem" >
					            <c:choose>
					            	<c:when test="${empty item.level}">
					            		<option value="${dictItem.value }"  ${item.courseKnowledge.level eq dictItem.value ? "selected='selected'":""} >${dictItem.label}</option>
					            	</c:when>
					                <c:otherwise>
					                    <option value="${dictItem.value }"  ${item.level eq dictItem.value ? "selected='selected'":""} >${dictItem.label}</option>
					                </c:otherwise>
					            </c:choose>
					       		
					       </c:forEach>
					    </select>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	     <div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="保存"/>
		</div>
	 </form:form>
</body>
</html>