<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>系统日志</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/pop/commons/include_all.jsp"></jsp:include>
	</head>
	<body>
		<div class="search_div">
			<form id="dom_var_search">
				日志:
				<input name="a.DESCRIBES:like" type="text">
				操作人:
				<input name="b.name:like" type="text">
				日志级别:
				<select name="a.LEVEL:=">
					<option value="">
						全部
					</option>
					<option value="INFO">
						INFO
					</option>
					<option value="DEBUG">
						DEBUG
					</option>
					<option value="WARN">
						WARN
					</option>
					<option value="ERROR">
						ERROR
					</option>
				</select>
				<a id="a_var_search" href="javascript:void(0)"
					class="easyui-linkbutton" data-options="plain:true"
					iconCls="icon-search">查询</a>
				<a id="a_var_reset" href="javascript:void(0)"
					class="easyui-linkbutton" data-options="plain:true"
					iconCls="icon-reload">清除条件</a>
			</form>
		</div>
		<div class="datagrid_div">
			<table id="dom_var_grid" style="height: 350px">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="DESCRIBES" data-options="sortable:true" width="200">
							日志
						</th>
						<th field="LEVEL" data-options="sortable:true" width="80">
							日志级别
						</th>
						<th field="APPUSER" data-options="sortable:true" width="80">
							操作人
						</th>
						<th field="IP" data-options="sortable:true" width="80">
							IP
						</th>
						<th field="CTIME" data-options="sortable:true" width="80">
							生成时间
						</th>
					</tr>
				</thead>
			</table>
			<!-- 分页栏 -->
			<div id="dom_var_pagination" class="easyui-pagination"></div>
		</div>
		<!-- 弹出窗口 -->
		<div id="dom_var_window"></div>
		<!-- 表格右键菜单 -->
		<div id="dom_var_menu" class="easyui-menu" style="width: 120px;">
			<div name="view" data-options="iconCls:'icon-tip'">
				查看
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var url_delAction = "admin/AloneApplogdeleteCommit.do";
	var url_formAction = "admin/AloneApplogshow.do";
	var url_searchAction = "admin/AloneApplogqueryAll.do";
	var title_window = "系统日志";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	$(function() {
		TOOL_BAR_ADD('view','查看','icon-tip',0,true);
		TOOL_BAR.push( {   
            text : '下载报表',   
            iconCls : 'icon-blog',   
            handler : function() {   
				window.location = 'admin/AloneApplogExcel.do';
            }   
        });  
		initLayout();
	});
</script>
</html>




