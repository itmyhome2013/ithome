<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>数据管理器</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="/PLUGIN/dbconsole/commons/include_all.jsp"></jsp:include>
	</head>
	<body style="height: 100%;">
		<div id="cc" class="easyui-layout" style="width: 100%; height: 100%;">
			<div class="easyui-tabs"
				data-options="region:'north',title:'',split:true"
				style="height: 200px;">
				<div title="查询数据"
					<c:if test="${type=='1'}">data-options="selected: true"</c:if>
					style="padding: 4px;">
					<form method="post" action="admin/HhcommonDatadosearch.do">
						<table style="width: 100%; height: 100%;">
							<tr>
								<td align="right">
									SELECT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td colspan="3">
									<input class="easyui-validatebox" type="text" name="titles"
										value="${titles}" style="width: 100%"
										data-options="required:true" />
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td align="right">
									FROM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td colspan="3">
									<input class="easyui-validatebox" type="text" name="tables"
										value="${tables}" style="width: 100%"
										data-options="required:true" />
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td align="right">
									WHERE&nbsp; 1=1
								</td>
								<td colspan="3">
									<input class="easyui-validatebox" type="text" name="wheres"
										value="${wheres}" style="width: 100%" data-options="" />
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td align="right">

								</td>
								<td colspan="3">
									每页显示
									<input type="text" class="easyui-numberbox" name="pagesize"
										value="${pagesize}" width="20" data-options="min:0"></input>
									条，显示第
									<input id="ss" class="easyui-numberspinner" name="currentPage"
										style="width: 80px;" value="${currentPage }"
										required="required" data-options="min:1,max:100,editable:true">
									</input>
									页
								</td>
								<td>
									<input type="submit" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div title="执行SQL"
					<c:if test="${type=='2'}">data-options="selected: true"</c:if>
					style="overflow: auto; padding: 20px;">
					<form method="post" action="admin/HhcommonDataSqlRun.do">
						<table style="width: 100%; height: 100%;">
							<tr>
								<td align="right">
									SQL:
								</td>
								<td colspan="3">
									<textarea style="width: 100%" name="sql" rows="5">${sql}</textarea>
								</td>
								<td>
									<input type="submit" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div title="文件查看"
					<c:if test="${type=='3'}">data-options="selected: true"</c:if>
					style="overflow: auto; padding: 20px;">
					<c:if test="${path==null}">
						<a href="admin/HhcommonDataFileMng.do">查看文件</a>
					</c:if>
					
					<c:if test="${path!=null}">
						上级目录：<a style="color: blue;cursor: pointer;" onclick="submitPath('${parent}')">${parent}</a><br />
						当前<c:if test="${diris}">目录</c:if><c:if test="${!diris}">文件</c:if>：${path}<br />
						<c:if test="${diris}"> 
							包含文件:<br />
							<c:forEach items="${files}" var="node">
								<span style="margin: 4px;"><a  style="color: blue;cursor: pointer;" onclick="submitPath('${path}/${node}')">${node}</a></span>
							</c:forEach>
						</c:if>
						<c:if test="${!diris}"> 
						大小:${len}b
						<a style="color: blue;cursor: pointer;" onclick="submitDownload('${path}')">下载该文件</a>
						</c:if>
					</c:if>
					<form id="pathForm" action="admin/HhcommonDataFileMng.do" method="post" >
						<input id="formpath" type="hidden" name="path">
					</form>
					<form id="downloadForm" action="admin/HhcommonDataFiledownload.do" method="post" >
						<input id="downloadpath" type="hidden" name="path">
					</form>
					<script type="text/javascript">
						function submitPath(path){
							$('#formpath').val(path);
							$('#pathForm').submit();
						}
						function submitDownload(path){
							$('#downloadpath').val(path);
							$('#downloadForm').submit();
						}
					</script>
				</div>
			</div>
			<div data-options="region:'south',title:'控制台',split:true"
				style="height: 100px;">
				${logs }
			</div>
			<div data-options="region:'center',title:'结果集合'"
				style="padding: 5px; background: #eee;">
				<c:if test="${list!=null}">
					<table class="easyui-datagrid">  
						<thead>
							<c:forEach items="${list}" var="node">
								<tr>
									<c:if test="${unitonlyone}">
										<th >
											${node}
										</th >
									</c:if>
									<c:if test="${!unitonlyone}">
										<c:forEach items="${node}" var="unit">
											<th >
												${unit}
											</th >
										</c:forEach>
									</c:if>
								</tr>
							</c:forEach>
						</thead>
					</table>
				</c:if>
				<c:if test="${result!=null}">
					<table class="easyui-datagrid">
						<thead>
							<tr>
								<c:forEach items="${result.titles}" var="node">
									<th data-options="field:'${node}'">
										${node}
									</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${result.resultListArray}" var="node">
								<tr>
									<c:forEach items="${node}" var="one">
										<td>
											${one}
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
		</div>
	</body>
</html>
