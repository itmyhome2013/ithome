<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>启动流程</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-FACE/conf/includeH.jsp"></jsp:include>
		<%-- <jsp:include page="/PLUGIN/pop/commons/include_all.jsp"></jsp:include> --%>
	</head>
	<body class="easyui-layout" >
		 <!-- 待填报表单树 -->
		<div data-options="region:'west',title:'已部署流程',split:true" style="width: 200px;">
			<div class="TREE_COMMON_BOX_SPLIT_DIV">
								<a id="a_tree_reload" href="javascript:void(0)"
									class="easyui-linkbutton" data-options="plain:true"
									iconCls="icon-reload">刷新菜单</a>
								<a id="a_tree_openAll" href="javascript:void(0)"
									class="easyui-linkbutton" data-options="plain:true"
									iconCls="icon-sitemap">全部展开</a>
			</div>
					<ul id="procdef_tree" class="easyui-tree" ></ul>
		</div>
		<!-- 结果列表 -->
		  <div id="StartProcessIframe_content_div" data-options="region:'center',border:false">
			   <iframe id="StartProcessIframe"  src="" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
		  </div>
	</body>
	<script type="text/javascript">
	  var StartProcessIframeAction = 'admin/StartProcessIframe_ACTION_CONSOLE.do';
		$(function() {
			$('#procdef_tree').tree({ 
				 url:'admin/StartProcessAction_findProcDefTree.do',
			    animate:true,
			    onClick:funTreeClick,
			    onLoadSuccess:funLoadSuccess
			});  
			$('#a_tree_reload').bind('click', function() {
					$('#procdef_tree').tree('reload');
				});
			$('#a_tree_openAll').bind('click', function() {
					$('#procdef_tree').tree('expandAll');
				});  
		});
		
		function funLoadSuccess(node, data){
			$("#procdef_tree li:eq(0)").find("div").addClass("tree-node-selected"); 
			funTreeClick(data[0]);
			
		}
		function funTreeClick(node){
			var procDefId =node.id;
			var url=StartProcessIframeAction+"?procDefId="+procDefId;
			$('#StartProcessIframe').attr('src',url);
			
		}
	</script>
</html>
		
			
										
										
