<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<input type="hidden" id="tableid" value="${tableid }"/>
<table class="easyui-datagrid" id="dom_datagridfrmfield">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="CNNAME" data-options="sortable:true" width="80"> 中文名 </th>
			<th field="ENNAME" data-options="sortable:true" width="80"> 英文名 </th>
			<th field="FILEDTYPE" data-options="sortable:true" width="80"> 字段类型 </th>
			<th field="LEN" data-options="sortable:true" width="80"> 长度 </th>
			<th field="LABELTYPE" data-options="sortable:true" width="80"> 标签类型 </th>
			<th field="INPUTTYPE" data-options="sortable:true" width="80"> 输入类型 </th>
			<th field="ISNULL" data-options="sortable:true" width="80"> 是否为空 </th>
			<th field="SORT" data-options="sortable:true" width="80"> 排序 </th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
	var url_delActionfrmfield = "admin/FrmFielddeleteCommit.do";//删除URL
	var url_formActionfrmfield = "admin/FrmFieldshow.do";//增加、修改、查看URL
	var url_previewFormActionfrmfield = "admin/FrmPreviewForm.do";//表单预览 
	var url_operateFormActionfrmfield = "admin/FrmOperateForm.do";//操作表单
	var url_searchActionfrmfield = "admin/FrmFieldqueryAll.do";//查询URL
	var title_windowfrmfield = "实体管理";//功能名称
	var gridfrmfield;//数据表格对象
	var searchfrmfield;//条件查询组件对象

	var TOOL_BARfrmfield;
	if(${pageset.pageType == 0}){
		TOOL_BARfrmfield = [ {
			id : 'view',
			text : '查看',
			iconCls : 'icon-tip',
			handler : viewDatafrmfield
		}, {
			id : 'preview',
			text : '预览表单',
			iconCls : 'icon-collaboration',
			handler : previewForm
		}];
	}else{
		TOOL_BARfrmfield = [ {
			id : 'view',
			text : '查看',
			iconCls : 'icon-tip',
			handler : viewDatafrmfield
		}, {
			id : 'add',
			text : '新增',
			iconCls : 'icon-add',
			handler : addDatafrmfield
		}, {
			id : 'edit',
			text : '修改',
			iconCls : 'icon-edit',
			handler : editDatafrmfield
		}, {
			id : 'del',
			text : '删除',
			iconCls : 'icon-remove',
			handler : delDatafrmfield
		}, {
			id : 'preview',
			text : '预览表单',
			iconCls : 'icon-collaboration',
			handler : previewForm
		}, {
			id : 'operate',
			text : '操作表单',
			iconCls : 'icon-report_edit',
			handler : operateForm
		}];
	}
	
	
	$(function() {
		//初始化数据表格
		gridfrmfield = $('#dom_datagridfrmfield').datagrid( {
			url : url_searchActionfrmfield + '?tableid='+$("#FrmTableIDVarId").val(),
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfrmfield,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		//初始化条件查询
		searchfrmfield = $('#dom_searchfrmfield').searchForm( {
			gridObj : gridfrmfield
		});
	});
	//查看
	function viewDatafrmfield() {
		var selectedArray = $(gridfrmfield).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfrmfield + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
					+ selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winfrmfield',
				width : 650,
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
	function addDatafrmfield() {
		var url = url_formActionfrmfield + '?pageset.pageType='+PAGETYPE.ADD+'&tableid=' + $("#FrmTableIDVarId").val();
		$.farm.openWindow( {
			id : 'winfrmfield',
			width : 650,
			height : 310,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatafrmfield() {
		var selectedArray = $(gridfrmfield).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfrmfield + '?pageset.pageType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID + '&tableid=' + $("#FrmTableIDVarId").val();
			$.farm.openWindow( {
				id : 'winfrmfield',
				width : 650,
				height : 310,
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
	function delDatafrmfield() {
		var selectedArray = $(gridfrmfield).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionfrmfield + '?ids=' + $.farm.getCheckedIds(gridfrmfield), {},
							function(flag) {
								if (flag.pageset.commitType == 0) {
									$(gridfrmfield).datagrid('reload');
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

	function previewForm(){
		var url = url_previewFormActionfrmfield + '?pageset.pageType='+PAGETYPE.ADD+'&tableid=' + $("#FrmTableIDVarId").val();
		$.farm.openWindow( {
			id : 'winfrmfield',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '表单预览'
		});
	}

	function operateForm(){
		var url = url_operateFormActionfrmfield + '?pageset.pageType='+PAGETYPE.ADD+'&tableid=' + $("#FrmTableIDVarId").val();
		$.farm.openWindow( {
			id : 'winfrmfield',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '操作表单'
		});
	}
</script>
		
			
										
										
