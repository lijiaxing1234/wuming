<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	
	
	
	var message ="${message}";
	 
    if(!!message)
	{
    	 alertx("${message}",function(){
    		   
 		    try{
 		    	top.$.jBox.closeTip();
 		    	top.$.jBox.close(true);
 		    }catch(e){
 		    	
 		    }
    		 
    	 });
    	// alert("试题导入成功 ！");
	   // window.parent.window.jBox.close();
	  
	} 
   
	
	
	
		$(document).ready(function() {
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					
					if($("#excelFile").val()=="")
					{
					   alertx("请选择excel文件！");
					    return ;
					}
					
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
 <style type="text/css">
 	.form-horizontal{ margin-top: 40px;}
 </style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="specialty" action="${ctx}/questionlib/specialty/importExcel" method="post" enctype="multipart/form-data" class="form-horizontal">
		<div class="control-group">
		
		     <p style="text-align: center;"> <font style="color: red;">请下载模板，根据模板填写内容，然后上传</font> </p>
			<label class="control-label">上传excel文件：</label>
			<div class="controls">
				<input id="excelFile" name="excelFile" type="file" class="input-medium"   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"  style="width:210px;"/> 
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="开始导入"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="top.$.jBox.close(true);"/>
		</div>
	</form:form>
</body>
</html>