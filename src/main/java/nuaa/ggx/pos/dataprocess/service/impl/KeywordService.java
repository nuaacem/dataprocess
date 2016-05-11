package nuaa.ggx.pos.dataprocess.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendDayDao;
import nuaa.ggx.pos.dataprocess.model.TKeyword;
import nuaa.ggx.pos.dataprocess.model.TTrendDay;
import nuaa.ggx.pos.dataprocess.service.interfaces.IKeywordService;
import nuaa.ggx.pos.dataprocess.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("KeywordService")
public class KeywordService implements IKeywordService{

	@Autowired
	private IKeywordDao iKeywordDao;
	
	@Autowired
	private ITrendDayDao iTrendDayDao;
	
	@Override
	public List<String> getAllKeywordsName() {
		return iKeywordDao.getAllKeywordsName();
	}
	
	@Override
	public Boolean updateKeywordsNum() {
		List<Integer> keywordsId = iKeywordDao.getAllKeywordsId();
		List<TTrendDay> tTrendDays;
		if(keywordsId.isEmpty())
			return false;
		for (Integer keywordId : keywordsId) {
			tTrendDays = iTrendDayDao.findByTypeAndOid(Constants.IS_KEYWORD, keywordId);
			TKeyword tKeyword;
			Integer positiveNum = 0, neutralNum = 0, negativeNum = 0, preTotalNum;
			for (TTrendDay tTrendDay : tTrendDays) {
				positiveNum = positiveNum + tTrendDay.getPositiveNum();
				neutralNum = neutralNum + tTrendDay.getNeutralNum();
				negativeNum = negativeNum + tTrendDay.getNegativeNum();
			}
			tKeyword = iKeywordDao.loadById(keywordId);
			if(tKeyword.getTotalNum() == null)
				preTotalNum = 0;
			else
				preTotalNum = tKeyword.getTotalNum();
			tKeyword.setPositiveNum(positiveNum);
			tKeyword.setNeutralNum(neutralNum);
			tKeyword.setNegativeNum(negativeNum);
			tKeyword.setTotalNum(positiveNum + neutralNum + negativeNum);
			tKeyword.setUpdateNum(positiveNum + neutralNum + negativeNum - preTotalNum);
			tKeyword.setUpdateTime(new Timestamp(new Date().getTime()));
			iKeywordDao.attachDirty(tKeyword);
		}
		return true;
	}
}
