<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	<title>题目替换列表</title>
	<%@ include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	</style>
	
</head>
<body>

     <form:form id="searchForm"  action="${ctx}/examination/adjustExamInfo?questionId=${question.id}&examdetailId=${examdetail.id}&examId=${examId}&questype=${questype}" method="post" class="breadcrumb form-search ">
		 <%-- <input id="questionId" type="hidden" name="questionId" value="${question.id}" />
		 <input id="examdetailId" type="hidden" name="examdetailId" value="${examdetail.id}" />
		 <input id="examId" type="hidden" name="examId" value="${examId}" /> --%>
		<label>出题频率：</label>
		  <input id="beginTime" name="beginTime" type="text"  readonly="readonly" maxlength="20"  class="input-mini Wdate"
		         value="<fmt:formatDate value="${beginTime}" pattern="yyyy-MM-dd"/>" 
		         onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowClear:true,maxDate: 'new date'});"/>
		  ~<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowClear:true, minDate:'#F{$dp.$D(\'beginTime\')}',maxDate: 'new date'})"/>
		<label>出题类型：</label>
		
	    <select name="examType">
            <option value="">请选择</option>
            <option value="2,5"  ${examType eq "2,5" ? "selected='selected'":"" } >组卷和在线考试出题</option>
            <option value="1,3,4"  ${examType eq "1,3,4" ? "selected='selected'":"" }>随堂测试、课后作业以及课堂例题出题</option>
	    </select>
	    <br/>
	    <label>难易程度：</label>
	    <select name="examLevel" id="examLevel">
			<option value="">请选择</option>
	        <c:forEach var="qlevel" items="${fns:getDictList('question_level')}">
				<option value="${qlevel.value}"   ${examLevel eq qlevel.value ? "selected='selected'":"" }  >${qlevel.label}</option>
			</c:forEach>
	    </select>
	    
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/>
	</form:form>
  
	<table id="contentTable" class="table table-striped table-bordered table-condensed"  style="margin-top: 20px;">
		<thead>
			<tr>
				<th>编号</th>
				<th>题干</th>
				<th>出题频率</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach items="${list}" var="item" varStatus="index">
			<tr>
				<td>${index.index+1}</td>
				<td>${item.question.title}</td>
				<td>
				    ${item.count}
				</td>
				<td>
				    <a href="javascript:void(0);"  class="btn" id="${item.question.id}_${item.count}">替换</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		(function(){
			$("#contentTable tbody tr").find("td:last-child a").click(function(){
				
				var questionId=$("#questionId").val();
				var id=$(this).attr("id").split("_")[0];
				var count=$(this).attr("id").split("_")[1];
				var examdetailId=$("#examdetailId").val();
				var examId=$("#examId").val();
				
				return confirmx("确认要替换当前试题吗",function(){
				
					  $.ajax({  
					         type : "post",       
					         async : true,        //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
					         url : "${ctx}/examination/adjustExamInfo2",   
					         cache:false,
					         data : {replaceQuestionId:id,questionId:questionId,count:count,examdetailId:examdetailId,examId:examId},        
					         dataType : "json",        //返回数据形式为json
					         success : function(result) {
					        	 alertx("替换成功！",function(){
					       		    var obj=parent.frames['teacherMainFrame']; //parent.$("iframe[id='teacherMainFrame']:first")
									if(!!!obj){
									    obj=parent;
									}
					       		    if(obj){
					       		    	obj.top.$.jBox.close(true);
					       		    }else{
					       		    	top.$.jBox.close(true);
					       		    }
					        	 });
					        },
					         error : function(errorMsg) {
					             alertx("网络异常，请稍后重试!");
					         }
					    })
					
				});
			});
		})();
	</script>
</body>
</html>