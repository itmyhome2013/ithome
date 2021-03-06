<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理-->
<div class="easyui-layout" data-options="fit:true">
<!-- <div class="TableTitle" data-options="region:'north',border:false">
		模型信息
	</div> -->
	<div data-options="region:'center'">
		<form id="dom_formPcsModel">
			<table class="editTable">
				<tr>
					<td class="title">
						模型名称: 
					</td>
					<td>
						<input type="text" class="easyui-validatebox" style="width: 180px;"   name="entity.name"  data-options="validType:'maxLength[64]'" required="required" value="">
					</td>
				</tr>
				<tr>
					<td class="title">
						模型KEY:
					</td>
					<td>
						<input type="text" class="easyui-validatebox"  style="width: 180px;"   name="entity.key"  data-options="validType:'maxLength[64]'" required="required" value="">
					</td>
				</tr>
					<tr>
					<td class="title">
						模型描述:
					</td>
					<td >
						<textarea class="easyui-validatebox" style="width: 320px;" name="entity.metaInfo" data-options="validType:'maxLength[400]'"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_add_entity" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_entity" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_form" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">关闭</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionPcsModel = 'admin/pcssaveProcessModel.do';
	var currentPageType= '${pageset.pageType}';
	var submitFormPcsModel;
	$(function() {
		//表单组件对象
		submitFormPcsModel = $('#dom_formPcsModel').SubmitForm( {
			pageType : currentPageType,
			grid : gridpcsmodelmanage,
			currentWindowId : 'pcsmodelwin'
		});
		//关闭窗口
		$('#dom_cancle_form').bind('click', function() {
			$('#pcsmodelwin').window('close');
		});
		
		//提交新增数据
		$('#dom_add_entity').bind('click', function() {
			submitFormPcsModel.postSubmit(submitAddActionPcsModel,function(flag){
			});
		});
		

	});
	//-->
</script>