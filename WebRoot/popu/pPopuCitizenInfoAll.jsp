<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--人口基础信息-->
<div id="PopuCitizentabsId">
	<div title="基本信息" style="padding: 10px"
		data-options="href:'admin/PopuCitizenInfoshow.do?pageset.pageType=${pageset.pageType}&ids=${ids}&huid=${huid}&liveaddress=${liveaddress }'">
	</div>
	
</div>
<!-- 唯一保证正确的用户ID -->
<input type="hidden" id="CitizenInfoIDVarId" value="${entity.citizenid}" />
<script type="text/javascript">
	$(function() {
		$('#PopuCitizentabsId').tabs( {
			fit : true,
			border : false
		});
		//$('#PopuCitizentabsId').tabs('disableTab', 1);
		//$('#PopuCitizentabsId').tabs('disableTab', 2);
		//$('#PopuCitizentabsId').tabs('disableTab', 3);
		//$('#PopuCitizentabsId').tabs('disableTab', 4);
		//$('#PopuCitizentabsId').tabs('disableTab', 5);
		//$('#PopuCitizentabsId').tabs('disableTab', 6);
	});
	$(function() {
	   
	})
</script>