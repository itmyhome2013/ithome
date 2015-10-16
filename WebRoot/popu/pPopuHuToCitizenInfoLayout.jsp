<%@ page language="java" pageEncoding="utf-8"%>
<div id="cc" class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false"
		style="height: 80px;">
		<div class="demo-info" style="margin: 4px; height: auto">
			<div class="demo-tip"></div>
			<div>
				
				户主姓名：
				<span id="HUNAME"></span>
				<br />
				<span id="currentLIVESADDRESS"></span>&nbsp;&nbsp;&nbsp;
				
			</div>
		</div>
	</div>
	<input type="hidden" id="liveaddress" name="liveaddress" value="测试" />
	<div data-options="region:'center',border:false">
		<table class="" id="dom_datagridpopucitizeninfo"
			data-options="border:false">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="CITIZENNAME" data-options="sortable:true" width="70">
						姓名
					</th>
					<th field="CITIZENBIRTH" data-options="sortable:true" width="80">
						出生日期
					</th>
					<th field="CITIZENMOBILE" data-options="sortable:true" width="80">
						手机号码
					</th>
					<th field="CITIZENHURELATION" data-options="sortable:true" width="100">
						与户主关系
					</th>
					<th field="CITIZENHUQUALITY" data-options="sortable:true" width="80">
						户口性质
					</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
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
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatapopucitizeninfo
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
	} ];
	$(function() {
		//初始化数据表格
		//gridpopucitizeninfo = $('#dom_datagridpopucitizeninfo').datagrid( {
		//	url : url_searchActionpopucitizeninfo,
		//	fit : true,
		//	'toolbar' : TOOL_BARpopucitizeninfo,
		//	pagination : true,
		//	closable : true,
		//	checkOnSelect : true,
		//	striped : true,
		//	rownumbers : true,
		//	ctrlSelect : true,
		//	fitColumns : false
		//});
		//初始化条件查询
		//searchpopucitizeninfo = $('#dom_searchpopucitizeninfo').searchForm( {
		//	gridObj : gridpopucitizeninfo
		//});
	});
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
		var ids = $("#ids").val();
		var liveaddress = $("#currentLIVESADDRESS").html();
		var url = url_formActionpopucitizeninfo + '?pageset.pageType='
				+ PAGETYPE.ADD + '&huid=' + ids + '';
		$.farm.openWindow( {
			id : 'winpopucitizeninfo',
			width : 800,
			height : 480,
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




