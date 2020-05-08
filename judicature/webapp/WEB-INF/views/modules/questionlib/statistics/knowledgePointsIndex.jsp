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
			    <select class="courseSelect" name="courseId" id="courseId" style="width:250px;" >
			      <option value="">请选择</option>
				   <c:forEach items="${ empty course.specialty.id ? null: (questionlib:getCourseBySpecialtyId(course.specialty.id))}" var="course">
						<option value="${course.id}" ${courseId==course.id?"selected":""}  >${course.title} </option>
				   </c:forEach>
			   	</select>
			</li>
			
			 <li  >
			  <label>版本：</label>
		          
			    <select name="vesionId" id="courseVesionSelect" class="courseVesionSelect input-small" style="width:200px"> <!-- class="input-medium" -->
			    	<option value="">请选择</option>
			    	 <c:forEach items="${empty courseId ? null:(questionlib:getCourseVersionByCourseId(courseId))}" var="courseVesion">
				   		<option value="${courseVesion.id}" ${versionId==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
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
		    if(courseVesionId){
				$.getJSON("${ctx}/questionlib/courseKnowledge/treeData?courseVesionId="+courseVesionId,{ r:Math.random() },function(data){
					var zTree=$.fn.zTree.init($("#ztree"), setting, data);
					var nodes = zTree.getNodes();  
		            if (nodes.length>0){  
		                expandLevel(zTree,nodes[0],2); 
		                zTree.selectNode(nodes[0]);  
		                zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件 
		                
		           
		            }  
		            //zTree.expandAll(true);
		            
			
				});
				$('#officeContent').attr("src","${ctx}/questionlib/statistics/knowledgePointsList?versionId="+courseVesionId);
		    }else{
		    	alertx("请选择版本后重试.");
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
			$(".ztree").width(leftWidth - 10).height(frameObj.height() -46- topHeight);
			var courseVesionId =$("#courseVesionSelect").val();
			$('#officeContent').attr("src","${ctx}/questionlib/statistics/knowledgePointsList?courseVesionId="+courseVesionId);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>