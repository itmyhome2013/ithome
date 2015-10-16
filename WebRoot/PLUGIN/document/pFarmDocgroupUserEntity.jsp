<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--工作小组成员-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formfarmdocgroupuser">
			<input type="hidden" name="entity.id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						状态:
					</td>
					<td colspan="3">
						<select name="entity.pstate" id="entity_pstate"
							val="${entity.pstate}">
							<option value="0">
								邀请
							</option>
							<option value="1">
								在用
							</option>
							<option value="2">
								删除
							</option>
							<option value="3">
								申请
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						是否小组管理员:
					</td>
					<td colspan="3">
						<select name="entity.leadis" id="entity_leadis"
							val="${entity.leadis}">
							<option value="0">
								否
							</option>
							<option value="1">
								是
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						是否有修改权限:
					</td>
					<td colspan="3">
						<select name="entity.editis" id="entity_editis"
							val="${entity.editis}">
							<option value="0">
								否
							</option>
							<option value="1">
								是
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						是否首页显示:
					</td>
					<td colspan="3">
						<select name="entity.showhome" id="entity_showhome"
							val="${entity.showhome}">
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
				<a id="dom_add_entityfarmdocgroupuser" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_edit_entityfarmdocgroupuser" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfarmdocgroupuser" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfarmdocgroupuser = 'admin/FarmDocgroupUseraddCommit.do';
	var submitEditActionfarmdocgroupuser = 'admin/FarmDocgroupUsereditCommit.do';
	var currentPageTypefarmdocgroupuser = '${pageset.pageType}';
	var submitFormfarmdocgroupuser;
	$(function() {
		//表单组件对象
		submitFormfarmdocgroupuser = $('#dom_formfarmdocgroupuser').SubmitForm(
				{
					pageType : currentPageTypefarmdocgroupuser,
					grid : gridfarmdocgroupuser,
					currentWindowId : 'winfarmdocgroupuser'
				});
		//关闭窗口
		$('#dom_cancle_formfarmdocgroupuser').bind('click', function() {
			$('#winfarmdocgroupuser').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfarmdocgroupuser').bind(
				'click',
				function() {
					submitFormfarmdocgroupuser
							.postSubmit(submitAddActionfarmdocgroupuser);
				});
		//提交修改数据
		$('#dom_edit_entityfarmdocgroupuser').bind(
				'click',
				function() {
					submitFormfarmdocgroupuser
							.postSubmit(submitEditActionfarmdocgroupuser);
				});
	});
	//-->
</script>