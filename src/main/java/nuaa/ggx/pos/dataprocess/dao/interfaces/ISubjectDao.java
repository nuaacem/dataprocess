package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.TSubject;


public interface ISubjectDao extends IBaseDao<TSubject> {
	
	public List<TSubject> findByUserID(Integer userId);
}

