<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<c:if test="${pageset.pageType==0}">
<style>
<!--
.adrressBoxClass>span {
	float: left;
}

.adrressBoxClass>div {
	float: left;
}
-->
</style>
</c:if>
<!--人口基础信息-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<c:if test="${pageset.pageType==1}"></c:if>
		<c:if test="${pageset.pageType==2}"></c:if>
		<c:if test="${pageset.pageType==0}"></c:if>
		<form id="dom_formperson">
			<input type="hidden" name="entity.id" id="entity_id" value="${entity.id}">
			<table class="editTable">
							
				<tr>
					<td class="title" style="text-align: left;color: red">基本信息</td>
					<td colspan="3"></td>
				</tr>			
							
				<tr>
					<td class="title">
						居民姓名:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[15]']"
							id="entity_name" name="entity.name" value="${entity.name}">
					</td>
					<td class="title">
						手机号码:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[15]']"
							id="entity_mobile" name="entity.mobile" value="${entity.mobile}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						出生地址:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_address" name="entity.address" value="${entity.address}">
					</td>
					<td class="title">
						备注:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_note" name="entity.note" value="${entity.note}">
					</td>
				</tr>
				
				<c:if test="${pageset.pageType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.pageType==2}">
					<!--修改-->
				</c:if>
			
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
				<a id="dom_add_entityperson" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_add_entityperson" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_formperson" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionperson = 'admin/PersonaddCommit.do';
	var submitEditActionperson = 'admin/PersonEditCommit.do';
	var currentPageTypeperson = '${pageset.pageType}';
	var submitFormperson;
	$(function() {
		//表单组件对象
		submitFormperson = $('#dom_formperson').SubmitForm( {
			pageType : currentPageTypeperson,
			grid : gridperson,
			currentWindowId : 'winperson'
		});
		//关闭窗口
		$('#dom_cancle_formperson').bind('click', function() {
			$('#winperson').window('close');
		});
		//提交新增数据
		$('#dom_add_entityperson').bind('click',function() {
			submitFormperson.postSubmit(submitEditActionperson, function(flag) {
					
				});
		});	
	
	});
	
	//-->
</script>
<!-- 
 -->