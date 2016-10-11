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
		<title>流程模型信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchpcsmodelmanage">
				<table class="editTable">
						<tr>
							<td class="title">
								模型名称:
							</td>
							<td>
								<input name="name:like" type="text" >
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
			<table class="easyui-datagrid" id="dom_datagridpcsmodelmanage">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="id" data-options="sortable:false" width="100"> 模型ID </th>
						<th field="name" data-options="sortable:false" width="100"> 模型名称 </th>
						<th field="key" data-options="sortable:false" width="100"> 模型KEY </th>
						<th field="createTime" data-options="sortable:false" width="100"> 创建时间 </th>
						<th field="lastUpdateTime" data-options="sortable:false" width="100"> 最后修改时间</th>
						<th field="metaInfo" data-options="sortable:false" width="120"> 模型描述 </th>
						<th field="editorsourceextravalueid" data-options="hidden:true" width="120"> editorsourceextravalueid </th>
					</tr>
				</thead>
			</table>
		</div>
         <div id="win">
			<img id="pcsImage" src="">
	      </div>  


	</body>
	<script type="text/javascript">
		var url_searchPcsmodelAction = "admin/pcsModelList.do";//查询URL
		var url_formPcsmodelAction = "admin/PcsModelEntity_ACTION_CONSOLE.do";//增加、修改、查看URL
		var url_delPcsmodelAction = "admin/pcsDelModel.do";//删除
		var url_exportModelAction="admin/pcsExportModel.do";//导出模型
		var url_deployModelAction="admin/pcsDeployModel.do";//部署模型
		var gridpcsmodelmanage;//数据表格对象
		var searchpcsmodelmanage;//条件查询组件对象
		var TOOL_BARpcsmodel = [ {
			id : 'addpcsmodelwin',
			text : '添加模型',
			iconCls : 'icon-add',
			handler : AddPcsModelWin
		},{
			id : 'delModel',
			text : '删除模型',
			iconCls : 'icon-remove',
			handler : DelPcsModel
		},{
			id : 'editModel',
			text : '编辑模型',
			iconCls : 'icon-edit',
			handler : editPcsModel
		},{
			id : 'exportModel',
			text : '导出模型',
			iconCls : 'icon-documents_email',
			handler : exportPcsModel
		},{
			id : 'deployModel',
			text : '部署模型',
			iconCls : 'icon-database_gear',
			handler : deployModel
		}];
		$(function() {
		 	//初始化数据表格
			gridpcsmodelmanage = $('#dom_datagridpcsmodelmanage').datagrid( {
				url : url_searchPcsmodelAction,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARpcsmodel,
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
			searchpcsmodelmanage = $('#dom_searchpcsmodelmanage').searchForm( {
				gridObj : gridpcsmodelmanage
			}); 
		});
		
		//新增模型函数
		function AddPcsModelWin(){
		var url = url_formPcsmodelAction + '?pageset.pageType='+ PAGETYPE.ADD;
			$.farm.openWindow({
				id : 'pcsmodelwin',
				width : 500,
				height : 250,
				modal : true,
				url : url,
				title : '创建流程模型'
			});
		};
		
		//删除模型函数
		function DelPcsModel(){
			var selectedArray = $(gridpcsmodelmanage).datagrid('getSelections');
			console.info(selectedArray);
			console.info($.farm.getCheckedIds(gridpcsmodelmanage,"id"));
			if (selectedArray.length > 0) {
				var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
				$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
					if (flag) {
						$.post(url_delPcsmodelAction + '?ids=' + $.farm.getCheckedIds(gridpcsmodelmanage,"id"), {},
								function(flag) {
									if (flag.pageset.commitType == 0) {
										$.messager.alert(MESSAGE_PLAT.PROMPT, "成功删除"+selectedArray.length+"条数据",'info');
										$(gridpcsmodelmanage).datagrid('reload');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ flag.pageset.message;
								$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
							}
						}); 
					}
				}); 
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		};
		
		//导出模型函数
		function exportPcsModel(){
			var selectedArray = $(gridpcsmodelmanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				rowData = selectedArray[0];
				modelId=rowData.id;
				modelName=rowData.name;
				editorsourceextravalueid=rowData.editorsourceextravalueid;
				if(editorsourceextravalueid){
					window.location.href=url_exportModelAction+"?entity.id="+modelId+"&entity.name="+modelName;
				}else{
					$.messager.alert(MESSAGE_PLAT.PROMPT, "流程模型为空",'info');
				}
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "请选择一条记录",
						'info');
			}
		}
		
		//部署模型函数
		function deployModel(){
			var selectedArray = $(gridpcsmodelmanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				rowData = selectedArray[0];
				modelId=rowData.id;
				editorsourceextravalueid=rowData.editorsourceextravalueid;
				if(editorsourceextravalueid){
					$.post(url_deployModelAction + '?entity.id=' + modelId,
					   function(flag) {
								if (flag.pageset.commitType == 0) {
									$.messager.alert(MESSAGE_PLAT.PROMPT, "部署成功",'info');
									$(gridpcsmodelmanage).datagrid('reload');
						} else {
							var str = MESSAGE_PLAT.ERROR_SUBMIT
									+ flag.pageset.message;
							$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
						}
					}); 
				}else{
					$.messager.alert(MESSAGE_PLAT.PROMPT, "流程模型为空",'info');
				}
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
		
		//编辑模型函数
		function editPcsModel(){
			var selectedArray = $(gridpcsmodelmanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				rowData = selectedArray[0];
				modelId=rowData.id;
				window.open('modeler/service/editor?id='+modelId); 
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
		
		
	</script>
</html>
		
			
										
										
