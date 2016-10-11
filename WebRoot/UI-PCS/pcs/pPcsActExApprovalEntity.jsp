<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理-->
	<div class="easyui-layout" data-options="fit:true"  >
	<!-- <div class="TableTitle" data-options="region:'north',border:false">
			网格事件审批页面
		</div> -->
		<div data-options="region:'center'" >
			<form id="dom_formActExApproval" >
				<input type="hidden" id="avtivityinames" name="actExApproval.submitnodekname" value="">
				<input type="hidden"  name="actExApproval.approvalid" value="${actExApproval.approvalid}">
				<input type="hidden"  name="actExApproval.processid" value="${actExApproval.processid}">
				<input type="hidden"  name="actExApproval.approvaltaskkey" value="${actExApproval.approvaltaskkey}">
				<input type="hidden"  name="actExApproval.approvaltaskname" value="${actExApproval.approvaltaskname}">
				<input type="hidden"  name="actExApproval.taskid" value="${actExApproval.taskid}">
				<table class="editTable">
						<tr id="comboxtr" style="display: none">
						<td class="title">
							<input type="radio" name="actExApproval.approvalstate" value="0" checked="checked">同意
							<input type="radio"  name="actExApproval.approvalstate" value="1" >驳回
						</td>
						<td >
						     <span id="combox">
						              <input id="backAvtivity" name="actExApproval.submitnodekey" value="" > 
							</span> 
						</td>
					</tr>
				   <tr>
						<td class="title">
							审批意见
						</td>
						<td >
							<textarea class="easyui-validatebox" style="width: 320px;" name="actExApproval.approvalmessage" data-options="validType:'maxLength[400]'">${actExApproval.approvalmessage}</textarea>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false">
			<div class="div_button" style="text-align: center; padding: 4px;">
			    <c:if test="${pageset.pageType==1}">
				<a id="dom_add_entity_ActExApproval" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">提交</a>
				</c:if>
				<c:if test="${pageset.pageType==2}">
				<a id="dom_edit_entity_ActExApproval" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">保存</a>
				</c:if>
				
				<a id="dom_cancle_form_ActExApproval" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">关闭</a>
			</div>
		</div>
	</div>

<script type="text/javascript">
	var saveApprovalandTaskcompleteaddCommitAction = 'admin/ActExApproval_saveApprovalandTaskcompleteaddCommit.do';//添加
	var findBackactivityIdAction ='admin/ActExApproval_BackactivityList.do';//查询可驳回的节点
	var currentPageType= '${pageset.pageType}';
	$('#combox').hide();
	var submitActExApprovalFrom;
	$(function() {
		$('#backAvtivity').combobox({    
		    url:findBackactivityIdAction+"?taskId=${taskId}",    
		    valueField:'key',    
		    textField:'val',
		    editable:false,
		    onSelect: backAvtivitychangfun,
		    onLoadSuccess:loadSuccessfun
		});  
		
		
		
		$("input:radio[name='actExApproval.approvalstate']").change(function (){ 
			var approvalState=$("input[name='actExApproval.approvalstate']:checked").val();
			if(approvalState==0){
				$('#combox').hide();
			}else{
				var combobox_data = $('#backAvtivity').combobox('getData');  //赋默认值
                if (combobox_data.length > 0) {
                    $("#backAvtivity").combobox('select', combobox_data[0].key);
                }
				$('#combox').show();
			}
	   });
		function loadSuccessfun(){
			var combobox_data = $('#backAvtivity').combobox('getData');
			 if (combobox_data.length > 0) {
					$('#comboxtr').show();
			    } 
		}
		function backAvtivitychangfun(record){
			$("#avtivityinames").val(record.val);
		}
		//表单组件对象
		submitActExApprovalFrom = $('#dom_formActExApproval').SubmitForm( {
			pageType : currentPageType,
		});
		//关闭窗口
		$('#dom_cancle_form_ActExApproval').bind('click', function() {
			$('#PfillFormListLayoutwin').window('close');
		});
		//提交新增数据
		$('#dom_add_entity_ActExApproval').bind('click', function() {
			var approvalState=$("input[name='actExApproval.approvalstate']:checked").val();
			if(approvalState==0){
				//上报前检查是否已填必填表单
				var url="admin/ActExApproval_reportVerify.do?taskId=${actExApproval.taskid}&taskDefKey=${actExApproval.approvaltaskkey}&processId=${actExApproval.processid}";
				$.post(url,
						   function(flag) {
									if (flag.pageset.commitType == 0) {
										submitFrom();
									} else {
										var str = MESSAGE_PLAT.ERROR_SUBMIT
												+ flag.pageset.message;
										$.messager.alert(MESSAGE_PLAT.PROMPT, flag.message, 'info');
		
									}
						});
			}else{
				submitFrom();
			}

		});
		//提交表单
		function submitFrom(){
			submitActExApprovalFrom.postSubmit(saveApprovalandTaskcompleteaddCommitAction,function(flag){
				$('#PfillFormListLayoutwin').window('close');
				$('#dom_datagridDoTask').datagrid('reload');
			});
		}
	});
	
	//-->
</script>