package com.ithome.popu.server;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.farm.console.FarmManager;
import com.farm.console.prisist.dao.DictionaryTypeDaoInter;
import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;


import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.ithome.popu.dao.PopuCitizenInfoDaoInter;
import com.ithome.popu.domain.PopuCitizenInfo;
import com.ithome.popu.web.action.ActionPopuAddressQuery;

/**
 * 人口基础信息
 * 
 * @author MAC_wd
 */
public class PopuCitizenInfoManagerImpl implements PopuCitizenInfoManagerInter {
	private PopuCitizenInfoDaoInter popuCitizenInfoDao;
	
	private DictionaryTypeDaoInter dictionarytypeDao;
	
	private static final Logger log = Logger
			.getLogger(PopuCitizenInfoManagerImpl.class);

	public PopuCitizenInfo insertPopuCitizenInfoEntity(PopuCitizenInfo entity,
			AloneUser user) {
		entity.setCitizenstate("1");
		entity.setCreateuserid(new BigDecimal(user.getId()));
		entity.setUpdateuserid(new BigDecimal(user.getId()));
		entity.setCreatedate(new Date());
		entity.setUpdatedate(new Date());
		
		/*{  // 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			apq.setIds(entity.getCitizenfloor().toString());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getCitizencell()==null){
				entity.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getCitizencell()) + entity.getCitizenroomno()
						+ "号");
			}
			
		}*/
		{// 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			
			AloneDictionaryType aloneDictionaryType = dictionarytypeDao.getEntity(entity.getCitizenfloor().toString());
			
			apq.setIds(aloneDictionaryType.getTreecode());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getCitizencell()==null){
				entity.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity.setLivesaddress(apq.getPathParent()
						//+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getCitizencell()) + entity.getCitizenroomno()
						+ "号");
			}
			
		}
		
