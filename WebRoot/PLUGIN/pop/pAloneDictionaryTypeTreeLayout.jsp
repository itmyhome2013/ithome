<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div>
	<table style="width: 100%;">
			<tr>
				<td style="vertical-align: top; width: 200px;">
					<div class="TREE_COMMON_BOX_DIV"  style="height: 380px">
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
					<div class="search_div" style="display: none;">
						<form id="dom_dT_var_search">
							名称:
							<input id="NAME_like" name="a.NAME:like" type="text">
							<a id="a_var_search" href="javascript:void(0)"
								class="easyui-linkbutton" data-options="plain:true"
								iconCls="icon-search">查询</a>
							<a id="a_var_reset" href="javascript:void(0)"
								class="easyui-linkbutton" data-options="plain:true"
								iconCls="icon-reload">清除条件</a>
						</form>
					</div>
					<div class="datagrid_div">
						<table id="dom_dT_var_grid" style="height: 360px">
							<thead>
								<tr>
									<th data-options="field:'ck',checkbox:true"></th>
									<th field="NAME" data-options="sortable:true" width="80">
										名称
									</th>
									<th field="PNAME" data-options="sortable:true" width="80">
										上级节点
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
					<div id="dom_var_window"></div>
				
				</td>
			</tr>
		</table>
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

	var LINK_SEARCH = '#aa_var_search';
	var LINK_RESET = '#aa_var_reset';
	var TOOL_dT_BAR = [ {
		text : '增加根项',
		iconCls : 'icon-add',
		handler : function() {
			short_dTMenuOpen_grid(null, {
				name : 'addRoot'
			})
		}
	},{
		text : '增加子项',
		iconCls : 'icon-add',
		handler : function() {
			short_dTMenuOpen_grid(null, {
				name : 'addSub'
			})
		}
	},  {
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
						console.info(selectedArray[0]);
						return 'admin/ALONEDictionaryType_VIEWTREE_SUBMIT.do' + '?pageset.pageType=2&ids=' + selectedArray[0].ID+'&parentId='+selectedArray[0].PARENTID;
					}, title_dt_window);
		}
		if (menuItem.name == 'del') {
			delGridRow(rowIndex, 'admin/ALONEDictionaryType_DEL_SUBMIT.do',
					DOM_dT_GRID_TABLE);
		}
		if (menuItem.name == 'addRoot') {
			openWindow(
					DOM_dT_WINDOW_DIV,
					400,
					600,
					true,
					'admin/ALONEDictionaryType_VIEWTREE_SUBMIT.do' + '?pageset.pageType=1&dicId=${ids}',
					title_dt_window);
		}
		if (menuItem.name == 'addSub') {
			
			var selectedArray = $(DOM_dT_GRID_TABLE).datagrid('getSelections');
			

			if (selectedArray.length > 0) {
				// 有数据执行操作
				var str = MESSAGE_PLAT.COMMIT_IS;
				openWindow(
						DOM_dT_WINDOW_DIV,
						400,
						600,
						true,
						'admin/ALONEDictionaryType_VIEWTREE_SUBMIT.do' + '?pageset.pageType=1&dicId=${ids}&parentId='+selectedArray[0].ID,
						title_dt_window); 
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE, 'info');
			}
			
			/*
			*/
		}
	}
	$(function() {
		$('#tt').tree( {   
            url : 'admin/ALONEDictionary_TREENODE.do?ids=${ids}',   
            onSelect : function(node) {   
                $('#NAME_like').attr('value', node.text);   
                do_search("#dom_dT_var_search", "#dom_dT_var_grid", "#dom_dT_var_pagination");   
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




