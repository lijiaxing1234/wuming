<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>班级作业详情</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		 function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		 $(document).ready(function() {
			$("input[id^='classExam_']").click(function(){
				var studentId=$(this).attr("id").split("_")[1];
				var examId=$("#examId").html();
			 	var examClassId=$("#examClassId").html();
		 		window.location.href = '${ctx}/exam/examPersonList?studentId='+studentId+'&examId='+examId+'&examClassId='+examClassId; 
			 }); 
			
			$("input[id^='remind']").click(function(){
				var id=$(this).attr("id");
				$.jBox("iframe:${ctx}/questionlib/messageStudent/form",{
					title:"提醒",
					width: 700,
				    height: 307,
					buttons:{}, 
				    bottomText:"",
				    loaded:function(h){
				    	var ifram=h.find("iframe").contents();
				    	$(".jbox-content", h.find("iframe")[0].document).css("overflow-y","hidden");
						$(".nav",ifram).hide();
						$("#message",ifram).attr("class","input-xlarge required");
						var action=$("#inputForm",ifram).attr("action");
						$("#inputForm",ifram).attr("action",action+"?param="+id);
						$("body", h.find("iframe").contents()).css("margin","10px");
						$("#btnCancel",h.find("iframe").contents()).attr("onclick","parent.$.jBox.close(true);");
					}
			    });  
			});
			
		});
		 
	</script>
</head>
<body>
 <sys:message content="${message}" />
    <form:form id="searchForm" action="${ctx}/exam/examClassList?examId=${exam.id}&schoolClassId=${school.id}" method="post" class="breadcrumb form-search ">
   		<input id="pageNo" name="pageNo" type="hidden" value="${student.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${student.pageSize}"/>
		<font id="examId" hidden="true">${exam.id}</font>
		<font id="examClassId" hidden="true">${school.id}</font>
		作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		状态：
		<c:if test="${exam.status eq '2'}">已完成</c:if>
		<c:if test="${exam.status eq '1'}">正在进行</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
		班级总人数：${countPerson }人&nbsp;&nbsp;&nbsp;&nbsp;
		未交作业人数:${unSubmitCount }人&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="btnExamSubmit" class="btn tbtn-primary" type="button" value="返回" onclick="history.go(-1)"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examClassDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th  hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center; width: 120px;">交作业时间</th>
					<c:if test="${exam.status eq '2'}">
						<th style="text-align: center;">正确率</th>
						<th style="text-align: center;">总题数</th>
						<th style="text-align: center;">答对数</th>
						<th style="text-align: center;">错误数</th>
					</c:if>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${student.list}" var="list">
				<tr>
					<td  hidden="true" style="text-align: center;">${list.student.id}</td>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							未提交
						</c:if>
						<c:if test="${!empty list.submitTime}">
							<fmt:formatDate value="${list.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</c:if>
					</td>
					<c:if test="${exam.status eq '2'}">
						<td style="text-align: center;">${list.rightPercent}</td>
						<td style="text-align: center;">${list.totalTitle}</td>
						<td style="text-align: center;">${list.totalRight}</td>
						<td style="text-align: center;">${list.totalError}</td>
					</c:if>
					<td style="text-align: center;">
						<c:if test="${exam.status eq '2'}">
							<c:if test="${!empty list.submitTime}">
								<input id="classExam_${list.student.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
							</c:if>
						</c:if>
						<c:if test="${exam.status eq '1'}">
							<c:if test="${list.submitTime eq null}">
								<input  style="background: url(${ctxStatic}/icon/tx.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="提醒"  id="remind_${list.student.id}_${exam.id}"/>
							</c:if>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form>
	  <div class="pagination">${student}</div>
</body>
</html>