<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>确认考试细节</title>
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
	  .form-horizontal .controls{margin-left:95px;}
	</style>
    <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
    <%@ include file="/WEB-INF/views/teacher/include/date.jsp"%>
    
    <link  href="${ctxStatic}/jquery-ui/css/jquery-accordion.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery.ui.core.js"></script>
    <script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery.ui.accordion.js"></script>
    <script type="text/javascript" src="${ctxStatic }/jquery/jquery.form.js"></script>
	<script type="text/javascript">
	  $(document).ready(function() {
		  
		  $("#accordion").accordion();
		   
		<%--  $("#btnSaveEmple").click(function(){
			     if(validate.form()){
			    	 
			    	 var knowledgeQuestions = [];
						$(".table input[id^='number']").each(function(i,e){
							
							var $this=$(this), 
							    id=$this.attr("id");
							    ids=id.split("_"),
				  			    knowledgeId=ids[0].replace("number",""),
				  			    quesType=ids[1],
				  			    quesLevel=ids[2];
							var score=$("#"+id.replace("number","score"));
							
							var scoreVal=score.val()==''? score.attr("placeholder"):score.val(),
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
						   var surplusScore=$("#surplusScore").val();
							if(surplusScore&&parseInt(surplusScore)==0){
								var jsonString=JSON.stringify(knowledgeQuestions);
								/* console.log(jsonString.replace('"','\"')); */
								$("#knowledgeQuestions").val(jsonString)

								$("#inputForm").ajaxSubmit({
									cache : false,
									dataType : "text",
									 url: "${ctx}/examination/saveEmple?id=${examination.id }", //请求url 
									success : function(html) {
										closeTip();
										alertx(html);
									},error:function(){
										closeTip();
										alertx("页面不存在!");
									}
								});
								
							}else{
								alertx("请检查出题分值！","error");
							}
						}else{
							alertx("请至少填写一组试题","error");
						}
						
					// $("#inputForm").attr("action","${ctx}/examination/saveEmple?id=${examination.id }");
					// $("#inputForm").submit();
			     }
			 });--%>
		  var validate =$("#inputForm").validate({
			    ignore:"",
				submitHandler: function(form){
					var knowledgeQuestions = [];
					$(".table input[id^='number']").each(function(i,e){
						
						var $this=$(this), 
						    id=$this.attr("id");
						    ids=id.split("_"),
			  			    knowledgeId=ids[0].replace("number",""),
			  			    quesType=ids[1],
			  			    quesLevel=ids[2];
						var score=$("#"+id.replace("number","score"));
						
						var scoreVal=score.val()==''? score.attr("placeholder"):score.val(),
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
					   var surplusScore=$("#surplusScore").val();
						if(surplusScore&&parseInt(surplusScore)==0){
							var jsonString=JSON.stringify(knowledgeQuestions);
							/* console.log(jsonString.replace('"','\"')); */
							$("#knowledgeQuestions").val(jsonString);

							
								loading('正在提交，请稍等...');
							    form.submit();
							
						}else{
							alertx("请检查出题分值！","error");
						}
					}else{
						alertx("请至少填写一组试题","error");
					}
					
			
			
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					}else if(element.parent().is("span")){
						error.insertAfter(element.parent());
					}else{
						error.insertAfter(element);
					}
				}
	     });
		  
		  
	  });
	</script>
</head>
<body>

 <sys:message content="${message}" />
 <fieldset>
   	<legend style="margin-left:10px;">确认考试细节</legend>
<form:form id="inputForm"  modelAttribute="examination" action="${ctx}/examination/examHandleDetails?id=${examination.id }" method="post" class="form-horizontal">
    <%--  <form:hidden path="id"/> --%>
     <input type="hidden" name="knowledgeQuestions" id="knowledgeQuestions">
			<div class="control-group">
				<h2>出题分值:</h2>
				<div class="controls">
				      试卷总分
				  <form:input path="totalScore" class="input-mini required digits"   cssStyle="width:60px ! important;"/>
				      已出分
				  <input id="beenOutScore" class="input-mini" type="text" disabled="disabled" style="width:60px ! important;"/>
				      剩余分
				  <input id="surplusScore" class="input-mini" type="text" disabled="disabled" style="width:60px ! important;" />
				</div>
			</div>
			<div class="control-group">
				<h3 class="con-t">试卷生成:</h3>
				<div class="controls">
					<form:radiobutton path="isAb" value="0"/>一套试卷
					<form:radiobutton path="isAb" value="1"/>AB卷
				</div>
			</div>
			<div class="control-group">
				<h2>试卷名称:</h2>
				<div class="controls">
					<form:input path="title" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font></span>
				</div>
			</div>
			<div class="control-group">
				<h2>适用班级:</h2>
				<div class="controls">
				  <span>
					<input name="classDataRelation" id="classDataRelation" type="hidden" class="input-xlarge required"/>
					  <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">添加相关班级</a>
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
							top.$.jBox.open("iframe:${ctx}/common/classList?pageSize=8", "添加相关专业",600,500,{
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
				<h2>考场地点:</h2>
				<div class="controls">
					<form:input path="examPlace" class="input-xxlarge"/>
				</div>
			</div>
			<jsp:useBean id="now" class="java.util.Date" />
			<fmt:formatDate value="${now}"  var="currentDate" pattern="yyyy-MM-dd HH:mm:ss"/>
			<div class="control-group">
			<h2>考试时间:</h2>
			
				<div class="controls">
					<span> <input id="beginTime" name="beginTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.beginTime}'  pattern='yyyy-MM-dd HH:mm:ss' />"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true, minDate:'${currentDate}',maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly/>
							<span class="help-inline"><font color="red">*</font></span>
						</span> -->
						<span> <input id="endTime" name="endTime" class="input-medium Wdate required" type="text"  value="<fmt:formatDate  value='${examination.endTime}'  pattern='yyyy-MM-dd HH:mm:ss' />"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'beginTime\')||\'${currentDate}\'}'})" readonly/>
					<span class="help-inline"><font color="red">*</font></span></span> 
				</div>
			</div>
			
			<c:choose>
				<c:when test="${examination.examType eq '5'}">
					<div class="control-group">
					   <h2>公布答案时间:</h2>
				       <div class="controls">
						 <input id="publishAnswerTime" name="publishAnswerTime" class="input-medium Wdate" type="text"  value="<fmt:formatDate  value='${examination.publishAnswerTime}'  pattern='yyyy.MM.dd HH:mm:ss' />"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'endTime\')}'})"  readonly/>
						</div>
					</div>
				</c:when>
				<c:otherwise>
				
				</c:otherwise>
			</c:choose>
			
			<div class="control-group">
				<h2>特殊说明:</h2>
				<div class="controls">
					<form:textarea path="remarks" rows="4" cols="50" style="width:700px;resize:none;"/>
				</div>
			</div>


	    <c:set var="quesTions" value="${fns:getDictList('question_type')}"/>
		<c:set var="dataList" value="${list}" />
      <c:choose>
         <c:when test="${fn:length(dataList)<=0}">
			      <div  style="text-align: center; height:50px; line-height: 50px;"><span class="help-inline"><font color='red'>知识点下没有题</font></span></div>
		 </c:when>
		 <c:otherwise>
  	    
  	    <div id="accordion">
		<c:forEach items="${dataList}" var="item" varStatus="i">
		 	<h3><a href="#">${item.courseKnowledge.title}(出题数量项，不填空视为不出题，出题数不能大于显示的数量；分值不填写的，适用默认分值)</a></h3>
		 	<div>
				<table  class="table table-striped table-bordered table-condensed " id="tableContent_${ i.index+1}">
					<tbody>
					<%-- 	<tr>
						    <td colspan="${fn:length(fns:getDictList('question_type'))}">${item.courseKnowledge.title}（出题数量项，不填空视为不出题，出题数不能大于显示的数量；分值不填写的，适用默认分值）</td>
						</tr>
						 --%>
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
							    <input class="input-mini"  type="text" id="number${item.courseKnowledge.id}_${qtype.value}_${qlevel.value}"  style="width: 30px ! important;font-weight:bold;color:red;"/>道<br/>
							    <input type="text" class="input-mini"  id="score${item.courseKnowledge.id}_${qtype.value}_${qlevel.value}"   style="width: 30px ! important;font-weight:bold;color:red;"/>分
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
	<!-- <input id="btnSaveEmple" class="btn btn-primary" type="button" value="存为模板"/> -->
	<div class="form-actions">
	    <a href="${ctx }/examination/selectKnowledge?id=${examination.id}" class="btn tbtn-primary" >上一步</a>
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="下一步"/>
	</div>
  </form:form>
