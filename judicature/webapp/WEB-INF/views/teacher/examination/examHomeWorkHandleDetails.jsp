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
	   .input-error{
	       background: url("${ctxStatic}/jquery-validation/1.11.0/images/unchecked.gif") no-repeat 0 0;
		    padding-left: 18px;
		    padding-bottom: 2px;
		    font-weight: bold;
		    color: #ea5200;
		    margin-left: 10px;
      }
      legend,.control-group .con-t{padding:0;color:#666;border-bottom:0 none;font-size:16px;line-height:28px;border-left:8px solid #38a5ac;padding-left:10px;}
	  .control-group h2{background:#eafcfe;padding-left:20px;color:#666;margin-bottom:10px;}
	  .ui-accordion .ui-accordion-header{background:#eafcfe;border:0 none;margin:10px auto ! important;}
	  .form-horizontal .controls{margin-left:80px;}
	  .input-mini{color:red ! important;font-weight:bold;}
	</style>
  
    <link  href="${ctxStatic}/jquery-ui/css/jquery-accordion.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery.ui.core.js"></script>
    <script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery.ui.accordion.js"></script>
    
	<script type="text/javascript">
	  $(document).ready(function() {
		  
		  $("#accordion").accordion();
		  
		  $("#inputForm").validate({
			    ignore:"",
				submitHandler: function(form){
					var examDay=$("#examDay").val();
					var examHour=$("#examHour").val();
					var examMinute=$("#examMinute").val();
					var answerDay=$("#answerDay").val();
					var answerHour=$("#answerHour").val();
					var answerMinute=$("#answerMinute").val();
					var knowledgeQuestions = [];
					$(".table input[id^='number']").each(function(i,e){
						var $this=$(this), 
						    id=$this.attr("id");
						    ids=id.split("_"),
			  			    knowledgeId=ids[0].replace("number",""),
			  			    quesType=ids[1],
			  			    quesLevel=ids[2];
						var score=$("#"+id.replace("number","score"));
						
						var scoreVal=score.val(),
						    numberVal=$this.val();
						if(scoreVal!=""&&numberVal!=""){
							var item={};
							    item.knowledgeId=knowledgeId;
							    item.questionType=quesType;
							    item.questionDegree=quesLevel;
							    item.questionScore=scoreVal;
							    item.questionNumber=numberVal;
						   knowledgeQuestions.push(item);
						}
						
					});
					
					if(knowledgeQuestions.length>0){
						var jsonString=JSON.stringify(knowledgeQuestions);
						/* console.log(jsonString.replace('"','\"')); */
						$("#knowledgeQuestions").val(jsonString);
						var date=$("#beginTime").val();
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
						/* if(date.length<=0){
							alertx("请填写最迟完成时间")
						}else{
							if(($("#classDataRelation").val()).length<=0){
								alertx("请选择班级")
							}else{
								loading('正在提交，请稍等...');
								form.submit();
							}
						} */
					}else{
						alertx("请至少填写一组试题","error");
					}
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
	  });
	</script>
</head>
<body>
 <sys:message content="${message}" />
<form:form id="inputForm"  modelAttribute="examination" action="${ctx}/examinationHomeWork/examHandleDetails?id=${examination.id}" method="post" class="form-horizontal">
      <input type="hidden" id="formType">
      <input type="hidden" name="knowledgeQuestions" id="knowledgeQuestions">
    <%--   <input type="hidden" id="examId" name="examId" value="${examination.id}"> --%>
   
     <fieldset>
	     	<legend>确认作业出题细节</legend>
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
				    <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">选择</a>
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
				<h2>最迟完成时间:</h2>
				<div class="controls">
					<input id="endHours" name="endHours" type="hidden"/>
					<input id="examDay" name="examDay" type="text" class="input-mini number required"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>天<span class="help-inline"><font color="red">*</font> </span> &nbsp; &nbsp;
					<input id="examHour" name="examHour" type="text" class="input-mini number required"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>小时<span class="help-inline"><font color="red">*</font> </span>&nbsp; &nbsp;
					<input id="examMinute" name="examMinute" type="text" class="input-mini number required"  style="width:60px ! important;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>分钟<span class="help-inline"><font color="red">*</font> </span>&nbsp;
				</div>
				<%-- <div class="controls">
 					<span><input id="endTime" name="endTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.beginTime}'  pattern='yyyy.MM.dd HH:mm:ss' />"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true, minDate: 'new date',maxDate:'#F{$dp.$D(\'publishAnswerTime\')}'})" readonly/>
					  <span class="help-inline"><font color="red">*</font> </span>
					</span>
				</div> --%>
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
				<%-- <div class="controls">
					<input id="publishAnswerTime" name="publishAnswerTime" class="input-medium Wdate" type="text"  value="<fmt:formatDate  value='${examination.endTime}'  pattern='yyyy.MM.dd HH:mm:ss' />"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'endTime\')}'})" readonly/>
				</div> --%>
			</div>
			<c:set var="quesTions" value="${fns:getDictList('question_type')}"/>
			<c:set var="dataList" value="${list}" />
			<c:choose>
			
			 <c:when test="${fn:length(dataList)<=0}">
			      <div  style="text-align: center; height:50px; line-height: 50px;"><span class="help-inline"><font color='red'>知识点下没有题</font></span></div>
			 </c:when>
			 <c:otherwise>
			   	<div id="accordion">
					<c:forEach items="${dataList}" var="item">
					 	<h3><a href="#">${item.courseKnowledge.title}(出题数量项，不填空视为不出题，出题数不能大于显示的数量.)</a></h3>
		 			<div>
						<table  class="table table-striped table-bordered table-condensed">
							<tbody>
							<%-- 	<tr>
								    <td colspan="${fn:length(fns:getDictList('question_type'))}">${item.courseKnowledge.title}（出题数量项，不填空视为不出题，出题数不能大于显示的数量）</td>
								</tr> --%>
								<tr>
								    <td></td>
								   <c:forEach var="qtype" items="${quesTions}" varStatus="index">
								     <td>
								          ${qtype.label}
								     </td>
								   </c:forEach>
								</tr>
								<c:forEach var="qlevel" items="${fns:getDictList('question_level')}">
								<tr>
								   <td>
								      ${qlevel.label}
								   </td>
								   <c:forEach var="qtype" items="${quesTions}" varStatus="index">
									<td>
									    <input class="input-mini" type="text" style="width: 30px ! important;" id="number${item.courseKnowledge.id}_${qtype.value}_${qlevel.value}"/>道
									</td>
									</c:forEach>
								</tr>
							    </c:forEach>
							</tbody>
						</table>
						</div>
					</c:forEach>
							
					</div> 
			 	</c:otherwise>
			</c:choose>
			<div class="form-actions">
			    <a href="${ctx}/examinationHomeWork/selectKnowledge?id=${examination.id}" class="btn tbtn-primary" >上一步</a>
				<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="开始组卷"/>
			</div>
	</fieldset>
</form:form>
<script type="text/javascript">
  (function(){
  		var dataSource=${fns:toJson(dataList)};
  		//赋值最多多少道题、多少分
  		$(".table input[id^='number']").each(function(){
  			var $this=$(this), 
  			    ids=$this.attr("id").split("_"),
  			    knowledgeId=ids[0].replace("number",""),
  			    quesType=ids[1],
  			    quesLevel=ids[2];
  			 
  			$this.attr("placeholder",0);  
  			$("#score"+knowledgeId+"_"+quesType+"_"+quesLevel).attr("placeholder",0);
  			 $.each(dataSource,function(k,v){
  				 if(v.courseKnowledge.id==knowledgeId){
  					$.each(v.questions,function(qk,qv){
  						if(qv.quesType==quesType&&qv.quesLevel==quesLevel){
  							$this.attr("placeholder",qv.count);
  						    //$("#score"+knowledgeId+"_"+quesType+"_"+quesLevel).attr("placeholder",qv.quesPoint);
  						    $this.parent().css({"background-color":"gainsboro"});
  						}
  					});
  				} 
  			});  
  			
  		});
  		
  	 $(".table input").change(function(){
  		var $this=$(this);
  		validata($this);
  	 });
  	$(".table input").trigger("change");
  	 function validata($this){
  		 var id=$this.attr("id"),
  		     val=$this.val(),
  		   placeholder=$this.attr("placeholder");
  		 
  		var success=true,
 		    label = $("<label>").attr("for",id).addClass("input-error").html("只能输入[>=0]的数字"),
		    tag=$("label[for="+id+"]");
  		
		!!tag&&tag.remove();
		
		if(/^[0-9]*$/.test(jQuery.trim(val))<= 0){
			label.insertAfter($this);
			success=false;
		}else{
			if(parseInt(val)>parseInt(placeholder)){
				label.insertAfter($this).html("比最大值还大");
				success=false;
			}
		}
		return success;
  	 }
	   
  })();
</script>
</body>
</html>