<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>表基础信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout" id="layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchfrmtable">
				<table class="editTable">
					<tr>
						<td class="title">
							表单名称:
						</td>
						<td>
							<input name="cnname:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							英文名:
						</td>
						<td>
							<input name="enname:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							类型:
						</td>
						<td>
							<select name="type:like" class=""  style="width: 180px">
								<option value="">请选择</option>
								<PF:OptionDictionary index="TEST" isTextValue="0" />
							</select>
						</td>
					</tr>
					<tr>
						<td class="title">
							URL:
						</td>
						<td>
							<input name="url:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							所属者:
						</td>
						<td colspan="3">
							<input name="owner:like" type="text" style="width: 180px;">
						</td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6">
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
			<input type="hidden" id="ids" value="" />
			<table class="" id="dom_datagridfrmtable">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="CNNAME" data-options="sortable:true" width="150">
							表单名称
						</th>
						<th field="ENNAME" data-options="sortable:true" width="150">
							英文名
						</th>
						<th field="TYPE" data-options="sortable:true" width="80">
							类型
						</th>
						<th field="URL" data-options="sortable:true" width="280">
							URL
						</th>
						<th field="STATE" data-options="sortable:true" width="80">
							状态
						</th>
						<th field="CRETIME" data-options="sortable:true" width="120">
							创建时间
						</th>
					
					</tr>
				</thead>
			</table>
		</div>
		
	</body>
	<script type="text/javascript">
	var url_delActionFrmTable = "admin/FrmTabledeleteCommit.do";//删除URL
	var url_formActionFrmTable = "admin/FrmTableAllShow.do";//增加、修改、查看URL
	var url_searchActionFrmTable = "admin/FrmTablequeryAll.do";//查询URL
	var title_windowFrmTable = "表单基础信息";//功能名称
	var gridfrmtable;//数据表格对象
	var searchfrmtable;//条件查询组件对象
	var TOOL_BARpopuhuinfo = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatapopuhuinfo
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatapopuhuinfo
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatapopuhuinfo
	}, {
		id : 'del',
		text : '禁用',
		iconCls : 'icon-busy',
		handler : disableDatapopuhuinfo
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatapopuhuinfo
	}
	];
	$(function() {
		//初始化数据表格
		gridfrmtable = $('#dom_datagridfrmtable')
				.datagrid(
						{
							url : url_searchActionFrmTable,
							fit : true,
							fitColumns : true,
							'toolbar' : TOOL_BARpopuhuinfo,
							pagination : true,
							closable : true,
							checkOnSelect : true,
							striped : true,
							rownumbers : true,
							ctrlSelect : true
						});
		//初始化条件查询
		searchfrmtable = $('#dom_searchfrmtable').searchForm( {
			gridObj : gridfrmtable
		});

	});
	
	//查看
	function viewDatapopuhuinfo() {
		var selectedArray = $(gridfrmtable).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionFrmTable + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winfrmtable',
				width : 750,
				height : 350,
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
	function addDatapopuhuinfo() {
		var url = url_formActionFrmTable + '?pageset.pageType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winfrmtable',
			width : 750,
			height : 350,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatapopuhuinfo() {
		var selectedArray = $(gridfrmtable).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionFrmTable + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow( {
				id : 'winfrmtable',
				width : 750,
				height : 350,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}

	//禁用 
	function disableDatapopuhuinfo() {
		var selectedArray = $(gridfrmtable).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "条数据将被禁用，是否继续?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionFrmTable + '?state=0&ids='
							+ $.farm.getCheckedIds(gridfrmtable, 'ID'), {},
							function(flag) {
								if (flag.pageset.commitType == 0) {
									$(gridfrmtable).datagrid('reload');
									$('#layout').layout('collapse', 'east');
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

	//删除
	function delDatapopuhuinfo() {
		var selectedArray = $(gridfrmtable).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
					//+ "</br>删除房屋信息,同时也会删除该房屋下的人员信息!";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionFrmTable + '?state=2&ids='
							+ $.farm.getCheckedIds(gridfrmtable, 'ID'), {},
							function(flag) {
								if (flag.pageset.commitType == 0) {
									$(gridfrmtable).datagrid('reload');
									$('#layout').layout('collapse', 'east');
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
	function beforeSelect(node) {
		//选中地址事件，但是不能够修改node否则会将改变应用到地址节点中
	}
</script>
</html>




