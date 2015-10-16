<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<div id="chooseIconsDivId">
	<c:forEach var="name" items="${menuIcons}">
		<div class="imgBox" 
			style="float: left; border: 2px solid gray; margin: 2px; cursor: pointer; padding: 4px; background-color: white;">
			<div>
				<img  name="${name}"
					src="<PF:basePath/>${urlPath}${name}" />
			</div>
		</div>
	</c:forEach>
</div>
<script type="text/javascript">
	$(function() {
		$('.imgBox', '#chooseIconsDivId').bind('mouseover', function() {
			$(this).css('border', '2px solid green');
			$(this).css('background-color', '#e1f7e3');
		});
		$('.imgBox', '#chooseIconsDivId').bind('mouseout', function() {
			$(this).css('border', '2px solid gray');
			$(this).css('background-color', 'white');
		});
		$('.imgBox', '#chooseIconsDivId').bind('click', function() {
			//选择该图片
			$('#iconImgId').html('<img style="width: 32px; height: 32px;"	src="'+$('img',this).attr('src')+'" />');
			$('#entity_img_ID').val('${urlPath}'+$('img',this).attr('name'));
			//关闭窗口
			closeWindows();
		});
	})
	//-->
</script>