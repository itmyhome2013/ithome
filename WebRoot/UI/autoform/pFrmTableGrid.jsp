<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfrmtable">
			<table class="editTable">
				<tr>
					<td class="title">
						id:
					</td>
					<td>
						<input name="ID:=" type="text" >
					</td>
					<td class="title">
						enname:
					</td>
					<td>
						<input name="ENNAME:like" type="text" >
					</td>
					<td class="title">
						cnname:
					</td>
					<td>
						<input name="CNNAME:like" type="text" >
					</td>
					<td class="title">
						state:
					</td>
					<td>
						<input name="STATE:=" type="text" >
					</td>
					<td class="title">
						type:
					</td>
					<td>
						<input name="TYPE:=" type="text" >
					</td>
					<td class="title">
						url:
					</td>
					<td>
						<input name="URL:like" type="text" >
					</td>
					<td class="title">
						owner:
					</td>
					<td>
						<input name="OWNER:=" type="text" >
					</td>
					<td class="title">
						flag:
					</td>
					<td>
						<input name="FLAG:=" type="text" >
					</td>
					<td class="title">
						cretime:
					</td>
					<td>
						<input name="CRETIME:like" type="text" >
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
		<table class="easyui-datagrid" id="dom_choosegridfrmtable">
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
</div>
<script type="text/javascript">
	var choosegridfrmtable;
	var chooseSearchfrmtable;
	var toolbar_choosefrmtable = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfrmtable').datagrid(
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
		choosegridfrmtable = $('#dom_choosegridfrmtable').datagrid( {
			url : 'admin/FrmTablequeryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefrmtable,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfrmtable = $('#dom_chooseSearchfrmtable').searchForm( {
			gridObj : choosegridfrmtable
		});
	});
	//-->
</script>
<!-- 
<a id="form_frmtable_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_frmtable_a_Choose').bindChooseWindow('chooseWinfrmtable', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FrmTable_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








