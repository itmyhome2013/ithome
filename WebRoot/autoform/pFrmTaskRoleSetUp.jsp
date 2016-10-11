<table class="" id="dom_datagridfrmfield">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="TEXT" data-options="sortable:false" width="80"> 角色名称 </th>
		</tr>
	</thead>
</table>

<form id="dom_formfrmtable">
	<input type="text" name="entity.taskkey" id="entity_taskkey" value="${entity.taskkey}">
	<input type="hidden" name="entity.role" id="entity_role" value="${entity.role}">
</form>


<script type="text/javascript">
	var submitEditActionFrmtable = 'admin/FrmTaskRoleeditCommit.do'; //保存 
	var url_searchActionfrmfield = "admin/FrmQueryAllRole.do";//查询URL
	var gridfrmfield;//数据表格对象
	var searchfrmfield;//条件查询组件对象
	var currentPageTypeFrmtable = '${pageset.pageType}';
	var submitFormFrmTable;
	var TOOL_BARfrmfield = [ 
    {
   		id : 'save',
   		text : '保存',
   		iconCls : 'icon-save',
   		handler : save
   	}]
	
	$(function() {
		//初始化数据表格
		gridfrmfield = $('#dom_datagridfrmfield').datagrid( {
			url : url_searchActionfrmfield,
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfrmfield,
			//pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			//ctrlSelect : true,
			onLoadSuccess:function(row){//当表格成功加载时执行               
	            var rowData = row.rows;
	            var role = "${role}";
          	    var u = role.split(",");

     
	            $.each(rowData,function(idx,val){//遍历JSON
	            	  
	            	  for(var i=0;i<u.length;i++){
	            		  if(val.ID == u[i]){
	  	                    $("#dom_datagridfrmfield").datagrid("selectRow", idx);//如果数据行为已选中则选中改行
	  	                  }
	            	  }
	                  
	            });              
	        }
		});
		//初始化条件查询
		searchfrmfield = $('#dom_searchfrmfield').searchForm( {
			gridObj : gridfrmfield
		});

		//表单组件对象
		submitFormFrmTable = $('#dom_formfrmtable').SubmitForm( {
			pageType : currentPageTypeFrmtable,
			grid : gridfrmtable,
			currentWindowId : 'winfrmtable'
		});

		//////

		
		
	});

	function save(){

		//var selectedArray = $(gridfrmfield).datagrid('getSelections');
		$("#entity_role").val($.farm.getCheckedIds(gridfrmfield, 'ID'));
		submitFormFrmTable.postSubmit(submitEditActionFrmtable,function(flag){
		});
		
		//console.info(selectedArray);
	}
	
	
</script>
		
			
										
										
