<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>流程表单配置信息</title>
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
							任务节点KEY:
						</td>
						<td>
							<input name="TASKKEY:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							表单名称:
						</td>
						<td>
							<input name="FORMNAME:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							表单类型:
						</td>
						<td>
							<select name="FROMTYPE:like" class=""  style="width: 180px">
								<option value="">请选择</option>
								<PF:OptionDictionary index="FORMTYPE" isTextValue="0" />
							</select>
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
						<th field="TASKKEY" data-options="sortable:true" width="60">
							任务节点KEY
						</th>
						<th field="TASKNAME" data-options="sortable:true" width="50">
							任务节点名称
						</th>
						<th field="FORMNAME" data-options="sortable:true" width="60">
							表单名称
						</th>
						<th field="FROMTYPE" data-options="sortable:true" width="50">
							表单类型
						</th>
						<th field="FORMURL" data-options="sortable:true" width="120">
							表单URL
						</th>
						<!-- 
						<th field="WORD" data-options="sortable:true" width="120">
							Word
						</th>
						 -->
						 <th field="FORMTABLENAME" data-options="sortable:true" width="50">
							表名
						</th>
						<th field="ISREQUIRED" data-options="sortable:true" width="50">
							是否必填
						</th>
						<th field="ISDISABLE" data-options="sortable:true" width="50">
							是否禁用
						</th>
						
					
					</tr>
				</thead>
			</table>
		</div>
		
	</body>
	<script type="text/javascript">
	var url_delActionFrmTable = "admin/FrmProcessFormdeleteCommit.do";//删除URL
	var url_formActionFrmTable = "admin/FrmProcessFormshow.do";//增加、修改、查看URL
	var url_searchActionFrmTable = "admin/FrmProcessFormqueryAll.do";//查询URL
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




