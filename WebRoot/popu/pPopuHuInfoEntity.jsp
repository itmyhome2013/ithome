<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<c:if test="${pageset.pageType==0}">
	<style>
<!--
.adrressBoxClass>span {
	float: left;
}

.adrressBoxClass>div {
	float: left;
}
-->
</style>
</c:if>
<!--房屋基础信息-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<form id="dom_formpopuhuinfo">
			<input type="hidden" name="entity.huid" id="entity_huid" value="${entity.huid}">
			<table class="editTable">
			
				<tr>
					<td class="title" style="text-align: left;color: red">基本信息</td>
					<td colspan="3"></td>
				</tr>
				
				<tr>
					<td class="title">
						证件类型:
					</td>
					<td>
						<select id="entity_hucardtype" name="entity.hucardtype" val="${entity.hucardtype}" class="easyui-validatebox" data-options="required:true"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENCARDTYPE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						证件编号:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['idcard']" 
					    	id="entity_huidentity" name="entity.huidentity" value="${entity.huidentity}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						户主姓名:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[30]']" 
					    	id="entity_huname" name="entity.huname" value="${entity.huname}">
					</td>
					<td class="title">
						户口性质:
					</td>
					<td>
						<select name="entity.huquale" id="entity_huquale" style="width: 180px;" val="${entity.huquale}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUQUALE" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						户籍状态:
					</td>
					<td>
						<select name="entity.hustate" id="entity_hustate" class="easyui-validatebox" data-options="required:true" style="width: 180px;" val="${entity.hustate}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUSTATE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						户籍地址:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[100]']" 
					    	id="entity_huaddress" name="entity.huaddress" value="${entity.huaddress}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						户主电话:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']" 
					    	id="entity_hutel" name="entity.hutel" value="${entity.hutel}">
					</td>
					<td class="title">
						户主手机:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer','maxLength[20]']" 
					    	id="entity_humobile" name="entity.humobile" value="${entity.humobile}">
					</td>
				</tr>
				<tr>
					<td class="title">
						户口类别:
					</td>
					<td>
						<select name="entity.hutype" id="entity_hutype" style="width: 180px;" val="${entity.hutype}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUTYPE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						家庭电话:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[20]']" 
					    	id="entity_hufamilytel" name="entity.hufamilytel" value="${entity.hufamilytel}">
					</td>
					
				</tr>
				<tr>
					<td class="title">
						户籍人数:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer','maxLength[10]']" 
					    	id="entity_hupeoplenum" name="entity.hupeoplenum" value="${entity.hupeoplenum}">
					</td>
					<td class="title">
						户籍签办人:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[20]']" 
					    	id="entity_husignman" name="entity.husignman" value="${entity.husignman}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						签发时间:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']" 
					    	id="entity_husigndate" name="entity.husigndate" value="${entity.husigndate}">
					</td>
					<td class="title">
						户主与房主的关系:
					</td>
					<td>
					    <select name="entity.huhouserelation" id="entity_huhouserelation" style="width: 180px;" val="${entity.huhouserelation}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUHOUSERELATION" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title" style="text-align: left;color: red">居住信息</td>
					<td colspan="3"></td>
				</tr>
				
				<tr>
					<td class="title">
						房屋地址:
					</td>
					<td colspan="3" class="adrressBoxClass">
					
						<span id="addressAllPathId"></span>
						
						<select id="entity_hufloor" class="easyui-combotree" style="width: 180px;" val="${entity.hufloortitle}"
							data-options="required:true,url:'admin/ALONEDictionaryTree_loadTree.do?index=ADDRESS_TREE',onlyLeaf:true,onSelect:beforeSelect,val:['${entity.hufloor}','${entity.hufloortitle}']">
						</select>	
						
						<input type="hidden" id="entity_hufloorVal" name="entity.hufloor" value="${entity.hufloor}">
						<input type="hidden" id="entity_hufloorTitle" name="entity.hufloortitle" value="${entity.hufloortitle}">
				
						<select id="entity_hucell" name="entity.hucell" val="${entity.hucell}" class="easyui-validatebox" data-options="required:true,isAddStar:false" style="width: 80px;">
							<option value="">选择单元</option>
							<PF:OptionDictionary index="HU_CELL" isTextValue="0" />
						</select>
						<input type="text" style="width: 60px;" class="easyui-validatebox" data-options="required:true,isAddStar:false,validType:['unnormal','integer','maxLength[5]']"
							id="entity_huroomno" name="entity.huroomno" value="${entity.huroomno}">
						<span id="roomno">(房间号)</span>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						居住地址:
					</td>
					<td colspan="3">
						<input type="text" style="width: 450px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[150]']" 
					    	id="entity_hucurraddress" name="entity.hucurraddress" value="${entity.hucurraddress}">
					    <c:if test="${pageset.pageType!=0}">
							<a href="javascript:void(0);" id="copy_hu_address">复制房屋地址</a>
						</c:if>
					</td>
				</tr>
				
				
				<tr>
					<td class="title">
						家庭人数:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer','maxLength[10]']"
							id="entity_hufamilypnum" name="entity.hufamilypnum" value="${entity.hufamilypnum}">
					</td>
					<td class="title">
						家庭结构:
					</td>
					<td>
						<select name="entity.hustructure" id="entity_hustructure" style="width: 180px;" val="${entity.hustructure}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUSTRUCTURE" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						住房性质:
					</td>
					<td>
						<select name="entity.huhousequale" id="entity_huhousequale" style="width: 180px;" val="${entity.huhousequale}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUHOUSEQUALE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						房主/产权单位:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[50]']" 
					    	id="entity_huunit" name="entity.huunit" value="${entity.huunit}">
					</td>
				</tr>
				
				
				<tr>
					<td class="title">
						住房面积(M2):
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['intOrFloat','maxLength[6]']" 
					    	id="entity_huarea" name="entity.huarea" value="${entity.huarea}">
					</td>
					<td class="title">
						人均面积(M2):
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['intOrFloat','maxLength[6]']" 
					    	id="entity_huavgarea" name="entity.huavgarea" value="${entity.huavgarea}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						区外住房(套):
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer']" 
					    	id="entity_huouthousenum" name="entity.huouthousenum" value="${entity.huouthousenum}">
					</td>
					<td class="title">
						区内住房（套）:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer']" 
					    	id="entity_huinnerhousenum" name="entity.huinnerhousenum" value="${entity.huinnerhousenum}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						人均收入:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['integer']" 
					    	id="entity_huavgincome" name="entity.huavgincome" value="${entity.huavgincome}">
					</td>
					<td class="title">
						是否低保家庭:
					</td>
					<td>
						<select name="entity.hulowsafe" id="entity_hulowsafe" style="width: 180px;" val="${entity.hulowsafe}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						是否常住:
					</td>
					<td colspan="3">
						<select name="entity.hulonglive" id="entity_hulonglive" style="width: 180px;" val="${entity.hulonglive}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						备注:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[200]']" 
					    	id="entity_hunote" name="entity.hunote" value="${entity.hunote}">
					</td>
					<td class="title">
						管理类型:
					</td>
					<td>
						<select name="entity.humanagerkind" id="entity_humanagerkind" style="width: 180px;" val="${entity.humanagerkind}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUMANAGERKIND" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						流入时间:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']"
					    	id="entity_huindate" name="entity.huindate" value="${entity.huindate}">
					</td>
					<td class="title">
						户籍非户籍:
					</td>
					<td>
						<select name="entity.hufloatingpopulation" id="entity_hufloatingpopulation" style="width: 180px;" val="${entity.hufloatingpopulation}">
							<option value=""> --请选择-- </option>
							<PF:OptionDictionary index="HUFLOATINGPOPULATION" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						流出时间:
					</td>
					<td colspan="3">
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']"
					    	id="entity_huoutdate" name="entity.huoutdate" value="${entity.huoutdate}">
					</td>
				</tr>
				
				<c:if test="${pageset.pageType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.pageType==2}">
					<!--修改-->
				</c:if>
				<c:if test="${pageset.pageType==0}">
					<!--浏览-->
				</c:if>
				<c:if test="${pageset.pageType==0}">
					<tr>
						<td class="title">
							创建日期:
						</td>
						<td>
							<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[3.5]']" 
								id="entity_hucreatedate" name="entity.hucreatedate" value="${entity.hucreatedate}">
						</td>
						<td class="title">
							更新日期:
						</td>
						<td>
							<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[3.5]']"
								id="entity_huupdatedate" name="entity.huupdatedate" value="${entity.huupdatedate}">
						</td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
				<a id="dom_edit_entitypopuhuinfo" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_edit_entitypopuhuinfo" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_formpopuhuinfo" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionpopuhuinfo = 'admin/PopuHuInfoaddCommit.do';
	var submitEditActionpopuhuinfo = 'admin/PopuHuInfoeditCommit.do';
	var currentPageTypepopuhuinfo = '${pageset.pageType}';
	var submitFormpopuhuinfo;
	$(function() {
		//表单组件对象
		submitFormpopuhuinfo = $('#dom_formpopuhuinfo').SubmitForm( {
			pageType : currentPageTypepopuhuinfo,
			grid : gridpopuhuinfo,
			currentWindowId : 'winpopuhuinfo'
		});
		//关闭窗口
		$('#dom_cancle_formpopuhuinfo').bind('click', function() {
			$('#winpopuhuinfo').window('close');
		});
		
	
		//提交新增和修改数据
		$('#dom_edit_entitypopuhuinfo').bind('click', function() {
			submitFormpopuhuinfo.postSubmit(submitEditActionpopuhuinfo,function(flag){
				$('#HuInfoIDVarId').val(flag.entity.huid);
				$('#entity_huid', '#dom_formpopuhuinfo').val(flag.entity.huid);
			});
		});

		var citizencardtype = $("#entity_hucardtype").val();
		if(citizencardtype!="124"){
			$('#entity_huidentity').validatebox({   
			    required: false,   
			    validType: 'unnormal'  
			});
		}else{
			$('#entity_huidentity').validatebox({   
			    required: false,   
			    validType: 'idcard'  
			});
		}
		$("#entity_hucardtype").live("change",function(){
			if($(this).val()=="124"){
				$('#entity_huidentity').validatebox({   
				    required: true,   
				    validType: 'idcard'  
				}); 
			}else{
				$('#entity_huidentity').validatebox({   
				    required: false,   
				    validType: 'unnormal'  
				});
			}
		})
		
		
	});
	$(function(){
		loadfloorPath('${node.TREECODE}');
	});
	//在选中楼的时候给隐藏楼字段赋值
	function beforeSelect(node){
		//设置hidden变量entity_hufloorVal
		$('#entity_hufloorVal').val(node.id);
		$('#entity_hufloorTitle').val(node.text);

		$.post("admin/ALONEDictionaryType_ByIdGetTreecode.do",{ids:node.id},function(flag){
			loadfloorPath(flag.treeCode);
		});
	}

	//复制房屋地址
	$("#copy_hu_address").bind("click",function(){
		var addressAll = $("#addressAllPathId").html();
		//var hufloor = $('#entity_hufloor').combotree('tree').tree('getSelected'); //获取combotree被选中的值
		var hufloor = $('#entity_hufloorTitle').val();
		if(hufloor==null){
			hufloor = "";
			if(${pageset.pageType==2}){ //如果是编辑状态 提取隐藏域里的值  
				hufloor = $("#myfloor").val();
			}
		}else{
			//hufloor = hufloor.text
		}
		var hucell = "";
		if($("#entity_hucell").val()!=""){
			hucell = $("#entity_hucell").find("option:selected").text();
		}
		var huroomno = $("#entity_huroomno").val();
        if(huroomno!=""){
        	huroomno+="号";
        }
        //判断是否隐藏
		if($("#entity_hucell").is(":visible")==false){
			hucell="";
		}
		if($("#entity_huroomno").is(":visible")==false){
			huroomno="";
		}
		$("#entity_hucurraddress").val(addressAll+hufloor+hucell+huroomno);
	})
	
	//显示地址楼的全地址路径(floorId节点CODE)
	function loadfloorPath(floorId,floorText){
		//$('#addressAllPathId').text("load...");
		$.post("admin/PopuAjaxAddressNodeLoad.do",{ids:floorId},function(flag){
			if(typeof(floorText)=="undefined"){
				floorText = "";
			}
			$('#addressAllPathId').text(flag);
			//$('#entity_livesaddress').val(flag+floorText);
		});
	}
	//-->
</script>