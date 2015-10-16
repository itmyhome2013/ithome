<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>角色管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/pop/commons/include_all.jsp"></jsp:include>
	</head>
	<body>
		<div class="search_div">
			<form id="dom_var_search">
				名称:
				<input name="NAME:like" type="text">
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
						<th field="NAME" data-options="sortable:true" width="80">
							角色名称
						</th>
						<th field="COMMENTS" data-options="sortable:true" width="80">
							备注
						</th>
						<th field="STATE" data-options="sortable:true" width="80">
							状态
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
	var url_delAction = "admin/alonerolegroupdeleteCommit.do";
	var url_formAction = "admin/alonerolegroupshow.do";
	var url_searchAction = "admin/alonerolegroupqueryAll.do";
	var title_window = "角色管理";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	$(function() {
		TOOL_BAR.push( {
			text : '设置权限',
			iconCls : 'icon-sitemap-application',
			handler : function() {
				//获得当前行
				//打开权限设置窗口
				operateHandleForGrid(null, DOM_GRID_TABLE, false, function(
						nodeArray) {
					openWindow(DOM_WINDOW_DIV, 400, 300, true, 'admin/ALONEROLEGROUP_SET_POPTREE.do?ids='+nodeArray[0].ID, '设置角色权限');
				});
			}
		});
		initLayout();
	});
</script>
</html>




