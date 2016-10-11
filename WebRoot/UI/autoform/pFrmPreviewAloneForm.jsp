<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>表基础信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="" style="width: 930px; margin:auto;" id="layout">
		<div class="easyui-layout" data-options="fit:true">
			<div class="TableTitle" data-options="region:'north',border:false">
				
			</div>
			<div data-options="region:'center'">
				<form id="dom_formfrmfield">
					<input type="hidden" name="tableid" value="${tableid }" />
					<input type="hidden" name="id" value="${ids }" />
					
					<input type="hidden" name="pageset.pageType" value="${pageset.pageType }" />
					<input type="hidden" name="completedformid" value="${completedformid }" />
					<input type="hidden" name="processid" value="${processid }" />
					<input type="hidden" name="pcsfromcfgid" value="${pcsfromcfgid }" />
					<input type="hidden" name="informant" value="${informant }" />
					<input type="hidden" name="formtablename" value="${formtablename }" />
									
					<table class="editTable">
						
						<PF:GenerateForm tableId="${tableid}" keyId="${ids}" />
						
					</table>
				</form>
			</div>
			<div data-options="region:'south',border:false">
				<div class="div_button" style="text-align: center; padding: 4px;">
					<c:if test="${pageset.pageType==1}">
						<a id="dom_add_entityfrmfield" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">提交</a>
					</c:if>
					<c:if test="${pageset.pageType==2}">
						<a id="dom_add_entityfrmfield" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">提交</a>
					</c:if>
				</div>
			</div>
		</div>
	</body>
<script type="text/javascript">
var submitAddActionfrmfield = 'admin/FrmFieldaddCommit.do';
var submitEditActionfrmfield = 'admin/FPFrmOperateFieldeditCommit.do';
var currentPageTypefrmfield = '${pageset.pageType}';
var submitFormfrmfield;
$(function() {
	
	//表单组件对象
	submitFormfrmfield = $('#dom_formfrmfield').SubmitForm( {
		pageType : '${pageset.pageType}',
		grid : '',
		currentWindowId : ''
	});
	
	//提交新增数据
	$('#dom_add_entityfrmfield').bind('click', function() {
		var formData = $("#dom_formfrmfield").serializeObject();
		
		submitFormfrmfield.postSubmit(submitEditActionfrmfield + "?fieldParameter="+JSON.stringify(formData),function(){
			$("input[name='pageset.pageType']").val("2"); //新增成功之后  页面状态改为修改 
		});
	});
});

$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(encodeURI(encodeURI(this.value)) || '');  //转码 
        } else {
            o[this.name] = encodeURI(encodeURI(this.value)) || '';
        }
    });
    return o;
};
//-->
</script>
</html>




