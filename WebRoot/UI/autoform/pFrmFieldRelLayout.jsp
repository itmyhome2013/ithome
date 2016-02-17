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
			<form id="dom_searchfrmfieldrel">
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
								fieldid:
							</td>
							<td>
								<input name="FIELDID:=" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								fieldname:
							</td>
							<td>
								<input name="FIELDNAME:like" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								tablename:
							</td>
							<td>
								<input name="TABLENAME:like" type="text" >
							</td>
							<td class="title">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td class="title">
								condition:
							</td>
							<td>
								<input name="CONDITION:like" type="text" >
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
			<table class="easyui-datagrid" id="dom_datagridfrmfieldrel">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="ID" data-options="sortable:true" width="80"> id </th>
						<th field="FIELDID" data-options="sortable:true" width="80"> fieldid </th>
						<th field="FIELDNAME" data-options="sortable:true" width="80"> fieldname </th>
						<th field="TABLENAME" data-options="sortable:true" width="80"> tablename </th>
						<th field="CONDITION" data-options="sortable:true" width="80"> condition </th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
		var url_delActionfrmfieldrel = "admin/FrmFieldReldeleteCommit.do";//删除URL
		var url_formActionfrmfieldrel = "admin/FrmFieldRelshow.do";//增加、修改、查看URL
		var url_searchActionfrmfieldrel = "admin/FrmFieldRelqueryAll.do";//查询URL
		var title_windowfrmfieldrel = "实体管理";//功能名称
		var gridfrmfieldrel;//数据表格对象
		var searchfrmfieldrel;//条件查询组件对象
		var TOOL_BARfrmfieldrel = [ {
			id : 'view',
			text : '查看',
			iconCls : 'icon-tip',
			handler : viewDatafrmfieldrel
		}, {
			id : 'add',
			text : '新增',
			iconCls : 'icon-add',
			handler : addDatafrmfieldrel
		}, {
			id : 'edit',
			text : '修改',
			iconCls : 'icon-edit',
			handler : editDatafrmfieldrel
		}, {
			id : 'del',
			text : '删除',
			iconCls : 'icon-remove',
			handler : delDatafrmfieldrel
		} ];
		$(function() {
			//初始化数据表格
			gridfrmfieldrel = $('#dom_datagridfrmfieldrel').datagrid( {
				url : url_searchActionfrmfieldrel,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARfrmfieldrel,
				pagination : true,
				closable : true,
				checkOnSelect : true,
				striped : true,
				rownumbers : true,
				ctrlSelect : true,
				fitColumns : true
			});
			//初始化条件查询
			searchfrmfieldrel = $('#dom_searchfrmfieldrel').searchForm( {
				gridObj : gridfrmfieldrel
			});
		});
		//查看
		function viewDatafrmfieldrel() {
			var selectedArray = $(gridfrmfieldrel).datagrid('getSelections');
			if (selectedArray.length > 0) {
				var url = url_formActionfrmfieldrel + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
						+ selectedArray[0].ID;
				$.farm.openWindow( {
					id : 'winfrmfieldrel',
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
		function addDatafrmfieldrel() {
			var url = url_formActionfrmfieldrel + '?pageset.pageType='+PAGETYPE.ADD;
			$.farm.openWindow( {
				id : 'winfrmfieldrel',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '新增'
			});
		}
		//修改
		function editDatafrmfieldrel() {
			var selectedArray = $(gridfrmfieldrel).datagrid('getSelections');
			if (selectedArray.length > 0) {
				var url = url_formActionfrmfieldrel + '?pageset.pageType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;;
				$.farm.openWindow( {
					id : 'winfrmfieldrel',
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
		function delDatafrmfieldrel() {
			var selectedArray = $(gridfrmfieldrel).datagrid('getSelections');
			if (selectedArray.length > 0) {
				// 有数据执行操作
				var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
				$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
					if (flag) {
						$.post(url_delActionfrmfieldrel + '?ids=' + $.farm.getCheckedIds(gridfrmfieldrel), {},
								function(flag) {
									if (flag.pageset.commitType == 0) {
										$(gridfrmfieldrel).datagrid('reload');
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
		
			
										
										
