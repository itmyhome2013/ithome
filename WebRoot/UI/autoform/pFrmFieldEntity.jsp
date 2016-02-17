<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!--实体管理-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<c:if test="${pageset.pageType==1}">新增${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==2}">修改${JSP_Messager_Title}记录</c:if>
		<c:if test="${pageset.pageType==0}">浏览${JSP_Messager_Title}记录</c:if>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formfrmfield">
			<input type="hidden" id="tableId" name="entity.tableid" value="${tableid }"/>
			<input type="hidden" name="entity.id" value="${entity.id}">
			<table class="editTable">
				
				<tr>
					<td class="title">
						中文名:
					</td>
					<td>
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:true,validType:',maxLength[20]'"
							id="entity_cnname" name="entity.cnname" value="${entity.cnname}">
					</td>
					<td class="title">
						英文名:
					</td>
					<td>
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:true,validType:',maxLength[20]'"
							id="entity_enname" name="entity.enname" value="${entity.enname}">
					</td>
				</tr>
				
				<tr>
					<td class="title" style="text-align: left;color: red">数据库表信息</td>
					<td colspan="3"></td>
				</tr>
				
				<tr>
					<td class="title">
						字段类型:
					</td>
					<td>
						<select id="entity_filedtype" name="entity.filedtype" val="${entity.filedtype}" class="easyui-validatebox" data-options="required:true"  style="width: 160px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="FILEDTYPE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						长度:
					</td>
					<td>
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:false,validType:['integer','maxLength[3]']"
							id="entity_len" name="entity.len" value="${entity.len}">
					</td>
				</tr>
			
				<tr>
					<td class="title">
						是否为空:
					</td>
					<td>
						<select id="entity_isnull" name="entity.isnull" val="${entity.isnull}" class="easyui-validatebox" data-options="required:false"  style="width: 160px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNULL" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						注释:
					</td>
					<td>
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:false,validType:',maxLength[20]'"
							id="entity_note" name="entity.note" value="${entity.note}">
					</td>
				</tr>
				
				<tr>
					<td class="title" style="text-align: left;color: red">表单显示信息</td>
					<td colspan="3"></td>
				</tr>
				
				<tr>
					<td class="title">
						标签类型:
					</td>
					<td>
						<select id="entity_labeltype" name="entity.labeltype" val="${entity.labeltype}" class="easyui-validatebox" data-options="required:false"  style="width: 160px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="LABELTYPE" isTextValue="0" />
						</select>
					</td>
					
					<td class="title" id="inputtype1">
						输入类型:
					</td>
					<td id="inputtype2">
						<select id="entity_inputtype" name="entity.inputtype" val="${entity.inputtype}" class="easyui-validatebox" data-options="required:false"  style="width: 160px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="INPUTTYPE" isTextValue="0" />
						</select>
					</td>
					
					<td class="title" id="constant1">
						常量值:
					</td>
					<td id="constant2">
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:false,validType:['maxLength[20]']"
							id="entity_constant" name="entity.constant" value="${entity.constant}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						是否必填:
					</td>
					<td>
						<select id="entity_isrequired" name="entity.isrequired" val="${entity.isrequired}" class="easyui-validatebox" data-options="required:false"  style="width: 160px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNULL" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						排序:
					</td>
					<td>
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:false,validType:['integer','maxLength[3]']"
							id="entity_sort" name="entity.sort" value="${entity.sort}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						验证信息:
					</td>
					<td>
						<input type="text" style="width: 160px;" class="easyui-validatebox" data-options="required:false,validType:['maxLength[100]']"
							id="entity_validaInfo" name="entity.validaInfo" value="${entity.validaInfo}">
					</td>
					<td colspan="2" style="color: red">
						格式：integer, maxLength[5]
					</td>
				</tr>
				
			<c:if test="${pageset.pageType==1}"><!--新增-->
			</c:if>
			<c:if test="${pageset.pageType==2}"><!--修改-->
			</c:if>
			<c:if test="${pageset.pageType==0}"><!--浏览-->
			</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
			<a id="dom_add_entityfrmfield" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
			<a id="dom_edit_entityfrmfield" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfrmfield" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfrmfield = 'admin/FrmFieldaddCommit.do';
	var submitEditActionfrmfield = 'admin/FrmFieldeditCommit.do';
	var currentPageTypefrmfield = '${pageset.pageType}';
	var submitFormfrmfield;
	$(function() {
		//表单组件对象
		submitFormfrmfield = $('#dom_formfrmfield').SubmitForm( {
			pageType : currentPageTypefrmfield,
			grid : gridfrmfield,
			currentWindowId : 'winfrmfield'
		});

		$("td[id^='constant']").hide();

		var labeltype = $("#entity_labeltype option:selected").val();
		if(labeltype=='2'){
			$("td[id^='constant']").show();
			$("td[id^='inputtype']").hide();
			$('#entity_constant').validatebox({   
			    required: true 
			}); 
		}else{
			$("td[id^='inputtype']").show();
			$("td[id^='constant']").hide();
			$('#entity_constant').validatebox({   
			    required: false 
			});
		}
		
		$("#entity_labeltype").on("change",function(){
			if($(this).val()=='2'){
				$("td[id^='constant']").show();
				$("td[id^='inputtype']").hide();
				$('#entity_constant').validatebox({   
				    required: true 
				}); 
				
			}else{
				$("td[id^='constant']").hide();
				$("td[id^='inputtype']").show();
				$('#entity_constant').validatebox({   
				    required: false 
				});
			}
		})
		
		//字段类型
		$("#entity_filedtype").on("change",function(){
			if($(this).val()=='2'){
				$('#entity_len').validatebox({   //如果varchar2类型  长度必填 
				    required: true 
				}); 
				
			}else{
				$('#entity_len').validatebox({   
				    required: false 
				});
			}
		})
		
		
		//关闭窗口
		$('#dom_cancle_formfrmfield').bind('click', function() {
			$('#winfrmfield').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfrmfield').bind('click', function() {
			var tableid = $("#tableId").val();
			if(tableid == "" || tableid == null){
				alert("请先填写并保存表单信息！");
			}else{
				submitFormfrmfield.postSubmit(submitEditActionfrmfield);
			}
		});
		//提交修改数据
		$('#dom_edit_entityfrmfield').bind('click', function() {
			submitFormfrmfield.postSubmit(submitEditActionfrmfield);
		});
	});
	//-->
</script>