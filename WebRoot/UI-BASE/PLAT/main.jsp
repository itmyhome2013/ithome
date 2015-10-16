<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<PF:basePath/>">
			<title><PF:sysTitle />
			</title> <jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
			<script type="text/javascript"
				src="WEB-FACE/model/easy_ui_1_3_6/jquery.portal.js"></script>
			<style>
<!--
.portal {
	padding: 0;
	margin: 0;
	border: 1px solid #99BBE8;
	overflow: auto;
}

.portal-noborder {
	border: 0;
}

.portal-panel {
	margin-bottom: 10px;
}

.portal-column-td {
	vertical-align: top;
}

.portal-column {
	padding: 10px 0 10px 10px;
	overflow: hidden;
}

.portal-column-left {
	padding-left: 10px;
}

.portal-column-right {
	padding-right: 10px;
}

.portal-proxy {
	opacity: 0.6;
	filter: alpha(opacity =     60);
}

.portal-spacer {
	border: 3px dashed #eee;
	margin-bottom: 10px;
}
-->
</style>
	</head>
	<body class="easyui-layout" style="background-image: url('WEB-FACE/img/style/backHistory.png')">
		<div class="demo-info" style="margin: 20px;">
			<div class="demo-tip icon-tip"></div>
			<div>
				<b><c:forEach var="node" varStatus="index"
						items="${sessionScope.LOGINROLES}">
						<c:if test="${index.count!=1}">,</c:if>
						${node.name}
						</c:forEach> </b>
				<span style="color: green; font-weight: bold;">${sessionScope.USEROBJ.name}</span>属于
				<b>${sessionScope.USERORG.name}</b>
				<br />
				<span style="color: #666666;">本次登录在 <PF:FormatTime
						date="${sessionScope.LOGINTIME}" yyyyMMddHHmmss="yyyy年MM月dd日HH:mm" />
					, 最近一次登录在 <PF:FormatTime date="${sessionScope.USEROBJ.logintime}"
						yyyyMMddHHmmss="yyyy年MM月dd日HH:mm" /> </span>
			</div>
		</div>
	</body>
</html>