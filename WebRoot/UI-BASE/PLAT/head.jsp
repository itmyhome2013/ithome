<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--40 <span style="font-size: 14px; font-weight: bold;margin: 20px;"> 系统管理控制台2.0.0</span> -->
<div>
	<div style="float: right; padding-top: 4px;">
		<b><c:forEach var="node" varStatus="index"
				items="${sessionScope.LOGINROLES}">
				<c:if test="${index.count!=1}">,</c:if>
						${node.name}
						</c:forEach> </b>
		<span style="color: green; font-weight: bold;">${sessionScope.USEROBJ.name}</span>属于
		<b>${sessionScope.USERORG.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
	</div>
	<div style="padding-top: 40px; margin-left: 250px; float: left;">
		<div style="float: left;">
			<c:forEach items="${result}" var="node">
				<c:if test="${node.PARENTID=='NONE'}">
					<a href="#" class="easyui-menubutton" data-options="menu:'#mm${node.ID}',iconCls:'${node.IMG}'">${node.NAME}</a>
					<div id="mm${node.ID}" style="width: 100px;">
						<c:forEach items="${result}" var="node2">
							<c:if test="${node2.PARENTID==node.ID}">
								<div class="frame_menulink" url="${node2.URL}" id="${node2.ID}"
									text="${node2.NAME}" data-options="iconCls:'${node2.IMG}'">
									<span>${node2.NAME}</span>
									<c:set var="isDiv" value="0"></c:set>
									<c:forEach items="${result}" var="node3">
										<c:if test="${node3.PARENTID==node2.ID}">
											<c:set var="isDiv" value="1"></c:set>
										</c:if>
									</c:forEach>
									<c:if test="${isDiv==1}">
										<div style="width: 150px;">
											<c:forEach items="${result}" var="node3">
												<c:if test="${node3.PARENTID==node2.ID}">
													<div class="frame_menulink" url="${node3.URL}"
														id="${node3.ID}" text="${node3.NAME}"
														data-options="iconCls:'${node3.IMG}'">
														<span>${node3.NAME}</span>
													</div>
												</c:if>
											</c:forEach>
										</div>
									</c:if>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
			</c:forEach>
			<a href="#" class="easyui-menubutton"
				data-options="menu:'#mm2',iconCls:'icon-suppliers'">用户</a>
			<div id="mm2" style="width: 100px;">
				<div id="editPW_a" data-options="iconCls:'icon-login'">
					修改密码
				</div>
				<div id="logout_a" data-options="iconCls:'icon-sign-out'">
					<span>退出系统</span>
				</div>
			</div>
		</div>
	</div>
	<div id="DIV_EDIT_PASSWORD_WINDOW"></div>
</div>
<script type="text/javascript">
	$('#logout_a').bind('click', function() {
		$.messager.confirm('退出系统', '确定要注销此用户并退出系统吗？', function(r) {
			if (r) {
				window.location = "admin/ALONEFRAME_LOGOUT_COMMIT.do";
			}
		});
	});
	$('#editPW_a').bind('click', function() {
		$('#DIV_EDIT_PASSWORD_WINDOW').dialog( {
			title : '修改用户密码',
			width : 300,
			height : 200,
			closed : false,
			cache : false,
			href : 'admin/AloneUser_UpdataPassWord.do',
			modal : true
		});
	});
	$('.frame_menulink').bind(
			'click',
			function() {
				if ($(this).attr('url')) {
					openUrlAtIfram($(this).attr("url"), $(this).attr('id'), $(
							this).attr('text'));
				}
			});
	/**在选项卡中打开一个菜单
	 *address:地址默认为action的index
	 *id：识别是否已经打开的标签用的
	 *text:显示的中文标题
	 *isURI:是否是URL，默认false即系统会自动拼admin/和.do
	 **/
	function openUrlAtIfram(address, id, text, isURI) {
		var URL = '';
		if (!isURI) {
			isURI == false;
			URL = 'admin/' + address + '.do';
		} else {
			URL = address;
		}
		//打开一个选项卡在选项卡中加载页面
		var exist_tab = $('#frame_tabs').tabs('getTab', text);
		//是否已经打开该选项卡
		if (exist_tab && $(exist_tab).attr('id') == 'tab' + id) {
			$('#frame_tabs').tabs('select', text);
			return;
		} else {
			$('#frame_tabs')
					.tabs(
							'add',
							{
								'id' : 'tab' + id,
								title : text,
								fit : true,
								content : '<div id="load'
										+ id
										+ '"  style="vertical-align:middle; text-align: center; padding-top: 25px;"><img style="margin-top: 50px;" alt="" src="WEB-FACE/img/style/loadinglogo.png"></div><iframe onload="loadLaberOver(\'load'
										+ id
										+ '\');" scrolling="auto" frameborder="0"  src="'
										+ URL
										+ '" style="width:100%;height:100%;"></iframe>',
								closable : true
							});
			$('#frame_tabs').tabs('getSelected').css('overflow', 'hidden');
		}
	}
	function loadLaberOver(id) {
		$('#' + id).remove();
	}
	//-->
</script>