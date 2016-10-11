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
		<title>流程部署信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
		<style type="text/css">@import url(<%=basePath%>WEB-FACE/model/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css);</style>
		<script type="text/javascript" src="<%=basePath%>WEB-FACE/model/plupload/js/plupload.full.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>WEB-FACE/model/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
		<script type="text/javascript" src="<%=basePath%>WEB-FACE/model/plupload/js/i18n/zh_CN.js"></script>
		
		<script type="text/javascript">
	$(function() {
		   $("#uploader").pluploadQueue({
			runtimes : 'html5,flash,silverlight,html4',
			url : '<%=basePath%>admin/PcsDeployProcess.do',
			max_file_size : '10mb',
			file_data_name:'deployProcessZip',
			unique_names:false,
			chunk_size: '0',  
			filters : [
				{title : "zip, 流程zip压缩文件", extensions : "zip"}
			],
			flash_swf_url : '<%=basePath%>WEB-FACE/model/plupload/js/Moxie.swf',
			silverlight_xap_url : '<%=basePath%>WEB-FACE/model/plupload/js/Moxie.xap',
									init : {
										BeforeUpload : function(up, file) {
											//重点在这里，上传的时候自定义参数信息
											this
													.setOption(
															"multipart_params",
															{
																"processName" : file.name
															});
										},

										FilesAdded : function(up, files) {
											$.each(up.files, function(i, file) {
												if (up.files.length <= 1) {
													return;
												}
												up.removeFile(file);
											});
										},
										UploadComplete : function(uploader,
												files) {
											alert('流程布署成功！');
											location.reload();
										}
									}

								});

			});
		</script>
		
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="dom_searchpcsdeploymanage">
				<table class="editTable">
						<tr>
							<td class="title">
								流程部署名:
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
			<table class="easyui-datagrid" id="dom_datagridpcsdeploymanage">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="id" data-options="sortable:false" width="100"> 流程部署ID </th>
						<th field="name" data-options="sortable:false" width="200"> 流程部署名 </th>
						<th field="deploymentTime" data-options="sortable:false" width="100"> 流程部署时间 </th>
					</tr>
				</thead>
			</table>
		</div>
		
		
		<div id="win" style="display: none">
		<div>
		<div style="width: 750px; margin: 0px auto">
			<form id="formId" action="<%=basePath%>admin/PcsDeployProcess.do" method="post">
				<div id="uploader">
					<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
				</div>
			</form>
		</div>
	</div>
	<input  type="hidden" id="processName"  value="">
		
		</div>  

	</body>
	<script type="text/javascript">
		var url_searchPcsdeployAction = "admin/pcsDeploymentList.do";//查询URL
		var url_synchronPcsdeployAction="admin/synchronProcessNode.do";//同步流程节点URL
		var url_delPcsdeployAction="admin/pcsDelProcessDefinition.do";//删除URL
		var gridpcsdeploymanage;//数据表格对象
		var searchpcsdeploymanage;//条件查询组件对象
		var TOOL_BARpcsdeploy = [ {
			id : 'deploypcs',
			text : '部署新流程',
			iconCls : 'icon-publish',
			handler : WinPcsdeploy
		},{
			id : 'synchron',
			text : '同步流程节点',
			iconCls : 'icon-reload',
			handler : synchronPcsdeploy
		},{
			id : 'delpcs',
			text : '删除',
			iconCls : 'icon-remove',
			handler : DelPcsdeploy
		}];
		$(function() {
		 	//初始化数据表格
			gridpcsdeploymanage = $('#dom_datagridpcsdeploymanage').datagrid( {
				url : url_searchPcsdeployAction,
				fit : true,
				fitColumns : true,
				'toolbar' : TOOL_BARpcsdeploy,
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
			searchpcsdeploymanage = $('#dom_searchpcsdeploymanage').searchForm( {
				gridObj : gridpcsdeploymanage
			}); 
		});
		//部署新流程
		function WinPcsdeploy() {
			$('#win').show();
			$('#win').window({ 
				title:'添加部署文件',
			    width:780,    
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,
			    height:390,    
			    modal:true   
			});  
		}
		//同步流程节点
		function synchronPcsdeploy(){
			var selectedArray = $(gridpcsdeploymanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				$.post(url_synchronPcsdeployAction + '?ids=' + $.farm.getCheckedIds(gridpcsdeploymanage,"id"), {},
						function(data) {
							if(data){
								$.messager.alert(MESSAGE_PLAT.PROMPT, "同步成功",'info');
								$(gridpcsdeploymanage).datagrid('reload');
							} else {
								$.messager.alert(MESSAGE_PLAT.ERROR, "同步失败", 'error');
							}
				}); 
			}
		}
		//删除流程定义
		function DelPcsdeploy(){
			var selectedArray = $(gridpcsdeploymanage).datagrid('getSelections');
			if (selectedArray.length > 0) {
				 // 有数据执行操作
				var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
				$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
					if (flag) {
						$.post(url_delPcsdeployAction + '?ids=' + $.farm.getCheckedIds(gridpcsdeploymanage,"id"), {},
								function(flag) {
									if (flag.pageset.commitType == 0) {
										$.messager.alert(MESSAGE_PLAT.PROMPT, "删除成功",'info');
										$(gridpcsdeploymanage).datagrid('reload');
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
		}
		
	</script>
</html>
		
			
										
										