		return popuCitizenInfoDao.insertEntity(entity);
	}

	public PopuCitizenInfo editPopuCitizenInfoEntity(PopuCitizenInfo entity,
			AloneUser user) {
		PopuCitizenInfo entity2 = popuCitizenInfoDao.getEntity(entity.getCitizenid());
		entity2.setCitizenid(entity.getCitizenid());
		entity2.setHuid(entity.getHuid());
		entity2.setCitizenname(entity.getCitizenname()); //居民姓名
		entity2.setCitizencardtype(entity.getCitizencardtype()); //证件号码
		entity2.setCitizenidentity(entity.getCitizenidentity()); //证件编号
		entity2.setCitizenoldname(entity.getCitizenoldname()); //曾用名
		entity2.setCitizenhurelation(entity.getCitizenhurelation()); //与户主关系
		entity2.setCitizenishouseower(entity.getCitizenishouseower()); //是否房主
		entity2.setCitizenhustate(entity.getCitizenhustate()); //户籍状况
		entity2.setCitizenhuquality(entity.getCitizenhuquality()); //户口性质
		entity2.setCitizennation(entity.getCitizennation()); //民族
		entity2.setCitizensex(entity.getCitizensex()); //性别
		entity2.setCitizenbirth(entity.getCitizenbirth()); //出生日期
		entity2.setCitizenliveaddress(entity.getCitizenliveaddress());  //居住地址
		entity2.setCitizenbirthaddress(entity.getCitizenbirthaddress()); //出生地址
		entity2.setCitizennativeaddress(entity.getCitizennativeaddress()); //户籍地址
		entity2.setCitizenmobile(entity.getCitizenmobile()); //手机号码
		entity2.setCitizentype(entity.getCitizentype()); //人员类别
		entity2.setCitizenindate(entity.getCitizenindate()); //何时迁入
		entity2.setCityzeninaddress(entity.getCityzeninaddress()); //何地迁入
		entity2.setCitizenwedstate(entity.getCitizenwedstate()); //婚姻状况
		entity2.setCitizenbloodtype(entity.getCitizenbloodtype()); //血型
		entity2.setCitizenheight(entity.getCitizenheight()); //身高
		entity2.setCitizenhealth(entity.getCitizenhealth()); //健康状况
		entity2.setCitizenfaith(entity.getCitizenfaith()); //宗教信仰
		entity2.setCitizenstatus(entity.getCitizenstatus()); //政治面貌
		entity2.setCitizeneducation(entity.getCitizeneducation()); //文化程度
		entity2.setCitizenjobstate(entity.getCitizenjobstate()); //就业现状
		entity2.setCitizenjob(entity.getCitizenjob()); //职业
		entity2.setIsrectify(entity.getIsrectify()); //矫正人员
		entity2.setCitizenunitaddress(entity.getCitizenunitaddress()); //单位地址
		entity2.setIshelpeducate(entity.getIshelpeducate()); //帮教人员
		entity2.setCitizentel(entity.getCitizentel()); //单位电话
		entity2.setCitizenservice(entity.getCitizenservice()); //兵役状况
		entity2.setCitizenserviceplace(entity.getCitizenserviceplace()); //兵役服务处所
		entity2.setCitizencare(entity.getCitizencare()); //是否优抚对象
		entity2.setCitizenfloatingpopulation(entity.getCitizenfloatingpopulation()); //户籍非户籍
		entity2.setCitizenlowsafe(entity.getCitizenlowsafe()); //是否低保
		entity2.setCitizenlonglive(entity.getCitizenlonglive()); //是否常住
		entity2.setCitizennote(entity.getCitizennote()); //备注
		entity2.setCitizenmanagerkind(entity.getCitizenmanagerkind()); //管理类型
		entity2.setCitizencomeindate(entity.getCitizencomeindate()); //流入时间
		entity2.setCitizenoutdate(entity.getCitizenoutdate()); //流出时间
		
		entity2.setCreateuserid(entity.getCreateuserid());
		entity2.setUpdateuserid(entity.getUpdateuserid());
		
		entity2.setCitizencell(entity.getCitizencell());
		entity2.setCitizenfloor(entity.getCitizenfloor());
		entity2.setCitizenfloortitle(entity.getCitizenfloortitle());
		entity2.setCitizenroomno(entity.getCitizenroomno());
		
		
		{// 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			
			AloneDictionaryType aloneDictionaryType = dictionarytypeDao.getEntity(entity.getCitizenfloor().toString());
			
			apq.setIds(aloneDictionaryType.getTreecode());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getCitizencell()==null){
				entity2.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity2.setLivesaddress(apq.getPathParent()
						//+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getCitizencell()) + entity.getCitizenroomno()
						+ "号");
			}
			
		}
		
		/*{// 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			apq.setIds(entity.getCitizenfloor().toString());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getCitizencell()==null){
				entity2.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity2.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getCitizencell()) + entity.getCitizenroomno()
						+ "号");
			}
			
		}*/
		
		// entity2.setCreatedate(entity.getCreatedate());
		// entity2.setUpdatedate(entity.getUpdatedate());
		//entity2.setIsCare(entity.getIsCare());
		//entity2.setIsDisable(entity.getIsDisable());
		//entity2.setIsWed(entity.getIsWed());
		//entity2.setIsDibao(entity.getIsDibao());
		//entity2.setIsPlanbreed(entity.getIsPlanbreed());
		//entity2.setCitizenfangrelation(entity.getCitizenfangrelation());
		/*entity2.setCitizenlivestate(entity.getCitizenlivestate());
		entity2.setCitizenyibao(entity.getCitizenyibao());
		entity2.setCitizenzzww(entity.getCitizenzzww());
		entity2.setIsSfltx(entity.getIsSfltx());
		entity2.setCitizenphone(entity.getCitizenphone());
		entity2.setCitizenbookdate(entity.getCitizenbookdate());*/
		entity2.setUpdateuserid(new BigDecimal(user.getId()));
		entity2.setUpdatedate(new Date());
		popuCitizenInfoDao.editEntity(entity2);
		return entity2;
	}

	public void deletePopuCitizenInfoEntity(BigDecimal id, AloneUser user) {
		popuCitizenInfoDao.deleteEntity(popuCitizenInfoDao.getEntity(id));
	}

	public void deletePopuCitizenInfoEntityByLogic(BigDecimal id, AloneUser user) {
		PopuCitizenInfo entity2 = popuCitizenInfoDao.getEntity(id);
		entity2.setCitizenstate("0");
		popuCitizenInfoDao.editEntity(entity2);
	}

	public PopuCitizenInfo getPopuCitizenInfoEntity(BigDecimal id) {
		if (id == null) {
			return null;
		}
		return popuCitizenInfoDao.getEntity(id);
	}

	@Override
	public DataQuery createPopuCitizenInfoSimpleQuery(DataQuery query) {
		query.addSort(new DBSort("updatedate","desc nulls last"));
		query.addSort(new DBSort("citizenfloor","asc"));
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"POPU_CITIZEN_INFO a left join POPU_HU_INFO b on a.HUID=b.HUID",
						"'1',CITIZENID,a.LIVESADDRESS,HUFLOOR,CITIZENNAME,CITIZENHURELATION,CITIZENSEX,CITIZENNATION,CITIZENBIRTH,CITIZENCARDTYPE,CITIZENIDENTITY,CITIZENHUADDRESS,CITIZENMOBILE,CITIZENSTATUS,CITIZENEDUCATION,CITIZENJOBUNIT,CITIZENSERVICE,a.CREATEUSERID as CREATEUSERID,a.UPDATEUSERID as UPDATEUSERID,a.CREATEDATE as CREATEDATE,a.UPDATEDATE as UPDATEDATE,CITIZENNOTE,IS_CARE,IS_DISABLE,IS_WED,IS_DIBAO,CITIZENLIVEADDRESS,CITIZENLIVESTATE,CITIZENYIBAO,CITIZENZZWW,IS_SFLTX,CITIZENPHONE,CITIZENBOOKDATE,CITIZENHUQUALITY");
		dbQuery.addRule(new DBRule("a.CITIZENSTATE", "1", "="));
		return dbQuery;
	}

	@Override
	public DataQuery createPopuCitizenInfoSimpleQuery(DataQuery query,
			AloneOrganization org) {
		query.addSort(new DBSort("updatedate","desc nulls last"));
		query.addSort(new DBSort("citizenfloor","asc"));
		DataQuery dbQuery = DataQuery
				.init(
						query,
						//"POPU_CITIZEN_INFO a left join POPU_HU_INFO b on a.HUID=b.HUID left join address_tree c on b.HUFLOOR = c.code",
						"POPU_CITIZEN_INFO a left join POPU_HU_INFO b on a.HUID=b.HUID ",
						"'1',CITIZENID,a.LIVESADDRESS as LIVESADDRESS,a.CITIZENFLOOR as CITIZENFLOOR,a.CITIZENCELL as CITIZENCELL,a.CITIZENROOMNO as CITIZENROOMNO,CITIZENHUQUALITY,CITIZENHUSTATE,CITIZENNATIVEADDRESS,HUFLOOR,CITIZENNAME,CITIZENHURELATION,CITIZENSEX,CITIZENNATION,CITIZENBIRTH,CITIZENCARDTYPE,CITIZENIDENTITY,CITIZENHUADDRESS,CITIZENMOBILE,CITIZENSTATUS,CITIZENEDUCATION,CITIZENJOBUNIT,CITIZENSERVICE,a.CREATEUSERID as CREATEUSERID,a.UPDATEUSERID as UPDATEUSERID,a.CREATEDATE as CREATEDATE,a.UPDATEDATE as UPDATEDATE,CITIZENNOTE,IS_CARE,IS_DISABLE,IS_WED,IS_DIBAO,CITIZENLIVEADDRESS,CITIZENLIVESTATE,CITIZENYIBAO,CITIZENZZWW,IS_SFLTX,CITIZENPHONE,CITIZENBOOKDATE");
		dbQuery.addRule(new DBRule("a.CITIZENSTATE", "1", "="));
		//.addRule(new DBRule("a.citizenfloor", org.getTreecode(), "like-"));
		return dbQuery;
	}

	@Override
	public DataQuery createPopuPartymemberInfoSimpleQuery(DataQuery query,
			AloneOrganization org) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"POPU_CITIZEN_INFO a left join POPU_HU_INFO b on a.HUID=b.HUID left join address_tree c on b.HUFLOOR = c.code left join popu_partymember d on a.citizenid=d.citizenid",
						"'1',CITIZENID,LIVESADDRESS,HUFLOOR,CITIZENNAME,CITIZENHURELATION,CITIZENSEX,CITIZENNATION,CITIZENBIRTH,CITIZENCARDTYPE,CITIZENIDENTITY,CITIZENHUADDRESS,CITIZENMOBILE,CITIZENSTATUS,CITIZENEDUCATION,CITIZENJOBUNIT,CITIZENSERVICE,a.CREATEUSERID as CREATEUSERID,a.UPDATEUSERID as UPDATEUSERID,a.CREATEDATE as CREATEDATE,a.UPDATEDATE as UPDATEDATE,CITIZENNOTE,IS_CARE,IS_DISABLE,IS_WED,IS_DIBAO,CITIZENLIVEADDRESS,CITIZENLIVESTATE,CITIZENYIBAO,CITIZENZZWW,IS_SFLTX,CITIZENPHONE,CITIZENBOOKDATE,PARTYDUTY,PARTYINTODATE,PARTYWORKDATE,PARTYINDATE,PARTYJOINADDR,PARTYTRUE,PARTYNAME,BOOKDATE,PARTYNOTE");
		dbQuery.addRule(new DBRule("a.CITIZENSTATE", "1", "=")).addRule(
				new DBRule("c.GRIDCODE", org.getTreecode(), "like-")).addRule(
				new DBRule("CITIZENSTATUS", "1", "="));
		return dbQuery;
	}

	// ----------------------------------------------------------------------------------
	public PopuCitizenInfoDaoInter getpopuCitizenInfoDao() {
		return popuCitizenInfoDao;
	}

	public void setpopuCitizenInfoDao(PopuCitizenInfoDaoInter dao) {
		this.popuCitizenInfoDao = dao;
	}
	public DictionaryTypeDaoInter getDictionarytypeDao() {
		return dictionarytypeDao;
	}

	public void setDictionarytypeDao(DictionaryTypeDaoInter dictionarytypeDao) {
		this.dictionarytypeDao = dictionarytypeDao;
	}
	@Override
	public DataQuery createPopuLowSafeInfoSimpleQuery(DataQuery query,
			AloneOrganization org) {
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"POPU_CITIZEN_INFO a left join POPU_HU_INFO b on a.HUID=b.HUID left join address_tree c on b.HUFLOOR = c.code left join popu_citizenlowsafe d on a.citizenid = d.citizenid",
						"'1',CITIZENID,LIVESADDRESS,HUFLOOR,CITIZENNAME,CITIZENHURELATION,CITIZENSEX,CITIZENNATION,CITIZENBIRTH,CITIZENCARDTYPE,CITIZENIDENTITY,CITIZENHUADDRESS,CITIZENMOBILE,CITIZENSTATUS,CITIZENEDUCATION,CITIZENJOBUNIT,CITIZENSERVICE,a.CREATEUSERID as CREATEUSERID,a.UPDATEUSERID as UPDATEUSERID,a.CREATEDATE as CREATEDATE,a.UPDATEDATE as UPDATEDATE,CITIZENNOTE,IS_CARE,IS_DISABLE,IS_WED,IS_DIBAO,CITIZENLIVEADDRESS,CITIZENLIVESTATE,CITIZENYIBAO,CITIZENZZWW,IS_SFLTX,CITIZENPHONE,CITIZENBOOKDATE,LOWPNUM,LOWSAFEMONEY,LOWTYPE,LOWNO,LOWBEGINTIME,LOWACCOUNT,LOWREASON,LOWBOOKDATE");
		dbQuery.addRule(new DBRule("a.CITIZENSTATE", "1", "=")).addRule(
				new DBRule("c.GRIDCODE", org.getTreecode(), "like-")).addRule(
				new DBRule("IS_DIBAO", "1", "="));
		return dbQuery;
	}
	/**
	 * 身份证唯一性校验
	 * @param idCard
	 * @return
	 */
	public boolean idCardCheckOnly(String idCard,String citizenid,BigDecimal huid){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		StringBuffer whereSql = new StringBuffer(" and  CITIZENIDENTITY = "+idCard+" and citizenstate != 0 and huid = "+ huid +"");
		
		//如果是修改 则不包含自己
		if(citizenid!=null&&!"".equals(citizenid)){
			whereSql.append(" and citizenid != "+citizenid+"");
		}
		try {
			list = DataQuery
			.init(
					null,
					"popu_citizen_info",
					"CITIZENID,HUID,CITIZENIDENTITY").addUserWhere(whereSql.toString()).search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		if(list.size()>0){
			return true;
		}
		return false;
	}

}
