package nuaa.ggx.pos.dataprocess.dao.impl;

import static org.hibernate.criterion.Example.create;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import nuaa.ggx.pos.dataprocess.dao.interfaces.IBaseDao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * Dao层基类 每个Dao类都要继承自此类
 * @author KOC-RY
 *
 * @param <T>继承此类时 泛型T设置为Dao对应的Model
 */
public class BaseDao<T> implements IBaseDao<T> {
	
	private Class<T> entityClass;
	
	private static Logger log = Logger.getLogger(BaseDao.class);
	
	@SuppressWarnings("unchecked")
	public BaseDao() {
        this.entityClass = (Class<T>)getSuperClassGenricType(getClass(), 0);  
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })  
    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {  
          
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
           return Object.class;  
        }  
        //返回表示此类型实际类型参数的 Type 对象的数组。  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
                     return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
              return Object.class;  
        }  
  
        return (Class) params[index];  
    }  
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void save(T transientInstance) {
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(T persistentInstance) {
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T instance) {
		try {
			List<T> results = (List<T>) getSession()
					.createCriteria("dao.Many").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			System.out.println();
			log.error("find by example failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public T merge(T detachedInstance) {
		try {
			T result = (T) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(T instance) {
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(T instance) {
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Integer id) {
		log.debug("getting instance with id: " + id);
        try {
            T instance = (T) getSession().get(entityClass, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get User failed", re);
            throw re;
        }
	}
	
	@SuppressWarnings("unchecked")	
	@Override
	public T loadById(Integer id) {
		log.debug("getting instance with id: " + id);
        try {
            T instance = (T) getSession().load(entityClass, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get User failed", re);
            throw re;
        }
	}

	@Override
	public Criteria getCriteria() {
		return getSession().createCriteria(this.entityClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll() {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("state", 1));
		return criteria.list();
	}	
}
