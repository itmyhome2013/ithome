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
		<form id="dom_formfrmoperatefield">
			<input type="hidden" name="id" value="${ids}">
			<input type="hidden" id="tableId" name="tableid" value="${tableid }"/>
			<input type="hidden" name="pageset.pageType" value="${pageset.pageType }"/>
			<table class="editTable">
				<PF:GenerateForm tableId="${tableid}" keyId="${ids}"/>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_add_entityfrmfield" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_entityfrmfield" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfrmfield" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfrmfield = 'admin/FrmFieldaddCommit.do';
	var submitEditActionfrmfield = 'admin/FrmOperateFieldeditCommit.do';
	var currentPageTypefrmfield = '${pageset.pageType}';
	var submitFormfrmfield;
	$(function() {
		
		//表单组件对象
		submitFormfrmfield = $('#dom_formfrmoperatefield').SubmitForm( {
			pageType : currentPageTypefrmfield,
			grid : choosegridfrmoperateform,
			currentWindowId : 'winfrmoperateform'
		});
		//关闭窗口
		$('#dom_cancle_formfrmfield').bind('click', function() {
			$('#winfrmoperateform').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfrmfield').bind('click', function() {
			
			var formData = $("#dom_formfrmoperatefield").serializeObject();
			submitFormfrmfield.postSubmit(submitEditActionfrmfield + "?fieldParameter="+JSON.stringify(formData));
			
		});
		//提交修改数据
		$('#dom_edit_entityfrmfield').bind('click', function() {
			var formData = $("#dom_formfrmoperatefield").serializeObject();
			submitFormfrmfield.postSubmit(submitEditActionfrmfield + "?fieldParameter="+JSON.stringify(formData));
		});
	});

	 //序列化表单 
	 $.fn.serializeObject = function() {
	        var o = {};
	        var a = this.serializeArray();
	        $.each(a, function() {
	            if (o[this.name]) {
	                if (!o[this.name].push) {
	                    o[this.name] = [o[this.name]];
	                }
	                o[this.name].push(this.value || '');
	            } else {
	                o[this.name] = this.value || '';
	            }
	        });
	        return o;
	    };
	//-->
</script>