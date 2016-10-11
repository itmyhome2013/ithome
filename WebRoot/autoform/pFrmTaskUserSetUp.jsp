<table class="" id="dom_datagridfrmfield">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="TEXT" data-options="sortable:false" width="80"> 人员姓名 </th>
		</tr>
	</thead>
</table>

<form id="dom_formfrmtable">
	<input type="text" name="entity.taskkey" id="entity_taskkey" value="${entity.taskkey}">
	<input type="hidden" name="entity.users" id="entity_users" value="${entity.users}">
</form>


<script type="text/javascript">
	var submitEditActionFrmtable = 'admin/FrmTaskUsereditCommit.do'; //保存 
	var url_searchActionfrmfield = "admin/FrmQueryAllUser.do";//查询URL
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
	            var users = "${users}";
          	    var u = users.split(",");
				var leng;

          	    if(users == null || users == ""){
              	   leng = 0;
          	    }else{
              	    leng = u.length;
          	    }
	            $(".datagrid-toolbar:eq(1) tr").append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='red' id='info'>已选中"+leng+"人</font></td>");
	            
	            $.each(rowData,function(idx,val){//遍历JSON
	            	  
	            	  for(var i=0;i<u.length;i++){
	            		  if(val.ID == u[i]){
	  	                    $("#dom_datagridfrmfield").datagrid("selectRow", idx);//如果数据行为已选中则选中改行
	  	                  }
	            	  }
	                  
	            });              
	        },
	        onSelect:function(rowIndex, rowData){  //触发事件 
	        	var selectedArray = $(gridfrmfield).datagrid('getSelections');
	        	$("#info").text("已选中"+selectedArray.length+"人");
		    },
		    onUnselect:function(rowIndex, rowData){
		    	var selectedArray = $(gridfrmfield).datagrid('getSelections');
	        	$("#info").text("已选中"+selectedArray.length+"人");
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
		$("#entity_users").val($.farm.getCheckedIds(gridfrmfield, 'ID'));
		submitFormFrmTable.postSubmit(submitEditActionFrmtable,function(flag){
		});
		
		//console.info(selectedArray);
	}
	
	
</script>
		
			
										
										
