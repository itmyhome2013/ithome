<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>留言板</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/document/commons/include_all.jsp"></jsp:include>
	</head>
	<body>
		<div class="search_div">
			<form id="dom_var_search">
				主题:
				<input name="TITLE:like" type="text">
				说明:
				<input name="PCONTENT:like" type="text">
				发送人:
				<input name="CUSER:like" type="text">
				接收人:
				<input name="READUSERID:like" type="text">
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
						<th field="CTIME" data-options="sortable:true" width="80">
							创建时间
						</th>
						<!-- <th field="CONTENT" data-options="sortable:true" width="80">
							内容
						</th>
						<th field="TITLE" data-options="sortable:true" width="80">
							主题
						</th> -->
						<th field="PCONTENT" data-options="sortable:true" width="80">
							说明
						</th>
						<th field="READSTATE" data-options="sortable:true" width="80">
							阅读状态
						</th>
						<th field="CUSERNAME" data-options="sortable:true" width="80">
							发送人
						</th>
						<th field="READUSERNAME" data-options="sortable:true" width="80">
							接收人
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
	//	 {
	//	id : 'view',
	//	text : '查看',
	//	iconCls : 'icon-tip',
	//	handler : function() {
	//		shortMenuOpen_grid(null, {
	//			name : 'view'
	//			})
	//		}
	//	}, 
	var TOOL_BAR = [ {
		id : 'add',
		text : '发送私人消息',
		iconCls : 'icon-add',
		handler : function() {
			shortMenuOpen_grid(null, {
				name : 'add'
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
	var url_delAction = "admin/FarmDocmessagedeleteCommit.do";
	var url_formAction = "admin/FarmDocmessageshow.do";
	var url_searchAction = "admin/FarmDocmessagequeryAll.do";
	var title_window = "留言板";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	$(function() {
		initLayout();
	});
</script>
</html>




