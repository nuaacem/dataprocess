package nuaa.ggx.pos.dataprocess.service.interfaces;

import java.security.NoSuchAlgorithmException;

import nuaa.ggx.pos.dataprocess.model.TUser;

public interface IUserService {

	public TUser getById(Integer id);
	
	public TUser loadById(Integer id);
	
	public boolean accountExist(String username);
	
	public TUser login(String username, String password) throws NoSuchAlgorithmException;
	
	public void saveRegister(TUser user) throws NoSuchAlgorithmException;
}
