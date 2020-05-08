<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>自定义新闻管理</title>
<meta name="decorator" content="default"/>
<link href="${ctxStatic}/styles/uploadBut.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/styles/transactioncommon.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/styles/showappbox.css" type="text/css" rel="stylesheet" />
<%@include file="/WEB-INF/views/include/js_paging.jsp"%>
<style type="text/css">
body { margin-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; }
.box {width: 1024px; border-bottom: 1px solid #ddd; }
.shang { height: 40px; line-height: 40px; font-weight: bold; font-size: 14px; }
.stu { padding: 15px 0px; overflow: hidden; }
.stua { width: 91px; text-align: center; }
.tubiao { width: 157px; text-align: center; }
.schuan { position: relative; height: 40px; }
.blue { border: 1px solid #007ead; -moz-border-radius: 5px; -webkit-border-radius: 5px; 
      border-radius: 5px; z-index: 2px;
      background: #0081cc; color: #FFF; padding: 5px 15px; margin-top: 5px;
}
.blue:hover {
	background: #007ead;
	background: -webkit-gradient(linear, left top, left bottom, from(#0095cc),
		to(#00678e) );
	background: -moz-linear-gradient(top, #0095cc, #00678e);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#0095cc',
		endColorstr='#00678e' );
}
#preview, .img{   width:200px;   height:200px;   }  
#preview  { border:1px solid #000; }  

</style>
<script type="text/javascript" src="${ctxStatic}/ckeditor/ckeditor.js"></script> <!--载入ckeditor类-->
<script type="text/javascript">

     $(document).ready(function() {
          $("#inputForm").validate({
	    	    submitHandler: function(form){
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
          
      });
	

	function subnewcontent() {
		$("#contentForm").submit();
	}

	//修改校验
	function uploadphotos1() {
		var filePath = document.getElementById("uploadshotFile_update").value;
		var fileExt = filePath.substring(filePath.lastIndexOf("."))
				.toLowerCase();
		if (!checkFileExt(fileExt)) {
			alert("文件类型不对，请上传正确的文件！");
			return false;
		} else {
			return true;
		}
	}
	
	//校验
	function uploadphotos() {
		var filePath = document.getElementById("uploadFile").value;
		var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
		if (!checkFileExt(fileExt)) {
			alert("文件类型不对，请上传正确的文件！");
			return false;
		} else {
			return true;
		}
	}
	//校验
	function checkFileExt(ext) {
		var patenName = /^.jpg|.bmp|.gif|.png|.ico|.jpeg|.tiff|.raw$/;
		if (!ext.match(patenName)) {
			return false;
		}
		return true;
	}


	
	
	
	function getTargetInfor() {
		var value = $("#type").val();
		if (value == 2) {
			$("#word").hide().find("#contentId").val("");
			$("#imgId").show().find("#uploadFile").val("");
			
			cleanContentId();
			
		} else {
			$("#imgId").hide().find("#uploadFile").val("");
			$("#word").show().find("#contentId").val("");
			
		    cleanContentId();
		}
	}
	
	
	function cleanContentId(){
		  //查找有无对应的编辑器
	    if(CKEDITOR.instances.contentId) {
	    	CKEDITOR.instances["contentId"].destroy(true);
	    }
	    CKEDITOR.replace("contentId", { toolbar:"MyBasic" });
	    
	}
	
	
	function addColumnImp() {
	  
	    cleanContentId();
		
		$("#newsType").val(2);
		
		$("#word").hide().find("#contentId").val("");
		$("#imgId").show().find("#uploadFile").val("");
		
	    
		$('#addColumnImpModel').modal({
			show : true,
			backdrop : 'static'
		});
		
	}
	function updatecontentImpmentInfor(newsimpId,implType) {
		$.ajax({
			type : "POST", //请求方式
			url : "${ctx}/admin/lv/getImplement", //请求路径
			cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
			data : "id=" + newsimpId,
			dataType : "html",
			success : function(html) {
				
				$("#updateImpleInfor").html(html);
				$("#updateColumnModel").modal({
					show : true,
					backdrop : 'static'
				});	
				
				if(implType ==='1'){
					//判定在ckeditor中编辑器contentIdupdate是否存在
					if(CKEDITOR.instances.contentIdupdate){  
						//销毁编辑器contentIdupdate
						CKEDITOR.instances["contentIdupdate"].destroy(true)
	                 } 				
					//ckeditor使用自定义的工具栏
					 CKEDITOR.replace('contentIdupdate',{toolbar : 'MyBasic'}); 
				}
			}
		});
	}
	
	
	function deletecontentimple(id){
		if(confirm("是否将此内容删除?")){
			$.ajax({
				type : "POST", //请求方式
				url : "${ctx}/admin/lv/deletImple", //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				data : "id=" + id ,
				success : function(html) {
					var iframe = document.getElementById("implementIframe");
					iframe.contentWindow.location.reload();
				}
			});
			}else return false;
	}
	
	
	
	function refreshColumneImpleList() {
		$('#addColumnImpModel').modal("hide");
		$('#updateColumnModel').modal("hide");
		var iframe = document.getElementById("implementIframe");
		iframe.contentWindow.location.reload();
	}
	
	
	
	function uploadnewsImple() {
		
		$("#updatetargetInfor .error").remove();
		
		var type = $("#typeId").val();
		//var targetinfor = $("#uploadshotFile_update").val();
		var wordinfro = $("#contentIdupdate").val();
		var flag;
		if(type == 2){
			/* if (targetinfor) {
			  flag = true;
			} else {
				flag = false;
			} */
			flag=true;
		}else{
			if (wordinfro) {
				flag = true;
			} else {
				flag = false;
			}
		}
		if (flag) {
			$("#updatetargetInfor .error").remove();
			$("#contentupdateForm").submit();
			$("#updateColumnModel").modal("hide");
			$('#addColumnImpModel').modal("hide");
		} else {
			$("#updatetargetInfor").append("<label class='error'>必填信息</label>");
		}
	}
	

	
	function goback(){
		var colCate='${columnKey.colCate}';
		if(colCate==='1'){
		   parent.changeColor(0);
		}else if(colCate==='2'){
			  parent.changeColor(2);
		}
     
		//location = "${ctx}/admin/lv/getpreViewByID?colId=${columnKey.colId}" ;
	}
	
	function moveUp(obj) {
		var current = $(obj).parent().parent();
		var prev = current.prev();
		if (current.index() > 0) {
			var str1 = current.attr("charoff") + "," + current.attr("lang");
			var str2 = prev.attr("charoff") + "," + prev.attr("lang");
			$.ajax({
				type : "POST", //请求方式
				url : "${ctx}/admin/lv/downImpleSeq", //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				data : "str1=" + str1 + "&str2=" + str2,
				success : function(html) {
					//current.insertBefore(prev);	
					var iframe = document.getElementById("implementIframe");
					iframe.contentWindow.location.reload();
				}
			});
		}
	}
	function moveDown(obj) {
		var current = $(obj).parent().parent();
		var next = current.next();
		if (next) {
			var str1 = current.attr("charoff") + "," + current.attr("lang");
			var str2 = next.attr("charoff") + "," + next.attr("lang");
			$.ajax({
				type : "POST", //请求方式
				url : "${ctx}/admin/lv/upImpleSeq", //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				data : "str1=" + str1 + "&str2=" + str2,
				success : function(html) {
					//current.insertAfter(next);
					var iframe = document.getElementById("implementIframe");
					iframe.contentWindow.location.reload();
				}
			});
		}
	}
	
	function popup(){
		var abc = "${ctx}/admin/lv/getpreViewByID?colId=${columnKey.colId}&colCate=${columnKey.colCate}";
		//window.open(abc, "", "scrollbars=yes,location=no,toolbar=no,height=600,width=750");
		window.open(abc, "", "scrollbars=yes,location=no,toolbar=no,height=600,width=1050");
		return false;
	}

	
	//预览上传的图片
	function preview(file, value) {
		if(file.files && file.files[0]) {
			var reader = new FileReader();
			reader.readAsDataURL(file.files[0]);
			reader.onload = function() {
				$(document.getElementById(value)).attr("src", reader.result);
			}
		} else {
			$(document.getElementById(value)).css({filter: 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\')'});
			$(document.getElementById(value)).attr("src","");
		}
	}
</script>
</head>
<body>
<tags:message content="${message}" />
	<ul class="nav nav-tabs">
	<%-- 	<li ><a href="${ctx}/admin/newsSelf/list">列表</a></li>
		<li class="active"><a href="#">添加</a></li> --%>
	</ul>
	
	<form:form id="inputForm" modelAttribute="lvCourse" action="${ctx}/admin/lv/courseFormSave?colId=${columnKey.colId}&colCate=${columnKey.colCate}&type=2" method="post" enctype="multipart/form-data" class="form-horizontal" >
		<div id="inputFormMessageBox" class="alert alert-error" style="display: none"><button data-dismiss="alert" class="close">×</button>输入有误，请先更正。</div>
		<input id="btnSubmit_save" style="margin-left: 150px" class="btn btn-primary" type="submit"  value="保存"  />
	   
		<input id="btnSubmit" style="margin-left: 120px" class="btn btn-primary" type="button" value="添加内容" onclick="addColumnImp()" />
		<a href="" title="预览" style="margin-left: 120px" class="btn btn-primary"  onclick="return popup()" onkeypress="return popup()">预览</a>
		<input id="btnSubmit" style="margin-left: 120px" class="btn btn-primary" type="button" value="返回" onclick="goback()" />
	
	</form:form>
	
	<div class="shang">内容:</div>
	 <br/>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span7">
				<iframe name="implementIframe" frameborder="0" width="150%" height="1420px" id="implementIframe" src="${ctx}/admin/lv/courseImplementList?colId=${columnKey.colId}&colCate=${columnKey.colCate}" > </iframe>
			</div>
		</div>
	</div>
	 <div class="modal hide fade in" id="addColumnImpModel"  style="overflow: hidden; top: 35%;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h2>添加内容</h2>
		</div>
		<form:form id="contentForm"  modelAttribute="lvCourseImplement"  target="hiddenFrame"  cssStyle="margin: 0;" action="${ctx}/admin/lv/courseImplementFormSave?lvCourseId=${columnKey.colId}&lvCourseCate=${columnKey.colCate}" method="post"  class="form-horizontal" enctype="multipart/form-data">
			<div id="messageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label">内容类型<span style="color: red">(*)</span>:
					</label>
					<div class="controls">
						<form:select path="type" onchange="getTargetInfor()" class="required">
							<form:option value="2" label="图片" />
							<form:option value="1" label="文字" />
						</form:select>
					</div>
				</div>
				<div class="control-group" id="iconImg">
					<label class="control-label">内容<span style="color: red">(*)</span>:
					</label>
					<div class="controls" style="display: none" id="imgId">
						<input type="file" name="uploadshotFile" class="required"  id="uploadFile"  onchange="uploadphotos()"/>
					</div>
					<div class="controls" style="display: none" id= "word">
						<textarea name="content" id="contentId" cols="50" rows="6"  class="required"></textarea>
					</div>
				</div>
			</div>
		</form:form>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
			<button type="button" class="btn btn-primary" onclick="subnewcontent()">提交</button>
		</div>
	</div>
	
	<div class="modal hide fade in" id="updateColumnModel" style="overflow: hidden; top: 35%;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h2>修改内容</h2>
		</div>
		<div class="modal-body" id="updateImpleInfor"></div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
			<button type="button" class="btn btn-primary" onclick="uploadnewsImple()">提交</button>
		</div>
	</div>
	
	<iframe name="hiddenFrame" style="display: none;" id="hiddenFrame"></iframe>
</body>
</html>