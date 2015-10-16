package com.ithome.popu.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PopuHuInfo entity. @author MyEclipse Persistence Tools
 */

public class PopuHuInfo implements java.io.Serializable {

	// Fields

	private BigDecimal huid; // 户编码
	private String huname; // 户主姓名
	private String huhouserelation; // 房主与户主的关系
	private BigDecimal huquale; // 户口性质
	private BigDecimal hutype; // 户口类别
	private BigDecimal hustate; // 户籍状态
	private String huaddress; // 户籍地址
	private BigDecimal hupeoplenum; // 户籍人数
	private String husignman; // 户籍签办人
	private BigDecimal hulowsafe; // 是低保家庭
	private Date husigndate; // 签发时间
	private String hufamilytel; // 家庭电话
	private BigDecimal hufamilypnum; // 家庭人数
	private BigDecimal hustructure; // 家庭结构
	private BigDecimal huhousequale; // 住房性质
	private String huunit; // 房主/产权单位
	private String huidentity; // 户主身份证
	private String hutel; // 户主电话
	private String humobile; // 户主手机
	private Double huarea; // 住房面积(M2)
	private Double huavgarea; // 人均面积(M2)
	private BigDecimal huouthousenum; // 区外住房(套)
	private BigDecimal huinnerhousenum; // 区内住房（套）
	private BigDecimal huavgincome; // 人均收入
	private BigDecimal hulonglive; // 是否常住
	private BigDecimal hustreet; //街道
	private BigDecimal huvillage; //小区
	private String hufloor; //楼层
	
	private String hufloortitle;
	
	private String hucell; //单元 
	private String huroomno; //房间号
	private BigDecimal hucommunity; //社区/村
	private String hucurraddress; //居住地址
	private BigDecimal createuserid; //创建人
	private BigDecimal updateuserid; //更新人
	private Date hucreatedate; //创建时间
	private Date huupdatedate; //更新时间
	private String hunote;  //备注
	
	private String livesaddress;    //住址  ：树
	private BigDecimal hulowincome;
	private BigDecimal ishappinessfamily;
	private BigDecimal ismodelfamily;
	private BigDecimal isexamplefamily;
	private BigDecimal humanagerkind;  //管理类型
	private Date huindate;  //流入时间
	private Date huoutdate; //流出时间
	private BigDecimal hufloatingpopulation;  //户籍非户籍
	private String hucardtype;  //证件类型
	
	private String husex;
	private String huchuzuis;
	private Date hubookdate;
	private String hucodetype;
	private Date starrenttdate;
	private Date endrentdate;
	private String hupstate;
	
