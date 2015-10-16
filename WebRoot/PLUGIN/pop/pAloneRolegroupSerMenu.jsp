<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div style="padding: 4px;">
	<div style="border-bottom: 1px solid #ddd;">
		<a id="a_tree_save" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-save">保存</a>
	</div>
	<ul id="menuTree_tt" class="easyui-tree"
		data-options="url:'admin/ALONEMENU_RULEGROUP_TREENODE.do?ids=${ids}',animate:true,checkbox:true"></ul>
</div>
<script type="text/javascript">
	$('#a_tree_save').bind(
			'click',
			function() {
				nodes1 = getCheckTreeNodeArray('#menuTree_tt');
				var s = '';
				for ( var i = 0; i < nodes1.length; i++) {
					if (s != '')
						s += ',';
					s += nodes1[i].id;
				}
				$.messager.confirm(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.COMMIT_IS
						+ '选中' + nodes1.length + '项', function(r) {
					if (r) {
						$('#a_tree_save').linkbutton('disable');
						$.post('admin/ALONEROLEGROUP_SYSBASE_SETTREE.do', {
							menuIds : s,
							ids : '${ids}'
						}, function(flag) {
							if (flag.pageset.commitType == '0') {

								$.messager.alert(MESSAGE_PLAT.PROMPT,
										MESSAGE_PLAT.SUCCESS, 'info');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ flag.pageset.message;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
							$('#a_tree_save').linkbutton('enable');   
						});
					}
				});
			});
	//-->
</script>