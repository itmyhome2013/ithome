<div class="demo-info">
	<div class="demo-tip icon-tip"></div>
	<div>
		请为选中用户设置组织机构(为所有选中用户分配组织机构)
	</div>
</div>
<div class="TREE_COMMON_BOX_DIV" style="height: 230px;">
	<div class="TREE_COMMON_BOX_SPLIT_DIV">
		<a id="a_User_Orgtree_openAll" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-sitemap">全部展开</a>
	</div>
	<ul id="ttUser_Org"></ul>
</div>
<script type="text/javascript">
	function submitUserOrg(userIds, orgId) {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.COMMIT_IS,
				function(r) {
					if (r) {
						$.post('admin/AloneAllUser_SubmitSetOrg.do', {
							'orgId' : orgId,
							ids : userIds
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
							$(DOM_GRID_TABLE).datagrid('reload');   
						}).error(function() {   
                            $.messager.alert(MESSAGE_PLAT.ERROR,   
                                    MESSAGE_PLAT.ERROR_SUBMIT_NO_MESSAGE,   
                                    'error'); 
                        });
					}
				});
	}

	$(function() {
		$('#ttUser_Org').tree( {
			url : 'admin/ALONEORG_SYSBASE_TREENODE.do',
			onSelect : function(node) {
				submitUserOrg('${ids}', node.id)
			}
		});
		$('#a_User_Orgtree_openAll').bind('click', function() {
			$('#ttUser_Org').tree('expandAll');
		});
	});
	//-->
</script>