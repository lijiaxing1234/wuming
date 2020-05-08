<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知识点统计</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	<script type="text/javascript">
	  $(document).ready(function(){
	      $("#specialtyId").change(function(){
	       $("#specialtyId option").each(function(i,o){
	          if($(this).attr("selected"))
	      {
	           $(".courseSelect").hide();
	           $(".courseSelect").eq(i*2).show();
	           $(".courseSelect").eq(i*2+1).show();
	           $(".courseSelect").eq(i*2+1).change();
	       }
	         });
	       });
	          $("#specialtyId").change();
	     });
	  
	  function chengeCourse() {

			$("#courseId").empty();
		     //调用Ajax获取
			  $.getJSON("${ctx}/questionlib/common/getCourseJSONBySpecialtyId?specialtyId="+$("#specialtyId").val(),{ r:Math.random() },function(data){
					for (var i=0; i<data.length; i++){
						if (i==0) {
							$("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>"); 
						} else {
							$("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>"); 
						}
						
					}
			  });
		}
	  
	  function loadVersion() {
		  
	     //调用Ajax获取版本信息
	        $("#courseVesionSelect").empty();
	     
		  $.getJSON("${ctx}/questionlib/courseKnowledge/getCourseQuestionByCouresId?id="+$("#courseId").val(),{ r:Math.random() },function(data){
				 for (var i=0; i<data.length; i++){
					  if(i==0){
					 	 $("#courseVesionSelect").append("<option value=\""+data[i].id+"\" \"selected\">"+data[i].value+"</option>"); 
					  }else{
						  $("#courseVesionSelect").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
					  }
					 
					}
			});
		  
		 
	} 
	 
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	
	<form id="searchForm" modelAttribute="courseVesion" action="${ctx}/questionlib/statistics/knowledgePointsIndex" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 
			 <li><label>专业：</label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${course.specialty.id}" labelName="specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
			 </li>
			
			 <li>
			  <label>课程：</label>
			    <select class="courseSelect" name="courseId" id="courseId" style="width:150px;" onchange="loadVersion(this);">
			   	</select>
			</li>
			
			 <li  >
			  <label>版本：</label>
		       <select name="vesionId" id="courseVesionSelect" class="courseVesionSelect" style="width:150px"> <!-- class="input-medium" -->
		 	     <c:forEach items="${courseVesionList}" var="courseVesion">
				   	<option value="${courseVesion.id}" ${courseVesionId==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
				 </c:forEach>
			  </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="refreshTree();" type="button" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	
	
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">知识点<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="${ctx}/questionlib/statistics/knowledgePointsList?id=&parentIds=" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		refreshTree();
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						var pIds = treeNode.pIds;
						var courseVesionId =$("#courseVesionSelect").val();
						$('#officeContent').attr("src","${ctx}/questionlib/statistics/knowledgePointsList?id="+id+"&parentIds="+id+"&versionId="+courseVesionId);
					}
				}
			};
		
		function refreshTree(){
		     var courseVesionId =$("#courseVesionSelect").val();
		     
			$.getJSON("${ctx}/questionlib/courseKnowledge/treeData?courseVesionId="+courseVesionId,{ r:Math.random() },function(data){
				var treeObj=$.fn.zTree.init($("#ztree"), setting, data);
				selectFirst(treeObj,1);
				//treeObj.expandAll(true);
			});
			//$('#officeContent').attr("src","${ctx}/questionlib/statistics/knowledgePointsList?versionId="+courseVesionId);
		}
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
			//var courseVesionId =$("#courseVesionSelect").val();
			//$('#officeContent').attr("src","${ctx}/questionlib/statistics/knowledgePointsList?courseVesionId="+courseVesionId);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>