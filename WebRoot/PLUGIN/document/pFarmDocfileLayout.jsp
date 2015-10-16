<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>文件</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/PLUGIN/document/commons/include_all.jsp"></jsp:include>
	</head>
	<body>
		<div class="search_div">
			<form id="dom_var_search">
				&nbsp;&nbsp;类型:
				<select name="TYPE:=">
					<option value=""> ---全部-- </option>
					<option value="1"> 图片 </option>
					<option value="2"> 其他 </option>
				</select>
				&nbsp;&nbsp;显示名称:
				<input name="NAME:like" type="text">
				&nbsp;&nbsp;扩展名:
				<input name="EXNAME:like" type="text">
				<a id="a_var_search" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-search">查询</a>
				<a id="a_var_reset" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-reload">清除条件</a>
			</form>
		</div>
		
		
		<div class="datagrid_div">
			<table id="dom_var_grid" style="height: 350px">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="100">
							显示名称
						</th>
						<th field="TYPE" data-options="sortable:true" width="80">
							类型
						</th>
						<th field="EXNAME" data-options="sortable:true" width="80">
							扩展名
						</th>
						<th field="LEN" data-options="sortable:true" width="80">
							文件大小
						</th>
						<!-- 
						<th field="FILENAME" data-options="sortable:true" width="160">
							文件名
						</th>
						 -->
						<th field="DIR" data-options="sortable:true" width="80">
							目录
						</th>
						<!-- 
						<th field="PSTATE" data-options="sortable:true" width="80">
							状态
						</th>
						 -->
						 <th field="OPER" data-options="field:'ID',width:60,align:'center',formatter:formatOper" >
							操作
						</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',border:false">
			<div id="dom_var_pagination" class="easyui-pagination"></div>
		</div>
		<!-- 弹出窗口 -->
		<div id="dom_var_window"></div>
		
	</body>
	<script type="text/javascript">
	var url_delAction = "admin/FarmDocfiledeleteCommit.do";
	var url_formAction = "admin/FarmDocfileshow.do";
	var url_searchAction = "admin/FarmDocfilequeryAll.do";

	var url_formActionhrmisrelicfile = "admin/FarmDocfileshow.do?pageset.pageType=1";//增加、修改、查看URL
	
	var title_window = "文件";
	var DOM_GRID_TABLE = '#dom_var_grid';
	var DOM_PAGINATION_DIV = '#dom_var_pagination';
	var DOM_MENU_DIV = '#dom_var_menu';
	var DOM_WINDOW_DIV = '#dom_var_window';
	var DOM_SEARCH_FORM = '#dom_var_search';
	var LINK_SEARCH = '#a_var_search';
	var LINK_RESET = '#a_var_reset';
	var TOOL_BAR = [ {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			shortMenuOpen_grid(null, {
				name : 'del'
			})
		}
	},{
		id : 'upload',
		text : '上传',
		iconCls : 'icon-upcoming-work',
		handler : upload
	}/*,{
		id : 'del',
		text : '下载',
		iconCls : 'icon-arrow_rotate_clockwise',
		handler : download
	}*/ ];
	$(function() {
		initLayout();
	});

	//上传
	function upload() {
		var url = url_formActionhrmisrelicfile ;
		openWindow(DOM_WINDOW_DIV,200,600,true,url,'上传');
	}

	function download(){
		//$.post('admin/downloadImage.do',{},function(data){
		//})
		
		//for(var i=0;i<2;i++){
			//alert(i);
			window.open('admin/downloadImage.do');
			//$('#dom_var_window').window('open');  
		//}
	}
	function formatOper(val,row,index){    
		var ids = "'"+row["ID"]+"'";
		if(row["TYPE"] == '图片'){
			return '<a href="javascript:readImg('+ids+');">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admin/downloadImage.do?ids='+row["ID"]+'">下载</a>';
		}else{
			return '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="admin/downloadImage.do?ids='+row["ID"]+'">下载</a>';
		}
            
    }  
    function readImg(ids){
    	openWindow(DOM_WINDOW_DIV,400,600,true,'admin/readImageShow.do?ids='+ids+'','查看');
    }
</script>
</html>




