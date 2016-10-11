<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--人口基础信息-->
<div id="pActExProcessHandletabsId">
  <!-- handleType 办理类型（0：办理，1：查看，2.已归档查看） -->
     <c:if test="${handleType==0 || handleType==1}">
		<div title="流程图" style="padding: 10px" data-options="">
		  <img id="currentImage" src="admin/DoTaskAction_findviewCurrentImage.do?taskId=${taskId}">
		</div>
	</c:if>
	<c:if test="${handleType==0}">
		<div title="可填业务表单" style="padding: 10px"
			data-options="href:'admin/DoTaskfillFormManage_ACTION_CONSOLE.do?taskDefKey=${taskDefKey}&processId=${processId}'">
		</div>
	</c:if>
	<div title="已填业务表单" style="padding: 10px"
		data-options="href:'admin/CompletedFormManage_ACTION_CONSOLE.do?processId=${processId}'">
	</div>
	<div title="流程的一生" style="padding: 10px"
		data-options="href:'admin/ActExApproval_ACTION_CONSOLE.do?processId=${processId}'">
	</div>
	<c:if test="${handleType==0}">
		<div title="审批" style="padding: 10px"
			data-options="href:'admin/ActExApproval_ApprovalInfoshow.do?taskId=${taskId}&processId=${processId}&taskDefKey=${taskDefKey}&taskName=${taskName}&pageset.pageType=1'">
		</div>
	</c:if>
	
</div>
<script type="text/javascript">
    
	$(function() {
		$('#pActExProcessHandletabsId').tabs( {
			fit : true,
			border : false,
			onSelect:function(title){  
		        if(title == '已填业务表单' || title == '可填业务表单'){
		        
		        	//当前tab  刷新
					var current_tab = $('#pActExProcessHandletabsId').tabs('getSelected');  
		        	$('#pActExProcessHandletabsId').tabs('update',{  
					     tab:current_tab,  
					     options : {  
					          content : '',  
					     }  
					}); 
		        }
		    } 
		});
		
	});
</script>