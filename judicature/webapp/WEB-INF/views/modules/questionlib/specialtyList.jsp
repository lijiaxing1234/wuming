<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
		
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
			
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		//导入专业界面
		(function(){
		    $(function(){
		    	$("#btnImport").on("click",function(){
		    		openJbox("iframe:${ctx}/questionlib/specialty/importForm","专业/课程/版本/题库导入");
		    		return false;
		    	});
		    	
		    	function openJbox(src,title){
		    		 top.$.jBox(src,{
							title:title,
							width: 450,
						    height: 255,
							buttons:{}, 
						    bottomText:"",
						    loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							},
						    closed:function(){
								//$("#searchForm").submit();
								window.location.href="${ctx}/questionlib/specialty/list";
							}
				     }); 
		    	}
		        	
		    });
		})();
		//打开导入窗口
		function showImportPage(){
			$("#importForm").show();
		}
		//关闭导入窗口
		function closeImportPage(){
			$("#importForm").hide();
		}
		
		//下载导入模板
		function loadTemplate(){
			window.location.href="${ctxStatic}/template/import_Specialty_Course_Version_Questionlib_Temp.xlsx";//导入专业课程版本题库模板
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/specialty/list?extId=${extId}">专业列表</a></li>
		<shiro:hasPermission name="questionlib:specialty:edit">
		  <li><a href="${ctx}/questionlib/specialty/form?extId=${extId}">专业添加</a></li>
		</shiro:hasPermission>
		
	</ul>
	
	<div class="breadcrumb form-search">
	   <shiro:hasPermission name="questionlib:specialty:download">
		<li><input id="btnImport" class="btn btn-primary"   type="button" value="专业/课程/版本/题库导入"/></li>
		<!-- <li><input id="btnImport" class="btn btn-primary"  type="button" value="专业/课程/版本/题库导入" onclick="showImportPage();"/></li> -->
		<li><input class="btn btn-primary" type="button" value="下载导入模板" onclick="loadTemplate();"/></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="questionlib:specialty:export">
		<li><input id="btnExport" class="btn btn-primary"  type="button" value="专业导出"
		   onclick="location.href='${ctx}/questionlib/specialty/export?id=${specialtyId}'"/></li>
		</shiro:hasPermission>
	</div>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><shiro:hasPermission name="questionlib:specialty:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/questionlib/specialty/form?id={{row.id}}&extId=${extId}">{{row.title}}</a></td>
			<shiro:hasPermission name="questionlib:specialty:edit"><td>
				<a href="${ctx}/questionlib/specialty/form?id={{row.id}}&extId=${extId}">修改</a>
				<shiro:hasPermission name="questionlib:specialty:delete">
					<a href="${ctx}/questionlib/specialty/delete?id={{row.id}}&extId=${extId}" onclick="return confirmx('要删除该专业吗？', this.href)">删除</a>
				</shiro:hasPermission>
				<a href="${ctx}/questionlib/specialty/form?parent.id={{row.id}}&extId=${extId}">添加子专业</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
	
	<!-- 导入界面 -->
	<div id="importForm" style="width: 100%;height: 100%;position: absolute; top: 0;display: none;">
		<div style="width: 30%;min-height: 10%;position: absolute;top: 30%;left: 25%;padding: 15px 4px 4px;border: 1px solid #ccc;text-align: center;background: #fff;">
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
					<td style="height: 40px;"><input name="excelFile" type="file" class="input-medium"   accept="xls,xlsx" style="width:210px;"/></td>
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