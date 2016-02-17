<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<c:if test="${pageset.pageType==0}">
	<style>
<!--
.adrressBoxClass>span {
	float: left;
}

.adrressBoxClass>div {
	float: left;
}
-->
</style>
</c:if>
<!--表单基础信息-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<form id="dom_formfrmtable">
			<input type="hidden" name="entity.id" id="entity_id" value="${entity.id}">
			<table class="editTable">
			
				<tr>
					<td class="title">
						表单名称:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[30]']" 
					    	id="entity_cnname" name="entity.cnname" value="${entity.cnname }">
					</td>
					<td class="title">
						英文名:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[30]']" 
					    	id="entity_enname" name="entity.enname" value="${entity.enname }">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						类型:
					</td>
					<td>
						<select id="entity_type" name="entity.type"  val="${entity.type}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="TEST" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						URL:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[100]']" 
					    	id="entity_url" name="entity.url" value="${entity.url }">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						所属者:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[30]']" 
					    	id="entity_owner" name="entity.owner" value="${entity.owner }">
					</td>
					<td class="title">
						排序:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer','maxLength[5]']" 
					    	id="entity_sort" name="entity.sort" value="${entity.sort }">
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
				<c:if test="${pageset.pageType==0}">
				
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
				<a id="dom_edit_entityfrmtable" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_edit_entityfrmtable" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_formfrmtable" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitEditActionFrmtable = 'admin/FrmTableeditCommit.do';
	var currentPageTypeFrmtable = '${pageset.pageType}';
	var submitFormFrmTable;
	$(function() {
		//表单组件对象
		submitFormFrmTable = $('#dom_formfrmtable').SubmitForm( {
			pageType : currentPageTypeFrmtable,
			grid : gridfrmtable,
			currentWindowId : ''
		});
		//关闭窗口
		$('#dom_cancle_formfrmtable').bind('click', function() {
			$('#winfrmtable').window('close');
		});
		
	
		//提交新增和修改数据
		$('#dom_edit_entityfrmtable').bind('click', function() {
			submitFormFrmTable.postSubmit(submitEditActionFrmtable,function(flag){
				$('#FrmTableIDVarId').val(flag.entity.id);
				$('#entity_id').val(flag.entity.id);
			});
		});


	});
	
	//-->
</script>