	public BigDecimal getHuid() {
		return huid;
	}
	public void setHuid(BigDecimal huid) {
		this.huid = huid;
	}
	public String getHuname() {
		return huname;
	}
	public void setHuname(String huname) {
		this.huname = huname;
	}
	public String getHuhouserelation() {
		return huhouserelation;
	}
	public void setHuhouserelation(String huhouserelation) {
		this.huhouserelation = huhouserelation;
	}
	public BigDecimal getHuquale() {
		return huquale;
	}
	public void setHuquale(BigDecimal huquale) {
		this.huquale = huquale;
	}
	public BigDecimal getHutype() {
		return hutype;
	}
	public void setHutype(BigDecimal hutype) {
		this.hutype = hutype;
	}
	public BigDecimal getHustate() {
		return hustate;
	}
	public void setHustate(BigDecimal hustate) {
		this.hustate = hustate;
	}
	public String getHuaddress() {
		return huaddress;
	}
	public void setHuaddress(String huaddress) {
		this.huaddress = huaddress;
	}
	public BigDecimal getHupeoplenum() {
		return hupeoplenum;
	}
	public void setHupeoplenum(BigDecimal hupeoplenum) {
		this.hupeoplenum = hupeoplenum;
	}
	public String getHusignman() {
		return husignman;
	}
	public void setHusignman(String husignman) {
		this.husignman = husignman;
	}
	public BigDecimal getHulowsafe() {
		return hulowsafe;
	}
	public void setHulowsafe(BigDecimal hulowsafe) {
		this.hulowsafe = hulowsafe;
	}
	public Date getHusigndate() {
		return husigndate;
	}
	public void setHusigndate(Date husigndate) {
		this.husigndate = husigndate;
	}
	public String getHufamilytel() {
		return hufamilytel;
	}
	public void setHufamilytel(String hufamilytel) {
		this.hufamilytel = hufamilytel;
	}
	public BigDecimal getHufamilypnum() {
		return hufamilypnum;
	}
	public void setHufamilypnum(BigDecimal hufamilypnum) {
		this.hufamilypnum = hufamilypnum;
	}
	public BigDecimal getHustructure() {
		return hustructure;
	}
	public void setHustructure(BigDecimal hustructure) {
		this.hustructure = hustructure;
	}
	public BigDecimal getHuhousequale() {
		return huhousequale;
	}
	public void setHuhousequale(BigDecimal huhousequale) {
		this.huhousequale = huhousequale;
	}
	public String getHuunit() {
		return huunit;
	}
	public void setHuunit(String huunit) {
		this.huunit = huunit;
	}
	public String getHuidentity() {
		return huidentity;
	}
	public void setHuidentity(String huidentity) {
		this.huidentity = huidentity;
	}
	public String getHutel() {
		return hutel;
	}
	public void setHutel(String hutel) {
		this.hutel = hutel;
	}
	public String getHumobile() {
		return humobile;
	}
	public void setHumobile(String humobile) {
		this.humobile = humobile;
	}
	public Double getHuarea() {
		return huarea;
	}
	public void setHuarea(Double huarea) {
		this.huarea = huarea;
	}
	public Double getHuavgarea() {
		return huavgarea;
	}
	public void setHuavgarea(Double huavgarea) {
		this.huavgarea = huavgarea;
	}
	public BigDecimal getHuouthousenum() {
		return huouthousenum;
	}
	public void setHuouthousenum(BigDecimal huouthousenum) {
		this.huouthousenum = huouthousenum;
	}
	public BigDecimal getHuinnerhousenum() {
		return huinnerhousenum;
	}
	public void setHuinnerhousenum(BigDecimal huinnerhousenum) {
		this.huinnerhousenum = huinnerhousenum;
	}
	public BigDecimal getHuavgincome() {
		return huavgincome;
	}
	public void setHuavgincome(BigDecimal huavgincome) {
		this.huavgincome = huavgincome;
	}
	public BigDecimal getHulonglive() {
		return hulonglive;
	}
	public void setHulonglive(BigDecimal hulonglive) {
		this.hulonglive = hulonglive;
	}
	public BigDecimal getHustreet() {
		return hustreet;
	}
	public void setHustreet(BigDecimal hustreet) {
		this.hustreet = hustreet;
	}
	public BigDecimal getHuvillage() {
		return huvillage;
	}
	public void setHuvillage(BigDecimal huvillage) {
		this.huvillage = huvillage;
	}
	
