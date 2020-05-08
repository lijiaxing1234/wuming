<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>图片添加</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
</script>

</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/admin/lv/LVupList">直播列表</a></li>
	</ul>
	<div class="basic-grey">
	<form id="addform" action="${ctx}/admin/lv/addHomeImg" method="post" enctype="multipart/form-data" class="form-horizontal">
		<input type="hidden" value="${lvId}" name="lvId">
		<div class="control-group">
		<label>
		<span>图片 :</span>(210*210 像素)
		<input id="file" type="file" name="uploadFile" />
		</label>
		</div>
		
		<div class="control-group"> 
		<label>
		<span>&nbsp;</span>
		<input type="submit" class="btn btn-primary" value="提交"/>
		</label>
		</div>
	</form>
	</div>
</body>
</html>