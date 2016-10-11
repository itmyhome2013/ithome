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
<!--人口基础信息-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<c:if test="${pageset.pageType==1}"></c:if>
		<c:if test="${pageset.pageType==2}"></c:if>
		<c:if test="${pageset.pageType==0}"></c:if>
		<form id="dom_formbook">
			<input type="hidden" name="entity.id" id="entity_id" value="${entity.id}">
			<input type="hidden" name="personId" value="${personId }">
			<table class="editTable">
							
				<tr>
					<td class="title" style="text-align: left;color: red">基本信息</td>
					<td colspan="3"></td>
				</tr>			
							
				<tr>
					<td class="title">
						书籍名称:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[15]']"
							id="entity_bookName" name="entity.bookName" value="${entity.bookName}">
					</td>
					<td class="title">
						作者:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[15]']"
							id="entity_author" name="entity.author" value="${entity.author}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						出版社:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal']"
							id="entity_press" name="entity.press" value="${entity.press}">
					</td>
					<td class="title">
						ISBN:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_isbn" name="entity.isbn" value="${entity.isbn}">
					</td>
				</tr>
				
				<c:if test="${pageset.pageType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.pageType==2}">
					<!--修改-->
				</c:if>
				<c:if test="${pageset.pageType==0}">
				
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
				<a id="dom_add_entitybook" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_add_entitybook" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_formbook" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	//var submitAddActionbook = 'admin/BookaddCommit.do';
	var submitEditActionbook = 'admin/BookAddCommit.do';
	var currentPageTypebook = '${pageset.pageType}';
	var submitFormbook;
	$(function() {
		//表单组件对象
		submitFormbook = $('#dom_formbook').SubmitForm( {
			pageType : currentPageTypebook,
			grid : gridbook,  //刷新
			currentWindowId : 'winbook'
		});
		//关闭窗口
		$('#dom_cancle_formbook').bind('click', function() {
			$('#winbook').window('close');
		});
		//提交新增数据
		$('#dom_add_entitybook').bind('click',function() {
			submitFormbook.postSubmit(submitEditActionbook, function(flag) {
				
			});
		});
	});

	
	//-->
</script>
<!-- 
 -->