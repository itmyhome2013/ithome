<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>请假信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
<body class="easyui-layout" data-options="fit:true">
 <div data-options="region:'center'">
		<form id="dom_formActExApprovaltest">
		  <input type="hidden" name="actExLeavetest.leaveid" value="${actExLeavetest.leaveid}">
		  <input type="hidden" name="actExLeavetest.processid" value="${actExLeavetest.processid}">
		  
		  <input type="hidden" name="actExCompletedform.dataid" value="${actExCompletedform.dataid}">
		  <input type="hidden" name="actExCompletedform.completedformid" value="${actExCompletedform.completedformid}">
		  <input type="hidden" name="actExCompletedform.processid" value="${actExCompletedform.processid}">
		  <input type="hidden" name="actExCompletedform.pcsfromcfgid" value="${actExCompletedform.pcsfromcfgid}">
		  <input type="hidden" name="actExCompletedform.formtablename" value="${actExCompletedform.formtablename}">
			<table class="editTable">
			   <tr>
					<td class="title">
						姓名:
					</td>
					<td>
						<input type="text" class="easyui-validatebox" style="width: 180px;"   name="actExLeavetest.leavename"  data-options="validType:'maxLength[64]'" required="required" value="${actExLeavetest.leavename}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						请假开始时间:
					</td>
					<td>
						<input type="text" class="easyui-validatebox" style="width: 180px;"   name="actExLeavetest.starttime"   required="required" value="${actExLeavetest.starttime}">
					</td>
				</tr>
				<tr>
					<td class="title">
						请假结束时间:
					</td>
					<td>
						<input type="text" class="easyui-validatebox" style="width: 180px;"   name="actExLeavetest.endtime"   required="required" value="${actExLeavetest.endtime}">
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
 	var submitAddActExApprovaltestAction = 'admin/ActExLeavetestAddCommit.do';//添加
 	var submitEditActExApprovaltestAction = 'admin/ActExLeavetestEditCommit.do';//修改
 	var currentPageType= '${pageset.pageType}';
	var submitformActExApprovaltest;
	$(function() {
		//表单组件对象
		submitformActExApprovaltest = $('#dom_formActExApprovaltest').SubmitForm( {
			pageType : currentPageType,
		});
		//关闭窗口
		$('#dom_cancle_ActExApprovaltestform').bind('click', function() {
			parent.$('#PfillFormListLayoutwin').window('close');
		});
		
		 //提交新增数据
		$('#dom_add_ActExApprovaltest').bind('click', function() {
			submitformActExApprovaltest.postSubmit(submitAddActExApprovaltestAction,function(flag){
			});
		});
		 //提交修改数据
		$('#dom_edit_ActExApprovaltest').bind('click', function() {
			submitformActExApprovaltest.postSubmit(submitEditActExApprovaltestAction,function(flag){
			});
		});
	});
	//-->
</script>
</body>
</html>





