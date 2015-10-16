<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div class="search_div">
	<form id="dom_AloneParameter_search_form_choose">
				名称:<input name="NAME:like" type="text" >
		<a id="a_AloneParameter_search_choose" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-search">查询</a>
		<a id="a_AloneParameter_reset_choose" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-reload">清除条件</a>
	</form>
</div>
<div class="datagrid_div">
	<table id="dom_AloneParameter_dg_choose"  style="height: 230px">
		<thead>
			<tr>
						<th field="NAME" data-options="sortable:true" width="80"> 名称 </th>
						<th field="STATE" data-options="sortable:true" width="80"> 状态 </th>
						<th field="PKEY" data-options="sortable:true" width="80"> 键 </th>
						<th field="PVALUE" data-options="sortable:true" width="80"> 值 </th>
						<th field="RULES" data-options="sortable:true" width="80"> 规则 </th>
						<th field="DOMAIN" data-options="sortable:true" width="80"> 域 </th>
						<th field="COMMENTS" data-options="sortable:true" width="80"> 备注 </th>
						<th field="VTYPE" data-options="sortable:true" width="80"> 类型 </th>
			</tr>
		</thead>
	</table>
	<!-- 分页栏 -->
	<div id="dom_AloneParameter_pp_choose" class="easyui-pagination"></div>
</div>
<script type="text/javascript">
	var toolbar_AloneParameter_choose = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_AloneParameter_dg_choose').datagrid('getSelections');
			if (selectedArray.length > 0) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,'info');
			}
		}
	} ];
	$(function() {
		init_grid("#dom_AloneParameter_pp_choose", "#dom_AloneParameter_dg_choose",
				"admin/AloneParameterqueryAll.do", null,toolbar_AloneParameter_choose,true);
		init_pagination("#dom_AloneParameter_pp_choose", "#dom_AloneParameter_dg_choose",
				"#dom_AloneParameter_search_form_choose");
		$('#a_AloneParameter_search_choose').bind('click',
			function() {
				do_search("#dom_AloneParameter_search_form_choose", "#dom_AloneParameter_dg_choose",
						"#dom_AloneParameter_pp_choose");
			});
		$('#a_AloneParameter_reset_choose').bind('click', function() {
			do_searchClear("#dom_AloneParameter_search_form_choose");
		});
	});
	//-->
</script>

<!-- 
<a id="form_AloneParameter_a_ChoosePop"  href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<div id="win_AloneParameter_ChoosPop" class="easyui-window" style="width: 500px; height: 350px;"
	data-options="closed:true,modal:true,href:'admin/AloneParameter_ACTION_ALERT.do'"></div>

<script type="text/javascript">
	$(function() {
		initChoose_Window_Default('#form_AloneParameter_a_ChoosePop','#win_AloneParameter_ChoosPop',function(rows){
			//$('#entity_name').attr('value',rows[0].NAME);
		});
	});
</script>
 -->







