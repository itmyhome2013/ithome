<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div class="search_div">
	<form id="dom_AloneRolegroup_search_form_choose">
		<a id="a_AloneRolegroup_search_choose" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-search">查询</a>
		<a id="a_AloneRolegroup_reset_choose" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-reload">清除条件</a>
	</form>
</div>
<div class="datagrid_div">
	<table id="dom_AloneRolegroup_dg_choose"  style="height: 230px">
		<thead>
			<tr>
						<th field="NAME" data-options="sortable:true" width="80"> 角色名称 </th>
						<th field="COMMENTS" data-options="sortable:true" width="80"> 备注 </th>
						<th field="STATE" data-options="sortable:true" width="80"> 状态 </th>
			</tr>
		</thead>
	</table>
	<!-- 分页栏 -->
	<div id="dom_AloneRolegroup_pp_choose" class="easyui-pagination"></div>
</div>
<script type="text/javascript">
	var toolbar_AloneRolegroup_choose = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_AloneRolegroup_dg_choose').datagrid('getSelections');
			if (selectedArray.length > 0) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,'info');
			}
		}
	} ];
	$(function() {
		init_grid("#dom_AloneRolegroup_pp_choose", "#dom_AloneRolegroup_dg_choose",
				"admin/AloneRolegroupqueryAll.do", null,toolbar_AloneRolegroup_choose,true);
		init_pagination("#dom_AloneRolegroup_pp_choose", "#dom_AloneRolegroup_dg_choose",
				"#dom_AloneRolegroup_search_form_choose");
		$('#a_AloneRolegroup_search_choose').bind('click',
			function() {
				do_search("#dom_AloneRolegroup_search_form_choose", "#dom_AloneRolegroup_dg_choose",
						"#dom_AloneRolegroup_pp_choose");
			});
		$('#a_AloneRolegroup_reset_choose').bind('click', function() {
			do_searchClear("#dom_AloneRolegroup_search_form_choose");
		});
	});
	//-->
</script>

<!-- 
<a id="form_AloneRolegroup_a_ChoosePop"  href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<div id="win_AloneRolegroup_ChoosPop" class="easyui-window" style="width: 500px; height: 350px;"
	data-options="closed:true,modal:true,href:'admin/AloneRolegroup_ACTION_ALERT.do'"></div>

<script type="text/javascript">
	$(function() {
		initChoose_Window_Default('#form_AloneRolegroup_a_ChoosePop','#win_AloneRolegroup_ChoosPop',function(rows){
			//$('#entity_name').attr('value',rows[0].NAME);
		});
	});
</script>
 -->







