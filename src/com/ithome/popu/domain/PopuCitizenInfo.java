package com.ithome.popu.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PopuCitizenInfo entity. @author MyEclipse Persistence Tools
 */

public class PopuCitizenInfo implements java.io.Serializable {

	// Fields

	private BigDecimal citizenid; // 居民ID
	private BigDecimal huid; // 户主姓名
	private String citizenname; // 居民姓名
	private BigDecimal citizenishouseower; // 是否房主(114是,420否)
	private BigDecimal citizenhurelation; // 与户主关系
	private String citizenoldname; // 曾用名
	private BigDecimal citizensex; // 性别
	private BigDecimal citizennation; // 民族
	private Date citizenbirth; // 出生日期
	private String citizenbirthaddress; // 出生地址
	private BigDecimal citizencardtype; // 证件类型
	private BigDecimal citizenhuquality; // 户口性质
	private String citizenidentity; // 证件编号
	private String citizenhuaddress; // 户口省市县
	private String citizennativeaddress; // 户籍地址
	private BigDecimal citizenhustate; // 户籍状况
	private Date citizenindate; // 何时迁入
	private String cityzeninaddress; // 何地迁入
	private BigDecimal citizenwedstate; // 婚姻状况
	private String citizenmobile; // 手机号码
	private String citizenheight; // 身高(CM)
	private BigDecimal citizenbloodtype; // 血型
	private String citizenhealth; // 健康状况
	private BigDecimal citizenfaith; // 宗教信仰
	private BigDecimal citizenstatus; // 政治面貌
	private BigDecimal citizeneducation; // 文化程度
	private BigDecimal citizentype; // 人员类别
	private BigDecimal citizenjobstate; // 就业现状
	private String citizenjob; // 职业
	private String citizenjobunit; // 现服务处所
	private String citizenunitaddress; // 单位地址
	private String citizentel; // 单位电话
	private String citizenservice; // 兵役状况
	private String citizenserviceplace; // 兵役服务处所
	private BigDecimal citizencare; // 是否优抚对象
	private BigDecimal citizenlowsafe; // 是否低保
	private String citizenspeciality; // 有何特长
	private BigDecimal citizenlonglive; // 是否常住
	private BigDecimal citizenstreet; // 街道/镇
	private BigDecimal citizenvillage; // 小区(组/队)
	private String citizenfloor; // 楼号
	
	private String citizenfloortitle;
	
	private String citizencell; // 单元
	private String citizenroomno; // 门牌号
	private BigDecimal citizencommunity; // 社区/村
	private BigDecimal createuserid; // 创建人
	private BigDecimal updateuserid; // 更新人
	private Date createdate; // 创建日期
	private Date updatedate; // 更新日期
	private String citizennote; // 备注
	private String isYuling;
	private String isFlow;
	private String isCare;
	private String isDisable;
	private String isParty;
	private String isWed;
	private String isWeiwen;
	private String isDibao;
	private String isOld;
	private String isShiye;
	private BigDecimal citizenisoutman;
	private BigDecimal isrectify;// 矫正人员
	private BigDecimal ishelpeducate; // 帮教人员
	private BigDecimal citizenmanagerkind; // 管理类型
	private Date citizencomeindate; // 流入时间
	private Date citizenoutdate; // 流出时间
	private Integer citizenfloatingpopulation; // 户籍非户籍
	private String citizenliveaddress; // 居住地址
	private String birth;
	private String citizenstate;  //状态  0：删除
	private String citizenlivestate;
	private String citizenyibao;
	private String citizenzzww;
	private String isSfltx;
	private String citizenphone;
	private Date citizenbookdate;
	
	private String livesaddress;    //住址  ：树
	

	// Constructors

	/** default constructor */
	public PopuCitizenInfo() {
	}

