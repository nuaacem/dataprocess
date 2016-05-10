package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.Iterator;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ISentimentWordPoleDao;
import nuaa.ggx.pos.dataprocess.model.TSentimentWordPole;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("SentimentWordPoleDao")
public class SentimentWordPoleDao extends BaseDao<TSentimentWordPole> implements ISentimentWordPoleDao{
	
	private static Logger log = Logger.getLogger(SentimentWordPoleDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public int getWordPole(String word) {
		log.debug("getting pole by word");
		List<TSentimentWordPole> sentimentWordPoles;
		int pole = 0;
		try {
			Criteria criteria = getSession().createCriteria(TSentimentWordPole.class);
			criteria.add(Restrictions.eq("word", word));
			sentimentWordPoles = criteria.list();
			for (Iterator iterator = sentimentWordPoles.iterator(); iterator.hasNext();) {
				TSentimentWordPole tSentimentWordPole = (TSentimentWordPole) iterator.next();
				pole = pole + tSentimentWordPole.getPole();
			}
		} catch (RuntimeException re) {
            log.error("getting pole failed", re);
            throw re;
		}
		return pole;
	}
	
}


	