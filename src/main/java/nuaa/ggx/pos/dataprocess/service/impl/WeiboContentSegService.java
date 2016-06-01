package nuaa.ggx.pos.dataprocess.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IConsensusDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.IWeiboContentSegDao;
import nuaa.ggx.pos.dataprocess.model.TConsensus;
import nuaa.ggx.pos.dataprocess.model.TWeiboContentSeg;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISentimentWordPoleService;
import nuaa.ggx.pos.dataprocess.service.interfaces.IWeiboContentSegService;
import nuaa.ggx.pos.dataprocess.util.CallNLPIR;
import nuaa.ggx.pos.dataprocess.util.Constants;
import nuaa.ggx.pos.dataprocess.util.TimeWordsSplit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("WeiboContentSegService")
public class WeiboContentSegService implements IWeiboContentSegService{

	@Autowired
	private IWeiboContentSegDao weiboContentSegDao;
	
	@Autowired
	private ISentimentWordPoleService sentimentWordPoleService;
	
	@Autowired
	private IConsensusDao iConsensusDao;
	
	@Override
	public Boolean saveWeioboContentSeg() {
		TWeiboContentSeg weiboContentSeg;
		TConsensus consensus;
		List<TConsensus> tConsensuses = iConsensusDao.getConsensusesWhereStateAreNull();
		if (tConsensuses.isEmpty()) {
			return false;
		}
		for (TConsensus tConsensus : tConsensuses) {
			weiboContentSeg = new TWeiboContentSeg();
			String Words = null, pubTime = null;
			weiboContentSeg.setWid(tConsensus.getUrl().substring(tConsensus.getUrl().lastIndexOf("/") + 1));
			try {
				Words = CallNLPIR.excludeStopWord(CallNLPIR.segWords(tConsensus.getSummary()), " ");//分词，去停用词
				weiboContentSeg.setTxt(Words);
			} catch (IOException e) {
				e.printStackTrace();
			}
			weiboContentSeg.setCount(Words.split(" ").length);
			if (tConsensus.getPublishTime() != null) {
				pubTime = tConsensus.getPublishTime().toString();
				weiboContentSeg.setPubtime(pubTime.substring(0, pubTime.lastIndexOf(":")));
			}
			weiboContentSegDao.save(weiboContentSeg);
			
			consensus = iConsensusDao.loadById(tConsensus.getId());
			consensus.setState(1);
			iConsensusDao.attachDirty(consensus);
		}
		return true;
	}
	
	@Override
	public Boolean updateWeioboContentSeg() {
		TWeiboContentSeg weiboContentSeg;
		List<TWeiboContentSeg> tWeiboContentSegs = weiboContentSegDao.getPoleIsNull();
		if (tWeiboContentSegs.isEmpty()) {
			return false;
		}
		for (TWeiboContentSeg tWeiboContentSeg : tWeiboContentSegs) {
			weiboContentSeg = weiboContentSegDao.loadById(tWeiboContentSeg.getId());
			weiboContentSeg.setPole(sentimentWordPoleService.getWordsPole(tWeiboContentSeg.getTxt().split(" ")));
			weiboContentSegDao.attachDirty(weiboContentSeg);
		}
		return true;
	}
	
	@Override
	public HashMap<String, Integer[]> getHourOrDayPoleNumByKeyword(String keyword, Boolean hourOrDay) {
		List<TWeiboContentSeg> positiveList = weiboContentSegDao.getPositiveTWeiboContentSegListByKeyword(keyword);
		List<TWeiboContentSeg> neutralList = weiboContentSegDao.getNeutralTWeiboContentSegListByKeyword(keyword);
		List<TWeiboContentSeg> negativeList = weiboContentSegDao.getNegativeTWeiboContentSegListByKeyword(keyword);
		HashMap<String, Integer[]> hourPoleNum = new HashMap<>();
		String temp;
		String reg = ":";
		if(hourOrDay == Constants.IS_DAY)
			reg = " ";
		Integer[] initPositiveNum = {1, 0, 0};
		Integer[] initNeutralNum = {0, 1, 0};
		Integer[] initNegativeNum = {0, 0, 1};
		Integer[] poleNum;
		for (TWeiboContentSeg tWeiboContentSeg : positiveList) {
			temp = tWeiboContentSeg.getPubtime();
			temp = temp.substring(0, temp.indexOf(reg));
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
			temp = temp.substring(0, temp.indexOf(reg));
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
			temp = temp.substring(0, temp.indexOf(reg));
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
