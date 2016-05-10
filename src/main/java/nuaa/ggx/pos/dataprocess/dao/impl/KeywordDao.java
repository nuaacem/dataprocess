package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.model.TKeyword;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("KeywordDao")
public class KeywordDao extends BaseDao<TKeyword> implements IKeywordDao{
	
	private static Logger log = Logger.getLogger(KeywordDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public int getIdByKeyword(String keyword) {
		log.debug("getting keywordid by keyword");
		List<TKeyword> tKeywords;
		int keywordid;
		try {
			Criteria criteria = getSession().createCriteria(TKeyword.class);
			criteria.add(Restrictions.eq("keyword", keyword));
			tKeywords = criteria.list();
			Iterator iterator = tKeywords.iterator();
			if(iterator.hasNext()){
				TKeyword tKeyword = (TKeyword) iterator.next();
				keywordid = tKeyword.getId();
			}
			else {
				return -1;
			}
		} catch (RuntimeException re) {
            log.error("getting keywordid failed", re);
            throw re;
		}
		return keywordid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getKeywordsName() {
		log.debug("getting all keywordsname");
		List<String> keywordsName = new ArrayList<>();
		List<TKeyword> keywords;
		try {
			Criteria criteria = getSession().createCriteria(TKeyword.class);
			criteria.add(Restrictions.gt("id", 0));
			keywords = criteria.list();
			for (TKeyword keyword : keywords) {
				keywordsName.add(keyword.getKeyword());
			}
		} catch (RuntimeException re) {
            log.error("getting all keywordsname failed", re);
            throw re;
		}
		return keywordsName;
	}
}


	