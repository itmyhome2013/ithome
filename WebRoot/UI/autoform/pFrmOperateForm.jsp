<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_choosegridfrmoperateform">
			<PF:GenerateHead tableId="${tableid}"/>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_formActionfrmoperateform = "admin/FrmOperateFieldshow.do";//增加、修改、查看URL
	var url_delActionfrmoperateform = "admin/FrmOperateFielddeleteCommit.do";//删除URL
	var choosegridfrmoperateform;
	var chooseSearchfrmoperateform;
	var toolbar_choosefrmoperateform = [ {
		text : '查看',
		iconCls : 'icon-tip',
		handler : function() {
			var selectedArray = $('#dom_choosegridfrmoperateform').datagrid(
					'getSelections');
			if (selectedArray.length > 0) {

				var url = url_formActionfrmoperateform + '?pageset.pageType='+PAGETYPE.VIEW+'&tableid=' + $("#FrmTableIDVarId").val()+'&ids=' + selectedArray[0].ID;
				$.farm.openWindow( {
					id : 'winfrmoperateform',
					width : 600,
					height : 300,
					modal : true,
					url : url,
					title : '查看'
				});
				
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
	},{
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			var url = url_formActionfrmoperateform + '?pageset.pageType='+PAGETYPE.ADD+'&tableid=' + $("#FrmTableIDVarId").val();
			$.farm.openWindow( {
				id : 'winfrmoperateform',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '新增'
			});
		}
	} ,{
		text : '修改',
		iconCls : 'icon-edit',
		handler : function() {
			var selectedArray = $('#dom_choosegridfrmoperateform').datagrid(
					'getSelections');
			console.info(selectedArray[0]);
			if (selectedArray.length > 0) {

				var url = url_formActionfrmoperateform + '?pageset.pageType='+PAGETYPE.EDIT+'&tableid=' + $("#FrmTableIDVarId").val()+'&ids=' + selectedArray[0].ID;
				$.farm.openWindow( {
					id : 'winfrmoperateform',
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
	},{
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {

			var selectedArray = $('#dom_choosegridfrmoperateform').datagrid('getSelections');
			if (selectedArray.length > 0) {
				// 有数据执行操作
				var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
				$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
					if (flag) {
						$.post(url_delActionfrmoperateform + '?ids=' + $.farm.getCheckedIds('#dom_choosegridfrmoperateform') + "&tableid=" + $("#FrmTableIDVarId").val(), {},
								function(flag) {
									if (flag.pageset.commitType == 0) {
										$('#dom_choosegridfrmoperateform').datagrid('reload');
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
	}];
	$(function() {
		choosegridfrmoperateform = $('#dom_choosegridfrmoperateform').datagrid( {
			url : 'admin/FrmOperateFormqueryAll.do?tableid='+"${tableid}",
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefrmoperateform,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfrmoperateform = $('#dom_chooseSearchfrmfield').searchForm( {
			gridObj : choosegridfrmoperateform
		});
	});
	//-->
</script>
<!-- 
<a id="form_frmfield_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_frmfield_a_Choose').bindChooseWindow('chooseWinfrmfield', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FrmField_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








