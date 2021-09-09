package spring.service.wkca;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertek.framework.exception.JudMsgConstant.ACTION_TYPE;
import com.sertek.framework.mybatis.MyHashMap;
import com.sertek.framework.spring.dao.DBProxy;
import com.sertek.framework.spring.service.AcerService;

/**
 * 	### 收容儀錶板等整合 ###
 * 	使用方法:
 * 	需再S0GService設定
 * 	如有一支新的警示需要增加，只需要將DB相關Table設定好，且再儀表板(DASHBOARDService)加上一個CASE "COCXXX" ，
 * 	使用countOnlineCaseSrv.updateCount(prgid);放入PRGID即可；
 * 	接者將該警示寫成如下範例之method:		 (C0CXXX:依順序排列下去)		(該警示名稱)
 * 	@CountOnlineCaseAnnotation(prgid = "COCXXX", prgnm = "線上收容案件待收案件數")
 * 	private int getXXXXCaseCnt(){
 * 		return dbProxy.getDB().selectOne(XML_PACKAGE + "getXXXXsolPendingCase", keyMap);
 * 	}
 * 	記得回傳int即可，會自動將筆數insert or update 至 G.S0G，
 * 	再由儀表板程式撈回顯示。
 * 	@author Ren.You
 * 
 */

@Service
public class CountOnlineCaseService extends AcerService {

	private final String XML_PACKAGE = "wkca.CountOnlineCase.";

	@Autowired
	private DBProxy dbProxy = new DBProxy();

	MyHashMap keyMap = new MyHashMap();

	public void init() {
		keyMap.put("gowner", getUser().getGowner());
		keyMap.put("crtid", getUser().getCrtid());
		keyMap.put("sys", getUser().getSys());
		keyMap.put("usrid", getUser().getUsrid());
		keyMap.put("id", getUser().getId());
		keyMap.put("owner", getUser().getOwner());
		keyMap.put("dpt", getUser().getDpt());
		keyMap.put("dptcd", getUser().getDptcd());
		keyMap.put("totalDptcd", getTotalDptcd());
		keyMap.put("totalDpt", getTotalDpt());
		keyMap.put("adusrid", getUser().getAdusrid());
		keyMap.put("gsoCrmid", getGsoCrmid());
		keyMap.put("gsoSwitch", getGsoSwitch());
		keyMap.put("JAXXdptcd", getJAXXdptcd());
		keyMap.put("CAXXdptcd", getCAXXdptcd());
	}
	
