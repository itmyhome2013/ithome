<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--工作小组成员选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfarmdocgroupuser">
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
		<table class="easyui-datagrid" id="dom_choosegridfarmdocgroupuser">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="PSTATE" data-options="sortable:true" width="80"> 状态 </th>
					<th field="PCONTENT" data-options="sortable:true" width="80"> 备注 </th>
					<th field="GROUPID" data-options="sortable:true" width="80"> 工作小组ID </th>
					<th field="USERID" data-options="sortable:true" width="80"> 用户ID </th>
					<th field="LEADIS" data-options="sortable:true" width="80"> 是否小组管理员 </th>
					<th field="EDITIS" data-options="sortable:true" width="80"> 修改权限 </th>
					<th field="SHOWHOME" data-options="sortable:true" width="80"> 是否首页显示 </th>
					<th field="SHOWSORT" data-options="sortable:true" width="80"> 首页显示顺序 </th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var choosegridfarmdocgroupuser;
	var chooseSearchfarmdocgroupuser;
	var toolbar_choosefarmdocgroupuser = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfarmdocgroupuser').datagrid(
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
		choosegridfarmdocgroupuser = $('#dom_choosegridfarmdocgroupuser').datagrid( {
			url : 'admin/FarmDocgroupUserqueryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefarmdocgroupuser,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmdocgroupuser = $('#dom_chooseSearchfarmdocgroupuser').searchForm( {
			gridObj : choosegridfarmdocgroupuser
		});
	});
	//-->
</script>
<!-- 
<a id="form_farmdocgroupuser_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_farmdocgroupuser_a_Choose').bindChooseWindow('chooseWinfarmdocgroupuser', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FarmDocgroupUser_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








