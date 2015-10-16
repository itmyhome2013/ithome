<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--数据类型-->
<form id="dom_dte_var_entity">
	<div class="TableTitle">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
		<input type="hidden" name="entity.id" value="${entity.id}">
	</div>
	<table class="editTable">
		<tr>
			<td class="title">
				上级节点:
			</td>
			<td colspan="3">
				${parentName}
				<input type="hidden" class="easyui-validatebox"
					data-options="required:false" id="entity_parentid"
					name="entity.parentid" value="${entity.parentid}">
			</td>
		</tr>
		<tr>
			<td class="title">
				名称:
			</td>
			<td colspan="3">
				<input type="text" style="width: 430px;" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[64]'"
					id="entity_name" name="entity.name" value="${entity.name}" />
				<input type="hidden" name="entity.entity" value="${entity.entity}">
			</td>
		</tr>
		<tr>
			<td class="title">
				类型:
			</td>
			<td colspan="3">
				<input type="text" style="width: 430px;" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[64]'"
					id="entity_entitytype" name="entity.entitytype"
					value="${entity.entitytype}" />
			</td>
		</tr>
		<tr>
			<td class="title">
				排序号:
			</td>
			<td colspan="1">
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:''" id="entity_sort"
					name="entity.sort" value="${entity.sort}">

			</td>
			<td class="title">
				状态:
			</td>
			<td colspan="1">
				<select name="entity.state" id="entity_state" val="${entity.state}">
					<option value="1">
						可用
					</option>
					<option value="0">
						禁用
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="title">
				备注:
			</td>
			<td colspan="3">
				<textarea rows="3" style="width: 430px;" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[64]'"
					id="entity_comments" name="entity.comments">${entity.comments}</textarea>
			</td>
		</tr>
		<c:if test="${pageset.pageType==1}">
			<!--新增-->
		</c:if>
		<c:if test="${pageset.pageType==2}">
			<!--修改-->
		</c:if>
		<c:if test="${pageset.pageType==0}">
			<!--浏览-->
		</c:if>
		<tr>
			<th colspan="4">
				<div class="div_button">
					<c:if test="${pageset.pageType==1}">
						<a id="dom_var_dte_add_entity" href="javascript:void(0)"
							class="easyui-linkbutton">增加</a>
					</c:if>
					<c:if test="${pageset.pageType==2}">
						<a id="dom_var_dte_edit_entity" href="javascript:void(0)"
							class="easyui-linkbutton">修改</a>
					</c:if>
				</div>
			</th>
		</tr>
	</table>
</form>
<script type="text/javascript">
	var submit_dte_AddAction = 'admin/AloneDictionaryTypeaddCommit.do';
	var submit_dte_EditAction = 'admin/AloneDictionaryTypeeditCommit.do';

	var submit_dteForm;
	$(function() {
		submit_dteForm = $('#dom_dte_var_entity').SubmitForm( {
			pageType : '${pageset.pageType}',
			gridId : '#dom_dT_var_grid',
			currentWindow : '#dom_dT_var_window'
		});
		$('#dom_var_dte_add_entity')
				.bind(
						'click',
						function() {
							if (!isDisableClass(this)) {
								submit_dteForm
										.postSubmit(
												'admin/ALONEDictionaryType_ADD_SUBMIT.do',
												null);
							}
						});
		$('#dom_var_dte_edit_entity').bind(
				'click',
				function() {
					if (!isDisableClass(this)) {
						submit_dteForm.postSubmit(
								'admin/ALONEDictionaryType_EDIT_SUBMIT.do',
								null);
					}
				});
		//$(LINK_FORM_CANCEL).bind('click', function() {
		//	if (!isDisableClass(this)) {
		//		cancel_fun('#dom_dT_var_window');
		//	}
		//});
	});
	//-->
</script>