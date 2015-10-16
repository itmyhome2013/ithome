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
<!--人口基础信息-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<c:if test="${pageset.pageType==1}"></c:if>
		<c:if test="${pageset.pageType==2}"></c:if>
		<c:if test="${pageset.pageType==0}"></c:if>
		<form id="dom_formpopucitizeninfo">
			<input type="hidden" name="entity.citizenid" id="entity_citizenid" value="${entity.citizenid}">
			<input type="hidden" name="entity.huid" id="entity_huid" value="${entity.huid}">
			<table class="editTable">
							
				<tr>
					<td class="title" style="text-align: left;color: red">基本信息</td>
					<td colspan="3"></td>
				</tr>			
							
				<tr>
					<td class="title">
						居民姓名:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[15]']"
							id="entity_citizenname" name="entity.citizenname" value="${entity.citizenname}">
					</td>
					<td class="title">
						证件类型:
					</td>
					<td>
						<select id="entity_citizencardtype" name="entity.citizencardtype" val="${entity.citizencardtype}" class="easyui-validatebox" data-options="required:true"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENCARDTYPE" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						证件编号:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal']"
							id="entity_citizenidentity" name="entity.citizenidentity" value="${entity.citizenidentity}">
					</td>
					<td class="title">
						曾用名:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_citizenoldname" name="entity.citizenoldname" value="${entity.citizenoldname}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						与户主关系:
					</td>
					<td>
						<select id="entity_citizenhurelation" name="entity.citizenhurelation"  style="width: 180px" val="${entity.citizenhurelation}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="HUHOUSERELATION" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						是否房主:
					</td>
					<td>
						<select id="entity_citizenishouseower" name="entity.citizenishouseower"  style="width: 180px" val="${entity.citizenishouseower}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						户籍状态:
					</td>
					<td>
						<select id="entity_citizenhustate" name="entity.citizenhustate" val="${entity.citizenhustate}" class="easyui-validatebox" data-options="required:true"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="HUSTATE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						户口性质:
					</td>
					<td>
						<select id="entity_citizenhuquality" name="entity.citizenhuquality"  style="width: 180px" val="${entity.citizenhuquality}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="HUQUALE" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						民族:
					</td>
					<td>
						<select id="entity_citizennation" name="entity.citizennation"  style="width: 180px" val="${entity.citizennation}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZEN_MINGZU" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						性别:
					</td>
					<td>
						<select id="entity_citizensex" name="entity.citizensex" val="${entity.citizensex}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="SEX" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						出生日期:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']" 
							id="entity_citizenbirth" name="entity.citizenbirth" value="${entity.citizenbirth}">
					</td>
					<td class="title">
						手机号码:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_citizenmobile" name="entity.citizenmobile" value="${entity.citizenmobile}">
					</td>
				</tr>
				
				<tr>
					<td class="title" style="text-align: left;color: red">居住信息</td>
					<td colspan="3"></td>
				</tr>
				
				<tr>
					<td class="title">
						出生地址:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[100]']"
							id="entity_citizenbirthaddress" name="entity.citizenbirthaddress" value="${entity.citizenbirthaddress}">
					</td>
					<td class="title">
						户籍地址:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[100]']"
							id="entity_citizennativeaddress" name="entity.citizennativeaddress" value="${entity.citizennativeaddress}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						房屋地址:
					</td>
					<td colspan="3" class="adrressBoxClass">
						<span id="addressCitizenInfoAllPathId"></span>
						
						<select id="entity_citizenfloor" class="easyui-combotree" style="width: 180px;" val="${entity.citizenfloortitle}"
							data-options="url:'admin/ALONEDictionaryTree_loadTree.do?index=ADDRESS_TREE',onlyLeaf:true,onSelect:beforeSelect,val:['${entity.citizenfloor}','${entity.citizenfloortitle}']">
						</select>	
						
						<input type="hidden" id="entity_citizenfloorVal" name="entity.citizenfloor" value="${entity.citizenfloor}">
						<input type="hidden" id="entity_citizenfloorTitle" name="entity.citizenfloortitle" value="${entity.citizenfloortitle}">
						
						<select id="entity_citizencell" name="entity.citizencell"
							val="${entity.citizencell}" class="easyui-validatebox"
							data-options="required:true" style="width: 80px;">
							<option value="">选择单元</option>
							<PF:OptionDictionary index="HU_CELL" isTextValue="0" />
						</select>
						<input type="text" style="width: 60px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','integer','maxLength[5]']"
							id="entity_citizenroomno" name="entity.citizenroomno" value="${entity.citizenroomno}">
						<span id="roomno">(房间号)</span>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						居住地址:
					</td>
					<td colspan="3">
						<input type="text" style="width: 450px;" class="easyui-validatebox" data-options="required:true,validType:['unnormal','maxLength[100]']"
							id="entity_citizenliveaddress" name="entity.citizenliveaddress" value="${entity.citizenliveaddress}">
						<c:if test="${pageset.pageType!=0}">
							<a href="javascript:void(0);" id="copy_citizen_address">复制房屋地址</a>
						</c:if>
					</td>
					
				</tr>
				
				<tr>
					<td class="title">
						何时迁入:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']" 
							id="entity_citizenindate" name="entity.citizenindate" value="${entity.citizenindate}">
					</td>
					<td class="title">
						何地迁入:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_cityzeninaddress" name="entity.cityzeninaddress" value="${entity.cityzeninaddress}">
					</td>
				</tr>
				
				<tr>
					<td class="title" style="text-align: left;color: red">工作信息</td>
					<td colspan="3"></td>
				</tr>	
				
				<tr>
					<td class="title">
						人员类别:
					</td>
					<td>
						<select id="entity_citizentype" name="entity.citizentype"  style="width: 180px" val="${entity.citizentype}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENTYPE" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						就业现状:
					</td>
					<td>
						<select id="entity_citizenjobstate" name="entity.citizenjobstate"  style="width: 180px" val="${entity.citizenjobstate}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENJOBSTATE" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						职业:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_citizenjob" name="entity.citizenjob" value="${entity.citizenjob}">
					</td>
					<td class="title">
						单位地址:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[50]']"
							id="entity_citizenunitaddress" name="entity.citizenunitaddress" value="${entity.citizenunitaddress}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						单位电话:
					</td>
					<td colspan="3">
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_citizentel" name="entity.citizentel" value="${entity.citizentel}">
					</td>
				</tr>
				
				<tr>
					<td class="title" style="text-align: left;color: red">其他信息</td>
					<td colspan="3"></td>
				</tr>
				
				<tr>
					<td class="title">
						婚姻状况:
					</td>
					<td>
						<select id="entity_citizenwedstate" name="entity.citizenwedstate"  style="width: 180px" val="${entity.citizenwedstate}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="WED_HYZK" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						血型:
					</td>
					<td>
						<select id="entity_citizenbloodtype" name="entity.citizenbloodtype"  style="width: 180px" val="${entity.citizenbloodtype}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENBLOODTYPE" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						身高:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[6]']"
							id="entity_citizenheight" name="entity.citizenheight" value="${entity.citizenheight}">
					</td>
					<td class="title">
						健康状况:
					</td>
					<td>
						<select id="entity_citizenhealth" name="entity.citizenhealth"  style="width: 180px" val="${entity.citizenhealth}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENHEALTH" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						宗教信仰:
					</td>
					<td>
						<select id="entity_citizenfaith" name="entity.citizenfaith"  style="width: 180px" val="${entity.citizenfaith}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZENFAITH" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						政治面貌:
					</td>
					<td>
						<select id="entity_citizenstatus" name="entity.citizenstatus"  style="width: 180px" val="${entity.citizenstatus}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CHILDPOLITICSSTATUS" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						文化程度:
					</td>
					<td>
						<select id="entity_citizeneducation" name="entity.citizeneducation"  style="width: 180px" val="${entity.citizeneducation}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="CITIZEN_WHCD" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						矫正人员:
					</td>
					<td>
						<select id="entity_isrectify" name="entity.isrectify"  style="width: 180px" val="${entity.isrectify}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					
					<td class="title">
						帮教人员:
					</td>
					<td>
						<select id="entity_ishelpeducate" name="entity.ishelpeducate"  style="width: 180px" val="${entity.ishelpeducate}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						兵役状况:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_citizenservice" name="entity.citizenservice" value="${entity.citizenservice}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						兵役服务处所:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[15]']"
							id="entity_citizenserviceplace" name="entity.citizenserviceplace" value="${entity.citizenserviceplace}">
					</td>
					<td class="title">
						是否优抚对象:
					</td>
					<td>
						<select id="entity_citizencare" name="entity.citizencare"  style="width: 180px" val="${entity.citizencare}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						户籍非户籍:
					</td>
					<td>
						<select id="entity_citizenfloatingpopulation" name="entity.citizenfloatingpopulation" val="${entity.citizenfloatingpopulation}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="HUFLOATINGPOPULATION" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						是否低保:
					</td>
					<td>
						<select id="entity_citizenlowsafe" name="entity.citizenlowsafe"  style="width: 180px" val="${entity.citizenlowsafe}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						是否常住:
					</td>
					<td>
						<select id="entity_citizenlonglive" name="entity.citizenlonglive" val="${entity.citizenlonglive}" class="easyui-validatebox" data-options="required:false"  style="width: 180px">
							<option value="">请选择</option>
							<PF:OptionDictionary index="ISNOT" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						备注:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[50]']"
							id="entity_citizennote" name="entity.citizennote" value="${entity.citizennote}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						管理类型:
					</td>
					<td>
						<select id="entity_citizenmanagerkind" name="entity.citizenmanagerkind"  style="width: 180px" val="${entity.citizenmanagerkind}">
							<option value="">请选择</option>
							<PF:OptionDictionary index="HUMANAGERKIND" isTextValue="0" />
						</select>
					</td>
					<td class="title">
						流入时间:
					</td>
					<td>
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']" 
							id="entity_citizencomeindate" name="entity.citizencomeindate" value="${entity.citizencomeindate}">
					</td>
				</tr>
				
				<tr>
					<td class="title">
						流出时间:
					</td>
					<td colspan="3">
						<input type="text" style="width: 180px;" class="easyui-datebox" data-options="required:false,validType:['date']" 
							id="entity_citizenoutdate" name="entity.citizenoutdate" value="${entity.citizenoutdate}">
					</td>
				
				</tr>
				
				<c:if test="${pageset.pageType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.pageType==2}">
					<!--修改-->
				</c:if>
				<c:if test="${pageset.pageType==0}">
					<tr>
						<td class="title">
							创建日期:
						</td>
						<td>
							<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[3.5]']" 
								id="entity_createdate" name="entity.createdate" value="${entity.createdate}">
						</td>
						<td class="title">
							更新日期:
						</td>
						<td>
							<input type="text" style="width: 180px;" class="easyui-validatebox" data-options="required:false,validType:['unnormal','maxLength[3.5]']"
								id="entity_updatedate" name="entity.updatedate" value="${entity.updatedate}">
						</td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.pageType==1}">
				<a id="dom_add_entitypopucitizeninfo" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<c:if test="${pageset.pageType==2}">
				<a id="dom_add_entitypopucitizeninfo" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">保存</a>
			</c:if>
			<a id="dom_cancle_formpopucitizeninfo" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionpopucitizeninfo = 'admin/PopuCitizenInfoaddCommit.do';
	var submitEditActionpopucitizeninfo = 'admin/PopuCitizenInfoeditCommit.do';
	var currentPageTypepopucitizeninfo = '${pageset.pageType}';
	var submitFormpopucitizeninfo;
	$(function() {
		//表单组件对象
		submitFormpopucitizeninfo = $('#dom_formpopucitizeninfo').SubmitForm( {
			pageType : currentPageTypepopucitizeninfo,
			grid : gridpopucitizeninfo
		});
		//关闭窗口
		$('#dom_cancle_formpopucitizeninfo').bind('click', function() {
			$('#winpopucitizeninfo').window('close');
		});
		//提交新增数据
		$('#dom_add_entitypopucitizeninfo').bind('click',function() {
							$.post("admin/IdCardCheckOnly_CitizenInfo.do?idCard='"+$('#entity_citizenidentity').val()+"'&huid="+$('#entity_huid').val()+"&citizenid="+$('#entity_citizenid').val()+"", {},function(data){
								if(data.only&&$("#entity_citizencardtype").val()=="124"){
									alert("该身份证号已存在");
									$("#entity_citizenidentity").focus();
								}else{
									submitFormpopucitizeninfo.postSubmit(submitEditActionpopucitizeninfo, function(flag) {
										$('#CitizenInfoIDVarId').val(flag.entity.citizenid);
										$('#entity_citizenid','#dom_formpopucitizeninfo').val(flag.entity.citizenid);
									});
								}
							})
							
				});

		var citizencardtype = $("#entity_citizencardtype").val();
		if(citizencardtype!="124"){
			$('#entity_citizenidentity').validatebox({   
			    required: false,   
			    validType: 'unnormal'  
			});
		}else{
			$('#entity_citizenidentity').validatebox({   
			    required: false,   
			    validType: 'idcard'  
			});
		}
		$("#entity_citizencardtype").live("change",function(){
			if($(this).val()=="124"){
				$('#entity_citizenidentity').validatebox({   
				    required: true,   
				    validType: 'idcard'  
				}); 
			}else{
				$('#entity_citizenidentity').validatebox({   
				    required: false,   
				    validType: 'unnormal'  
				});
			}
		})
		$("#entity_citizenidentity").live("change",function(){
			if($("#entity_citizencardtype").val()=="124"){
				if($("#entity_citizenidentity").val().length==18){ //18位身份证 
					var identity = $(this).val().substring(16,17);
					if(!isNaN(identity)&&identity!=""){
						if(identity%2==0){
							$("#entity_citizensex").val("118")  //女
						}else{
							$("#entity_citizensex").val("117")  //男 
						}
					}else{
						$("#entity_citizensex").val("")
					}
					
					var year = $(this).val().substring(6,10);
					var month = $(this).val().substring(10,12);
					var day = $(this).val().substring(12,14);
					var bir = year+"-"+month+"-"+day;
					
					if((!isNaN(year)&&year!="")&&(!isNaN(month)&&month!="")&&(!isNaN(day)&&day!="")){
						$("#entity_citizenbirth").datebox('setValue', bir);
					}
				}
				if($("#entity_citizenidentity").val().length==15){ //15位身份证 
					var identity = $(this).val().substring(14,15);
					
					if(!isNaN(identity)&&identity!=""){
						if(identity%2==0){
							$("#entity_citizensex").val("118")  //女
						}else{
							$("#entity_citizensex").val("117")  //男 
						}
					}else{
						$("#entity_citizensex").val("")
					}
					
					var year = $(this).val().substring(6,8);
					var month = $(this).val().substring(8,10);
					var day = $(this).val().substring(10,12);
					var bir = year+"-"+month+"-"+day;
					
					if((!isNaN(year)&&year!="")&&(!isNaN(month)&&month!="")&&(!isNaN(day)&&day!="")){
						$("#entity_citizenbirth").datebox('setValue', bir);
					}
					
				}
			}
		})
		//常住人口暂住人口 自动带出现居住地址
		if(${pageset.pageType==1}){
			var livestate = $("#entity_citizenlivestate").val();
			if(livestate=="1"||livestate=="2"){
				$("#entity_citizenliveaddress").val("${liveaddress}")
			}
			$("#entity_citizenlivestate").on("change",function(){
				var livestate = $("#entity_citizenlivestate").val();
				if(livestate=="1"||livestate=="2"){
					$("#entity_citizenliveaddress").val("${liveaddress}")
				}else{
					$("#entity_citizenliveaddress").val("")
				}
			})
		}
	});
	$(function(){
		if($("#entity_citizenhurelation").val()=="1"||${entity.citizenhurelation=="1"}){
			$("#fangRelation1").show();
			$("#fangRelation2").show();
		}else{
			$("#fangRelation1").hide();
			$("#fangRelation2").hide();
		}
		$("#entity_citizenhurelation").bind("change",function(){
			if($("#entity_citizenhurelation").val()=="1"){
				$("#fangRelation1").show();
				$("#fangRelation2").show();
			}else{
				$("#fangRelation1").hide();
				$("#fangRelation2").hide();
			}
		})
	})
	
	$(function(){
		
		loadfloorPath('${node.TREECODE}');
		$('#entity_citizenfloorVal').val('${node.ID}');  //设置citizenfloor
	});
	//在选中楼的时候给隐藏楼字段赋值
	function beforeSelect(node){
		
		$('#entity_citizenfloorVal').val(node.id);
		$('#entity_citizenfloorTitle').val(node.text);

		$.post("admin/ALONEDictionaryType_ByIdGetTreecode.do",{ids:node.id},function(flag){
			loadfloorPath(flag.treeCode);
		});
		
	}
	//显示地址楼的全地址路径(floorId节点CODE)
	function loadfloorPath(floorId){
		$('#addressCitizenInfoAllPathId').text("load...");
		$.post("admin/PopuAjaxAddressNodeLoad.do",{ids:floorId},function(flag){
			$('#addressCitizenInfoAllPathId').text(flag);
		});
	
	}
	$(function(){
		//复制房屋地址
		$("#copy_citizen_address").on("click",function(){
			var addressAll = $("#addressCitizenInfoAllPathId").html();
			var hufloor = $('#entity_citizenfloor').combotree('tree').tree('getSelected'); //获取combotree被选中的值
			if(hufloor==null){
				hufloor = "";
				if(${pageset.pageType==2}){ //如果是编辑状态 提取隐藏域里的值  
					hufloor = $("#myfloor").val();
				}
			}else{
				hufloor = hufloor.text
			}
			var hucell = "";
			if($("#entity_citizencell").val()!=""){
				hucell = $("#entity_citizencell").find("option:selected").text();
			}
			var huroomno = $("#entity_citizenroomno").val();
	        if(huroomno!=""){
	        	huroomno+="号";
	        }
	        //判断是否隐藏
			if($("#entity_citizencell").is(":visible")==false){
				hucell="";
			}
			if($("#entity_citizenroomno").is(":visible")==false){
				huroomno="";
			}
			$("#entity_citizenliveaddress").val(addressAll+hufloor+hucell+huroomno);
		})
	})
	
	//-->
</script>
<!-- 
 -->