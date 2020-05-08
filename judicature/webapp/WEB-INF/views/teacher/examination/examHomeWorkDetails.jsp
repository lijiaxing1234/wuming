<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>确认作业出题细节</title>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	  <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	   .input-mini{width:30px;}
	   .form-horizontal .controls{margin-bottom:10px;}
	   .form-horizontal .control-group{padding-bottom:0;}
       .form-horizontal .controls ul li{margin-bottom:10px;}
	   .form-horizontal legend,.control-group .con-tit{padding:0;color:#666;border-bottom:0 none;font-size:16px;line-height:28px;border-left:8px solid #38a5ac;padding-left:10px;}
		.control-group h2{background:#eafcfe;padding-left:20px;color:#666;margin-bottom:10px;}
		.form-horizontal .controls{margin-left:40px;}
		.controls li label,.controls li font{color:#39aab6;}
		.input-mini{color:red ! important;font-weight:bold;}
	</style>
	<script type="text/javascript">
	  $(document).ready(function() {
		  
		  $("div.control-group:first input[name^='quesScore']").change(function(){
			  var totalScore=0;
			  $("div.control-group:first input[name^='quesScore']").each(function(key,value){
				  var val=$(value).val()||$(value).attr("placeholder");
				  if(!isNaN(val)){
				    totalScore +=parseInt(val);
				  }
			  });   
			  $("#totalScore").val(totalScore);
		  });
		  $("div.control-group:first input[name^='quesScore']").each(function(){
			 var value= $(this).val();
			 if(!!!value){
				 $(this).val(0);
			 }
		  });
		  $("#difficult,#general,#simple").each(function(){
				 var value= $(this).val();
				 if(!!!value){
					 $(this).val(0);
				 }
	      });
		  
		  
		  $("#inputForm").validate({
			    ignore:"",
				submitHandler: function(form){
					var beginTime=$("#beginTime").val();
					var endTime=$("#endTime").val();
					var examDay=$("#examDay").val();
					var examHour=$("#examHour").val();
					var examMinute=$("#examMinute").val();
					var answerDay=$("#answerDay").val();
					var answerHour=$("#answerHour").val();
					var answerMinute=$("#answerMinute").val();
					var className=$("#classDataRelation").val();
					var sum=0;
					 $("#difficultTip").html("");
					$("#difficult,#general,#simple").each(function(){
						 sum+=parseInt($(this).val());
					});
				    if(sum>0&&sum!==100){
				    	$("span#difficultTip").html($("<font color='red'>难易程度不是(100)%</font>"));
				    	/* $("#difficult").parent().find("span.help-inline").html("<font color='red'>难易程度不是(100)%</font>"); */
				    }else{
			    		/* if(className==null||className==""){
				    		alertx("请选择班级"); */
				    	if(examDay==null||examDay==""){
				    		examDay=0;
				    	}
			    		if(examHour==null||examHour==""){
			    			examHour=0;
			    		}
			    		if(answerDay==null||answerDay==""){
			    			answerDay=0;
				    	}
			    		if(answerHour==null||answerHour==""){
			    			answerHour=0;
			    		}
			    		examMinute=parseInt(examMinute)+parseInt(examHour*60)+parseInt(examDay*24*60);
			    		answerMinute=parseInt(answerMinute)+parseInt(answerHour*60)+parseInt(answerDay*24*60);
		    			$("#endHours").val(examMinute);
		    			$("#answerHours").val(answerMinute);
			    		var a=$("#endHours").val();
			    		var b=$("#endHours").val();
			    		if(a!=null&&parseInt(a)>=1){
			    			if(parseInt(a)>=262800){
			    				alertx("您所填作业用时已经超过半年了,请重新输入");
			    			}else{
			    				if(parseInt(b)>=262800){
				    				alertx("您所填公布答案时间已经超过半年了,请重新输入");
			    				}else{
			    					loading('正在提交，请稍等...');
					    			form.submit();
			    				}
			    			}
			    		}else{
			    				alertx("至少要留一分钟答题时间");
			    			}
			    		}
				},
				//errorContainer: "#messageBox",
				
				/* else{ 
				    		loading('正在提交，请稍等...');
							form.submit();
				     	} */
				
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else if(element.parent().is("span")){
						error.appendTo(element.parent());
					}else{
						error.insertAfter(element);
					}
				}
			});
	  });
	  
	  function resit()
	  {
		  $("#difficultTip").html("");
		  $("#difficult,#general,#simple").each(function(){
			  $(this).val(0);
			});
	  }
	</script>
</head>
<body>
 <sys:message content="${message}" />
<form:form id="inputForm"  modelAttribute="examination" action="${ctx}/examinationHomeWork/examDetails?id=${examination.id}" method="post" class="form-horizontal">
     <fieldset>
	     	<legend>确认作业出题细节</legend>
			<div class="control-group">
				<h2>题型:</h2>
				<div class="controls">
				     <ul>
					     <c:forEach var="item" items="${fns:getDictList('question_type')}">
					       <li>
					          ${item.label} 数量:
					         <input id="quesScore_${item.value}" name="quesScore_${item.value}"  style="width:60px ! important;" class="input-mini number required"  min="0" type="text" value="${ questionlib:getExamQuestionScore(examQuestionScoreList,item.value).quesScore}"/>
					         <label for="quesScore_${item.value}">
					         &nbsp;&nbsp;
					           <c:choose>
					               <c:when test="${ not empty quesMap[item.value]}">
					                      <font color="green"> 总题数： ${quesMap[item.value]}</font> 
					               </c:when>
					               <c:otherwise>
					                                       总题数：0 
					               </c:otherwise>
					           </c:choose>
					         </label>
					      
					      </li>
					     </c:forEach>
				     </ul>
				</div>
			</div>
			<div class="control-group">
				<h2>总数量:</h2>
				<div class="controls">
				  <span><form:input path="totalScore" class="input-mini required" cssStyle="width:60px ! important;"  readonly="true"/>
				  <span class="help-inline"><font color="red">*</font> </span>
				  </span>
				</div>
			</div>
			<div class="control-group">
				<h2>难度倾向:</h2>
				<div class="controls">
				  	难题 <span><form:input path="difficult"  class="input-mini number required" cssStyle="width:60px ! important;"  min="0"/>%</span>
					    一般 <span><form:input path="general"  class="input-mini number required"  cssStyle="width:60px ! important;"  min="0"/>%</span>
					    简单<span><form:input path="simple"  class="input-mini number required"  cssStyle="width:60px ! important;" min="0" />%</span>
				 	<!--  <span class="help-inline">难度倾向是(0%或者100%)</span>     -->
				 	  <span class="help-inline">   <span id="difficultTip"></span> 若此项不设置，则随机出题  </span> <a href="javascript:resit()" class="btn tbtn-primary" >恢复默认值</a>  
				 	
				 	   
				</div>
			</div>
			<div class="control-group">
				<h2>作业名称:</h2>
				<div class="controls">
				   <span><form:input path="title" class="input-xlarge required"/>
				  	 <span class="help-inline"><font color="red">*</font> </span>
				   </span>
				</div>
			</div>
			<div class="control-group">
				<h2>适用班级:</h2>
				<div class="controls">
				  <span>
					<input name="classDataRelation" id="classDataRelation" type="hidden" class="input-xlarge required"/>
				  <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">添加班级</a>
				  <span class="help-inline"><font color="red">*</font> </span>
				  </span>
				  <ol id="classSelectList"></ol>
				  
				  <script type="text/javascript">
						var classSelect = [];
						function classSelectAddOrDel(id,title){
							var isExtents = false, index = 0;
							for (var i=0; i<classSelect.length; i++){
								if (classSelect[i][0]==id){
									isExtents = true;
									index = i;
								}
							}
							if(isExtents){
								classSelect.splice(index,1);
							}else{
								classSelect.push([id,title]);
							}
							
							//classSelectRefresh();
						}
						function classSelectRefresh(){
							var classDataRelation=$("#classDataRelation"),
								classSelectList=$("#classSelectList");
							
							classDataRelation.val("");
							classSelectList.children().remove();
							
							for (var i=0; i<classSelect.length; i++){
								console.log(classSelect[i][1]);
								classSelectList.append("<li>"+classSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"classSelectAddOrDel('"+classSelect[i][0]+"','"+classSelect[i][1]+"');classSelectRefresh();\">×</a></li>");
								classDataRelation.val(classDataRelation.val()+classSelect[i][0]+",");
							} 
						}
						
					      $.getJSON("${ctx}/common/findClassByExamId",{examId:"${examination.id}", r:Math.random()},function(data){
							 for (var i=0; i<data.length; i++){
								 classSelect.push([data[i]["id"],data[i]["title"]]);
							 } 
							 classSelectRefresh();
						});
						
						$("#classRelationButton").click(function(){
							top.$.jBox.open("iframe:${ctx}/common/classList?pageSize=8", "添加相关班级",600,500,{
								buttons:{"确定":true}, loaded:function(h){
									$(".jbox-content", top.document).css("overflow-y","hidden");
								},submit:function(b,d,f){
									if(b){
									   classSelectRefresh();
									}
								},closed:function(){
									classSelectRefresh();
								}
							});
						});
					</script>
				</div>
			</div>
			
			<div class="control-group">
				<h2>作业用时:</h2>
				<div class="controls">
					<input id="endHours" name="endHours" type="hidden"/>
					<input id="examDay" name="examDay" type="text" class="input-mini number required"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>天<span class="help-inline"><font color="red">*</font> </span> &nbsp; &nbsp;
					<input id="examHour" name="examHour" type="text" class="input-mini number required"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>小时<span class="help-inline"><font color="red">*</font> </span>&nbsp; &nbsp;
					<input id="examMinute" name="examMinute" type="text" class="input-mini number required"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>分钟<span class="help-inline"><font color="red">*</font> </span>&nbsp;
				</div>
			</div>
			<div class="control-group">
				<h2>答案公布时间:</h2>
				<div class="controls">
					<input id="answerHours" name="answerHours" type="hidden"/>
					<input id="answerDay" name="answerDay" type="text" class="input-mini number"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>天 &nbsp; &nbsp;
					<input id="answerHour" name="answerHour" type="text" class="input-mini number"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>小时&nbsp; &nbsp;
					<input id="answerMinute" name="answerMinute" type="text" class="input-mini number"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>分钟&nbsp;
				<span class="help-inline"><font color="red">(作业结束后隔所填时间后发布答案，不填默认为不发布答案)</font> </span>
				</div>
			</div>
			<%-- <div class="control-group">
				<label class="control-label">最迟完成时间:</label>
				<div class="controls">
					<span><input id="endTime" name="endTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.beginTime}'  pattern='yyyy.MM.dd HH:mm:ss' />"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true, minDate: 'new date',maxDate:'#F{$dp.$D(\'publishAnswerTime\')}'})" readonly/>
					  <span class="help-inline"><font color="red">*</font> </span>
					</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">答案公布时间:</label>
				<div class="controls">
					<input id="publishAnswerTime" name="publishAnswerTime" class="input-medium Wdate" type="text"  value="<fmt:formatDate  value='${examination.endTime}'  pattern='yyyy.MM.dd HH:mm:ss' />"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'endTime\')}'})"  readonly/>
					</span>
				</div>
			</div> --%>
			
			<div class="form-actions">
			    <a href="${ctx}/examinationHomeWork/selectKnowledge?id=${examination.id}" class="btn tbtn-primary" >上一步</a>
				<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="开始组题" />
			</div>
	</fieldset>
</form:form>

</body>
</html>