</fieldset>
<script type="text/javascript">
  (function(){
  		var dataSource=${fns:toJson(dataList)};
  		var sourceList=${fns:toJson(sourceList)};
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
  						    $("#score"+knowledgeId+"_"+quesType+"_"+quesLevel).attr("placeholder",qv.quesPoint);
  						  $("#score"+knowledgeId+"_"+quesType+"_"+quesLevel).parent().css({"background-color":"gainsboro"});
  						}
  					});
  				} 
  			});  
  			 $.each(sourceList,function(k,v){
  				 if(v.knowledgeId==knowledgeId && v.questionType==quesType && v.questionDegree==quesLevel){
					$this.val(v.questionNumber);
				    $("#score"+knowledgeId+"_"+quesType+"_"+quesLevel).val(v.questionScore);
  				} 
  			});  
  		});
  		
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
		console.log(parseInt(val)>parseInt(placeholder));
		return success;
  	 }
  	 
  	 function calc(){
  		 
  		 var  beenOutScore=$("#beenOutScore"),suerplusScore=$("#surplusScore"), totalScore=$("#totalScore");
		  var totalVal=totalScore.val()==""? 0 : parseInt(totalScore.val());
		  var countVal=0;
		 
			$(".table input[id^='number']").each(function(){
				var  item=$(this);
				var  scoreVal=$("#"+item.attr("id").replace("number","score")).val()==''? $("#"+item.attr("id").replace("number","score")).attr("placeholder"):$("#"+item.attr("id").replace("number","score")).val(),
				     numberVal=item.val();
				if(numberVal !="" &&scoreVal !=""){
					console.log(scoreVal);
					countVal+=parseInt(scoreVal)*parseInt(numberVal);
				}
			});
			beenOutScore.val(countVal);
		 
		  if(totalVal>0){
			suerplusScore.val(totalVal-countVal);
		  }else{
			  suerplusScore.val("0");
		  }
  	 }
  	    
  	 $(".table input").change(function(){
  		var $this=$(this);
  		if(validata($this)){
  			calc();
  		}
  	 }).trigger("change");
  	 $("#totalScore").change(function(){
  		calc();
  	 });
/*   	$(".table input").trigger("change"); */
  })();
</script>
</body>
</html>