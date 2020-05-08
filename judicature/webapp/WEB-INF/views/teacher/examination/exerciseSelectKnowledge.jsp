<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>随堂测试选择知识点</title>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
		#content{margin:5px 5px;}
		.center-jz li{margin-bottom:15px;}
		.form-horizontal .control-label{margin-right:5px;}
        .form-horizontal .control-label{width:auto;float:none;}
	</style>
    <script type="text/javascript">
      
    $(function(){
         var validate=$("#inputForm").validate({
        	    ignore:"",
				submitHandler: function(form){
					var className=$("#classDataRelation").val();
					var questionIds= $("#questionIds").val();
		    		if(className==null||className==""){
			    		alertx("请选择班级");
			    	}else{
			    		if(questionIds==null||questionIds==""){
				    		alertx("请选择考点");
				    	}else{
				    		form.submit();
				    	}
			     	}
						
				},
				//errorContainer: "#messageBox",
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
         
         $("#saveSubmit").click(function(){
    		 $('#inputForm').attr("action","${ctx}/exerciseExamination/saveExercise");
      		 $('#inputForm').submit();
 	      });
    	
         
         $("#btnSubmit").click(function(){
 	          $('#inputForm').submit();
 	      });
         var questionIds=$("#questionIds").val();
         
       /*   $("#btnSubmit").click(function(){
	 	    	var examTitle=$("#examTitle").val();
	 	    	var limitTime=$("#limitTime").val();
	 	    	var classDataRelation=$("#classDataRelation").val();
	 	    	var knowId=$("#knowId").val();
	 	    	if(examTitle==""||examTitle==null){
	 	    		jAlert("请输入练习名");
	 	    	}else{
	 	    		if(limitTime==""||limitTime==null){
	 	    			jAlert("请输入限制时间");
	 	    		}else{
	 	    			if(classDataRelation==""||classDataRelation==null){
	 	        			jAlert("请选择班级");
	 	        		}else{
	 	            		$('#inputForm').submit();
	 	        		}
	 	    		}
	 	    	}
	 	 }); */
    });
    
    
    
    
    /* function addHiddenInputValue(value,knowId){
    	 $("#questionId").val(value);
    	 $("#knowId").val(knowId);
     } */
    function addHiddenInputValue(value){
	   	 $("#questionIds").val(value);
    }
    </script>
</head>
<body>
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
	<input type="hidden" id="ckIds" value="${knowledgeIds}">
	<br>
	
		<h2 style="border-left:8px solid #38ABB7;padding-left:10px;font-size:16px;margin-bottom:10px;line-height:30px;">随堂练习</h2>
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
<form:form id="inputForm" modelAttribute="exam" action="${ctx}/exerciseExamination/selectQuestionMonitor" method="post" class="form-horizontal" style="margin-top:20px;">
        <input id="examId" type="hidden" name="id" value="${exam.id}"/>
        <input type="hidden" name="questionIds" id="questionIds" value="${questionIds}"/>
		<div class="control-group" style="padding-left: 20px;">
			<label class="control-label">练习名称:</label>
			    <span><input id="examTitle" name="title" type="text" class="input-xlarge required" value="${exam.title}"/>
			      <span class="help-inline"><font color="red">*</font> </span>
				</span>
				<label class="control-label">练习时间限制:</label>
				<span>
				 <input id="limitTime" name="examHours" type="text" class="input-mini required"  value="${exam.examHours}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>分钟
				 <span class="help-inline"><font color="red">*</font> </span>
				</span>
			</div>
			<div class="control-group" style="padding-left: 20px;">
			<label class="control-label">适用班级:</label>
			   <ol id="classSelectList" style="display:inline-block;margin:0 10px 0 20px;"></ol>
			      <span>
					  <input name="classDataRelation" id="classDataRelation" type="hidden" class="input-xlarge"/>
					  <a id="classRelationButton" href="javascript:" class="btn tbtn-primary">选择</a>
					  <span class="help-inline"><font color="red">*</font> </span>
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
						<c:if test="${ not empty exam.id}">
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
   	    <c:if test="${empty exam.id}">
			<input id="btnSubmit" class="btn tbtn-primary" type="button" value="发布练习"/>
   	    </c:if>
		<input id="saveSubmit" class="btn tbtn-primary" type="button" value="保存练习"/>
	</div>

		<script type="text/javascript">
			(function(){
				var setting = { data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},view:{showIcon:false}},
					callback:{ onClick:function(event, treeId, treeNode){
							var id = treeNode.id;
							$('#knowledgeContent').attr("src","${ctx}/exerciseExamination/selectQuestion?teacherQuestionId="+id); 
						}
					}
				};
				function refreshTree(){
					$.getJSON("${ctx}/common/courseKnowledgeByVersionTreeData",function(data){
						var treeObj=$.fn.zTree.init($("#ztree"), setting, data);//treeObj.expandAll(true);
						var examId=$("#examId").val();
						var ckIds=$("#ckIds").val();
						if(!!!examId){
							//$("#ztree > li:first > a:first").trigger("click");
							selectFirst(treeObj,1);
						}else{
							//$("#ztree > li:first > a:first").trigger("click");
							var ztreeObj=$.fn.zTree.getZTreeObj("ztree");
								
							//初始化右侧树
							var ckIds=$("#ckIds").val().split(",");
							if(ckIds.length>0){
								var nodes = ztreeObj.transformToArray(ztreeObj.getNodes());
							    for(var i=0;i<nodes.length;i++){			
									var node = nodes[i];
								    if(jQuery.inArray(node.id, ckIds) !==-1){
								    	//leftObj.checkNode(node, true, true);
								    	    //var zTree = $.fn.zTree.getZTreeObj("tree");//获取ztree对象  
							                //var node = zTree.getNodeByParam('id', 1);//获取id为1的点  
							                //zTree.selectNode(node);//选择点  
							                //zTree.setting.callback.onClick(null, zTree.setting.treeId, node);//调用事件  
								    	ztreeObj.selectNode(node);
								    	ztreeObj.setting.callback.onClick(null, ztreeObj.setting.treeId, node);//调用事件  
								    }
								}
							}
						}
						
					});
				}
				$("#left .icon-refresh").click(function(){
					refreshTree();
				});
				refreshTree();
				
			})();
			 (function(){
				 var frameObj = $("#left,#right,#right iframe"),mainRight=$(window),bottom=$("div.form-actions");
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