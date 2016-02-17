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
			<form id="dom_searchfrmsqu">
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
								num:
							</td>
							<td>
								<input name="NUM:=" type="text" >
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
			<table class="easyui-datagrid" id="dom_datagridfrmsqu">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="ID" data-options="sortable:true" width="80"> id </th>
						<th field="TYPE" data-options="sortable:true" width="80"> type </th>
						<th field="NUM" data-options="sortable:true" width="80"> num </th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
		var url_delActionfrmsqu = "admin/FrmSqudeleteCommit.do";//删除URL
		var url_formActionfrmsqu = "admin/FrmSqushow.do";//增加、修改、查看URL
		var url_searchActionfrmsqu = "admin/FrmSququeryAll.do";//查询URL
		var title_windowfrmsqu = "实体管理";//功能名称
		var gridfrmsqu;//数据表格对象
		var searchfrmsqu;//条件查询组件对象
		var TOOL_BARfrmsqu = [ {
			id : 'view',
			text : '查看',
			iconCls : 'icon-tip',
			handler : viewDatafrmsqu
		}, {
			id : 'add',
			text : '新增',
			iconCls : 'icon-add',
			handler : addDatafrmsqu
		}, {
			id : 'edit',
			text : '修改',
			iconCls : 'icon-edit',
			handler : editDatafrmsqu
		}, {
			id : 'del',
			text : '删除',
			iconCls : 'icon-remove',
			handler : delDatafrmsqu
		} ];
		$(function() {
			//初始化数据表格
			gridfrmsqu = $('#dom_datagridfrmsqu').datagrid( {
				url : url_searchActionfrmsqu,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARfrmsqu,
				pagination : true,
				closable : true,
				checkOnSelect : true,
				striped : true,
				rownumbers : true,
				ctrlSelect : true,
				fitColumns : true
			});
			//初始化条件查询
			searchfrmsqu = $('#dom_searchfrmsqu').searchForm( {
				gridObj : gridfrmsqu
			});
		});
		//查看
		function viewDatafrmsqu() {
			var selectedArray = $(gridfrmsqu).datagrid('getSelections');
			if (selectedArray.length > 0) {
				var url = url_formActionfrmsqu + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
						+ selectedArray[0].ID;
				$.farm.openWindow( {
					id : 'winfrmsqu',
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
		function addDatafrmsqu() {
			var url = url_formActionfrmsqu + '?pageset.pageType='+PAGETYPE.ADD;
			$.farm.openWindow( {
				id : 'winfrmsqu',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '新增'
			});
		}
		//修改
		function editDatafrmsqu() {
			var selectedArray = $(gridfrmsqu).datagrid('getSelections');
			if (selectedArray.length > 0) {
				var url = url_formActionfrmsqu + '?pageset.pageType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;;
				$.farm.openWindow( {
					id : 'winfrmsqu',
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
		function delDatafrmsqu() {
			var selectedArray = $(gridfrmsqu).datagrid('getSelections');
			if (selectedArray.length > 0) {
				// 有数据执行操作
				var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
				$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
					if (flag) {
						$.post(url_delActionfrmsqu + '?ids=' + $.farm.getCheckedIds(gridfrmsqu), {},
								function(flag) {
									if (flag.pageset.commitType == 0) {
										$(gridfrmsqu).datagrid('reload');
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
		
			
										
										
