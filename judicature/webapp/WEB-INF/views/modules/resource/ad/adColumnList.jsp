<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>广告管理</title>
<meta name="decorator" content="default"/>
<style type="text/css">
.addColor td {
	background-color: #cdcdcd;
}
</style>
<script type="text/javascript">
	var queryParam = {};
	queryParam.publicationName="";
	queryParam.busId="";
	var pageSize = 5;
	var grid, pager,sjGrid,sjPager;
	$(document).ready(
			function() {
				$.ajax({
					type : "POST", //请求方式
					url : "${ctx}/ad/getAdColumnAjax", //请求路径
					cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
					dataType : "html",
					success : function(html) {
						$("#adcolumnInofor").html(html);
						var colId = $("#adcolumnInofor table").find(
								"tr:first-child a").attr("href");
						$("#adcolumnInofor table tr:first-child").addClass(
								"addColor");
						$("#adframe").attr("src", colId ? colId : "about:blank");
					}
				});
				$("#inputForm").validate({
					submitHandler : function(form) {
						form.submit();
					},
					errorContainer : "#inputFormMessageBox",
					errorPlacement : function(error, element) {
						if (element.is(":checkbox") || element.is(":radio")) {
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					}
				});
				$("#adUpdateForm").validate({
					submitHandler : function(form) {
						form.submit();
					},
					errorContainer : "#messageBox",
					errorPlacement : function(error, element) {
						if (element.is(":checkbox") || element.is(":radio")) {
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					}
				});
			});
	function changecolor(obj) {
		$("#adcolumnInofor table tr").removeClass("addColor");
		$(obj).parent().parent().addClass("addColor");
	}
	function xiugaiadColumn() {
		var colId;
		$("#adcolumnInofor table tr").each(function() {
			if ($(this).attr("class") == "addColor") {
				colId = $(this).find("td:first-child input").attr("value");
			}
		})
		if (colId) {
			location = "${ctx}/ad/form?id=" + colId;
		} else {
			alert("请选择广告位！");
		}

	}
	function shanchadColumn() {
		var colId;
		$("#adcolumnInofor table tr").each(function() {
			if ($(this).attr("class") == "addColor") {
				colId = $(this).find("td:first-child input").attr("value");
			}
		})
		if (colId) {
			if (confirm("确定要删除该广告位吗？")) {
				$.ajax({
					type : "POST", //请求方式
					url : "${ctx}/ad/deleteADColumn", //请求路径
					cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
					data : "id=" + colId,
					dataType : "json",
					success : function(data) {
						if (data) {
							alert("删除成功");
						} else {
							alert("删除失败，请检查该广告位下是否存在广告！");
						}
						location = "${ctx}/ad/adColumnlist";
					}
				});

			}
		} else {
			alert("请选择广告位！");
		}
	}
	function refreshAdList() {
		$('#addadModel').modal("hide");
		$('#adUpdateModel').modal("hide");
		$("#shelfColumndiv").hide();
		var iframe = document.getElementById("adframe");
		iframe.contentWindow.location.reload();
	}
	//以下为添加广告
		function addAD() {
		var colId;
		$("#adcolumnInofor table tr").each(function() {
			if ($(this).attr("class") == "addColor") {
				colId = $(this).find("td:first-child input").attr("value");
			}
		});
		if (colId) {
			
			$("#targetInfor .error").remove();
			$("#iconImg .error").remove();
			$("#updatetargetInfor .error").remove();
			
			$("#ebookId").val("");
			$("#title").val("");
			$("#ebookName").val("");
			$("#adurl").val("");
			$("#uploadFile").val("");
			$("#parentColumnId").val("");
			
			$("#colId").val(colId);
			$('#addadModel').modal({
				show : true,
				backdrop : 'static'
			});
		} else {
			alert("请选择对应广告");
		}
	}

		function qudiaoclass(){
			var filePath = document.getElementById("uploadFile").value;
			if(filePath){
				$("#iconImg .error").remove();
			}		
		}
		
		function uploadIcon() {
			var filePath = document.getElementById("uploadFile").value;
			$("#inputForm .error").remove();
			var flag;
		/* 	$("#inputForm").find("input[class='required']").each(function(){
				 var value=$(this).val(),
				     parent=$(this).parent();
				 alert(value+"=="+parent);
				if(value){
					flag = true;
				}else{
					flag=false;
					parent.append("<label class='error'><fmt:message key='required.information'></fmt:message></label>");
				}
				
				$(this).change(function(){
					$("#inputForm .error").remove();
				});
			});
				
			if (flag) { */
		/* 		if (filePath) {
					$("#iconImg .error").remove();
					var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
					if(filePath){
						if (!checkFileExt(fileExt)) {
							alert("<fmt:message key='ebook.uploadrestrict'></fmt:message>");
							return;
						}
					}
				}  */
				
				$("#inputForm").submit();
		/* 	}
			 */
		/* } */
		}
		//上移
		function moveUp(obj) {
			var current = $(obj).parent().parent();
			var prev = current.prev();
			if (current.index() > 0) {
				var str1 = current.attr("adId") + "," + current.attr("seq");
				var str2 = prev.attr("adId") + "," + prev.attr("seq");
				$.ajax({
					type : "POST", //请求方式
					url : "${ctx}/ad/updateUpad", //请求路径
					cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
					data : "str1=" + str1 + "&str2=" + str2,
					success : function(html) {
						//current.insertBefore(prev);	
						var iframe = document.getElementById("adframe");
						iframe.contentWindow.location.reload();
					}
				});
			}
		}
		//下移
		function moveDown(obj) {
			var current = $(obj).parent().parent();
			var next = current.next();
			if (next) {
				var str1 = current.attr("adId") + "," + current.attr("seq");
				var str2 = next.attr("adId") + "," + next.attr("seq");
				$.ajax({
					type : "POST", //请求方式
					url : "${ctx}/ad/updateDownad", //请求路径
					cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
					data : "str1=" + str1 + "&str2=" + str2,
					success : function(html) {
						//current.insertAfter(next);
						var iframe = document.getElementById("adframe");
						iframe.contentWindow.location.reload();
					}
				});
			}
		}
		
		function add1(){
			$("#ebookName").attr("disabled",false);
			$("#courseSelect").attr("disabled",true);
			$("#lvSelect").attr("disabled",true);
			$("#specialty").attr("disabled",true);
			//$("#ebookName").attr("path","target");
			$("#targetDiv").show(1000);
			$("#courseDiv").hide(1000);
			$("#lvDiv").hide(1000);
			$("#resDiv").hide(1000);
		}
		
		function add2(){
			$("#courseSelect").attr("disabled",false);
			$("#ebookName").attr("disabled",true);
			$("#lvSelect").attr("disabled",true);
			$("#specialty").attr("disabled",true);
			//$("#courseSelect").attr("path","target");
			$("#courseDiv").show(1000);
			$("#lvDiv").hide(1000);
			$("#targetDiv").hide(1000);
			$("#resDiv").hide(1000);
		}
		
		function add3(){
			$("#lvSelect").attr("disabled",false);
			$("#ebookName").attr("disabled",true);
			$("#courseSelect").attr("disabled",true);
			$("#specialty").attr("disabled",true);
			//$("#lvSelect").attr("path","target");
			$("#lvDiv").show(1000);
			$("#courseDiv").hide(1000);
			$("#targetDiv").hide(1000);
			$("#resDiv").hide(1000);
		}
		function add4(){
			$("#specialty").attr("disabled",false);
			$("#ebookName").attr("disabled",true);
			$("#courseSelect").attr("disabled",true);
			$("#lvSelect").attr("disabled",true);
			//$("#lvSelect").attr("path","target");
			$("#resDiv").show(1000);
			$("#lvDiv").hide(1000);
			$("#courseDiv").hide(1000);
			$("#targetDiv").hide(1000);
		}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ad/adColumnlist">广告栏目列表</a></li>
		<li><a href="${ctx}/ad/form">广告栏目添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adColumn" action="${ctx}/ad/adColumnlist" method="post" 	class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<div>
			<input id="xiugai" class="btn btn-primary" type="button" onclick="xiugaiadColumn()" value="编辑" />
		     &nbsp;&nbsp;
		    <input id="shanchu" class="btn btn-primary" type="button" value="删除" 	onclick="shanchadColumn()" /> 
		    &nbsp;&nbsp; 
		    <a data-toggle="modal" 	href="#" id="addADInfor" onclick="addAD()" 	class="btn btn-primary">添加资源</a>
		</div>
	</form:form>
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- 广告的位置 -->
			<div id="adcolumnInofor" class="span5" style="height: 1000px;" ></div>
			<!-- 添加的 广告-->
			<div class="span7" style="margin-left: 1%;" >
				<iframe name="adframe" frameborder="0" width="100%" height="420px"
					id="adframe"> </iframe>
			</div>
		</div>
	</div>
		<div class="modal hide fade in" id="addadModel"  style="overflow: hidden; top:30%;width:40%;">
		 <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h2>添加广告</h2>
		</div>
		<form:form id="inputForm" modelAttribute="ad" target="hiddenFrame" cssStyle="margin: 0;" action="${ctx}/ad/addAD" method="post" class="form-horizontal" enctype="multipart/form-data">
			<div id="inputFormMessageBox" class="alert alert-error" style="display: none">添加广告</div>
			<form:hidden path="colId" />
			<div class="modal-body">
			
			<div class="control-group">
					<label class="control-label">资源类型<span style="color: red">(*)</span>:
					</label>
					<div class="controls">
						 	<%-- <form:radiobuttons path="adType" items="${fns:getDictList('ad_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/> --%>
						 	<form:radiobutton id="adType1" path="adType" value="1" onclick="add1()"/>外部URl
							<form:radiobutton id="adType2" path="adType" value="2" onclick="add2()"/>课程
							<form:radiobutton id="adType3" path="adType" value="3" onclick="add3()"/>直播
							<form:radiobutton id="adType3" path="adType" value="4" onclick="add4()"/>资源
					</div>
				</div>
				
				<div class="control-group" id="courseDiv">
					<label class="control-label">课程<span style="color: red">(*)</span>:
					</label>
					<div class="controls">
						 <form:select path="target" items="${course}" itemLabel="title" itemValue="id" id="courseSelect"></form:select>
					</div>
				</div>
				
				<div class="control-group" id="lvDiv">
					<label class="control-label">直播<span style="color: red">(*)</span>:
					</label>
					<div class="controls">
						 <form:select path="target" items="${LVList}" itemLabel="course_name" itemValue="course_id" id="lvSelect"></form:select>
					</div>
				</div>
				
				<div class="control-group" id="resDiv">
					<label class="control-label">资源<span style="color: red">(*)</span>:
					</label>
					<div class="controls">
						<sys:treeselect id="specialty" name="target" value="" labelName="course.title" labelValue="" 
				title="专业" url="/category/treeData" cssClass="input-small" allowClear="false" notAllowSelectParent="true" />
					</div>
				</div>
				
				
				
				<div class="control-group" id="targetDiv">
					<label class="control-label">目标对象<span style="color: red">(*)</span>:
					</label>
					<div class="controls">
						 <!-- <input id="ebookName" name="target" maxlength="100" type="text"  class="required" style="width: 400px;"/> (若为内部资源，填写资源ID号) -->
						 <form:input id="ebookName" path="target" maxlength="100"  class="required" style="width: 400px;"/> (若为内部资源，填写资源ID号)
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">图标<span style="color: red">(*)</span>:
					</label>
					<div class="controls" id="iconImg">
						<input type="file" name="uploadFile" id="uploadFile"/>
						<span class="help-inline">(1536×400)</span>
					</div>
				</div>
			</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
			<input type="button" class="btn btn-primary" onclick="uploadIcon()" value="提交"/>
		</div>
	</form:form>
	</div>
	
	<iframe name="hiddenFrame" style="display: none;" id="hiddenFrame"></iframe>
</body>
</html>