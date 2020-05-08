<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<title>bbs论坛</title>
<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
<style>
.table-tr tr th{font-weight:normal;}
.table-bordered th{background:#64dfda;color:#000;}
</style>
</head>
<body>
	<sys:message content="${message}"/>
    <form:form id="searchForm" action="${ctx}/bbs/queslist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input type="button"  id="addQuesBtn" class="btn btn-default tbtn-primary" value="创建讨论"  />
	</form:form>
	

	<c:forEach items="${page.list}" var="item">
	<table  class="table table-striped table-bordered table-condensed table-tr">
			<tr>
			    <th style="width: 20%;">
			      <c:if test="${not empty item.teacher.name}">
			        ${item.teacher.name}
			      </c:if>
			      <c:if test="${not empty item.student.name }">
			         ${item.student.name }
			      </c:if>
				</th>
				<th style="text-align: left;" onclick="$('#a_${item.id}').toggle()">
				  <a style="cursor: pointer;"> ${fns:abbr(fns:replaceHtml(item.detail),55)}</a>
				   <span style="float: right;">
				     <fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				   </span>
				</th>
				<th style="width: 20%;">
				   回复${item.answerCount}个
				  <a href="${ctx }/bbs/answerlist?question.id=${item.id}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;position:relative;top:4px;"  type="button" title="查看详细"> </a>
				</th>
			</tr>
			<tr id="a_${item.id}" style="background:#fdfdfd;display:none;"><td colspan="3" style=" text-align: left;word-break:break-all;">${fns:replaceHtml(item.detail)}</td></tr>
	</table>
	</c:forEach>
	<div class="pagination">${page}</div>
	
	<div class="modal hide fade in" id="addQuestion" style="width: 400px; margin-left: -100px; top: 20%;"  >
		<form:form action="${ctx}/bbs/saveQuesForm"  method="post" class="form-horizontal" id="addQuesForm">
		    <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h2>创建讨论(新建讨论在审核通过后才会在列表显示)</h2>
			</div>
			<div class="modal-body" style="overflow-y: hidden;  height:220px;">
				<div id="messageBoxModel" class="alert hide">
				</div>
			   
			  	<div class="control-group">
						
						<span>
						<textarea id="content" name="content" rows="3" cols="200" class="input-xlarge required" minlength="4" placeholder="请至少写4个字" style="margin: 0px; height: 198px; width: 339px ! important;" maxlength="200" ></textarea>
						<span class="help-inline"><font color="red">*最多添加200字</font></span>
					    </span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn tbtn-primary" data-dismiss="modal">关闭</button>
				<input type="submit"  class="btn tbtn-primary"  value="提交"/>
			</div>
		</form:form>	   
	</div>
<script type="text/javascript">
  (function(){
	  $("#addQuesBtn").click(function(){
		    $("#messageBoxModel").empty();
		    $("#addQuesForm")[0].reset();
			$("#addQuestion").modal({
				show : true,
				backdrop : 'static'
			});
			/* .on('hide.bs.modal',function(){
				window.location.replace('${ctx}/bs/message/systemPushLog?pageNo=1');
				return false;
			}) */      	  	  
	  });
	  
	  $("#addQuesForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			//errorContainer: "#messageBoxModel",
			errorPlacement: function(error, element) {
			    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} if(element.parent().is("span")){
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				} 
				//error.appendTo($("#messageBoxModel"));
			}
		});
	  
  })();
</script>
</body>
</html>