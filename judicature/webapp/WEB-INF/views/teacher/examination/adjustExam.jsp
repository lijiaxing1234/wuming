<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>试卷调整</title>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	</style>
 <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.query.js"></script>
<script type="text/javascript">
 $(function(){
	  try{top.$.jBox.closeTip();}catch(e){}
	/*  $("#eidtTemplate").click(function(){
		  top.$.jBox($("#importForm").html(),{
				title:"编辑标题",
				width: 500,
			    height: 300,
				buttons:{'确认':'submit','取消':'cancel'}, 
			    bottomText:"",
			    loaded:function(h){
			    	var parameter=$.query.load($("#inputForm div[class='control-group']:last").find("a.btn:first").attr("href"));
			    	console.log($(h).find("#mainTitle"));
			    	$(h).find("#mainTitle").val(parameter.get("mainTitle"));
			    	$(h).find("#subTitle").val(parameter.get("subTitle"));
			    
			    },
			    submit:function(btn,jq,form){
			    	if(btn==='submit'){
			    		$("#inputForm div[class='control-group']:last").find("a.btn").each(function(key,value){
			    			var param=$.query.load(value.href).set("mainTitle",form["mainTitle"]).set("subTitle",form["subTitle"]);
			    			$(this).attr("href",$(this).attr("dataUrl")+param);
			    		});
			    		top.$.jBox.close(true);
			    	}else if(btn==='cancel'){
			    		top.$.jBox.close(true);
			    	}
			    }
		 });  
		 return false;
	 }); */
	 
	 
	  $("#inputForm").validate({
		    ignore:"",
			submitHandler: function(form){
				 form.submit();
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

function  validata(value,type){
	  
		  
	  var error=true;
		$("#mainTitle"+type+",#subTitle"+type).each(function(i,e){
			var  label = $("<label>").attr("for",e.id).addClass("error").html("必填项");
			var tag=$("label[for="+e.id+"]");
			!!tag&&tag.remove();
			
			if(!!!e.value){
				label.insertAfter($(e).parent());
				error=false;
			}
		});
		if(error){
			 var param=$.query.load(value.href).set("mainTitle",$("#mainTitle"+type).val()).set("subTitle",$("#subTitle"+type).val());
			  $(value).attr("href",$(value).attr("dataUrl")+param);
		  return true;
		}else{
			top.$.jBox.tip("输入有误请检查","error",{persistent:true,opacity:0});
		}
	 
	  return false;
}
$("#submitButton").click(function(){
	/* var mainTitle_A=$("#mainTitle_A").val();
	var subTitle_A=$("#subTitle_A").val();
	var examTypeB=$("#examTypeB").val();
	if(examTypeB==null){
		if(mainTitle_A==null&&subTitle_A==null){
			alertx("请检查主副标题是否填写正确");
		}else{
			$("#inputForm").attr("action","${ctx}/examination/updateExamTitle?mainTitle_A="+mainTitle_A+"&subTitle_A="+subTitle_A);
			$("#inputForm").submit();
		}
	}else{
		var mainTitle_B=$("#mainTitle_B").val();
		var subTitle_B=$("#subTitle_B").val();
		if(mainTitle_A==null&&subTitle_A==null&&mainTitle_B==null&&subTitle_B==null){
			alertx("请检查AB卷所有的主副标题是否填写正确");
		}else{
			$("#inputForm").attr("action","${ctx}/examination/updateExamTitle?mainTitle_A="+mainTitle_A+"&subTitle_A="+subTitle_A+"&mainTitle_B="+mainTitle_B+"&subTitle_B="+subTitle_B);
			$("#inputForm").submit();
		}
	} */
	
});
</script>
</head>
<body>

<form:form id="inputForm" modelAttribute="exam"  action="${ctx}/examination/updateExamTitle" method="post" class="form-horizontal">
<div class="form-horizontal">

   <fieldset>
	     	<legend>题目调整</legend>
	     	
			<div class="control-group" style="border-bottom:0px; padding-bottom: 0px;">
				 <input id="examId" type="hidden" name="id" value="${examination.id}" />
				 <input id="examType" type="hidden" name="examType" value="${examination.examType}" />
				<label class="control-label">名称:</label>
				<div class="controls">
				   <span>
					   <input id="examTitle" type="text" name="title" value="${examination.title}"  class="required"/>
					   <span class="help-inline"><font color="red">*</font></span>
				   </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">第1步:题目微调</label>
				<div class="controls">
				      
				     <c:forEach var="item" items="${examdetails}" >
				        <c:if test="${item.type eq '0'||item.type eq 'A'}">
				           <a href="${ctx}/examination/adjustExamList?examdetailId=${item.id}&examId=${examination.id}" class="btn">查看A卷</a>
				        </c:if>
				        <c:if test="${item.type eq 'B'}">
				         <a href="${ctx}/examination/adjustExamList?examdetailId=${item.id}&examId=${examination.id}" class="btn">查看B卷</a>
				        </c:if>
				     </c:forEach>
				     
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">试卷题数：</label>
				<div class="controls">
					${questionlibCount}道
				</div>
			</div>
			<c:choose>
				<c:when test="${examination.examType eq '3'}">
				
				</c:when>
				<c:otherwise>
					<div class="control-group">
						<label class="control-label">试卷总分：</label>
						<div class="controls">
						     ${examination.totalScore}分
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">试卷时长：</label>
						<div class="controls">
							${examination.timeDifference}分钟
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<c:if test="${examination.examType eq '2'}">
				<div class="control-group">
					<label class="control-label">第2步:选择打印模板</label>
					<div class="controls">
						<%-- <c:forEach var="item" items="${ }" varStatus="index">
						
						</c:forEach> --%>
						
						<input type="radio"  value=""  name="template"  checked="checked">系统模板一
						<!--<input type="radio"  value=""  name="template">系统模板一   <br/>
						 <input type="radio"  value=""  name="template">自定义模板一
						<input type="radio"  value=""  name="template">自定义模板二 -->
						
						 <!--  <a href="#" class="btn" id="eidtTemplate">编辑模板</a> -->
					    <!--   <a href="#" class="btn">上传模板数据</a>
					      <a href="#" class="btn">下载模板要求</a> -->
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">第3步:下载</label>
					
				<c:forEach var="item" items="${examdetails}" >
			       <c:if test="${item.type eq '0'||item.type eq 'A'}">
					<div class="controls" style="margin-bottom:10px;">
					    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
					     <span>
					     <input id="mainTitle_A" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }"/>
					      	<span class="help-inline"><font color="red">*</font> </span>
					     </span>
					     
					</div>
					<div class="controls" style="margin-bottom:10px;">
					 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
					     <span>
					        <input id="subTitle_A" name="subTitle" type="text" value="${item.subTitle }"  class="input-medium" />
					    	<span class="help-inline"><font color="red">*</font></span>
					     </span>
					</div>
					
					<div class="controls">
			            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"   onclick="return validata(this,'_A');"  >下载试卷A</a>
			            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById" onclick="return validata(this,'_A');"  >下载试卷A答案及讲解</a>
					</div>
			    </c:if>
			    
			    
		        <c:if test="${item.type eq 'B'}">
		        	<input id="examTypeB" name="examTypeB" type="hidden"  class="input-medium" value="${item.type}"/>
		          	<div class="controls" style="margin-bottom:10px;">
		          		<br>
					    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
					     <span>
					     <input id="mainTitle_B" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }" />
					      	<span class="help-inline"><font color="red">*</font> </span>
					     </span>
					     
					</div>
					<div class="controls" style="margin-bottom:10px;">
					 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
					     <span>
					        <input id="subTitle_B" name="subTitle" type="text"  class="input-medium" value="${item.subTitle }" />
					    	<span class="help-inline"><font color="red">*</font></span>
					     </span>
					</div>
		          <div class="controls">
		            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"  onclick="return validata(this,'_B');"  >下载试卷B</a>
		            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById"  onclick="return validata(this,'_B');"  >下载试卷B答案及讲解</a>
		          </div>
		        </c:if>
		     </c:forEach>
				</div>
			</c:if>
			<c:if test="${examination.examType eq '3'}">
				<div class="control-group">
						<label class="control-label">第2步:下载</label>
					<c:forEach var="item" items="${examdetails}" >
				       <c:if test="${item.type eq '0'||item.type eq 'A'}">
						<div class="controls" style="margin-bottom:10px;">
						    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
						     <span>
						     <input id="mainTitle_A" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }"/>
						      	<span class="help-inline"><font color="red">*</font> </span>
						     </span>
						     
						</div>
						<div class="controls" style="margin-bottom:10px;">
						 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
						     <span>
						        <input id="subTitle_A" name="subTitle" type="text" value="${item.subTitle }"  class="input-medium" />
						    	<span class="help-inline"><font color="red">*</font></span>
						     </span>
						</div>
						
						<div class="controls">
				            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"   onclick="return validata(this,'_A');"  >下载试卷A</a>
				            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById" onclick="return validata(this,'_A');"  >下载试卷A答案及讲解</a>
						</div>
				    </c:if>
				    
				    
			        <c:if test="${item.type eq 'B'}">
			     	   <input id="examTypeB" name="examTypeB" type="hidden"  class="input-medium" value="${item.type}"/>	
			          	<div class="controls" style="margin-bottom:10px;">
						    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
						     <span>
						     <input id="mainTitle_B" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }" />
						      	<span class="help-inline"><font color="red">*</font> </span>
						     </span>
						     
						</div>
						<div class="controls" style="margin-bottom:10px;">
						 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
						     <span>
						        <input id="subTitle_B" name="subTitle" type="text"  class="input-medium" value="${item.subTitle }" />
						    	<span class="help-inline"><font color="red">*</font></span>
						     </span>
						</div>
			          <div class="controls">
			            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"  onclick="return validata(this,'_B');"  >下载试卷B</a>
			            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById"  onclick="return validata(this,'_B');"  >下载试卷B答案及讲解</a>
			          </div>
			        </c:if>
			     </c:forEach>
				</div>
			</c:if>
				  <input  style="margin-left: 300px;" id="submitButton" type="button" class="btn tbtn-primary" value="确定" onclick='var mainTitle_A=$("#mainTitle_A").val();  var subTitle_A=$("#subTitle_A").val(); var examTypeB=$("#examTypeB").val();if(examTypeB==""||examTypeB==null){if(mainTitle_A==""||subTitle_A==""){alertx("请检查主副标题是否填写正确");}else{$("#inputForm").attr("action","${ctx}/examination/updateExamTitle?mainTitle_A="+mainTitle_A+"&subTitle_A="+subTitle_A);	$("#inputForm").submit();}}else{var mainTitle_B=$("#mainTitle_B").val();var subTitle_B=$("#subTitle_B").val();if(mainTitle_A==""||subTitle_A==""||mainTitle_B==""||subTitle_B==""){alertx("请检查AB卷所有的主副标题是否填写正确");}else{	$("#inputForm").attr("action","${ctx}/examination/updateExamTitle?mainTitle_A="+mainTitle_A+"&subTitle_A="+subTitle_A+"&mainTitle_B="+mainTitle_B+"&subTitle_B="+subTitle_B);$("#inputForm").submit();}}'/>
	</fieldset>
</div>
</form:form>
<%-- <form:form id="importForm"  method="post" class="form-horizontal hide">
     <table id="importTable">
		<tr>
			<th class="control-label">主标题：</th>
			<td>
			    <input id="mainTitle" name="mainTitle" type="text"  class="input-medium"  style="width:220px;"/>
			</td>
		</tr>
		<tr>
			<th class="control-label">副标题：</th>
			<td>
			    <input id="subTitle" name="subTitle" type="text"  class="input-medium"  style="width:220px;"/>
			</td>
		</tr>
	</table>
</form:form> --%>
</body>
</html>