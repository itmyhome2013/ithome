<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--文件-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<form id="dom_var_entity">
			<div class="TableTitle">
				<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
				<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
				<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
				<input type="hidden" name="entity.id" value="${entity.id}">
			</div>
			 <img id="showImg" alt="" src="admin/readImage.do?ids=${ids }">  
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_var_cancle_form" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#dom_var_cancle_form").bind('click',function(){
		$('#dom_var_window').window('close');
	})
})
</script>