	public Integer updateCount(String casePrgid) {
		
		int lstresult = 0;
		
		try {

			Method[] methodArr = this.getClass().getDeclaredMethods();

			for (Method method : methodArr) {
				if (method.isAnnotationPresent(CountOnlineCaseAnnotation.class)) {
					String thisMethodPrgid = method.getDeclaredAnnotation(CountOnlineCaseAnnotation.class).prgid();
					String thisMethodPrgnm = method.getDeclaredAnnotation(CountOnlineCaseAnnotation.class).prgnm();
					
					if(StringUtils.equalsIgnoreCase(thisMethodPrgid, casePrgid)) {
						logger.info("###### 執行更新之警示 : {} ######", thisMethodPrgnm);
						// 警示查詢回傳的筆數
						lstresult = (int) method.invoke(this);
						
//						String lsttm = ud.formatDate(ud.nowWDate(), "/") + " " + ud.formatTime(ud.nowTime(), ":");
//						logger.info("###### 最後執行日期 : {} ######", lsttm);
//
//						HashMap<String, Object> map = new HashMap<>();
//						map.put("gowner", getUser().getGowner());
//						map.put("usrid", getUser().getUsrid());
//						map.put("prgid", thisMethodPrgid);
//						map.put("lsttm", lsttm);
//						map.put("lstresult", lstresult);
//						boolean s0gHasData = (int)(dbProxy.getDB().selectOne(XML_PACKAGE + "check_S0G_Data", map)) > 0 ? true : false;
//						logger.info("##### G.S0G是否有資料 : {} #####", s0gHasData);
//						if(s0gHasData) {
//							logger.info("###### doLoginUpdate : {} ######", map);
////							dbProxy.getDB().update(XML_PACKAGE + "doLoginUpdate", map);
//						}else {
//							logger.info("###### doLoginInsert : {} ######", map);
//							dbProxy.getDB().update(XML_PACKAGE + "doLoginInsert", map);
//						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("警示查詢失敗!請洽詢資訊人員!，錯誤的Prgid : \"" + casePrgid + "\"");
			throw judExceptionFactory.createJudException(ACTION_TYPE.SYSTEM, "警示查詢失敗!請洽詢資訊人員!");
		}
		return lstresult;
	}
	
	
	@CountOnlineCaseAnnotation(prgid = "COC001", prgnm = "線上收容案件待收案件數")
	private int getGsoPendingCAXXReciveCaseCnt() { // 線上收容案件待收案件數
		return dbProxy.getDB().selectOne(XML_PACKAGE + "getGsoPendingCAXXReciveCaseCnt", keyMap);
	}

//	@CountOnlineCaseAnnotation(prgid = "COC002", prgnm = "線上起訴待處理")
//	private int getSolPendingCaseCnt() { // 線上起訴待處理
//		return dbProxy.getDB().selectOne(XML_PACKAGE + "getsolPendingCase", keyMap);
//	}
//
//	@CountOnlineCaseAnnotation(prgid = "COC003", prgnm = "線上起訴待預審處理")
//	private int getSolPendingPreViewCaseCnt() { // 線上起訴待預審處理
//		return dbProxy.getDB().selectOne(XML_PACKAGE + "getsolPendingPreViewCase", keyMap);
//	}

//	@CountOnlineCaseAnnotation(prgid = "COC004", prgnm = "線上起訴收狀待處理") // XML尚未加入
//	private int getSolPendingSupplementCaseCnt() { // 線上起訴收狀待處理
//		return dbProxy.getDB().selectOne(XML_PACKAGE + "getSolPendingSupplementCaseCnt", keyMap);
//	}

	@CountOnlineCaseAnnotation(prgid = "COC005", prgnm = "線上收容原本未處理案件數")
	private int getgsoPendingOriginalCaseCnt() { // 線上收容原本未處理案件數
		return dbProxy.getDB().selectOne(XML_PACKAGE + "getgsoPendingOriginalCase", keyMap);
	}

//	@CountOnlineCaseAnnotation(prgid = "COC006", prgnm = "線上收容筆錄聲請重簽數")
//	private int getGsoPendingRecordReSignCnt() {// 線上收容筆錄聲請重簽數
//		return dbProxy.getDB().selectOne(XML_PACKAGE + "getGsoPendingRecordReSignCnt", keyMap);
//	}

	@CountOnlineCaseAnnotation(prgid = "COC007", prgnm = "線上收容監印室已用印未送數")
	private int getGsoPendingPrintReciveCaseCnt() {// 線上收容監印室已用印未送數
		return dbProxy.getDB().selectOne(XML_PACKAGE + "gsoPendingPrintReciveCase", keyMap);
	}

	@CountOnlineCaseAnnotation(prgid = "COC008", prgnm = "線上收容案件新收案件數")
	private int getGsoPendingJAXXReciveCaseCnt() {// 線上收容案件新收案件數
		return dbProxy.getDB().selectOne(XML_PACKAGE + "getGsoPendingJAXXReciveCaseCnt", keyMap);
	}

//	@CountOnlineCaseAnnotation(prgid = "COC009", prgnm = "線上收容案件電子歸檔數")
//	private int getGsoPendingArchiveCaseCnt() { // 線上收容案件電子歸檔數
//		return dbProxy.getDB().selectOne(XML_PACKAGE + "getGsoPendingArchiveCase");
//	}
//
//	@CountOnlineCaseAnnotation(prgid = "COC010", prgnm = "線上收容案件報結提醒")
//	private List<HashMap<String, String>> getGsoPendingReportOverCaseCnt() { // 線上收容案件報結提醒
//		return dbProxy.getDB().selectOne(XML_PACKAGE + "getGsoPendingReportOverCase");
//	}

	private List<String> getTotalDpt() {
		List<String> totalDpt = new ArrayList<String>();
		totalDpt.add(getUser().getDpt());
		if (!"".equals(getUser().getAgentDptStr())) {
			String[] agentDpts = getUser().getAgentDptStr().split(",");
			for (int i = 0; i < agentDpts.length; i++) {
				totalDpt.add(agentDpts[i]);
			}
		}
		return totalDpt;
	}

	private List<String> getTotalDptcd() {
		List<String> totalDptcd = new ArrayList<String>();
		totalDptcd.add(getUser().getDptcd());
		if (!"".equals(getUser().getAgentDptcdStr())) {
			String[] agentDptcds = getUser().getAgentDptcdStr().split(",");
			for (int i = 0; i < agentDptcds.length; i++) {
				totalDptcd.add(agentDptcds[i]);
			}
		}
		return totalDptcd;
	}

	private List<String> getGsoCrmid() {
		List<String> gsoCrmid = new ArrayList<String>();
		String gsoCrmids = "";
		List<MyHashMap> gsoCrmidList = dbProxy.getDB().selectList(XML_PACKAGE + "getGsoCrmid");
		if (gsoCrmidList.size() > 0) {
			gsoCrmids = gsoCrmidList.get(0).get("ARGVL").toString();
		}
		if (!"".equals(gsoCrmids)) {
			String[] gso_crmid = gsoCrmids.split(",");
			for (int i = 0; i < gso_crmid.length; i++) {
				gsoCrmid.add(gso_crmid[i]);
			}
		}
		return gsoCrmid;
	}

	private String getGsoSwitch() {
		String gsoSwitch = dbProxy.getDB().selectOne(XML_PACKAGE + "getGsoSwitch", getUser().getGowner());
		return "Y".equals(gsoSwitch) ? gsoSwitch : "";
	}

	private List<String> getJAXXdptcd() {
		keyMap.put("adusrid", getUser().getAdusrid());
		return dbProxy.getDB().selectList(XML_PACKAGE + "getJAXXdptcd", keyMap);
	}

	private List<String> getCAXXdptcd() {
		keyMap.put("adusrid", getUser().getAdusrid());
		return dbProxy.getDB().selectList(XML_PACKAGE + "getCAXXdptcd", keyMap);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	private @interface CountOnlineCaseAnnotation {
		String prgid();

		String prgnm();
	}

}
