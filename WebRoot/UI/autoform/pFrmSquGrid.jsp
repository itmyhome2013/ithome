<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfrmsqu">
			<table class="editTable">
				<tr>
					<td class="title">
						id:
					</td>
					<td>
						<input name="ID:=" type="text" >
					</td>
					<td class="title">
						type:
					</td>
					<td>
						<input name="TYPE:=" type="text" >
					</td>
					<td class="title">
						num:
					</td>
					<td>
						<input name="NUM:=" type="text" >
					</td>
					<td class="title">
						<a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a>
					</td>
					<td>
						<a id="a_reset" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_choosegridfrmsqu">
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
</div>
<script type="text/javascript">
	var choosegridfrmsqu;
	var chooseSearchfrmsqu;
	var toolbar_choosefrmsqu = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfrmsqu').datagrid(
					'getSelections');
			if (selectedArray.length > 0) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
	} ];
	$(function() {
		choosegridfrmsqu = $('#dom_choosegridfrmsqu').datagrid( {
			url : 'admin/FrmSququeryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefrmsqu,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfrmsqu = $('#dom_chooseSearchfrmsqu').searchForm( {
			gridObj : choosegridfrmsqu
		});
	});
	//-->
</script>
<!-- 
<a id="form_frmsqu_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_frmsqu_a_Choose').bindChooseWindow('chooseWinfrmsqu', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FrmSqu_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








