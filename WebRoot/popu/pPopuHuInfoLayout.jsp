<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>户基础信息</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
	</head>
	<body class="easyui-layout" id="layout">
		<div data-options="region:'north',border:false">
			<form id="dom_report_search" action="admin/exportRepothuInfo.do" method="post">
				<input type="hidden" name="query.ruleText" id="query_ruleText">
			</form>
			<form id="dom_searchpopuhuinfo">
				<table class="editTable">
					<tr>
						<td class="title">
							户主姓名:
						</td>
						<td>
							<input name="huname:like" type="text" style="width: 180px;">
						</td>
						<td class="title">
							户口性质:
						</td>
						<td>
							<select name="HUQUALE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HUQUALE" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							户主身份证:
						</td>
						<td>
							<input name="HUIDENTITY:like" type="text" style="width: 180px;">
						</td>

					</tr>
					<tr>
						<td class="title">
							户口类别:
						</td>
						<td>
							<select name="HUTYPE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HUTYPE" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							户籍状态:
						</td>
						<td>
							<select name="HUSTATE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HUSTATE" isTextValue="0" />
							</select>
						</td>

						<td class="title">
							户籍地址:
						</td>
						<td>
							<input name="HUADDRESS:like" type="text" style="width: 180px;">
						</td>
					</tr>
					<tr>

						<td class="title">
							是低保家庭:
						</td>
						<td>
							<select name="HULOWSAFE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HULOWSAFE" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							是否常住:
						</td>
						<td>
							<select name="HULONGLIVE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HULONGLIVE" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							家庭结构:
						</td>
						<td>
							<select name="HUSTRUCTURE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HUSTRUCTURE" isTextValue="0" />
							</select>
						</td>
					</tr>
					<tr>
						<td class="title">
							住房性质:
						</td>
						<td>
							<select name="HUHOUSEQUALE:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HUHOUSEQUALE" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							管理类型
						</td>
						<td>
							<select name="HUMANAGERKIND:=" style="width: 180px;">
								<option value="">
									--请选择--
								</option>
								<PF:OptionDictionary index="HUMANAGERKIND" isTextValue="0" />
							</select>
						</td>
						<td class="title">
							地址
						</td>
						<td>
							<input name="LIVESADDRESS:like" type="text" style="width: 180px;">
						</td>
					</tr>
					
					
					<tr style="text-align: center;">
						<td colspan="6">
							<a id="a_search" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a id="a_reset" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<input type="hidden" id="ids" value="" />
			<table class="" id="dom_datagridpopuhuinfo">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="HUNAME" data-options="sortable:true" width="80">
							户主姓名
						</th>
						<th field="LIVESADDRESS" data-options="sortable:true" width="350">
							户地址
						</th>
						<th field="HUQUALE" data-options="sortable:true" width="80">
							户口性质
						</th>
						<th field="HUIDENTITY" data-options="sortable:true" width="140">
							户主身份证
						</th>
						<th field="HUTYPE" data-options="sortable:true" width="80">
							户口类别
						</th>
						<th field="HUSTATE" data-options="sortable:true" width="120">
							户籍状态
						</th>
						<th field="HUPEOPLENUM" data-options="sortable:true" width="60">
							户籍人数
						</th>
						<th field="HUSTRUCTURE" data-options="sortable:true" width="60">
							家庭结构
						</th>
						<th field="HUTEL" data-options="sortable:true" width="100">
							户主电话
						</th>
						<th field="HUMOBILE" data-options="sortable:true" width="100">
							户主手机
						</th>
						<th field="HUHOUSEQUALE" data-options="sortable:true" width="80">
							住房性质
						</th>
						<th field="HUSIGNMAN" data-options="sortable:true" width="80">
							户籍签办人
						</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'east',collapsed:true,split:true,title:'居民信息'" style="width: 500px">
			<jsp:include page="pPopuHuToCitizenInfoLayout.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionpopuhuinfo = "admin/PopuHuInfodeleteCommit.do";//删除URL
	var url_formActionpopuhuinfo = "admin/PopuHuInfoAllShow.do";//增加、修改、查看URL
	var url_searchActionpopuhuinfo = "admin/PopuHuInfoqueryAll.do";//查询URL
	var title_windowpopuhuinfo = "房屋基础信息";//功能名称
	var gridpopuhuinfo;//数据表格对象
	var searchpopuhuinfo;//条件查询组件对象
	var TOOL_BARpopuhuinfo = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatapopuhuinfo
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatapopuhuinfo
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatapopuhuinfo
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatapopuhuinfo
	}, {
	 	id : 'export',
	 	text : '导出',
	 	iconCls : 'icon-report',
	 	handler : exportDatapopuhuinfo
	 } 
	];
	$(function() {
		//初始化数据表格
		gridpopuhuinfo = $('#dom_datagridpopuhuinfo')
				.datagrid(
						{
							url : url_searchActionpopuhuinfo,
							fit : true,
							fitColumns : false,
							'toolbar' : TOOL_BARpopuhuinfo,
							pagination : true,
							closable : true,
							checkOnSelect : true,
							striped : true,
							rownumbers : true,
							ctrlSelect : true,
							onClickRow : function(index, date) {
								$('#layout').layout('expand', 'east'); //展开
							//getcheckbox();
							$("#ids").val(date['HUID']); //设置huid
							$("#currentLIVESADDRESS").html(date['LIVESADDRESS']);
							$("#HUNAME").html(date['HUNAME']);
							gridpopucitizeninfo = $(
									'#dom_datagridpopucitizeninfo')
									.datagrid(
											{
												url : "admin/PopuHuCitizenInfoqueryAll.do?ids="
														+ date['HUID'] + "",
												fit : true,
												'toolbar' : TOOL_BARpopucitizeninfo,
												pagination : true,
												closable : true,
												checkOnSelect : true,
												striped : true,
												rownumbers : true,
												ctrlSelect : true,
												fitColumns : false,
												onDblClickRow : function() {
													viewDatapopucitizeninfo();
												}
											});
						},
						onUnselect : function() {
							//getcheckbox();
						},
						onLoadSuccess : function () {
							//$(this).datagrid("fixRownumber");
						}
						});
		//初始化条件查询
		searchpopuhuinfo = $('#dom_searchpopuhuinfo').searchForm( {
			gridObj : gridpopuhuinfo
		});

	});
	//获得所选户信息
	function getcheckbox() {
		var ids = [];
		var rows = $('#dom_datagridpopuhuinfo').datagrid('getSelections');
		//获取datagrid选中行
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i]['HUID'] + ",";
		}
		//刷新人口信息 
		gridpopucitizeninfo = $('#dom_datagridpopucitizeninfo').datagrid( {
			url : "admin/PopuHuCitizenInfoqueryAll.do?ids=" + ids + "",
			fit : true,
			'toolbar' : TOOL_BARpopucitizeninfo,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : false
		});
	}
	//查看
	function viewDatapopuhuinfo() {
		var selectedArray = $(gridpopuhuinfo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionpopuhuinfo + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].HUID;
			$.farm.openWindow( {
				id : 'winpopuhuinfo',
				width : 800,
				height : 480,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//新增
	function addDatapopuhuinfo() {
		var url = url_formActionpopuhuinfo + '?pageset.pageType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winpopuhuinfo',
			width : 800,
			height : 480,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatapopuhuinfo() {
		var selectedArray = $(gridpopuhuinfo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionpopuhuinfo + '?pageset.pageType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].HUID;
			;
			$.farm.openWindow( {
				id : 'winpopuhuinfo',
				width : 800,
				height : 480,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//导出报表
	function exportDatapopuhuinfo() {
		var str = $(searchpopuhuinfo).searchForm().arrayStr();
		$('#query_ruleText').val(str);
		$('#dom_report_search').submit();
	}

	//删除
	function delDatapopuhuinfo() {
		var selectedArray = $(gridpopuhuinfo).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS
					+ "</br>删除房屋信息,同时也会删除该房屋下的人员信息!";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionpopuhuinfo + '?ids='
							+ $.farm.getCheckedIds(gridpopuhuinfo, 'HUID'), {},
							function(flag) {
								if (flag.pageset.commitType == 0) {
									$(gridpopuhuinfo).datagrid('reload');
									getcheckbox();
									$('#layout').layout('collapse', 'east');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	function beforeSelect(node) {
		//选中地址事件，但是不能够修改node否则会将改变应用到地址节点中
	}
</script>
</html>




