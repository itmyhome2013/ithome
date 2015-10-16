<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>工作小组成员</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/document/commons/include_all.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchfarmdocgroupuser">
				<table class="editTable">
					<tr>
						<td class="title">
							工作小组:
						</td>
						<td>
							<input name="c.GROUPNAME:like" type="text">
						</td>
						<td class="title">
							用户:
						</td>
						<td>
							<input name="b.name:like" type="text">
						</td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="4">
							<a id="a_search" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a id="a_reset" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table class="easyui-datagrid" id="dom_datagridfarmdocgroupuser">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="GROUPNAME" data-options="sortable:true" width="80">
							工作小组
						</th>
						<th field="USERNAME" data-options="sortable:true" width="80">
							用户
						</th>
						<th field="LEADIS" data-options="sortable:true" width="80">
							是否小组管理员
						</th>
						<th field="EDITIS" data-options="sortable:true" width="80">
							修改权限
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionfarmdocgroupuser = "admin/FarmDocgroupUserdeleteCommit.do";//删除URL
	var url_formActionfarmdocgroupuser = "admin/FarmDocgroupUsershow.do";//增加、修改、查看URL
	var url_searchActionfarmdocgroupuser = "admin/FarmDocgroupUserqueryAll.do";//查询URL
	var title_windowfarmdocgroupuser = "工作小组成员";//功能名称
	var gridfarmdocgroupuser;//数据表格对象
	var searchfarmdocgroupuser;//条件查询组件对象
	var TOOL_BARfarmdocgroupuser = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatafarmdocgroupuser
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatafarmdocgroupuser
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatafarmdocgroupuser
	} ];
	$(function() {
		//初始化数据表格
		gridfarmdocgroupuser = $('#dom_datagridfarmdocgroupuser').datagrid( {
			url : url_searchActionfarmdocgroupuser,
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfarmdocgroupuser,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		//初始化条件查询
		searchfarmdocgroupuser = $('#dom_searchfarmdocgroupuser').searchForm( {
			gridObj : gridfarmdocgroupuser
		});
	});
	//查看
	function viewDatafarmdocgroupuser() {
		var selectedArray = $(gridfarmdocgroupuser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmdocgroupuser + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winfarmdocgroupuser',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//新增
	function addDatafarmdocgroupuser() {
		var url = url_formActionfarmdocgroupuser + '?pageset.pageType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winfarmdocgroupuser',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatafarmdocgroupuser() {
		var selectedArray = $(gridfarmdocgroupuser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmdocgroupuser + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow( {
				id : 'winfarmdocgroupuser',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//删除
	function delDatafarmdocgroupuser() {
		var selectedArray = $(gridfarmdocgroupuser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionfarmdocgroupuser + '?ids='
							+ $.farm.getCheckedIds(gridfarmdocgroupuser), {},
							function(flag) {
								if (flag.pageset.commitType == 0) {
									$(gridfarmdocgroupuser).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>




