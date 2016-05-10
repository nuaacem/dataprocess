package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendHourDao;
import nuaa.ggx.pos.dataprocess.model.TTrendHour;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("TrendHourDao")
public class TrendHourDao extends BaseDao<TTrendHour> implements ITrendHourDao{
	
	private static Logger log = Logger.getLogger(TrendHourDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TTrendHour> findByTypeAndOid(Short type, Integer oid) {
		// TODO Auto-generated method stub
		log.debug("finding trendhours by type and oid");
		List<TTrendHour> trendHours = null;
		try {
			Criteria criteria = getSession().createCriteria(TTrendHour.class);
			criteria.add(Restrictions.and(Restrictions.eq("type", type), Restrictions.eq("oid", oid)));
			trendHours = criteria.list();
		} catch (RuntimeException re) {
            log.error("finding trendhours failed", re);
            throw re;
		}
		return trendHours;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TTrendHour> findByTypeAndOidAndHour(Short type, Integer oid, Integer hour) {
		// TODO Auto-generated method stub
		log.debug("finding trendhours by type and oid and hour");
		List<TTrendHour> trendHours = null;
		try {
			Criteria criteria = getSession().createCriteria(TTrendHour.class);
			criteria.add(Restrictions.and(Restrictions.eq("type", type), Restrictions.eq("oid", oid), Restrictions.eq("hour", hour)));
			trendHours = criteria.list();
		} catch (RuntimeException re) {
            log.error("finding trendhours failed", re);
            throw re;
		}
		return trendHours;
	}
}


	