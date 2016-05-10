package nuaa.ggx.pos.dataprocess.service.interfaces;

import nuaa.ggx.pos.dataprocess.model.TSubject;


public interface ISubjectManageService {
	public TSubject getById(Integer id);
	public TSubject loadById(Integer id);
	public void findByIdlist();
	public void save(TSubject subject);
	public void delete(Integer id);
	public void update(TSubject subject);
	public TSubject merge(TSubject subject);
}
