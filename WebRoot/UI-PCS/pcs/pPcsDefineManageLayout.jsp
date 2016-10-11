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
		<title>流程定义信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchpcsdefinemanage">
				<table class="editTable">
						<tr>
							<td class="title">
								流程定义KEY:
							</td>
							<td>
								<input name="key:like" type="text" >
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
			<table class="easyui-datagrid" id="dom_datagridpcsdefinemanage">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="id" data-options="sortable:false" width="100"> 流程定义ID </th>
						<th field="deploymentId" data-options="sortable:false" width="100"> 流程署名ID </th>
						<th field="name" data-options="sortable:false" width="100"> 流程定义名称 </th>
						<th field="key" data-options="sortable:false" width="100"> 流程定义KEY </th>
						<th field="version" data-options="sortable:false" width="100"> 流程版本 </th>
						<th field="imageName" data-options="sortable:false" width="120"> 图片定义名称</th>
						<th field="resourcename" data-options="sortable:false" width="120"> 流程文件名称 </th>
					</tr>
				</thead>
			</table>
		</div>
         <div id="win">
			<img id="pcsImage" src="">
	      </div>  


	</body>
	<script type="text/javascript">
		var url_searchPcsdefineAction = "admin/pcsDefineList.do";//查询URL
		var url_ImagePcsdefineAction="admin/pcsDefineImage.do";//流程图
		var url_ConverttomodelAction="admin/pcsConvertToModel.do";//流程转为模型
		var gridpcsdefinemanage;//数据表格对象
		var searchpcsdefinemanage;//条件查询组件对象
		var TOOL_BARpcsdefine = [ {
			id : 'pcsdefineImage',
			text : '查看流程图',
			iconCls : 'icon-eye',
			handler : WinPcsdefineImage
		},{
			id : 'converttomodel',
			text : '转为模型',
			iconCls : 'icon-product',
			handler : converttomodel
		}];
		$(function() {
		 	//初始化数据表格
			gridpcsdefinemanage = $('#dom_datagridpcsdefinemanage').datagrid( {
				url : url_searchPcsdefineAction,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARpcsdefine,
				pagination : true,
				closable : true,
				checkOnSelect : true,
				singleSelect:true,
				striped : true,
				rownumbers : true,
				ctrlSelect : true,
				fitColumns : true
			});
			//初始化条件查询
			searchpcsdefinemanage = $('#dom_searchpcsdefinemanage').searchForm( {
				gridObj : gridpcsdefinemanage
			}); 
		});
		//流程图片
		function WinPcsdefineImage() {
			var selectedArray = $(gridpcsdefinemanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				rowData = selectedArray[0];
				imageName=rowData.imageName
				name=rowData.name+"流程图";
				
				$("#pcsImage").attr("src",url_ImagePcsdefineAction+ '?imageName='+imageName+'&ids=' + $.farm.getCheckedIds(gridpcsdefinemanage,"deploymentId"));
				$('#win').window({
		            width: 800,
		            height: 400,
		            modal: true,
		            collapsible:false,
				    minimizable:false,
		            title: name

		        });
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "请选择一条记录",
						'info');
			}
		}
		
		
		function converttomodel(){
			var selectedArray = $(gridpcsdefinemanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				rowData = selectedArray[0];
				defineId=rowData.id;
					$.post(url_ConverttomodelAction + '?processDefinitionEntity.id=' + defineId,
					   function(flag) {
								if (flag.pageset.commitType == 0) {
									$.messager.alert(MESSAGE_PLAT.PROMPT, "转换成功",'info');
									$(gridpcsdefinemanage).datagrid('reload');
						} else {
							var str = MESSAGE_PLAT.ERROR_SUBMIT
									+ flag.pageset.message;
							$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
						}
					}); 
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
	</script>
</html>
		
			
										
										
