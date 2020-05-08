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
	legend,.control-group h3{padding:0;color:#666;border-bottom:0 none;font-size:16px;line-height:28px;border-left:8px solid #38a5ac;padding-left:10px;}
	.control-group h2{background:#eafcfe;padding-left:20px;color:#666;margin-bottom:10px;}
	.form-horizontal .controls{margin-left:80px;}
	.form-horizontal .control-label{width:95px;}
	</style>
    <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
    <%@ include file="/WEB-INF/views/teacher/include/date.jsp"%>
	<script type="text/javascript">
	  $(document).ready(function() {
		  try{ parent.removeJbox();}catch(e){}
		  $("#btnSaveEmple").click(function(){
				 loading('正在提交，请稍等...');
				 $("#inputForm").attr("action","${ctx}/template/saveEmple");
				 $("#inputForm").submit();
			 });
		  $("#inputForm").validate({
				submitHandler: function(form){
					var classDataRelation=$("#classDataRelation").val();
					var beginTime=$("#beginTime").val();
					var endTime=$("#endTime").val();
					if(classDataRelation==""||classDataRelation==null){
						alertx("请选择班级");
					}else{
						form.submit();
					}
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
	  });
	</script>
</head>
<body>

 <sys:message content="${message}" />
 <fieldset>
   	<legend style="margin-left:10px;">确认考试细节</legend>
   <form:form id="inputForm"  modelAttribute="examination" action="${ctx}/template/saveTemplate" method="post" class="form-horizontal">
    <%--  <form:hidden path="id"/> --%>
     <input type="hidden" name="examType" id="examType" value="${examination.examType }">
     <input type="hidden" name="id" id="examId" value="${examination.id}">
     <input type="hidden" name="knowledgeQuestions" id="knowledgeQuestions">
			<div class="control-group">
				<h2>试卷总分:</h2>
				<div class="controls">
				      <!-- 试卷总分 -->
				  <form:input path="totalScore" class="input-mini required digits" readonly="true"/>
				   <!--    已出分
				  <input id="beenOutScore" class="input-mini" type="text" disabled="disabled"/>
				      剩余分
				  <input id="surplusScore" class="input-mini" type="text" disabled="disabled"/> -->
				</div>
			</div>
			<div class="control-group">
				<h3>试卷生成:</h3>
				<div class="controls">
					<form:radiobutton path="isAb" value="0"/>一套试卷
					<form:radiobutton path="isAb" value="1"/>AB卷
				</div>
			</div>
			<div class="control-group">
				<h2>试卷命名:</h2>
				<div class="controls">
					<form:input path="title" class="input-xxlarge"/>
				</div>
			</div>
			<div class="control-group">
				<h2>适用班级:</h2>
				<div class="controls">
					<input name="classDataRelation" id="classDataRelation" type="hidden" class="input-xlarge required"/>
				  <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">添加相关班级</a>
					  <span class="help-inline"><font color="red">*</font></span>
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
						
					      $.getJSON("${ctx}/common/findClassByExamId",{examId:"${examination.id}", r:Math.random()},function(data){
							 for (var i=0; i<data.length; i++){
								 classSelect.push([data[i]["id"],data[i]["title"]]);
							 } 
							 classSelectRefresh();
						});
						
						$("#classRelationButton").click(function(){
							top.$.jBox.open("iframe:${ctx}/common/classList?pageSize=8", "添加相关班级",600,500,{
								buttons:{"确定":true}, 
								loaded:function(h){
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
		<%-- 	<div class="control-group">
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
				    
						 <span> <input id="beginTime" name="beginTime" class="input-medium Wdate required" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true, minDate:'${currentDate}',maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly/>
							<span class="help-inline"><font color="red">*</font></span>
						</span> 
				 
			--
		  
				   <span><input id="endTime" name="endTime" class="input-medium Wdate required" type="text" 
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'beginTime\')||\'${currentDate}\'}'})" readonly/>
					<span class="help-inline"><font color="red">*</font></span></span> 
				</div>
			</div>
			<div class="control-group">
				<h2>公布答案时间:</h2>
			   <div class="controls">
				   <input id="publishAnswerTime" name="publishAnswerTime" class="input-medium Wdate" type="text"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', autoPickDate:true,isShowClear:true,minDate:'#F{$dp.$D(\'endTime\')}'})" readonly/>
			  </div>
			</div>
			<div class="control-group">
				<h2>特殊说明:</h2>
				<div class="controls">
					<form:textarea path="remarks" rows="4" cols="50"  style="width:700px;resize:none;"/>
				</div>
			</div>

<%-- 	<c:set var="quesTions" value="${fns:getDictList('question_type')}"/>
	<c:set var="dataList" value="${list}" />
	<c:forEach items="${dataList}" var="item">
		<table  class="table table-striped table-bordered table-condensed">
			<tbody>
				<tr>
				    <td colspan="${fn:length(fns:getDictList('question_type'))}">${item.courseKnowledge.title}（出题数量项，不填空视为不出题，出题数不能大于显示的数量；分值不填写的，适用默认分值）</td>
				</tr>
				
				<tr>
				   <c:forEach var="qtype" items="${quesTions}" varStatus="index">
				     <td>
				          ${qtype.label}
				     </td>
				   </c:forEach>
				</tr>
				<c:forEach var="qlevel" items="${fns:getDictList('question_level')}">
				<tr>
				   <c:forEach var="qtype" items="${quesTions}" varStatus="index">
					<td>
					    ${qlevel.label}<input class="input-mini" type="text" id="number${item.courseKnowledge.id}_${qtype.value}_${qlevel.value}"/>道<br/>
					         每题<input type="text" class="input-mini"  id="score${item.courseKnowledge.id}_${qtype.value}_${qlevel.value}" />分
					</td>
					</c:forEach>
				</tr>
			    </c:forEach>
			</tbody>
		</table>
	</c:forEach>  --%>
	<%--<input id="btnSaveEmple" class="btn btn-primary" type="button" value="存为模板"/>--%>
	<div class="form-actions">
	 <%--    <a href="${ctx }/examination/selectKnowledge?id=${exam.id}" class="btn btn-primary" >上一步</a> --%>
		<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="下一步"/>
	</div>
  </form:form>
</fieldset>
<%--  <script type="text/javascript">
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
  		
  	 $(".table input").change(function(){
  		var $this=$(this);
  		if(validata($this)){
  			var  beenOutScore=$("#beenOutScore"),
			     suerplusScore=$("#surplusScore"),
			     totalScore=$("#totalScore");
           var totalVal=totalScore.val()==""? 0 : parseInt(totalScore.val());
           var countVal=0;
 			$(".table input[id^='number']").each(function(){
 				var  item=$(this);
 				var  scoreVal=$("#"+item.attr("id").replace("number","score")).val(),
 				     numberVal=item.val();
 				if(numberVal !="" && scoreVal!=""){
 					countVal+=parseInt(scoreVal)*parseInt(numberVal);
 				}
 			});
 			beenOutScore.val(countVal);
 			suerplusScore.val(totalVal-countVal);
  		}
  		
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
		console.log(parseInt(val)>parseInt(placeholder));
		return success;
  	 }
	   
  })();
</script> --%>
</body>
</html>