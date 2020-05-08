<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!-- 判断页码的起始结束位置 -->
	<c:choose>
		<c:when test="${page.pageNo < 4}">
			<c:set var="begin" value="1" />
			<c:set var="end" value="5" />
			<c:if test="${end > page.totalPage}">
				<c:set var="end" value="${page.totalPage}" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="begin" value="${page.pageNo - 2 }" />
			<c:set var="end" value="${page.pageNo + 2 }" />
			<c:if test="${end > page.totalPage}">
				<c:set var="end" value="${page.totalPage}" />
				<c:set var="begin" value="${page.totalPage == 4 ?1:(page.totalPage - 4)}" />
			</c:if>
		</c:otherwise>
	</c:choose>				

	<a href="${page.path }?pageNB=1">首页</a>
	<c:if test="${page.pageNo!=1}">
		<a href="${page.path }?pageNB=${page.pageNo-1}">上一页</a>
	</c:if>
	<c:forEach begin="${begin }" end="${end}" var="i">
		<c:choose>
			<c:when test="${page.pageNo==i }">
				<a>【${i}】</a>
			</c:when>
			<c:otherwise>
				<a href="${page.path }?pageNB=${i}">${i}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:if test="${page.pageNo!=page.totalPage}">
		<a href="${page.path }?pageNB=${page.pageNo+1}">下一页</a>
	</c:if>
	<a href="${page.path }?pageNB=${page.totalPage}">末页</a>
	共${page.totalPage}页，${page.totalRecordNo}条记录 到第<input value="${page.pageNo}" name="pn" id="pn_input"/>页
	<input type="button" value="确定" id="pg_btn">
	<script type="text/javascript">
		$(function(){
			//获取按钮对象，并绑定单击响应函数
			$("#pg_btn").click(function(){
				//获取页码
				var pageNo = $("#pn_input").val();
				var reg = /^\d+$/;
				if(!reg.test(pageNo)){
					$(this).val("");
					alert("输入的页码不合法");
					return;
				}
				
				var pageNo2 = parseInt(pageNo);
				if(pageNo2 > parseInt("${page.totalPage}")||pageNo2 < 1){
					$(this).val("");
					alert("输入的页码不合法");
					return;
				}
				//根据不同页码跳转页面
				//通过修改window.location的属性，可以跳转当前页面
				window.location = "${page.path }?pageNB="+pageNo;
			});
			
		});
	</script>