<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>文档分类</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/document/commons/include_all.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true"
			style="width: 200px; padding: 4px;">
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
		<div data-options="region:'center'" style="padding: 5px;">
			<div class="search_div">
				<form id="dom_var_search">
					分类名称:
					<input name="a.NAME:like"  id="like_name" type="text">
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
								分类名称
							</th>
							<th field="TYPEMOD" data-options="sortable:true" width="80">
								分类模板
							</th>
							<th field="CONTENTMOD" data-options="sortable:true" width="80">
								内容模板
							</th>
							<th field="SORT" data-options="sortable:true" width="80">
								排序
							</th>
							<th field="TYPE" data-options="sortable:true" width="80">
								类型
							</th>
							<th field="PSTATE" data-options="sortable:true" width="80">
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
		</div>
	</body>
	<script type="text/javascript">
	var url_delAction = "admin/FarmDoctypedeleteCommit.do";
	var url_formAction = "admin/FarmDoctypeshow.do";
	var url_searchAction = "admin/FarmDoctypequeryAll.do";
	var title_window = "文档分类";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	var windowDefaultSize = {
		width : 600,
		height : 500
	};
	var TOOL_BAR = [
			{
				text : '增加一级分类',
				iconCls : 'icon-add',
				handler : function() {

					shortMenuOpen_grid(null, {
						name : 'add'
					})
				}
			},
			{
				text : '增加子分类',
				iconCls : 'icon-add',
				handler : function() {
					operateHandleForGrid(null, DOM_GRID_TABLE, false, function(
							nodeArray) {
						openWindow(DOM_WINDOW_DIV, 500, 600, true,
								url_formAction
										+ '?pageset.pageType=1&ids='
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
				id : 'del',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					shortMenuOpen_grid(null, {
						name : 'del'
					})
				}
			} ];
	$(function() {
		initLayout();
	});
	$(function() {
		$('#tt').tree( {
			url : 'admin/FarmDoctypeLoadTreeNode.do',
			onSelect : function(node) {
				$('#like_name').attr('value', node.text);
				do_search(DOM_SEARCH_FORM, DOM_GRID_TABLE, DOM_PAGINATION_DIV);
			}
		});
		$('#a_tree_reload').bind('click', function() {
			$('#tt').tree('reload');
		});
		$('#a_tree_openAll').bind('click', function() {
			$('#tt').tree('expandAll');
		});
	});
</script>
</html>




