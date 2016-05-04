package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IUserDao;
import nuaa.ggx.pos.dataprocess.model.TUser;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao层范例 其它Dao都可以仿照此类写
 * @author KOC-RY
 *
 */
@Repository("UserDao")
public class UserDao extends BaseDao<TUser> implements IUserDao{
	
	private static Logger log = Logger.getLogger(UserDao.class);
	
    public TUser findById(Integer id) {
        log.debug("getting User instance with id: " + id);
        try {
            TUser instance = (TUser) getSession()
                    .get("model.TUser", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get User failed", re);
            throw re;
        }
    }
    
    @SuppressWarnings("unchecked")
	public TUser findWhenLogin(String username, String password)
    {
    	log.debug("finding User when login");
    	TUser user = null;
        try {
            List<TUser> results = (List<TUser>) getSession()
                    .createCriteria(TUser.class)
                    .add(Restrictions.eq("username", username))
                    .add(Restrictions.eq("password", password))
                    .list();
            if (results != null && !results.isEmpty()) {
            	user = results.get(0);
            	log.debug("find User when login successful");
            	return user;
            }
            
        } catch (RuntimeException re) {
            log.error("find User when login failed", re);
            throw re;
        }
        return user;
    }

	@Override
	public boolean findIfExists(String username) {
		
	   	        log.debug("getting User instance with named: " + username);
	        try {
	        	Criteria criteria = getSession().createCriteria(TUser.class);
	    		criteria.add(Restrictions.eq("username", username));
	        	criteria.setProjection(Projections.rowCount());
	        	Integer count = Integer.parseInt(criteria.uniqueResult().toString());
	        	if(count!=null && count>0)
	        		return true;
	        	else
	        		return false;
	        } catch (RuntimeException re) {
	            log.error("get Usenamer failed", re);
	            throw re;
	        }
	}
    
}
