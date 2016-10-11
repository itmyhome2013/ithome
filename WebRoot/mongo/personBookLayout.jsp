<%@ page language="java" pageEncoding="utf-8"%>
<div id="cc" class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false"
		style="height: 90px;">
		<div class="demo-info" style="margin: 4px; height: auto">
			<div class="demo-tip"></div>
			<div>
				
				人员姓名：
				<span id="PersonName"></span>
				</br>
				</br>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table class="" id="dom_datagridbook"
			data-options="border:false">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="BOOKNAME" data-options="sortable:true" width="90">
						书名
					</th>
					<th field="AUTHOR" data-options="sortable:true" width="60">
						作者
					</th>
					<th field="PRESS" data-options="sortable:true" width="100">
						出版社
					</th>
					<th field="ISBN" data-options="sortable:true" width="80">
						ISBN号
					</th>
					<th field="NAME" data-options="sortable:true" width="80">
						人员姓名
					</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionbook = "admin/BookDeleteCommit.do";//删除URL
	var url_formActionbook = "admin/Bookshow.do";//增加、修改、查看URL
	var title_windowbook = "人口基础信息";//功能名称
	var gridbook;//数据表格对象
	var searchbook;//条件查询组件对象
	var TOOL_BARbook = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatabook
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatabook
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatabook
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatabook
	} ];
	$(function() {
		
	});
	//查看
	function viewDatabook() {
		var selectedArray = $(gridbook).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionbook + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winbook',
				width : 750,
				height : 280,
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
	function addDatabook() {
		var personId = $("#personId").val();
		var url = url_formActionbook + '?pageset.pageType='
				+ PAGETYPE.ADD + '&personId=' + personId + '';
		$.farm.openWindow( {
			id : 'winbook',
			width : 750,
			height : 280,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatabook() {
		var selectedArray = $(gridbook).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionbook + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow( {
				id : 'winbook',
				width : 750,
				height : 280,
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
	function delDatabook() {
		var selectedArray = $(gridbook).datagrid('getSelections');
		var personId = $("#personId").val();
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionbook
							+ '?personId=' + personId + '&ids='
							+ $.farm.getCheckedIds(gridbook,
									'ID'), {}, function(flag) {
						if (flag.pageset.commitType == 0) {
							$(gridbook).datagrid('reload');
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




