<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--文件-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
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
								<option value="1">图片</option>
								<option value="2">其他</option>
							</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_var_add_entity" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_var_edit_entity" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_var_cancle_form" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddAction = 'admin/FarmDocfileaddCommit.do';
	var submitEditAction = 'admin/FarmDocfileeditCommit.do';
	var currentPageType = '${pageset.pageType}';
	var DOM_DATAFORM_FORM = '#dom_var_entity';
	var LINK_FORM_ADD = '#dom_var_add_entity';
	var LINK_FORM_EDIT = '#dom_var_edit_entity';
	var LINK_FORM_CANCEL = '#dom_var_cancle_form';
	$(function() {
		initEntity();
	});
	function initEntity() {
		var submitForm = $(DOM_DATAFORM_FORM).SubmitForm( {
			pageType : currentPageType,
			gridId : DOM_GRID_TABLE,
			currentWindow : DOM_WINDOW_DIV
		});
		$(LINK_FORM_CANCEL).bind('click',function(){
			$('#dom_var_window').window('close');
		})
		$(LINK_FORM_ADD).bind('click', function() {

			$.ajaxFileUpload( {
				url : 'admin/FPFlashFileUpload.do',
				secureuri : false,
				fileElementId : 'imgFile',
				dataType : 'json',
				data: {//加入的文本参数  
		            "entity.type": $("#entity_type").val()
		        },  
				success : function(data, status) {
					console.info(data);
					if(data.error=="1"){
						alert("请检查文件大小和类型,文件大小不能超过20兆");
					}else{
						//$("#entity_fileid").val(data.id);
						$('#dom_var_grid').datagrid('reload');  //刷新

						$.messager.confirm(MESSAGE_PLAT.PROMPT,
								MESSAGE_PLAT.SUCCESS_CLOSE_WINDOW,
								function(r) {
									if (r) {
										$('#dom_var_window').window('close'); //关闭
									}
								});
					}
				},
				error : function(data, status, e) {
					alert("上传失败，请检查文件大小和类型");
				}
			 })
			
		});

	}
	//-->
</script>