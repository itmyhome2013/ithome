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
		<title>待办理任务</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchDoTaskfrom">
				<table class="editTable">
						<tr>
							<td class="title">
								事件名称:
							</td>
							<td>
								<input name="c.PROCESSNAME:like" type="text" >
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
			<table class="easyui-datagrid" id="dom_datagridDoTask">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="PROCESSID" data-options="hidden:true" width="100"> PROCESSID</th>
						<th field="TASKDEFKEY" data-options="hidden:true" width="100"> TASKDEFKEY</th>
						<th field="TASKID" data-options="hidden:true" width="100"> TASKID</th>
						<th field="TASKNAME" data-options="hidden:true" width="100"> TASKNAME</th>
						<th field="PROCESSNAME" data-options="hidden:false" width="100"> 流程名称</th>
						<th field="CREATEUSERNAME" data-options="sortable:true" width="100">创建人 </th>
						<th field="CREATEDATE" data-options="sortable:true" width="100"> 创建时间 </th>
						<th field="PROCDEFNAME" data-options="sortable:true" width="100"> 流程定义名称 </th>
						<th field="URGENCY" data-options="sortable:true" width="100"> 紧急程度 </th>
						<th field="COMMENTS" data-options="sortable:true" width="200"> 备注 </th>
						<th field="CURRENTTASKNAME" data-options="sortable:true" width="100"> 任务节点 </th>
					</tr>
				</thead>
			</table>
		</div>
		
	</body>
	<script type="text/javascript">
		var findDoTaskListAction = "admin/DoTaskAction_findDoTaskList.do";//查询URL
		var reportTaskAction="admin/DoTaskAction_reportTask.do";//上报URL
		var datagridDoTask;//数据表格对象
		var searchDoTaskfrom;//条件查询组件对象
		var TOOL_BARdotask = [{
			id : 'viewhandleId',
			text : '办理',
			iconCls : 'icon-special-offer',
			handler : handlefun
		},{
			id : 'DotaskviewId',
			text : '查看',
			iconCls : 'icon-eye',
			handler : viewfun
		}];
		$(function() {
		 	//初始化数据表格
			datagridDoTask = $('#dom_datagridDoTask').datagrid( {
				url : findDoTaskListAction,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARdotask,
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
			searchDoTaskfrom = $('#dom_searchDoTaskfrom').searchForm( {
				gridObj : datagridDoTask
			}); 
		});
		
		function handlefun(handleType){
			var selectedArray = $(datagridDoTask).datagrid('getSelections');
			if (selectedArray.length > 0) {
				 rowData = selectedArray[0];
				var  taskId =rowData.TASKID;
				var taskDefKey =rowData.TASKDEFKEY;
				var processId = rowData.PROCESSID;
				var taskName = rowData.TASKNAME;
				//handleType 办理类型（0：办理，1：查看，2.已归档查看）
			    var url="admin/TaskHandleManage_ACTION_CONSOLE.do?handleType=0&taskId="+taskId+"&taskDefKey="+taskDefKey+"&processId="+processId+"&taskName="+taskName;
				$.farm.openWindow({
					id : 'PfillFormListLayoutwin',
					width : 900,
					height : 450,
					modal : true,
					url : url,
					title : '办理'
				});  
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "请选择一条记录",
						'info');
			}
		}
		
     function viewfun(){
    	 var selectedArray = $(datagridDoTask).datagrid('getSelections');
			if (selectedArray.length > 0) {
				 rowData = selectedArray[0];
				var  taskId =rowData.TASKID;
				var taskDefKey =rowData.TASKDEFKEY;
				var processId = rowData.PROCESSID;
				var taskName = rowData.TASKNAME;
				//handleType 办理类型（0：办理，1：查看，2.已归档查看）
			    var url="admin/TaskHandleManage_ACTION_CONSOLE.do?handleType=1&taskId="+taskId+"&taskDefKey="+taskDefKey+"&processId="+processId+"&taskName="+taskName;
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
		
			
										
										
