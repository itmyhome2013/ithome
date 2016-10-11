<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>审批信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
<body class="easyui-layout" data-options="fit:true">
 <div data-options="region:'center'">
		<form id="dom_formactExApprovaltest">
		  <input type="hidden" name="actExApprovaltest.approvaltestid" value="${actExApprovaltest.approvaltestid}">
		  <input type="hidden" name="actExApprovaltest.processid" value="${actExApprovaltest.processid}">
		  
		  <input type="hidden" name="actExCompletedform.dataid" value="${actExCompletedform.dataid}">
		  <input type="hidden" name="actExCompletedform.completedformid" value="${actExCompletedform.completedformid}">
		  <input type="hidden" name="actExCompletedform.processid" value="${actExCompletedform.processid}">
		  <input type="hidden" name="actExCompletedform.pcsfromcfgid" value="${actExCompletedform.pcsfromcfgid}">
		  <input type="hidden" name="actExCompletedform.formtablename" value="${actExCompletedform.formtablename}">
			<table class="editTable">
					<tr>
					<td class="title">
						批注:
					</td>
					<td >
						<textarea class="easyui-validatebox" style="width: 320px;" name="actExApprovaltest.approvalmessage" data-options="validType:'maxLength[400]'">${actExApprovaltest.approvalmessage}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_add_ActExApprovaltest" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_ActExApprovaltest" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_ActExApprovaltestform" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">关闭</a>
		</div>
	</div>
	
	
	<script type="text/javascript">
 	var submitAddActExApprovaltestAction = 'admin/ActExApprovaltestAddCommit.do';//添加
 	var submitEditActExApprovaltestAction = 'admin/ActExApprovaltestEditCommit.do';//修改
 	var currentPageType= '${pageset.pageType}';
	var submitformactExApprovaltest;
	$(function() {
		//表单组件对象
		submitformactExApprovaltest = $('#dom_formactExApprovaltest').SubmitForm( {
			pageType : currentPageType,
		});
		//关闭窗口
		$('#dom_cancle_ActExApprovaltestform').bind('click', function() {
			parent.$('#PfillFormListLayoutwin').window('close');
		});
		
		 //提交新增数据
		$('#dom_add_ActExApprovaltest').bind('click', function() {
			submitformactExApprovaltest.postSubmit(submitAddActExApprovaltestAction,function(flag){
			});
		});
		 //提交修改数据
		$('#dom_edit_ActExApprovaltest').bind('click', function() {
			submitformactExApprovaltest.postSubmit(submitEditActExApprovaltestAction,function(flag){
			});
		});
	});
	//-->
</script>
</body>
</html>





