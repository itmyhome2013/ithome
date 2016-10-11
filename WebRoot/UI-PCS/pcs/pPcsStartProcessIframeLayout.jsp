<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>启动流程</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<%-- <jsp:include page="/PLUGIN/pop/commons/include_all.jsp"></jsp:include> --%>
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout" >
		 <!-- 待填报表单树 -->
		    <div class="panel-body panel-body-noheader panel-body-noborder layout-body"  data-options="region:'north',border:false" style=" background-color: rgb(243, 243, 243);width: 100%">
				<div style="text-align: right; color: #000000;padding-right: 10px;">
					<a id="synchronprocessbtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">同步流程节点</a>
					<a id="startprocessbtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-sprocket_dark'">启动流程</a> 
				</div>
		    </div>	
		<!-- 结果列表 -->
		  <div id="image_content_div" data-options="region:'center',border:false" style="width: 100%;padding: 10px; " align="center">
		                <div style="border:1px solid rgb(243, 243, 243);border-radius:8px;width:90%;padding: 10px" align="left">
				                <table border="0" style="padding-left: 5px">
				                   <tr>
				                     <td rowspan="3">
				                     <img  src="WEB-FACE/model/easy_ui_1_3_6/themes/icons/process-50.png">
				                     </td>
				                   </tr>
				                   <tr>
				                     <td>  
				                         <div style="letter-spacing:20;padding-left: 10px;color: #4F94CD">
				                              <h2> ${procDefEntity.name}</h2>
				                         </div>
				                          
				                     </td>
				                   </tr>
				                   <tr>
				                     <td >
				                         <img  src="WEB-FACE/model/easy_ui_1_3_6/themes/icons/version-16.png" style="margin-bottom:-1px; ">&nbsp;&nbsp;版本 ${procDefEntity.version}  
				                         &nbsp;&nbsp;&nbsp;&nbsp;  
				                         <img  src="WEB-FACE/model/easy_ui_1_3_6/themes/icons/time.png" style="margin-bottom:-2px;">  &nbsp;&nbsp;部署时间：<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${deploymentEntity.deploymentTime}" />
				                       
				                     </td>
				                   </tr>
				                </table>
				                
				                <div>
				                <HR style="border:1 dashed #987cb9" width="98%" color=#987cb9 SIZE=1>
				                </div>
				               
				               <div style="padding-top: 30px;padding-left: 10px;color: #4F94CD">         
				                   <h2>流程图</h2> 
				               </div>
				               
				               <div style="border:1px solid rgb(243, 243, 243);border-radius:8px;width:95%;padding-top: 5px;padding-left: 10px;height: 300px;overflow :auto">
				               <!--  <img  src="UI-PCS/pcs/EventProcess.png"> -->
				               <img  src="admin/pcsDefineImage.do?imageName=${procDefEntity.diagramResourceName}&&ids=${procDefEntity.deploymentId}">
				               </div>
		               
		                </div>
		  </div>
	</body>
	<script type="text/javascript">
	    var forSendActExProcessEntityAction="admin/forSendActExProcessEntity_ACTION_CONSOLE.do";//业务流程表添加页面 	
		$(function() {
			$("#synchronprocessbtn").click(function(){
           	 	$.ajax({
           	 		url:'admin/synchronProcessNode.do?actExProcessEntity.procdefkey=${procDefEntity.key}',
           	 		dataType:'text',
           	 		success:function(data){
           	 			if(data == 'true'){
           	 				alert("同步成功");
           	 			}else{
           	 				alert("同步失敗");
           	 			}
           	 		}
           	 	});
            });
             $("#startprocessbtn").click(function(){
            	 $.farm.openWindow({
     				id : 'startprocesswin',
     				width : 500,
     				height : 250,
     				modal : true,
     				url : forSendActExProcessEntityAction+"?pageset.pageType="+ PAGETYPE.ADD+"&actExProcessEntity.procdefkey=${procDefEntity.key}&actExProcessEntity.procdefname=${procDefEntity.name}",
     				title : '添加${procDefEntity.name}'
     			});
             });
		});
	</script>
</html>
		
			
										
										
