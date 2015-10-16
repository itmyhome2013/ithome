<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--留言板-->
<form id="dom_var_entity">
	<div class="TableTitle">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
		<input type="hidden" name="entity.id" value="${entity.id}">
	</div>
	<table class="editTable">
		<c:if test="${pageset.pageType!=0}">
			<tr>
				<td class="title">
					接收人:
				</td>
				<td colspan="3">
					<input type="text" class="easyui-validatebox" readonly="readonly"
						data-options="required:true,validType:',maxLength[16]'"
						id="entity_readuserid" value="${entity.readuserid}">
					<input type="hidden" name="entity.readuserid" id="entityreaduserid">
					<a id="form_AloneUser_a_ChoosePop" href="javascript:void(0)"
						class="easyui-linkbutton" style="color: #000000;">选择</a>
					<div id="win_AloneUser_ChoosPop" class="easyui-window"
						style="width: 600px; height: 350px;" title='选择用户'
						data-options="closed:true,modal:true,href:'admin/AloneUser_ACTION_ALERT.do'"></div>
					<script type="text/javascript">
	$(function() {
		initChoose_Window_Default('#form_AloneUser_a_ChoosePop',
				'#win_AloneUser_ChoosPop', function(rows) {
					$('#entity_readuserid').val(rows[0].A_NAME);
					$('#entityreaduserid').val(rows[0].ID);
				});
	});
</script>
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="title">
				主题:
			</td>
			<td colspan="3">
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[32]'"
					id="entity_title" name="entity.title" value="${entity.title}">

			</td>
		</tr>
		<tr>
			<td class="title">
				内容:
			</td>
			<td colspan="3">
				<textarea rows="3" cols="32" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[128]'"
					id="entity_content" name="entity.content">${entity.content}</textarea>
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
	var submitAddAction = 'admin/FarmDocmessageaddCommit.do';
	var submitEditAction = 'admin/FarmDocmessageeditCommit.do';
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