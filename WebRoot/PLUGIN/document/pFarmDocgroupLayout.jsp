<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>工作小组</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/document/commons/include_all.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchfarmdocgroup">
				<table class="editTable">
					<tr>
						<td class="title">
							小组名称:
						</td>
						<td>
							<input name="GROUPNAME:like" type="text">
						</td>
						<td class="title">
						</td>
						<td>
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
			<table class="easyui-datagrid" id="dom_datagridfarmdocgroup">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="GROUPNAME" data-options="sortable:true" width="80">
							小组名称
						</th>
						<th field="GROUPNOTE" data-options="sortable:true" width="80">
							小组介绍
						</th>
						<th field="GROUPTAG" data-options="sortable:true" width="80">
							小组标签
						</th>
						<th field="JOINCHECK" data-options="sortable:true" width="80">
							加入时验证
						</th>
						<th field="USERNUM" data-options="sortable:true" width="80">
							小组人数
						</th>
						<th field="PSTATE" data-options="sortable:true" width="80">
							状态
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionfarmdocgroup = "admin/FarmDocgroupdeleteCommit.do";//删除URL
	var url_formActionfarmdocgroup = "admin/FarmDocgroupshow.do";//增加、修改、查看URL
	var url_searchActionfarmdocgroup = "admin/FarmDocgroupqueryAll.do";//查询URL
	var title_windowfarmdocgroup = "工作小组";//功能名称
	var gridfarmdocgroup;//数据表格对象
	var searchfarmdocgroup;//条件查询组件对象
	var TOOL_BARfarmdocgroup = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatafarmdocgroup
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatafarmdocgroup
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatafarmdocgroup
	} ];
	$(function() {
		//初始化数据表格
		gridfarmdocgroup = $('#dom_datagridfarmdocgroup').datagrid( {
			url : url_searchActionfarmdocgroup,
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfarmdocgroup,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		//初始化条件查询
		searchfarmdocgroup = $('#dom_searchfarmdocgroup').searchForm( {
			gridObj : gridfarmdocgroup
		});
	});
	//查看
	function viewDatafarmdocgroup() {
		var selectedArray = $(gridfarmdocgroup).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmdocgroup + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winfarmdocgroup',
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
	function addDatafarmdocgroup() {
		var url = url_formActionfarmdocgroup + '?pageset.pageType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winfarmdocgroup',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatafarmdocgroup() {
		var selectedArray = $(gridfarmdocgroup).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmdocgroup + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow( {
				id : 'winfarmdocgroup',
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
	function delDatafarmdocgroup() {
		var selectedArray = $(gridfarmdocgroup).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str
					+ "(该小组所有文档将被公开,编辑权限和阅读权限将开放给所有人)", function(flag) {
				if (flag) {
					$.post(url_delActionfarmdocgroup + '?ids='
							+ $.farm.getCheckedIds(gridfarmdocgroup), {},
							function(flag) {
								if (flag.pageset.commitType == 0) {
									$(gridfarmdocgroup).datagrid('reload');
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




