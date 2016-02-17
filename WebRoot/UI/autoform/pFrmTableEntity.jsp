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
		<form id="dom_formfrmtable">
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
						enname:
					</td>
					<td colspan="3">
							<textarea rows="3" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[50]'"
							id="entity_enname" name="entity.enname">${entity.enname}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						cnname:
					</td>
					<td colspan="3">
							<textarea rows="3" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[50]'"
							id="entity_cnname" name="entity.cnname">${entity.cnname}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						state:
					</td>
					<td colspan="3">
							<select name="entity.state" id="entity_state"  val="${entity.state}"><option value="0">NONE</option></select>
					</td>
				</tr>
				<tr>
					<td class="title">
						type:
					</td>
					<td colspan="3">
							<select name="entity.type" id="entity_type"  val="${entity.type}"><option value="0">NONE</option></select>
					</td>
				</tr>
				<tr>
					<td class="title">
						url:
					</td>
					<td colspan="3">
							<textarea rows="3" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[250]'"
							id="entity_url" name="entity.url">${entity.url}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						sort:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[2.5]'"
							id="entity_sort" name="entity.sort" value="${entity.sort}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						owner:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[5]'"
							id="entity_owner" name="entity.owner" value="${entity.owner}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						flag:
					</td>
					<td colspan="3">
							<select name="entity.flag" id="entity_flag"  val="${entity.flag}"><option value="0">NONE</option></select>
					</td>
				</tr>
				<tr>
					<td class="title">
						cretime:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[10]'"
							id="entity_cretime" name="entity.cretime" value="${entity.cretime}">
							
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
			<a id="dom_add_entityfrmtable" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_entityfrmtable" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfrmtable" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfrmtable = 'admin/FrmTableaddCommit.do';
	var submitEditActionfrmtable = 'admin/FrmTableeditCommit.do';
	var currentPageTypefrmtable = '${pageset.pageType}';
	var submitFormfrmtable;
	$(function() {
		//表单组件对象
		submitFormfrmtable = $('#dom_formfrmtable').SubmitForm( {
			pageType : currentPageTypefrmtable,
			grid : gridfrmtable,
			currentWindowId : 'winfrmtable'
		});
		//关闭窗口
		$('#dom_cancle_formfrmtable').bind('click', function() {
			$('#winfrmtable').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfrmtable').bind('click', function() {
			submitFormfrmtable.postSubmit(submitAddActionfrmtable);
		});
		//提交修改数据
		$('#dom_edit_entityfrmtable').bind('click', function() {
			submitFormfrmtable.postSubmit(submitEditActionfrmtable);
		});
	});
	//-->
</script>