<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知识点管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	
    <script type="text/javascript" src="${ctxStatic}/modules/questionlib/js/questionlibSelect.js"></script>
	<script type="text/javascript">
	
	 function chengeCourse(ids,names){
		 $.utils.BindSelect("courseId",[]);
		 $.utils.BindSelect("courseVesionSelect",[]);
		 $.utils.BindSelect("courseId", "/questionlib/common/getCourseBySpecialtyId?specialtyId="+ ids[0]);
	 }
	 
	 $(function(){
		  $("#courseId").off("change").on("change",function(e){
			 var selectId=e.val;
			 $.utils.BindSelect("courseVesionSelect", "/questionlib/common/getCourseVersionByCouresId?courseId="+selectId);
		 }); 
		 
	 });
	 
	//打开知识点导入窗口
	function showImportPage(){
		$("#importForm").show();
	}
	
	
	//导入知识点
	function importKnowledge(){
		var specialtyId = $("#specialtyIdId").val();
		var courseId = $("#courseSelect").val();
		var versionId = $("#vesionSelect").val();
		if(specialtyId==null||specialtyId==""||courseId==null||courseId==""||versionId==null||versionId==""){
			alert("请先确定专业、课程、版本后，再导入知识点！");
		}else{
			$.ajax({
		         url:"${ctx}/questionlib/courseKnowledge/importExcel", 
		         data:{ r:Math.random()},
		         type:"post",  
		         dataType:"text",
		         success:function(data) { 
		        	  var message = data.message;
		        	  $("#messageBox").val(message);
		         },  
		         error:function(data) {  
		              alert(data.message); 
		         }
		    }); 
		}
	}
	
	//关闭知识点导入窗口
	function closeImportPage(){
		$("#importForm").hide();
	}
	//下载导入模板
	function loadTemplate(){
		window.location.href="${ctxStatic}/template/import_Knowledge_Temp.xlsx";	//知识点导入模板示例
	}
	
	</script>
</head>
<body>
	<form id="searchForm" modelAttribute="courseKnowledge" action="${ctx}/questionlib/courseKnowledge/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    
			 <li>
			  <label>课程：</label>
			    <select class="courseSelect input-small" name="courseId" id="courseId" style="width:200px;">
			      <option value="">请选择</option>
			      <c:forEach items="${ empty courseKnowledge.specialtyId ? null: (questionlib:getCourse())}" var="course">
						<option value="${course.id}" ${courseKnowledge.courseId==course.id?"selected":""}  >${course.title} </option>
				  </c:forEach>
				  
			   	</select>
			</li>
			 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="refreshTree();" type="button" value="查询"/></li>
		 
		</ul>
	</form>
	<sys:message content="${message}"/>
	 
	
	<div id="content" class="row-fluid" >
		<div id="left" class="accordion-group" >
			<div class="accordion-heading">
		    	<a class="accordion-toggle">知识点<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
		   <div style="width: 170.5px; height: 750px;">
				<div id="ztree" class="ztree" style="width: 170.5px; height: 750px;"></div>
			</div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="${ctx}/questionlib/courseKnowledge/list?id=&parentIds=" width="800px" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		refreshTree();
		//显示所有子节点
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
				callback:{onClick:function(event, treeId, treeNode){
						var id = treeNode.id;
						var pIds = treeNode.pIds;
						var courseId =$("#courseId").val();
						$('#officeContent').attr("src","${ctx}/questionlib/courseKnowledge/list?extId="+id+"&parentIds="+pIds+"&courseId="+courseId);
					}
				}
			};
		
		function refreshTree(){
		     var courseId =$("#courseId").val();
		    if(courseVesionId){
				$.getJSON("${ctx}/questionlib/courseKnowledge/treeData?courseId="+courseId,{ r:Math.random() },function(data){
					var zTree=$.fn.zTree.init($("#ztree"), setting, data);
					//zTree.expandAll(true);
					var nodes = zTree.getNodes();  
				   if (nodes.length>0){  
					     expandLevel(zTree,nodes[0],2); 
					     
		                zTree.selectNode(nodes[0]);  
		                zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件 
		            } 
					
				});
				//$('#officeContent').attr("src","${ctx}/questionlib/courseKnowledge/list?courseVesionId="+courseVesionId);
		    }else{
		    	alertx("请选择课程版本后重试.");
		    }
		}
		
		
		/* function expandLevel(treeObj,node,level){  
            var childrenNodes = node.children;
            if(childrenNodes){
	            for(var i=0;i<childrenNodes.length;i++){  
	                treeObj.expandNode(childrenNodes[i], true, false, false);  
	                level=level-1;  
	                if(level>0)  
	                {  
	                    expandLevel(treeObj,childrenNodes[i],level);  
	                }  
	            }  
            }
        }  */
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		var topHeight=$("#searchForm").height();
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5-topHeight);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() -46-topHeight);
			var courseVesionId =$("#courseVesionSelect").val();
			$('#officeContent').attr("src","${ctx}/questionlib/courseKnowledge/list?courseId="+courseVesionId);
		}
		
		
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	 
</body>
</html>