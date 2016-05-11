package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ITrendDayDao;
import nuaa.ggx.pos.dataprocess.model.TTrendDay;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("TrendDayDao")
public class TrendDayDao extends BaseDao<TTrendDay> implements ITrendDayDao{
	
	private static Logger log = Logger.getLogger(TrendDayDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TTrendDay> findByTypeAndOid(Short type, Integer oid) {
		// TODO Auto-generated method stub
		log.debug("finding trenddays by type and oid");
		List<TTrendDay> trendDays = null;
		try {
			Criteria criteria = getSession().createCriteria(TTrendDay.class);
			criteria.add(Restrictions.and(Restrictions.eq("type", type), Restrictions.eq("oid", oid)));
			trendDays = criteria.list();
		} catch (RuntimeException re) {
            log.error("finding trenddays failed", re);
            throw re;
		}
		return trendDays;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TTrendDay> findByTypeAndOidAndDay(Short type, Integer oid, Integer day) {
		// TODO Auto-generated method stub
		log.debug("finding trenddays by type, oid and day");
		List<TTrendDay> trendDays = null;
		try {
			Criteria criteria = getSession().createCriteria(TTrendDay.class);
			criteria.add(Restrictions.and(Restrictions.eq("type", type), Restrictions.eq("oid", oid), Restrictions.eq("day", day)));
			trendDays = criteria.list();
		} catch (RuntimeException re) {
            log.error("finding trenddays failed", re);
            throw re;
		}
		return trendDays;
	}
}


	