<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div class="datagrid_div">
	<table id="dom_dT_var_grid" style="height: 350px">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th field="NAME" data-options="sortable:true" width="80">
					名称
				</th>
				<th field="ENTITYTYPE" data-options="sortable:true" width="80">
					类型
				</th>
				<th field="SORT" data-options="sortable:true" width="80">
					排序号
				</th>
				<th field="STATE" data-options="sortable:true" width="80">
					状态
				</th>
			</tr>
		</thead>
	</table>
	<!-- 分页栏 -->
	<div id="dom_dT_var_pagination" class="easyui-pagination"></div>
</div>
<!-- 弹出窗口 -->
<div id="dom_dT_var_window"></div>
<!-- 表格右键菜单 -->
<div id="dom_dT_var_menu" class="easyui-menu" style="width: 120px;">
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
<script type="text/javascript">
	var title_dt_window = "数据类型";
	var DOM_dT_GRID_TABLE = '#dom_dT_var_grid';
	var DOM_dT_PAGINATION_DIV = '#dom_dT_var_pagination';
	var DOM_dT_MENU_DIV = '#dom_dT_var_menu';
	var DOM_dT_WINDOW_DIV = '#dom_dT_var_window';
	var TOOL_dT_BAR = [ {
		text : '增加',
		iconCls : 'icon-add',
		handler : function() {
			short_dTMenuOpen_grid(null, {
				name : 'add'
			})
		}
	}, {
		text : '修改',
		iconCls : 'icon-edit',
		handler : function() {
			short_dTMenuOpen_grid(null, {
				name : 'edit'
			})
		}
	}, {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			short_dTMenuOpen_grid(null, {
				name : 'del'
			})
		}
	} ];
	$(function() {
		
		setQueryRule('#dom_dT_var_grid', 'ids', '${ids}');
		init_grid(DOM_dT_PAGINATION_DIV, DOM_dT_GRID_TABLE,
				'admin/ALONEDictionaryType_SYSBASE_QUERYALL.do', null,
				TOOL_dT_BAR, false);
		init_pagination(DOM_dT_PAGINATION_DIV, DOM_dT_GRID_TABLE, null);
	});
	function short_dTMenuOpen_grid(rowIndex, menuItem) {
		if (menuItem.name == 'view') {
			row_ViewAndEditFun(
					DOM_dT_GRID_TABLE,
					rowIndex,
					DOM_dT_WINDOW_DIV,
					function(selectedArray) {
						return 'admin/ALONEDictionaryType_VIEW_SUBMIT.do' + '?pageset.pageType=0&ids=' + selectedArray[0].ID;
					}, title_dt_window);
		}
		if (menuItem.name == 'edit') {
			row_ViewAndEditFun(
					DOM_dT_GRID_TABLE,
					rowIndex,
					DOM_dT_WINDOW_DIV,
					function(selectedArray) {
						return 'admin/ALONEDictionaryType_VIEW_SUBMIT.do' + '?pageset.pageType=2&ids=' + selectedArray[0].ID;
					}, title_dt_window);
		}
		if (menuItem.name == 'del') {
			delGridRow(rowIndex, 'admin/ALONEDictionaryType_DEL_SUBMIT.do',
					DOM_dT_GRID_TABLE);
		}
		if (menuItem.name == 'add') {
			openWindow(
					DOM_dT_WINDOW_DIV,
					400,
					600,
					true,
					'admin/ALONEDictionaryType_VIEW_SUBMIT.do' + '?pageset.pageType=1&dicId=${ids}',
					title_dt_window);
		}
	}
</script>




