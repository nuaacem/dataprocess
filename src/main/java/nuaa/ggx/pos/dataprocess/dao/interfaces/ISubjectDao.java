package nuaa.ggx.pos.dataprocess.dao.interfaces;

import java.util.Set;

import nuaa.ggx.pos.dataprocess.model.TSubject;

public interface ISubjectDao extends IBaseDao<TSubject> {
	public Set<Integer> getAllSubjectsId();
}

