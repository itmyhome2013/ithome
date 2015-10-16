<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>系统菜单</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/pop/commons/include_all.jsp"></jsp:include>
	</head>
	<body>
		<table style="width: 100%;">
			<tr>
				<td style="vertical-align: top; width: 250px;">
					<div class="TREE_COMMON_BOX_DIV">
						<div class="TREE_COMMON_BOX_SPLIT_DIV">
							<a id="a_tree_reload" href="javascript:void(0)"
								class="easyui-linkbutton" data-options="plain:true"
								iconCls="icon-reload">刷新菜单</a>
							<a id="a_tree_openAll" href="javascript:void(0)"
								class="easyui-linkbutton" data-options="plain:true"
								iconCls="icon-sitemap">全部展开</a>
						</div>
						<ul id="tt"></ul>
					</div>
				</td>
				<td style="vertical-align: top;">
					<div class="search_div">
						<form id="dom_var_search">
							名称:
							<input id="NAME_like" name="NAME:like" type="text">
							类型:
							<input name="TYPE:=" type="text">
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
										名称
									</th>
									<th field="TYPE" data-options="sortable:true" width="80">
										类型
									</th>
									<th field="SORT" data-options="sortable:true" width="80">
										排序号
									</th>
									<th field="STATE" data-options="sortable:true" width="80">
										状态
									</th>
									<th field="IMG" data-options="sortable:true" width="80">
										图片
									</th>
									<th field="COMMENTS" data-options="sortable:true" width="80">
										备注
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
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
	var url_delAction = "admin/ALONEMenu_DEL_SUBMIT.do";
	var url_formAction = "admin/ALONEMenu_VIEW_SUBMIT.do";
	var url_searchAction = "admin/ALONEMenu_SYSBASE_QUERYALL.do";
	var title_window = "系统菜单";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	var TOOL_BAR = [
			{
				text : '查看',
				iconCls : 'icon-tip',
				handler : function() {
					shortMenuOpen_grid(null, {
						name : 'view'
					})
				}
			},
			{
				text : '增加根节点',
				iconCls : 'icon-add',
				handler : function() {
					shortMenuOpen_grid(null, {
						name : 'add'
					})
				}
			},
			{
				text : '增加子节点',
				iconCls : 'icon-add',
				handler : function() {
					operateHandleForGrid(null, DOM_GRID_TABLE, false, function(
							nodeArray) {
						openWindow(DOM_WINDOW_DIV, 400, 600, true,
								url_formAction
										+ '?pageset.pageType=1&aloneContext='
										+ nodeArray[0].ID, '增加子节点');
					});
				}
			}, {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					shortMenuOpen_grid(null, {
						name : 'edit'
					})
				}
			}, {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					shortMenuOpen_grid(null, {
						name : 'del'
					})
				}
			} ];
	$(function() {
		$('#tt').tree( {
			url : 'admin/ALONEMENU_SYSBASE_TREENODE.do',
			onSelect : function(node) {
				$('#NAME_like').attr('value', node.text);
				do_search(DOM_SEARCH_FORM, DOM_GRID_TABLE, DOM_PAGINATION_DIV);
			}
		});
		$('#a_tree_reload').bind('click', function() {
			$('#tt').tree('reload');
		});
		$('#a_tree_openAll').bind('click', function() {
			$('#tt').tree('expandAll');
		});
		initLayout();
	});
</script>

</html>




