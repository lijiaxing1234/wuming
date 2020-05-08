<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>

	<title>确认考试细节</title>
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
	</style>
    <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
    <%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
    	
    <script type="text/javascript" src="${ctxStatic }/jquery/jquery.form.js"></script>
    
	<script type="text/javascript">
	  $(document).ready(function() {
		  
		  $("#inputForm").validate({
			    ignore: [],
				submitHandler: function(form){
					var sum=0,flag=true;
					 $("#difficultTip").html("");
					$("#difficult,#general,#simple").each(function(){
						 sum+=parseInt($(this).val());
					});
				    if(sum>0&&sum!==100){
				    	$("span#difficultTip").html($("<font color='red'>难易程度不是(100)%</font>"));
				    	console.log($("span#difficultTip").html());
				    	flag=false;
				    }
					if(flag){
						loading('正在处理，请稍等...');
						$(form).ajaxSubmit({
							cache : false,
							dataType : "html",
							success : function(html) {
								closeTip();
								$("#tipsModel").html(html);
								$("#tipsModel").modal({
									show : true,
									backdrop : 'static'
								});
								/* .on('hide.bs.modal',function(){
									window.location.replace('${ctx}/bs/message/systemPushLog?pageNo=1');
									return false;
								}) */
							},error:function(){
								closeTip();
								alertx("输入有误!");
							}
						});
					}else{
						top.$.jBox.tip("输入有误请检查","error",{persistent:true,opacity:0});
					}
					
					return false;
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					/* $("#messageBox").text("输入有误，请先更正。"); */
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					}else if(element.parent().is("span")){
						error.insertAfter(element.parent());
					}else{
						error.insertAfter(element);
					}
					
				}
			});
		  
		  
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
 
<form:form id="inputForm"  modelAttribute="examination" action="${ctx}/examination/examDetails?id=${examination.id}" method="post" class="form-horizontal">
     <fieldset>
	     	<legend>确认考试细节</legend>
			<div class="control-group">
				<h2 >题型及分值:</h2>
				<div class="controls">
				     <ul>
					     <c:forEach var="item" items="${fns:getDictList('question_type')}">
					       <li>
					          ${item.label} 总分:
					         <input id="quesScore_${item.value}" name="quesScore_${item.value}"  class="input-mini number required"  style="width: 60px ! important;"  min="0" type="text" value="${ questionlib:getExamQuestionScore(examQuestionScoreList,item.value).quesScore}"/>
					         <label for="quesScore_${item.value}">
					         &nbsp;&nbsp;
					           <c:choose>
					               <c:when test="${ not empty quesMap[item.value]}">
					                      <font>
					                                                         总分数： ${quesMap[item.value][0]['totalScore']} &nbsp;&nbsp;
					                                                         总题数： ${quesMap[item.value][0]['quesType_count']} &nbsp;&nbsp;
					                                                         详细：
					                          <c:forTokens var="str" items="${quesMap[item.value][0]['scoreList']}" delims="," varStatus="s">
					                          		${str}&nbsp;分&nbsp;${questionlib:groupbyString(quesMap[item.value][0]['scoreListInfo'])[str]}&nbsp;道		                          	
					                          		<c:if test="${not s.last}">,</c:if>
					                          </c:forTokens>					                                                       
					                      </font> 
					               </c:when>
					               <c:otherwise>
					                                       可用分数：0.0  
					               </c:otherwise>
					           </c:choose>
					         </label>
					      </li>
					     </c:forEach>
				     </ul>
				     
				</div>
			</div>
			<div class="control-group">
				<h2>考试总分:</h2>
				<div class="controls">
				  <div class="input-append">
					  <form:input path="totalScore" class="input-mini required"  readonly="true"/>
				  </div>
				   <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<h2>难度倾向:</h2>
				<div class="controls">
					    难题 <span><form:input path="difficult"  class="input-mini number required" min="0"  style="width: 60px ! important;" />%</span>
					    一般 <span><form:input path="general"  class="input-mini number required"  min="0" style="width: 60px ! important;" />%</span>
					    简单<span><form:input path="simple"  class="input-mini number required" min="0"  style="width: 60px ! important;" />%</span>
				 	 <span class="help-inline">   <span id="difficultTip"></span> 若此项不设置，则随机出题  </span> <a href="javascript:resit()" class="btn tbtn-primary" >恢复默认值</a>  
				 	
				</div>
			</div>
			
			<div class="control-group">
				<h2>考点学时:</h2>
				
				    <%-- <form:input path="examHours" class="input-mini"/> --%>
				   <c:forEach var="item" items="${examHoursList}" varStatus="i">
					    <div class="controls">
					      <span>
					         <input name="examHours" type="text"  class="input-mini number " style="width: 60px ! important;"  value="${not empty examination.examHoursList[i.index] ? examination.examHoursList[i.index] :item.creditHours}"/>
					        ${item.title}
					      </span>
					    </div>
				   </c:forEach>
			     <script type="text/javascript">
			        (function(){
			        	var reg = /\d+/g;
			        	$("input[name='examHours']").each(function(){
			        		var $input=$(this),
			        		    inputValue=$input.val();
			        		if(inputValue){
			        		  var ms = inputValue.match(reg),result=0;
			        		 // console.log(ms.join(','));
			        		  for(var i=0,len=ms.length;i<len;i++){
			        			  result+=parseInt(ms[i]);
			        		  }
			        		  $input.val(result);
			        		 // console.log(result);
			        		}
			        		//console.log("======================");
			        	});
			        })();
				     
			     </script>
			</div>
			<div class="control-group">
				<h3 class="con-tit">试卷生成:</h3>
				<div class="controls">
					<form:radiobutton path="isAb" value="0"/>一套试卷
					<form:radiobutton path="isAb" value="1"/>AB卷
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<h2>试卷名称:</h2>
				<div class="controls">
				    <div class="input-append">
						<form:input path="title" class="input-xxlarge required"/>
					</div>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			
			<%-- <div class="control-group">
				<label class="control-label">适用专业:</label>
				<div class="controls">
                     <syst:specilityTreeSelect id="specilityDataRelation" name="specilityDataRelation" 
                      url="${ctx}/common/specilityListTreeData"  
                      valueUrl="${ctx}/common/findSpecilityByExamId?examId=${examination.id}"
                       cssClass="input-xlarge required"/>					
				</div>
			</div> --%>
			
			<div class="control-group">
				<h2>适用班级:</h2>
				<div class="controls">
				    <span>
					 <input name="classDataRelation" id="classDataRelation" type="hidden" class="input-xlarge required"/>
					  <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">添加班级</a>
					  <span class="help-inline"><font color="red">*</font></span>
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
						
						<c:if test="${not empty examination.id}"> 
							$.getJSON("${ctx}/common/findClassByExamId",{examId:"${examination.id}", r:Math.random()},function(data){
								 for (var i=0; i<data.length; i++){
									 classSelect.push([data[i]["id"],data[i]["title"]]);
								 } 
								 classSelectRefresh();
							});
						</c:if>
						$("#classRelationButton").click(function(){
							top.$.jBox.open("iframe:${ctx}/common/classList?pageSize=8", "添加班级",600,500,{
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
				<h2>考场地点:</h2>
				<div class="controls">
					<form:input path="examPlace" class="input-xxlarge"/>
				</div>
			</div>
			<jsp:useBean id="now" class="java.util.Date" />
			<fmt:formatDate value="${now}"  var="currentDate" pattern="yyyy-MM-dd HH:mm:ss"/>
			<div class="control-group">
				<h2>考试时间:</h2>
				     
						 <span style="margin-left:40px;"> <input id="beginTime" name="beginTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.beginTime}'  pattern='yyyy-MM-dd HH:mm:ss' />"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true, minDate:'${currentDate}',maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly/>
							<span class="help-inline"> </span>
						</span> 
				 
				
			-->
				 
				   <span><input id="endTime" name="endTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.endTime}'  pattern='yyyy-MM-dd HH:mm:ss' />"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'beginTime\')||\'${currentDate}\'}'})" readonly/>
					<span class="help-inline"><font color="red">*</font></span></span> 
				 
			</div>
		<%-- 	<div class="control-group">
			<label class="control-label">结束时间:</label>
				<div class="controls">
				   <span><input id="endTime" name="endTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.endTime}'  pattern='yyyy-MM-dd HH:mm:ss' />"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'beginTime\')||\'${currentDate}\'}'})" readonly/>
					<span class="help-inline"><font color="red">*</font></span></span> 
				</div>
			</div> --%>
			<c:choose>
				<c:when test="${examination.examType eq '5'}">
					<div class="control-group">
						<h2  style=" width: 120px; ">公布答案时间:</h2>
						   <div class="controls">
							  <input id="publishAnswerTime" name="publishAnswerTime" class="input-medium Wdate" type="text"  value="<fmt:formatDate  value='${examination.publishAnswerTime}'  pattern='yyyy-MM-dd HH:mm:ss' />"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'endTime\')}'})" readonly/>
							</div>
					</div>
				</c:when>
				<c:otherwise>
				
				</c:otherwise>
			</c:choose>
			
			<div class="control-group">
				<h2>特殊说明:</h2>
				<div class="controls">
					<form:textarea path="remarks" rows="4" cols="50"  style="width:700px;resize:none;"  />
				</div>
			</div>
			<div class="form-actions">
			    <a href="${ctx }/examination/selectKnowledge?id=${examination.id}" class="btn tbtn-primary" >上一步</a>
				<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="下一步"/>
			</div>
	</fieldset>
</form:form>
	<div class="modal hide fade in" id="tipsModel" style="width: 400px; margin-left: -100px; top: 20%;"  >
	</div>
</body>
</html>