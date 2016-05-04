package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ISubjectDao;
import nuaa.ggx.pos.dataprocess.model.TSubject;
import nuaa.ggx.pos.dataprocess.model.TUser;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("SubjectDao")
public class SubjectDao extends BaseDao<TSubject> implements ISubjectDao{
	
	private static Logger log = Logger.getLogger(SubjectDao.class);
	
	@SuppressWarnings("unchecked")
	public List<TSubject> findByUserID(Integer userId) {
		 log.debug("getting User instance with userId: " + userId);
	        try {
	        	TUser user = (TUser) getSession().load(TUser.class, userId);
	    		Criteria criteria = getSession().createCriteria(TSubject.class);
	    		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    		criteria.add(Restrictions.eq("TUser", user));
	    		return criteria.list();
	        } catch (RuntimeException re) {
	            log.error("get User_id failed", re);
	            throw re;
	        }
	}	
}


	