<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>已归档</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchArchivingProcessfrom">
				<table class="editTable">
						<tr>
							<td class="title">
								事件名称:
							</td>
							<td>
								<input name="b.PROCESSNAME:like" type="text" >
							</td>
							
							<td colspan="6">
							</td>
						</tr>
						
					<tr style="text-align: center;">
						<td colspan="8">
							<a id="a_search" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a id="a_reset" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table class="easyui-datagrid" id="dom_datagridArchivingProcess">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="PROCESSID" data-options="hidden:true" width="100"> PROCESSID</th>
						<th field="PROCESSNAME" data-options="hidden:false" width="100"> 流程名称</th>
						<th field="CREATEUSERNAME" data-options="sortable:true" width="100">创建人 </th>
						<th field="CREATEDATE" data-options="sortable:true" width="100"> 创建时间 </th>
						<th field="PROCDEFNAME" data-options="sortable:true" width="100"> 流程定义名称 </th>
						<th field="URGENCY" data-options="sortable:true" width="100"> 紧急程度 </th>
						<th field="COMMENTS" data-options="sortable:true" width="200"> 备注 </th>
					</tr>
				</thead>
			</table>
		</div>
		
	</body>
	<script type="text/javascript">
		var findArchivingProcessListAction = "admin/ArchivingProcessAction_findArchivingProcessList.do";//查询URL
		var datagridArchivingProcess;//数据表格对象
		var searchArchivingProcessfrom;//条件查询组件对象
		var TOOL_BARArchivingProcess = [{
			id : 'ArchivingProcessviewId',
			text : '查看',
			iconCls : 'icon-eye',
			handler : viewfun
		}];
		$(function() {
		 	//初始化数据表格
			datagridArchivingProcess = $('#dom_datagridArchivingProcess').datagrid( {
				url : findArchivingProcessListAction,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARArchivingProcess,
				pagination : true,
				closable : true,
				checkOnSelect : true,
				singleSelect:false,
				striped : true,
				rownumbers : true,
				ctrlSelect : true,
				fitColumns : true
			});
			//初始化条件查询
			searchArchivingProcessfrom = $('#dom_searchArchivingProcessfrom').searchForm( {
				gridObj : datagridArchivingProcess
			}); 
		});
		
     function viewfun(){
    	 var selectedArray = $(datagridArchivingProcess).datagrid('getSelections');
			if (selectedArray.length > 0) {
				 rowData = selectedArray[0];
				var processId = rowData.PROCESSID;
				//handleType 办理类型（0：办理，1：查看，2.已归档查看）
			    var url="admin/TaskHandleManage_ACTION_CONSOLE.do?handleType=2&processId="+processId;
				$.farm.openWindow({
					id : 'PfillFormListLayoutwin',
					width : 900,
					height : 450,
					modal : true,
					url : url,
					title : '查看'
				});  
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "请选择一条记录",
						'info');
			}
     }
		
	</script>
</html>
		
			
										
										
