<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理选择-->
<div class="easyui-layout" data-options="fit:true">
       <!-- 待填报表单树 -->
		<div data-options="region:'west',title:'待填报表单',split:true" style="width: 200px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
							<a id="a_tree_reload" href="javascript:void(0)"
								class="easyui-linkbutton" data-options="plain:true"
								iconCls="icon-reload">刷新菜单</a>
							<a id="a_tree_openAll" href="javascript:void(0)"
								class="easyui-linkbutton" data-options="plain:true"
								iconCls="icon-sitemap">全部展开</a>
		</div>
				<ul id="fillForm_tree" class="easyui-tree" >
	
				</ul>
		</div>
		<!-- 结果列表 -->
		  <div id="form_content_div" data-options="region:'center',border:false">
			   <iframe id="fillformframe"  src="" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
		  </div>
</div>
<script type="text/javascript">
$(function() {
 	 $('#fillForm_tree').tree({ 
		 url:'admin/DoTaskAction_doTaskfillFormList.do?taskDefKey=${taskDefKey}&processId=${processId}',
	    animate:true,
	    onClick:funClick
	});  
	 $('#a_tree_reload').bind('click', function() {
			$('#fillForm_tree').tree('reload');
		});
	$('#a_tree_openAll').bind('click', function() {
			$('#fillForm_tree').tree('expandAll');
		}); 
});

 function funClick(node){
	 var url=node.attributes.url;
     $('#fillformframe').attr('src',url);
} 
</script>









