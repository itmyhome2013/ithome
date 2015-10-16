<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--系统菜单-->
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
				上级目录:
			</td>
			<td colspan="3">
				${fatherEntity.name}
				<input type="hidden" name="fatherEntity.id"
					value="${fatherEntity.id}" />
			</td>
		</tr>
		<tr>
			<td class="title">
				名称:
			</td>
			<td>
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:'maxLength[32]'"
					id="entity_name" name="entity.name" value="${entity.name}">

			</td>
			<td class="title">
				排序号:
			</td>
			<td>
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:'integerAndMaxLength[3]'"  id="entity_sort"
					name="entity.sort" value="${entity.sort}">

			</td>
		</tr>
		<tr>
			<td class="title">
				类型:
			</td>
			<td>
				<select name="entity.type" id="entity_type" val="${entity.type}">
					<option value="1">
						构造
					</option>
					<option value="0">
						实体
					</option>
					<option value="3">
						功能
					</option>
				</select>
			</td>
			<td class="title">
				状态:
			</td>
			<td>
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
				图标:
			</td>
			<td colspan="3">
				<input type="hidden" id="entity_img_id" name="entity.img" value="${entity.img}">
				<a id="chooseImg"  class="easyui-linkbutton" data-options="iconCls:'${entity.img}'">选择</a>  
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
		<tr id="URL_TR_DOM">
			<td class="title">
				资源:
			</td>
			<td colspan="3">
				<input type="text" class="easyui-validatebox" style="width: 380px;"
					data-options="required:false,validType:',maxLength[64]'"
					id="entity_action_title" name="action.url" value="${action.url}">
				<input type="hidden" value="${entity.action}" name="entity.action">
				<c:if test="${pageset.pageType!=0}">
					<!--非浏览-->
					<input type="hidden" name="action.id" id="entity_action_value">
					<a id="form_AloneAction_a_ChoosePop" href="javascript:void(0)"
						class="easyui-linkbutton" style="color: #000000;">选择</a>
				</c:if>
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
	var submitAddAction = 'admin/ALONEMenu_ADD_SUBMIT.do';
	var submitEditAction = 'admin/ALONEMenu_EDIT_SUBMIT.do';
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
<!-- 选择权限 -->
<div id="win_AloneAction_ChoosPop" class="easyui-window"
	style="width: 550px; height: 350px;"
	data-options="closed:true,modal:true,href:'admin/AloneAction_ACTION_ALERT.do'"></div>
<div id="windowChooseImg"></div>
<script type="text/javascript">
	$(function() {
		initChoose_Window_Default('#form_AloneAction_a_ChoosePop',
				'#win_AloneAction_ChoosPop', function(rows) {
					$('#entity_action_value').attr('value', rows[0].ID);
					$('#entity_action_title').attr('value', rows[0].URL);
				});
		$('#entity_type').bind("change", function() {
			initType($(this).attr('value'));
		});
		initType($('#entity_type').attr('value'));
		$('#chooseImg').bind('click',function(){
			openWindow("#windowChooseImg", "300", "600",true,"admin/ALONEMenu_ACTION_cssIcon.do","选择图标");
		});
	});
	// 依据类型设置URL的显示和隐藏
	function initType(type) {
		if (type == '1') {
			$('#URL_TR_DOM').hide();
		}
		if (type == '0') {
			$('#URL_TR_DOM').show();
		}
		if (type == '3') {
			$('#URL_TR_DOM').show();
		}
	}
</script>