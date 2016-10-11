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
		<form id="dom_formfrmfield">
			<input type="hidden" name="pageset.pageType" value="${pageset.pageType }"/>
			<input type="hidden" name="id" value="${ids}">
			<input type="hidden" id="tableId" name="tableid" value="${tableid }"/>
			<input type="hidden" name="completedForm.dataid" value="${completedForm.dataid}">
			<input type="hidden" name="completedForm.completedformid" value="${completedForm.completedformid}">
			<input type="hidden" name="completedForm.processid" value="${completedForm.processid}">
			<input type="hidden" name="completedForm.pcsfromcfgid" value="${completedForm.pcsfromcfgid}">
			<input type="hidden" name="completedForm.formtablename" value="${completedForm.formtablename}">
			
			<table class="editTable">
				<PF:GenerateForm tableId="${tableid }" keyId="${ids}"/>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_add_ActExApprovaltest" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_add_ActExApprovaltest" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_ActExApprovaltestform" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">关闭</a>
		</div>
	</div>
	
	
<script type="text/javascript">
	var submitFormfrmfield;
	var submitEditActionfrmfield = 'admin/FrmOperateFieldeditCommit.do';
	var currentPageType= '${pageset.pageType}';
	$(function() {
		//表单组件对象
		submitFormfrmfield = $('#dom_formfrmfield').SubmitForm( {
			pageType : currentPageType
		});

		//提交新增数据
		$('#dom_add_ActExApprovaltest').bind('click', function() {
			
			var formData = $("#dom_formfrmfield").serializeObject();
			submitFormfrmfield.postSubmit(submitEditActionfrmfield + "?fieldParameter="+JSON.stringify(formData));
			
		});
	});

	//序列化表单 
	 $.fn.serializeObject = function() {
	        var o = {};
	        var a = this.serializeArray();
	        $.each(a, function() {
	            if (o[this.name]) {
	                if (!o[this.name].push) {
	                    o[this.name] = [o[this.name]];
	                }
	                o[this.name].push(this.value || '');
	            } else {
	                o[this.name] = this.value || '';
	            }
	        });
	        return o;
	    };
	//-->
</script>
</body>
</html>