	/** full constructor */
	public PopuCitizenInfo(BigDecimal huid, String citizenname,
			BigDecimal citizenishouseower, BigDecimal citizenhurelation,
			String citizenoldname, BigDecimal citizensex,
			BigDecimal citizennation, Date citizenbirth,
			String citizenbirthaddress, BigDecimal citizencardtype,
			BigDecimal citizenhuquality, String citizenidentity,
			String citizenhuaddress, String citizennativeaddress,
			BigDecimal citizenhustate, Date citizenindate,
			String cityzeninaddress, BigDecimal citizenwedstate,
			String citizenmobile, String citizenheight,
			BigDecimal citizenbloodtype, String citizenhealth,
			BigDecimal citizenfaith, BigDecimal citizenstatus,
			BigDecimal citizeneducation, BigDecimal citizentype,
			BigDecimal citizenjobstate, String citizenjob,
			String citizenjobunit, String citizenunitaddress,
			String citizentel, String citizenservice,
			String citizenserviceplace, BigDecimal citizencare,
			BigDecimal citizenlowsafe, String citizenspeciality,
			BigDecimal citizenlonglive, BigDecimal citizenstreet,
			BigDecimal citizenvillage, String citizenfloor,
			String citizencell, String citizenroomno,
			BigDecimal citizencommunity, BigDecimal createuserid,
			BigDecimal updateuserid, Date createdate, Date updatedate,
			String citizennote, String isYuling, String isFlow, String isCare,
			String isDisable, String isParty, String isWed, String isWeiwen,
			String isDibao, String isOld, String isShiye,
			BigDecimal citizenisoutman, BigDecimal isrectify,
			BigDecimal ishelpeducate, BigDecimal citizenmanagerkind,
			Date citizencomeindate, Date citizenoutdate,
			Integer citizenfloatingpopulation, String citizenliveaddress,
			String birth, String citizenstate, String citizenlivestate,
			String citizenyibao, String citizenzzww, String isSfltx,
			String citizenphone, Date citizenbookdate) {
		this.huid = huid;
		this.citizenname = citizenname;
		this.citizenishouseower = citizenishouseower;
		this.citizenhurelation = citizenhurelation;
		this.citizenoldname = citizenoldname;
		this.citizensex = citizensex;
		this.citizennation = citizennation;
		this.citizenbirth = citizenbirth;
		this.citizenbirthaddress = citizenbirthaddress;
		this.citizencardtype = citizencardtype;
		this.citizenhuquality = citizenhuquality;
		this.citizenidentity = citizenidentity;
		this.citizenhuaddress = citizenhuaddress;
		this.citizennativeaddress = citizennativeaddress;
		this.citizenhustate = citizenhustate;
		this.citizenindate = citizenindate;
		this.cityzeninaddress = cityzeninaddress;
		this.citizenwedstate = citizenwedstate;
		this.citizenmobile = citizenmobile;
		this.citizenheight = citizenheight;
		this.citizenbloodtype = citizenbloodtype;
		this.citizenhealth = citizenhealth;
		this.citizenfaith = citizenfaith;
		this.citizenstatus = citizenstatus;
		this.citizeneducation = citizeneducation;
		this.citizentype = citizentype;
		this.citizenjobstate = citizenjobstate;
		this.citizenjob = citizenjob;
		this.citizenjobunit = citizenjobunit;
		this.citizenunitaddress = citizenunitaddress;
		this.citizentel = citizentel;
		this.citizenservice = citizenservice;
		this.citizenserviceplace = citizenserviceplace;
		this.citizencare = citizencare;
		this.citizenlowsafe = citizenlowsafe;
		this.citizenspeciality = citizenspeciality;
		this.citizenlonglive = citizenlonglive;
		this.citizenstreet = citizenstreet;
		this.citizenvillage = citizenvillage;
		this.citizenfloor = citizenfloor;
		this.citizencell = citizencell;
		this.citizenroomno = citizenroomno;
		this.citizencommunity = citizencommunity;
		this.createuserid = createuserid;
		this.updateuserid = updateuserid;
		this.createdate = createdate;
		this.updatedate = updatedate;
		this.citizennote = citizennote;
		this.isYuling = isYuling;
		this.isFlow = isFlow;
		this.isCare = isCare;
		this.isDisable = isDisable;
		this.isParty = isParty;
		this.isWed = isWed;
		this.isWeiwen = isWeiwen;
		this.isDibao = isDibao;
		this.isOld = isOld;
		this.isShiye = isShiye;
		this.citizenisoutman = citizenisoutman;
		this.isrectify = isrectify;
		this.ishelpeducate = ishelpeducate;
		this.citizenmanagerkind = citizenmanagerkind;
		this.citizencomeindate = citizencomeindate;
		this.citizenoutdate = citizenoutdate;
		this.citizenfloatingpopulation = citizenfloatingpopulation;
		this.citizenliveaddress = citizenliveaddress;
		this.birth = birth;
		this.citizenstate = citizenstate;
		this.citizenlivestate = citizenlivestate;
		this.citizenyibao = citizenyibao;
		this.citizenzzww = citizenzzww;
		this.isSfltx = isSfltx;
		this.citizenphone = citizenphone;
		this.citizenbookdate = citizenbookdate;
	}

	// Property accessors

	public BigDecimal getCitizenid() {
		return this.citizenid;
	}

