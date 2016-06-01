package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendHourDao;
import nuaa.ggx.pos.dataprocess.model.TTrendHour;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISubjectManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendHourManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;
import nuaa.ggx.pos.dataprocess.util.Constants;
import nuaa.ggx.pos.dataprocess.util.TimeWordsSplit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TrendHourManageService")
public class TrendHourManageService implements ITrendHourManageService{
    
	@Autowired
	private ITrendHourDao trendhourDao;
	
	@Autowired
	private IKeywordDao iKeywordDao;
	
	@Autowired
	private IWeiboContentSegService iWeiboContentSegService;
	
	@Autowired
	private ISubjectManageService iSubjectManageService;
	
	@Override
	public List<TTrendHour> findByTypeAndOid(Short type, Integer oid) {
		return trendhourDao.findByTypeAndOid(type, oid);
	}
	
	@Override
	public Boolean updateTrendHourByKeyword(String keyword) {
		HashMap<String, Integer[]> hourPoleNum = iWeiboContentSegService.getHourOrDayPoleNumByKeyword(keyword, Constants.IS_HOUR);
		if (hourPoleNum.isEmpty()) {
			return false;
		}
		for (String key : hourPoleNum.keySet()) 
		{
			int hour = (int)TimeWordsSplit.getKeyHours(key) + 8;//本地时间比GMT格式时间早八个小时
			int oid = iKeywordDao.getIdByKeyword(keyword);
			Integer[] poleNum = hourPoleNum.get(key);
			TTrendHour tTrendHour = new TTrendHour();
			tTrendHour.setOid(oid);
			tTrendHour.setType(Constants.IS_KEYWORD);
			tTrendHour.setHour(hour);
			tTrendHour.setTotalNum(poleNum[0] + poleNum[1] + poleNum[2]);
			tTrendHour.setPositiveNum(poleNum[0]);
			tTrendHour.setNeutralNum(poleNum[1]);
			tTrendHour.setNegativeNum(poleNum[2]);
			if (trendhourDao.findByTypeAndOidAndHour(Constants.IS_KEYWORD, oid, hour).isEmpty()) {
			}
			else {
				tTrendHour.setId(trendhourDao.findByTypeAndOidAndHour(Constants.IS_KEYWORD, oid, hour).get(0).getId());
			}
			trendhourDao.merge(tTrendHour);
		}
		return true;
	}

	@Override
	public Boolean updateTrendHourBySubjects() {
		HashMap<Integer, Integer[]> allSubjectsIdToKeywordsId = iSubjectManageService.getAllSubjectsIdToKeywordsId();
		if (allSubjectsIdToKeywordsId.isEmpty()) {
			return false;
		}
		for (Integer subjectId : allSubjectsIdToKeywordsId.keySet()) 
		{
			List<TTrendHour> trendHours = new ArrayList<>();
			Integer[] keywordsId = allSubjectsIdToKeywordsId.get(subjectId);
			for (int i = 0; i < keywordsId.length; i++) {
				trendHours.addAll(trendhourDao.findByTypeAndOid(Constants.IS_KEYWORD, keywordsId[i]));
			}
			HashMap<Integer, Integer[]> hourSubjectNum = new HashMap<>();
			for (TTrendHour trendHour : trendHours) {
				Integer tempHour = trendHour.getHour();
				if (hourSubjectNum.containsKey(tempHour)) {
					Integer[] hourSubjectPoleNum = {hourSubjectNum.get(tempHour)[0] + trendHour.getPositiveNum(), hourSubjectNum.get(tempHour)[1] + 
													trendHour.getNeutralNum(), hourSubjectNum.get(tempHour)[2] + trendHour.getNegativeNum()};
					hourSubjectNum.put(tempHour, hourSubjectPoleNum);
				}
				else {
					Integer[] hourSubjectPoleNum = {trendHour.getPositiveNum(), trendHour.getNeutralNum(), trendHour.getNegativeNum()};
					hourSubjectNum.put(tempHour, hourSubjectPoleNum);
				}
			}
			for (Integer keyHour : hourSubjectNum.keySet()) 
			{
				Integer[] hourSubjectPoleNum = hourSubjectNum.get(keyHour);
				TTrendHour tTrendHour = new TTrendHour();
				tTrendHour.setOid(subjectId);
				tTrendHour.setType(Constants.IS_SUBJECT);
				tTrendHour.setHour(keyHour);
				tTrendHour.setTotalNum(hourSubjectPoleNum[0] + hourSubjectPoleNum[1] + hourSubjectPoleNum[2]);
				tTrendHour.setPositiveNum(hourSubjectPoleNum[0]);
				tTrendHour.setNeutralNum(hourSubjectPoleNum[1]);
				tTrendHour.setNegativeNum(hourSubjectPoleNum[2]);
				if (trendhourDao.findByTypeAndOidAndHour(Constants.IS_SUBJECT, subjectId, keyHour).isEmpty()) {
				}
				else {
					tTrendHour.setId(trendhourDao.findByTypeAndOidAndHour(Constants.IS_SUBJECT, subjectId, keyHour).get(0).getId());
				}
				trendhourDao.merge(tTrendHour);
			}
		}
		return true;
	}
}
