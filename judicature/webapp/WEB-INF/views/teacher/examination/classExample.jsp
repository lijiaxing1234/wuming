<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<title>首页的随堂例题</title>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
<style type="text/css">
	.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	.form-horizontal .control-label{float:none;}
	
</style>
    <script type="text/javascript" src="${ctxStatic }/jquery/jquery.form.js"></script>
<script type="text/javascript">
 
/*  function addHiddenInputValue(value,knowId){
	 $("#questionId").val(value);
	 $("#knowId").val(knowId);
 }  */
 function addHiddenInputValue(value){
   	 $("#questionIds").val(value);
} 
 
 $(function(){
	  $("#inputForm").validate({
		    ignore:"",
			submitHandler: function(form){
				var flag=true;
				var className=$("#classDataRelation").val();
				var questionIds=$("#questionIds").val();
				var title=$("#examTitle").val();
				if(title==null||title==""){
	    			flag=false;
		    		alertx("请填写例题名称");
		    	}else if(className==null||className==""){
	    			flag=false;
		    		alertx("请选择班级");
		    	}else{
			 	
			 		if(questionIds==null||questionIds==""){
		    			flag=false;
			    		alertx("请选择题目");
			    	}
		    	}
	    		if(flag){
					var classDataRelation=$("#classDataRelation").val();
			    	// window.location.href ='${ctx}/classExample/saveClassExample?classDataRelation='+classDataRelation+'&questionIds='+questionIds+'&examTitle='+title;
					form.submit();
	    		}
			},
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
	  
 })
	  function submitForm(){
	 		var classDataRelation=$("#classDataRelation").val();
	 		var questionIds=$("#questionIds").val();
	 		var title=$("#examTitle").val();
	 		$("#inputForm").submit();
	    	// window.location.href ='${ctx}/classExample/saveClassExample?classDataRelation='+classDataRelation+'&questionIds='+questionIds+'&examTitle='+title;
		 }
</script>
</head>
<body>
	<br>
<c:choose>
<c:when test="${totalQuesCount<=0}">
<script type="text/javascript">
    $(function(){
    	parent.removeJbox();	
		alertx("数据库中没有试题,请先导入试题后重试。",function(){
			parent.removeJbox();	
			 top.window.location.replace("${ctx}");
		});
    });
</script>
  <!--   <div style='padding:10px;color:red;'>数据库中没有试题,请先导入试题后重试。</div> -->
</c:when>
<c:otherwise>

 <h2 style="border-left:8px solid #38ABB7;padding-left:10px;font-size:16px;margin-bottom:10px;line-height:30px;">课堂例题</h2>
 <div class="clearfix">
<div id="left" class="accordion-group">
		<div class="accordion-heading">
	    	<span class="accordion-toggle" style="list-style-type: none;">知识点<i class="icon-refresh pull-right"></i></span>
	    </div>
	<div id="ztree" class="ztree"></div>
</div>
<div id="right">
		<iframe id="knowledgeContent" src="" width="100%" height="100%" frameborder="0"></iframe>
</div>
</div>
<form:form id="inputForm" modelAttribute="exam"  action="${ctx}/classExample/saveClassExample" method="post" class="form-horizontal" style="margin-top:20px;">
        <input id="examId" type="hidden" name="id" value=${exam.id}>
        <input id="questionIds" type="hidden" name="questionIds">
       <!--  <input id="knowId" type="hidden" name="knowId"  id="knowId"> -->
		<div class="control-group"> 
			<label class="control-label" style="margin-right:30px;">例题名称:</label>
	
				<span><input type="text" id="examTitle" name="title" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
				</span>
		
		</div>
		<div class="control-group"> 
			<label class="control-label">适用班级:</label>
			<ol id="classSelectList" style="display:inline-block;margin:0 10px 0 20px;"></ol>
			      <span>
				  <input name="classDataRelation" id="classDataRelation" type="hidden" class="input-xlarge"/>
				  <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">添加班级</a>
				  <span class="help-inline"><font color="red">*</font></span>
				  </span>
				  
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
						<c:if test="${not empty exam.id}">
					    $.getJSON("${ctx}/common/findClassByExamId",{examId:"${exam.id}", r:Math.random()},function(data){
							 for (var i=0; i<data.length; i++){
								 classSelect.push([data[i]["id"],data[i]["title"]]);
							 } 
							 classSelectRefresh();
						});
					    </c:if>
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
</form:form>

<div class="form-actions" style="border-top:0px; text-align: right; clear: both;">
    <input id="btnSubmit" class="btn tbtn-primary" type="button" value="保存例题" onclick="submitForm();"/>
</div>			
		<script type="text/javascript">
			(function(){
				var setting = { data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},view:{showIcon:false}},
					callback:{ onClick:function(event, treeId, treeNode){
							var id = treeNode.id;
							$('#knowledgeContent').attr("src","${ctx}/classExample/selectQuestion?teacherQuestionId="+id);
							$('#knowId').val(id);
						}
					}
				};
				function refreshTree(){
					$.getJSON("${ctx}/common/courseKnowledgeByVersionTreeData",{ r:Math.random()},function(data){
						var treeObj=$.fn.zTree.init($("#ztree"), setting, data);
						selectFirst(treeObj,1);
						//treeObj.expandAll(true);
						//$("#ztree > li:first > a:first").trigger("click");
					});
				}
				$("#left .icon-refresh").click(function(){
					refreshTree();
				});
				refreshTree();
			})();
			 (function(){
				 var frameObj = $("#left,#right"),mainRight=$(window),bottom=$("div.form-actions");
				function wSize(){
					/* mainRight.css({"overflow-y":"hidden"}); */
					frameObj.height(mainRight.height()- 5-bottom.height()-$("#inputForm").height()-40);
				    $("#left").width("180").css({"float":"left"});
					var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
					$("#right").width(mainRight.width()- leftWidth -40).css({"float":"left"}); 
				   $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46); 
				}
				$(window).resize(function(){
					wSize();
				});
				wSize();
			})(); 
		</script>
</c:otherwise>
</c:choose>
</body>
</html>