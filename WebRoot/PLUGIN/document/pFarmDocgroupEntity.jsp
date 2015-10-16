<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--工作小组-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formfarmdocgroup">
			<input type="hidden" name="entity.id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						加入时验证:
					</td>
					<td colspan="3">
						<select name="entity.joincheck" id="entity_joincheck"
							val="${entity.joincheck}">
							<option value="0">
								否
							</option>
							<option value="1">
								是
							</option>
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
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
				<a id="dom_add_entityfarmdocgroup" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_edit_entityfarmdocgroup" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfarmdocgroup" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfarmdocgroup = 'admin/FarmDocgroupaddCommit.do';
	var submitEditActionfarmdocgroup = 'admin/FarmDocgroupeditCommit.do';
	var currentPageTypefarmdocgroup = '${pageset.pageType}';
	var submitFormfarmdocgroup;
	$(function() {
		//表单组件对象
		submitFormfarmdocgroup = $('#dom_formfarmdocgroup').SubmitForm( {
			pageType : currentPageTypefarmdocgroup,
			grid : gridfarmdocgroup,
			currentWindowId : 'winfarmdocgroup'
		});
		//关闭窗口
		$('#dom_cancle_formfarmdocgroup').bind('click', function() {
			$('#winfarmdocgroup').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfarmdocgroup').bind('click', function() {
			submitFormfarmdocgroup.postSubmit(submitAddActionfarmdocgroup);
		});
		//提交修改数据
		$('#dom_edit_entityfarmdocgroup').bind('click', function() {
			submitFormfarmdocgroup.postSubmit(submitEditActionfarmdocgroup);
		});
	});
	//-->
</script>