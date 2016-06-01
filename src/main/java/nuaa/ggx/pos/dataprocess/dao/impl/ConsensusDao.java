package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IConsensusDao;
import nuaa.ggx.pos.dataprocess.model.TConsensus;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("ConsensusDao")
public class ConsensusDao extends BaseDao<TConsensus> implements IConsensusDao{

	private static Logger log = Logger.getLogger(ConsensusDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TConsensus> getConsensusesWhereStateAreNull() {
		log.debug("getting consensuses where state are null");
		List<TConsensus> consensuses;
		try {
			Criteria criteria = getSession().createCriteria(TConsensus.class);
			criteria.add(Restrictions.isNull("state"));
			consensuses = criteria.list();
		} catch (RuntimeException re) {
            log.error("getting consensuses failed", re);
            throw re;
		}
		return consensuses;
	}
}
