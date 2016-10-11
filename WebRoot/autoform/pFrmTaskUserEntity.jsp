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
			<input type="hidden" name="entity.taskusercfgid" id="entity_taskusercfgid" value="${entity.taskusercfgid}">
			<input type="hidden" name="entity.taskkey" id="entity_taskkey" value="${entity.taskkey}">
			<input type="hidden" name="entity.users" id="entity_users" value="${entity.users}">
			<input type="hidden" name="entity.role" id="entity_role" value="${entity.role}">
			<input type="hidden" name="entity.group" id="entity_group" value="${entity.group}">
			<table class="editTable">
				<tr>
					<td class="title" style="text-align: left;">&nbsp;</td>
					<td colspan="1"></td>
				</tr>
				<tr>
					<td class="title">
						人员:
					</td>
					<td>
						<input id="cc_users" name="users" value="" style="width: 220px;">  
					</td>
				</tr>
				<tr>
					<td class="title">
						小组:
					</td>
					<td>
					    <input id="cc_group" name="group" value="" style="width: 220px;">  
					</td>
				</tr>
				
				<tr>
					<td class="title">
						角色:
					</td>
					<td>
					    <input id="cc_role" name="role" value="" style="width: 220px;">  
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
	var submitEditActionFrmtable = 'admin/FrmaskUsereditCommit.do';
	var currentPageTypeFrmtable = '${pageset.pageType}';
	var submitFormFrmTable;
	$(function() {

		var users_url = "admin/FrmQueryAllUser.do";
		$.getJSON(users_url, function(json) {
			$('#cc_users').combobox({
				data : json.jsonResult.rows,
				multiple:true,
				valueField:'ID',  
			    textField:'TEXT'  
			});
			var users = "${users}";
			if(users != ''){
				$('#cc_users').combobox('setValues',users.split(","));
			}
			
		});

		var group_url = "admin/FrmQueryAllGroup.do";
		$.getJSON(group_url, function(json) {
			$('#cc_group').combobox({
				data : json.jsonResult.rows,
				multiple:true,
				valueField:'ID',  
			    textField:'TEXT'  
			});
			var group = "${group}";
			if(group != ''){
				$('#cc_group').combobox('setValues',group.split(","));
			}
		});

		var role_url = "admin/FrmQueryAllRole.do";
		$.getJSON(role_url, function(json) {
			$('#cc_role').combobox({
				data : json.jsonResult.rows,
				multiple:true,
				valueField:'ID',  
			    textField:'TEXT'  
			});
			var role = "${role}";
			if(role != ''){
				$('#cc_role').combobox('setValues',role.split(","));
			}
		});
		
		/*$('#cc').combobox({  
		    url:'admin/FrmQueryAllUser.do',  
		    multiple:true,
		    data : json.rows,
		    valueField:'ID',  
		    textField:'TEXT'  
		});  */
		
		//表单组件对象
		submitFormFrmTable = $('#dom_formfrmtable').SubmitForm( {
			pageType : currentPageTypeFrmtable,
			grid : gridfrmtable,
			currentWindowId : 'winfrmtable'
		});
		//关闭窗口
		$('#dom_cancle_formfrmtable').bind('click', function() {
			$('#winfrmtable').window('close');
		});
		
	
		//提交新增和修改数据
		$('#dom_edit_entityfrmtable').bind('click', function() {

			//人员
			$("#entity_users").val($('#cc_users').combobox('getValues'));
			$("#entity_role").val($('#cc_role').combobox('getValues'));
			$("#entity_group").val($('#cc_group').combobox('getValues'));
			submitFormFrmTable.postSubmit(submitEditActionFrmtable,function(flag){
		
			});
		});


	});
	

	$(function(){
		//$("#entity_taskkey").val("4028818f39ad9ff20139af9d3ab100c1,4028818f39ad9ff20139afa10ab400ca");
		//$(".combo").after("<input type='hidden' class='combo-value' value='4028818f39ad9ff20139af9b938800bd'>");
		var industryCodes = "4028818f39ad9ff20139af9b938800bd,4028818f39ad9ff20139afa10ab400ca";
		
	})
	//-->
</script>