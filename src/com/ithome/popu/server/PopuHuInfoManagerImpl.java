package com.ithome.popu.server;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farm.console.FarmManager;
import com.farm.console.prisist.dao.DictionaryTypeDaoInter;
import com.farm.console.prisist.domain.AloneDictionaryType;
import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;
import org.apache.log4j.Logger;


import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.ithome.popu.dao.PopuHuInfoDaoInter;
import com.ithome.popu.domain.PopuHuInfo;
import com.ithome.popu.web.action.ActionPopuAddressQuery;

/**
 * 房屋基础信息
 * 
 * @author MAC_wd
 */
public class PopuHuInfoManagerImpl implements PopuHuInfoManagerInter {
	private PopuHuInfoDaoInter popuHuInfoDao;
	//private PopuCitizenInfoDaoInter popuCitizenInfoDao;
	
	private DictionaryTypeDaoInter dictionarytypeDao;
	
	private static final Logger log = Logger
			.getLogger(PopuHuInfoManagerImpl.class);

	public PopuHuInfo insertPopuHuInfoEntity(PopuHuInfo entity, AloneUser user) {
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		
		{// 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			
			AloneDictionaryType aloneDictionaryType = dictionarytypeDao.getEntity(entity.getHufloor().toString());
			
			apq.setIds(aloneDictionaryType.getTreecode());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getHucell()==null){
				entity.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity.setLivesaddress(apq.getPathParent()
						//+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getHucell()) + entity.getHuroomno()
						+ "号");
			}
			
		}
		
		
		/*entity.setHucreatedate(new Date());
		{  // 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			apq.setIds(entity.getHufloor().toString());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getHucell()==null){
				entity.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getHucell()) + entity.getHuroomno()
						+ "号");
			}
			
		}*/
		entity.setHupstate("1");
		entity.setCreateuserid(new BigDecimal(user.getId()));
		entity.setHucreatedate(new Date());
		entity.setUpdateuserid(new BigDecimal(user.getId()));
		entity.setHuupdatedate(new Date());
		
		PopuHuInfo huinfo = popuHuInfoDao.insertEntity(entity);
		
		//保存户信息时自动保存一条相应的居民信息
		/*PopuCitizenInfo citizeninfo = new PopuCitizenInfo();
		citizeninfo.setHuid(huinfo.getHuid());
		citizeninfo.setCitizenname(huinfo.getHuname());  //居民姓名
		citizeninfo.setCitizencardtype(new BigDecimal(huinfo.getHucardtype())); //证件类型
		citizeninfo.setCitizenidentity(huinfo.getHuidentity()); //身份证
		if(huinfo.getHuhouserelation()!=null&&!"".equals(huinfo.getHuhouserelation())){
			citizeninfo.setCitizenhurelation(new BigDecimal(huinfo.getHuhouserelation()));//与户主关系
		}
		citizeninfo.setCitizenishouseower(new BigDecimal(1)); //是否房主
		citizeninfo.setCitizenmobile(huinfo.getHumobile()); //手机号码
		citizeninfo.setCitizenhustate(huinfo.getHustate()); //户籍状态
		citizeninfo.setCitizennativeaddress(huinfo.getHuaddress()); //户籍地址
		
		citizeninfo.setCitizenhuquality(huinfo.getHuquale()); //户口性质  
		citizeninfo.setCitizenliveaddress(huinfo.getHucurraddress()); //居住地址
		citizeninfo.setCitizenmanagerkind(huinfo.getHumanagerkind()); //管理类型
		citizeninfo.setCitizenfloatingpopulation((huinfo.getHufloatingpopulation()==null?0:huinfo.getHufloatingpopulation()).intValue()); //户籍非户籍
		citizeninfo.setCitizenlonglive(huinfo.getHulonglive()); //是否常住
		
		//18位身份证
		if(huinfo.getHuidentity()!=null&&huinfo.getHuidentity().length()==18){ 
			String year = huinfo.getHuidentity().substring(6,10);
			String month = huinfo.getHuidentity().substring(10,12);
			String day = huinfo.getHuidentity().substring(12,14);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				citizeninfo.setCitizenbirth(sdf.parse(year+"-"+month+"-"+day)); //出生日期
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			int sex = Integer.parseInt(huinfo.getHuidentity().substring(16,17));
			if(sex%10==0){
				citizeninfo.setCitizensex(new BigDecimal(118)); //女
			}else{
				citizeninfo.setCitizensex(new BigDecimal(117)); //男 
			}
		}
		//15位身份证
		if(huinfo.getHuidentity()!=null&&huinfo.getHuidentity().length()==15){
			String year = "19"+huinfo.getHuidentity().substring(6,8);
			String month = huinfo.getHuidentity().substring(8,10);
			String day = huinfo.getHuidentity().substring(10,12);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				citizeninfo.setCitizenbirth(sdf.parse(year+"-"+month+"-"+day)); //出生日期
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			int sex = Integer.parseInt(huinfo.getHuidentity().substring(14,15));
			if(sex%10==0){
				citizeninfo.setCitizensex(new BigDecimal(118)); //女
			}else{
				citizeninfo.setCitizensex(new BigDecimal(117)); //男 
			}
		}
		
		citizeninfo.setCitizenfloor(huinfo.getHufloor());  //楼层
		citizeninfo.setCitizencell(huinfo.getHucell()); //单元
		citizeninfo.setCitizenroomno(huinfo.getHuroomno()); //门牌号
		
		citizeninfo.setCitizenstate("1"); //状态
		citizeninfo.setCreatedate(new Date());
		
		popuCitizenInfoDao.insertEntity(citizeninfo);*/
		
