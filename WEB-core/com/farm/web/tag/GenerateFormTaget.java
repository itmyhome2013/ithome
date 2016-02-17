package com.farm.web.tag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ithome.autoform.domain.FrmField;
import com.ithome.autoform.domain.FrmTable;
import com.ithome.autoform.server.FrmFieldManagerInter;
import com.ithome.autoform.server.FrmTableManagerInter;

import com.farm.console.FarmManager;
import com.farm.core.autoform.DBUtil;
import com.farm.core.sql.result.DataResult;
import com.farm.web.spring.BeanFactory;
import com.farm.web.string.StringUtils;


public class GenerateFormTaget extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String tableId; //表ID
	private String keyId;   //主键ID

	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		JspWriter jspw = this.pageContext.getOut();
		String fieldLen = "160px";  //input文本框长度
		String areaLen = "260px";   //textarea文本域长度
		StringBuffer fieldsList = new StringBuffer(); //表单字段
		
		//表中所有字段
		List<FrmField> list = aloneFieldIMP.querFrmFieldsByTableId(tableId);
		dataTrans(list);
		for(FrmField f : list){
			fieldsList.append(f.getEnname()).append(",");
			if("1".equals(f.getIsrequired())){
				f.setIsrequired("true");
			}
		}
		
		FrmTable frmTable = aloneTableIMP.getFrmTableEntity(tableId);
		Map<String,String> formValue = new HashMap<String,String>();  //表单将要回显的值
		
		//如果keyId有值则为修改或查看
		if(!"".equals(keyId) && keyId!=null){
			keyId = "'" + keyId + "'";
			String sql = "select "+fieldsList.substring(0, fieldsList.length()-1)+" from "+frmTable.getEnname()+" where id = "+keyId+""; 
			Connection conn = DBUtil.getConnection();
			PreparedStatement pst;
			ResultSet rs;
			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				while(rs.next()){
					
					String fs[] = fieldsList.toString().split(",");
					for(int k=0;k<fs.length;k++){
						formValue.put(fs[k], rs.getString(fs[k]));
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		for(Map.Entry<String, String> entry : formValue.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		//DataResult.runDictionaryList(list,FarmManager.instance().findDicTitleForIndex("FILEDTYPE"), "FILEDTYPE");
		
		// 5个 3行 
		
		List<FrmField> rowList = new ArrayList<FrmField>();
		
		int splitSize = 2;// 分割的块大小
		List<List<FrmField>> subAry = splitAry(list, splitSize);
		StringBuffer rowAll = new StringBuffer();
		for(int i=0;i<subAry.size();i++){
			List<FrmField> ff = subAry.get(i);
			StringBuffer rows = new StringBuffer("<tr>");
			if(ff.size()==2){
				for(int j=0;j<ff.size();j++){
					StringBuffer buf = new StringBuffer();
					buf.append("<td class='title'>");
					buf.append(ff.get(j).getCnname());
					buf.append("</td>");
					buf.append("<td>");
					if(!"".equals(keyId) && keyId!=null){  //修改或查看
						//Select下拉框
						if("2".equals(ff.get(j).getLabeltype())){
							StringBuffer value = new StringBuffer();
							value.append("<select id='entity_"+ff.get(j).getEnname()+"' name='"+ff.get(j).getEnname()+"' val='"+StringUtils.filterNull(formValue.get(ff.get(j).getEnname()))+"' class='easyui-validatebox' data-options='required:"+ff.get(j).getIsrequired()+"'  style='width: "+fieldLen+"'>");
							value.append("<option value=''>请选择</option>");
							
							List<Map<String, Object>> constlist = null;
							constlist = FarmManager.instance().findDicTitleForIndeHasSort(ff.get(j).getConstant());
							for (Iterator iterator = constlist.iterator(); iterator.hasNext();) {
								Map<String, String> type = (Map<String, String>) iterator.next();
								value.append("<option value='" + type.get("ENTITYTYPE")
										+ "'>" + type.get("NAME") + "</option>");
							}
							value.append("</select>");
							buf.append(value);
						}else{
							if("DATE".equals(ff.get(j).getInputtype())){
								buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-datebox' data-options='required:"+ff.get(j).getIsrequired()+",validType:[\"date\"]' id='entity_"+ff.get(j).getEnname()+"' name='"+ff.get(j).getEnname()+"' value='"+StringUtils.filterNull(formValue.get(ff.get(j).getEnname()))+"'>");	
							}else{
								buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-validatebox' data-options='required:"+ff.get(j).getIsrequired()+"' id='entity_"+ff.get(j).getEnname()+"' name='"+ff.get(j).getEnname()+"' value='"+StringUtils.filterNull(formValue.get(ff.get(j).getEnname()))+"'>");
							}	
						}
						
							
					}else{ //新增
						//Select下拉框
						if("2".equals(ff.get(j).getLabeltype())){
							StringBuffer value = new StringBuffer();
							value.append("<select id='entity_"+ff.get(j).getEnname()+"' name='"+ff.get(j).getEnname()+"' val='' class='easyui-validatebox' data-options='required:"+ff.get(j).getIsrequired()+"'  style='width: "+fieldLen+"'>");
							value.append("<option value=''>请选择</option>");
							
							List<Map<String, Object>> constlist = null;
							constlist = FarmManager.instance().findDicTitleForIndeHasSort(ff.get(j).getConstant());
							for (Iterator iterator = constlist.iterator(); iterator.hasNext();) {
								Map<String, String> type = (Map<String, String>) iterator.next();
								value.append("<option value='" + type.get("ENTITYTYPE")
										+ "'>" + type.get("NAME") + "</option>");
							}
							value.append("</select>");
							buf.append(value);
						}else{
							if("DATE".equals(ff.get(j).getInputtype())){
								buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-datebox' data-options='required:"+ff.get(j).getIsrequired()+",validType:[\"date\"]' id='entity_"+ff.get(j).getEnname()+"' name='"+ff.get(j).getEnname()+"' value=''>");	
							}else{
								// 字段验证信息 "\"integer\",\"maxLength[5]\""
								String vt = ff.get(j).getValidaInfo();
								StringBuffer validas = new StringBuffer();
								if(vt!=null){
									String validaType[]  = vt.split(",");
									for(int k=0;k<validaType.length;k++){
										validas.append("\"").append(validaType[k]).append("\"").append(",");
									}
								}
								buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-validatebox' data-options='required:"+ff.get(j).getIsrequired()+",validType:["+validas+"]' id='entity_"+ff.get(j).getEnname()+"' name='"+ff.get(j).getEnname()+"' value=''>");
							}
						}
					}
					
					buf.append("</td>");
					rows.append(buf);
				}
			}
			if(ff.size()==1){
				StringBuffer buf = new StringBuffer();
				buf.append("<td class='title'>");
				buf.append(ff.get(0).getCnname());
				buf.append("</td>");
				buf.append("<td colspan='3'>");
				if(!"".equals(keyId) && keyId!=null){ //修改或查看
					
					//Select下拉框
					if("2".equals(ff.get(0).getLabeltype())){
						StringBuffer value = new StringBuffer();
						value.append("<select id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' val='"+StringUtils.filterNull(formValue.get(ff.get(0).getEnname()))+"' class='easyui-validatebox' data-options='required:"+ff.get(0).getIsrequired()+"'  style='width: "+fieldLen+"'>");
						value.append("<option value=''>请选择</option>");
						
						List<Map<String, Object>> constlist = null;
						constlist = FarmManager.instance().findDicTitleForIndeHasSort(ff.get(0).getConstant());
						for (Iterator iterator = constlist.iterator(); iterator.hasNext();) {
							Map<String, String> type = (Map<String, String>) iterator.next();
							value.append("<option value='" + type.get("ENTITYTYPE")
									+ "'>" + type.get("NAME") + "</option>");
						}
						value.append("</select>");
						buf.append(value);
					}else if("3".equals(ff.get(0).getLabeltype())){  //TextArea
						// 字段验证信息 "\"integer\",\"maxLength[5]\""
						String vt = ff.get(0).getValidaInfo();
						StringBuffer validas = new StringBuffer();
						if(vt!=null){
							String validaType[]  = vt.split(",");
							for(int k=0;k<validaType.length;k++){
								validas.append("\"").append(validaType[k]).append("\"").append(",");
							}
						}
						buf.append("<textarea rows='2' style='width: "+areaLen+";' class='easyui-validatebox' data-options='required:"+ff.get(0).getIsrequired()+",validType:["+validas+"]' id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' value='"+StringUtils.filterNull(formValue.get(ff.get(0).getEnname()))+"'>"+StringUtils.filterNull(formValue.get(ff.get(0).getEnname()))+"</textarea>");
					}else{
						if("DATE".equals(ff.get(0).getInputtype())){
							buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-datebox' data-options='required:"+ff.get(0).getIsrequired()+",validType:[\"date\"]' id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' value='"+StringUtils.filterNull(formValue.get(ff.get(0).getEnname()))+"'>");	
						}else{
							buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-validatebox' data-options='required:"+ff.get(0).getIsrequired()+"' id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' value='"+StringUtils.filterNull(formValue.get(ff.get(0).getEnname()))+"'>");
						}
					}
					
				}else{ //新增
					//Select下拉框
					if("2".equals(ff.get(0).getLabeltype())){
						StringBuffer value = new StringBuffer();
						value.append("<select id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' val='' class='easyui-validatebox' data-options='required:"+ff.get(0).getIsrequired()+"'  style='width: "+fieldLen+"'>");
						value.append("<option value=''>请选择</option>");
						
						List<Map<String, Object>> constlist = null;
						constlist = FarmManager.instance().findDicTitleForIndeHasSort(ff.get(0).getConstant());
						for (Iterator iterator = constlist.iterator(); iterator.hasNext();) {
							Map<String, String> type = (Map<String, String>) iterator.next();
							value.append("<option value='" + type.get("ENTITYTYPE")
									+ "'>" + type.get("NAME") + "</option>");
						}
						value.append("</select>");
						buf.append(value);
					}else if("3".equals(ff.get(0).getLabeltype())){
						// 字段验证信息 "\"integer\",\"maxLength[5]\""
						String vt = ff.get(0).getValidaInfo();
						StringBuffer validas = new StringBuffer();
						if(vt!=null){
							String validaType[]  = vt.split(",");
							for(int k=0;k<validaType.length;k++){
								validas.append("\"").append(validaType[k]).append("\"").append(",");
							}
						}
						buf.append("<textarea rows='2' style='width: "+areaLen+";' class='easyui-validatebox' data-options='required:"+ff.get(0).getIsrequired()+",validType:["+validas+"]' id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' value=''></textarea>");
					}
					else{
						if("DATE".equals(ff.get(0).getInputtype())){
							buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-datebox' data-options='required:"+ff.get(0).getIsrequired()+",validType:[\"date\"]' id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' value=''>");	
						}else{
							// 字段验证信息 "\"integer\",\"maxLength[5]\""
							String vt = ff.get(0).getValidaInfo();
							StringBuffer validas = new StringBuffer();
							if(vt!=null){
								String validaType[]  = vt.split(",");
								for(int k=0;k<validaType.length;k++){
									validas.append("\"").append(validaType[k]).append("\"").append(",");
								}
							}
							buf.append("<input type='text' style='width: "+fieldLen+";' class='easyui-validatebox' data-options='required:"+ff.get(0).getIsrequired()+",validType:["+validas+"]' id='entity_"+ff.get(0).getEnname()+"' name='"+ff.get(0).getEnname()+"' value=''>");
						}
					}
				}
				
				buf.append("</td>");
				rows.append(buf);
			}
			rows.append("</tr>");
			rowAll.append(rows);
			
		}
		System.out.println(rowAll);
		try {
			jspw.println(rowAll.toString());
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
		boolean flag = false;
		int count = ary.size() % subSize == 0 ? ary.size() / subSize : ary.size() / subSize + 1;
		List<List<FrmField>> subAryList = new ArrayList<List<FrmField>>();
		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<FrmField> list = new ArrayList<FrmField>();
			List<FrmField> list2 = new ArrayList<FrmField>();
			int j = 0;
			while (j < subSize && index < ary.size()) {
				FrmField fd = (FrmField)ary.get(index++);
				list.add(fd);
				j++;
				if(isTextAreaSelf(fd)){  //先判断自己是否area
					flag = true;
					break;
				}else{
					if(isTextArea(ary, index)){
						flag = true;
					}else{
						if(index < ary.size()){
							list.add((FrmField)ary.get(index));
						}
						flag = false;
					}
					break;
				}
				
			}
			if(flag){
				if(index<ary.size()){
					list2.add((FrmField)ary.get(index));	
				}
				
			}
			
			subAryList.add(list);
			if(list2.size()!=0){
				subAryList.add(list2);
			}
			
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
	 * 判断下一项是否TextArea
	 * @param ary
	 * @param point
	 * @return
	 */
	public boolean isTextArea(List<FrmField> ary,int point){
		if(point<ary.size()){
			FrmField ff = ary.get(point);
			if("3".equals(ff.getLabeltype())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断自身是否TextArea
	 * @param ff
	 * @return
	 */
	public boolean isTextAreaSelf(FrmField ff){
		if("3".equals(ff.getLabeltype())){
			return true;
		}
		return false;
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

	private final static FrmFieldManagerInter aloneFieldIMP = (FrmFieldManagerInter) BeanFactory
		.getBean("FRM_FIELDProxyId");
	
	private final static FrmTableManagerInter aloneTableIMP = (FrmTableManagerInter) BeanFactory
		.getBean("FrmTableProxyId");
	
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

}
