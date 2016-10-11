<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>系统用户</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/pop/commons/include_all.jsp"></jsp:include>
	</head>
	<body>
		<div class="search_div">
			<form id="dom_var_search">
				名称:
				<input name="a.NAME:like" type="text">
				组织机构:
				<input name="f.name:like" type="text">
				登录名:
				<input name="a.LOGINNAME:like" type="text">
				<a id="a_var_search" href="javascript:void(0)"
					class="easyui-linkbutton" data-options="plain:true"
					iconCls="icon-search">查询</a>
				<a id="a_var_reset" href="javascript:void(0)"
					class="easyui-linkbutton" data-options="plain:true"
					iconCls="icon-reload">清除条件</a>
			</form>
		</div>
		<div class="datagrid_div">
			<table id="dom_var_grid" style="height: 350px">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="A_NAME" data-options="sortable:true" width="40">
							用户名称
						</th>
						<th field="ID" data-options="sortable:true" width="140">
							id
						</th>
						<th field="ROLENAME" data-options="sortable:true" width="80">
							管理角色
						</th>
						<th field="ORGNAME" data-options="sortable:true" width="80">
							组织机构
						</th>
						<th field="A_TYPE" data-options="sortable:true" width="80">
							类型
						</th>
						<th field="A_STATE" data-options="sortable:true" width="80">
							状态
						</th>
						<th field="A_LOGINNAME" data-options="sortable:true" width="80">
							登录名
						</th>
						<th field="A_LOGINTIME" data-options="sortable:true" width="80">
							最近登录时间
						</th>
					</tr>
				</thead>
			</table>
			<!-- 分页栏 -->
			<div id="dom_var_pagination" class="easyui-pagination"></div>
		</div>
		<!-- 弹出窗口 -->
		<div id="dom_var_window"></div>
		<!-- 表格右键菜单 -->
		<div id="dom_var_menu" class="easyui-menu" style="width: 120px;">
			<div name="view" data-options="iconCls:'icon-tip'">
				查看
			</div>
			<div name="edit" data-options="iconCls:'icon-edit'">
				修改
			</div>
			<div class="menu-sep"></div>
			<div name="del" data-options="iconCls:'icon-remove'">
				删除
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var url_delAction = "admin/AloneUserdeleteCommit.do";
	var url_formAction = "admin/AloneUsershow.do";
	var url_searchAction = "admin/AloneUserqueryAll.do";
	var title_window = "系统用户";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';

	$(function() {
		TOOL_BAR
				.push( {
					text : '设置管理角色',
					iconCls : 'icon-special-offer',
					handler : function() {
						//获得当前行
					operateHandleForGrid(
							null,
							DOM_GRID_TABLE,
							false,
							function(nodeArray) {
								//打开权限设置窗口AloneUser_rule_CONSOLE
								openWindow(
										DOM_WINDOW_DIV,
										340,
										300,
										true,
										'admin/AloneUser_rule_CONSOLE.do?ids=' + nodeArray[0].ID,
										'设置管理角色');
							});
				}
				});
		TOOL_BAR
				.push( {
					text : '设置组织机构',
					iconCls : 'icon-sitemap-application',
					handler : function() {
						//获得当前行
					operateHandleForGrid(
							null,
							DOM_GRID_TABLE,
							false,
							function(nodeArray) {

								//打开权限设置窗口AloneUser_rule_CONSOLE
								openWindow(
										DOM_WINDOW_DIV,
										340,
										300,
										true,
										'admin/AloneUser_orgMore_CONSOLE.do?ids=' + getGridCheckedIds(DOM_GRID_TABLE),
										'设置组织机构');
							});
				}
				});
		initLayout();
	});
</script>
</html>




