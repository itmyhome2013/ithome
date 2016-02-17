<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfrmfieldrel">
			<table class="editTable">
				<tr>
					<td class="title">
						id:
					</td>
					<td>
						<input name="ID:=" type="text" >
					</td>
					<td class="title">
						fieldid:
					</td>
					<td>
						<input name="FIELDID:=" type="text" >
					</td>
					<td class="title">
						fieldname:
					</td>
					<td>
						<input name="FIELDNAME:like" type="text" >
					</td>
					<td class="title">
						tablename:
					</td>
					<td>
						<input name="TABLENAME:like" type="text" >
					</td>
					<td class="title">
						condition:
					</td>
					<td>
						<input name="CONDITION:like" type="text" >
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
		<table class="easyui-datagrid" id="dom_choosegridfrmfieldrel">
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
</div>
<script type="text/javascript">
	var choosegridfrmfieldrel;
	var chooseSearchfrmfieldrel;
	var toolbar_choosefrmfieldrel = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfrmfieldrel').datagrid(
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
		choosegridfrmfieldrel = $('#dom_choosegridfrmfieldrel').datagrid( {
			url : 'admin/FrmFieldRelqueryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefrmfieldrel,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfrmfieldrel = $('#dom_chooseSearchfrmfieldrel').searchForm( {
			gridObj : choosegridfrmfieldrel
		});
	});
	//-->
</script>
<!-- 
<a id="form_frmfieldrel_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_frmfieldrel_a_Choose').bindChooseWindow('chooseWinfrmfieldrel', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FrmFieldRel_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








