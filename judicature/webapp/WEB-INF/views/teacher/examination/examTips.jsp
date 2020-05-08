<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>


<c:choose>
  <c:when test="${empty message}">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h2>系统提示</h2>
	</div>
	<form action="${ctx }/examination/examTipsSave" method="post">
	<div class="modal-body" style="overflow-x: hidden;overflow-y: scroll; height:200px;">
	
		
		
		 <input type="hidden" name="id" value="${examination.id}">
		 <input type="hidden" name="countNumber" id="countNumber">
		<c:forEach var="item" items="${examdetails}" begin="0" end="0">
		   <input  type="hidden"  id="examDetailId" name="examDetailId" value="${item.id}"/>
			<div class="control-group">
				<label class="control-label" style="margin-bottom: 20px;">试卷出题情况:</label><%-- ${item.type eq '0' ? "试":item.type} --%>
				<div class="controls">
				      试卷总分
				  <input id="totalScore" name="totalScore" value="${examination.totalScore}" style="width: 60px ! important;" type="text" disabled="disabled"/>
				      已出分
				  <input id="beenOutScore" style="width: 60px ! important;" value="${item.scores}" type="text" disabled="disabled"/>
				      剩余分
				  <input id="surplusScore" style="width: 60px ! important;" value="${examination.totalScore - item.scores }" type="text" disabled="disabled"/>
				</div>
			</div>
		</c:forEach>
		
		
		<div class="control-group">
			<label class="control-label" style=" margin-bottom: 20px;">请选择用什么题型填补:</label>
			<div class="controls">
			
			 <c:forEach  items="${fns:getDictList('question_score')}" var="qs">
			     <c:set  var="quesType" value="${fns:getDictValue(qs.label,'question_type','')}"/>
			 	<c:if test="${not empty quesType}">
				 	<input id="quesType_${quesType }" name="quesType"  value="${quesType }"  type="radio" score="${qs.value}" />
				    <label for="quesType_${quesType }">${qs.label }</label>
			 	</c:if>
			 	
			 </c:forEach>
			
			  
			  
			</div>
		</div>
		<div id="messageBox" class="alert hide">
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn tbtn-primary" data-dismiss="modal">关闭</button>
		<input type="submit"  class="btn tbtn-primary" disabled="disabled" value="下一步"/>
	</div>
	</form>
	<script type="text/javascript">
	  (function(){
		  var surplusScore=$("#surplusScore").val(),examDetailId=$("#examDetailId").val();
		      surplusScore=parseInt(surplusScore);
		      
		  $("div.control-group input[name='quesType']").each(function(){
			  $(this).click(function(){
			  $("#messageBox").empty().hide();
			  $("div.modal-footer input.btn").attr("disabled","disabled");
			  var $this=$(this),value=$this.val(),score=$this.attr("score");
			  if(surplusScore % score==0){
			    var count=surplusScore / parseInt(score);
			    var reulst=0,examId='${examination.id}';
			    $.ajax({
				     type: 'POST',
				     url: "${ctx}/examination/countVersionQuesByExamId",
				     data: {id:examId,examDetailId:examDetailId,quesType:value,r:Math.random()} ,
				     cache:'false',
				     success: function(data){
				    	 reulst=parseInt(data);
				    	// console.log(count+"=="+reulst);
				    	 
			    	    if(reulst>=count){
					    	 $("#countNumber").val(count);
					    	 $("div.modal-footer input.btn").removeAttr("disabled");
					    }else{
					    	$("#messageBox").text("没有符合条件的试题,请调整题型及分值后重试").addClass("alert-info").show();
					    }
				    	 
				     } ,
				     dataType: "json"
				});
			  }else{
				 $("#messageBox").text("没有符合条件的试题,请调整题型及分值后重试").addClass("alert-info").show();
			  }
			 });
		  });
	      
		  /* $("div.modal-footer button.btn:last").click(function(){
			  alert(1);
		  }); */
		  
		  if(surplusScore==0){
			  $("div.modal-body div.control-group:last").hide();
			 /*  $("div.modal-footer button:last").attr("disabled","") */;
			 //top.window.location.replace('${ctx}/examination/adjustExam?id=${examination.id}');
			 window.location.replace('${ctx}/examination/adjustExam?id=${examination.id}');
		  }
	  })();
	</script>
  
  </c:when>
  <c:otherwise>
       
    <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h2>系统提示</h2>
	</div>
	<div class="modal-body" style="overflow-x: hidden;overflow-y: scroll; height:50px; width: 400px;">
		<div id="messageBox" class="alert alert-info">
		   ${message}
		</div>
	</div>
  </c:otherwise>
</c:choose>

