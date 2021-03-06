<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.core.config.AppConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<PF:basePath/>">
		<title><PF:sysTitle />
		</title>
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div id="loadInitFrameIndex"
			style="vertical-align: middle; text-align: center; padding-top: 25px; height: 2000px;">
			<img style="margin-top: 50px;" alt=""
				src="WEB-FACE/img/style/loadinglogo.png">
			<!-- <img align="middle" src="WEB-FACE/img/style/loading.gif" />
			<span style="font-size: 14px; color: #102947;">&nbsp;加载中...</span> -->
		</div>
		<div data-options="region:'north',border:false"
			style="height: 70px; border: 0px; padding: 0px; overflow: hidden; background-image: url('WEB-FACE/img/style/headback.png')">
			<jsp:include page="head.jsp"></jsp:include>
		</div>
		<div data-options="region:'south',border:false"
			style="height: 25px; background-color: #F3F3F3;">
			<jsp:include page="foot.jsp"></jsp:include>
		</div>
		<div data-options="region:'center'">
			<div class="easyui-tabs" id="frame_tabs" style="overflow: hidden;"
				data-options="fit:true,border:false">
				<div title="首页" style="overflow: hidden;">
					<iframe scrolling="auto" frameborder="0"
						src="UI-BASE/PLAT/main.jsp" style="width: 100%; height: 100%;"></iframe>
				</div>
			</div>
		</div>
		<div id="tabsShortMenu" class="easyui-menu"
			style="width: 120px; display: none;">
			<div id="closeCtabs">
				关闭当前
			</div>
			<div id="closeOtabs">
				关闭其他
			</div>
			<div id="closeAtabs">
				关闭全部
			</div>
		</div>
	</body>
	<script type="text/javascript">
	$.parser.onComplete = function() {
		$('#loadInitFrameIndex').hide();
	};

	$(function() {
		//加载选项卡上的右键菜单（用于关闭多个选项卡）
		$('#frame_tabs').bind('contextmenu', function(e) {
			e.preventDefault();
			$('#tabsShortMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		});
		//关闭当前选项卡
		$('#closeCtabs', '#tabsShortMenu').bind('click', function() {
			var tab = $('#frame_tabs').tabs('getSelected');
			var index = $('#frame_tabs').tabs('getTabIndex', tab);
			if (index != 0) {
				$('#frame_tabs').tabs('close', index);
			}
		});
		//关闭其他选项卡
		$('#closeOtabs', '#tabsShortMenu').bind('click', function() {
			var tab = $('#frame_tabs').tabs('getSelected');
			var index = $('#frame_tabs').tabs('getTabIndex', tab);
			var delIndex = 1;
			$($('#frame_tabs').tabs('tabs')).each(function(i) {
				if (i == index) {
					delIndex = 2;
					//如果当前页是首页就从第一个开始删
					if (index == 0) {
						delIndex = 1;
					}
				}
				if (i != 0) {
					if ($('#frame_tabs').tabs('exists', delIndex)) {
						$('#frame_tabs').tabs('close', delIndex);
					}
				}
			});
			if ($('#frame_tabs').tabs('exists', 1)) {
				$('#frame_tabs').tabs('select', 1);
			}
		});
		//关闭所有选项卡
		$('#closeAtabs', '#tabsShortMenu').bind('click', function() {
			$($('#frame_tabs').tabs('tabs')).each(function(i) {
				if ($('#frame_tabs').tabs('exists', 1)) {
					$('#frame_tabs').tabs('close', 1);
				}
			});
		});
	});
	//-->
</script>
</html>