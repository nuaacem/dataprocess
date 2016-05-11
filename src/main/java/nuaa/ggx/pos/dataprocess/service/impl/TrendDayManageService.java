package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendDayDao;
import nuaa.ggx.pos.dataprocess.model.TTrendDay;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISubjectManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendDayManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;
import nuaa.ggx.pos.dataprocess.util.Constants;
import nuaa.ggx.pos.dataprocess.util.WordsSplit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TrendDayManageService")
public class TrendDayManageService implements ITrendDayManageService{
    
	@Autowired
	private ITrendDayDao trenddayDao;
	
	@Autowired
	private IKeywordDao iKeywordDao;
	
	@Autowired
	private IWeiboContentSegService iWeiboContentSegService;

	@Autowired
	private ISubjectManageService iSubjectManageService;
	
	@Override
	public List<TTrendDay> findByTypeAndOid(Short type, Integer oid) {
		return trenddayDao.findByTypeAndOid(type, oid);
	}

	@Override
	public Boolean updateTrendDayByKeyword(String keyword) {
		HashMap<String, Integer[]> dayPoleNum = iWeiboContentSegService.getHourOrDayPoleNumByKeyword(keyword, Constants.IS_DAY);
		if (dayPoleNum.isEmpty()) {
			return false;
		}
		for (String key : dayPoleNum.keySet()) 
		{
			int day = (int)WordsSplit.getKeyDays(key);
			int oid = iKeywordDao.getIdByKeyword(keyword);
			Integer[] poleNum = dayPoleNum.get(key);
			TTrendDay tTrendDay = new TTrendDay();
			tTrendDay.setOid(oid);
			tTrendDay.setType(Constants.IS_KEYWORD);
			tTrendDay.setDay(day);
			tTrendDay.setTotalNum(poleNum[0] + poleNum[1] + poleNum[2]);
			tTrendDay.setPositiveNum(poleNum[0]);
			tTrendDay.setNeutralNum(poleNum[1]);
			tTrendDay.setNegativeNum(poleNum[2]);
			if (trenddayDao.findByTypeAndOidAndDay(Constants.IS_KEYWORD, oid, day).isEmpty()) {
			}
			else {
				tTrendDay.setId(trenddayDao.findByTypeAndOidAndDay(Constants.IS_KEYWORD, oid, day).get(0).getId());
			}
			trenddayDao.merge(tTrendDay);
		}
		return true;
	}
	
	@Override
	public Boolean updateTrendDayBySubjects() {
		HashMap<Integer, Integer[]> allSubjectsIdToKeywordsId = iSubjectManageService.getAllSubjectsIdToKeywordsId();
		if (allSubjectsIdToKeywordsId.isEmpty()) {
			return false;
		}
		for (Integer subjectId : allSubjectsIdToKeywordsId.keySet()) 
		{
			List<TTrendDay> trendDays = new ArrayList<>();
			Integer[] keywordsId = allSubjectsIdToKeywordsId.get(subjectId);
			for (int i = 0; i < keywordsId.length; i++) {
				trendDays.addAll(trenddayDao.findByTypeAndOid(Constants.IS_KEYWORD, keywordsId[i]));
			}
			HashMap<Integer, Integer[]> daySubjectNum = new HashMap<>();
			for (TTrendDay trendDay : trendDays) {
				Integer tempDay = trendDay.getDay();
				if (daySubjectNum.containsKey(tempDay)) {
					Integer[] daySubjectPoleNum = {daySubjectNum.get(tempDay)[0] + trendDay.getPositiveNum(), daySubjectNum.get(tempDay)[1] + 
													trendDay.getNeutralNum(), daySubjectNum.get(tempDay)[2] + trendDay.getNegativeNum()};
					daySubjectNum.put(tempDay, daySubjectPoleNum);
				}
				else {
					Integer[] daySubjectPoleNum = {trendDay.getPositiveNum(), trendDay.getNeutralNum(), trendDay.getNegativeNum()};
					daySubjectNum.put(tempDay, daySubjectPoleNum);
				}
			}
			for (Integer keyDay : daySubjectNum.keySet()) 
			{
				Integer[] daySubjectPoleNum = daySubjectNum.get(keyDay);
				TTrendDay tTrendDay = new TTrendDay();
				tTrendDay.setOid(subjectId);
				tTrendDay.setType(Constants.IS_SUBJECT);
				tTrendDay.setDay(keyDay);
				tTrendDay.setTotalNum(daySubjectPoleNum[0] + daySubjectPoleNum[1] + daySubjectPoleNum[2]);
				tTrendDay.setPositiveNum(daySubjectPoleNum[0]);
				tTrendDay.setNeutralNum(daySubjectPoleNum[1]);
				tTrendDay.setNegativeNum(daySubjectPoleNum[2]);
				if (trenddayDao.findByTypeAndOidAndDay(Constants.IS_SUBJECT, subjectId, keyDay).isEmpty()) {
				}
				else {
					tTrendDay.setId(trenddayDao.findByTypeAndOidAndDay(Constants.IS_SUBJECT, subjectId, keyDay).get(0).getId());
				}
				trenddayDao.merge(tTrendDay);
			}
		}
		return true;
	}
}
