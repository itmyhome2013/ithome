<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--系统日志-->
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
						生成时间:
					</td>
					<td colspan="3">
							<input type="text" class="easyui-validatebox" data-options="required:true,validType:',maxLength[7]'"
							id="entity_ctime" name="entity.ctime" value="${entity.ctime}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						日志:
					</td>
					<td colspan="3">
							<textarea rows="3" cols="32" class="easyui-validatebox" data-options="required:true,validType:',maxLength[256]'"
							id="entity_describes" name="entity.describes">${entity.describes}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						操作人:
					</td>
					<td colspan="3">
							<input type="text" class="easyui-validatebox" data-options="required:true,validType:',maxLength[16]'"
							id="entity_appuser" name="entity.appuser" value="${entity.appuser}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						日志级别:
					</td>
					<td colspan="3">
							<input type="text" class="easyui-validatebox" data-options="required:false,validType:',maxLength[16]'"
							id="entity_level" name="entity.level" value="${entity.level}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						方法:
					</td>
					<td colspan="3">
							<textarea rows="3" cols="32" class="easyui-validatebox" data-options="required:false,validType:',maxLength[64]'"
							id="entity_method" name="entity.method">${entity.method}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						类:
					</td>
					<td colspan="3">
							<textarea rows="3" cols="32" class="easyui-validatebox" data-options="required:false,validType:',maxLength[64]'"
							id="entity_classname" name="entity.classname">${entity.classname}</textarea>
					</td>
				</tr>
		<c:if test="${pageset.pageType==1}"><!--新增-->
		</c:if>
		<c:if test="${pageset.pageType==2}"><!--修改-->
		</c:if>
		<c:if test="${pageset.pageType==0}"><!--浏览-->
		</c:if>
		<tr>
			<th colspan="4">
				<div class="div_button">
					<c:if test="${pageset.pageType==1}">
						<a id="dom_var_add_entity" href="javascript:void(0)" class="easyui-linkbutton">增加</a>
					</c:if>
					<c:if test="${pageset.pageType==2}">
						<a id="dom_var_edit_entity" href="javascript:void(0)" class="easyui-linkbutton">修改</a>
					</c:if>
					<a id="dom_var_cancle_form" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">取消</a>
				</div>
			</th>
		</tr>
	</table>
</form>
<script type="text/javascript">
	var submitAddAction = 'admin/AloneApplogaddCommit.do';
	var submitEditAction = 'admin/AloneApplogeditCommit.do';
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