	public void setCitizenid(BigDecimal citizenid) {
		this.citizenid = citizenid;
	}

	public BigDecimal getHuid() {
		return this.huid;
	}

	public void setHuid(BigDecimal huid) {
		this.huid = huid;
	}

	public String getCitizenname() {
		return this.citizenname;
	}

	public void setCitizenname(String citizenname) {
		this.citizenname = citizenname;
	}

	public BigDecimal getCitizenishouseower() {
		return this.citizenishouseower;
	}

	public void setCitizenishouseower(BigDecimal citizenishouseower) {
		this.citizenishouseower = citizenishouseower;
	}

	public BigDecimal getCitizenhurelation() {
		return this.citizenhurelation;
	}

	public void setCitizenhurelation(BigDecimal citizenhurelation) {
		this.citizenhurelation = citizenhurelation;
	}

	public String getCitizenoldname() {
		return this.citizenoldname;
	}

	public void setCitizenoldname(String citizenoldname) {
		this.citizenoldname = citizenoldname;
	}

	public BigDecimal getCitizensex() {
		return this.citizensex;
	}

	public void setCitizensex(BigDecimal citizensex) {
		this.citizensex = citizensex;
	}

	public BigDecimal getCitizennation() {
		return this.citizennation;
	}

	public void setCitizennation(BigDecimal citizennation) {
		this.citizennation = citizennation;
	}

	public Date getCitizenbirth() {
		return this.citizenbirth;
	}

	public void setCitizenbirth(Date citizenbirth) {
		this.citizenbirth = citizenbirth;
	}

	public String getCitizenbirthaddress() {
		return this.citizenbirthaddress;
	}

	public void setCitizenbirthaddress(String citizenbirthaddress) {
		this.citizenbirthaddress = citizenbirthaddress;
	}

	public BigDecimal getCitizencardtype() {
		return this.citizencardtype;
	}

	public void setCitizencardtype(BigDecimal citizencardtype) {
		this.citizencardtype = citizencardtype;
	}

	public BigDecimal getCitizenhuquality() {
		return this.citizenhuquality;
	}

	public void setCitizenhuquality(BigDecimal citizenhuquality) {
		this.citizenhuquality = citizenhuquality;
	}

	public String getCitizenidentity() {
		return this.citizenidentity;
	}

	public void setCitizenidentity(String citizenidentity) {
		this.citizenidentity = citizenidentity;
	}

	public String getCitizenhuaddress() {
		return this.citizenhuaddress;
	}

	public void setCitizenhuaddress(String citizenhuaddress) {
		this.citizenhuaddress = citizenhuaddress;
	}

	public String getCitizennativeaddress() {
		return this.citizennativeaddress;
	}

	public void setCitizennativeaddress(String citizennativeaddress) {
		this.citizennativeaddress = citizennativeaddress;
	}

	public BigDecimal getCitizenhustate() {
		return this.citizenhustate;
	}

	public void setCitizenhustate(BigDecimal citizenhustate) {
		this.citizenhustate = citizenhustate;
	}

	public Date getCitizenindate() {
		return this.citizenindate;
	}

	public void setCitizenindate(Date citizenindate) {
		this.citizenindate = citizenindate;
	}

	public String getCityzeninaddress() {
		return this.cityzeninaddress;
	}

	public void setCityzeninaddress(String cityzeninaddress) {
		this.cityzeninaddress = cityzeninaddress;
	}

	public BigDecimal getCitizenwedstate() {
		return this.citizenwedstate;
	}

	public void setCitizenwedstate(BigDecimal citizenwedstate) {
		this.citizenwedstate = citizenwedstate;
	}

	public String getCitizenmobile() {
		return this.citizenmobile;
	}

	public void setCitizenmobile(String citizenmobile) {
		this.citizenmobile = citizenmobile;
	}

	public String getCitizenheight() {
		return this.citizenheight;
	}

	public void setCitizenheight(String citizenheight) {
		this.citizenheight = citizenheight;
	}

	public BigDecimal getCitizenbloodtype() {
		return this.citizenbloodtype;
	}

	public void setCitizenbloodtype(BigDecimal citizenbloodtype) {
		this.citizenbloodtype = citizenbloodtype;
	}

	public String getCitizenhealth() {
		return this.citizenhealth;
	}

	public void setCitizenhealth(String citizenhealth) {
		this.citizenhealth = citizenhealth;
	}

	public BigDecimal getCitizenfaith() {
		return this.citizenfaith;
	}

	public void setCitizenfaith(BigDecimal citizenfaith) {
		this.citizenfaith = citizenfaith;
	}

