<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="fnc" uri="/WEB-INF/tlds/fnc.tld" %>
<%@ taglib prefix="questionlib" uri="/WEB-INF/tlds/questionlib.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="act" tagdir="/WEB-INF/tags/act" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/cms" %>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>

<c:set var="imageServer" value="${fns:getConfig('category.server')}"/>

<style>
.btn-primary{
background-color:#1283c8 ! important;
background-image:-webkit-gradient(linear,0 0,0 100%,from(#1283c8),to(#0f74b2)) ! important;
background-image:-webkit-linear-gradient(top,#1283c8,#0f74b2) ! important;
background-image:linear-gradient(to bottom,#1283c8,#0f74b2) ! important;
padding:4px 6px ! important;
}
.btn{padding:4px 6px;}
#btnCancel{padding:5px 25px ! important;}
</style>