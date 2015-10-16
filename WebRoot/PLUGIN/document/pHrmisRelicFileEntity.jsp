<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--文物附件-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formhrmisrelicfile">
			<input type="hidden" name="entity.id" value="${entity.id}">
			<input type="hidden" id="entity_fileid" name="entity.fileid" value="${entity.fileid}">
			<input type="hidden" name="entity.relicid" value="${entity.relicid}">
			<table class="editTable">
				<tr>
					<td class="title">
						名称:
					</td>
					<td colspan="3">
							<input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:',maxLength[32]'"
							id="entity_name" name="entity.name" value="${entity.name}">
							
					</td>
				</tr>
				<tr>
					<td class="title">
						文件:
					</td>
					<td colspan="3">
					<input type="file"  style="width: 155px;" id="imgFile" name="imgFile" value="">&nbsp;&nbsp;<span style="color:red">最大上传20兆<span>
					</td>
				</tr>
				<tr>
					<td class="title">
						类型:
					</td>
					<td colspan="3">
							<select name="entity.type" id="entity_type"  val="${entity.type}">
								<PF:OptionDictionary index="FZLX" isTextValue="0" /></select>
					</td>
				</tr>
				<tr>	<td class="title">
						备注:
					</td>
					<td colspan="3">
							<textarea rows="3" style="width: 360px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[64]'"
							id="entity_pcontent" name="entity.pcontent">${entity.pcontent}</textarea>
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
			<a id="dom_add_entityhrmisrelicfile" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_entityhrmisrelicfile" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formhrmisrelicfile" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionhrmisrelicfile = 'admin/HrmisRelicFileaddCommit.do';
	var submitEditActionhrmisrelicfile = 'admin/HrmisRelicFileeditCommit.do';
	var currentPageTypehrmisrelicfile = '${pageset.pageType}';
	var submitFormhrmisrelicfile;

	
	$(function() {
		//表单组件对象
		submitFormhrmisrelicfile = $('#dom_formhrmisrelicfile').SubmitForm( {
			pageType : currentPageTypehrmisrelicfile,
			grid : gridhrmisrelicfile,
			currentWindowId : 'winhrmisrelicfile'
		});
		//关闭窗口
		$('#dom_cancle_formhrmisrelicfile').bind('click', function() {
			$('#winhrmisrelicfile').window('close');
		});
		//提交新增数据
		$('#dom_add_entityhrmisrelicfile').bind('click', function() {
			if($("#entity_type").val()=='' ){
				alert("请选择上传的文件类型！");
				return 
			}
		});
		
		//提交新增数据
		$('#dom_add_entityhrmisrelicfile').bind('click', function() {
			if($("#imgFile").val()==null||$("#imgFile").val()==""){
				alert("请选择上传文件");
				return false;
			}else{
				$.ajaxFileUpload( {
					url : 'admin/FPFlashFileUpload.do',
					secureuri : false,
					fileElementId : 'imgFile',
					dataType : 'json',
					success : function(data, status) {
						if(data.error=="1"){
							alert("请检查文件大小和类型,文件大小不能超过20兆");
						}else{
							$("#entity_fileid").val(data.id);
							submitFormhrmisrelicfile.postSubmit(submitAddActionhrmisrelicfile,function(){
								$('#dom_datagridhrmisrelicfile').datagrid('reload');  //刷新   
							});
						}
					},
					error : function(data, status, e) {
						alert("上传失败，请检查文件大小和类型");
					}
				 })
			}
			 return false;
			//submitFormhrmisapprelicfile.postSubmit(submitAddActionhrmisapprelicfile);
			//$("#dom_formhrmisapprelicfilemoveinfo").submit();
		});
	});
	
	//-->
</script>