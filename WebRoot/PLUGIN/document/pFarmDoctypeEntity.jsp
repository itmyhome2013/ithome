<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--文档分类-->
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
				上级分类:
			</td>
			<td colspan="3">
				${fatherEntity.name }
				<input type="hidden" name="entity.parentid" value="${fatherEntity.id}">
			</td>
		</tr>
		<tr>
			<td class="title">
				分类名称:
			</td>
			<td>
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[64]'"
					id="entity_name" name="entity.name" value="${entity.name}">
			</td>
			<td class="title">
				状态:
			</td>
			<td>
				<select name="entity.pstate" id="entity_pstate"
					val="${entity.pstate}">
					<option value="1">
						正常
					</option>
					<option value="0">
						禁用
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="title">
				分类模板:
			</td>
			<td>
				<select name="entity.typemod" id="entity_typemod"
					val="${entity.typemod}">
					<PF:OptionDictionary index="FARM_DOCTYE_TYPEMOD" isTextValue="0" />
				</select>
			</td>
			<td class="title">
				内容模板:
			</td>
			<td>
				<select name="entity.contentmod" id="entity_contentmod"
					val="${entity.contentmod}">
					<PF:OptionDictionary index="FARM_DOCTYE_DOCMOD" isTextValue="0" />
				</select>
			</td>
		</tr>
		<tr>
			<td class="title">
				排序:
			</td>
			<td>
				<input type="text" class="easyui-validatebox"
					data-options="required:true,validType:'integer'" id="entity_sort"
					name="entity.sort" value="${entity.sort}">

			</td>
			<td class="title">
				类型:
			</td>
			<td>
				<select name="entity.type" id="entity_type" val="${entity.type}">
					<option value="1">
						内容
					</option>
					<option value="2">
						建设
					</option>
					<option value="3">
						结构
					</option>
					<option value="4">
						链接
					</option>
					<option value="5">
						单页
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="title">
				外部链接:
			</td>
			<td colspan="3">
				<textarea rows="1" cols="32" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[128]'"
					id="entity_linkurl" name="entity.linkurl">${entity.linkurl}</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">
				meta标题:
			</td>
			<td colspan="3">
				<textarea rows="2" cols="32" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[128]'"
					id="entity_metatitle" name="entity.metatitle">${entity.metatitle}</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">
				meta关键字:
			</td>
			<td colspan="3">
				<textarea rows="2" cols="32" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[128]'"
					id="entity_metakey" name="entity.metakey">${entity.metakey}</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">
				meta描述:
			</td>
			<td colspan="3">
				<textarea rows="2" cols="32" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[128]'"
					id="entity_metacontent" name="entity.metacontent">${entity.metacontent}</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">
				备注:
			</td>
			<td colspan="3">
				<textarea rows="3" cols="32" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[64]'"
					id="entity_pcontent" name="entity.pcontent">${entity.pcontent}</textarea>
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
	var submitAddAction = 'admin/FarmDoctypeaddCommit.do';
	var submitEditAction = 'admin/FarmDoctypeeditCommit.do';
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