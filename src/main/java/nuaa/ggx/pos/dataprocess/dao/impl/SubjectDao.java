package nuaa.ggx.pos.dataprocess.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nuaa.ggx.pos.dataprocess.dao.interfaces.ISubjectDao;
import nuaa.ggx.pos.dataprocess.model.TSubject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("SubjectDao")
public class SubjectDao extends BaseDao<TSubject> implements ISubjectDao{
	
	private static Logger log = Logger.getLogger(TSubject.class);
	
	@Override
	public Set<Integer> getAllSubjectsId() {
		log.debug("getting all subjectsid");
		Set<Integer> subjectsId = new HashSet<>();
		List<TSubject> subjects;
		try {
			subjects = listAll();
			for (TSubject subject : subjects) {
				subjectsId.add(subject.getId());
			}
		} catch (RuntimeException re) {
            log.error("getting all subjectsid failed", re);
            throw re;
		}
		return subjectsId;
	}
}


	