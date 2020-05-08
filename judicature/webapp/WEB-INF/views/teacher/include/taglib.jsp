<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="fnc" uri="/WEB-INF/tlds/fnc.tld" %>
<%@ taglib prefix="questionlib" uri="/WEB-INF/tlds/questionlib.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="syst" tagdir="/WEB-INF/tags/syst" %>

<c:set var="prc" value="${pageContext.request.contextPath}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getTeacherPath()}"/>
<c:set var="adminPath" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<style>
.tbtn-primary{
background-color:#1c9993 ! important;
background-image:-webkit-gradient(linear,0 0,0 100%,from(#1c9993),to(#1c9993)) ! important;
background-image:-webkit-linear-gradient(top,#1c9993,#1c9993) ! important;
background-image:linear-gradient(to bottom,#1c9993,#1c9993) ! important;
padding:4px 25px ! important;
color:#fff ! important;
}

.main-left-con .navlist > li em{margin-right:65px;}.main-content .main-left .main-left-con{width:120%;}


 @media screen and (min-width: 1500px){
  .container{width:1500px;}
  .main-content .content-right{width:80%;}
  }
</style>