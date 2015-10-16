<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>人口基础信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_report_search" action="admin/exportRepotcitizenInfo.do"
				method="post">
				<input type="hidden" name="query.ruleText" id="query_ruleText">
			</form>
			<form id="dom_searchpopucitizeninfo">
				<table class="editTable">
					<tr>
						<td class="title">
							姓名:
						</td>
						<td>
							<input name="CITIZENNAME:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							性别:
						</td>
						<td>
							<select name="CITIZENSEX:=" style="width: 180px;">
								<option value="">
									请选择
								</option>
								<PF:OptionDictionary index="SEX" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							证件编号:
						</td>
						<td>
							<input name="CITIZENIDENTITY:like" type="text" style="width: 180px;">
						</td>
					</tr>
					<tr>
						<td class="title">
							出生日期:
						</td>
						<td>
							<input name="CITIZENBIRTH:>=" type="text" style="width: 85px;"
								class="easyui-datebox">
							-
							<input name="CITIZENBIRTH:<=" type=" text" style="width: 85px;"
								class="easyui-datebox">
						</td>
						<td class="title">
							手机号码:
						</td>
						<td>
							<input name="CITIZENMOBILE:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							户口性质:
						</td>
						<td>
							<select name="CITIZENHUQUALITY:=" style="width: 180px;">
								<option value="">
									请选择
								</option>
								<PF:OptionDictionary index="HUQUALE" isTextValue="0" />
							</select>
						</td>
					</tr>
					<tr>
						<td class="title">
							户籍状况:
						</td>
						<td>
							<select name="CITIZENHUSTATE:=" style="width: 180px;">
								<option value="">
									请选择
								</option>
								<PF:OptionDictionary index="HUSTATE" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							管理类型:
						</td>
						<td>
							<select name="CITIZENMANAGERKIND:=" style="width: 180px;">
								<option value="">
									请选择
								</option>
								<PF:OptionDictionary index="HUMANAGERKIND" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							户籍地址:
						</td>
						<td>
							<input name="CITIZENNATIVEADDRESS:like" type="text" style="width: 180px;">
						</td>
						
					</tr>
					<tr>
						<td class="title">
							户籍非户籍:
						</td>
						<td>
							<select name="CITIZENFLOATINGPOPULATION:=" style="width: 180px;">
								<option value="">
									请选择
								</option>
								<PF:OptionDictionary index="HUFLOATINGPOPULATION" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							房屋地址:
						</td>
						<td colspan="3">
							<select name="HUFLOOR:like-" class="easyui-combotree"
								style="width: 180px;"
								data-options="url:'admin/ALONEDictionaryTree_loadTree.do?index=ADDRESS_TREE',onBeforeSelect:beforeSelect">
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
			<table class="" id="dom_datagridpopucitizeninfo">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="LIVESADDRESS" data-options="sortable:true" width="320">
							房屋地址
						</th>
						<th field="CITIZENNAME" data-options="sortable:true" width="100">
							姓名
						</th>
						<th field="CITIZENSEX" data-options="sortable:true" width="100">
							性别
						</th>
						<th field="CITIZENBIRTH" data-options="sortable:true" width="100">
							出生日期
						</th>
						
						<th field="CITIZENIDENTITY" data-options="sortable:true" width="150">
							证件编号
						</th>
						<th field="CITIZENMOBILE" data-options="sortable:true" width="120">
							手机号码
						</th>
						
						<th field="CITIZENHUQUALITY" data-options="sortable:true" width="120">
							户口性质
						</th>
						<th field="CITIZENHUSTATE" data-options="sortable:true" width="120">
							户籍状况
						</th>
						<th field="CITIZENNATIVEADDRESS" data-options="sortable:true" width="120">
							户籍地址
						</th>
						
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionpopucitizeninfo = "admin/PopuCitizenInfodeleteCommit.do";//删除URL
	var url_formActionpopucitizeninfo = "admin/PopuCitizenInfoAllshow.do";//增加、修改、查看URL
	var url_searchActionpopucitizeninfo = "admin/PopuCitizenInfoqueryAll.do";//查询URL
	var title_windowpopucitizeninfo = "人口基础信息";//功能名称
	var gridpopucitizeninfo;//数据表格对象
	var searchpopucitizeninfo;//条件查询组件对象
	var TOOL_BARpopucitizeninfo = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatapopucitizeninfo
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatapopucitizeninfo
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatapopucitizeninfo
	}
	//, {
	//	id : 'export',
	//	text : '导出',
	//	iconCls : 'icon-report',
	//	handler : exportDatapopucitizeninfo
	//} 
	 ];
	//, {
	//	id : 'add',
	//	text : '新增',
	//	iconCls : 'icon-add',
	//	handler : addDatapopucitizeninfo
	//}
	$(function() {
		//初始化数据表格
		gridpopucitizeninfo = $('#dom_datagridpopucitizeninfo').datagrid( {
			url : url_searchActionpopucitizeninfo,
			fit : true,
			toolbar : TOOL_BARpopucitizeninfo,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : false,
			onLoadSuccess : function () {
				$(this).datagrid("fixRownumber");
			}
		});
		//初始化条件查询
		searchpopucitizeninfo = $('#dom_searchpopucitizeninfo').searchForm( {
			gridObj : gridpopucitizeninfo
		});
	});
	//导出报表
	function exportDatapopucitizeninfo() {
		var str = $(searchpopucitizeninfo).searchForm().arrayStr();
		$('#query_ruleText').val(str);
		$('#dom_report_search').submit();
	}
	function beforeSelect(node){
		//选中地址事件，但是不能够修改node否则会将改变应用到地址节点中
	}
	//查看
	function viewDatapopucitizeninfo() {
		var selectedArray = $(gridpopucitizeninfo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionpopucitizeninfo + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].CITIZENID;
			$.farm.openWindow( {
				id : 'winpopucitizeninfo',
				width : 800,
				height : 500,
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
	function addDatapopucitizeninfo() {
		var url = url_formActionpopucitizeninfo + '?pageset.pageType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winpopucitizeninfo',
			width : 800,
			height : 500,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatapopucitizeninfo() {
		var selectedArray = $(gridpopucitizeninfo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionpopucitizeninfo + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].CITIZENID;
			;
			$.farm.openWindow( {
				id : 'winpopucitizeninfo',
				width : 800,
				height : 500,
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
	function delDatapopucitizeninfo() {
		var selectedArray = $(gridpopucitizeninfo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionpopucitizeninfo
							+ '?ids='
							+ $.farm.getCheckedIds(gridpopucitizeninfo,
									'CITIZENID'), {}, function(flag) {
						if (flag.pageset.commitType == 0) {
							$(gridpopucitizeninfo).datagrid('reload');
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




