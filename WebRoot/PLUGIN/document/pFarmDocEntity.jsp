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
				文档分类:
			</td>
			<td colspan="3">
				${doctype.name }
				<input type="hidden" name="doctype.id" value="${doctype.id}">
			</td>
		</tr>
		<tr>
			<td class="title">
				标题:
			</td>
			<td colspan="3">
				<input type="text" style="width: 80%" class="easyui-validatebox"
					data-options="required:true,validType:',maxLength[128]'"
					id="entity_title" name="entity.title" value="${entity.title}">
			</td>
		</tr>
		<tr>
			<td class="title">
				短标题:
			</td>
			<td>
				<input type="text" class="easyui-validatebox"
					data-options="required:false,validType:',maxLength[32]'"
					id="entity_shorttitle" name="entity.shorttitle"
					value="${entity.shorttitle}">

			</td>
			<td class="title">
				发布时间:
			</td>
			<td>
				<input type="text" class="easyui-validatebox"
					data-options="validType:',maxLength[20]'" id="entity_pubtime"
					name="entity.pubtime" value="${entity.pubtime}">
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div style="margin: 10px;">
					<textarea name="content" id="contentId"
						style="height: 400px; width: 100%;">${entity.texts.text1}</textarea>
				</div>
			</td>
		</tr>

		<tr>
			<td colspan="4">
				<div id="p" class="easyui-panel" title="详细信息"
					style="background: #fafafa;"
					data-options="iconCls:'icon-save',collapsed:true,collapsible:true">
					<table width="100%">
						<tr>
							<td class="title">
								摘要:
							</td>
							<td colspan="3">
								<textarea rows="3" cols="32" class="easyui-validatebox"
									data-options="required:false,validType:',maxLength[128]'"
									id="entity_docdescribe" name="entity.docdescribe">${entity.docdescribe}</textarea>
							</td>
						</tr>
						<tr>
							<td class="title">
								作者:
							</td>
							<td>
								<input type="text" class="easyui-validatebox"
									data-options="required:false,validType:',maxLength[32]'"
									id="entity_author" name="entity.author"
									value="${entity.author}">

							</td>
							<td class="title">
								内容图:
							</td>
							<td>
								<input type="text" class="easyui-validatebox"
									data-options="required:false,validType:',maxLength[16]'"
									id="entity_imgid" name="entity.imgid" value="${entity.imgid}">

							</td>
						</tr>
						<tr>
							<td class="title">
								内容类型:
							</td>
							<td>
								<select name="entity.domtype" id="entity_domtype"
									val="${entity.domtype}">
									<PF:OptionDictionary index="FARM_DOC_TYPE" isTextValue="0" />
								</select>
							</td>
							<td class="title">
								状态:
							</td>
							<td>
								<select name="entity.state" id="entity_state"
									val="${entity.state}">
									<option value="1">
										开放
									</option>
									<option value="0">
										禁用
									</option>
									<option value="2">
										审查
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">
								tag标签:
							</td>
							<td colspan="3">
								<textarea rows="3" cols="32" class="easyui-validatebox"
									data-options="required:false,validType:',maxLength[128]'"
									id="entity_tagkey" name="entity.tagkey">${entity.tagkey}</textarea>
							</td>
						</tr>
						<tr>
							<td class="title">
								来源:
							</td>
							<td>
								<input type="text" class="easyui-validatebox"
									data-options="required:false,validType:',maxLength[128]'"
									id="entity_source" name="entity.source"
									value="${entity.source}">

							</td>
							<td class="title">
								置顶等级:
							</td>
							<td>
								<input type="text" class="easyui-validatebox"
									data-options="required:false,validType:''" id="entity_topleve"
									name="entity.topleve" value="${entity.topleve}">

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

					</table>
				</div>
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
	var editor;
	var submitAddAction = 'admin/FarmDocaddCommit.do';
	var submitEditAction = 'admin/FarmDoceditCommit.do';
	var currentPageType = '${pageset.pageType}';
	var DOM_DATAFORM_FORM = '#dom_var_entity';
	var LINK_FORM_ADD = '#dom_var_add_entity';
	var LINK_FORM_EDIT = '#dom_var_edit_entity';
	var LINK_FORM_CANCEL = '#dom_var_cancle_form';
	$(function() {
		initEntity();
		$('#entity_pubtime').datetimebox( {
			required : true,
			editable : false,
			showSeconds : true
		});
		editor = KindEditor.create('textarea[name="content"]', {
			resizeType : 1,
			uploadJson : basePath+'admin/FPupload.do',
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
					'bold', 'italic', 'underline', 'removeformat', '|',
					'justifyleft', 'justifycenter', 'justifyright',
					'insertorderedlist', 'insertunorderedlist', '|',
					'emoticons', 'image', 'link' ]
		});
	});
	function initEntity() {
		var submitForm = $(DOM_DATAFORM_FORM).SubmitForm( {
			pageType : currentPageType,
			gridId : DOM_GRID_TABLE,
			currentWindow : DOM_WINDOW_DIV
		});
		$(LINK_FORM_ADD).bind('click', function() {
			editor.sync();
			checkDocText();
			if (!isDisableClass(this)) {
				submitForm.postSubmit(submitAddAction, null);
			}
		});
		$(LINK_FORM_EDIT).bind('click', function() {
			editor.sync();
			checkDocText();
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