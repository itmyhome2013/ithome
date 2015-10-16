<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--组织机构-->
<form id="dom_var_entity">
	<div class="TableTitle">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
		<input type="hidden" name="entity.id" value="${entity.id}">
	</div>
	<table class="editTable">
		<tr>
			<td class="title">
				上级机构:
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
			<td colspan="1">
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:'maxLength[32]'"
					id="entity_name" name="entity.name" value="${entity.name}">
			</td>
			<td class="title">
				排序号:
			</td>
			<td colspan="1">
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:'integerAndMaxLength[3]'"
					id="entity_sort" name="entity.sort" value="${entity.sort}">

			</td>
		</tr>
		<tr>
			<td class="title">
				状态:
			</td>
			<td colspan="3">
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
				<textarea rows="3" cols="32" class="easyui-validatebox"
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
						<a id="dom_var_add_entity" href="javascript:void(0)"
							class="easyui-linkbutton">增加</a>
					</c:if>
					<c:if test="${pageset.pageType==2}">
						<a id="dom_var_edit_entity" href="javascript:void(0)"
							class="easyui-linkbutton">修改</a>
					</c:if>
					<a id="dom_var_cancle_form" href="javascript:void(0)"
						class="easyui-linkbutton" style="color: #000000;">取消</a>
				</div>
			</th>
		</tr>
	</table>
</form>
<script type="text/javascript">
	var submitAddAction = 'admin/AloneOrganizationaddCommit.do';
	var submitEditAction = 'admin/AloneOrganizationeditCommit.do';
	var currentPageType = '${pageset.pageType}';
	var DOM_DATAFORM_FORM = '#dom_var_entity';
	var LINK_FORM_ADD = '#dom_var_add_entity';
	var LINK_FORM_EDIT = '#dom_var_edit_entity';
	var LINK_FORM_CANCEL = '#dom_var_cancle_form';
	$(function() {
		initEntity();
	});
	//-->
</script>