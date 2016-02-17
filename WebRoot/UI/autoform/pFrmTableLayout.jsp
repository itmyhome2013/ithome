<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>实体管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchfrmtable">
				<table class="editTable">
						<tr>
							<td class="title">
								id:
							</td>
							<td>
								<input name="ID:=" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								enname:
							</td>
							<td>
								<input name="ENNAME:like" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								cnname:
							</td>
							<td>
								<input name="CNNAME:like" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								state:
							</td>
							<td>
								<input name="STATE:=" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								type:
							</td>
							<td>
								<input name="TYPE:=" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								url:
							</td>
							<td>
								<input name="URL:like" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								owner:
							</td>
							<td>
								<input name="OWNER:=" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								flag:
							</td>
							<td>
								<input name="FLAG:=" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								cretime:
							</td>
							<td>
								<input name="CRETIME:like" type="text" >
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
			<table class="easyui-datagrid" id="dom_datagridfrmtable">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="ID" data-options="sortable:true" width="80"> id </th>
						<th field="ENNAME" data-options="sortable:true" width="80"> enname </th>
						<th field="CNNAME" data-options="sortable:true" width="80"> cnname </th>
						<th field="STATE" data-options="sortable:true" width="80"> state </th>
						<th field="TYPE" data-options="sortable:true" width="80"> type </th>
						<th field="URL" data-options="sortable:true" width="80"> url </th>
						<th field="SORT" data-options="sortable:true" width="80"> sort </th>
						<th field="OWNER" data-options="sortable:true" width="80"> owner </th>
						<th field="FLAG" data-options="sortable:true" width="80"> flag </th>
						<th field="CRETIME" data-options="sortable:true" width="80"> cretime </th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
		var url_delActionfrmtable = "admin/FrmTabledeleteCommit.do";//删除URL
		var url_formActionfrmtable = "admin/FrmTableshow.do";//增加、修改、查看URL
		var url_searchActionfrmtable = "admin/FrmTablequeryAll.do";//查询URL
		var title_windowfrmtable = "实体管理";//功能名称
		var gridfrmtable;//数据表格对象
		var searchfrmtable;//条件查询组件对象
		var TOOL_BARfrmtable = [ {
			id : 'view',
			text : '查看',
			iconCls : 'icon-tip',
			handler : viewDatafrmtable
		}, {
			id : 'add',
			text : '新增',
			iconCls : 'icon-add',
			handler : addDatafrmtable
		}, {
			id : 'edit',
			text : '修改',
			iconCls : 'icon-edit',
			handler : editDatafrmtable
		}, {
			id : 'del',
			text : '删除',
			iconCls : 'icon-remove',
			handler : delDatafrmtable
		} ];
		$(function() {
			//初始化数据表格
			gridfrmtable = $('#dom_datagridfrmtable').datagrid( {
				url : url_searchActionfrmtable,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARfrmtable,
				pagination : true,
				closable : true,
				checkOnSelect : true,
				striped : true,
				rownumbers : true,
				ctrlSelect : true,
				fitColumns : true
			});
			//初始化条件查询
			searchfrmtable = $('#dom_searchfrmtable').searchForm( {
				gridObj : gridfrmtable
			});
		});
		//查看
		function viewDatafrmtable() {
			var selectedArray = $(gridfrmtable).datagrid('getSelections');
			if (selectedArray.length > 0) {
				var url = url_formActionfrmtable + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
						+ selectedArray[0].ID;
				$.farm.openWindow( {
					id : 'winfrmtable',
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
		function addDatafrmtable() {
			var url = url_formActionfrmtable + '?pageset.pageType='+PAGETYPE.ADD;
			$.farm.openWindow( {
				id : 'winfrmtable',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '新增'
			});
		}
		//修改
		function editDatafrmtable() {
			var selectedArray = $(gridfrmtable).datagrid('getSelections');
			if (selectedArray.length > 0) {
				var url = url_formActionfrmtable + '?pageset.pageType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;;
				$.farm.openWindow( {
					id : 'winfrmtable',
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
		function delDatafrmtable() {
			var selectedArray = $(gridfrmtable).datagrid('getSelections');
			if (selectedArray.length > 0) {
				// 有数据执行操作
				var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
				$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
					if (flag) {
						$.post(url_delActionfrmtable + '?ids=' + $.farm.getCheckedIds(gridfrmtable), {},
								function(flag) {
									if (flag.pageset.commitType == 0) {
										$(gridfrmtable).datagrid('reload');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ flag.pageset.message;
								$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
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
		
			
										
										
