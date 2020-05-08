<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<%-- <div style="width: 100%;min-height: 40px;position:relative;align:right;text-align: right;background: #fff;">
	<form id="searchForm" modelAttribute="specialty" action="${ctx}/questionlib/specialty/importExcel" method="post" enctype="multipart/form-data" class="breadcrumb form-search" style="align:right;">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" style="align:right;">
			<!-- <li>上传excel文件：<input name="excelFile" type="file" class="input-medium"  style="width:210px;"/></li>
			<li class="btns"><input id="btnImportFile" class="btn btn-primary" type="submit" value="专业/课程/版本/题库导入"/></li>
			<li style="align:right;"><input id="btnImport" style="align:right;" class="btn btn-primary" type="button" value="专业/课程/版本/题库导入" onclick="showImportPage();"/></li>
			<li><font face="楷体" >（可在此导入专业/课程/版本/题库等数据）</font></li> -->
			<li class="clearfix"></li>
		</ul>
	</form>
	</div> --%>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group" style="height: 400px">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">专业<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",pIdsKey:"pIds",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.id;
					var pIds = treeNode.pIds;
					$('#officeContent').attr("src","${ctx}/questionlib/specialty/list?id="+id+"&parentIds=");
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/questionlib/specialty/treeData",{ r:Math.random() },function(data){
				var tree =$.fn.zTree.init($("#ztree"), setting, data);//.expandAll(true)
				selectFirst(tree,0);
			});
		}
		refreshTree();
		 
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
		}
	
		//打开导入窗口
		function showImportPage(){
			$("#importForm").show();
		}
		//关闭导入窗口
		function closeImportPage(){
			$("#importForm").hide();
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	<!-- 导入界面 -->
	<div id="importForm" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<div style="width: 30%;min-height: 10%;position: absolute;top: 30%;left: 30%;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
		<form id="saveImportDocMessage" action="${ctx}/questionlib/specialty/importExcel" method="post" enctype="multipart/form-data">
			<table style="margin: 0 auto;" id="importTable">
				<tr>
					<th colspan="2" class="control-label"><h3>专业/课程/版本/题库导入</h3></th>
				</tr>
				<tr>
					<td colspan="2" style="height: 10px;"> </td>
				</tr>
				<tr>
					<th class="control-label">上传excel文件：</th>
					<td style="height: 40px;"><input name="excelFile" type="file" class="input-medium"  style="width:210px;"/></td>
				</tr>
				<tr>
				<td></td>
					<td style="height: 40px;align:center;"><input id="saveImportMessage" class="btn btn-primary" type="submit" value="开始导入"/>
					<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="closeImportPage();"/></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>