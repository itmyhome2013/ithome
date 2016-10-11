<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>人员基础信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout" id="layout">
		<div data-options="region:'north',border:false">
			
			<form id="dom_searchpersoninfo">
				<table class="editTable">
					<tr>
						<td class="title">
							人员姓名:
						</td>
						<td>
							<input name="NAME:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							手机号码:
						</td>
						<td>
							<input name="MOBILE:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							出生地址:
						</td>
						<td>
							<input name="ADDRESS:like" type="text" style="width: 180px;">
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
			<input type="hidden" id="personId" value="" />
			<table class="" id="dom_datagridperson">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						
						<th field="NAME" data-options="sortable:true" width="280">
							人员姓名
						</th>
						<th field="MOBILE" data-options="sortable:true" width="280">
							手机号码
						</th>
						<th field="ADDRESS" data-options="sortable:true" width="280">
							出生地址
						</th>
						<th field="NOTE" data-options="sortable:true" width="280">
							备注
						</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'east',collapsed:true,split:true,title:'书籍信息'" style="width: 500px">
			<jsp:include page="../mongo/personBookLayout.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionperson = "admin/PersonDeleteCommit.do";//删除URL
	var url_formActionperson = "admin/PersonAllshow.do";//增加、修改、查看URL
	var url_searchActionperson = "admin/PersonQueryAll.do";//查询URL
	var title_windowperson = "人口基础信息";//功能名称
	var gridperson;//数据表格对象
	var searchperson;//条件查询组件对象
	var TOOL_BARperson = [{
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataperson
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataperson
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataperson
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataperson
	}
	 ];
	
	$(function() {
		//初始化数据表格
		gridperson = $('#dom_datagridperson').datagrid( {
			url : url_searchActionperson,
			fit : true,
			toolbar : TOOL_BARperson,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : false,
			onLoadSuccess : function () {
				//$(this).datagrid("fixRownumber");
			},
			onClickRow : function(index, date) {
				$('#layout').layout('expand', 'east'); //展开
				$("#personId").val(date['ID']); //设置PersonID
				$("#PersonName").html(date['NAME']);
				gridbook = $('#dom_datagridbook')
						.datagrid(
								{
									url : "admin/PersonBookqueryAll.do?ids="+date['ID']+"",
									fit : true,
									'toolbar' : TOOL_BARbook,
									pagination : true,
									closable : true,
									checkOnSelect : true,
									striped : true,
									rownumbers : true,
									ctrlSelect : true,
									fitColumns : false
								});
				
			}
		});
		//初始化条件查询
		searchperson = $('#dom_searchpersoninfo').searchForm( {
			gridObj : gridperson
		});
	});


	//查看
	function viewDataperson() {
		var selectedArray = $(gridperson).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionperson + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winperson',
				width : 750,
				height : 200,
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
	function addDataperson() {
		var url = url_formActionperson + '?pageset.pageType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winperson',
			width : 750,
			height : 200,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataperson() {
		var selectedArray = $(gridperson).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionperson + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow( {
				id : 'winperson',
				width : 750,
				height : 200,
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
	function delDataperson() {
		var selectedArray = $(gridperson).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionperson
							+ '?ids='
							+ $.farm.getCheckedIds(gridperson,
									'ID'), {}, function(flag) {
						if (flag.pageset.commitType == 0) {
							$(gridperson).datagrid('reload');
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




