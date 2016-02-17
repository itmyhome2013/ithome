<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfrmfield">
			<table class="editTable">
				<tr>
					<td class="title">
						编号:
					</td>
					<td>
						<input name="ID:=" type="text" >
					</td>
					<td class="title">
						表ID:
					</td>
					<td>
						<input name="TABLEID:=" type="text" >
					</td>
					<td class="title">
						中文名:
					</td>
					<td>
						<input name="CNNAME:like" type="text" >
					</td>
					<td class="title">
						英文名:
					</td>
					<td>
						<input name="ENNAME:like" type="text" >
					</td>
					<td class="title">
						字段类型:
					</td>
					<td>
						<input name="FILEDTYPE:like" type="text" >
					</td>
					<td class="title">
						长度:
					</td>
					<td>
						<input name="LEN:=" type="text" >
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
		<table class="easyui-datagrid" id="dom_choosegridfrmfield">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="ID" data-options="sortable:true" width="80"> 编号 </th>
					<th field="TABLEID" data-options="sortable:true" width="80"> 表ID </th>
					<th field="CNNAME" data-options="sortable:true" width="80"> 中文名 </th>
					<th field="ENNAME" data-options="sortable:true" width="80"> 英文名 </th>
					<th field="FILEDTYPE" data-options="sortable:true" width="80"> 字段类型 </th>
					<th field="LEN" data-options="sortable:true" width="80"> 长度 </th>
					<th field="SORT" data-options="sortable:true" width="80"> 排序 </th>
					<th field="INPUTTYPE" data-options="sortable:true" width="80"> inputtype </th>
					<th field="STATE" data-options="sortable:true" width="80"> state </th>
					<th field="CRETIME" data-options="sortable:true" width="80"> cretime </th>
					<th field="UPDTIME" data-options="sortable:true" width="80"> updtime </th>
					<th field="ISREL" data-options="sortable:true" width="80"> isrel </th>
					<th field="ISNULL" data-options="sortable:true" width="80"> isnull </th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var choosegridfrmfield;
	var chooseSearchfrmfield;
	var toolbar_choosefrmfield = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfrmfield').datagrid(
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
		choosegridfrmfield = $('#dom_choosegridfrmfield').datagrid( {
			url : 'admin/FrmFieldqueryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefrmfield,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfrmfield = $('#dom_chooseSearchfrmfield').searchForm( {
			gridObj : choosegridfrmfield
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








