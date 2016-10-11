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
			<input type="hidden" name="entity.pcsfromcfgid" id="entity_pcsfromcfgid" value="${entity.pcsfromcfgid}">
			<input type="hidden" name="entity.formtableid" id="entity_formtableid" value="${entity.formtableid}">
			<input type="hidden" name="entity.wordurl" id="entity_wordurl" value="${entity.wordurl}">
			<table class="editTable">
				<tr>
					<td class="title" style="text-align: left;">&nbsp;</td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td class="title">
						任务节点KEY:
					</td>
					<td>
					    <select id="entity_taskkey" name="entity.taskkey"  val="${entity.taskkey}" class="easyui-validatebox" data-options="required:true"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionFromTable isTextValue="0" table="ACT_EX_TASK" fieldKey="taskkey" fieldName="taskname"/>
						</select>
					</td>
					<td class="title">
						表单名称:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[30]']" 
					    	id="entity_formname" name="entity.formname" value="${entity.formname }">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						表单类型:
					</td>
					<td>
						<select id="entity_fromtype" name="entity.fromtype"  val="${entity.fromtype}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="FORMTYPE" isTextValue="0" />
						</select>
					</td>
					
					<td class="title" name="url">
						表单URL:
					</td>
					<td name="url">
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['maxLength[100]']" 
					    	id="entity_formurl" name="entity.formurl" value="${entity.formurl }">
					</td>
					
					<td class="title" style="display:none;" name="form">
						选择表单:
					</td>
					<td style="display:none;" name="form">
						<select id="entity_formtablename" name="entity.formtablename"  val="${entity.formtablename}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionFromTable isTextValue="0" table="FRM_TABLE" fieldKey="enname" fieldName="cnname" title="id" />
						</select>
					</td>
					
					<td class="title" style="display:none;" name="word">
						选择文件:
					</td>
					<td style="display:none;" name="word">
						<select id="entity_word" name="entity.word"  val="${entity.word}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				
			
				<tr>
					<td class="title">
						是否必填:
					</td>
					<td>
						<select id="entity_isrequired" name="entity.isrequired"  val="${entity.isrequired}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNULL" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						是否禁用:
					</td>
					<td>
						<select id="entity_isdisable" name="entity.isdisable"  val="${entity.isdisable}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNULL" isTextValue="0" />
						</select>
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
	var submitEditActionFrmtable = 'admin/FrmProcessFormeditCommit.do';
	var currentPageTypeFrmtable = '${pageset.pageType}';
	var submitFormFrmTable;
	$(function() {
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
		
			var wordurl = $("#entity_formtablename option:selected").attr("title");
			$("#entity_formtableid").val(wordurl);
			console.info(wordurl);
		
			submitFormFrmTable.postSubmit(submitEditActionFrmtable,function(flag){
				$('#FrmTableIDVarId').val(flag.entity.id);
				$('#entity_id').val(flag.entity.id);
			});
		});


	});
	
	$(function(){
	
		$("#entity_word").val($("#entity_formtablename").val());
		
		if(${pageset.pageType} == '1'){
			$("#entity_isdisable").val("0");  //是否禁用  默认"否"
		}
		
		if($("#entity_fromtype").val() == '2'){
			$("td[name='word']").show();
			$("td[name='form']").hide();
			$("td[name='url']").hide();
		}else if($("#entity_fromtype").val() == '1'){
			$("td[name='form']").show();
			$("td[name='word']").hide();
			$("td[name='url']").hide();
		}else{
			$("td[name='url']").show();
			$("td[name='form']").hide();
			$("td[name='word']").hide();
		}
	})
	
	$("#entity_fromtype").on("change",function(){
		if($(this).val() == '2'){  //Word文件 
			$("td[name='word']").show();
			$("td[name='form']").hide();
			$("td[name='url']").hide();
		}else if($(this).val() == '1'){  //表单
			$("td[name='form']").show();
			$("td[name='word']").hide();
			$("td[name='url']").hide();
		}else{
			$("td[name='url']").show();
			$("td[name='form']").hide();
			$("td[name='word']").hide();
		}
	})
	
	//-->
</script>