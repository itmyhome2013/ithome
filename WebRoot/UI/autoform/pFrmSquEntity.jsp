<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formfrmsqu">
			<input type="hidden" name="entity.id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						id:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[16]'"
							id="entity_id" name="entity.id" value="${entity.id}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						type:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[10]'"
							id="entity_type" name="entity.type" value="${entity.type}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						num:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:''"
							id="entity_num" name="entity.num" value="${entity.num}">
							
					</td>
				</tr>
			<c:if test="${pageset.pageType==1}"><!--新增-->
			</c:if>
			<c:if test="${pageset.pageType==2}"><!--修改-->
			</c:if>
			<c:if test="${pageset.pageType==0}"><!--浏览-->
			</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_add_entityfrmsqu" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_entityfrmsqu" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfrmsqu" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfrmsqu = 'admin/FrmSquaddCommit.do';
	var submitEditActionfrmsqu = 'admin/FrmSqueditCommit.do';
	var currentPageTypefrmsqu = '${pageset.pageType}';
	var submitFormfrmsqu;
	$(function() {
		//表单组件对象
		submitFormfrmsqu = $('#dom_formfrmsqu').SubmitForm( {
			pageType : currentPageTypefrmsqu,
			grid : gridfrmsqu,
			currentWindowId : 'winfrmsqu'
		});
		//关闭窗口
		$('#dom_cancle_formfrmsqu').bind('click', function() {
			$('#winfrmsqu').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfrmsqu').bind('click', function() {
			submitFormfrmsqu.postSubmit(submitAddActionfrmsqu);
		});
		//提交修改数据
		$('#dom_edit_entityfrmsqu').bind('click', function() {
			submitFormfrmsqu.postSubmit(submitEditActionfrmsqu);
		});
	});
	//-->
</script>