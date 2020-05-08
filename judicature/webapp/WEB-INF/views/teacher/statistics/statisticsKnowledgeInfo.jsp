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
</style>
<script type="text/javascript" src="${ctxStatic }/jquery/jquery.form.js"></script>
<script type="text/javascript">
 function addHiddenInputValue(value){
   	 $("#questionIds").val(value);
} 
 
 $(function(){
	  $("#inputForm").validate({
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
<div id="left" class="accordion-group">
		<div class="accordion-heading">
	    	<span class="accordion-toggle" style="list-style-type: none;">知识点<i class="icon-refresh pull-right"></i></span>
	    </div>
		<div id="ztree" class="ztree"></div>
</div>

<div id="right">
		<iframe id="knowledgeContent" src="" width="100%" height="100%" frameborder="0"></iframe>
</div>

<div class="form-actions pagination-left"  style="clear: both;" id="bottom">
	   <!-- <input id="btnSubmit" class="btn btn-primary" type="button" onclick="history.go(-1);" value="返回"/> -->
	   <a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${examId }&classId=${classId }&backUrl=${backUrl}"  class="btn tbtn-primary">返回</a>
</div>


<script type="text/javascript">
	(function(){
		var setting = { data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},view:{showIcon:false}},
			callback:{ onClick:function(event, treeId, treeNode){
					var id = treeNode.id;
					var examId="${examId}";
					var classId="${classId}";
					$('#knowledgeContent').attr("src","${ctx}/statistics/staticKnowledgeByexamIdAndClassIdInfoList?knowId="+id+"&examId="+examId+"&classId="+classId);
				}
			}
		};
		function refreshTree(){
			var selectKnowId="${knowId}";
			$.getJSON("${ctx}/common/examKnowledgeListByExamId?examId=${examId}",{ r:Math.random()},function(data){
				var treeObj=$.fn.zTree.init($("#ztree"), setting, data);
				/* $("#ztree > li:first > a:first").trigger("click"); */
				selectFirst(treeObj,1,selectKnowId);
				//treeObj.expandAll(true);
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
			frameObj.height(mainRight.height()- 5-bottom.height()-40-$("#bottom").height());
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
</body>
</html>