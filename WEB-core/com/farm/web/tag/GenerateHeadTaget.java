package com.farm.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ithome.autoform.domain.FrmField;
import com.ithome.autoform.server.FrmFieldManagerInter;

import com.farm.console.FarmManager;
import com.farm.core.sql.result.DataResult;
import com.farm.web.spring.BeanFactory;


public class GenerateHeadTaget extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String tableId;

	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		//List<Map<String, Object>> list = null;
		JspWriter jspw = this.pageContext.getOut();

		/*
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th field="ID" data-options="sortable:true" width="80"> 编号 </th>
				<th field="TABLEID" data-options="sortable:true" width="80"> 表ID </th>
				<th field="CNNAME" data-options="sortable:true" width="80"> 中文名 </th>
				<th field="ENNAME" data-options="sortable:true" width="80"> 英文名 </th>
				<th field="FILEDTYPE" data-options="sortable:true" width="80"> 字段类型 </th>
				<th field="LEN" data-options="sortable:true" width="80"> 长度 </th>
			</tr>
		</thead>
		*/
		
		List<FrmField> list = aloneIMP.querFrmFieldsByTableId(tableId);
		
		StringBuffer buf = new StringBuffer();
		buf.append("<thead><tr>");
		buf.append("<th data-options='field:\"ck\",checkbox:true'></th>");
		
		int viewNum = list.size()>6?6:list.size(); //最多显示6列
		
		for(int i=0;i<viewNum;i++){
			buf.append("<th field='"+list.get(i).getEnname().toUpperCase()+"' data-options='sortable:true' width='50'> "+list.get(i).getCnname()+" </th>");
		}
		
		buf.append("</tr></thead>");
		
		try {
			jspw.println(buf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 分隔数组
	 * @param ary
	 * @param subSize
	 * @return
	 */
	private List<List<FrmField>> splitAry(List<FrmField> ary, int subSize) {
		
		int count = ary.size() % subSize == 0 ? ary.size() / subSize : ary.size() / subSize + 1;
		List<List<FrmField>> subAryList = new ArrayList<List<FrmField>>();
		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<FrmField> list = new ArrayList<FrmField>();
			int j = 0;
			while (j < subSize && index < ary.size()) {
				list.add((FrmField)ary.get(index++));
				j++;
			}
			subAryList.add(list);
		}
		/*Object[] subAry = new Object[subAryList.size()];
		for (int i = 0; i < subAryList.size(); i++) {
			List<FrmField> subList = subAryList.get(i);
			List<FrmField>[] subAryItem = new ArrayList[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j].add(subList.get(j));
			}
			subAry[i] = subAryItem;
		}*/
		return subAryList;
	}

	/**
	 * 字典转换
	 * @param list
	 */
	public void dataTrans(List<FrmField> list){
		Map<String, String> map = FarmManager.instance().findDicTitleForIndex("INPUTTYPE");
		
		for(FrmField node : list){
			String key = String.valueOf(node.getInputtype());
			Object value = map.get(key);
			if (value != null) {
				node.setInputtype(value.toString());
			}
		}
	}
	
	@Override
	public int doStartTag() throws JspException {

		return 0;
	}

	private final static FrmFieldManagerInter aloneIMP = (FrmFieldManagerInter) BeanFactory
		.getBean("FRM_FIELDProxyId");
	
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

}
