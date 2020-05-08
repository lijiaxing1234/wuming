<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>找回密码</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
	 
	
	top.$.jBox.closeTip();
	 var okFlag ="${okFlag}";
	  
	 var message ="${message}";
	 
	    if("false"==okFlag)
		{
	    	 alertx(message,function(){
	    		   
	 		    try{
	 		    	top.$.jBox.closeTip();
	 		    	top.$.jBox.close(true);
	 		    }catch(e){
	 		    	
	 		    }
	    		 
	    	 });
	    	 
		    
		} 
	    
	   
		$(document).ready(function() {
			 
			$("#savePassword").validate({
				submitHandler: function(form){
				 var pass1=$("#password").val();
				 var pass2 =$("#pass2").val();
				 var code =$("#code").val();
				 if(code=="")
					{
					   alertx("请选输入验证码！");
					    return ;
					}
				 
					if(pass1=="")
					{
					   alertx("请选输入新密码！");
					    return ;
					}
					if(pass1!=pass2)
					{
					   alertx("两次密码不一致，请重新输入！");
					    return ;
					}
					
				 
					loading('正在上传文件，请稍等...');
					form.submit();
					$("#importTable").hide();
					
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else if(element.parent().is("span")){
						
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	
	       <sys:message content="${message}"/>  
		   <form id="savePassword" action="/pass/save" method="post" enctype="multipart/form-data">
			<input type="hidden" id="loginName"  name ="loginName" value="${loginName }">
			<input type="hidden" id="type"  name ="type" value="${type }">
			<table style="margin: 0 auto;" id="importTable" >
				<tr>
					<th colspan="2" class="control-label"><h3>找回密码</h3></th>
				</tr>
				<tr>
					<td colspan="2" style="height: 10px;"> </td>
				</tr>
				
				<tr>
					 
					<th class="control-label">登录名：</th>
					<td>${loginName}</td>
				</tr>
				 
				<tr>
					<th class="control-label">验证码：</th>
				  
						<td><input id ="code" name="code" type="password" value="" class="input-medium required"  style="width:210px;"/><font style="color: red">*</font></td>
					  
				</tr>
				
				 <tr>
					<th class="control-label">新密码：</th>
				 
						<td><input id ="password" name="password" type="password" value="" class="input-medium required"  style="width:210px;"/><font style="color: red">*</font></td>
						 
					 
					<th></th>
					<td></td>
				</tr>
				 <tr>
					<th class="control-label">再次输入密码：</th>
						<td><input id ="pass2" name="pass2" type="password" value="" class="input-medium required"  style="width:210px;"/><font style="color: red">*</font></td>
				</tr>
			 
				<tr>
				<td></td>
					<td style="height: 40px;align:center;"><input id="saveImportMessage" class="btn btn-primary" type="submit" value="提交"/>
					
					<input id="closeImportQuestion" class="btn btn-primary" type="button" value="关闭" onclick="window.parent.window.jBox.close();"/></td>
				</tr>
			</table>
		</form>
</body>
</html>