	public String getHufloor() {
		return hufloor;
	}
	public void setHufloor(String hufloor) {
		this.hufloor = hufloor;
	}
	public String getHucell() {
		return hucell;
	}
	public void setHucell(String hucell) {
		this.hucell = hucell;
	}
	public String getHuroomno() {
		return huroomno;
	}
	public void setHuroomno(String huroomno) {
		this.huroomno = huroomno;
	}
	public BigDecimal getHucommunity() {
		return hucommunity;
	}
	public void setHucommunity(BigDecimal hucommunity) {
		this.hucommunity = hucommunity;
	}
	public String getHucurraddress() {
		return hucurraddress;
	}
	public void setHucurraddress(String hucurraddress) {
		this.hucurraddress = hucurraddress;
	}
	public BigDecimal getCreateuserid() {
		return createuserid;
	}
	public void setCreateuserid(BigDecimal createuserid) {
		this.createuserid = createuserid;
	}
	public BigDecimal getUpdateuserid() {
		return updateuserid;
	}
	public void setUpdateuserid(BigDecimal updateuserid) {
		this.updateuserid = updateuserid;
	}
	public Date getHucreatedate() {
		return hucreatedate;
	}
	public void setHucreatedate(Date hucreatedate) {
		this.hucreatedate = hucreatedate;
	}
	public Date getHuupdatedate() {
		return huupdatedate;
	}
	public void setHuupdatedate(Date huupdatedate) {
		this.huupdatedate = huupdatedate;
	}
	public String getHunote() {
		return hunote;
	}
	public void setHunote(String hunote) {
		this.hunote = hunote;
	}
	public String getLivesaddress() {
		return livesaddress;
	}
	public void setLivesaddress(String livesaddress) {
		this.livesaddress = livesaddress;
	}
	public BigDecimal getHulowincome() {
		return hulowincome;
	}
	public void setHulowincome(BigDecimal hulowincome) {
		this.hulowincome = hulowincome;
	}
	public BigDecimal getIshappinessfamily() {
		return ishappinessfamily;
	}
	public void setIshappinessfamily(BigDecimal ishappinessfamily) {
		this.ishappinessfamily = ishappinessfamily;
	}
	public BigDecimal getIsmodelfamily() {
		return ismodelfamily;
	}
	public void setIsmodelfamily(BigDecimal ismodelfamily) {
		this.ismodelfamily = ismodelfamily;
	}
	public BigDecimal getIsexamplefamily() {
		return isexamplefamily;
	}
	public void setIsexamplefamily(BigDecimal isexamplefamily) {
		this.isexamplefamily = isexamplefamily;
	}
	public BigDecimal getHumanagerkind() {
		return humanagerkind;
	}
	public void setHumanagerkind(BigDecimal humanagerkind) {
		this.humanagerkind = humanagerkind;
	}
	public Date getHuindate() {
		return huindate;
	}
	public void setHuindate(Date huindate) {
		this.huindate = huindate;
	}
	public Date getHuoutdate() {
		return huoutdate;
	}
	public void setHuoutdate(Date huoutdate) {
		this.huoutdate = huoutdate;
	}
	public BigDecimal getHufloatingpopulation() {
		return hufloatingpopulation;
	}
	public void setHufloatingpopulation(BigDecimal hufloatingpopulation) {
		this.hufloatingpopulation = hufloatingpopulation;
	}
	public String getHucardtype() {
		return hucardtype;
	}
	public void setHucardtype(String hucardtype) {
		this.hucardtype = hucardtype;
	}
	public String getHusex() {
		return husex;
	}
	public void setHusex(String husex) {
		this.husex = husex;
	}
	public String getHuchuzuis() {
		return huchuzuis;
	}
	public void setHuchuzuis(String huchuzuis) {
		this.huchuzuis = huchuzuis;
	}
	public Date getHubookdate() {
		return hubookdate;
	}
	public void setHubookdate(Date hubookdate) {
		this.hubookdate = hubookdate;
	}
	public String getHucodetype() {
		return hucodetype;
	}
	public void setHucodetype(String hucodetype) {
		this.hucodetype = hucodetype;
	}
	public Date getStarrenttdate() {
		return starrenttdate;
	}
	public void setStarrenttdate(Date starrenttdate) {
		this.starrenttdate = starrenttdate;
	}
	public Date getEndrentdate() {
		return endrentdate;
	}
	public void setEndrentdate(Date endrentdate) {
		this.endrentdate = endrentdate;
	}
	public String getHupstate() {
		return hupstate;
	}
	public void setHupstate(String hupstate) {
		this.hupstate = hupstate;
	}
	public String getHufloortitle() {
		return hufloortitle;
	}
	public void setHufloortitle(String hufloortitle) {
		this.hufloortitle = hufloortitle;
	}
	
	

	// Constructors

	// Property accessors

	

}