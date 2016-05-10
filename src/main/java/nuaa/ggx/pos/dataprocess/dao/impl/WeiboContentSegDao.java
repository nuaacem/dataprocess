package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IWeiboContentSegDao;
import nuaa.ggx.pos.dataprocess.model.TWeiboContentSeg;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("WeiboContentSegDao")
public class WeiboContentSegDao extends BaseDao<TWeiboContentSeg> implements IWeiboContentSegDao{
	
	private static Logger log = Logger.getLogger(WeiboContentSegDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TWeiboContentSeg> getPositiveTWeiboContentSegListByKeyword(String keyword) {
		log.debug("getting t_weibo_content_seglist which pole gt 0 by keyword");
		List<TWeiboContentSeg> weiboContentSegs = null;
		try {
			Criteria criteria = getSession().createCriteria(TWeiboContentSeg.class);
			criteria.add(Restrictions.and(Restrictions.like("txt", keyword, MatchMode.ANYWHERE), Restrictions.gt("pole", 0)));
			weiboContentSegs = criteria.list();
		} catch (RuntimeException re) {
            log.error("getting t_weibo_content_seglist failed", re);
            throw re;
		}
		return weiboContentSegs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TWeiboContentSeg> getNeutralTWeiboContentSegListByKeyword(String keyword) {
		log.debug("getting t_weibo_content_seglist which pole eq 0 by keyword");
		List<TWeiboContentSeg> weiboContentSegs = null;
		try {
			Criteria criteria = getSession().createCriteria(TWeiboContentSeg.class);
			criteria.add(Restrictions.and(Restrictions.like("txt", keyword, MatchMode.ANYWHERE), Restrictions.eq("pole", 0)));
			weiboContentSegs = criteria.list();
		} catch (RuntimeException re) {
            log.error("getting t_weibo_content_seglist failed", re);
            throw re;
		}
		return weiboContentSegs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TWeiboContentSeg> getNegativeTWeiboContentSegListByKeyword(String keyword) {
		log.debug("getting t_weibo_content_seglist which pole lt 0 by keyword");
		List<TWeiboContentSeg> weiboContentSegs = null;
		try {
			Criteria criteria = getSession().createCriteria(TWeiboContentSeg.class);
			criteria.add(Restrictions.and(Restrictions.like("txt", keyword, MatchMode.ANYWHERE), Restrictions.lt("pole", 0)));
			weiboContentSegs = criteria.list();
		} catch (RuntimeException re) {
            log.error("getting t_weibo_content_seglist failed", re);
            throw re;
		}
		return weiboContentSegs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TWeiboContentSeg> getPoleIsNull() {
		log.debug("getting t_weibo_content_seglist");
		List<TWeiboContentSeg> tWeiboContentSegs = null;
		try {
			Criteria criteria = getSession().createCriteria(TWeiboContentSeg.class);
			criteria.setMaxResults(500);
			criteria.add(Restrictions.isNull("pole"));
			tWeiboContentSegs = criteria.list();
		} catch (RuntimeException re) {
            log.error("getting t_weibo_content_seglist failed", re);
            throw re;
		}
		return tWeiboContentSegs;
	}
}


	