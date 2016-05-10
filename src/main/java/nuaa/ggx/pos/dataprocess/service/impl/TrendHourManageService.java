package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.HashMap;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendHourDao;
import nuaa.ggx.pos.dataprocess.model.TTrendHour;
import nuaa.ggx.pos.dataprocess.service.interfaces.ITrendHourManageService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;
import nuaa.ggx.pos.dataprocess.util.WordsSplit;

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
	
	@Override
	public List<TTrendHour> findByTypeAndOid(Short type, Integer oid) {
		return trendhourDao.findByTypeAndOid(type, oid);
	}
	
	@Override
	public Integer updateTrendHourKeyword(String keyword) {
		HashMap<String, Integer[]> hourPoleNum = iWeiboContentSegService.getHourPoleNumByKeyword(keyword);
		if (hourPoleNum.isEmpty()) {
			return 0;
		}
		for (String key : hourPoleNum.keySet()) 
		{
			int hour = (int)WordsSplit.getKeyHours(key);
			int oid = iKeywordDao.getIdByKeyword(keyword);
			Integer[] poleNum = hourPoleNum.get(key);
			TTrendHour tTrendHour = new TTrendHour();
			tTrendHour.setOid(oid);
			tTrendHour.setType((short)0);
			tTrendHour.setHour(hour);
			tTrendHour.setTotalNum(poleNum[0] + poleNum[1] + poleNum[2]);
			tTrendHour.setPositiveNum(poleNum[0]);
			tTrendHour.setNeutralNum(poleNum[1]);
			tTrendHour.setNegativeNum(poleNum[2]);
			if (trendhourDao.findByTypeAndOidAndHour((short)0, oid, hour).isEmpty()) {
			}
			else {
				tTrendHour.setId(trendhourDao.findByTypeAndOidAndHour((short)0, oid, hour).get(0).getId());
			}
			trendhourDao.merge(tTrendHour);
		}
		return hourPoleNum.size();
	}

}
