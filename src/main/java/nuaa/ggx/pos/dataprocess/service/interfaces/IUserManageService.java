package nuaa.ggx.pos.dataprocess.service.interfaces;

import nuaa.ggx.pos.dataprocess.model.TUser;

public interface IUserManageService {

	public TUser findById(Integer id);
	
	public void save(TUser user);
	
	public void update(TUser user);
	
	public TUser findByIdName(String name);
}