		return huinfo;
	}

	public PopuHuInfo editPopuHuInfoEntity(PopuHuInfo entity, AloneUser user) {
		PopuHuInfo entity2 = popuHuInfoDao.getEntity(entity.getHuid());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		
		entity2.setHuid(entity.getHuid());
		entity2.setHucardtype(entity.getHucardtype()); //证件类型
		entity2.setHuidentity(entity.getHuidentity()); //证件编号
		entity2.setHuname(entity.getHuname()); //户主姓名
		entity2.setHuquale(entity.getHuquale()); //户口性质
		entity2.setHutype(entity.getHutype()); // 户口类别
		entity2.setHustate(entity.getHustate()); // 户籍状态
		entity2.setHuaddress(entity.getHuaddress()); //户籍地址
		entity2.setHupeoplenum(entity.getHupeoplenum()); //户籍人数
		entity2.setHusignman(entity.getHusignman()); //户籍签办人
		entity2.setHusigndate(entity.getHusigndate()); //签发时间
		entity2.setHuhouserelation(entity.getHuhouserelation()); //房主与户主的关系
		entity2.setHufamilytel(entity.getHufamilytel()); //家庭电话
		entity2.setLivesaddress(entity.getLivesaddress()); //住址
		entity2.setHufamilypnum(entity.getHufamilypnum()); //家庭人数
		entity2.setHustructure(entity.getHustructure()); //家庭结构
		entity2.setHuhousequale(entity.getHuhousequale()); //住房性质
		entity2.setHuunit(entity.getHuunit()); //房主/产权单位
		entity2.setHutel(entity.getHutel()); //户主电话
		entity2.setHumobile(entity.getHumobile()); //户主手机
		entity2.setHuarea(entity.getHuarea()); //住房面积(M2)
		entity2.setHuavgarea(entity.getHuavgarea()); //人均面积(M2)
		entity2.setHuouthousenum(entity.getHuouthousenum()); //区外住房(套)
		entity2.setHuinnerhousenum(entity.getHuinnerhousenum()); //区内住房（套）
		entity2.setHuavgincome(entity.getHuavgincome()); //人均收入
		entity2.setHulowsafe(entity.getHulowsafe()); //是低保家庭
		entity2.setHulonglive(entity.getHulonglive()); //是否常住
		entity2.setHucurraddress(entity.getHucurraddress()); //居住地址
		entity2.setHunote(entity.getHunote()); //备注
		entity2.setHumanagerkind(entity.getHumanagerkind()); //管理类型
		entity2.setHuindate(entity.getHuindate()); //流入时间
		entity2.setHufloatingpopulation(entity.getHufloatingpopulation()); //户籍非户籍
		entity2.setHuoutdate(entity.getHuoutdate()); //流出时间
		entity2.setUpdateuserid(new BigDecimal(user.getId()));
		entity2.setHuupdatedate(new Date());
		
		
		entity2.setHutel(entity.getHutel());
		entity2.setHufloor(entity.getHufloor());
		entity2.setHufloortitle(entity.getHufloortitle());
		entity2.setHucell(entity.getHucell());
		entity2.setHuroomno(entity.getHuroomno());
		
		
		{// 房屋地址文本路径的拼接
			ActionPopuAddressQuery apq = new ActionPopuAddressQuery();
			
			AloneDictionaryType aloneDictionaryType = dictionarytypeDao.getEntity(entity.getHufloor().toString());
			
			apq.setIds(aloneDictionaryType.getTreecode());
			// 加载楼的上层地址
			apq.nodeAllPath();
			// 加载楼的信息
			apq.nodeLoad();
			if(entity.getHucell()==null){
				entity2.setLivesaddress(apq.getPathParent()
						+ apq.getNode().get("NAME"));
			}else{
				entity2.setLivesaddress(apq.getPathParent()
						//+ apq.getNode().get("NAME")
						+ FarmManager.instance().findDicTitleForIndex("HU_CELL")
								.get(entity.getHucell()) + entity.getHuroomno()
						+ "号");
			}
			
		}

		popuHuInfoDao.editEntity(entity2);
		return entity2;
	}

	public void deletePopuHuInfoEntity(BigDecimal id, AloneUser user) {
		popuHuInfoDao.deleteEntity(popuHuInfoDao.getEntity(id));
	}

	public PopuHuInfo getPopuHuInfoEntity(BigDecimal id) {
		if (id == null) {
			return null;
		}
		return popuHuInfoDao.getEntity(id);
	}

	@Override
	public DataQuery createPopuHuInfoSimpleQuery(DataQuery query,
			AloneOrganization org) {
		query.addSort(new DBSort("huupdatedate","desc nulls last"));
		query.addSort(new DBSort("hufloor","asc"));
		DataQuery dbQuery = DataQuery
				.init(
						query,
						"POPU_HU_INFO",
						"'1',HUID,HUNAME,LIVESADDRESS,HUFLOOR,HUCELL,HUROOMNO,huquale,huidentity,hutype,HUSTATE,hupeoplenum,hustructure,hutel,HUMOBILE,huhousequale,husignman")
						.addRule(new DBRule("HUPSTATE", "1", "="));
		return dbQuery;
	}

	@Override
	public void deletePopuHuInfoEntityByLogic(BigDecimal id, AloneUser user) {
		PopuHuInfo entity2 = popuHuInfoDao.getEntity(id);
		entity2.setHupstate("0");
		popuHuInfoDao.editEntity(entity2);
		// 删除人
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("HUID", entity2.getHuid(), "="));
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("CITIZENSTATE", "0");
		//popuCitizenInfoDao.updataEntitys(values, rules);
	}

	
	/**
	 * 身份证唯一性校验
	 * @param idCard
	 * @return
	 */
	public boolean idCardCheckOnly(String idCard,String huid){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		StringBuffer whereSql = new StringBuffer(" and  huidentity = "+idCard + "and hupstate != 0");
		
		//如果是修改 则不包含自己
		if(huid!=null&&!"".equals(huid)){
			whereSql.append(" and huid != "+huid+"");
		}
		try {
			list = DataQuery
			.init(
					null,
					"popu_hu_info",
					"HUID,HUNAME,huidentity").addUserWhere(whereSql.toString()).search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		if(list.size()>0){
			return true;
		}
		
		return idCardCheckOnlyInCitizen(idCard,huid);
		
		//return false;
	}
	/**
	 * 同时校验居民信息表中身份证是否唯一
	 * @param idCard
	 * @return
	 */
	public boolean idCardCheckOnlyInCitizen(String idCard,String huid){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		StringBuffer whereSql = new StringBuffer(" and  citizenidentity = "+idCard + " and citizenstate !=0");
		
		//如果是修改 则不包含自己
		if(huid!=null&&!"".equals(huid)){
			whereSql.append(" and huid != "+huid+"");
			//如果是修改户信息 则不校验居民表中身份证 直接返回false
			return false;
		}
		
		try {
			list = DataQuery
			.init(
					null,
					"POPU_CITIZEN_INFO",
					"citizenid,citizenname,citizenidentity").addUserWhere(whereSql.toString()).search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		if(list.size()>0){
			return true;
		}
		return false;
	}
	
	
	// ----------------------------------------------------------------------------------
	public PopuHuInfoDaoInter getpopuHuInfoDao() {
		return popuHuInfoDao;
	}

	public void setpopuHuInfoDao(PopuHuInfoDaoInter dao) {
		this.popuHuInfoDao = dao;
	}

	public DictionaryTypeDaoInter getDictionarytypeDao() {
		return dictionarytypeDao;
	}

	public void setDictionarytypeDao(DictionaryTypeDaoInter dictionarytypeDao) {
		this.dictionarytypeDao = dictionarytypeDao;
	}

	/*public PopuCitizenInfoDaoInter getPopuCitizenInfoDao() {
		return popuCitizenInfoDao;
	}

	public void setPopuCitizenInfoDao(PopuCitizenInfoDaoInter popuCitizenInfoDao) {
		this.popuCitizenInfoDao = popuCitizenInfoDao;
	}*/

}
