<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>文档管理</title>
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
					标题:
					<input name="TITLE:like" type="text">
					分类:
					<input name="C.NAME:like" id="C_NAME_LIMIT" type="text">
					<a id="a_var_search" href="javascript:void(0)"
						class="easyui-linkbutton" data-options="plain:true"
						iconCls="icon-search">查询</a>
					<a id="a_var_reset" href="javascript:void(0)"
						class="easyui-linkbutton" data-options="plain:true"
						iconCls="icon-reload">清除条件</a>
				</form>
				<input type="hidden" id="doctypeId">
			</div>
			<div class="datagrid_div">
				<table id="dom_var_grid" style="height: 350px">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th field="TITLE" data-options="sortable:true" width="80">
								标题
							</th>
							<th field="DOMTYPE" data-options="sortable:true" width="80">
								内容类型
							</th>
							<th field="AUTHOR" data-options="sortable:true" width="80">
								作者
							</th>
							<th field="PUBTIME" data-options="sortable:true" width="80">
								发布时间
							</th>
							<th field="SHORTTITLE" data-options="sortable:true" width="80">
								短标题
							</th>
							<th field="TAGKEY" data-options="sortable:true" width="80">
								tag标签
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
		</div>
	</body>
	<script type="text/javascript">
	var url_delAction = "admin/FarmDocdeleteCommit.do";
	var url_formAction = "admin/FarmDocshow.do";
	var url_searchAction = "admin/FarmDocqueryAll.do";
	var title_window = "文档管理";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	var TOOL_BAR = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : function() {
			shortMenuOpen_grid(null, {
				name : 'view'
			})
		}
	}, {
		id : 'add',
		text : '增加',
		iconCls : 'icon-add',
		handler : function() {
			if (!$('#doctypeId').val()) {
				alert('请选择一个分类');
				return false;
			}
			shortMenuOpen_grid(null, {
				name : 'add'
			})
		}
	}, {
		id : 'edit',
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
	}, {
		id : 'del',
		text : '权限设置',
		iconCls : 'icon-settings',
		handler : function() {
			shortMenuOpen_grid(null, {
				name : 'settings'
			})
		}
	} ];
	/**
	 * 表格控件的按钮或菜单被点击
	 * 
	 * @param rowIndex
	 *            点击的表格中的行号
	 * @param menuItem
	 *            当前操作的字符串标示，对象 有name参数
	 * @return 
	 */
	function shortMenuOpen_grid(rowIndex, menuItem) {
		if (menuItem.name == 'view') {
			row_ViewAndEditFun(DOM_GRID_TABLE, rowIndex, DOM_WINDOW_DIV,
					function(selectedArray) {
						return url_formAction + '?pageset.pageType=0&ids='
								+ selectedArray[0].ID;
					}, title_window);
		}
		if (menuItem.name == 'edit') {
			row_ViewAndEditFun(DOM_GRID_TABLE, rowIndex, DOM_WINDOW_DIV,
					function(selectedArray) {
						return url_formAction + '?pageset.pageType=2&ids='
								+ selectedArray[0].ID;
					}, title_window);
		}
		if (menuItem.name == 'settings') {
			row_ViewAndEditFun(DOM_GRID_TABLE, rowIndex, DOM_WINDOW_DIV,
					function(selectedArray) {
						return "admin/FarmDocRightedit.do" + '?pageset.pageType=2&ids='
								+ selectedArray[0].ID;
					}, title_window);
		}
		if (menuItem.name == 'del') {
			delGridRow(rowIndex, url_delAction, DOM_GRID_TABLE);
		}
		if (menuItem.name == 'add') {
			openWindow(DOM_WINDOW_DIV, windowDefaultSize.height,
					windowDefaultSize.width, true,
					url_formAction + '?pageset.pageType=1', title_window);
		}
		menuFunctionHandle(rowIndex, menuItem.name);
	}
	$(function() {
		initLayout();
	});
	$(function() {
		$('#tt').tree( {
			url : 'admin/FarmDoctypeLoadTreeNode.do',
			onSelect : function(node) {
				$('#C_NAME_LIMIT').val(node.text);
				$('#doctypeId').val(node.id);
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
	function openWindow(divId, heightSize, widthSize, modalType, url,
			windowTitle) {
		$(divId).window( {
			width : widthSize,
			height : heightSize,
			title : windowTitle,
			modal : modalType,
			minimizable : false,
			maximized : false,
			closed : true
		});
		$(divId).window('open');
		$(divId)
				.window('refresh', url + '&doctype.id=' + $('#doctypeId').val());
	}
</script>
</html>




