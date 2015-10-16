<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--人口基础信息-->
<div id="PopuHuInfotabsId">
	<div title="户信息" style="padding: 10px"
		data-options="href:'admin/PopuHuInfoshow.do?pageset.pageType=${pageset.pageType}&ids=${ids}&huid=${huid}'">
	</div>
</div>
<!-- 唯一保证正确的用户ID -->
<input type="hidden" id="HuInfoIDVarId" value="${entity.huid}" />
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