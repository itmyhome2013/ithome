<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div id="frame_accordion" class="easyui-accordion"
	data-options="fit:true,border:false">
	<c:set var="index" value="0"></c:set>
	<c:forEach items="${result}" var="node">
		<c:if test="${index==0}">
			<div img="${node.IMG}" url="${node.URL}" id="${node.ID}"
				title="${node.NAME}" data-options="selected:true">
				<ul id="tree${node.ID}" class="easyui-tree">
				</ul>
			</div>
		</c:if>
		<c:if test="${index==1}">
			<div title="${node.NAME}" img="${node.IMG}" url="${node.URL}"
				id="${node.ID}" style="">
				<ul id="tree${node.ID}" class="easyui-tree">
				</ul>
			</div>
		</c:if>
		<c:set var="index" value="1"></c:set>
	</c:forEach>
</div>

