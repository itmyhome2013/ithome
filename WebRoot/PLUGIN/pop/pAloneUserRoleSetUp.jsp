<div class="demo-info">
	<div class="demo-tip icon-tip"></div>
	<div>
		请为该用户设置权限角色(多选时只对选中的第一个用户有效)
	</div>
</div>
<div style="padding: 4px;">
	<div style="border-bottom: 1px solid #ddd;">
		<a id="a_tree_save_UserRule" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-save">保存</a>
	</div>
	<div style="margin-top: 8px;">
		<table id="dg_grid_ruleForUser" style="height: 200px; margin: auto;">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'ANAME',width:240">
						当前可用角色
					</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	function submitUserRole(rules, userId) {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.COMMIT_IS,
				function(r) {
					if (r) {
						$('#a_tree_save').linkbutton('disable');
						$.post('admin/ALONE_SETUSER_ROLEGROUP_COMMIT.do', {
							'userid' : userId,
							ids : rules
						}, function(flag) {
							if (flag.pageset.commitType == '0') {

								$.messager.alert(MESSAGE_PLAT.PROMPT,
										MESSAGE_PLAT.SUCCESS, 'info');
								$("#dom_var_grid").datagrid('reload');
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

	}

	$(function() {
		$('#a_tree_save_UserRule').bind('click', function() {
			var ids_str = getGridCheckedIds('#dg_grid_ruleForUser');
			if (!ids_str) {
				ids_str = "";
			}
			submitUserRole(ids_str, '${ids}');
		});

		$('#dg_grid_ruleForUser')
				.datagrid(
						{
							url : 'admin/AlonequeryAllRulegroupForUser.do?userId=${ids}',
							closable : true,
							singleSelect : false,
							checkOnSelect : true,
							striped : true,
							loadFilter : function(data) {
								try {
									if (data.result.message) {
										$.messager.alert(MESSAGE_PLAT.ERROR,
												data.result.message, 'error');
									}
									var data_ = {
										"rows" : data.result.resultList,
										"total" : data.result.totalSize
									};
								} catch (e) {
									$.messager
											.alert(
													MESSAGE_PLAT.ERROR,
													MESSAGE_PLAT.ERROR_SUBMIT_NO_MESSAGE
															+ e, 'error');
									return {
										"rows" : [],
										"total" : 0
									};
								}
								return data_;
							},
							onLoadError : function(none) {
								$.messager.alert(MESSAGE_PLAT.ERROR,
										MESSAGE_PLAT.ERROR_SUBMIT_NO_MESSAGE,
										'AjaxError');
							},
							onLoadSuccess : function(data) {
								$(data.rows).each(
										function(index, obj) {
											if (obj.BID == 1) {
												$('#dg_grid_ruleForUser')
														.datagrid('selectRow',
																index);
											}
										});
							}
						});

	});
	//-->
</script>