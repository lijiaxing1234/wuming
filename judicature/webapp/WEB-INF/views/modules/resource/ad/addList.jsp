<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title><fmt:message key="ad.manage"></fmt:message></title>
<meta name="decorator" content="default"/>
<style type="text/css">
	#contentTable{-moz-user-select:none;}
</style>
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function updateADInfor(adId, event) {
		$.Event(event).preventDefault();
		parent.updateADInfor(adId);
	}
	
	function deleteAD(id){		
		if(confirm("确定要删除该广告吗？")){
			$.ajax({
				type : "POST", //请求方式
				url : "${ctx}/ad/delete", //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				data : "adId=" + id,				
				success : function() {
					alert("删除成功！");
					parent.refreshAdList();
				},
				error:function(xhr,status,error){
					parent.refreshAdList();
				}
			});
		}
	}
</script>
</head>
<body>		
	<table id="contentTable" class="table table-striped table-bordered ">
		<thead>
			<tr>							
				<th>封面</th>
				<th>广告对象</th>
				<th>类型</th>
				<th>操作</th>
				<th>排序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ad" varStatus="s">
				<tr adId="${ad.id}" seq="${ad.seq}">					
					<td>
					      <img src="${imageServer }${ad.imageUrl}" height="40" style="max-width: 120px;"/>
					 </td>
					<td max-width="200px">
					     <c:choose>
							<c:when test="${fn:length(ad.target) > 30}">
								<c:out value="${fn:substring(ad.target, 0, 30)}..." />
							</c:when>
							<c:otherwise>
								 ${ad.target}
							</c:otherwise>
						</c:choose>					
					</td>
					
					<td>
					     <%-- ${fns:getDictLabel(ad.adType, 'ad_type', '')} --%>
					<c:if test="${ad.adType==1}">外部URL</c:if>
					<c:if test="${ad.adType==2}">课程</c:if>
					<c:if test="${ad.adType==3}">直播</c:if>
					<c:if test="${ad.adType==4}">资源</c:if>
					 </td>
					<td>
						<a href="javascript:void(0);" onclick="deleteAD('${ad.id}')">删除</a>
					</td>
					<td style="text-align: center; vertical-align: middle;">
						<c:if test="${s.index > 0 }">
						<a href="javascript:void(0)" onClick="parent.moveUp(this)">上移</a>
						</c:if>
						<c:if test="${s.index + 1 < fn:length(page.list) }">
						<a href="javascript:void(0)" onClick="parent.moveDown(this)">下移</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<table id="moveable" class="table table-striped table-bordered hide" style="position:absolute;">
		<tbody><tr></tr></tbody>
	</table>
</body>
</html>