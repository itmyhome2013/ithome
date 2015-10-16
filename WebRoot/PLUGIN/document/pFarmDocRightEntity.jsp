<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--文档管理-->
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
				编辑权限:
			</td>
			<td colspan="3">
				<select name="entity.writepop" id="entity_writepop"
					val="${entity.writepop}">
					<option value="1">
						公开、
					</option>
					<option value="0">
						本人、
					</option>
					<option value="2">
						小组、
					</option>
					<option value="3">
						禁止
					</option>
				</select>
			</td>
			<td class="title">
				阅读权限:
			</td>
			<td colspan="3">
				<select name="entity.readpop" id="entity_readpop"
					val="${entity.readpop}">
					<option value="1">
						公开
					</option>
					<option value="0">
						本人
					</option>
					<option value="2">
						小组
					</option>
					<option value="3">
						禁止
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<th colspan="8">
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
	var editor;
	var submitAddAction = '';
	var submitEditAction = 'admin/FarmDocRighteditCommit.do';
	var currentPageType = '${pageset.pageType}';
	var DOM_DATAFORM_FORM = '#dom_var_entity';
	var LINK_FORM_ADD = '#dom_var_add_entity';
	var LINK_FORM_EDIT = '#dom_var_edit_entity';
	var LINK_FORM_CANCEL = '#dom_var_cancle_form';
	$(function() {
		initEntity();
	});
	function initEntity() {
		var submitForm = $(DOM_DATAFORM_FORM).SubmitForm( {
			pageType : currentPageType,
			gridId : DOM_GRID_TABLE,
			currentWindow : DOM_WINDOW_DIV
		});
		$(LINK_FORM_ADD).bind('click', function() {
			if (!isDisableClass(this)) {
				submitForm.postSubmit(submitAddAction, null);
			}
		});
		$(LINK_FORM_EDIT).bind('click', function() {
			if (!isDisableClass(this)) {
				submitForm.postSubmit(submitEditAction, null);
			}
		});
		$(LINK_FORM_CANCEL).bind('click', function() {
			if (!isDisableClass(this)) {
				cancel_fun(DOM_WINDOW_DIV);
			}
		});

	}
	function checkDocText() {
		if ($('#contentId').val() == null || $('#contentId').val() == '') {
			$.messager.alert('提示', '请录入文档!');
		}
	}
	//-->
</script>