	public BigDecimal getCitizenstatus() {
		return this.citizenstatus;
	}

	public void setCitizenstatus(BigDecimal citizenstatus) {
		this.citizenstatus = citizenstatus;
	}

	public BigDecimal getCitizeneducation() {
		return this.citizeneducation;
	}

	public void setCitizeneducation(BigDecimal citizeneducation) {
		this.citizeneducation = citizeneducation;
	}

	public BigDecimal getCitizentype() {
		return this.citizentype;
	}

	public void setCitizentype(BigDecimal citizentype) {
		this.citizentype = citizentype;
	}

	public BigDecimal getCitizenjobstate() {
		return this.citizenjobstate;
	}

	public void setCitizenjobstate(BigDecimal citizenjobstate) {
		this.citizenjobstate = citizenjobstate;
	}

	public String getCitizenjob() {
		return this.citizenjob;
	}

	public void setCitizenjob(String citizenjob) {
		this.citizenjob = citizenjob;
	}

	public String getCitizenjobunit() {
		return this.citizenjobunit;
	}

	public void setCitizenjobunit(String citizenjobunit) {
		this.citizenjobunit = citizenjobunit;
	}

	public String getCitizenunitaddress() {
		return this.citizenunitaddress;
	}

	public void setCitizenunitaddress(String citizenunitaddress) {
		this.citizenunitaddress = citizenunitaddress;
	}

	public String getCitizentel() {
		return this.citizentel;
	}

	public void setCitizentel(String citizentel) {
		this.citizentel = citizentel;
	}

	public String getCitizenservice() {
		return this.citizenservice;
	}

	public void setCitizenservice(String citizenservice) {
		this.citizenservice = citizenservice;
	}

	public String getCitizenserviceplace() {
		return this.citizenserviceplace;
	}

	public void setCitizenserviceplace(String citizenserviceplace) {
		this.citizenserviceplace = citizenserviceplace;
	}

	public BigDecimal getCitizencare() {
		return this.citizencare;
	}

	public void setCitizencare(BigDecimal citizencare) {
		this.citizencare = citizencare;
	}

	public BigDecimal getCitizenlowsafe() {
		return this.citizenlowsafe;
	}

	public void setCitizenlowsafe(BigDecimal citizenlowsafe) {
		this.citizenlowsafe = citizenlowsafe;
	}

	public String getCitizenspeciality() {
		return this.citizenspeciality;
	}

	public void setCitizenspeciality(String citizenspeciality) {
		this.citizenspeciality = citizenspeciality;
	}

	public BigDecimal getCitizenlonglive() {
		return this.citizenlonglive;
	}

	public void setCitizenlonglive(BigDecimal citizenlonglive) {
		this.citizenlonglive = citizenlonglive;
	}

	public BigDecimal getCitizenstreet() {
		return this.citizenstreet;
	}

	public void setCitizenstreet(BigDecimal citizenstreet) {
		this.citizenstreet = citizenstreet;
	}

	public BigDecimal getCitizenvillage() {
		return this.citizenvillage;
	}

	public void setCitizenvillage(BigDecimal citizenvillage) {
		this.citizenvillage = citizenvillage;
	}

	

	public String getCitizenfloor() {
		return citizenfloor;
	}

	public void setCitizenfloor(String citizenfloor) {
		this.citizenfloor = citizenfloor;
	}

	public String getCitizencell() {
		return this.citizencell;
	}

	public void setCitizencell(String citizencell) {
		this.citizencell = citizencell;
	}

	public String getCitizenroomno() {
		return this.citizenroomno;
	}

	public void setCitizenroomno(String citizenroomno) {
		this.citizenroomno = citizenroomno;
	}

	public BigDecimal getCitizencommunity() {
		return this.citizencommunity;
	}

	public void setCitizencommunity(BigDecimal citizencommunity) {
		this.citizencommunity = citizencommunity;
	}

	public BigDecimal getCreateuserid() {
		return this.createuserid;
	}

	public void setCreateuserid(BigDecimal createuserid) {
		this.createuserid = createuserid;
	}

	public BigDecimal getUpdateuserid() {
		return this.updateuserid;
	}

