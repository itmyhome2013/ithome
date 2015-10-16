package com.farm.doc.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.commons.DocumentConfig;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.web.ParameterUtils;
import com.farm.web.action.WebSupport;
import com.farm.web.spring.BeanFactory;

/**
 * 文件
 * 
 * @author autoCode
 * 
 */
public class ActionFarmDocfileQuery extends WebSupport {
	private DataResult result;// 结果集合
	private DataQuery query;// 条件查询
	private FarmDocfile entity;// 实体封装
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合

	private File imgFile;
	private String imgFileFileName;
	private String fileId;
	
	private String filePath;      //文件路径 
	private InputStream inputStream;  
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			DataQuery dbQuery = DataQuery.init(query, "farm_docfile",
					"id,DIR,TYPE,NAME,EXNAME,LEN,FILENAME,PSTATE,PCONTENT");
			result = dbQuery.search();
			result.runDictionary("1:使用,0:临时", "PSTATE");
			result.runDictionary("1:图片,2:其他", "TYPE");
			List<Map<String, Object>> list = result.getResultList();
			for(Map<String,Object> map:list){
				map.put("DIR", DocumentConfig.getString("config.doc.dir").replace("/", "\\") + map.get("DIR"));
				DecimalFormat decimalFormat=new DecimalFormat("0.00");
				map.put("LEN", decimalFormat.format( Float.parseFloat(map.get("LEN").toString())/1024) + " KB" + " / " + decimalFormat.format( Float.parseFloat(map.get("LEN").toString())/1048576)+" MB");
			}
		} catch (Exception e) {
			result = DataResults.setException(result, e);
		}
		return SUCCESS;
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	public String editSubmit() {
		try {
			pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.UPDATE,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 文件上传
	 * @return
	 */
	public String fileUpload(){
		try {
			PrintWriter out = getResponse().getWriter();
			fileId = aloneIMP.saveFile(imgFile, entity.getType(),imgFileFileName, getCurrentUser());
			aloneIMP.submitFile(fileId);
			pageset = new PageSet(PageType.ADD,CommitType.TRUE);
			int error = 0;
			String url = aloneIMP.getFileURL(fileId);
			String message = "";
			out.println("{id:'"+fileId+"',error:'"+error+"',url:'"+url+"',message:'"+message+"'}");
			out.close();
		} catch (Exception e) {
			pageset=PageSets.initPageSet(pageset, PageType.ADD, CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	
	 /** 
     * 图片下载 
     * @return 
     */  
    public String download() {  
    	
    	FarmDocfile farmDocfile = aloneIMP.getFarmDocfile(ids);
    	
		 //String path = filePath;  
        String path = DocumentConfig.getString("config.doc.dir")+farmDocfile.getDir()+farmDocfile.getName();  
        HttpServletResponse response = ServletActionContext.getResponse();  
        try {  
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            // 取得文件的后缀名。  
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();  
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            //清空response  
            response.reset();  
            //设置response的Header  
            String filenameString = new String(filename.getBytes("gbk"),"iso-8859-1");  
            response.addHeader("Content-Disposition", "attachment;filename=" + filenameString);  
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
            response.setContentType("application/octet-stream");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    	
        return null;  
    }  
    
    /** 
     * 用于图片页面显示 
     *  
     * @return 
     */  
    public String readImg() {  
        try {  
        	
        	FarmDocfile farmDocfile = aloneIMP.getFarmDocfile(ids);
        	
            String path = DocumentConfig.getString("config.doc.dir")+farmDocfile.getDir()+farmDocfile.getName();  
        	
            inputStream = new FileInputStream(new File(path));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  
        return SUCCESS;  
    }  
	
	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		
		try {
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.DEL,
					CommitType.FALSE, e);
		}
		
		return SUCCESS;
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	public String delSubmit() {
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				aloneIMP.delFile(id, getCurrentUser());
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.DEL,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}

	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forSend() {
		return SUCCESS;
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				return SUCCESS;
			}
			case (0): {// 展示
				return SUCCESS;
			}
			case (2): {// 修改
				return SUCCESS;
			}
			default:
				break;
			}
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.OTHER,
					CommitType.FALSE, e);
		}
		return SUCCESS;
	}
	private final static FarmDocManagerInter aloneIMP = (FarmDocManagerInter) BeanFactory
	.getBean("farm_docProxyId");
	// ----------------------------------------------------------------------------------
	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public FarmDocfile getEntity() {
		return entity;
	}

	public void setEntity(FarmDocfile entity) {
		this.entity = entity;
	}

	public PageSet getPageset() {
		return pageset;
	}

	public void setPageset(PageSet pageset) {
		this.pageset = pageset;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getFileId() {
		return fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	private static final Logger log = Logger
			.getLogger(ActionFarmDocfileQuery.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 加载树节点 public String loadTreeNode() { treeNodes =
	 * EasyUiTreeNode.formatAjaxTree(EasyUiTreeNode .queryTreeNodeOne(id,
	 * "SORT", "alone_menu", "ID", "PARENTID", "NAME").getResultList(),
	 * EasyUiTreeNode .queryTreeNodeTow(id, "SORT", "alone_menu", "ID",
	 * "PARENTID", "NAME").getResultList(), "PARENTID", "ID", "NAME"); return
	 * SUCCESS; }
	 * 
	 * @return
	 */
}
