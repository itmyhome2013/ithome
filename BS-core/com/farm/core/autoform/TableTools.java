package com.farm.core.autoform;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.ithome.autoform.domain.CompletedForm;
import com.ithome.autoform.domain.FrmField;

import com.farm.console.FarmManager;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;

/**
* 表单操作公共类
*/
public class TableTools {

	/**
	 * 创建表
	 * 
	 * @param tableName
	 */
	public static void generateTable(String tableName) {
		Statement st = null;
		st = DBUtil.getStatement();
		//默认id、taskid、bussnessid字段
		String createTableSql = "CREATE TABLE " + tableName
				+ " ( id varchar2(50), taskid varchar2(50), bussnessid varchar2(50))";
		try {
			st.executeUpdate(createTableSql);
		} catch (SQLException e) {
			if (e.getMessage().indexOf("ORA-00903") != -1) {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>表名无效<div>");
			} else if (e.getMessage().indexOf("ORA-00955") != -1) {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>该表单已存在<div>");
			} else {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>请检查表单英文名称<div>");
			}
		} finally {
			DBUtil.closeStatement(st);
		}
	}

	/**
	 * 删除表
	 * @param tableName
	 */
	public static void delTable(String tableName){
		Statement st = null;
		st = DBUtil.getStatement();
		//String delTableSql = "DROP TABLE "+tableName+"";
		//删除表之前判断是否存在
		String delTableSql = "DECLARE num NUMBER; BEGIN SELECT COUNT(1) INTO num FROM USER_TABLES  WHERE TABLE_NAME = UPPER('"+tableName+"'); IF num > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE "+tableName+"';END IF;END;";
		try {
			st.executeUpdate(delTableSql);
		} catch (SQLException e) {
			throw new RuntimeException("<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
		} finally {
			DBUtil.closeStatement(st);
		}
	}
	/**
	 * 更改表名
	 * 
	 * @param oldName
	 * @param newName
	 */
	public static void renameTable(String oldName, String newName) {
		Statement st = null;
		st = DBUtil.getStatement();
		String renameTableSql = "alter table " + oldName + " rename to "
				+ newName + "";
		try {
			if(!newName.equals(oldName)){
				st.executeUpdate(renameTableSql);	
			}
		} catch (SQLException e) {
			if (e.getMessage().indexOf("ORA-00942") != -1) {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>该表单已不存在<div>");
			} else {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>请检查表单英文名称<div>");
			}
		} finally {
			DBUtil.closeStatement(st);
		}
	}

	/**
	 * 添加字段
	 * alter table frm_field add note varchar2(50); 
	 * 表名、字段名、字段类型、字段长度、是否为空
	 */
	public static void addField(FieldModel fieldModel) {
		// alter table frm_field add note varchar2(50);
		StringBuffer sql = new StringBuffer();
		String fieldLen = ""; //如果字段长度为空 则默认100
		if("".equals(fieldModel.getFieldLen()) || fieldModel.getFieldLen()==null){
			fieldLen = "100";
		}else{
			fieldLen = fieldModel.getFieldLen();
		}
		//日期类型 没有长度
		if("DATE".equals(fieldModel.getFieldType())){
			sql.append("alter table " + fieldModel.getTableName() + " add "
					+ fieldModel.getFieldName() + " "
					+ fieldModel.getFieldType());
		}else{
			sql.append("alter table " + fieldModel.getTableName() + " add "
					+ fieldModel.getFieldName() + " "
					+ fieldModel.getFieldType() + "("
					+ fieldLen + ")");
		}
		if("0".equals(fieldModel.getIsNull())){
			sql.append(" not null");
		}
		PreparedStatement pst = null;
		try {
			pst = DBUtil.getConnection().prepareStatement(sql.toString());
			pst.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			
			if (e1.getMessage().indexOf("ORA-01430") != -1) {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>表中已存在要添加的列<div>");
			}else{
				throw new RuntimeException(e1.getMessage());	
			}
			
		} finally {
			DBUtil.closeStatement(pst);
		}
	}
	
	public static void updateField(String tableName,FrmField oldField,FrmField newField){
		Connection conn = DBUtil.getConnection();
		Statement pst = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		
		//修改字段名
		if(!oldField.getEnname().equals(newField.getEnname())){
			sql1 = "alter table "+tableName+" rename column "+oldField.getEnname()+" to "+newField.getEnname()+"";
		}
		
		if(oldField.getLen() == null){
			oldField.setLen("");
		}
		
		if(newField.getLen() == null){
			newField.setLen("");
		}
		//修改字段类型
		if(!oldField.getFiledtype().equals(newField.getFiledtype()) || !oldField.getLen().equals(newField.getLen())){
			String oldFieldType = DataResult.runDictionarySingle(FarmManager.instance().findDicTitleForIndex("FILEDTYPE"), oldField.getFiledtype());
			String newFieldType = DataResult.runDictionarySingle(FarmManager.instance().findDicTitleForIndex("FILEDTYPE"), newField.getFiledtype());
			if("DATE".equals(newFieldType)){
				 sql2 = "alter table "+tableName+" modify "+newField.getEnname()+" "+newFieldType+"";
			}else{
				 sql2 = "alter table "+tableName+" modify "+newField.getEnname()+" "+newFieldType+"(" +newField.getLen()+ ")";
			}
		}
		
		//修改字段是否可为空
		if("0".equals(newField.getIsnull())){
			//sql3 = "alter table "+tableName+" modify "+newField.getEnname()+"  not null";
			sql3 = "DECLARE num varchar2(100); BEGIN SELECT NULLABLE INTO num from cols where table_name = upper('"+tableName+"') and column_name = upper('"+newField.getEnname()+"'); IF num = 'Y' THEN execute immediate 'alter table "+tableName+" modify "+newField.getEnname()+" not null';  END IF; END;";
		}else {
			sql3 = "DECLARE num varchar2(100); BEGIN SELECT NULLABLE INTO num from cols  where table_name = upper('"+tableName+"') and column_name = upper('"+newField.getEnname()+"'); IF num = 'N' THEN execute immediate 'alter table "+tableName+" modify "+newField.getEnname()+" null'; END IF; END;";
		}
		try {
			
			pst = conn.createStatement();
			
			if(!"".equals(sql1)){
				pst.executeUpdate(sql1);
			}
			if(!"".equals(sql2)){
				pst.executeUpdate(sql2);				
			}
			if(!"".equals(sql3)){
				pst.executeUpdate(sql3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getMessage().indexOf("ORA-01439") != -1) {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>要更改数据类型, 则要修改的列必须为空<div>");
			}else if(e.getMessage().indexOf("ORA-02296") != -1){
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>该字段找到空值 无法修改为不允许为空<div>");
			}else{
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
			}
		} finally {
			DBUtil.closeStatement(pst);
			DBUtil.closeConnection(conn);
		}
		
	}
	
	/**
	 * 删除字段 
	 * @param tableName  表名
	 * @param fieldName  字段名 
	 */
	public static void delField(String tableName, String fieldName){
		//删除字段前先判断是否存在
		String sql = "DECLARE num NUMBER; BEGIN SELECT COUNT(1) INTO num  from cols " +
				"where table_name = upper('"+tableName+"')  and column_name = upper('"+fieldName+"'); " +
				"IF num > 0 THEN  execute immediate 'alter table "+tableName+" drop column "+fieldName+"'; END IF; END;";
		PreparedStatement pst = null;
		try {
			pst = DBUtil.getConnection().prepareStatement(sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getMessage().indexOf("ORA-00904") != -1) {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>表中无此字段<div>");
			} else {
				throw new RuntimeException(
						"<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
			}
		} finally {
			DBUtil.closeStatement(pst);
		}
	}
	
	/**
	 * 向表单中添加数据
	 * @param tableName  表名
	 * @param map 字段:值
	 */
	public static String insertFrmForm(String tableName,Map<String,String> map){
		String id = ""; //添加成功后返回主键ID
		
		StringBuffer sql = new StringBuffer("insert into "+tableName+"");
		StringBuffer columnName = new StringBuffer();
		StringBuffer columnValue = new StringBuffer();
		for(Map.Entry<String, String> entry : map.entrySet()){
			if(!"tableid".equals(entry.getKey())){
				//ID为传值
				if("id".equals(entry.getKey())){ 
					
					//如果ID为空 则生成随机数
					if(entry.getValue()==null || "".equals(entry.getValue())){
						columnName.append(entry.getKey()).append(",");
						columnValue.append("'").append(NumberTools.generateRandomArray(10)).append("'").append(",");	
					}else{ //否则ID有值 为URL传过来
						columnName.append(entry.getKey()).append(",");
						columnValue.append("'").append(entry.getValue()).append("'").append(",");
					}
					
				}else{
					columnName.append(entry.getKey()).append(",");
					
					// 日期格式  to_date('2016-01-01','yyyy-mm-dd')
					if(entry.getValue().matches("^\\d{4}-\\d{2}-\\d{2}$")){
						columnValue.append("to_date('").append(entry.getValue()).append("','yyyy-mm-dd')").append(",");
					}else{
						columnValue.append("'").append(entry.getValue()).append("'").append(",");	
					}
					
				}
				
			}
		}
		
		sql.append("(").append(columnName.substring(0,columnName.length()-1)).append(")")
		.append(" values (").append(columnValue.substring(0, columnValue.length()-1)).append(")");
		
		System.out.println("************ " + sql);
		
		PreparedStatement pst = null;
		ResultSet rs = null;  
		try {
			String generatedColumns[] = { "ID" };  //获得指定ID  
			pst = DBUtil.getConnection().prepareStatement(sql.toString(),generatedColumns);
			pst.executeUpdate();
			// 检索对象生成的键  
            rs = pst.getGeneratedKeys();  
            if (rs.next()) {  
            	id = rs.getString(1);
                System.out.println("数据主键：" + rs.getString(1));  
            }  
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
		} finally {
			DBUtil.closeStatement(pst);
		}
		return id;
	}
	
	/**
	 * 向已填业务表单中插入数据
	 * @param dataid 表单ID
	 * @param form 业务实体
	 */
	public static void insertCompletedForm(String dataid,CompletedForm form){
		
		String sql = "insert into ACT_EX_COMPLETEDFORM(completedformid,processid,pcsfromcfgid,informant,infordate,dataid,fromtablename,lastupdatedate) " +
				"values(?,?,?,?,?,?,?,?)";
		
		PreparedStatement pst = null;
		try {
			pst = DBUtil.getConnection().prepareStatement(sql.toString());
			pst.setString(1, String.valueOf(NumberTools.generateRandomArray(10)));
			pst.setString(2, form.getProcessid());
			pst.setString(3, form.getPcsfromcfgid());
			pst.setString(4, form.getInformant());
			pst.setString(5, TimeTool.getFormatTimeDate14());
			pst.setString(6, dataid);
			pst.setString(7, form.getFromtablename());
			pst.setString(8, "");
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
		} finally {
			DBUtil.closeStatement(pst);
		}
	}
	
	/**
	 * 更新已填表单数据(更新日期)
	 * @param completedformid  主键
	 */
	public static void updateCompletedForm(String completedformid){
		
		String sql = "update ACT_EX_COMPLETEDFORM set lastupdatedate = ? where completedformid = ?";
		
		PreparedStatement pst = null;
		try {
			pst = DBUtil.getConnection().prepareStatement(sql.toString());
			pst.setString(1, TimeTool.getFormatTimeDate14());
			pst.setString(2, completedformid);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
		} finally {
			DBUtil.closeStatement(pst);
		}
	}
	
	/**
	 * 向表单中修改数据
	 * @param tableName  表名
	 * @param map 字段:值
	 */
	public static void editFrmForm(String tableName,Map<String,String> map){
		String id = "";
		StringBuffer sql = new StringBuffer("update "+tableName+" set ");
		StringBuffer setSql = new StringBuffer();
		for(Map.Entry<String, String> entry : map.entrySet()){
			if(!"tableid".equals(entry.getKey())){
				if("id".equals(entry.getKey())){  //ID为主键 
					id = "'" + entry.getValue() + "'";
				}else{
					// 日期格式  to_date('2016-01-01','yyyy-mm-dd')
					if(entry.getValue().matches("^\\d{4}-\\d{2}-\\d{2}$")){
						setSql.append(entry.getKey() + " = " + "to_date('").append(entry.getValue()).append("','yyyy-mm-dd')").append(",");
					}else{
						setSql.append(entry.getKey() + " = " + "'" + entry.getValue() + "'").append(",");
					}
				}
				
			}
		}
		
		sql.append(setSql.substring(0, setSql.length()-1) + " where id = "+id+"");
		System.out.println("************ " + sql);
		
		PreparedStatement pst = null;
		try {
			pst = DBUtil.getConnection().prepareStatement(sql.toString());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
		} finally {
			DBUtil.closeStatement(pst);
		}
		
	}
	
	/**
	 * 删除操作表单数据
	 * @param tableName  表名
	 * @param id 主键ID
	 */
	public static void delFrmForm(String tableName,String id){
		StringBuffer sql = new StringBuffer("delete from "+tableName);
		
		sql.append(" where id = "+id+"");
		System.out.println("************ " + sql);
		PreparedStatement pst = null;
		try {
			pst = DBUtil.getConnection().prepareStatement(sql.toString());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("<div style='color:red;font-weight:bold;'>"+e.getMessage()+"<div>");
		} finally {
			DBUtil.closeStatement(pst);
		}
		
	}

	public static void main(String[] args) {
		/*FieldModel model = new FieldModel("test", "huname", "varchar2", "30");
		addField(model);*/
	}
}
