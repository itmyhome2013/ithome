<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--数据实体-->
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
				名称:
			</td>
			<td colspan="3">
				<textarea rows="1" cols="32" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[64]'"
					id="entity_name" name="entity.name">${entity.name}</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">
				键:
			</td>
			<td colspan="3">
				<c:if test="${pageset.pageType!=2}">
					<textarea rows="1" cols="32" class="easyui-validatebox"
						data-options="required:true,validType:'ajaxValidate[\'\']'"
						id="entity_entityindex" name="entity.entityindex">${entity.entityindex}</textarea>
				</c:if>
				<c:if test="${pageset.pageType==2}">
					<textarea rows="1" cols="32" class="easyui-validatebox"
						data-options="required:true,validType:'ajaxValidate[\'${ids}\']'"
						id="entity_entityindex" name="entity.entityindex">${entity.entityindex}</textarea>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="title">
				类型:
			</td>
			<td colspan="3">
				<select id="entity_type" name="entity.type" val="${entity.type }">
					<option value="1">序列</option>
					<option value="0">树</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="title">
				备注:
			</td>
			<td colspan="3">
				<textarea rows="2" cols="32" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[120]'"
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
	var submitAddAction = 'admin/ALONEDictionary_ADD_SUBMIT.do';
	var submitEditAction = 'admin/ALONEDictionary_EDIT_SUBMIT.do';
	var currentPageType = '${pageset.pageType}';
	var DOM_DATAFORM_FORM = '#dom_var_entity';
	var LINK_FORM_ADD = '#dom_var_add_entity';
	var LINK_FORM_EDIT = '#dom_var_edit_entity';
	var LINK_FORM_CANCEL = '#dom_var_cancle_form';
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {
			ajaxValidate : {
				validator : function(value, para) {
					var resultVar = $.ajax( {
						url : 'admin/ALONEDictionary_validateIsRepeatKey.do',
						dataType : "json",
						data : {
							key : value,
							ids : para[0]
						},
						async : false,
						cache : false,
						type : "post"
					}).responseText;
					return $.parseJSON(resultVar).isRepeatKey == false;
				},
				message : "该KEY已经存在"
			}
		});
		initEntity();
	});
	//-->
</script>