	public void setUpdateuserid(BigDecimal updateuserid) {
		this.updateuserid = updateuserid;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getCitizennote() {
		return this.citizennote;
	}

	public void setCitizennote(String citizennote) {
		this.citizennote = citizennote;
	}

	public String getIsYuling() {
		return this.isYuling;
	}

	public void setIsYuling(String isYuling) {
		this.isYuling = isYuling;
	}

	public String getIsFlow() {
		return this.isFlow;
	}

	public void setIsFlow(String isFlow) {
		this.isFlow = isFlow;
	}

	public String getIsCare() {
		return this.isCare;
	}

	public void setIsCare(String isCare) {
		this.isCare = isCare;
	}

	public String getIsDisable() {
		return this.isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	public String getIsParty() {
		return this.isParty;
	}

	public void setIsParty(String isParty) {
		this.isParty = isParty;
	}

	public String getIsWed() {
		return this.isWed;
	}

	public void setIsWed(String isWed) {
		this.isWed = isWed;
	}

	public String getIsWeiwen() {
		return this.isWeiwen;
	}

	public void setIsWeiwen(String isWeiwen) {
		this.isWeiwen = isWeiwen;
	}

	public String getIsDibao() {
		return this.isDibao;
	}

	public void setIsDibao(String isDibao) {
		this.isDibao = isDibao;
	}

	public String getIsOld() {
		return this.isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public String getIsShiye() {
		return this.isShiye;
	}

	public void setIsShiye(String isShiye) {
		this.isShiye = isShiye;
	}

	public BigDecimal getCitizenisoutman() {
		return this.citizenisoutman;
	}

	public void setCitizenisoutman(BigDecimal citizenisoutman) {
		this.citizenisoutman = citizenisoutman;
	}

	public BigDecimal getIsrectify() {
		return this.isrectify;
	}

	public void setIsrectify(BigDecimal isrectify) {
		this.isrectify = isrectify;
	}

	public BigDecimal getIshelpeducate() {
		return this.ishelpeducate;
	}

	public void setIshelpeducate(BigDecimal ishelpeducate) {
		this.ishelpeducate = ishelpeducate;
	}

	public BigDecimal getCitizenmanagerkind() {
		return this.citizenmanagerkind;
	}

	public void setCitizenmanagerkind(BigDecimal citizenmanagerkind) {
		this.citizenmanagerkind = citizenmanagerkind;
	}

	public Date getCitizencomeindate() {
		return this.citizencomeindate;
	}

	public void setCitizencomeindate(Date citizencomeindate) {
		this.citizencomeindate = citizencomeindate;
	}

	public Date getCitizenoutdate() {
		return this.citizenoutdate;
	}

	public void setCitizenoutdate(Date citizenoutdate) {
		this.citizenoutdate = citizenoutdate;
	}

	public Integer getCitizenfloatingpopulation() {
		return this.citizenfloatingpopulation;
	}

	public void setCitizenfloatingpopulation(Integer citizenfloatingpopulation) {
		this.citizenfloatingpopulation = citizenfloatingpopulation;
	}

	public String getCitizenliveaddress() {
		return this.citizenliveaddress;
	}

	public void setCitizenliveaddress(String citizenliveaddress) {
		this.citizenliveaddress = citizenliveaddress;
	}

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getCitizenstate() {
		return this.citizenstate;
	}

	public void setCitizenstate(String citizenstate) {
		this.citizenstate = citizenstate;
	}

	public String getCitizenlivestate() {
		return this.citizenlivestate;
	}

	public void setCitizenlivestate(String citizenlivestate) {
		this.citizenlivestate = citizenlivestate;
	}

	public String getCitizenyibao() {
		return this.citizenyibao;
	}

	public void setCitizenyibao(String citizenyibao) {
		this.citizenyibao = citizenyibao;
	}

	public String getCitizenzzww() {
		return this.citizenzzww;
	}

	public void setCitizenzzww(String citizenzzww) {
		this.citizenzzww = citizenzzww;
	}

	public String getIsSfltx() {
		return this.isSfltx;
	}

	public void setIsSfltx(String isSfltx) {
		this.isSfltx = isSfltx;
	}

	public String getCitizenphone() {
		return this.citizenphone;
	}

	public void setCitizenphone(String citizenphone) {
		this.citizenphone = citizenphone;
	}

	public Date getCitizenbookdate() {
		return this.citizenbookdate;
	}

	public void setCitizenbookdate(Date citizenbookdate) {
		this.citizenbookdate = citizenbookdate;
	}

	public String getLivesaddress() {
		return livesaddress;
	}

	public void setLivesaddress(String livesaddress) {
		this.livesaddress = livesaddress;
	}

	public String getCitizenfloortitle() {
		return citizenfloortitle;
	}

	public void setCitizenfloortitle(String citizenfloortitle) {
		this.citizenfloortitle = citizenfloortitle;
	}


	
	
}