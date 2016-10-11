<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">

	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_chooseApproval">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="APPROVALUSERNAME" data-options="sortable:true" width="80"> 审核人 </th>
					<th field="APPROVALDATE" data-options="sortable:true" width="80"> 审核时间 </th>
					<th field="APPROVALSTATE" data-options="sortable:true" width="80"> 审核状态 </th>
					<th field="APPROVALMESSAGE" data-options="sortable:true" width="200"> 审核意见 </th>
					<th field="APPROVALTASKNAME" data-options="sortable:true" width="80"> 审批节点 </th>
					<!-- <th field="SUBMITNODEKNAME" data-options="sortable:true" width="80"> 驳回节点 </th> -->
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
    var findApprovalListAction="admin/ActExApproval_findApprovalList.do";//查询
	var chooseApproval;
	$(function() {
		chooseApproval = $('#dom_chooseApproval').datagrid( {
			url : findApprovalListAction+"?processId=${processId}",
			fit : true,
			fitColumns : true,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
	});
	//-->
</script>

