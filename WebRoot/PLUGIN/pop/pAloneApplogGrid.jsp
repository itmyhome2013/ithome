<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div class="search_div">
	<form id="dom_AloneApplog_search_form_choose">
				生成时间:<input name="CTIME:=" type="text" >
				日志:<input name="DESCRIBES:like" type="text" >
				操作人:<input name="APPUSER:like" type="text" >
				日志级别:<input name="LEVEL:=" type="text" >
				类:<input name="CLASSNAME:like" type="text" >
		<a id="a_AloneApplog_search_choose" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-search">查询</a>
		<a id="a_AloneApplog_reset_choose" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-reload">清除条件</a>
	</form>
</div>
<div class="datagrid_div">
	<table id="dom_AloneApplog_dg_choose"  style="height: 230px">
		<thead>
			<tr>
						<th field="CTIME" data-options="sortable:true" width="80"> 生成时间 </th>
						<th field="CUSER" data-options="sortable:true" width="80"> 操作人 </th>
						<th field="DESCRIBES" data-options="sortable:true" width="80"> 日志 </th>
						<th field="APPUSER" data-options="sortable:true" width="80"> 操作人 </th>
						<th field="LEVEL" data-options="sortable:true" width="80"> 日志级别 </th>
						<th field="METHOD" data-options="sortable:true" width="80"> 方法 </th>
						<th field="CLASSNAME" data-options="sortable:true" width="80"> 类 </th>
			</tr>
		</thead>
	</table>
	<!-- 分页栏 -->
	<div id="dom_AloneApplog_pp_choose" class="easyui-pagination"></div>
</div>
<script type="text/javascript">
	var toolbar_AloneApplog_choose = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_AloneApplog_dg_choose').datagrid('getSelections');
			if (selectedArray.length > 0) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,'info');
			}
		}
	} ];
	$(function() {
		init_grid("#dom_AloneApplog_pp_choose", "#dom_AloneApplog_dg_choose",
				"admin/AloneApplogqueryAll.do", null,toolbar_AloneApplog_choose,true);
		init_pagination("#dom_AloneApplog_pp_choose", "#dom_AloneApplog_dg_choose",
				"#dom_AloneApplog_search_form_choose");
		$('#a_AloneApplog_search_choose').bind('click',
			function() {
				do_search("#dom_AloneApplog_search_form_choose", "#dom_AloneApplog_dg_choose",
						"#dom_AloneApplog_pp_choose");
			});
		$('#a_AloneApplog_reset_choose').bind('click', function() {
			do_searchClear("#dom_AloneApplog_search_form_choose");
		});
	});
	//-->
</script>

<!-- 
<a id="form_AloneApplog_a_ChoosePop"  href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<div id="win_AloneApplog_ChoosPop" class="easyui-window" style="width: 500px; height: 350px;"
	data-options="closed:true,modal:true,href:'admin/AloneApplog_ACTION_ALERT.do'"></div>

<script type="text/javascript">
	$(function() {
		initChoose_Window_Default('#form_AloneApplog_a_ChoosePop','#win_AloneApplog_ChoosPop',function(rows){
			//$('#entity_name').attr('value',rows[0].NAME);
		});
	});
</script>
 -->







