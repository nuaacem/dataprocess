package nuaa.ggx.pos.dataprocess.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ISubjectDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendDayDao;
import nuaa.ggx.pos.dataprocess.model.TKeyword;
import nuaa.ggx.pos.dataprocess.model.TSubject;
import nuaa.ggx.pos.dataprocess.model.TTrendDay;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISubjectManageService;
import nuaa.ggx.pos.dataprocess.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("SubjectManageService")
public class SubjectManageService implements ISubjectManageService{
    
	@Autowired
	private ISubjectDao subjectDao;
	
	@Autowired
	private IKeywordDao keywordDao;
	
	@Autowired
	private ITrendDayDao iTrendDayDao;
	
	@Override
	public void findByIdlist() {
		//不会
	}

	@Override
	public void delete(Integer id) {
		subjectDao.delete(subjectDao.loadById(id));
	}

	@Override
	public void update(TSubject subject) {
		subjectDao.attachDirty(subject);
	}

	@Override
	public TSubject getById(Integer id) {
		return subjectDao.getById(id);
	}

	@Override
	public TSubject loadById(Integer id) {
		return subjectDao.loadById(id);
	}

	@Override
	public TSubject merge(TSubject subject) {
		return subjectDao.merge(subject);
	}

	@Override
	public void save(TSubject subject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<Integer, Integer[]> getAllSubjectsIdToKeywordsId() {
		HashMap<Integer, Integer[]> allSubjectsIdToKeywordsId = new HashMap<>();
		Set<Integer> allSubjectsId = subjectDao.getAllSubjectsId();
		Set<TKeyword> tKeywords;
		Integer[] tKeywordsId;
		for (Integer integer : allSubjectsId) {
			tKeywords = subjectDao.loadById(integer).getTKeywords();
			tKeywordsId = new Integer[tKeywords.size()];
			int i = 0;
			for (TKeyword tKeyword : tKeywords) {
				tKeywordsId[i] = (tKeyword.getId());
				i++;
			}
			allSubjectsIdToKeywordsId.put(integer, tKeywordsId);
		}
		return allSubjectsIdToKeywordsId;
	}
	
	@Override
	public Boolean updateSubjectsNum() {
		Set<Integer> subjectsId = subjectDao.getAllSubjectsId();
		List<TTrendDay> tTrendDays;
		if(subjectsId.isEmpty())
			return false;
		for (Integer subjectId : subjectsId) {
			tTrendDays = iTrendDayDao.findByTypeAndOid(Constants.IS_SUBJECT, subjectId);
			TSubject tSubject;
			Integer positiveNum = 0, neutralNum = 0, negativeNum = 0, preTotalNum;
			for (TTrendDay tTrendDay : tTrendDays) {
				positiveNum = positiveNum + tTrendDay.getPositiveNum();
				neutralNum = neutralNum + tTrendDay.getNeutralNum();
				negativeNum = negativeNum + tTrendDay.getNegativeNum();
			}
			tSubject = subjectDao.loadById(subjectId);
			if(tSubject.getTotalNum() == null)
				preTotalNum = 0;
			else
				preTotalNum = tSubject.getTotalNum();
			tSubject.setPositiveNum(positiveNum);
			tSubject.setNeutralNum(neutralNum);
			tSubject.setNegativeNum(negativeNum);
			tSubject.setTotalNum(positiveNum + neutralNum + negativeNum);
			tSubject.setUpdateNum(positiveNum + neutralNum + negativeNum - preTotalNum);
			tSubject.setUpdateTime(new Timestamp(new Date().getTime()));
			subjectDao.attachDirty(tSubject);
		}
		return true;
	}
}
