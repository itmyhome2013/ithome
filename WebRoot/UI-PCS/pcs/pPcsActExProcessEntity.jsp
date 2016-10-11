<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理-->
<div class="easyui-layout" data-options="fit:true">
<!-- <div class="TableTitle" data-options="region:'north',border:false">
		模型信息
	</div> -->
	<div data-options="region:'center'">
		<form id="dom_formActExProcess">
		<input type="hidden" name="actExProcessEntity.processid" value="${actExProcessEntity.processid}">
		<input type="hidden" name="actExProcessEntity.createusername" value="${actExProcessEntity.createusername}">
		<input type="hidden" name="actExProcessEntity.createuserid" value="${actExProcessEntity.createuserid }">
		<input type="hidden" name="actExProcessEntity.createdate" value="${actExProcessEntity.createdate}">
		<input type="hidden" name="actExProcessEntity.isdeleted" value="${actExProcessEntity.isdeleted}">
		<input type="hidden" name="actExProcessEntity.procdefkey" value="${actExProcessEntity.procdefkey}">
		<input type="hidden" name="actExProcessEntity.procdefname" value="${actExProcessEntity.procdefname}">

			<table class="editTable">
			   <tr>
					<td class="title">
						流程名称:
					</td>
					<td>
						<input type="text" class="easyui-validatebox" style="width: 180px;"   name="actExProcessEntity.processname"  data-options="validType:'maxLength[64]'" required="required" value="${actExProcessEntity.processname}">
					</td>
				</tr>
				<tr>
					<td class="title">
						紧急程度:
					</td>
					<td>
						<select name="actExProcessEntity.urgency" id="actExProcessEntity_processname" style="width: 180px;" value="${actExProcessEntity.urgency}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="URGENCY" isTextValue="0" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						备注:
					</td>
					<td >
						<textarea class="easyui-validatebox" style="width: 320px;" name="actExProcessEntity.comments" data-options="validType:'maxLength[400]'">${actExProcessEntity.comments}</textarea>
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
 	var startProcessaddCommitAction = 'admin/startProcessaddCommit.do';//添加
	var currentPageType= '${pageset.pageType}';
	var submitFormActExProcess;
	$(function() {
		//表单组件对象
		submitFormActExProcess = $('#dom_formActExProcess').SubmitForm( {
			pageType : currentPageType,
		});
		//关闭窗口
		$('#dom_cancle_form').bind('click', function() {
			$('#startprocesswin').window('close');
		});
		
		 //提交新增数据
		$('#dom_add_entity').bind('click', function() {
			submitFormActExProcess.postSubmit(startProcessaddCommitAction,function(flag){
				$('#startprocesswin').window('close');
			});
		});
	});
	//-->
</script>