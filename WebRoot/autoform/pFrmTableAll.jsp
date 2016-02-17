<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--表单基础信息-->
<div id="PopuHuInfotabsId">
	<div title="表单信息" style="padding: 10px"
		data-options="href:'admin/FrmTableshow.do?pageset.pageType=${pageset.pageType}&ids=${ids}&tableid=${ids }'">
	</div>
	<div title="字段信息" style="padding: 10px"
		data-options="href:'admin/FrmField_ACTION_CONSOLE.do?pageset.pageType=${pageset.pageType}&ids=${ids}&tableid=${ids }'">
	</div>
</div>
<!-- 唯一保证正确的用户ID -->
<input type="hidden" id="FrmTableIDVarId" value="${entity.id}" />
<script type="text/javascript">
	$(function() {
		$('#PopuHuInfotabsId').tabs( {
			fit : true,
			border : false
		});
		//$('#PopuHuInfotabsId').tabs('disableTab', 1);
		//$('#PopuHuInfotabsId').tabs('disableTab', 2);
	});
	$(function() {
	   
	})
</script>