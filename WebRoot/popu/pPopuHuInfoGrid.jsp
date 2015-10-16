<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--房屋基础信息选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchpopuhuinfo">
			<table class="editTable">
				<tr>
					<td class="title">
						房主姓名:
					</td>
					<td>
						<input name="HUNAME:like" type="text" >
					</td>
					<td class="title">
						住房性质:
					</td>
					<td>
						<input name="HUHOUSEQUALE:=" type="text" >
					</td>
					<td class="title">
						房主证件号码:
					</td>
					<td>
						<input name="HUIDENTITY:=" type="text" >
					</td>
					<td class="title">
						房屋地址:
					</td>
					<td>
						<input name="HUFLOOR:=" type="text" >
					</td>
					<td class="title">
						房主户籍非户籍:
					</td>
					<td>
						<input name="HUFLOATINGPOPULATION:like" type="text" >
					</td>
					<td class="title">
						房主性别:
					</td>
					<td>
						<input name="HUSEX:=" type="text" >
					</td>
					<td class="title">
						是否出租:
					</td>
					<td>
						<input name="HUCHUZUIS:=" type="text" >
					</td>
					<td class="title">
						房主证件类型:
					</td>
					<td>
						<input name="HUCODETYPE:=" type="text" >
					</td>
					<td class="title">
						开始出租日期:
					</td>
					<td>
						<input name="STARRENTTDATE:=" type="text" >
					</td>
					<td class="title">
						终止出租日期:
					</td>
					<td>
						<input name="ENDRENTDATE:=" type="text" >
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
		<table class="easyui-datagrid" id="dom_choosegridpopuhuinfo">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="HUID" data-options="sortable:true" width="80"> 房编码 </th>
					<th field="HUNAME" data-options="sortable:true" width="80"> 房主姓名 </th>
					<th field="HUHOUSERELATION" data-options="sortable:true" width="80"> 户主与房主的关系 </th>
					<th field="HUSTATE" data-options="sortable:true" width="80"> 房主户籍状态 </th>
					<th field="HUADDRESS" data-options="sortable:true" width="80"> 房主户籍地址 </th>
					<th field="HUHOUSEQUALE" data-options="sortable:true" width="80"> 住房性质 </th>
					<th field="HUIDENTITY" data-options="sortable:true" width="80"> 房主证件号码 </th>
					<th field="HUTEL" data-options="sortable:true" width="80"> 房主电话 </th>
					<th field="HUFLOOR" data-options="sortable:true" width="80"> 房屋地址 </th>
					<th field="HUCELL" data-options="sortable:true" width="80"> 单元 </th>
					<th field="HUROOMNO" data-options="sortable:true" width="80"> 门牌号（室） </th>
					<th field="HUCURRADDRESS" data-options="sortable:true" width="80"> 房主现居住地址 </th>
					<th field="HUNOTE" data-options="sortable:true" width="80"> 拥有其他房产 </th>
					<th field="LIVESADDRESS" data-options="sortable:true" width="80"> 本房产地址（自动生成地址） </th>
					<th field="HUFLOATINGPOPULATION" data-options="sortable:true" width="80"> 房主户籍非户籍 </th>
					<th field="HUSEX" data-options="sortable:true" width="80"> 房主性别 </th>
					<th field="HUCHUZUIS" data-options="sortable:true" width="80"> 是否出租 </th>
					<th field="HUBOOKDATE" data-options="sortable:true" width="80"> 登记时间 </th>
					<th field="HUCODETYPE" data-options="sortable:true" width="80"> 房主证件类型 </th>
					<th field="STARRENTTDATE" data-options="sortable:true" width="80"> 开始出租日期 </th>
					<th field="ENDRENTDATE" data-options="sortable:true" width="80"> 终止出租日期 </th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var choosegridpopuhuinfo;
	var chooseSearchpopuhuinfo;
	var toolbar_choosepopuhuinfo = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridpopuhuinfo').datagrid(
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
		choosegridpopuhuinfo = $('#dom_choosegridpopuhuinfo').datagrid( {
			url : 'admin/PopuHuInfoqueryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosepopuhuinfo,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchpopuhuinfo = $('#dom_chooseSearchpopuhuinfo').searchForm( {
			gridObj : choosegridpopuhuinfo
		});
	});
	//-->
</script>
<!-- 
<a id="form_popuhuinfo_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_popuhuinfo_a_Choose').bindChooseWindow('chooseWinpopuhuinfo', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/PopuHuInfo_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








