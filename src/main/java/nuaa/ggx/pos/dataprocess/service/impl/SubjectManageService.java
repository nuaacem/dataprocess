package nuaa.ggx.pos.dataprocess.service.impl;

import java.util.List;
import nuaa.ggx.pos.dataprocess.dao.interfaces.IKeywordDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.ISubjectDao;
import nuaa.ggx.pos.dataprocess.dao.interfaces.IUserDao;
import nuaa.ggx.pos.dataprocess.model.TSubject;
import nuaa.ggx.pos.dataprocess.service.interfaces.ISubjectManageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("SubjectManageService")
public class SubjectManageService implements ISubjectManageService{
    
	@Autowired
	private ISubjectDao subjectDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IKeywordDao keywordDao;
	
	@Override
	public void findByIdlist() {
		//不会
	}

	@Override
	public List<TSubject> findByUserId(Integer userId) {
		return subjectDao.findByUserID(userId);
	}

	@Override
	public void delete(Integer id) {
		subjectDao.delete(subjectDao.loadById(id));
	}

	@Override
	public void update(TSubject subject) {
		subjectDao.attachDirty(subject);
	}

	@Override
	public TSubject getById(Integer id) {
		return subjectDao.getById(id);
	}

	@Override
	public TSubject loadById(Integer id) {
		return subjectDao.loadById(id);
	}

	@Override
	public TSubject merge(TSubject subject) {
		return subjectDao.merge(subject);
	}

	@Override
	public void save(TSubject subject) {
		// TODO Auto-generated method stub
		
	}

}
