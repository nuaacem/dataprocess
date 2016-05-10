package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.HashMap;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IWeiboContentSegDao;
import nuaa.ggx.pos.dataprocess.model.TWeiboContentSeg;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISentimentWordPoleService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;
import nuaa.ggx.pos.dataprocess.util.WordsSplit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("WeiboContentSegService")
public class WeiboContentSegService implements IWeiboContentSegService{

	@Autowired
	private IWeiboContentSegDao weiboContentSegDao;
	
	@Autowired
	private ISentimentWordPoleService sentimentWordPoleService;
	
	@Override
	public Boolean updateWeioboContentSeg() {
		TWeiboContentSeg weiboContentSeg;
		List<TWeiboContentSeg> tWeiboContentSegs = weiboContentSegDao.getPoleIsNull();
		if (tWeiboContentSegs.isEmpty()) {
			return false;
		}
		for (TWeiboContentSeg tWeiboContentSeg : tWeiboContentSegs) {
			
			weiboContentSeg = weiboContentSegDao.loadById(tWeiboContentSeg.getId());
			weiboContentSeg.setPole(sentimentWordPoleService.getWordsPole(WordsSplit.wordsSplit(tWeiboContentSeg.getTxt())));
			weiboContentSegDao.attachDirty(weiboContentSeg);
		}
		return true;
	}
	
	@Override
	public HashMap<String, Integer[]> getHourPoleNumByKeyword(String keyword) {
		List<TWeiboContentSeg> positiveList = weiboContentSegDao.getPositiveTWeiboContentSegListByKeyword(keyword);
		List<TWeiboContentSeg> neutralList = weiboContentSegDao.getNeutralTWeiboContentSegListByKeyword(keyword);
		List<TWeiboContentSeg> negativeList = weiboContentSegDao.getNegativeTWeiboContentSegListByKeyword(keyword);
		HashMap<String, Integer[]> hourPoleNum = new HashMap<>();
		String temp;
		Integer[] initPositiveNum = {1, 0, 0};
		Integer[] initNeutralNum = {0, 1, 0};
		Integer[] initNegativeNum = {0, 0, 1};
		Integer[] poleNum;
		for (TWeiboContentSeg tWeiboContentSeg : positiveList) {
			temp = tWeiboContentSeg.getPubtime();
			temp = temp.substring(0, temp.indexOf(":"));
			if(hourPoleNum.containsKey(temp)){
				poleNum = hourPoleNum.get(temp).clone();
				poleNum[0] = poleNum[0] + 1;
				hourPoleNum.put(temp, poleNum);
			}
			else {
				hourPoleNum.put(temp, initPositiveNum);
			}	
		}
		for (TWeiboContentSeg tWeiboContentSeg : neutralList) {
			temp = tWeiboContentSeg.getPubtime();
			temp = temp.substring(0, temp.indexOf(":"));
			if(hourPoleNum.containsKey(temp)){
				poleNum = hourPoleNum.get(temp).clone();
				poleNum[1] = poleNum[1] + 1;
				hourPoleNum.put(temp, poleNum);
			}
			else {
				hourPoleNum.put(temp, initNeutralNum);
			}	
		}
		for (TWeiboContentSeg tWeiboContentSeg : negativeList) {
			temp = tWeiboContentSeg.getPubtime();
			temp = temp.substring(0, temp.indexOf(":"));
			if(hourPoleNum.containsKey(temp)){
				poleNum = hourPoleNum.get(temp).clone();
				poleNum[2] = poleNum[2] + 1;
				hourPoleNum.put(temp, poleNum);
			}
			else {
				hourPoleNum.put(temp, initNegativeNum);
			}	
		}
		return hourPoleNum;
	}
	
}
