<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<c:if test="${pageset.pageType==1}">预览</c:if>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formfrmfield">
			<table class="editTable">
				<PF:GenerateForm tableId="${tableid}" keyId=""/>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	var submitFormfrmfield;
	$(function() {
		//表单组件对象
		submitFormfrmfield = $('#dom_formfrmfield').SubmitForm( {
		});
	});
	//-->
</script>