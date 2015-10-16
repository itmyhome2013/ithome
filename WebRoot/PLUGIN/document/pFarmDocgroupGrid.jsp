<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--工作小组选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfarmdocgroup">
			<table class="editTable">
				<tr>
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
		<table class="easyui-datagrid" id="dom_choosegridfarmdocgroup">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="PSTATE" data-options="sortable:true" width="80"> 状态 </th>
					<th field="PCONTENT" data-options="sortable:true" width="80"> 备注 </th>
					<th field="GROUPNAME" data-options="sortable:true" width="80"> 小组名称 </th>
					<th field="GROUPNOTE" data-options="sortable:true" width="80"> 小组介绍 </th>
					<th field="GROUPTAG" data-options="sortable:true" width="80"> 小组标签 </th>
					<th field="GROUPIMG" data-options="sortable:true" width="80"> 小组头像 </th>
					<th field="JOINCHECK" data-options="sortable:true" width="80"> 加入时验证 </th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var choosegridfarmdocgroup;
	var chooseSearchfarmdocgroup;
	var toolbar_choosefarmdocgroup = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfarmdocgroup').datagrid(
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
		choosegridfarmdocgroup = $('#dom_choosegridfarmdocgroup').datagrid( {
			url : 'admin/FarmDocgroupqueryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefarmdocgroup,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmdocgroup = $('#dom_chooseSearchfarmdocgroup').searchForm( {
			gridObj : choosegridfarmdocgroup
		});
	});
	//-->
</script>
<!-- 
<a id="form_farmdocgroup_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_farmdocgroup_a_Choose').bindChooseWindow('chooseWinfarmdocgroup', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FarmDocgroup_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








