package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * BaseDao接口
 * @author KOC-RY
 *
 * @param <T>
 */

public interface IBaseDao<T> {
	
	public Session getSession();
	
	public Criteria getCriteria();
	
	public T getById(Integer id);

	public T loadById(Integer id);

	public void save(T t);
	
	public void delete(T t);
	
	public List<T> findByExample(T t);
	
	public T merge(T t);
	
	public void attachClean(T t);
	
	public void attachDirty(T t);
	
	public List<T> listAll();
}
